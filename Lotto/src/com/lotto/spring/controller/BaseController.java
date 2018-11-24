package com.lotto.spring.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.UserSession;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class BaseController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private BaseController() {
		super();
	}

	/**
	 * 기본 화면 호출
	 * 2018.01.16
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/base/main")
	public String main(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 기본 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
			
			return BASE;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
}
