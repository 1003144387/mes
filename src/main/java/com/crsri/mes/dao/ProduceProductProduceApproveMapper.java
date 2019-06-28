package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceProductProduceApprove;

public interface ProduceProductProduceApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceProductProduceApprove record);

    int insertSelective(ProduceProductProduceApprove record);

    ProduceProductProduceApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceProductProduceApprove record);

    int updateByPrimaryKey(ProduceProductProduceApprove record);

    /**
	 * 获取指定时间内物品分类的名称
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime, @Param("stopTime")Date stopTime);

	/**
	 * 按条件查询产品装配记录
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getProductProduceReport(@Param("startTime") Date startTime,
         @Param("stopTime") Date stopTime,
         @Param("operator") String operator,
         @Param("type") String type);
}