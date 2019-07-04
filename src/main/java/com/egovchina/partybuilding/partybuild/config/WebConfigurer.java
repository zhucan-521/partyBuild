package com.egovchina.partybuilding.partybuild.config;

import com.egovchina.partybuilding.common.config.AuthInterceptor;
import com.egovchina.partybuilding.common.config.LogCollectionAspect;
import com.egovchina.partybuilding.common.config.PermissionInterceptor;
import com.egovchina.partybuilding.common.util.ExcludePathPatternBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

import static com.egovchina.partybuilding.common.util.ExcludePathPattern.ofPath;

/**
 * Mvc配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final ExcludePathPatternBuilder BUILDER;

    static {
        BUILDER = ExcludePathPatternBuilder.builder()
                .patterns(
                        ofPath("/error"),
                        ofPath("/actuator/**"),
                        ofPath("/v2/api-docs/**")
                );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(redisTemplate, BUILDER)).order(1);
        registry.addInterceptor(new PermissionInterceptor(BUILDER)).order(2);
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

    @Bean
    public LogCollectionAspect logCollectionAspect() {
        return new LogCollectionAspect();
    }

}
