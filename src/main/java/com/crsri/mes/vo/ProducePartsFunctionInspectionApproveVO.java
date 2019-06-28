package com.crsri.mes.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产部件功能检测审批的VO对象
 * @author 2011102394
 *
 */

@Getter
@Setter
@ToString
public class ProducePartsFunctionInspectionApproveVO {

	private String id;

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
