package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User selectUserByPhone(@Param("phone") String phone);

	List<User> selectUserAll();

	User selectUserByUserId(@Param("userId") String userId);

	/**
	 * 通过用户名查找用户信息
	 * @param username
	 * @return
	 */
	User queryUserByName(@Param("username")String username);

}