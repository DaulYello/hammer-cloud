package com.fmkj.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.gateway.api.HcPermissApi;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * @Auther: youxun
 * @Date: 2018/8/24 21:03
 * @Description:  1、限流拦截器、使用令牌桶算法，限流优先级最高
 *                2、权限拦截
 *                3、其他拦截
 *
 */

@Component
public class AccessTokenFilter extends ZuulFilter{

    // 每秒钟放置100个令牌
    private  static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Autowired
    private HcPermissApi hcPermissApi;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run(){

        try {
            // 1、限流拦截器、使用令牌桶算法，限流优先级最高
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            String servletPath = request.getServletPath();
            //过滤掉登陆方法
            if(servletPath.contains("loginByTelephone") || servletPath.contains("loginByRcodeAndPhone")
                    || servletPath.contains("loginByPassword") || servletPath.contains("sendDycode"))
                return null;
            // 没有拿到令牌处理方法
            if(!RATE_LIMITER.tryAcquire()){
                return new BaseResult<Boolean>(BaseResultEnum.NOACCESS, false);
            }
            RequestContext context = getCurrentContext();
            String token = request.getHeader("token");
            boolean isPass = hcPermissApi.queryToken(token);
            if(!isPass){
                context.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
                BaseResult<Boolean> result = new BaseResult<Boolean>(BaseResultEnum.TOKEN_INVALID, isPass);
                context.setResponseBody(JSON.toJSONString(result));// 返回错误内容
                HttpServletResponse response = context.getResponse();
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.setLocale(new java.util.Locale("zh","CN"));
            }
        } catch (Exception e) {
            rethrowRuntimeException(e);
        }
        return null;
    }
}
