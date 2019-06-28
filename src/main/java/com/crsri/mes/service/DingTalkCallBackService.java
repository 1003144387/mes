package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 钉钉回调事件的处理接口
 * 
 * @author 2011102394
 *
 */
public interface DingTalkCallBackService {

	/**
	 * 处理审批相关的回调
	 * @param processCode 审批流的id
	 * @param processBody 审批的消息体
	 */
	void handleApproveCallBack(String processCode, JSONObject processBody);
}
