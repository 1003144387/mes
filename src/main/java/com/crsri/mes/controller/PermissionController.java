package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限操作相关的接口
 * 
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="权限操作的接口")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	@GetMapping("/permission/list")
	@ApiOperation("获取权限列表")
	public ServerResponse queryPermissionList() {
		return permissionService.queryPermissionList();
	}
}
