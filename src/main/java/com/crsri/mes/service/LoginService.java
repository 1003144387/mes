package com.crsri.mes.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;

public interface LoginService {

	/**
	 * 
	 * @Title: appLogin   
	 * @Description: 小程序端登录的接口
	 * @param: @param authCode
	 * @param: @return      
	 * @return: ServerResponse      
	 * @throws
	 */
	ServerResponse appLogin(String authCode,HttpSession session);

	/**
	 * web端登录
	 * @return
	 */
	ServerResponse webLogin(JSONObject json,HttpSession session);


}
