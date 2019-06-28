package com.crsri.mes.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.crsri.mes.dao.ProduceComponentProcessMapper;
import com.crsri.mes.dao.RecordTopicMapper;
import com.crsri.mes.dao.RepairTaskMapper;
import com.crsri.mes.entity.ProduceComponent;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.RecordTopic;
import com.crsri.mes.entity.RepairTask;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.service.RecordTopicService;
import com.crsri.mes.service.RepairTaskService;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.util.dingtalk.AccessTokeUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

@Service
public class RepairTaskServiceImpl implements RepairTaskService {

	@Resource
	private RepairTaskMapper repairTaskMapper;
	
	@Resource
	private RecordTopicService recordTopicService;

	@Value("${web.host}")
	private String host;
	
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private RecordTopicMapper recordTopicMapper;

	private static final String PERMISSION_NAME = "receiveDingTalkMsg:whenRepairTaskStart";

	// 待处理
	private static final Integer PENDING = 0;
	// 进行中
	private static final Integer PROCESSING = 1;
	// 已完成
	private static final Integer SOLVED = 2;

	private static final Logger log = LoggerFactory.getLogger(RepairTaskServiceImpl.class);
	
	@Resource
	private ProduceComponentProcessMapper produceComponentProcessMapper;

	@Override
	public ServerResponse save(RepairTask repairTask) {
		String idPrefix = "R";
		repairTask.setId(idPrefix + System.currentTimeMillis());
		repairTask.setStatus(0);
		try {
			// 维修单数据存入数据库
			repairTaskMapper.insertSelective(repairTask);
			// 给维修人员发送钉钉工作通知
			boolean res = this.sendOfficialMessage(repairTask);
			if (!res) {
				log.error("工作通知发送失败！");
			}
			return ServerResponse.createBySuccess(repairTask.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("维修单提交失败");
		}

	}

	private boolean sendOfficialMessage(RepairTask repairTask) {
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
		msg.getOa().getHead().setText("收到来自" + repairTask.getProjectName() + "的维修单");
		msg.getOa().getHead().setBgcolor("#ddd");
		msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
		List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
		msg.getOa().getBody().setForm(list);
		// 设备型号
		msg.getOa().getBody().setTitle("收到来自" + repairTask.getProjectName() + "的维修单");
		OapiMessageCorpconversationAsyncsendV2Request.Form deviceCategory = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		deviceCategory.setKey("设备型号:");
		deviceCategory.setValue(repairTask.getDeviceCategory());

		msg.getOa().getBody().getForm().add(deviceCategory);
		// 设备编号
		OapiMessageCorpconversationAsyncsendV2Request.Form deviceId = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		deviceId.setKey("设备编号:");
		deviceId.setValue(repairTask.getDeviceId());
		msg.getOa().getBody().getForm().add(deviceId);
		// 联系人
		OapiMessageCorpconversationAsyncsendV2Request.Form contact = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		contact.setKey("联系人:");
		contact.setValue(repairTask.getContact());
		msg.getOa().getBody().getForm().add(contact);
		// 联系方式
		OapiMessageCorpconversationAsyncsendV2Request.Form phone = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		phone.setKey("联系方式:");
		phone.setValue(repairTask.getPhone());
		msg.getOa().getBody().getForm().add(phone);
		// 故障描述
		OapiMessageCorpconversationAsyncsendV2Request.Form troubleDesc = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		troubleDesc.setKey("故障描述:");
		troubleDesc.setValue(repairTask.getTroubleDesc());
		msg.getOa().getBody().getForm().add(troubleDesc);
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
			return false;
		}

	}

	@Override
	public ServerResponse getRepairOrderCount(JSONObject json) {
		// 项目名称
		String projectName = StringUtil.tirm(json.getString("projectName"));
		// 设备类型
		String deviceCategory = StringUtil.tirm(json.getString("deviceCategory"));
		// 设备编号
		String deviceId = StringUtil.tirm(json.getString("deviceId"));
		// 开始时间
		Date startTime = json.getDate("startTime");
		// 结束时间
		Date stopTime = json.getDate("stopTime");
		int pendingNumber = repairTaskMapper.queryRepairTaskCountByStatus(PENDING, deviceId, deviceCategory,
				projectName, startTime, stopTime);
		int processingNumber = repairTaskMapper.queryRepairTaskCountByStatus(PROCESSING, deviceId, deviceCategory,
				projectName, startTime, stopTime);
		int solvedNumber = repairTaskMapper.queryRepairTaskCountByStatus(SOLVED, deviceId, deviceCategory, projectName,
				startTime, stopTime);
		int totalNumber = repairTaskMapper.queryRepairTaskCountByStatus(null, deviceId, deviceCategory, projectName,
				startTime, stopTime);
		Map<String, Integer> res = new HashMap<>();
		res.put("pendingNumber", pendingNumber);
		res.put("processingNumber", processingNumber);
		res.put("solvedNumber", solvedNumber);
		res.put("totalNumber", totalNumber);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse getRepairTaskList(JSONObject json) {
		// 任务状态
		Integer repairTaskStatus = json.getInteger("repairTaskStatus");
		// 维修人员
		String repairOperator = StringUtil.tirm(json.getString("repairOperator"));
		// 项目名称
		String projectName = StringUtil.tirm(json.getString("projectName"));
		// 设备型号
		String deviceCategory = StringUtil.tirm(json.getString("deviceCategory"));
		// 设备编号
		String deviceId = StringUtil.tirm(json.getString("deviceId"));
		// 开始时间
		Date startTime = json.getDate("startTime");
		// 结束时间
		Date stopTime = json.getDate("stopTime");
		List<RepairTask> list = repairTaskMapper.queryRepairTaskList(repairTaskStatus, repairOperator,
				projectName, deviceCategory, deviceId, startTime, stopTime);
		for (RepairTask repairTask : list) {
			repairTask.setImage(ImageUtil.imageUtil(repairTask.getImage(), host));
		}
		return ServerResponse.createBySuccess(list);
	}

	@Override
	public ServerResponse updateRepairTask(RepairTask repairTask) {
		String id = repairTask.getId();
		Integer status = repairTask.getStatus();
		if(status==2) {
			//维修任务结束
			RepairTask record = repairTaskMapper.selectByPrimaryKey(id);
			//将维修合格的组件重新流转
			updateComponent(record);
		}
		repairTaskMapper.updateByPrimaryKeySelective(repairTask);
		//如果是关闭
		return ServerResponse.createBySuccessMessage("操作成功");
	}
	
	public void updateComponent(RepairTask repairTask) {
		String id = repairTask.getDeviceId();
		String componentName = repairTask.getDeviceCategory();
		ProduceComponentProcess component = new ProduceComponentProcess();
		component.setId(id);
		component.setComponentName(componentName);
		component.setInspectionRemark("维修检验");
		ProduceComponentProcess process = produceComponentProcessMapper.selectByPrimaryKey(id);
		if(process!=null) {
			//该组件在生产流程中，重置该组件的在检验合格之后的所有信息
			produceComponentProcessMapper.updateAfterRepair(component);
		}else {
			//该组件未在生产流程中，新增该组件信息
			component.setInspectionApproveResult(1);
			produceComponentProcessMapper.insertSelective(component);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse deleteRepairTask(String id) {
		//根据id删除维修任务
		repairTaskMapper.deleteByPrimaryKey(id);
		//删除与之相关的维修记录与回复信息
		List<RecordTopic> recordTopics = recordTopicMapper.queryByTaskId(id);
		for (RecordTopic recordTopic : recordTopics) {
			recordTopicService.deleteById(recordTopic.getId());
		}
		return ServerResponse.createBySuccessMessage("操作成功");
	}

}











