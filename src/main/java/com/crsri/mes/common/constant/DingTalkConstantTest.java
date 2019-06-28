package com.crsri.mes.common.constant;

/**
 * 
 * @ClassName:  DingTalkConstant   
 * @Description:TODO(钉钉开发相关的常量)   
 * @author: 2011102394 
 * @date:   2018年12月16日 下午8:58:42   
 *
 */
public class DingTalkConstantTest {

	/**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "dingaf124a5fffee1c1035c2f4657eb6378f";
    /**
     * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPKEY = "dingnq0psgrnvk2kz0ff";
    /**
     * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPSECRET = "okjqhXjGvFnGf5GHvc85FNIh9e_AIL7ALVuTRwelJtN42ljI0Rmhls24VIyAiU9C";

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "21cq6q1gsl59sh5oyjxauygcitsboxxccc02xr2zgpb";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "123456";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 197963511L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到
     */
    public static final String PROCESS_CODE = "***";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = "http://9xjsjz.natappfree.cc";
}
