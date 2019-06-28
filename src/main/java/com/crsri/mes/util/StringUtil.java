package com.crsri.mes.util;

import org.apache.commons.lang3.StringUtils;

import com.crsri.mes.common.response.ServerResponse;

/**
 * 字符串处理的工具类
 * 
 * @author 2011102394
 *
 */
public class StringUtil {

	public static ServerResponse validateRepate(String[] codes, String number, String msg) {
		for (String code : codes) {
			if (number.contains(code)) {
				return ServerResponse.createByFailMessage(code + " " + msg);
			}
		}
		return ServerResponse.createBySuccess();
	}

	/**
	 * 去除字符串空格
	 * 
	 * @param str
	 * @return
	 */
	public static String tirm(String str) {
		return StringUtils.isBlank(str) ? str : str.trim();
	}
}
