package com.crsri.mes.common.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.crsri.mes.service.OSSService;
import com.crsri.mes.util.ApplicationContextHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈OSS备份的定时任务〉
 *
 * @author zcj
 * @date 2018/12/5 15:19
 * @since 1.0.0
 */
@Slf4j
public class OSSJob implements Job {

    private OSSService ossService =  ApplicationContextHelper.getBean(OSSService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ossService.uploadBackup();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        log.info("资源文件备份成功,时间：{}",format);
    }


}
