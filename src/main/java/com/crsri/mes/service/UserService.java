package com.crsri.mes.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.User;

public interface UserService {

	/**
	 * 
	 * @Title: selectUserById   
	 * @Description: 根据id查找User   
	 * @param: @param id
	 * @param: @return      
	 * @return: ServerResponse<User>      
	 * @throws
	 */
	ServerResponse<User> selectUserById(Integer id);

	/**
	 * 
	 * @Title: selectUserAll   
	 * @Description: 获取全部用户   
	 * @param: @return      
	 * @return: ServerResponse      
	 * @throws
	 */
	ServerResponse selectUserAll();
	
	
	/**
	 *  根据用户id获取用户的角色和权限信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> selectRolesAndPermissions(String userId);

	/**
	 * 根据用户id删除用户账户
	 * @param id
	 * @return
	 */
	ServerResponse deleteAccount(Integer id);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	ServerResponse updateAccount(User user);

	/**
	 * 设置用户角色
	 * @param json
	 * @return
	 */
	ServerResponse setRole(JSONObject json);

}
