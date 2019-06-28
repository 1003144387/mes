package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;

/**
 * 操作页面菜单的service接口
 * @author 2011102394
 *
 */
public interface MenuService {

	/**
	 * 根据用户is获取展示的菜单
	 * @param userId
	 * @return
	 */
	ServerResponse getMenu(String userId);

	/**
	 * 获取所有的页面权限列表
	 * @return
	 */
	ServerResponse getPageALl();

}
