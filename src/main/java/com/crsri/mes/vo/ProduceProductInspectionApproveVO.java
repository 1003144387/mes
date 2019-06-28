package com.crsri.mes.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 生产产品检验的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceProductInspectionApproveVO {

	private String id;

    private String productName;

    private String codes;
    
    private String operator;

    private Integer number;

    private Integer status;

    private Integer approveStatus;

    private Integer approveResult;

    private String remark;

    private String image;

    private Long deptId;
    
    private String userId;
}
