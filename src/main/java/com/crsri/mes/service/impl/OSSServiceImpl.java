package com.crsri.mes.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crsri.mes.service.OSSService;
import com.crsri.mes.util.OSSUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈OSS相关的接口的实现类〉
 *
 * @author zcj
 * @date 2018/12/5 16:14
 * @since 1.0.0
 */
@Service
public class OSSServiceImpl implements OSSService {


    @Value("${OSS.endpoint}")
    private String endpoint;

    @Value("${OSS.accessKeyId}")
    private String accessKeyId;

    @Value("${OSS.accessKeySecret}")
    private String accessKeySecret;

    @Value("${OSS.bucketName}")
    private String bucketName;

    @Value("${OSS.localFilePath}")
    private String localFilePath;

    @Value("${OSS.basePath}")
    private String basePath;

    @Override
    public void uploadBackup() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        OSSUtil.upload(endpoint,accessKeyId,accessKeySecret,bucketName,localFilePath+"/"+format,basePath);
    }
}
