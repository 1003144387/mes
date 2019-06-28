package com.crsri.mes.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.util.file.UploadUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文件上传的工具类
 * 
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="文件上传的接口")
public class FileController {

	@Value("${web.upload-path}")
	private String dirPath;

	@ApiOperation("E应用端的图片上传")
	@PostMapping("/file/upload/app")
	public ServerResponse uploadImage(MultipartFile file) {
		String filePath = null;
		try {
			filePath = UploadUtil.transformImage(file, dirPath + "upload/");
			return ServerResponse.createBySuccess(filePath);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("文件上传失败");
		}

	}

	@ApiOperation("PC端文件上传")
	@PostMapping("/file/upload/web")
	public ServerResponse uploadImageWeb(MultipartFile file) {
		String filePath = null;
		try {
			filePath = UploadUtil.transformImage(file, dirPath + "upload/");
			return ServerResponse.createBySuccess(filePath);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("文件上传失败");
		}

	}
}
