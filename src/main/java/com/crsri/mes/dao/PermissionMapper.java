package com.crsri.mes.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.Permission;
import com.crsri.mes.vo.MenuVO;
import com.crsri.mes.vo.PermissionVO;

public interface PermissionMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Permission record);

	int insertSelective(Permission record);

	Permission selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Permission record);

	int updateByPrimaryKey(Permission record);

	/**
	 * 根据角色id查找相应的权限
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<String> selectPermissionsByRoleIds(@Param("roleIds") Set<Integer> roleIds);

	/**
	 * 根据角色id获取权限id的集合
	 * 
	 * @param roleId
	 * @return
	 */
	List<Integer> queryMenuByRoleIds(@Param("roleIds") Set<Integer> roleIds);

	/**
	 * 根据id集合批量查询权限
	 * 
	 * @param permissionIds
	 * @return
	 */
	List<Permission> queryIds(@Param("ids") List<Integer> ids);

	/**
	 * 
	 * @param parentId      父级权限的id
	 * @param permissionIds 指定角色对应的权限id的集合
	 * @return
	 */
	List<MenuVO> queryMenuByParentId(@Param("parentId") Integer parentId, @Param("ids") List<Integer> ids);

	/**
	 * 获取所有的一级权限
	 * 
	 * @return
	 */
	List<PermissionVO> queryRootPermission();

	/**
	 * 根据父级权限的id查找子权限
	 * 
	 * @param parentId 父级权限id
	 * @return
	 */
	List<PermissionVO> queryPermissionByParentId(@Param("parentId") Integer parentId);

	/**
	 * @param id
	 * @return
	 */
	PermissionVO selectPermissionVOByPrimaryKey(@Param("id") Integer id);

	/**
	 * 获取页面权限
	 * 
	 * @param id
	 * @return
	 */
	PermissionVO selectPagePermissionVOByPrimaryKey(Integer id);

	/**
	 * 根据父级id获取子页面
	 * 
	 * @param id
	 * @return
	 */
	List<PermissionVO> queryPagePermissionByParentId(Integer id);

	/**
	 * 根据权限名获取权限
	 * 
	 * @param permissionName
	 * @return
	 */
	Permission queryByName(String permissionName);
}
