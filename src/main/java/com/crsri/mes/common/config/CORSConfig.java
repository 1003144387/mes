package com.crsri.mes.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 前后端分离项目，前端联调时跨域的配置，当前端项目合并时需要注释掉该类，否则会导致前端路由失效，页面无法正常跳转
 * 
 * @author 2011102394
 *
 */
//@Configuration
public class CORSConfig extends WebMvcConfigurationSupport {

	/**
	 * 跨域的相关设置
	 */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	/**
	 * 该配置类生效时，application.yml中配置的静态资源会失效，所以需要重新配置
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/",
				"classpath:/resources/", "classpath:/static/", "classpath:/public/", "file:D:/MES/data/");
	}

}
