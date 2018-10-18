package com.fmkj.race.server.aspectj;

import com.alibaba.fastjson.JSON;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.ServletUtils;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.RaceOperateLog;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.server.async.RaceAsyncFactory;
import com.fmkj.race.server.async.RaceAsyncManager;
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
public class RaceLogAspect {

    private static final Logger log = LoggerFactory.getLogger(RaceLogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.fmkj.race.server.annotation.RaceLog)")
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
            RaceLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            RaceOperateLog operateLog = new RaceOperateLog();
            // 设置方法名称
            operateLog.setCreateTime(new Date());
            if(e != null){
                operateLog.setExceptionMsg(e.getMessage());
            }
            String userId = ServletUtils.getRequest().getHeader("globalUserId");
            if(StringUtils.isNull(userId)){
                operateLog.setUserId(Integer.parseInt(userId));
            }
            operateLog.setModule(controllerLog.module());
            operateLog.setOperateStatus(operateStatus);
            operateLog.setOperateDesc(controllerLog.actionDesc());
            operateLog.setRequestHost(ServletUtils.getRequest().getRemoteHost());
            operateLog.setRequestMethod(ServletUtils.getRequest().getRequestURI());
            operateLog.setRequestClass(joinPoint.getTarget().getClass().getName());
            Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
            operateLog.setRequestparams(JSON.toJSONString(map));

            //异步保存数据库
            RaceAsyncManager.me().execute(RaceAsyncFactory.recordOper(operateLog));
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
    private RaceLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(RaceLog.class);
        }
        return null;
    }
}
