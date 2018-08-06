package com.lotto.spring.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.common.AuthInfo;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;

@Controller("index")
@SessionAttributes({"SystemInfo"})
public class IndexController extends DefaultSMController{

	
	public static final int KEY_SIZE = 1024;
	
    private Logger log = Logger.getLogger(this.getClass());
    
    /**
	 * Index Controller
	 * @param modelMap
	 * @param request
	 * @return    String
     * @throws IOException 
     * @throws ServletException 
	 * @uml.property  name="storeInfoService"
	 * @uml.associationEnd  readOnly="true"
	 */
    @RequestMapping ("/index")
    public void main(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ServletException {
    	log.info("[IndexController] 시작");
    	
    	
    	HttpSession session = request.getSession();
    	UserSession userInfo = null;
    	SystemSession systemInfo = null;
    	
    	if (session != null) {
    		userInfo = (UserSession) session.getAttribute("UserInfo");
    		
    		log.debug("[IndexController] userInfo [" +userInfo + "]");
    		if (userInfo != null) {
    			systemInfo = (SystemSession) session.getAttribute("SystemInfo");
//    			return userInfo.getBeforeUrl();
    			
    			String referUrl = request.getHeader("referer"); 	// 이전 url
    			String nextUrl = userInfo.getNextUrl();
    			String beforeUrl = userInfo.getBeforeUrl();
    			log.info("[IndexController] referUrl [" +referUrl + "]");
    			log.info("[IndexController] beforeUrl [" +beforeUrl + "]");
    			log.info("[IndexController] userInfo.getNextUrl() [" +userInfo.getNextUrl() + "]");
				if (beforeUrl != null) {
					beforeUrl = beforeUrl.replace("http://","");
					beforeUrl = beforeUrl.replace("https://","");
					beforeUrl = beforeUrl.substring(beforeUrl.indexOf("/")+1);
					
					if ("".equals(beforeUrl)) {
						beforeUrl = "fhrmdlsapdls.do";
					}
					
					if (nextUrl != null && !"".equals(nextUrl)) {
						nextUrl = nextUrl.replace("http://","");
						nextUrl = nextUrl.replace("https://","");
						nextUrl = nextUrl.substring(nextUrl.indexOf("/")+1);
//						return "redirect:/" + nextUrl;
						
						RequestDispatcher rd = request.getRequestDispatcher("/" + nextUrl);
						rd.forward(request, response);
						
					} else {
//						return "redirect:/" + beforeUrl;
						RequestDispatcher rd = request.getRequestDispatcher("/" + beforeUrl);
						rd.forward(request, response);
					}
				} else {
					//TODO 로그아웃 처리
					
//					return "redirect:/fhrmdlsapdls.do";	//로그인메인
					
//					return LOGIN;
					
					RequestDispatcher rd = request.getRequestDispatcher("/sysmng/usermng.do");
					rd.forward(request, response);
				}
    		} else {
    			systemInfo = AuthInfo.getSystemSetting();
    				
    			//로그인 화면으로 이동
    			log.info("[IndexController] 사용자 정보 없음. 로그인 화면으로 이동.");
    			
    			modelMap.addAttribute("SystemInfo", systemInfo);
//   				return LOGIN;
    			RequestDispatcher rd = request.getRequestDispatcher("/sysmng/usermng.do");
				rd.forward(request, response);
				
				//TODO
				//캐시정보 설정하기
				//1. 실시간 재고량 설정
				//2. 항목별 아이템 목록 설정
				
				//TODO
				//시스템관리자 캐시정보 갱신 기능 개발 필요
				
    		}
    		
    	} else {
    		
    		//로그인 화면으로 이동
    		log.info("사용자 정보 없음. 로그인 화면으로 이동.(No Session)");
    		systemInfo = AuthInfo.getSystemSetting();
			modelMap.addAttribute("SystemInfo", systemInfo);
//			if (!"".equals(systemInfo.getHtml_target())) {
//				return LOGIN+"/"+systemInfo.getHtml_target();
//			} else {
//				return LOGIN;
//			}
			RequestDispatcher rd = request.getRequestDispatcher("/sysmng/usermng.do");
			rd.forward(request, response);
    	}
    	
    }
    
}
