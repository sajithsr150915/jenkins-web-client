package com.stackroute.field.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect 
@Component
public class LoggingAspect {

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	
	
	@Before("execution(* com.com.stackroute.keepnote.controller.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("entering Controller method"+joinPoint.getSignature().getName());
	}
	
	@After("execution(* com.com.stackroute.keepnote.controller.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("aftr xcuting Controller method"+joinPoint.getSignature().getName());
	}
	
	@AfterReturning("execution(* com.com.stackroute.keepnote.controller.*(..))")
	public void afterRt(JoinPoint joinPoint) {
		logger.info("after returning Controller method"+joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("execution(* com.com.stackroute.keepnote.controller.*(..))")
	public void afterThrowing(JoinPoint joinPoint) {
		logger.info("error Controller method"+joinPoint.getSignature().getName());
	}
	
	
}
