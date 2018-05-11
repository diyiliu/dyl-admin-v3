package com.diyiliu.support.filter;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 记录请求行为
 *
 * Description: LogRecordFilter
 * Author: DIYILIU
 * Update: 2018-05-11 10:10
 */

@Aspect
@Component
public class LogRecordFilter {

    @AfterReturning(pointcut = "execution(* com.diyiliu.web..*Controller.*(..))", returning = "result")
    public void doAfter(Object result) {


    }
}
