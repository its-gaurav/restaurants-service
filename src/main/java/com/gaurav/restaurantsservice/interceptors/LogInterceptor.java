package com.gaurav.restaurantsservice.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRemoteAddr()+" accessed resource "+request.getRequestURI()+" ("+request.getMethod()+")"+" @ "+getCurrentTime());
        log.trace("log.trace");
        log.warn("log.warn");
        log.error("log.error");
        log.debug("log.debug");

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        response.setDateHeader("endTime",startTime);
        // cache-control used for browser-caching
        response.setHeader(HttpHeaders.CACHE_CONTROL,
                CacheControl.noCache()
                        .cachePublic()
                        .mustRevalidate().sMaxAge(60L, TimeUnit.MINUTES)
                        .getHeaderValue());

        /*
        *
        * Suppose for all requests having ips ending with 192,
        * we want to redirect request to "auth-failed"
        *
        * */
        if(request.getRemoteAddr().startsWith("192")){
            response.sendRedirect("/auth-failed");
            // returning false ensures that request is not further required to be intercepted. Response is directly send to user hereafter.
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        log.info("------------ LogInterceptor postHandle (Start) -------------");
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("------------------ LogInterceptor after view is rendered (Start) ---------------");
        long endTime = System.currentTimeMillis();
        long startTime = Long.parseLong(request.getAttribute("startTime")+"");
        log.info("-------------- Total time taken to process request (in milliseconds) "+(endTime-startTime)+" ms");
        log.info("------------------ LogInterceptor after view is rendered (End) ---------------");
    }

    LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }
}
