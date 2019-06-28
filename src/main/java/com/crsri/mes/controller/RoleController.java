package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.Role;
import com.crsri.mes.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色操作的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="角色操作相关的接口")
public class RoleController {

	
	@Resource
	private RoleService roleService;
	
	/**
	 * 获取角色列表
	 * @return
	 */
	@GetMapping("/role/all")
	@ApiOperation("获取角色列表")
	public ServerResponse getRoleList() {
		return roleService.getRoleList();
	}
	
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	@PutMapping("/role")
	@ApiOperation("更新角色信息")
	@SystemControllerLog(description="更新角色信息")
	public ServerResponse updateRole(@RequestBody Role role) {
		return roleService.updateRole(role);
	}
	
	/**
	 * 根据id删除角色
	 */
	@DeleteMapping("/role/{id}")
	@ApiOperation("根据id删除角色")
	@SystemControllerLog(description="删除角色")
	public ServerResponse deleteRole(@PathVariable("id") Integer roleId) {
		return roleService.deleteRole(roleId);
	}
	
	/**
	 * 配置角色权限
	 * @param json
	 * @return
	 */
	@PostMapping("/role/setPermission")
	@ApiOperation("配置角色权限")
	@SystemControllerLog(description="配置角色权限")
	public ServerResponse setRolePermission(@RequestBody JSONObject json) {
		return roleService.setRolePermission(json);
	}
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@PostMapping("/role")
	@ApiOperation("新增角色")
	@SystemControllerLog(description="新增角色")
	public ServerResponse saveRole(@RequestBody Role role) {
		return roleService.saveRole(role);
	}
}














