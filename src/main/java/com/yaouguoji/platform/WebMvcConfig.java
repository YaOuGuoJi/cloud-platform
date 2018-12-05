package com.yaouguoji.platform;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yaouguoji.platform.common.ShopInterceptor;
import org.springframework.context.annotation.Bean;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(shopInterceptor).addPathPatterns("/shop/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 阿里云短信客户端
     *
     * @return
     */
    @Bean
    public IAcsClient acsClient() {
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        final String accessKeyId = "LTAIwk1N65FkASo4";
        final String accessKeySecret = "pUF11Qq9fKPWfeLB5raoagRyKrmrR0";
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        return new DefaultAcsClient(profile);
    }

}
