package com.crsri.mes.util.dingtalk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

/**
 * 根据E应用的appkey和appsecret获取accessToken的工具类
 * @author 2011102394
 *
 */
public class AccessTokeUtil {

	private static final Logger bizLogger = LoggerFactory.getLogger(AccessTokeUtil.class);

    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();
            
            request.setAppkey(DingTalkConstant.APPKEY);
            request.setAppsecret(DingTalkConstant.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            
            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }
    
}
