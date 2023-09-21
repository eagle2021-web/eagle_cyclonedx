package com.eagle.mysql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        System.out.println(request.getRequestedSessionId());
                        System.out.println(request.getSession().getId());
                        System.out.println("1111111111111111");
                        return HandlerInterceptor.super.preHandle(request, response, handler);
                    }

                    @Override
                    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                                Exception ex) throws Exception {
                        System.out.println(request.getRequestedSessionId());
                        System.out.println(request.getSession().getId());
                        System.out.println("1111111111111111");
                        // 在视图渲染完成后进行拦截逻辑的处理
                    }
                })
                .addPathPatterns("/**");
    }
}