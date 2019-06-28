package com.crsri.mes.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产组件检验审批的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentInspectionApproveVO {

	private String id;

    private String componentName;

    private String codes;

    private Integer number;

    private String operator;

    private Integer status;

    private String image;

    private String remark;

    private Integer approveStatus;

    private Integer approveResult;
    
    private Long deptId;
    
    private String userId;
}
