package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.PermissionMapper;
import com.crsri.mes.dao.RoleMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.dao.UserRoleMapper;
import com.crsri.mes.entity.Permission;
import com.crsri.mes.service.MenuService;
import com.crsri.mes.vo.MenuVO;
import com.crsri.mes.vo.PermissionVO;

@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public ServerResponse getMenu(String userId) {
		// 通过userId 查找对应的角色Set<RoleId>
		Set<Integer> roleIds = userRoleMapper.selectRoleIdsByUserId(userId);
		// TODO 这里默认进入系统，给与游客的身份与最基本的权限，所以不考虑用户角色不存在的情况
		Set<String> roleSet = roleMapper.selectRolesById(roleIds);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 通过角色id查找所有的页面菜单信息
		List<MenuVO> list = getMenuList(roleIds);
		return ServerResponse.createBySuccess(list);
	}

	/**
	 * 根据角色id查找菜单按钮列表
	 * @param roleIds
	 * @return
	 */
	private List<MenuVO> getMenuList(Set<Integer> roleIds) {
		List<MenuVO> res = new ArrayList<>();
		//根据角色id获取页面菜单按钮权限id的集合
		List<Integer> permissionIds = permissionMapper.queryMenuByRoleIds(roleIds);
		//获取这些权限中的一级权限
		List<Permission> permissions = permissionMapper.queryIds(permissionIds);
		for (Permission permission : permissions) {
			MenuVO menuVO = new MenuVO();
			menuVO.setTitle(permission.getPermissionDesc());
			menuVO.setIconType(permission.getPermissionIcon());
			menuVO.setKey(permission.getPermissionUrl());
			List<MenuVO> menuVOs = permissionMapper.queryMenuByParentId(permission.getId(),permissionIds);
			if(menuVOs.size()>0) {
				menuVO.setChildren(menuVOs);
			}else {
				menuVO.setChildren(null);
			}
			
			res.add(menuVO);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see com.crsri.mes.service.MenuService#getPageALl()
	 */
	@Override
	public ServerResponse getPageALl() {
		List<PermissionVO> permissionList = new ArrayList<>();
		findChildPermission(permissionList, 0);
		return ServerResponse.createBySuccess(permissionList);
	}

	
	/**
	 * 递归查询所有子权限
	 * 
	 * @param permissionList
	 * @param id
	 */
	private List<PermissionVO> findChildPermission(List<PermissionVO> permissionList, Integer id) {
		PermissionVO permission = permissionMapper.selectPagePermissionVOByPrimaryKey(id);
		List<PermissionVO> childPermisssion = permissionMapper.queryPagePermissionByParentId(id);
		if (permission != null) {
			if(CollectionUtils.isNotEmpty(childPermisssion)) {
				permission.setChildren(childPermisssion);
				permissionList.add(permission);
			}
		}
		for (PermissionVO permissionVO : childPermisssion) {
			findChildPermission(permissionList, permissionVO.getId());
		}
		return permissionList;
	}
	
}
