package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProducePartsSoftInstallApprove;

public interface ProducePartsSoftInstallApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProducePartsSoftInstallApprove record);

    int insertSelective(ProducePartsSoftInstallApprove record);

    ProducePartsSoftInstallApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProducePartsSoftInstallApprove record);

    int updateByPrimaryKey(ProducePartsSoftInstallApprove record);

    /**
     * 按条件获取部件软件灌装和设备校准的审批记录
     * @param startTime
     * @param stopTime
     * @param operator
     * @param type
     * @return
     */
    List<Map<String, Object>> getPartsSoftInstallReport(@Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);
}