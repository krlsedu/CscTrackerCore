package com.csctracker.configs;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class MicrometerConfig {

    private TimedAspect timedAspect;

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        timedAspect = new TimedAspect(registry);
        return timedAspect;
    }

    @Around("execution(* com.csctracker..service..*(..)) || execution(* com.csctracker..controller..*(..)) || execution(* com.csctracker..repository..*(..))")
    public Object timeds(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Timed timed = method.getAnnotation(Timed.class);
        if (timed == null) {
            method.setAccessible(true);
            Annotation timedAnnotation = new Timed() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return Timed.class;
                }

                @Override
                public String value() {
                    return "execution.time";
                }

                @Override
                public String[] extraTags() {
                    return new String[0];
                }

                @Override
                public boolean longTask() {
                    return false;
                }

                @Override
                public double[] percentiles() {
                    return new double[0];
                }

                @Override
                public boolean histogram() {
                    return false;
                }

                @Override
                public String description() {
                    return "Time taken to return greeting";
                }
            };
            AddAnotations.addAnnotation(method, timedAnnotation);
        }
        return timedAspect.timedMethod(joinPoint);
    }
}
