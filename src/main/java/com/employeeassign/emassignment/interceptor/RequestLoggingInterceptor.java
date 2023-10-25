package com.employeeassign.emassignment.interceptor;

import com.employeeassign.emassignment.Exceptions.HeaderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        logger.info("Request has been made to/at {} {}", request.getMethod(), request.getRequestURI());

        request.setAttribute("start-time", System.currentTimeMillis());
        String customHeaderValue = request.getHeader("auth-value");
        if(!customHeaderValue.equals("proceed")){
            throw new HeaderNotFoundException("Required Header Not present");
        }

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler,
                              ModelAndView modelAndView){
        long startTime = (Long) request.getAttribute("start-time");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime-startTime;
        logger.info("Response sent status: {} Time taken for execution: {}", response.getStatus(), executionTime);
    }



}
