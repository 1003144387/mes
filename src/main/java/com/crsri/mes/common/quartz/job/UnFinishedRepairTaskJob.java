package com.crsri.mes.common.quartz.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.dao.RepairTaskMapper;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.util.ApplicationContextHelper;
import com.crsri.mes.util.dingtalk.DingTalkMessageUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈未完成的维修任务统计〉
 *
 * @author zcj
 * @date 2018/12/3 21:31
 * @since 1.0.0
 */
public class UnFinishedRepairTaskJob implements Job {

	private PermissionService permissionService = ApplicationContextHelper.getBean(PermissionService.class);

	private static final String PERMISSION_NAME = "receiveDingTalkMsg:unFinishedRepairTaskCount";

	private RepairTaskMapper repairTaskMapper = ApplicationContextHelper.getBean(RepairTaskMapper.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		sendMessage();
		System.out.println("消息发送完毕");
	}

	public void sendMessage() {
		int pendingRepairTaskCount = repairTaskMapper.queryRepairTaskCountByStatus(0, null, null, null, null, null);
		int procedingRepairTaskCount = repairTaskMapper.queryRepairTaskCountByStatus(1, null, null, null, null, null);
		JSONObject json = new JSONObject();
		List<String> users = permissionService.queryUserIdHadPermission(PERMISSION_NAME);
		 String msg = "你有" + (pendingRepairTaskCount+procedingRepairTaskCount) + "个未完成的维修任务\n"
	        		+ "其中：\n"
	        		+ "待处理"+pendingRepairTaskCount+"个\n"
	        		+ "进行中"+procedingRepairTaskCount+"个\n"
	        		+ "请及时完成";
	        String receiver = String.join(",", users);
	        json.put("receiver", receiver);
	        json.put("sender","系统消息");
	        json.put("message", msg);
	        if(pendingRepairTaskCount==0&&procedingRepairTaskCount==0) {
	        	return;
	        }
	        DingTalkMessageUtil.sendOAMessage(json);
	}
}
