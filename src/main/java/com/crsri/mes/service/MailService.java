package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈邮件相关的service〉
 *
 * @author zcj
 * @date 2018/12/4 11:13
 * @since 1.0.0
 */
public interface MailService {

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    ServerResponse sendAttachmentsMail(String[] to, String subject, String content, String filePath);

}
