package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProducePartsDefendApprove;

public interface ProducePartsDefendApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProducePartsDefendApprove record);

    int insertSelective(ProducePartsDefendApprove record);

    ProducePartsDefendApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProducePartsDefendApprove record);

    int updateByPrimaryKey(ProducePartsDefendApprove record);

    /**
	 * 获取指定时间内物品分类的名称
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime, @Param("stopTime")Date stopTime);

	/**
	 * 获取三防刷漆审批记录
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getPartsDefendReport(@Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}