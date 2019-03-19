package com.yizheng.partybuilding.config;

import com.yizheng.commons.util.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用feign进行服务调用时，需要拦截请求，添加token到header中
 *
 * @Author zhuyu
 */
@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (null == getHttpServletRequest()) {
            return;
        }
        //将token向下面的服务传递
        requestTemplate.header(JwtUtil.HEADER_AUTH, getHttpServletRequest().getHeader(JwtUtil.HEADER_AUTH));
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

}
