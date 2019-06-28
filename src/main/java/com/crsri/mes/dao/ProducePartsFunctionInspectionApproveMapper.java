package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProducePartsFunctionInspectionApprove;

public interface ProducePartsFunctionInspectionApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProducePartsFunctionInspectionApprove record);

    int insertSelective(ProducePartsFunctionInspectionApprove record);

    ProducePartsFunctionInspectionApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProducePartsFunctionInspectionApprove record);

    int updateByPrimaryKey(ProducePartsFunctionInspectionApprove record);

    /**
     * 按条件查询部件功能检测列表
     * @param startTime
     * @param stopTime
     * @param operator
     * @param type
     * @return
     */
    List<Map<String, Object>> getPartsFactoryInspectionReport(
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}