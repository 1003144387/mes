package com.crsri.mes.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProduceComponentProcess;

public interface ProduceComponentProcessMapper {
	int deleteByPrimaryKey(String id);

	int insert(ProduceComponentProcess record);

	int insertSelective(ProduceComponentProcess record);

	ProduceComponentProcess selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProduceComponentProcess record);

	int updateByPrimaryKey(ProduceComponentProcess record);

	/**
	 * 检查是否有重复提交的装配审批
	 * 
	 * @param codes
	 * @return
	 */
	List<String> componentProduceApproveCheck(@Param("codes") String[] codes);

	/**
	 * 根据id批量查询生产组件流程记录
	 * 
	 * @param ids
	 * @return
	 */
	List<ProduceComponentProcess> queryByIds(@Param("ids") String[] ids);

	/**
	 * 获取装配结束未经过组件装配审批的组件列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryComponentsUnProduceApprove();

	/**
	 * 获取装配审批结束未经过组件检验审批的组件列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryComponentsProduceUnInspection();

	/**
	 * 检查是否有重复提交的组件检验审批
	 * 
	 * @param codes
	 * @return
	 */
	List<String> componentInspectionApproveCheck(@Param("codes") String[] codes);

	/**
	 * 获取待入库的生产组件列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryComponentsAfterInspection();

	/**
	 * 检查是否有重复入库的审批
	 * 
	 * @param codes
	 * @return
	 */
	List<String> goodsStockInCheck(@Param("codes") String[] codes);

	/**
	 * 检查是否有重复出库的审批
	 * 
	 * @param codes
	 * @return
	 */
	List<String> goodsStockOutCheck(@Param("codes") String[] codes);

	/**
	 * 获取待出库的生产组件列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryComponentsInStock();

	/**
	 * 获取检验合格未被使用的组件列表
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryComponentsAfterInspectionUnused();

	/**
	 * 获取组件库存
	 * 
	 * @return
	 */
	List<Map<String, Integer>> getComponentStock();

	/**
	 * 获取组件在库存中的种类数量
	 * 
	 * @return
	 */
	int getCountGroupByComponentId();

	/**
	 * 
	 * 维修合格的组件重新流转
	 * 
	 * @param id
	 */
	int updateAfterRepair(ProduceComponentProcess record);
}