package com.crsri.mes.common.constant;

/**
 * 
 * @ClassName: DingTalkURLConstant
 * @Description:钉钉的URL常量类
 * @author: 2011102394
 * @date: 2018年12月16日 下午9:00:06
 *
 */
public class DingTalkURLConstant {

	/**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     *获取用户姓名的接口url
     */
    public static final String URL_USER_GET = "https://oapi.dingtalk.com/user/get";

    /**
     * 发起审批实例的接口url
     */
    public static final String URL_PROCESSINSTANCE_START = "https://oapi.dingtalk.com/topapi/processinstance/create";

    /**
     * 获取审批实例的接口url
     */
    public static final String URL_PROCESSINSTANCE_GET = "https://oapi.dingtalk.com/topapi/processinstance/get";

    /**
     * 发送企业通知消息的接口url
     */
    public static final String MESSAGE_ASYNCSEND = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    /**
     * 删除企业回调接口url
     */
    public static final String DELETE_CALLBACK = "https://oapi.dingtalk.com/call_back/delete_call_back";

    /**
     * 注册企业回调接口url
     */
    public static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/call_back/register_call_back";
    
    /**
     * 获取企业回调接口的url
     */
    public static final String GET_CALLBACK = "https://oapi.dingtalk.com/call_back/get_call_back";
    
    /**
     * 周报页面的地址
     */
    public static final String REPORT_PAGE_URL = "http://123.85.3.97:9090/modules/report/index.html";
}
