package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceProductProcess {
    private String id;

    private String productId;

    private String productName;

    private String partsCode;

    private String componentCode;

    private String produceOperator;

    private Date produceTime;

    private String produceImage;

    private String produceRemark;

    private String produceApproveId;

    private Date produceApproveStartTime;

    private String produceApproveOperator;

    private Integer produceApproveStatus;

    private Integer produceApproveResult;

    private Date produceApproveStopTime;

    private Integer inspectionStatus;

    private String inspectionOperator;

    private String inspectionImage;

    private String inspectionRemark;

    private Date inspectionTime;

    private String inspectionApproveId;

    private Date inspectionApproveStartTime;

    private Integer inspectionApproveStatus;

    private Integer inspectionApproveResult;

    private Date inspectionApproveStopTime;

    private Integer stockStatus;

    private String stockPosition;

    private String stockInApproveId;

    private Integer stockInApproveStatus;

    private Integer stockInApproveResult;

    private String stockInApproveOperator;

    private Date stockInApproveStartTime;

    private Date stockInApproveStopTime;

    private String stockOutApproveId;

    private Integer stockOutApproveStatus;

    private Date stockOutApproveStartTime;

    private Integer stockOutApproveResult;

    private String stockOutApproveOperator;

    private Date stockOutApproveStopTime;

    private Integer stockOutApproveType;

    private Date createTime;

    private Date updateTime;

}