package com.crsri.mes.common.quartz.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.crsri.mes.service.MailService;
import com.crsri.mes.util.ApplicationContextHelper;
import com.crsri.mes.util.MySQLDatabaseBackup;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈定时备份数据库任务〉
 *
 * @author zcj
 * @date 2018/12/4 11:27
 * @since 1.0.0
 */
@Slf4j
public class DatabaseBackupJob implements Job {

    private MailService mailService = ApplicationContextHelper.getBean(MailService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            backup();
            log.info("数据库备份任务完成,时间:{}",System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
            log.error("定时备份数据库数据发生异常，异常信息:{}", Arrays.toString(e.getStackTrace()));
        }
    }

    private void backup() throws InterruptedException {
        String hostIp = "127.0.0.1";
        String username = "root";
        String password = "gcaqs123";
        String savePath = "D:/CK-MES/data/sql_backup";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = simpleDateFormat.format(new Date())+".sql";
        String databaseName = "mes";
        boolean backupRes = MySQLDatabaseBackup.exportDatabaseTool(hostIp, username, password, savePath, fileName, databaseName);
        if(backupRes){
            String to = "1211996804@qq.com";
            String subject = "数据库备份";
            String content = "数据库备份文件详见附件";
            mailService.sendAttachmentsMail(to.split(","),subject,content,savePath+ File.separator+fileName);
        }else{
            log.error("数据备份失败");
        }

    }
}
