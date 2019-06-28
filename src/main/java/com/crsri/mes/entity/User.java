package com.crsri.mes.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel("用户对象模型")
public class User {
	
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
    private Integer id;

	/**
	 * 用户钉钉唯一身份标识
	 */
	@ApiModelProperty("用户在钉钉中的唯一身份标识")
    private String userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
    private String username;

	/**
	 * 用户所在部门id
	 */
	@ApiModelProperty("用户所在部门id")
    private Long deptId;

	/**
	 * 用户钉钉绑定的手机号
	 */
	@ApiModelProperty("用户钉钉绑定的手机号")
    private String phone;

	/**
	 * 用户密码
	 */
	@ApiModelProperty("用户密码")
    private String password;

	/**
	 * 记录创建时间
	 */
    private Date createTime;

    /**
     * 记录修改时间
     */
    private Date updateTime;
  
}