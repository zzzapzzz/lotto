package com.lotto.spring.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.chello.base.spring.core.DefaultController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * <pre>
 * DefaultController
 *
 * Since 2017. 11. 14
 * </pre>
 *
 * @author  cremazer
 */
public class DefaultSMController extends DefaultController {

	protected final static String MAIN   		= "main";
	protected final static String LOGIN   		= "login";
	
    protected final static String MSG       	= "msg";	//2018.02.23
    protected final static String POPUP       	= "popup";
	protected final static String POPUP_MSG     = "popup_msg";	//2016.04.01 msg 팝업용 화면
    protected final static String BASE_PLUGIN   = "base/plugin";	//2017.11.27
    protected final static String ERROR       	= "baseerror";
	
//    protected final static String LOCKBASE    = "lockbase";
//    protected final static String BASE        = "base";
//    protected final static String APPROVAL      = "approval";
//    protected final static String SEARCH      = "search";
//    protected final static String SEARCH_BH      = "searchbase";	//2016.09.08 cremazer
//    protected final static String INTEREST      = "interest";
//    protected final static String ACCESS      = "access";
//    protected final static String STATISTIC   = "statistic";
//    protected final static String STATISTICTREE   = "statistictree";
//    protected final static String SYSMNG      = "sysmng";
//    protected final static String SYSMNGTREE  = "sysmngtree";
//    protected final static String SYSINFO     = "sysinfo";
//    protected final static String SYSCODEMNG  = "syscodemng";
//    protected final static String ONLYCONT    = "onlycont";
//    protected final static String EXT         = "ext";
//    protected final static String NO_ACCESS   = "baseerror"; // 접근권한 없음.
//    protected final static String PLUGIN   = "plugin";
    
	/** JS Plugin Page 2017.11.27 */
	protected final static String PLUGIN_PAGE   = "pluginPage";
	
	@Autowired(required = true)
    private UserInfoService userInfoService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
    protected void writeJSON(HttpServletResponse response, JSONObject jsonObj){
		try {
			
			//2016.06.01 cremazer
			String data = jsonObj.toString(); 
			//2016.06.02 적용 시 jqgrid 에서 아이콘 처리 못하는 문제 발생함. 주석처리.
//			data = WebUtil.replaceResParam(data, "");
			
			response.setContentType("text/javascript;charset=UTF-8");
			response.getWriter().print(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//log.error(e.getMessage());
			System.err.println("IOException : "+e);
		}
	}
    
    protected void writeJsonArray(HttpServletResponse response, JSONArray jsonArray){
		try {
			response.setContentType("text/javascript;charset=UTF-8");
			response.getWriter().print(jsonArray.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//log.error(e.getMessage());
			System.err.println("IOException : "+e);
		}
	}
    
    protected void setActiveMenuSetting(UserSession userInfo,String strPageUri) {
    	List<CaseInsensitiveMap> gnbMenu =  userInfo.getGNBmenulist();
    	
    	CaseInsensitiveMap one = null;
    	CaseInsensitiveMap parentMenu = null;
    	for (int i=0; i<gnbMenu.size(); i++) {
    		one = gnbMenu.get(i);
    		
    		int pMenuId = Integer.parseInt(String.valueOf(one.get("p_menu_id")));    		
    		if (pMenuId == 0) {
    			parentMenu = gnbMenu.get(i);
    			parentMenu.put("haschild", "N");
    		} else {
    			int menuId = Integer.parseInt(String.valueOf(parentMenu.get("menu_id")));
    			if (menuId == pMenuId) {
    				parentMenu.put("haschild", "Y");
    			}
    		}
    		
    		String menuUrl = String.valueOf(one.get("menu_url"));
    		
			if (strPageUri.equals(menuUrl)) {
				one.put("current", "Y");
				parentMenu.put("current", "Y");
			} else {
				one.put("current", "N");
			}
    		
    	}
    	
    	System.out.println(gnbMenu.toString());
    	
    }
    
    protected CaseInsensitiveMap getCurrMenuInfo(UserSession userInfo,String strPageUri) {
    	
    	CaseInsensitiveMap currMenuInfo = new CaseInsensitiveMap();
    	
    	List<CaseInsensitiveMap> gnbMenu =  userInfo.getGNBmenulist();
    	
    	CaseInsensitiveMap one = null;
    	CaseInsensitiveMap parentMenu = null;
    	for (int i=0; i<gnbMenu.size(); i++) {
    		one = gnbMenu.get(i);
    		
    		int pMenuId = Integer.parseInt(String.valueOf(one.get("p_menu_id")));    		
    		if (pMenuId == 0) {
    			parentMenu = gnbMenu.get(i);
    			currMenuInfo = gnbMenu.get(i);
    			currMenuInfo.put("LNA_ELEMENT", parentMenu.get("lna_element"));
    			currMenuInfo.put("P_MENU_NM", parentMenu.get("menu_nm"));
    		} else {	//2017.11.27
    		
	    		String menuUrl = String.valueOf(one.get("menu_url"));
	    		
	    		if (strPageUri.equals(menuUrl)) {
	    			currMenuInfo = gnbMenu.get(i);
	    			currMenuInfo.put("LNA_ELEMENT", one.get("lna_element"));
	    			currMenuInfo.put("P_MENU_NM", parentMenu.get("menu_nm"));
	    			
	    			//2017.11.27
	    			break;
	    		}
    		}
    		
    	}
    	
    	return currMenuInfo;
    }
    
    protected void setModelMap (ModelMap modelMap, HttpServletRequest request) {
    	HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		// 시스템관리자 여부
	    String isAdmin = "N";
	    if (userInfo.isAdmin()) {
	    	isAdmin = "Y";
	    }
	    
	    //URL 확인
	    String strPageUri = (request.getRequestURI()).replaceFirst(".do", "");
	    //2018.01.19 메뉴호출시(ajax) url정보 제거
	    strPageUri = strPageUri.replace("ajax", "");
	    setActiveMenuSetting(userInfo, strPageUri);
	    
	    // 기본 설정 시 anonymous 권한허용 처리 2019.09.23 
	    modelMap.addAttribute("status", "authenticatedUser");
	    
	    // Content Page - File which will included in tiles definition
	    modelMap.addAttribute("IsAdmin",  isAdmin);
	    modelMap.addAttribute("UserInfo",  userInfo);
		modelMap.addAttribute("SystemInfo",  systemInfo);
		modelMap.addAttribute("GnbMenuList", userInfo.getGNBmenulist());
		modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, strPageUri));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setModelMapWithAuthCheck (ModelMap modelMap, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
    	SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
    	
    	// 시스템관리자 여부
    	String isAdmin = "N";
    	if (userInfo.isAdmin()) {
    		isAdmin = "Y";
    	}
    	
    	//URL 확인
    	String strPageUri = (request.getRequestURI()).replaceFirst(".do", "");
    	//2018.01.19 메뉴호출시(ajax) url정보 제거
    	strPageUri = strPageUri.replace("ajax", "");
    	setActiveMenuSetting(userInfo, strPageUri);
    	
    	// Content Page - File which will included in tiles definition
    	modelMap.addAttribute("IsAdmin",  isAdmin);
    	modelMap.addAttribute("UserInfo",  userInfo);
    	modelMap.addAttribute("SystemInfo",  systemInfo);
    	modelMap.addAttribute("GnbMenuList", userInfo.getGNBmenulist());
    	modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, strPageUri));
    	modelMap.addAttribute("referer",  request.getHeader("referer"));
    	
    	// 로그인 체크, 권한체크
    	int loginUserId = userInfo.getUser_no();
		modelMap.addAttribute("isLogin", userInfo.getIsLogin()); 
		if (!"Y".equals(userInfo.getIsLogin())) {
			log.info("["+loginUserId+"]\t로그인이 필요합니다.");
			modelMap.addAttribute("checkMsg", "로그인이 필요합니다.");
			modelMap.addAttribute("status", "need_login");
		} else {
			/*
			 * 권한 존재여부에 따라 차등 처리
			 * 1. anonymous 체크. 없으면 회원가입 필요
			 * 2. normal 체크. 없으면 유료가입 필요
			 * 3. paiduser 체크. 없으면 잘못된 접근.
			 */
			Map map = new HashMap();
			map.put("email", userInfo.getEmail());
			map.put("userNo", userInfo.getUser_no());
			map.put("menuUrl", strPageUri);
			boolean result = userInfoService.checkAuth(map);
			if (!result) {
				if (UserSession.GRADE_N.equals(userInfo.getAuth_task())) {
					log.info("["+loginUserId+"]\t유료 회원만 사용할 수 있습니다.");
					modelMap.addAttribute("checkMsg", "유료 회원만 사용할 수 있습니다.");
					modelMap.addAttribute("status", "need_paid");
				} else {
					if (userInfo.isAdmin()) {
						log.info("["+loginUserId+"]\t시스템관리자 입니다.");
						modelMap.addAttribute("status", "authenticatedUser");
					} else {
						log.info("["+loginUserId+"]\t잘못된 접근입니다.");
						modelMap.addAttribute("checkMsg", "잘못된 접근입니다.");
						modelMap.addAttribute("status", "wrong_access");
					}
				}
			} else {
				modelMap.addAttribute("status", "authenticatedUser");	
			}
		}
    }
}
