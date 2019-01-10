package com.yaouguoji.platform;

import com.yaouguoji.platform.common.ShopInterceptor;
import com.yaouguoji.platform.common.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/12
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private ShopInterceptor shopInterceptor;

    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(shopInterceptor).addPathPatterns("/shop/**")
                .excludePathPatterns("/shop/isLogin")
                .excludePathPatterns("/shop/verifyCode")
                .excludePathPatterns("/shop/login");
        registry.addInterceptor(userInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns("/user/isLogin")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/verifyCode");
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
