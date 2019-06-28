package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.MenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户菜单操作的相关接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api/menu")
@Api(tags="侧边栏菜单操作相关的接口")
public class MenuController {

	
	@Resource
	private MenuService menuService;
	/**
	 * 根据用户id获取侧边菜单栏
	 * @param userId
	 * @return
	 */
	@GetMapping("/getMenuList/{userId}")
	@ApiOperation("根据用户id获取侧边菜单栏")
    public ServerResponse getMenu(@PathVariable(value = "userId",required = false) String userId) {
        return menuService.getMenu(userId);
    }
	
	/**
	 * 获取所有页面权限列表
	 * @return
	 */
	@GetMapping("/all")
	@ApiOperation("获取所有页面列表")
	public ServerResponse getPageAll() {
		return menuService.getPageALl();
	}
}
