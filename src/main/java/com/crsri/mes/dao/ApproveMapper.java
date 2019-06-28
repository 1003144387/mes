package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.Approve;

public interface ApproveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Approve record);

    int insertSelective(Approve record);

    Approve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Approve record);

    int updateByPrimaryKey(Approve record);

	/**
	 * @return
	 */
	List<Approve> queryApproveList();
	
	/**
	 * 根据审批流的code值，获取审批内容
	 */
	Approve queryByCode(@Param("code")String code);
}