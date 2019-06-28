package com.crsri.mes.service;

import java.util.List;

import com.crsri.mes.common.response.ServerResponse;

/**
 * 权限操作的service接口
 * @author 2011102394
 *
 */
public interface PermissionService {

	/**
	 * 获取权限列表
	 * @return
	 */
	ServerResponse queryPermissionList();

	/**
	 * 根据权限名获取userId
	 */
	List<String> queryUserIdHadPermission(String permissionName);
}
