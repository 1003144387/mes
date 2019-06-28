package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * 周报相关的dao
 * 
 * @author 2011102394
 *
 */
public interface ReportMapper {

	/**
	 * 获取不同的审批，在指定时间内的数量
	 * 
	 * @param tableName 表名
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @param goodsType  物品类别 0 部件 1组件 2产品
	 * @return
	 */
	int getCount(@Param("tableName") String tableName, @Param("startTime") Date startTime,
			@Param("stopTime") Date stopTime,@Param("goodsType")Integer goodsType);
	
	/**
	 * 获取指定时间段内操作人员列表
	 * @param tableName 表名
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @param goodsType  物品类别 0 部件 1组件 2产品
	 * @return
	 */
	List<String> getOperators(@Param("tableName") String tableName, @Param("startTime") Date startTime,
			@Param("stopTime") Date stopTime,@Param("goodsType")Integer goodsType);
}
