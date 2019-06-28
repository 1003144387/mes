package com.crsri.mes.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	Set<String> selectRolesById(@Param("roleIds") Set<Integer> roleIds);
	
	
	Set<String> selectRoleNamesById(@Param("roleIds") Set<Integer> roleIds);

	/**
	 * 获取角色列表
	 * @return
	 */
	List<Role> getRoleList();
}