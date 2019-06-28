package com.crsri.mes.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.DingTalkCallBackService;
import com.crsri.mes.util.dingtalk.DingTalkMessageUtil;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;

import io.swagger.annotations.Api;

/**
 * 钉钉回调相关的接口
 * 
 * @author 2011102394
 *
 */
@RestController
@Api(tags="钉钉回调的接口（开放给钉钉服务器访问）")
public class DingTalkCallBackController {

	private static final Logger bizLogger = LoggerFactory.getLogger("BIZ_CALLBACKCONTROLLER");
	private static final Logger mainLogger = LoggerFactory.getLogger(DingTalkCallBackController.class);

	/**
	 * 审批实例回调
	 */
	private static final String BPMS_INSTANCE_CHANGE = "bpms_instance_change";

	/**
	 * 相应钉钉回调时的值
	 */
	private static final String CALLBACK_RESPONSE_SUCCESS = "success";
	
	
	@Resource
	private DingTalkCallBackService dingTalkCallBackService;

	@PostMapping(value = "/dingTalk/callback")
	public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce,
			@RequestBody(required = false) JSONObject json) {
		String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
		try {
			DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(DingTalkConstant.TOKEN,
					DingTalkConstant.ENCODING_AES_KEY, DingTalkConstant.CORP_ID);

			// 从post请求的body中获取回调信息的加密数据进行解密处理
			String encryptMsg = json.getString("encrypt");
			String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
			JSONObject obj = JSON.parseObject(plainText);
			// 根据回调数据类型做不同的业务处理
			String eventType = obj.getString("EventType");
			if (BPMS_INSTANCE_CHANGE.equals(eventType)) {
				bizLogger.info("收到审批实例状态更新: " + plainText);
				String processCode = obj.getString("processCode");
				//实现审批的业务逻辑
				dingTalkCallBackService.handleApproveCallBack(processCode,obj);
			} else {
				// TODO 其他类型事件处理
			}

			// 返回success的加密信息表示回调处理成功
			return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(),
					Utils.getRandomStr(8));
		} catch (Exception e) {
			//TODO 给管理员发送钉钉通知，告知回调失败
			// 失败的情况，应用的开发者应该通过告警感知，并干预修复
			mainLogger.error("process callback failed！" + params, e);
			return null;
		}

	}
	
	@PostMapping("/api/dingTalk/sendMessage")
    @SystemControllerLog(description = "发送钉钉消息")
    public ServerResponse sendDingTalkMessage(@RequestBody JSONObject json){
        return DingTalkMessageUtil.sendOAMessage(json);
    }
}
