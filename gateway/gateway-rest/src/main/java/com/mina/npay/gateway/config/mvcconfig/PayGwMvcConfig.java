package com.mina.npay.gateway.config.mvcconfig;

import com.mina.npay.gateway.config.interceptor.ReqParamHandlerInterceptor;
import com.mina.npay.gateway.config.interceptor.SignAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置类
 * @author daimingdong
 */
@Configuration
public class PayGwMvcConfig implements WebMvcConfigurer {

    @Bean //将组件注册在容器中
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //加入的顺序就是拦截器执行的顺序
                registry.addInterceptor(new ReqParamHandlerInterceptor())
                        .addPathPatterns("/npay/**");
                registry.addInterceptor(new SignAuthInterceptor())
                        .addPathPatterns("/h5/pay");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                //registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
                registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            }

        };
    }

}


