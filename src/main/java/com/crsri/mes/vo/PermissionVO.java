package com.crsri.mes.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class PermissionVO {

	private Integer id;

    private String permissionName;

    private String permissionDesc;

    private Integer permissionParentId;

    private String permissionUrl;

    private String permissionIcon;

    private Integer permissionOrder;

    private Integer permissionType;

    private Date createTime;

    private Date updateTime;
    
    private List<PermissionVO> children;
}
