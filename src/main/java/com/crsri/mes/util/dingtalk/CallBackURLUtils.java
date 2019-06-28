package com.crsri.mes.util.dingtalk;

import java.util.Arrays;

import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.taobao.api.ApiException;

/**
 * 关于企业回调的工具类
 * 
 * @author 2011102394
 *
 */
public class CallBackURLUtils {

	/**
	 * 删除企业回调
	 * 
	 * @throws RuntimeException
	 * @throws ApiException
	 */

	public static void deletCallBackUrl() throws ApiException, RuntimeException {
		// 删除企业已有的回调
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.DELETE_CALLBACK);
		OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
		request.setHttpMethod("GET");
		client.execute(request, AccessTokeUtil.getToken());
		System.out.println("====回调地址成功删除======");
	}

	/**
	 * 获取企业回调地址
	 * @return
	 * @throws RuntimeException 
	 * @throws ApiException 
	 */
	public static String getCallBackUrl() throws ApiException, RuntimeException {
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.GET_CALLBACK);
		OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
		request.setHttpMethod("GET");
		OapiCallBackGetCallBackResponse response = client.execute(request,AccessTokeUtil.getToken());
		String body = response.getUrl();
		return body;
	}
	/**
	 * 注册审批的回调地址
	 * 注意：审批事件的回调类型有两种
	 * bpms_task_change :  审批任务开始，结束，转交
	 * bpms_instance_change：审批实例开始，结束
	 * 	本应用中的审批流程只监听审批实例的开始和结束
	 * 
	 * @throws RuntimeException
	 * @throws ApiException
	 */
	public static void registerCallBackUrl() throws ApiException, RuntimeException {
		// 重新为企业注册回调
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.REGISTER_CALLBACK);
		OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
		registerRequest.setUrl(DingTalkConstant.CALLBACK_URL_HOST + "/dingTalk/callback");
		registerRequest.setAesKey(DingTalkConstant.ENCODING_AES_KEY);
		registerRequest.setToken(DingTalkConstant.TOKEN);
		//这里
		registerRequest.setCallBackTag(Arrays.asList("bpms_instance_change"));
		OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest,
				AccessTokeUtil.getToken());
		if (registerResponse.isSuccess()) {
			System.out.println("回调注册成功了！！！");
		}
	}
	
//	public static void main(String[] args) throws ApiException, RuntimeException {
//		System.out.println(getCallBackUrl());
//	}
//	public static void main(String[] args) throws ApiException, RuntimeException {
//		registerCallBackUrl();
//	}
//	public static void main(String[] args) throws ApiException, RuntimeException {
//		deletCallBackUrl();
//	}
}
