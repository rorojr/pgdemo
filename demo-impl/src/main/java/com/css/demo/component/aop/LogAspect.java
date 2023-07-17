package com.css.demo.component.aop;

import com.css.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

    @Around("execution(* com.css.demo.controller..*.*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest requestContext = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        Object[] args = joinPoint.getArgs();
        List<Object> arguments  = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
                continue;
            }
            arguments.add(arg);
        }

        Object result = joinPoint.proceed();

        log.info("InboundRequest:[{}] {}, params {}",
                requestContext.getMethod(),
                requestContext.getRequestURI(),
                JsonUtils.serialize(arguments));
        log.info("InboundResponse: [{}] {} response {}",
                requestContext.getMethod(),
                requestContext.getRequestURI(),
                JsonUtils.serialize(result));

        return result;
    }
}
