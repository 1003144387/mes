package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProducePartsProcess {

	/**
	 * 部件编号（二维码）
	 */
	private String id;

	/**
	 * 部件类别id
	 */
	private String partsId;

	/**
	 * 部件名
	 */
	private String partsName;

	/**
	 * 部件的规格
	 */
	private Integer specification;

	/**
	 * 部件的检验状态
	 */
	private Integer inspectionStatus;

	/**
	 * 部件来料检验人员
	 */
	private String inspectionOperator;

	/**
	 * 部件来料检验开始时间
	 */
	private Date inspectionStartTime;

	/**
	 * 部件来料检验结束时间
	 */
	private Date inspectionStopTime;

	/**
	 * 部件来料检验的审批id
	 */
	private String inspectionApproveId;

	/**
	 * 部件来料检验的审批状态
	 */
	private Integer inspectionApproveStatus;

	/**
	 * 部件来料检验的审批结果
	 */
	private Integer inspectionApproveResult;

	/**
	 * 部件的库存状态 null 待入库 0 库存中 1 已出库
	 */
	private Integer stockStatus;

	/**
	 * 部件在库存中的位置
	 */
	private String stockPosition;

	/**
	 * 部件的入库操作人员
	 */
	private String stockInOperator;

	/**
	 * 入库审批开始时间
	 */
	private Date stockInStartTime;

	/**
	 * 入库结束时间
	 */
	private Date stockInStopTime;

	/**
	 * 入库审批的id
	 */
	private String stockInApproveId;

	/**
	 * 入库审批状态
	 */
	private Integer stockInApproveStatus;

	/**
	 * 入库审批结果
	 */
	private Integer stockInApproveResult;

	/**
	 * 出库人员
	 */
	private String stockOutOperator;

	/**
	 * 出库开始时间
	 */
	private Date stockOutStartTime;

	private Date stockOutStopTime;

	private String stockOutApproveId;

	private Integer stockOutApproveStatus;

	private Integer stockOutApproveResult;

	private String defendOperator;

	private Integer defendStatus;

	private String defendApproveId;

	private Date defendStartTime;

	private Date defendStopTime;

	private Integer defendApproveStatus;

	private Integer defendApproveResult;

	private String softInstallOperator;

	private String softInstallVersion;

	private Date softInstallStartTime;

	private Date softInstallStopTime;

	private String softInstallApproveId;

	private Integer softInstallApproveStatus;

	private Integer softInstallApproveResult;

	private String functionInspectionOperator;

	private Integer functionInspectionStatus;

	private Date functionInspectionStartTime;

	private Date functionInspectionSotpTime;

	private String functionInspectionApproveId;

	private Integer functionInspectionApproveStatus;

	private Integer functionInspectionApproveResult;

	/**
	 * 该部件是否被使用 null 未使用  0 使用中 1使用完毕
	 */
	private Integer used;
	
	private Date createTime;

	private Date updateTime;

}