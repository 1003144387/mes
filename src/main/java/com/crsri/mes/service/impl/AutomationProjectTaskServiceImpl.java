package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.common.log.annontation.SystemServiceLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.AutomationProjectTaskMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.entity.AutomationProjectTask;
import com.crsri.mes.service.AutomationProjectTaskService;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.util.dingtalk.AccessTokeUtil;
import com.crsri.mes.vo.CountMonthVO;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务相关的service接口的实现〉
 *
 * @author zcj
 * @date 2018/11/26 16:35
 * @since 1.0.0
 */
@Service
@Slf4j
public class AutomationProjectTaskServiceImpl implements AutomationProjectTaskService {

	// 进行中
	private static final Integer PROCESSING = 0;
	// 已完成
	private static final Integer SOLVED = 1;

	@Value("${web.host}")
	private String host;

	@Resource
	private UserMapper userMapper;

	@Resource
	private AutomationProjectTaskMapper automationProjectTaskMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	@SystemServiceLog(description = "创建自动化任务")
	public ServerResponse save(AutomationProjectTask automationProjectTask) {
		try {
			String prefix = "APT";
			automationProjectTask.setId(prefix + System.currentTimeMillis());
			// 设置任务状态为进行中
			automationProjectTask.setStatus(PROCESSING);
			automationProjectTaskMapper.insertSelective(automationProjectTask);
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动化项目任务创建失败,异常信息：{}", Arrays.toString(e.getStackTrace()));
			return ServerResponse.createByFailMessage("自动化项目任务创建失败");
		}

	}

	@Override
	public ServerResponse getAutomationProjectTaskList(JSONObject json) {
		// 项目名
		String projectName = StringUtil.tirm(json.getString("projectName"));
		// 项目类型
		String projectCategory = StringUtil.tirm(json.getString("projectCategory"));
		// 客户单位
		String company = StringUtil.tirm(json.getString("company"));
		// 创建人员
		String operator = StringUtil.tirm(json.getString("operator"));
		// 任务状态
		Integer status = json.getInteger("status");
		// 开始时间
		Date startTime = json.getDate("startTime");
		// 结束时间
		Date stopTime = json.getDate("stopTime");
		List<AutomationProjectTask> list = automationProjectTaskMapper.getAutomationProjectTaskList(projectName,
				projectCategory, company, operator, status, startTime, stopTime);
		// 处理任务中的附件
		list.forEach(item -> {
			if (item.getAttachment() != null) {
				item.setAttachment(host + item.getAttachment());
			}
		});
		return ServerResponse.createBySuccess(list);
	}

	@Override
	public ServerResponse getAutomationProjectTaskCount(JSONObject json) {
		// 项目名
		String projectName = StringUtil.tirm(json.getString("projectName"));
		// 项目类型
		String projectCategory = StringUtil.tirm(json.getString("projectCategory"));
		// 客户单位
		String company = StringUtil.tirm(json.getString("company"));
		// 创建人员
		String operator = StringUtil.tirm(json.getString("operator"));
		// 开始时间
		Date startTime = json.getDate("startTime");
		// 结束时间
		Date stopTime = json.getDate("stopTime");
		// 进行中的任务数量
		int processingNumber = automationProjectTaskMapper.getAutomationProjectTaskCountByStatus(projectName,
				projectCategory, company, operator, PROCESSING, startTime,
				stopTime);
		// 已完成的任务数量
		int solvedNumber = automationProjectTaskMapper.getAutomationProjectTaskCountByStatus(projectName,
				projectCategory, company, operator,SOLVED, startTime, stopTime);
		// 任务总数
		int totalNumber = automationProjectTaskMapper.getAutomationProjectTaskCountByStatus(projectName,
				projectCategory, company, operator, null, startTime, stopTime);

		Map<String, Integer> res = new HashMap<>();
		res.put("processingNumber", processingNumber);
		res.put("solvedNumber", solvedNumber);
		res.put("totalNumber", totalNumber);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	@SystemServiceLog(description = "删除自动化项目任务")
	@Transactional(rollbackFor = Exception.class)
	public ServerResponse deleteAutomationProjectTaskById(String id) {
		try {
			automationProjectTaskMapper.deleteByPrimaryKey(id);
			return ServerResponse.createBySuccessMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除自动化项目任务失败，异常信息：{}", Arrays.toString(e.getStackTrace()));
			return ServerResponse.createByFailMessage("删除任务失败");
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServerResponse updateAutomationProjectTask(AutomationProjectTask task) {
		try {
			automationProjectTaskMapper.updateByPrimaryKeySelective(task);
			return ServerResponse.createBySuccessMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新自动化项目任务失败，异常信息：{}", Arrays.toString(e.getStackTrace()));
			return ServerResponse.createByFailMessage("删除任务失败");
		}
	}

	@Override
	public ServerResponse getAutomationProjectTaskSolution(String id) {
		AutomationProjectTask automationProjectTask = automationProjectTaskMapper.selectByPrimaryKey(id);
		Map<String, Object> res = new HashMap<>();
		res.put("solution", automationProjectTask.getSolution());
		res.put("author", automationProjectTask.getSolutionAuthor());
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse getAutomationProjectTaskById(String id) {

		AutomationProjectTask automationProjectTask = automationProjectTaskMapper.selectByPrimaryKey(id);
		automationProjectTask.setAttachment(host + automationProjectTask.getAttachment());
		return ServerResponse.createBySuccess(automationProjectTask);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServerResponse customerConfirmation(AutomationProjectTask task) {
		try {
			// 更新客户确认相关内容（客户名称、联系方式、确认结果、客户意见）
			automationProjectTaskMapper.updateByPrimaryKeySelective(task);
			AutomationProjectTask automationProjectTask = automationProjectTaskMapper.selectByPrimaryKey(task.getId());
			String solutionAuthor = automationProjectTask.getSolutionAuthor();
			String solutionAuthorUserId = userMapper.queryUserByName(solutionAuthor).getUserId();
			// 将客户确认的结果发送给方案编写人员
			sendMessage(solutionAuthorUserId, automationProjectTask);
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("客户确认解决方案发生异常，异常信息:{}", Arrays.toString(e.getStackTrace()));
		}
		return ServerResponse.createByFail();
	}

	private boolean sendMessage(String userId, AutomationProjectTask task) {
		// 发送钉钉工作通知，参考：https://open-doc.dingtalk.com/microapp/serverapi2/pgoxpy
		// 创建发送钉钉工作通知的客户端
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.MESSAGE_ASYNCSEND);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		// 设置接收人列表，这里设置为方案编写人员
		request.setUseridList(userId);
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
		List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList();
		msg.getOa().getBody().setForm(list);
		// 项目名称
		msg.getOa().getBody().setTitle("自动化项目解决方案客户确认通知");
		OapiMessageCorpconversationAsyncsendV2Request.Form company = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		company.setKey("客户单位:");
		company.setValue(task.getCompany());

		msg.getOa().getBody().getForm().add(company);

		OapiMessageCorpconversationAsyncsendV2Request.Form projectName = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		projectName.setKey("项目名称:");
		projectName.setValue(task.getProjectName());

		msg.getOa().getBody().getForm().add(projectName);
		// 联系人
		OapiMessageCorpconversationAsyncsendV2Request.Form contact = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		contact.setKey("客户签字:");
		contact.setValue(task.getCustomerSignature());
		msg.getOa().getBody().getForm().add(contact);
		// 联系方式
		OapiMessageCorpconversationAsyncsendV2Request.Form phone = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		phone.setKey("联系方式:");
		phone.setValue(task.getCustomerSignaturePhone());
		msg.getOa().getBody().getForm().add(phone);

		request.setMsg(msg);
		// 确认结果
		OapiMessageCorpconversationAsyncsendV2Request.Form result = new OapiMessageCorpconversationAsyncsendV2Request.Form();
		result.setKey("确认结果:");
		if (task.getCustomerConfirmationResult() == 0) {
			result.setValue("通过");
		} else {
			result.setValue("未通过");
		}
		msg.getOa().getBody().getForm().add(result);

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
	public ServerResponse getAutomationProjectTaskCountGroupByMonth(JSONObject json) {

		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		List<CountMonthVO> processingTaskCount = automationProjectTaskMapper.getAutomationProjectTaskCountGroupByMonth(
				PROCESSING, startTime, stopTime);
		List<CountMonthVO> solvedTaskCount = automationProjectTaskMapper.getAutomationProjectTaskCountGroupByMonth(
				SOLVED, startTime, stopTime);
		List<CountMonthVO> allTaskCount = automationProjectTaskMapper.getAutomationProjectTaskCountGroupByMonth(null,
				startTime, stopTime);
		Map<String, Object> processingTaskCount1 = new HashMap<>();
		if (processingTaskCount.size() > 0) {
			processingTaskCount1 = processingTaskCount.stream()
					.collect(Collectors.toMap(CountMonthVO::getTime, CountMonthVO::getNumber));
			processingTaskCount1.put("name", "进行中");
		}
		Map<String, Object> solvedTaskCount1 = new HashMap<>();
		solvedTaskCount1.put("name", "已完成");
		if (solvedTaskCount.size() > 0) {
			solvedTaskCount1 = solvedTaskCount.stream()
					.collect(Collectors.toMap(CountMonthVO::getTime, CountMonthVO::getNumber));
			solvedTaskCount1.put("name", "已完成");
		}
		Map<String, Object> allTaskCount1 = new HashMap<>();

		if (allTaskCount.size() > 0) {
			allTaskCount1 = allTaskCount.stream()
					.collect(Collectors.toMap(CountMonthVO::getTime, CountMonthVO::getNumber));
			allTaskCount1.put("name", "总任务");
		}
		Map<String, Object> res = new HashMap<>();
//        res.put("processingTaskCount",processingTaskCount1);
//        res.put("solvedTaskCount",solvedTaskCount1);
		res.put("allTaskCount", allTaskCount1);
		return ServerResponse.createBySuccess(res);
	}

}
