package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceComponentProduceApprove;

public interface ProduceComponentProduceApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceComponentProduceApprove record);

    int insertSelective(ProduceComponentProduceApprove record);

    ProduceComponentProduceApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceComponentProduceApprove record);

    int updateByPrimaryKey(ProduceComponentProduceApprove record);

    /**
	 * 获取指定时间内物品分类的名称
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime, @Param("stopTime")Date stopTime);

	/**
	 * 按条件获取组件装配审批记录
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getComponentProduceReport(
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}