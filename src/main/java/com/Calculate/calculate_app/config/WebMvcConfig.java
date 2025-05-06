package com.Calculate.calculate_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//设置默认访问路径
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 根路径重定向到登录页面（GET请求）
        registry.addViewController("/").setViewName("login");
        // 替代redirect，直接返回视图（需配合视图解析器）
    }
}