package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产组件流程记录表的实体类
 * 
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentProcess {
	private String id;

	private String componentId;

	private String componentName;

	private String partsCode;

	private String produceOperator;

	private Date produceTime;

	private String produceImage;

	private String produceRemark;

	private String produceApproveId;

	private Date produceApproveStartTime;

	private Integer produceApproveStatus;

	private Integer produceApproveResult;

	private Date produceApproveStopTime;

	private Integer inspectionStatus;

	private String inspectionOperator;

	private Date inspectionTime;

	private String inspectionImage;

	private String inspectionRemark;

	private String inspectionApproveId;

	private Date inspectionApproveStartTime;

	private Integer inspectionApproveStatus;

	private Integer inspectionApproveResult;

	private Date inspectionApproveStopTime;

	private Integer stockStatus;

	private String stockPosition;

	private String stockInApproveId;

	private Date stockInApproveStartTime;

	private String stockInApproveOperator;

	private Integer stockInApproveStatus;

	private Integer stockInApproveResult;

	private Date stockInApproveStopTime;

	private String stockOutApproveId;

	private Date stockOutApproveStartTime;

	private String stockOutApproveOperator;

	private Integer stockOutApproveStatus;

	private Integer stockOutApproveResult;

	private Date stockOutApproveStopTime;

	/**
	 * 生产组件出库审批的类别 0 出库 1发货
	 */
	private Integer stockOutApproveType;

	private Integer used;

	private Date createTime;

	private Date updateTime;

}