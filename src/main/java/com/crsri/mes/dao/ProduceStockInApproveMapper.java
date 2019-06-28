package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceStockInApprove;

public interface ProduceStockInApproveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProduceStockInApprove record);

    int insertSelective(ProduceStockInApprove record);

    ProduceStockInApprove selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProduceStockInApprove record);

    int updateByPrimaryKey(ProduceStockInApprove record);

	int insertSelectiveBackUp(ProduceStockInApprove item);

	/**
	 * 按条件查询入库历史
	 * @param goodsType 物品类型
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @param name 物品名称
	 * @param status 审批状态
	 * @return
	 */
	List<ProduceStockInApprove> getStockInHistory(@Param("goodsType") Integer goodsType,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("name")String name,
            @Param("status")Integer status);

	/**
	 * 获取指定时间内指定类别物品的分类名
	 * @param goodsType 0 部件 1组件 2产品
	 * @param startTime 开始时间
	 * @param stopTime 结束时间
	 * @return
	 */
	List<String> getCategoryTypes(int goodsType, Date startTime, Date stopTime);

	/**
	 * 按条件查询物品入库记录
	 * @param goodsType
	 * @param startTime
	 * @param stopTime
	 * @param operator
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getStockInReport(
            @Param("goodsType") Integer goodsType,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("operator") String operator,
            @Param("type") String type);

}












