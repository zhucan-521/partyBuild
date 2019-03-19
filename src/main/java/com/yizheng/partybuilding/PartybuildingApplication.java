package com.yizheng.partybuilding;

import com.yizheng.commons.config.CommonExceptionHandler;
import com.yizheng.commons.config.ResponseBodyWrapFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@MapperScan({"com.yizheng.partybuilding.repository", "com.yizheng.partybuilding.system.mapper"})
@ComponentScan(basePackages = "com.yizheng")
@EnableFeignClients
@SpringBootApplication
public class PartybuildingApplication {

	public static void main(String[] args) {

		SpringApplication.run(PartybuildingApplication.class, args);
	}

	/**
	 * 注入公用commons项目，改写统一公用返回
	 * @return
	 */
//	@Bean
//	public ResponseBodyWrapFactoryBean getResponseBodyWrap(){
//		return new ResponseBodyWrapFactoryBean();
//	}


	/**
	 * 注入公用commons项目，捕获全局异常，统一返回错误信息
	 * @return
	 */
	@ControllerAdvice
	public class CommonException extends CommonExceptionHandler {

	}
}
