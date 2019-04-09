package com.example.interceptor.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截规则
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new MyHandlerInterceptorAdapter());
        // 排除的路径
        registration.excludePathPatterns("/hello");
        registration.excludePathPatterns("/get/{id}");
        // 被拦截的路径，可不配置
        //registration.addPathPatterns("/**");
    }
}
