package com.crsri.mes.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.CustomerTaskDetailMapper;
import com.crsri.mes.dao.CustomerTaskMapper;
import com.crsri.mes.dao.RecordTopicMapper;
import com.crsri.mes.entity.CustomerTask;
import com.crsri.mes.entity.CustomerTaskDetail;
import com.crsri.mes.entity.RecordTopic;
import com.crsri.mes.service.CustomerTaskService;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.service.RecordTopicService;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.util.dingtalk.AccessTokeUtil;
import com.crsri.mes.vo.CustomerTaskVO;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

@Service
public class CustomerTaskServiceImpl implements CustomerTaskService {

	@Resource
	private CustomerTaskMapper customerTaskMapper;

	@Resource
	private CustomerTaskDetailMapper detailMapper;

	@Value("${web.host}")
	private String host;
	
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private RecordTopicMapper recordTopicMapper;
	
	@Resource
	private RecordTopicService recordTopicService;
	

	private static final String PERMISSION_NAME = "receiveDingTalkMsg:whenCustomerTaskStart";

	// Id前缀
	private static final String PREFIX = "C";

	// 待处理
	private static final Integer PENDING = 0;
	// 进行中
	private static final Integer PROCESSING = 1;
	// 已完成
	private static final Integer SOLVED = 2;

	private static final Logger log = LoggerFactory.getLogger(CustomerTaskServiceImpl.class);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServerResponse save(CustomerTaskVO vo) {
		try {
			// 对象转换
			CustomerTask customerTask = vo.customerTaskVO2CustomerTask(vo);
			String id = PREFIX + System.currentTimeMillis();
			customerTask.setId(id);
			// 保存售后任务对象设置任务状态为0--待处理
			customerTask.setStatus(PENDING);
			customerTaskMapper.insertSelective(customerTask);
			List<CustomerTaskDetail> detail = vo.getDetail();
			// 保存售后问题描述的明细信息
			for (CustomerTaskDetail customerTaskDetail : detail) {
				customerTaskDetail.setTaskId(id);
				detailMapper.insertSelective(customerTaskDetail);
			}
			// 给指定的售后人员发送钉钉工作通知
			boolean res = this.sendOfficialMessage(customerTask);
			if (!res) {
				log.error("工作通知发送失败！");
			}
			return ServerResponse.createBySuccessMessage("新增售后任务成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("新增售后任务失败");
		}

	}

	/**
	 * 给售后人员发送通知
	 * 
	 * @param customerTask
	 * @return
	 */
	private boolean sendOfficialMessage(CustomerTask customerTask) {
		// 发送钉钉工作通知，参考：https://open-doc.dingtalk.com/microapp/serverapi2/pgoxpy
		// 创建发送钉钉工作通知的客户端
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.MESSAGE_ASYNCSEND);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		// 设置接收人列表，这里设置为所有的维修人员
		// 获取拥有指定权限的人的userId
		List<String> userList = permissionService.queryUserIdHadPermission(PERMISSION_NAME);
		if (CollectionUtils.isEmpty(userList)) {
			return false;
		}	
		request.setUseridList(String.join(",", userList));
		request.setAgentId(DingTalkConstant.AGENTID);
		request.setToAllUser(false);
		request.setHttpMethod("POST");

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		// 发送OA消息
		// 钉钉OA消息的格式 参考：https://open-doc.dingtalk.com/microapp/serverapi2/ye8tup
		msg.setMsgtype("oa");
		msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
		msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
		// 设置消息头部标题
		// 消息的头部标题 (向普通会话发送时有效，向企业会话发送时会被替换为微应用的名字)，所以这里设置背景颜色遮盖
		msg.getOa().getHead().setBgcolor("#ddd");
		msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
		List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
		msg.getOa().getBody().setForm(list);
		// 项目名称
		msg.getOa().getBody().setTitle("收到一条新的售后反馈单，请及时处理");
		OapiMessageCorpconversationAsyncsendV2Request.Form company = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		company.setKey("客户名称");
		company.setValue(customerTask.getCustomerCompany());

		msg.getOa().getBody().getForm().add(company);

		OapiMessageCorpconversationAsyncsendV2Request.Form deviceCategory = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		deviceCategory.setKey("项目名称");
		deviceCategory.setValue(customerTask.getProjectName());

		msg.getOa().getBody().getForm().add(deviceCategory);
		// 联系人
		OapiMessageCorpconversationAsyncsendV2Request.Form contact = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		contact.setKey("联系人:");
		contact.setValue(customerTask.getContact());
		msg.getOa().getBody().getForm().add(contact);
		// 联系方式
		OapiMessageCorpconversationAsyncsendV2Request.Form phone = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		phone.setKey("联系方式:");
		phone.setValue(customerTask.getPhone());
		msg.getOa().getBody().getForm().add(phone);
		// 发送时间
		OapiMessageCorpconversationAsyncsendV2Request.Form sendTime = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		sendTime.setKey("发送时间:");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(new Date());
		sendTime.setValue(format);
		msg.getOa().getBody().getForm().add(sendTime);

		request.setMsg(msg);

		// 获取token
		String accessToken = AccessTokeUtil.getToken();
		try {
			OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
			return true;
		} catch (ApiException e) {
			log.error(e.getErrMsg());
			return false;
		}
	}

	@Override
	public ServerResponse getCustomerTaskList(JSONObject json) {
		String customerCompany = StringUtil.tirm(json.getString("customerCompany"));
		String customerProjectName = StringUtil.tirm(json.getString("customerProjectName"));
		Integer customerTaskStatus = json.getInteger("customerTaskStatus");
		String customerOperator = StringUtil.tirm(json.getString("customerOperator"));
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		List<CustomerTask> list = customerTaskMapper.getCustomerTaskList(customerCompany, customerProjectName,
				customerTaskStatus, customerOperator, startTime, stopTime);
		return ServerResponse.createBySuccess(list);
	}

	@Override
	public ServerResponse getCustomerTaskCount(JSONObject json) {
		// 客户公司
		String customerCompany = StringUtil.tirm(json.getString("customerCompany"));
		// 项目名称
		String customerProjectName = StringUtil.tirm(json.getString("customerProjectName"));
		// 售后人员
		String customerOperator = StringUtil.tirm(json.getString("customerOperator"));
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		int pendingNumber = customerTaskMapper.getCustomerTaskCount(customerCompany, customerProjectName,
				customerOperator, startTime, stopTime, PENDING);
		int processingNumber = customerTaskMapper.getCustomerTaskCount(customerCompany, customerProjectName,
				customerOperator, startTime, stopTime, PROCESSING);
		int solvedNumber = customerTaskMapper.getCustomerTaskCount(customerCompany, customerProjectName,
				customerOperator, startTime, stopTime, SOLVED);
		int totalNumber = customerTaskMapper.getCustomerTaskCount(customerCompany, customerProjectName,
				customerOperator, startTime, stopTime, null);
		Map<String, Integer> res = new HashMap<>();
		res.put("pendingNumber", pendingNumber);
		res.put("processingNumber", processingNumber);
		res.put("solvedNumber", solvedNumber);
		res.put("totalNumber", totalNumber);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse updateCustomerTask(CustomerTask task) {
		customerTaskMapper.updateByPrimaryKeySelective(task);
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse getCustomerTask(String id) {
		// 获取售后任务
		CustomerTask task = customerTaskMapper.selectByPrimaryKey(id);
		// 获取售后任务问题描述明细
		List<CustomerTaskDetail> details = detailMapper.queryByTaskId(id);
		for (CustomerTaskDetail item : details) {
			item.setImage(ImageUtil.imageUtil(item.getImage(), host));
		}
		CustomerTaskVO vo = new CustomerTaskVO();
		vo = vo.customerTask2CustomerTaskVO(task);
		vo.setDetail(details);
		return ServerResponse.createBySuccess(vo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse deleteCustomerTask(String id) {
		// 1、删除售后任务
		customerTaskMapper.deleteByPrimaryKey(id);
		// 2、删除售后任务详情
		detailMapper.deleteByTaskId(id);
		// 3、删除与该售后任务相关的记录和回复
		List<RecordTopic> topics = recordTopicMapper.queryByTaskId(id);
		for (RecordTopic recordTopic : topics) {
			recordTopicService.deleteById(recordTopic.getId());
		}
		return ServerResponse.createBySuccessMessage("操作成功");
	}

}
