package com.jp.ht.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jp.ht.interceptors.LoginInterceptors;
@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Autowired
	private LoginInterceptors loginInterceptors;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//登录接口和注册接口不拦截
		registry.addInterceptor(loginInterceptors).excludePathPatterns("/user/login","/user/register");
	}

}
