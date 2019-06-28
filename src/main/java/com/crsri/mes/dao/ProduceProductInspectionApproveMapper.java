package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceProductInspectionApprove;

public interface ProduceProductInspectionApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceProductInspectionApprove record);

    int insertSelective(ProduceProductInspectionApprove record);

    ProduceProductInspectionApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceProductInspectionApprove record);

    int updateByPrimaryKey(ProduceProductInspectionApprove record);

	/**
	 * 获取指定时间内物品分类的名称
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime, @Param("stopTime")Date stopTime);

	/**
	 * 按指定条件获取产品检验审批记录
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getProductInspectionReport(
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}