package com.crsri.mes.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 菜单的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class MenuVO {

	private String title;
	
	private String iconType;
	
	private String key;
	
	private List<MenuVO> children;
}
