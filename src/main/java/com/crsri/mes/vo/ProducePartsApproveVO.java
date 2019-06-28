package com.crsri.mes.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部件来料检验审批的VO对象
 * 
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProducePartsApproveVO {

	private String id;

	private String partsId;

	private String partsName;

	private String partsCodes;

	private String operator;
	
	private String userId;
	
	private Long deptId;

	private Integer partsStatus;

	private Integer specification;

	private Integer number;

	private String image;

	private String remark;


}
