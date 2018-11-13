package com.fmkj.chat.server.utils;

/**
 * @Author: youxun
 * @Date: 2018/8/29 12:59
 * @Description:Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHandler implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHandler.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (SpringContextHandler.applicationContext == null) {
            throw new RuntimeException("race-applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

}