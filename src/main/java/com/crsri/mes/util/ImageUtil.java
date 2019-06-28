package com.crsri.mes.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 图片处理的工具类
 * 
 * @author 2011102394
 *
 */
public class ImageUtil {

	private static final String FLAG = "无";
	
	/**
	 * 图片地址拼接
	 * 
	 * @param imagePath 数据库中存储的图片地址
	 * @param host 主机地址
	 * @return
	 */
	public static String imageUtil(String imagePath, String host) {
		if (StringUtils.isBlank(imagePath)||FLAG.equals(imagePath)) {
			return null;
		}
		String[] urls = imagePath.split(";");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < urls.length; i++) {
			urls[i] =host + "upload/"+ urls[i];
			list.add(urls[i]);
		}
		return String.join(";", urls);
	}
}
