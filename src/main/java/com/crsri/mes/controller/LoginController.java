package com.crsri.mes.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @ClassName:  AuthController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 2011102394 
 * @date:   2018年12月16日 下午6:35:48   
 *
 */
@RestController
@Api(tags="用户登录相关的接口")
public class LoginController {
	
	@Resource
	private LoginService loginService;

	/**
	 * 
	 * @Title: notLogin   
	 * @Description: 当前用户未登录  
	 * @param: @return      
	 * @return: ServerResponse      
	 * @throws
	 */
	@GetMapping("/notlogin")
	public ServerResponse notLogin(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return ServerResponse.createByFailMessage("尚未登录，请登录");
	}
	
	@GetMapping("/notRole")
	public ServerResponse noRole() {
		return ServerResponse.createByFailMessage("无权限操作");
	}
	
	/**
	 * 
	 * @Title: appLogin   
	 * @Description: app端的登录  
	 * @param: authCode 钉钉免登鉴权码      
	 * @return: ServerResponse      
	 * @throws
	 */
	@PostMapping("/login/app")
	@ApiOperation("E应用登录")
	public ServerResponse appLogin(@ApiParam("钉钉鉴权的code码")String authCode,HttpSession session) {
		return loginService.appLogin(authCode,session);
	}
	
	/**
	 * 
	 * @Title: login   
	 * @Description: web端的登录
	 * @param: @return      
	 * @return: ServerResponse      
	 * @throws
	 */
	@PostMapping("/login/web")
	@ApiOperation("web端的登录")
	public ServerResponse webLogin(@RequestBody JSONObject json,HttpSession session) {
		return loginService.webLogin(json,session);
	}
	
	@GetMapping("/logout")
	@ApiOperation("退出登录")
	public ServerResponse logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ServerResponse.createBySuccessMessage("退出系统成功");
	}
}
