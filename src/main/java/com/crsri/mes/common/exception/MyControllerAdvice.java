package com.crsri.mes.common.exception;

import javax.security.auth.login.AccountException;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crsri.mes.common.response.ServerResponse;

/**
 * 自定义异常捕获
 * 
 * @author 2011102394
 *
 */
@RestControllerAdvice
public class MyControllerAdvice {

	/**
	 * 
	 * 请求参数异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ServerResponse errorHandler(Exception ex) {
		ex.printStackTrace();
		return ServerResponse.createByFailMessage("参数错误，请检查");
	}
	
	@ExceptionHandler(AccountException.class)
	public ServerResponse accountException(AccountException exception) {
		exception.printStackTrace();
		return ServerResponse.createByFailMessage("登录失败");
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ServerResponse authorzationException(AuthorizationException exception) {
		exception.printStackTrace();
		return ServerResponse.createByFailMessage("无权限操作");
	}
	

}
