package com.egovchina.partybuilding.partybuild.config;

import com.egovchina.partybuilding.common.config.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Mvc配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(redisTemplate)).excludePathPatterns(getExcludePath()).order(1);
//        registry.addInterceptor(new PermissionInterceptor()).excludePathPatterns(getExcludePath()).order(2);
    }

    /**
     * 不需要拦截的地址
     * @return list
     */
    private List<String> getExcludePath() {
        List<String> excludeList = new ArrayList<>();
        excludeList.add("/error");
        excludeList.add("/user/login");
        excludeList.add("/account/login");
        excludeList.add("/Party/sign");
        excludeList.add("/teachers/export");
        excludeList.add("/v2/api-docs/**");
        return excludeList;
    }

    /**
     * 设置支持跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(3600);
    }
}
