package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sound.midi.VoiceStatus;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.PermissionMapper;
import com.crsri.mes.dao.RoleMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.dao.UserRoleMapper;
import com.crsri.mes.entity.User;
import com.crsri.mes.entity.UserRole;
import com.crsri.mes.service.UserService;
import com.crsri.mes.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public ServerResponse<User> selectUserById(Integer id) {
		return ServerResponse.createBySuccess(userMapper.selectByPrimaryKey(id));
	}

	@Override
	public ServerResponse selectUserAll() {
		List<User> users = userMapper.selectUserAll();
		List<UserVO> userVOs = new ArrayList<>();
		for (User user : users) {
			Set<Integer> roleIds = userRoleMapper.selectRoleIdsByUserId(user.getUserId());
			UserVO vo = new UserVO();
			BeanUtils.copyProperties(user, vo);
			Set<String> roleNames = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(roleIds)) {
				roleNames = roleMapper.selectRoleNamesById(roleIds);
			}
			vo.setRoleName(roleNames);
			userVOs.add(vo);
		}

		return ServerResponse.createBySuccess(userVOs);
	}

	@Override
	public Map<String, Object> selectRolesAndPermissions(String userId) {
		// 通过userId 查找对应的角色Set<RoleId>
		Set<Integer> roleIds = userRoleMapper.selectRoleIdsByUserId(userId);
		// TODO 这里默认进入系统，给与游客的身份与最基本的权限，所以不考虑用户角色不存在的情况
		Set<String> roleSet = roleMapper.selectRolesById(roleIds);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 通过角色id查找所有的权限信息
		Set<String> stringPermissions = permissionMapper.selectPermissionsByRoleIds(roleIds);
		Map<String, Object> res = new HashMap<>();
		res.put("roles", roleSet);
		res.put("permissions", stringPermissions);
		return res;
	}

	@Override
	public ServerResponse deleteAccount(Integer id) {
		// 根据用户id获取角色id
		User user = userMapper.selectByPrimaryKey(id);
		// 删除用户角色信息
		userRoleMapper.deleteByUserId(user.getUserId());
		userMapper.deleteByPrimaryKey(id);
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse updateAccount(User user) {
		userMapper.updateByPrimaryKeySelective(user);
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse setRole(JSONObject json) {
		String[] roldIds = json.getString("roleId").split(",");
		String userId = json.getString("userId");
		// 根据用户id删除用户角色关系表中的记录
		userRoleMapper.deleteByUserId(userId);
		for (String roleId : roldIds) {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(Integer.parseInt(roleId));
			userRoleMapper.insertSelective(userRole);
		}
		return ServerResponse.createBySuccess();
	}

}
