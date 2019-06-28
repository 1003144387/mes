package com.crsri.mes.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生产组件装配审批的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentProduceApproveVO {

	private String id;
	
	private String name;

    private String codes;

    private Integer number;

    private String operator;

    private Integer approveStatus;

    private Integer approveResult;

    private String image;

    private String remark;

    private Long deptId;
    
    private String userId;
}
