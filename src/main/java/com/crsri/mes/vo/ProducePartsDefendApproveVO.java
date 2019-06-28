package com.crsri.mes.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/**
 * 
 * @author 2011102394
 *
 */
@ApiModel("生产部件三防审批的数据封装对象")
public class ProducePartsDefendApproveVO {

	/**
	 * 审批id
	 */
	@ApiModelProperty("三防审批id")
	private String id;

	/**
	 * 三防部件的二维码合集
	 */
	@ApiModelProperty("三防的部件二维码合集")
	private String codes;

	/**
	 * 三防的物品类型
	 */
	@ApiModelProperty("物品类型")
	private String typeName;

	/**
	 * 三防的部件数量
	 */
	@ApiModelProperty("三防的部件数量")
	private Integer number;

	/**
	 * 三防的操作人员
	 */
	@ApiModelProperty("三防的操作人员")
	private String operator;

	/**
	 * 相关图片
	 */
	@ApiModelProperty("相关图片")
	private String image;

	/**
	 * 备注信息
	 */
	@ApiModelProperty("备注信息")
	private String remark;

	/**
	 * 审批状态
	 */
	@ApiModelProperty("审批状态")
	private Integer approveStatus;

	/**
	 * 审批结果
	 */
	@ApiModelProperty()
	private Integer approveResult;
	
	private Long deptId;
	
	private String userId;
}











