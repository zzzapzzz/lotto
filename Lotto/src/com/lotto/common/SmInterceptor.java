package com.lotto.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SmInterceptor extends HandlerInterceptorAdapter{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	// 클라이언트 요청이 컨트롤러에 도착하기 전에 호출 
    // false를 리턴하면 HandlerInterceptor 또는 컨트롤러를 실행하지 않고 요청 종료
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle 접근");
       	return true;
        
    }
 
    // 컨트롤러가 요청을 처리 한 후에 호출. 컨트롤러 실행 중 예외가 발생하면 실행 하지 않음  
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	System.out.println("postHandle 접근");
    }
 
    // 클라이언트 요청을 처리한 뒤, 즉 뷰를 통해 클라이언트에 응답을 전송한 뒤에 실행
    // 컨트롤러 처리 중 또는 뷰를 생성하는 과정에 예외가 발생해도 실행  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	System.out.println("afterCompletion 접근");
    }
}
