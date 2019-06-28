package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.ProducePartsProcess;

public interface ProducePartsProcessMapper {
	int deleteByPrimaryKey(String id);

	ProducePartsProcess selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProducePartsProcess record);

	/**
	 * 根据codes值获取生产部件的id
	 * 
	 * @param codes
	 * @return
	 */
	List<String> checkReInspection(@Param("codes") String[] codes);

	/**
	 * 批量创建生产部件的记录
	 * 
	 * @param partsProcessList
	 * @return
	 */
	int insertSelectiveBatch(@Param("list")List<ProducePartsProcess> partsProcessList);

	/**
	 * 根据来料检验审批id获取生产部件记录
	 * @param inspectionApproveId 来料检验审批id
	 * @return
	 */
	List<ProducePartsProcess> selectByInspectionApproveId(@Param("inspectionApproveId")String inspectionApproveId);

	/**
	 * 获取检验完成待入库的部件列表
	 * @return
	 */
	List<Map<String, Object>> getProducePartsWaittingStockInList();

	/**
	 * 检查部件入库审批是否重复
	 * @param codes
	 * @return
	 */
	List<String> goodsStockInCheck(@Param("codes") String[] codes);

	/**
	 * 根据id批量获取生产部件的流程记录
	 * @param codes
	 * @return
	 */
	List<ProducePartsProcess> selectByIdBatch(@Param("ids")String[] codes);

	/**
	 * 获取在库存中的生产部件的列表
	 * @return
	 */
	List<Map<String, Object>> queryProducePartsStockInList();

	/**
	 * 检查部件出库审批是否重复
	 * @param codes
	 * @return
	 */
	List<String> goodsStockOutCheck(@Param("codes") String[] codes);

	/**
	 * 检查生产部件三防审批是否重复
	 * @param codes
	 * @return
	 */
	List<String> partsDefendCheck(@Param("codes")String[] codes);

	/**
	 * 获取出库待三防处理的部件列表
	 * @return
	 */
	List<Map<String, Object>> queryStockOutWaittingDefendPartsList();

	/**
	 * 检查生产部件软件灌装和设备校准审批是否重复
	 * @param codes
	 * @return
	 */
	List<String> partsSoftInstallCheck(@Param("codes")String[] codes);

	/**
	 * 获取出库待软件灌装和设备校准的部件列表
	 * @return
	 */
	List<Map<String, Object>> queryStockOutWaittingSoftInstallPartsList();

	/**
	 * 获取出库待部件功能检测的部件列表
	 * @return
	 */
	List<Map<String, Object>> queryStockOutWaittingFunctionInspcetionPartsList();

	/**
	 * 部件功能检测审批重复提交检测
	 * @param codes
	 * @return
	 */
	List<String> functionInspectionCheck(@Param("codes")String[] codes);

	/**
	 * 获取已出库未被使用的生产部件列表
	 * @return
	 */
	List<Map<String, Object>> queryStockOutUnUsedPartsList();

	/**
	 * 数据迁移批量导入
	 * @param partsProcessList
	 * @return
	 */
	int insertSelectiveBatchImport(@Param("list")List<ProducePartsProcess> partsProcessList);

	/**
	 * 获取部件库存
	 * @return
	 */
	List<Map<String, Integer>> getPartsStock();

	/**
	 * 获取在库存中的部件的种类数量
	 * @return
	 */
	int getCountGroupByPartsId();

	/**
	 * 获取在库存中的生产部件的总数
	 * @return
	 */
	int queryProducePartsStockInNumber();

}













