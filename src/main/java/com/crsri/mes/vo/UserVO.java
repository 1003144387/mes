package com.crsri.mes.vo;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户的VO对象
 * 
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class UserVO {

	private Integer id;

	private String userId;

	private String username;

	private String phone;

	private String password;

	private Date createTime;

	private Set<String> roleName;
}
