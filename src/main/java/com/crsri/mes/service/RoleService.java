package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.Role;

/**
 * 角色相关操作的service接口
 * 
 * @author 2011102394
 *
 */
public interface RoleService {

	/**
	 * 获取用户角色列表
	 * 
	 * @return
	 */
	ServerResponse getRoleList();

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 * @return
	 */
	ServerResponse updateRole(Role role);

	/**
	 * 根据角色id删除角色
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	ServerResponse deleteRole(Integer roleId);

	/**
	 * 配置角色权限
	 * @param json
	 * @return
	 */
	ServerResponse setRolePermission(JSONObject json);

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	ServerResponse saveRole(Role role);

}
