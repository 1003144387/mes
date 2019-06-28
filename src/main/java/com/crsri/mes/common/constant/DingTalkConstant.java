package com.crsri.mes.common.constant;

/**
 * 
 * @ClassName: DingTalkConstant
 * @Description:TODO(钉钉开发相关的常量（线上环境）)
 * @author: 2011102394
 * @date: 2018年12月16日 下午8:58:42
 *
 */
public class DingTalkConstant {

	/**
	 * 企业corpid, 需要修改成开发者所在企业
	 */
	public static final String CORP_ID = "dinge9a51823819f2b82";
	/**
	 * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
	 */
	public static final String APPKEY = "dingtisviiyeemndqpor";
	/**
	 * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
	 */
	public static final String APPSECRET = "Z6qHa9LJ9vDAwNoCY_lX3we5fLBr_dJtVX9VW2WGM91KcS_pFJL2TvsQ1qhWxko9";

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

	public static final long AGENTID = 198218413;

	/**
	 * 回调host
	 */
	public static final String CALLBACK_URL_HOST = "http://123.85.3.97:9090";
}
