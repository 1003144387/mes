package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceComponentInspectionApprove;

public interface ProduceComponentInspectionApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceComponentInspectionApprove record);

    int insertSelective(ProduceComponentInspectionApprove record);

    ProduceComponentInspectionApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceComponentInspectionApprove record);

    int updateByPrimaryKey(ProduceComponentInspectionApprove record);

	/**
	 * 获取指定时间内物品分类的名称
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime, @Param("stopTime")Date stopTime);

	/**
	 * 按条件查询生产组件检验列表
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getComponentInspectionReport(@Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}