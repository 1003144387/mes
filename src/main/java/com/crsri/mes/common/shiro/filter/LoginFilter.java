package com.crsri.mes.common.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 
 * @author 2011102394
 *
 */
public class LoginFilter extends FormAuthenticationFilter {

	/**
	 * 在访问controller前判断是否登录，返回json，不进行重定向。
	 * 
	 * @param request
	 * @param response
	 * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		if (isAjax(request)) {
//			httpServletResponse.setCharacterEncoding("UTF-8");
//			httpServletResponse.setContentType("application/json");
//			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		} else {
//			// saveRequestAndRedirectToLogin(request, response);
//			httpServletResponse.sendRedirect("/notlogin");
//		}
		return false;
	}

	private boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
