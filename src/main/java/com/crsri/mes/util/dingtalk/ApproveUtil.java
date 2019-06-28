package com.crsri.mes.util.dingtalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.util.dingtalk.LogFormatter.LogEvent;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 审批相关的工具类
 * 
 * @author 2011102394
 *
 */
@Slf4j
public class ApproveUtil {

	/**
	 * 发起审批实例
	 * 
	 * @param approve
	 * @return
	 */
	public static ServerResponse<String> startProcessInstance(ApproveInstanceVO approve) {
		try {
			DefaultDingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.URL_PROCESSINSTANCE_START);
			OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
			// 设置审批的id
			request.setProcessCode(approve.getProcessCode());
			// 设置agentID
			request.setAgentId(approve.getAgentId());
			// 设置审批人
			request.setApprovers(approve.getApprovers());
			// 设置抄送人员
			request.setCcList(approve.getCcList());
			// 设置发起人
			request.setOriginatorUserId(approve.getOriginatorUserId());
			// 设置审批部门
			request.setDeptId(approve.getDeptId());
			// 设置抄送时间
			request.setCcPosition(approve.getCcPosition());
			// 设置审批表单的内容
			request.setFormComponentValues(approve.getFormComponentValueVos());
			OapiProcessinstanceCreateResponse response = client.execute(request, AccessTokeUtil.getToken());
			if(!response.isSuccess()) {
				//审批发起失败
				if(StringUtils.isBlank(CallBackURLUtils.getCallBackUrl())) {
					//未注册回调地址,自动注册
					CallBackURLUtils.registerCallBackUrl();
				}
			}
			if (response.getErrcode().longValue() != 0) {
				return ServerResponse.createByFailMessage(response.getErrmsg());
			}
			return ServerResponse.createBySuccess(response.getProcessInstanceId());
		} catch (Exception e) {
			String errLog = LogFormatter.getKVLogData(LogEvent.END,
					LogFormatter.KeyValue.getNew("processInstance", JSON.toJSONString(approve)));
			log.info(errLog, e);
			return ServerResponse.createByFailMessage("审批实例发送出现异常");
		}
	}

	public static FormComponentValueVo imageUtil(String imagePath, String host) {
		FormComponentValueVo image = new FormComponentValueVo();
		image.setName("图片");
		if (StringUtils.isBlank(imagePath)) {
			return null;
		}
		String[] urls = imagePath.split(";");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < urls.length; i++) {
			urls[i] = host +"upload/"+ urls[i];
			list.add(urls[i]);
		}
		JSONArray imageValue = JSONArray.parseArray(JSONObject.toJSONString(list));
		image.setValue(JSONObject.toJSONString(imageValue));
		return image;
	}

	public static FormComponentValueVo remarkUtil(String remarkInfo) {
		if (StringUtils.isBlank(remarkInfo)) {
			remarkInfo = "无";
		}
		FormComponentValueVo remark = new FormComponentValueVo();
		remark.setName("备注");
		remark.setValue(remarkInfo);
		return remark;
	}

	public static JSONArray dingTalkFormImageValue(String imagePath, String host) {
		if (StringUtils.isBlank(imagePath)) {
			return null;
		}
		String[] urls = imagePath.split(";");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < urls.length; i++) {
			urls[i] = host + urls[i];
			list.add(urls[i]);
		}
		return JSONArray.parseArray(JSONObject.toJSONString(list));
	}
	
	public static Map<String, Integer> getApproveResult(String type, String result) {
		int statutValue	= 0;
		int resultValue = 0;
		if(DingTalkApproveConstant.processInstanceType.FINISHE.equals(type)) {
			//审批结束
			if(DingTalkApproveConstant.processInstanceResult.AGREE.equals(result)) {
				//审批通过
				statutValue = DingTalkApproveConstant.approveStatus.APPROVE_FINISHED;
				resultValue = DingTalkApproveConstant.approveResult.AGREE;
			}else {
				//审批拒绝
				statutValue = DingTalkApproveConstant.approveStatus.APPROVE_FINISHED;
				resultValue = DingTalkApproveConstant.approveResult.REFUSE;
			}
		}else if(DingTalkApproveConstant.processInstanceType.TERMINATE.equals(type)){
			//审批撤销
			statutValue = DingTalkApproveConstant.approveStatus.APPROVE_FINISHED;
			resultValue = DingTalkApproveConstant.approveResult.REFUSE;
		}else {
			//TODO 审批实例开始的处理
		}
		Map<String, Integer> res = new HashMap<>();
		res.put("status", statutValue);
		res.put("result", resultValue);
		return res;
	}
}
