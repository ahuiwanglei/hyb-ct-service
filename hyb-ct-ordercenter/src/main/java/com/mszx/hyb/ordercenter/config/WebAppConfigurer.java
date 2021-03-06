package com.mszx.hyb.ordercenter.config;

import com.mszx.hyb.common.interceptor.LogCommonInterceptor;
import com.mszx.hyb.common.interceptor.SecretValidateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Bean   //把我们的拦截器注入为bean
    public HandlerInterceptor getMyInterceptor() {
        return new LogCommonInterceptor();
    }

    @Bean
    public HandlerInterceptor getSecretValidateInterceptor(){
        return new SecretValidateInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getSecretValidateInterceptor()).addPathPatterns("/api/**");
        super.addInterceptors(registry);
    }
}
