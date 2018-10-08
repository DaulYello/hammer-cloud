package com.fmkj.user.server.annotation;


import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLog {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 功能描述
     */
    String actionDesc() default "";


}
