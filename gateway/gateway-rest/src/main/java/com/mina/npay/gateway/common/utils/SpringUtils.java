package com.mina.npay.gateway.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring容器工具类，获取容器bean
 * @author daimingdong
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationCtx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationCtx = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass){
        return applicationCtx.getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationCtx.getBean(name, type);
    }

    public static HttpServletRequest getCurrentReq() {
        ServletRequestAttributes requestAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttrs == null) {
            return null;
        }
        return requestAttrs.getRequest();
    }

}
