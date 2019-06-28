package com.crsri.mes.common.quartz.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.dao.CustomerTaskMapper;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.util.ApplicationContextHelper;
import com.crsri.mes.util.dingtalk.DingTalkMessageUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈未完成的售后任务统计〉
 *
 * @author zcj
 * @date 2018/12/3 21:37
 * @since 1.0.0
 */
public class UnFinishedCustomerTaskJob implements Job {

	private PermissionService permissionService = ApplicationContextHelper.getBean(PermissionService.class);

	private static final String PERMISSION_NAME = "receiveDingTalkMsg:unFinishedCustomerTaskCount";


    private CustomerTaskMapper customerTaskMapper =
            ApplicationContextHelper.getBean(CustomerTaskMapper.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sendMessage();
    }

    public void sendMessage() {
        int pendingCustomerTaskCount = customerTaskMapper.getCustomerTaskCount(null,
                null, null, null, null, 0);
        int procedingCustomerTaskCount = customerTaskMapper.getCustomerTaskCount(null,
        		null, null, null, null, 1);
        JSONObject json = new JSONObject();
        List<String> users = permissionService.queryUserIdHadPermission(PERMISSION_NAME);
        String msg = "你有" + (pendingCustomerTaskCount+procedingCustomerTaskCount) + "个未完成的售后任务\n"
        		+ "其中：\n"
        		+ "待处理"+pendingCustomerTaskCount+"个\n"
        		+ "进行中"+procedingCustomerTaskCount+"个\n"
        		+ "请及时完成";
        String receiver = String.join(",", users);
        json.put("receiver", receiver);
        json.put("sender","系统消息");
        json.put("message", msg);
        if(pendingCustomerTaskCount==0&&procedingCustomerTaskCount==0) {
        	return;
        }
        DingTalkMessageUtil.sendOAMessage(json);
    }
}
