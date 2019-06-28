package com.crsri.mes.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

	Set<Integer> selectRoleIdsByUserId(@Param("userId") String userId);
	
	int deleteByUserId(@Param("userId") String userId);

	/**
	 * 根据角色id删除记录
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);

	/**
	 * 通过角色id获取用户id
	 * @param roleIds
	 * @return
	 */
	List<String> queryUserIdByRoleIds(@Param("roleIds")List<Integer> roleIds);
}