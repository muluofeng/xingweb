package com.xxx.xing.service;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author xing
 * @Created by 2017-05-03 上午10:50.
 */
@Aspect
@Component
public class AspectDemo {
//    @Pointcut("execution(* com.xxx.xing.service..*.*(..))")
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public void executeService(){}
//
//    @Before("executeService()")
//    public void doBefore(JoinPoint joinPoint){
//        System.out.println("前置通知!!!");
//    }
//
//    @AfterReturning(value = "execution(* com.xxx.xing.web..*.*(..))",returning = "obj")
//    public void doAfter(JoinPoint joinPoint,Object obj){
//        System.out.println("后置通知："+obj);
//    }
//
//    @Around(value = "execution(* com.xxx.xing.web..*.*(..))")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
//        System.out.println("环绕通知:"+proceedingJoinPoint.getSignature().getName());
//        try {
//            Object obj=proceedingJoinPoint.proceed();
//            return obj;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }
}
