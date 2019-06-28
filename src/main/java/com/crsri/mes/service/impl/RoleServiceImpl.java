package com.crsri.mes.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.RoleMapper;
import com.crsri.mes.dao.RolePermissionMapper;
import com.crsri.mes.dao.UserRoleMapper;
import com.crsri.mes.entity.Role;
import com.crsri.mes.entity.RolePermission;
import com.crsri.mes.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private UserRoleMapper userRoleMapper;
	
	@Resource
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public ServerResponse getRoleList() {
		List<Role> res = roleMapper.getRoleList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse updateRole(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
		return ServerResponse.createBySuccessMessage("更新角色信息成功！");
	}

	@Override
	public ServerResponse deleteRole(Integer roleId) {
		//删除用户-角色信息
		userRoleMapper.deleteByRoleId(roleId);
		//删除角色-权限信息
		rolePermissionMapper.deleteByRoleId(roleId);
		//删除角色
		roleMapper.deleteByPrimaryKey(roleId);
		return ServerResponse.createBySuccessMessage("删除角色成功!");
	}

	@Override
	public ServerResponse setRolePermission(JSONObject json) {
		String[] permissionIds = json.getString("permissionId").split(",");
		Integer roleId = json.getInteger("roleId");
		for (String permissionId : permissionIds) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(Integer.parseInt(permissionId));
			rolePermission.setRoleId(roleId);
			rolePermissionMapper.insertSelective(rolePermission);
		}
		return ServerResponse.createBySuccessMessage("配置权限成功");
	}

	/* (non-Javadoc)
	 * @see com.crsri.mes.service.RoleService#saveRole(com.crsri.mes.entity.Role)
	 */
	@Override
	public ServerResponse saveRole(Role role) {
		roleMapper.insertSelective(role);
		return ServerResponse.createBySuccessMessage("新增角色成功!");
	}

}
