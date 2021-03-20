package com.kakao.pay.investment.config.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;
import java.util.stream.Collectors;


@Aspect
@Component
@Slf4j
public class AopLogging {

    @Around("within(com.kakao.pay.investment.controller..*))")
    public Object aopLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String params = getRequestParams(); // request 값 가져오기

        long startTime = System.currentTimeMillis();

        log.info("====== request : {}({}) = {}", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName(), params);

        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        log.info("====== response : {}({}) = {} ({}ms)", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName(), new Gson().toJson(result), endTime - startTime);

        return result;

    }
    private String paramMapToString(Map<String, Object> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s) , ",
                        entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private String getRequestParams() {

        String params = "없음";

        RequestAttributes requestAttributes = RequestContextHolder
                .getRequestAttributes(); // 3

        if (requestAttributes != null) {
            ServerRequest request = (ServerRequest) RequestContextHolder.getRequestAttributes();

            Map<String, Object> paramMap = request.attributes();
            if (!paramMap.isEmpty()) {
                params = " [" + paramMapToString(paramMap) + "]";
            }
        }

        return params;

    }
}
