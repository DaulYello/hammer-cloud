package com.fmkj.moon.server.aspectj;

import com.alibaba.fastjson.JSON;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.ServletUtils;
import com.fmkj.moon.dao.domain.MoonOperateLog;
import com.fmkj.moon.server.annotation.MoonLog;
import com.fmkj.moon.server.async.AsyncFactory;
import com.fmkj.moon.server.async.AsyncManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author youxun
 */
@Aspect
@Component
@EnableAsync
public class OrderLogAspect {

    private static final Logger log = LoggerFactory.getLogger(OrderLogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.fmkj.moon.server.annotation.MoonLog)")
    public void logPointCut() {

    }

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null, LogConstant.OPERATE_SUCCESS);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, LogConstant.OPERATE_FAIL);
    }

    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e, int operateStatus) {
        try {
            // 获得注解
            MoonLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            MoonOperateLog operateLog = new MoonOperateLog();
            // 设置方法名称
            operateLog.setCreateTime(new Date());
            if(e != null){
                operateLog.setExceptionMsg(e.getMessage());
            }
            //operateLog.setUserId();
            operateLog.setModule(controllerLog.module());
            operateLog.setOperateStatus(operateStatus);
            operateLog.setOperateDesc(controllerLog.actionDesc());
            operateLog.setRequestHost(ServletUtils.getRequest().getRemoteHost());
            operateLog.setRequestMethod(ServletUtils.getRequest().getRequestURI());
            operateLog.setRequestClass(joinPoint.getTarget().getClass().getName());
            Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
            operateLog.setRequestparams(JSON.toJSONString(map));

            //异步保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operateLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private MoonLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(MoonLog.class);
        }
        return null;
    }
}
