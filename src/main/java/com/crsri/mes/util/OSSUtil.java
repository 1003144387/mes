package com.crsri.mes.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.oss.OSSClient;

/**
 * 〈一句话功能简述〉<br>
 * 〈阿里云OSS上传文件示例〉
 *
 * @author zcj
 * @date 2018/12/4 16:15
 * @since 1.0.0
 */
public class OSSUtil {

    /**
     * 上传文件到OSS
     *
     * @param endpoint        OSS存储的地区
     * @param accessKeyId     阿里云帐号的id
     * @param accessKeySecret 阿里云访问密钥
     * @param bucketName      存储块的名称
     * @param filePath        上传文件在本地的存储地址
     * @param basePath        oss中文件的根目录
     */
    public static void upload(String endpoint,
                              String accessKeyId,
                              String accessKeySecret,
                              String bucketName,
                              String filePath,
                              String basePath) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        //读取指定的文件
        List<Map<String, String>> list = new ArrayList<>();
        List<Map<String, String>> fileLists = getFileLists(list, filePath, basePath);
        if(fileLists.size()!=0){
            fileLists.forEach(item -> {
                String objectName = item.get("objectName");
                String localFilePath = item.get("localFileName");
                // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
                File file = new File(localFilePath);
                ossClient.putObject(bucketName, objectName, file);
            });
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 读取上传文件的本地目录
     *
     * @param list
     * @param filePath
     * @param basePath
     * @return
     */
    private static List<Map<String, String>> getFileLists(List<Map<String, String>> list, String filePath, String basePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在");
        } else {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File file1 : files) {
                        getFileLists(list, file1.getPath(), basePath);
                    }
                }
            } else {
                String objectName = basePath + "/" + file.getParentFile().getName() + "/" + file.getName();
                String localFileName = file.getAbsolutePath();
                Map<String, String> res = new HashMap<>();
                res.put("objectName", objectName);
                res.put("localFileName", localFileName);
                list.add(res);
            }
        }
        return list;
    }

}
