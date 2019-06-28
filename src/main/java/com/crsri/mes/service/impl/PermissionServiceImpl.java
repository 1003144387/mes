package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.apache.catalina.startup.RealmRuleSet;
import org.apache.commons.collections.CollectionUtils;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.PermissionMapper;
import com.crsri.mes.dao.RolePermissionMapper;
import com.crsri.mes.dao.UserRoleMapper;
import com.crsri.mes.entity.Permission;
import com.crsri.mes.service.PermissionService;
import com.crsri.mes.vo.PermissionVO;

/**
 * 
 * @author 2011102394
 *
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;
	
	@Resource
	private RolePermissionMapper rolePermissionMapper;
	
	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public ServerResponse queryPermissionList() {
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
		PermissionVO permission = permissionMapper.selectPermissionVOByPrimaryKey(id);
		List<PermissionVO> childPermisssion = permissionMapper.queryPermissionByParentId(id);
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


	@Override
	public List<String> queryUserIdHadPermission(String permissionName) {
		List<String> res = new ArrayList<>();
		Permission permission = permissionMapper.queryByName(permissionName);
		if(permission==null) {
			return res;
		}
		List<Integer> roleIds = rolePermissionMapper.queryRoleIdByPermissionId(permission.getId());
		if(CollectionUtils.isEmpty(roleIds)) {
			return res;
		}
		res = userRoleMapper.queryUserIdByRoleIds(roleIds);
		return res;
	}

}






















