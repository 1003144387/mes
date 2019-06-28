package com.crsri.mes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @ClassName:  MesApplication   
 * @Description: 启动类  
 * @author: 2011102394 
 * @date:   2018年12月16日 下午4:40:06   
 *
 */
@SpringBootApplication
@MapperScan("com.crsri.mes.dao")
@EnableSwagger2
public class MesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesApplication.class, args);
	}

}

