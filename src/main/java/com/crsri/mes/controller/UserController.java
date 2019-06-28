package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.User;
import com.crsri.mes.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(tags = "用户账户操作相关的接口")
public class UserController {

	@Resource
	private UserService userService;

	@ApiOperation("根据用户id获取用户信息")
	@GetMapping("/user/{id}")
	public ServerResponse<User> selectUserById(@ApiParam("用户id") @PathVariable("id") Integer id) {

		return userService.selectUserById(id);
	}

	@ApiOperation("获取用户列表")
	@GetMapping("/user/all")
	public ServerResponse selectUserAll() {
		return userService.selectUserAll();
	}

	/**
	 * 删除用户账户
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation("根据id删除用户")
	@DeleteMapping("/user/account/{id}")
	@SystemControllerLog(description="删除用户账户")
	public ServerResponse deleteAccount(@ApiParam("用户id") @PathVariable("id") Integer id) {
		return userService.deleteAccount(id);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("/user/account")
	@ApiOperation("更新用户信息")
	@SystemControllerLog(description="更新用户信息")
	public ServerResponse updateAccount(@ApiParam("用户对象") @RequestBody User user) {
		return userService.updateAccount(user);
	}

	/**
	 * 设置用户角色
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/user/setRole")
	@ApiOperation("设置用户角色")
	@SystemControllerLog(description="设置用户角色")
	public ServerResponse setRole(@RequestBody JSONObject json) {
		return userService.setRole(json);
	}
}
