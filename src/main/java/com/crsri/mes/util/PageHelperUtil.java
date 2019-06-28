package com.crsri.mes.util;

import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 分页的工具类
 * @author 2011102394
 *
 */
public class PageHelperUtil {

	/**
	 * 获取分页信息
	 * @param page
	 * @return
	 */
	public static Map<String, Object> getPageInfo(Page page) {
		long total = page.getTotal();
		int current = page.getPageNum();
		int pageSize = page.getPageSize();
		int endRow = page.getEndRow();
		int startRow = page.getStartRow();
		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("total", total);
		pageInfo.put("current", current);
		pageInfo.put("pageSize", pageSize);
		pageInfo.put("startRow", startRow);
		pageInfo.put("endRow", endRow);
		return pageInfo;
	}
}
