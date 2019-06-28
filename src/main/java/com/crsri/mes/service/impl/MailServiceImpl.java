package com.crsri.mes.service.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.MailService;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈邮件相关的service的实现〉
 *
 * @author zcj
 * @date 2018/12/4 11:13
 * @since 1.0.0
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String form;

    @Override
    public ServerResponse sendAttachmentsMail(String[] to, String subject, String content, String filePath) {
        log.info("发送邮件被调用{}",System.currentTimeMillis());
        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(form);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            FileSystemResource file=new FileSystemResource(new File(filePath));
            String fileName=filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,file);
            mailSender.send(message);
            return ServerResponse.createBySuccessMessage("带附件的邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByFailMessage("带附件的邮件发送失败");
        }
    }

}
