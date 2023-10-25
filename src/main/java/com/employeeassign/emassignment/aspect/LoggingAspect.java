package com.employeeassign.emassignment.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.employeeassign.emassignment.controller.EmployeeController.getEmployees(..))" +
            " || execution(* com.employeeassign.emassignment.controller.EmployeeController.getEmployeeById(..))")
    public void beforeGetEmployees(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toString();
        logger.info("Method {} has been invoked", methodName);
    }
}
