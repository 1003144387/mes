package com.crsri.mes.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceProductProcess;

public interface ProduceProductProcessMapper {
	int deleteByPrimaryKey(String id);

	int insertSelective(ProduceProductProcess record);

	ProduceProductProcess selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProduceProductProcess record);

	/**
	 * 检验是否是重复的生产产品装配审批
	 * 
	 * @param codes 产品的id
	 * @return
	 */
	List<String> productProductApproveCheck(@Param("codes")String[] codes);

	/**
	 * 获取装配完成待装配审批的产品列表
	 * @return
	 */
	List<Map<String, Object>> queryWaittingProduceApproveList();
	
	/**
	 * 根据id获取生产产品装配流程
	 * @param ids
	 * @return
	 */
	List<ProduceProductProcess> queryByIds(@Param("ids")String[] ids);

	/**
	 * 获取待检验的产品列表
	 * @return
	 */
	List<Map<String, Object>> queryWaittingInspectionApproveList();

	/**
	 * 检验是否是重复的生产产品检验审批
	 * 
	 * @param codes 产品的id
	 * @return
	 */
	List<String> productInspectionApproveCheck(@Param("codes")String[] codes);

	/**
	 * 获取待入库的产品列表
	 * @return
	 */
	List<Map<String, Object>> queryWaittingStockInApproveList();

	/**
	 * 获取待出库的产品列表
	 * @return
	 */
	List<Map<String, Object>> queryWaittingStockOutApproveList();

	/**
	 * 检查是否是重复提交的入库审批
	 * @param codes
	 * @return
	 */
	List<String> goodsStockInCheck(@Param("codes")String[] codes);

	/**
	 * 检查是否是重复提交的出库审批
	 * @param codes
	 * @return
	 */
	List<String> goodsStockOutCheck(@Param("codes")String[] codes);

	/**
	 * 获取产品库存
	 * @return
	 */
	List<Map<String, Integer>> getProductStock();

	/**
	 * 获取生产产品库存的产品种类
	 * @return
	 */
	int getCountGroupByProductId();


}