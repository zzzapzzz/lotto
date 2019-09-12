package com.lotto.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.chello.base.common.resource.ResourceManager;
import com.lotto.common.AuthInfo;
import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.service.UserInfoService;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class BaseController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private UserInfoService userInfoService;
	
	private BaseController() {
		super();
	}

	/**
	 * 기본 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/base/base")
	public String base(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 기본 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
			modelMap.addAttribute(PLUGIN_PAGE, "base/plugins/Main_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 첫 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/base/main")
	public String baseMain(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 첫 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
			modelMap.addAttribute(PLUGIN_PAGE, "base/plugins/Main_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			ResourceManager resManagerForAuth = ResourceManager.getInstance();
			SystemSession systemInfo = AuthInfo.getSystemSetting();
			String approot = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "");
			String userIp = WebUtil.getUser_IP(request, response);
			
			log.info("["+userIp+"][C] 첫 화면 호출");
			
			// TODO 메인 화면으로 접속 처리 2019.08.19
			boolean isAdmin = false;
			String nextUrl = "/fhrmdlsapdls.do";
			
			Map paramMap = new HashMap();
			paramMap.put("isAdmin", isAdmin);
				
			log.info("[IndexController] > 일반사용자 메뉴 조회");
			List<CaseInsensitiveMap> GNBmenulist = userInfoService.getMenuAuthUrlListForUser(paramMap);
			
			if (GNBmenulist != null && GNBmenulist.size() > 0) {
        		int currPMenuId = 0;
        		
        		for(int i = 0 ; i < GNBmenulist.size() ; i++) {
        			CaseInsensitiveMap menuOne = (CaseInsensitiveMap) GNBmenulist.get(i);
        			int pMenuId = Integer.parseInt(String.valueOf(menuOne.get("p_menu_id")));
        			int menuId = Integer.parseInt(String.valueOf(menuOne.get("menu_id")));
        			if (pMenuId == 0) {
        				currPMenuId = menuId; 
        				boolean hasChild = false;
        				for(int j = i+1 ; j < GNBmenulist.size() ; j++) {
        					CaseInsensitiveMap menuTwo = (CaseInsensitiveMap) GNBmenulist.get(j);
        					int subPMenuId = Integer.parseInt(String.valueOf(menuTwo.get("p_menu_id")));
        					if (menuId != 0 && menuId == subPMenuId) {
        						hasChild = true;
        						break;
        					}
        				}
        				
        				menuOne.put("hasChild", hasChild?"Y":"N");
        				GNBmenulist.set(i, menuOne);
        			}
        		}
        	}
			
			if (GNBmenulist != null) {
	    		CaseInsensitiveMap menuOne = (CaseInsensitiveMap) GNBmenulist.get(0);
	    		String menuUrl = (String) menuOne.get("menu_url");
	    		nextUrl = approot + menuUrl + ".do";
	    	}
			
			UserSession userSession = new UserSession();
			userSession.setGNBmenulist(GNBmenulist);
        	
        	userSession.setBeforeUrl("/fhrmdlsapdls.do");
        	userSession.setNextUrl(nextUrl);
	        
        	// 익명의 사용자는 UserNo를 접속시간으로 설정 처리
        	Date date = new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    		String todayAccessTime = sdf.format(date);
    		userSession.setAccess_no(Long.parseLong(todayAccessTime));
    		userSession.setIsLogin("N");
    		userSession.setAuth_task("anonymous");
    		userSession.setAuth_menu("anonymous");
    		
        	modelMap.addAttribute("UserInfo", userSession);
            modelMap.addAttribute("SystemInfo", systemInfo);
            modelMap.addAttribute("nextUrl", nextUrl);
			
			
			return MAIN;			
			
		}
	}
	
	/**
	 * 기본 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/base/baseajax")
	public String baseajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 기본 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 기본 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/base/baseplugin")
	public String baseplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 기본 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "base/plugins/Main_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 에러 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 */
	@RequestMapping("/error")
	public String error(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 에러 화면 호출");
			
			setModelMap(modelMap, request);
			
			return ERROR;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
}
