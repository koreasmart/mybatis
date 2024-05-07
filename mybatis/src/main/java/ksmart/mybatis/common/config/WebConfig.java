package ksmart.mybatis.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart.mybatis.interceptor.CommonInterceptor;
import ksmart.mybatis.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{

	private final LoginInterceptor loginInterceptor;
	private final CommonInterceptor commonInterceptor; 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/favicon.ico");
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/")
				.excludePathPatterns("/member/addMember")
				.excludePathPatterns("/member/memberIdCheck")
				.excludePathPatterns("/member/login")
				.excludePathPatterns("/member/logout");
				
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}










