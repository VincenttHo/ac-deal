package com.vincenttho.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @className:com.vincent.config.aop.ExceptionAop
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2019/10/10     VincentHo       v1.0.0        create
 */
@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || " +
            "@within(org.springframework.stereotype.Service)")
    public void point() {

    }

    /**
    * @Title: aroundLog
    * @Description: 输出日志
    * @param pjp
    * @return java.lang.Object
    * @throws
    * @author: VincentHo
    * @date 2019/10/10
    * @version 1.0
    */
    @Around("point()")
    private Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {

        log.info("方法开始，方法签名[{}], 参数值{}",
                pjp.toString(),
                Arrays.toString(pjp.getArgs()));

        // 开始时间
        long startTime = System.currentTimeMillis();

        Object retVal = pjp.proceed(pjp.getArgs());

        // 结束时间
        long endTime = System.currentTimeMillis();
        float excTime = (float)(endTime-startTime)/1000;

        log.info("方法结束，方法签名[{}]，返回值[{}]",
                pjp.toString(),
                retVal);
        log.info("方法耗时：[{}]", excTime);

        return retVal;
    }

    /**
     * @Description: 异常日志输出
     */
    @AfterThrowing("point()")
    private void exceptionLog(JoinPoint jp) {
        log.error("执行函数异常[{}]，参数值{}", jp.getSignature(), Arrays.toString(jp.getArgs()));
    }

    @Before("point()")
    public void writeBeforeLog(JoinPoint jp) {
        this.debugInService(jp, "Start");
    }

    @After("point()")
    public void writeAfterLog(JoinPoint jp) {
        this.debugInService(jp, "End");
    }

    private void debugInService(JoinPoint jp, String msg) {
        log.info("{}.{}(){}",jp.getTarget().getClass().getSimpleName(), jp.getSignature().getName(), msg);
    }

}