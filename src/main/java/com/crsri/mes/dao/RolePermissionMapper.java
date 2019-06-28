package com.crsri.mes.dao;

import java.util.List;

import com.crsri.mes.entity.RolePermission;

public interface RolePermissionMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(RolePermission record);

	int insertSelective(RolePermission record);

	RolePermission selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RolePermission record);

	int updateByPrimaryKey(RolePermission record);

	/**
	 * 根据角色id删除记录
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);

	/**
	 * 根据权限id获取角色id
	 * @return
	 */
	List<Integer> queryRoleIdByPermissionId(Integer permissionId);
}