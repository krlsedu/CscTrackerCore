package com.csctracker.configs;


import com.csctracker.dto.Metric;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;
import java.util.Date;

@Configuration
@Aspect
@EnableAspectJAutoProxy
@Log4j2
public class MetricGenerator {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${metric_service.host:http://metrics-service}")
    private String metricServiceHost;

    @Value("${metric_service.port:5000}")
    private String metricServicePort;

    @Around("execution(* com.csctracker..service..*(..)) || execution(* com.csctracker..repository..*(..)) || execution(* com.csctracker..dto..*(..)) || execution(* com.csctracker..model..*(..)) || execution(* com.csctracker..core..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        var metric = new Metric();
        metric.setDate(new Date());

        Object proceed = joinPoint.proceed();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();

        var thread = new Thread(() -> {
            long executionTime = System.currentTimeMillis() - start;
            metric.setAppName(appName);
            metric.setClazz(method.getDeclaringClass().getSimpleName());
            metric.setMethod(method.getName());
            metric.setFullClassName(declaringTypeName);
            metric.setExecutionTime(executionTime);
            try {
                log.info(Unirest.post(metricServiceHost + ":" + metricServicePort + "/metric")
                        .header("Content-Type", "application/json")
                        .body(metric)
                        .asString());
            } catch (Exception e) {
                log.error(e);
            }
        });

        thread.start();
        return proceed;
    }
}
