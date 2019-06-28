package com.crsri.mes.common.quartz.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.util.ApplicationContextHelper;
import com.crsri.mes.util.dingtalk.AccessTokeUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

/**
 * 〈一句话功能简述〉<br>
 * 〈库存管理周报任务〉
 *
 * @author zcj
 * @date 2018/10/23 14:13
 * @since 1.0.0
 */
public class StockWeekReportJob implements Job {

	private PermissionService permissionService = ApplicationContextHelper.getBean(PermissionService.class);

	private static final String PERMISSION_NAME = "receiveDingTalkMsg:receiveReport";


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println("提示周报消息已发送");
		sentLinkMessage();
	}

	private void sentLinkMessage() {
		DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.MESSAGE_ASYNCSEND);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		List<String> userIds = permissionService.queryUserIdHadPermission(PERMISSION_NAME);
		request.setUseridList(String.join(",", userIds));
		request.setAgentId(DingTalkConstant.AGENTID);
		request.setToAllUser(false);
		request.setHttpMethod("POST");

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype("link");
		msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
		msg.getLink().setTitle("本周CK-MES审批周报已生成");
		msg.getLink().setText("点击查看");
		msg.getLink().setMessageUrl(DingTalkURLConstant.REPORT_PAGE_URL);
		msg.getLink().setPicUrl("https://i.imgur.com/j8Dy2yx.jpg");
		request.setMsg(msg);

		// 获取token
		String accessToken = AccessTokeUtil.getToken();
		try {
			OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
			System.out.println(response.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}
