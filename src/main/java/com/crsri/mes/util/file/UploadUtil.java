package com.crsri.mes.util.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传的工具类
 * @author 2011102394
 *
 */
public class UploadUtil {

	public static String transformImage(MultipartFile file, String dirPath) throws IllegalStateException, IOException {
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String newName = UUID.randomUUID().toString()+suffix;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File filePath = new File(dirPath + File.separator +sdf.format(date));
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdirs();
        }
        String newFilePath = dirPath + File.separator +sdf.format(date)+File.separator+ newName;
        file.transferTo(new File(newFilePath));
        return sdf.format(date)+"/"+ newName;
	}
	

}
