package com.egovchina.partybuilding.partybuild.config;

import com.egovchina.partybuilding.common.config.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mvc配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(redisTemplate)).excludePathPatterns(getExcludePath()).order(1);
//        registry.addInterceptor(new PermissionInterceptor()).excludePathPatterns(getExcludePath()).order(2);
    }

    /**
     * 不需要拦截的地址
     * @return regions
     */
    private List<String> getExcludePath() {
        List<String> excludeList = new ArrayList<>();
        excludeList.add("/error");
        excludeList.add("/actuator/**");
        excludeList.add("/v2/api-docs/**");
        return excludeList;
    }

    private CorsConfiguration addCorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource uccs = new UrlBasedCorsConfigurationSource();
        uccs.registerCorsConfiguration("/**", addCorsConfig());
        return new CorsFilter(uccs);
    }
}
