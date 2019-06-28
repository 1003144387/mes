package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProducePartsInspectionApprove;

public interface ProducePartsInspectionApproveMapper {
	int deleteByPrimaryKey(String id);

	int insert(ProducePartsInspectionApprove record);

	int insertSelective(ProducePartsInspectionApprove record);

	ProducePartsInspectionApprove selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProducePartsInspectionApprove record);

	int updateByPrimaryKey(ProducePartsInspectionApprove record);

	/**
	 * 数据导入时插入的工具类
	 * 
	 * @param producePartsInspectionApprove
	 * @return
	 */
	int insertSelectiveBackUp(ProducePartsInspectionApprove producePartsInspectionApprove);

	/**
	 * 获取指定条件的来料检验的数量
	 * @param partsName 部件分类名称
	 * @param operator 操作人员
	 * @param processInstanceType 审批结果
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @return
	 */
	int getPartsInspectionCountByProcessStatus(@Param("partsName") String partsName,
            @Param("operator") String operator,
            @Param("processInstanceType") Integer processInstanceType,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

	
	/**
	 * 获取指定条件下的来料检验记录
	 * @param partsName 部件名
	 * @param processInstanceType 审批状态
	 * @param processInstanceResult 审批结果
	 * @param operator 检验人员
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @return
	 */
	List<ProducePartsInspectionApprove> getPartInspectionList(@Param("partsName") String partsName,
            @Param("processInstanceType") Integer processInstanceType,
            @Param("processInstanceResult") Integer processInstanceResult,
            @Param("operator") String operator,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

	/**
	 * 获取指定时间段内生产部件检验审批的数量
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	Integer getCount(@Param("startTime")Date startTime,@Param("stopTime") Date stopTime);

	/**
	 * 获取指定时间内 审批物品的分类名
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	List<String> getCategoryTypes(@Param("startTime")Date startTime,@Param("stopTime") Date stopTime);

	/**
	 * 获取指定条件下的来料检验记录
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getPartsInspectionReport(@Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type")String type);


}