package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.CustomerTaskDetail;

public interface CustomerTaskDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerTaskDetail record);

    int insertSelective(CustomerTaskDetail record);

    CustomerTaskDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerTaskDetail record);

    int updateByPrimaryKey(CustomerTaskDetail record);

    /**
     * 根据售后任务id获取售后任务描述明细
     * @param id
     * @return
     */
	List<CustomerTaskDetail> queryByTaskId(@Param("taskId") String id);

	/**
	 * 根据售后任务id删除售后任务详情
	 * @param id
	 */
	int deleteByTaskId(String id);
}