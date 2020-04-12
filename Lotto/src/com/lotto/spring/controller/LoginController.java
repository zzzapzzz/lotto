package com.lotto.spring.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.chello.base.common.resource.ResourceManager;
import com.lotto.common.AuthInfo;
import com.lotto.common.CommonUtils;
import com.lotto.common.LottoUtil;
import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class LoginController extends DefaultSMController implements HttpSessionBindingListener {
	
	@Autowired(required = true)
    private UserInfoService userInfoService;
	
    private static LoginController loginController = null;
	private static Hashtable loginUsers = new Hashtable();
	private static Hashtable loginUserIps = new Hashtable();
		
	private Logger log = Logger.getLogger(this.getClass());
	
	private LoginController() {
		super();
	}

	public static synchronized LoginController getInstance() {
		if (loginController == null) {
			loginController = new LoginController();
		}
		return loginController;
	}

	// 아이디가 맞는지 체크
	public boolean isValid(String userID, String userPW) {
				
		return true;			
	}

	// 해당 세션에 이미 로그인 되있는지 체크
	public boolean isLogin(String sessionID) {
		boolean isLogin = false;
		Enumeration e = loginUsers.keys();
		String key = "";
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (sessionID.equals(key)) {
				isLogin = true;
			}
		}
		return isLogin;
	}

	// 중복 로그인 막기 위해 아이디 사용중인지 체크
	public boolean isUsing(String userID) {
		boolean isUsing = false;
		Enumeration e = loginUsers.keys();
		String key = "";
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (userID.equals(loginUsers.get(key))) {
				isUsing = true;
				break;
			}
		}
		return isUsing;
	}
	
	/**
	 * 중복 로그인 막기 위해 아이디 사용중인지 체크
	 * 2016.04.12 cremazer
	 * 
	 * @param userID 사용자ID
	 * @param userIp 접속IP
	 * @return
	 */
	public boolean isUsing(String userID, String userIp) {
		boolean isUsing = false;
		Enumeration e = loginUsers.keys();
		String key = "";
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (userID.equals(loginUsers.get(key))) {
				isUsing = true;
				break;
			}
		}
		
		if (isUsing) {
			if (loginUserIps.containsKey(userID)) {
				String loginIp = (String) loginUserIps.get(userID);
				if (loginIp != null && userIp.equals(loginIp)) {
					//같은자리 접속
					isUsing = false;
				} else {
					//다른자리 접속
					isUsing = true;
				}
			} else {
				//접속IP 없음. 로그인 처리
				isUsing = false;
			}
		}
		return isUsing;
	}

	// 세션 생성
	public void setSession(HttpSession session, String userID) {
		loginUsers.put(session.getId(), userID);
		session.setAttribute("login", this.getInstance());
	}
	
	/**
	 * 세션 생성
	 * 2016.04.12 cremazer
	 * 
	 * @param session 세션
	 * @param userID 사용자ID
	 * @param userIp 접속IP
	 */
	public void setSession(HttpSession session, String userID, String userIp) {
		loginUsers.put(session.getId(), userID);
		loginUserIps.put(userID, userIp);
		session.setAttribute("login", this.getInstance());
	}

	// 세션 성립될 때
	public void valueBound(HttpSessionBindingEvent event) {
	}
	public void valueUnbound(HttpSessionBindingEvent event) {

		log.info("[" + event.getSession().getId() + "] > 세션 끊어짐 사용자 정보를 삭제합니다.");
		String userId = (String) loginUsers.get(event.getSession().getId());
		log.debug("userId : " + userId);
		loginUserIps.remove(userId);
		loginUsers.remove(event.getSession().getId());		
		log.debug("!!!!!!!!! 세션 끊어짐 valueUnbound");
		
	}

	// 세션 ID로 로긴된 ID 구분
	public String getUserID(String sessionID) {
		return (String) loginUsers.get(sessionID);
	}

	// 현재 접속자수
	public int getUserCount() {
		return loginUsers.size();
	}
    
    
	@RequestMapping("/fhrmdlsapdls")	//로그인메인
	public String main(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		log.info("fhrmdlsapdls >> 로그인 페이지로 이동하자");
        SystemSession systemInfo = AuthInfo.getSystemSetting(request, response);

        String isLogin = WebUtil.replaceParam(request.getParameter("isLogin"),"");
        
		modelMap.addAttribute("SystemInfo", systemInfo);
		modelMap.addAttribute("isLogin", isLogin);
		
		if (!"".equals(systemInfo.getHtml_target())) {
			return LOGIN+"/"+systemInfo.getHtml_target();
		} else {
			return LOGIN;
		}
	}
	
	@RequestMapping("/api/naver/member/oauth2c")	//네이버로그인콜백
	public String naverOauth2c(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		log.info("naverOauth2c >> 네이버로그인콜백 페이지 이동");
		SystemSession systemInfo = AuthInfo.getSystemSetting(request, response);
		
		modelMap.addAttribute("SystemInfo", systemInfo);
		return LOGIN+"/callback/naver";
	}
	
	@RequestMapping("/api/kakao/member/oauth2c")	//카카오로그인콜백
	public String kakaoOauth2c(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		log.info("naverOauth2c >> 카카오로그인콜백 페이지 이동");
		SystemSession systemInfo = AuthInfo.getSystemSetting(request, response);
		
		modelMap.addAttribute("SystemInfo", systemInfo);
		return LOGIN+"/callback/kakao";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping
	public void loginProc(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResourceManager resManagerForAuth = ResourceManager.getInstance();
		
        String email = WebUtil.replaceParam(request.getParameter("email"),"");
        String thwd = WebUtil.replaceParam(request.getParameter("thwd"),"");
        String userIp = WebUtil.getUser_IP(request, response); 
        
        log.info("[" + email + "][C] 일반 로그인 프로세스 시작");
        log.debug("[" + email + "] > email=" + email );
        log.debug("[" + email + "] > thwd=" + thwd );
        
        //개발자 체크
		String admList = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SYSTEM"), "");
		String approot = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "");
		
		String[] arrAdm = admList.split(",");
		boolean isAdmin = false;
		
		for (int i=0;i<arrAdm.length;i++) {
			if (email.split("@")[0].equals(arrAdm[i])) {
				isAdmin = true;				
				break;
			}
		}
		
        LoginController loginManager = LoginController.getInstance();
        HttpSession session = request.getSession();
        
        JSONObject jsonObj = new JSONObject();
        JSONArray rsltJSN = new JSONArray();
	        
			
		// 아이디 재설정 후 로그인 아이디로 설정
		log.info("[" + email + "] >  로그인 수행");
			
		CaseInsensitiveMap resultInfo = userInfoService.loginProc(email, thwd, userIp);
        if (resultInfo != null) {
        	String loginResult  = (String) resultInfo.get("result");
        	int userNo  = (int) resultInfo.get("user_no");
        	log.debug("result::" + loginResult);
        	
        	for (int i = 0; i < UserSession.RESULT_ARR.length; i++) {
        		if (UserSession.RESULT_ARR[i][0].equals(loginResult)) {
        			jsonObj.put("msg", UserSession.RESULT_ARR[i][1]);
        			jsonObj.put("result", loginResult);
        			break;
        		} else {
        			jsonObj.put("status", "fail");
        			jsonObj.put("msg", "Unknown Msg.");
        			jsonObj.put("result", loginResult);
        		}
			}

        	if ("T".equals(loginResult)) {
        		
        		// 상단메뉴
	        	List<CaseInsensitiveMap> GNBmenulist = null;
	        	//사용자 업무권한 설정
	        	Map taskAuth = null;
	        	// 왼쪽메뉴 정보 조회
	        	List<CaseInsensitiveMap> lnbMenuAuthList = null;
	        	// 메뉴권한 조회
	        	List<HashMap> menuAuthUrlList = null;
	        	
	        	UserSession userSession = null;
	        	
	        	if (isAdmin) {
	        		//관리자
	        		userSession = userInfoService.getAdminUserInfo(email);
	        	} else {
	        		//사용자
	        		userSession = userInfoService.getUserInfo(email);
	        	}
	        	
	        	Map paramMap = new HashMap();
				paramMap.put("userNo", userNo);
				paramMap.put("isAdmin", isAdmin);
					
				log.info("[" + email + "] > 사용자 메뉴 조회");
				GNBmenulist = userInfoService.getMenuAuthUrlListForUser(paramMap);
		        	
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
	        			} else {
	        				if (i == 0 || currPMenuId != pMenuId) {
	        					//2018.06.26
	        					//메뉴 목록에서 첫 번째 항목이 parent 메뉴가 없이 child 메뉴만 있을 경우, parent를 강제로 생성시킴
	        					/*
	        					 * menu_id
	        					 * 100	0	시스템설정	/sysmng/usermng		1	1		100	fa-gear	Y
	        					 * 		200	0	오더관리	/odmng/main		1	1		200	fa-database	N
	        					 * 300	0	프로모션	/promotion/distributiontable		1	1		300	fa-flag-checkered	Y
	        					 * 		500	0	특판오더	/special/order		1	1		500	fa-bullhorn	N
	        					 * 600	0	직원세일	/employee/sale		1	1		600	fa-group	Y
	        					 * 700	0	프리굳	/freegood/main		1	1		700	fa-cube	Y
	        					 */
	        					CaseInsensitiveMap menuParent = new CaseInsensitiveMap();
	        					String menuNm = "";
	        					String menuUrl = String.valueOf(menuOne.get("menu_url"));
	        					String lnaElement = "";
	        					menuParent.put("menu_id", pMenuId);
	        					menuParent.put("p_menu_id", 0);
	        					
	        					if (100 == pMenuId) {
	        						menuNm = "시스템설정";
	        						lnaElement = "fa-gear";
	        					} else if (300 == pMenuId) {
	        						menuNm = "프로모션";
	        						lnaElement = "fa-flag-checkered";
	        					} else if (600 == pMenuId) {
	        						menuNm = "직원세일";
	        						lnaElement = "fa-group";
	        					} else if (700 == pMenuId) {
	        						menuNm = "프리굳";
	        						lnaElement = "fa-cube";
	        					}  
	        					
	        					menuParent.put("menu_nm", menuNm);
	        					menuParent.put("m_type", "P");
	        					menuParent.put("menu_url", menuUrl);
	        					menuParent.put("lna_element", lnaElement);
	        					
	        					GNBmenulist.add(i, menuParent);
	        					i--;
	        				}
	        				
	        			}
	        		}
	        	}
	        	
	        	//2016.04.04
				//승인 모듈은 없으므로 주석처리함.
				String auth = null;
//				String uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "approval");
//			    if (uAth != null && !"".equals(uAth)) {
//			    	jsonObj.put("listenReqCnt", commonService.getReqListenCount(userid));
//			    } else {
//			    	jsonObj.put("listenReqCnt", 0);
//			    }
				
				log.info("[" + email + "] > 사용자 메뉴 URL 권한 체크");
//			    uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "main");
			    
			    if (auth == null || "".equals(auth)) {
			    	/*
			    	if (isAdmin) {
			    		jsonObj.put("goto", approot+"/base/main.do");
			    	} else {
			    		jsonObj.put("goto", approot+"/base/main.do");
//			    		jsonObj.put("goto", approot+userInfoService.userMenuFirstURL1(userid));
			    	}
			    	*/
			    	if (GNBmenulist != null) {
			    		CaseInsensitiveMap menuOne = (CaseInsensitiveMap) GNBmenulist.get(0);
			    		String menuUrl = (String) menuOne.get("menu_url");
			    		jsonObj.put("goto", approot + menuUrl + ".do");
			    	} else {
			    		jsonObj.put("goto", approot+"/base/main.do");
			    	}
			    } 
//			    else {
//			    	jsonObj.put("goto", approot+"/base/main.do");
//			    }
	        	
			    //세션 정보
				if (userSession != null) {
	        		session.setAttribute("login.id", userSession.getEmail());
	        		session.setAttribute("login.nm", userSession.getNickname());
	        		session.setAttribute("login.ip", userIp);
					
					userSession.setGNBmenulist(GNBmenulist);
		        	userSession.setLnbMenuAuthList(lnbMenuAuthList);
		        	userSession.setTaskAuth(taskAuth);
		        	userSession.setMenuAuthUrlList(menuAuthUrlList);
		        	userSession.setIsLogin("Y");
		        	
		        	userSession.setBeforeUrl("/fhrmdlsapdls.do");
		        	String nextUrl = jsonObj.getString("goto");
		        	userSession.setNextUrl(nextUrl);
		        	
				}
				
	        	modelMap.addAttribute("UserInfo", userSession);
	        	
	        	SystemSession systemInfo = AuthInfo.getSystemSetting();
	            modelMap.addAttribute("SystemInfo", systemInfo);
	        	
				jsonObj.put("status", "success");
				
			} else {
				jsonObj.put("status", "fail");
			}
        	
        	
        } else {
        	log.info("[" + email + "] > DB 조회결과 없음.");
        	jsonObj.put("status", "fail");
        	jsonObj.put("result", "noauth");
        	jsonObj.put("msg", UserSession.RESULT_ARR[1][1]);
        }
		
        log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping
	public void snsLoginProc(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResourceManager resManagerForAuth = ResourceManager.getInstance();
		
		String email = WebUtil.replaceParam(request.getParameter("email"),"");
		String emailVarifyYn = WebUtil.replaceParam(request.getParameter("email_varify_yn"),"");
		String userId = WebUtil.replaceParam(request.getParameter("user_id"),"");
		String nickname = WebUtil.replaceParam(request.getParameter("nickname"),"");
		String thumbnailImage = WebUtil.replaceParam(request.getParameter("thumbnail_image"),"");
		String snsType = WebUtil.replaceParam(request.getParameter("sns_type"),"");
		String userIp = WebUtil.getUser_IP(request, response); 
		
		log.info("[" + email + "][C] SNS 로그인 프로세스 시작");
		log.debug("[" + email + "] > email=" + email );
		log.debug("[" + email + "] > emailVarifyYn=" + emailVarifyYn );
		log.debug("[" + email + "] > userId=" + userId );
		log.debug("[" + email + "] > nickname=" + nickname );
		log.debug("[" + email + "] > thumbnailImage=" + thumbnailImage );
		log.debug("[" + email + "] > snsType=" + snsType );
		
		Map map = new HashMap();
		map.put("email", email);
		map.put("email_varify_yn", emailVarifyYn);
		if (!"".equals(userId)) {
			map.put("user_id", Integer.parseInt(userId));
		}
		map.put("nickname", nickname);
		map.put("thumbnail_image", thumbnailImage);
		map.put("sns_type", snsType);
		map.put("access_ip", userIp);
		
		//개발자 체크
		String admList = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SYSTEM"), "");
		String approot = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "");
		
		String[] arrAdm = admList.split(",");
		boolean isAdmin = false;
		
		for (int i=0;i<arrAdm.length;i++) {
			if (email.split("@")[0].equals(arrAdm[i])) {
				isAdmin = true;				
				break;
			}
		}
		
		LoginController loginManager = LoginController.getInstance();
		HttpSession session = request.getSession();
		
		JSONObject jsonObj = new JSONObject();
		JSONArray rsltJSN = new JSONArray();
		
		
		// 아이디 재설정 후 로그인 아이디로 설정
		log.info("[" + email + "] > SNS 로그인 수행");
		
		CaseInsensitiveMap resultInfo = userInfoService.snsLoginProc(map);
		if (resultInfo != null) {
			String loginResult  = (String) resultInfo.get("result");
			log.debug("result::" + loginResult);
			int userNo  = (int) resultInfo.get("user_no");
			
			for (int i = 0; i < UserSession.RESULT_ARR.length; i++) {
				if (UserSession.RESULT_ARR[i][0].equals(loginResult)) {
					jsonObj.put("msg", UserSession.RESULT_ARR[i][1]);
					jsonObj.put("result", loginResult);
					break;
				} else {
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "Unknown Msg.");
					jsonObj.put("result", loginResult);
				}
			}
			
			if ("T".equals(loginResult)) {
				
				// 상단메뉴
				List<CaseInsensitiveMap> GNBmenulist = null;
				//사용자 업무권한 설정
				Map taskAuth = null;
				// 왼쪽메뉴 정보 조회
				List<CaseInsensitiveMap> lnbMenuAuthList = null;
				// 메뉴권한 조회
				List<HashMap> menuAuthUrlList = null;
				
				UserSession userSession = null;
				
				if (isAdmin) {
					//관리자
					userSession = userInfoService.getAdminUserInfo(email);
				} else {
					//사용자
					userSession = userInfoService.getUserInfo(email);
				}
				
				Map paramMap = new HashMap();
				paramMap.put("userNo", userNo);
				paramMap.put("isAdmin", isAdmin);
				
				log.info("[" + email + "] > 사용자 메뉴 조회");
				GNBmenulist = userInfoService.getMenuAuthUrlListForUser(paramMap);
				
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
						} else {
							if (i == 0 || currPMenuId != pMenuId) {
								//2018.06.26
								//메뉴 목록에서 첫 번째 항목이 parent 메뉴가 없이 child 메뉴만 있을 경우, parent를 강제로 생성시킴
								/*
								 * menu_id
								 * 100	0	시스템설정	/sysmng/usermng		1	1		100	fa-gear	Y
								 * 		200	0	오더관리	/odmng/main		1	1		200	fa-database	N
								 * 300	0	프로모션	/promotion/distributiontable		1	1		300	fa-flag-checkered	Y
								 * 		500	0	특판오더	/special/order		1	1		500	fa-bullhorn	N
								 * 600	0	직원세일	/employee/sale		1	1		600	fa-group	Y
								 * 700	0	프리굳	/freegood/main		1	1		700	fa-cube	Y
								 */
								CaseInsensitiveMap menuParent = new CaseInsensitiveMap();
								String menuNm = "";
								String menuUrl = String.valueOf(menuOne.get("menu_url"));
								String lnaElement = "";
								menuParent.put("menu_id", pMenuId);
								menuParent.put("p_menu_id", 0);
								
								if (100 == pMenuId) {
									menuNm = "시스템설정";
									lnaElement = "fa-gear";
								} else if (300 == pMenuId) {
									menuNm = "프로모션";
									lnaElement = "fa-flag-checkered";
								} else if (600 == pMenuId) {
									menuNm = "직원세일";
									lnaElement = "fa-group";
								} else if (700 == pMenuId) {
									menuNm = "프리굳";
									lnaElement = "fa-cube";
								}  
								
								menuParent.put("menu_nm", menuNm);
								menuParent.put("m_type", "P");
								menuParent.put("menu_url", menuUrl);
								menuParent.put("lna_element", lnaElement);
								
								GNBmenulist.add(i, menuParent);
								i--;
							}
							
						}
					}
				}
				
				//2016.04.04
				//승인 모듈은 없으므로 주석처리함.
				String auth = null;
//				String uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "approval");
//			    if (uAth != null && !"".equals(uAth)) {
//			    	jsonObj.put("listenReqCnt", commonService.getReqListenCount(userid));
//			    } else {
//			    	jsonObj.put("listenReqCnt", 0);
//			    }
				
				log.info("[" + email + "] > 사용자 메뉴 URL 권한 체크");
//			    uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "main");
				
				if (auth == null || "".equals(auth)) {
					/*
			    	if (isAdmin) {
			    		jsonObj.put("goto", approot+"/base/main.do");
			    	} else {
			    		jsonObj.put("goto", approot+"/base/main.do");
//			    		jsonObj.put("goto", approot+userInfoService.userMenuFirstURL1(userid));
			    	}
					 */
					if (GNBmenulist != null) {
						CaseInsensitiveMap menuOne = (CaseInsensitiveMap) GNBmenulist.get(0);
						String menuUrl = (String) menuOne.get("menu_url");
						jsonObj.put("goto", approot + menuUrl + ".do");
					} else {
						jsonObj.put("goto", approot+"/base/main.do");
					}
				} 
//			    else {
//			    	jsonObj.put("goto", approot+"/base/main.do");
//			    }
				
				//세션 정보
				if (userSession != null) {
					session.setAttribute("login.id", userSession.getEmail());
					session.setAttribute("login.nm", userSession.getNickname());
					session.setAttribute("login.ip", userIp);
					
					userSession.setGNBmenulist(GNBmenulist);
					userSession.setLnbMenuAuthList(lnbMenuAuthList);
					userSession.setTaskAuth(taskAuth);
					userSession.setMenuAuthUrlList(menuAuthUrlList);
					userSession.setIsLogin("Y");
					
					userSession.setBeforeUrl("/fhrmdlsapdls.do");
					String nextUrl = jsonObj.getString("goto");
					userSession.setNextUrl(nextUrl);
					
				}
				
				modelMap.addAttribute("UserInfo", userSession);
				
				SystemSession systemInfo = AuthInfo.getSystemSetting();
				modelMap.addAttribute("SystemInfo", systemInfo);
				
				jsonObj.put("status", "success");
				
			} else {
				jsonObj.put("status", "fail");
			}
			
			
		} else {
			log.info("[" + email + "] > DB 조회결과 없음.");
			jsonObj.put("status", "fail");
			jsonObj.put("result", "noauth");
			jsonObj.put("msg", UserSession.RESULT_ARR[1][1]);
		}
		
		log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 로그아웃
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/logoutProc")
	public String logoutProc(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, SessionStatus sessionStastus) throws IOException {
		ResourceManager resManagerForAuth = ResourceManager.getInstance();
		
		HttpSession session = request.getSession();
    	
    	UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
			
    	String userIp = WebUtil.getUser_IP(request, response); 
		
		// 로그인 아이디
		String email = null;
		int userNo = 0;
		if (userInfo != null) {
			email = userInfo.getEmail();
			userNo = userInfo.getUser_no();
			
			//개발자 체크
			String admList = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SYSTEM"), "");
			
			String[] arrAdm = admList.split(",");
			boolean isAdmin = false;
			
			for (int i=0;i<arrAdm.length;i++) {
				if (email.equals(arrAdm[i])) {
					isAdmin = true;				
					break;
				}
			}
			
			if (!isAdmin) {
				log.info("[" + email + "][C] 로그아웃");
				
				// 로그기록 호출
				String msgS = "로그아웃";
				String msgL = "로그아웃 했습니다.";
				userInfoService.insertLogAgent("LOGOUT", userNo, userIp, msgS, msgL);
				
				log.info("[" + email + "] > 사용자 정보를 삭제합니다.");
			}
			
			session.invalidate();
			sessionStastus.setComplete();
		}

//        return "redirect:/fhrmdlsapdls.do";	//로그인메인
        return "redirect:/";	//첫화면
	} 
	
	/**
	 * 사용자 초기정보 설정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/login/setUserInfo")
	public void setUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject jsonObj = new JSONObject();
		
		String email       = WebUtil.replaceParam(request.getParameter("email"), "");
		log.info("[" + email + "][C] 사용자 초기정보 설정");
		
		String newThwd       = WebUtil.replaceParam(request.getParameter("new_thwd"), "");
		String thwdQ       = WebUtil.replaceParam(request.getParameter("thwd_q"), "");
		String thwdA       = WebUtil.replaceParam(request.getParameter("thwd_a"), "");
		String userip = request.getRemoteHost();
			
		Map map = new HashMap();
		map.put("email", email);
		map.put("access_ip", userip);
		map.put("thwd", newThwd);
		map.put("thwd_q", thwdQ);
		map.put("thwd_a", thwdA);
		
		
		
		//사용자 초기정보 설정
		log.info("[" + email + "] > 사용자 초기정보 설정");
		CaseInsensitiveMap resultInfo = userInfoService.setUserInfo(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + email + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
				
		log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 사용자 비밀번호 변경
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/login/changeThwd")
	public void changeThwd(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject jsonObj = new JSONObject();
		
		String email       = WebUtil.replaceParam(request.getParameter("email"), "");
		log.info("[" + email + "][C] 사용자 비밀번호 변경");
		
		String thwdA       = WebUtil.replaceParam(request.getParameter("thwd_a"), "");
		String changeThwd       = WebUtil.replaceParam(request.getParameter("change_thwd"), "");
		String userip = request.getRemoteHost();
		
		Map map = new HashMap();
		map.put("email", email);
		map.put("access_ip", userip);
		map.put("thwd", changeThwd);
		map.put("thwd_a", thwdA);
		
		//사용자 비밀번호 변경
		log.info("[" + email + "] > 사용자 비밀번호 변경");
		CaseInsensitiveMap resultInfo = userInfoService.changeThwd(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + email + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
		
		log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 비밀번호 찾기
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/login/forgetUserInfo")
	public void forgetUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject jsonObj = new JSONObject();
		
		String email       = WebUtil.replaceParam(request.getParameter("email"), "");
		log.info("[" + email + "][C] 비밀번호 찾기");
		
		//비밀번호 찾기
		log.info("[" + email + "] > 회원정보 조회");
		UserSession userSession = userInfoService.getUserInfo(email);
		String status = "";
//		String question = "";
		String msg = "";
		if (userSession != null) {
			status = "success";
//			question = userSession.getThwd_q();
			msg = "다음 단계를 진행해 주세요.";
		} else {
			status = "fail";
			msg = "등록되지 않은 사용자입니다.";
		}
		
		log.info("[" + email + "]\t" + msg);
		jsonObj.put("status", status);
//		jsonObj.put("question", question);
		jsonObj.put("msg", msg);
		
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 비밀번호 초기화
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/login/initThwd")
	public void initThwd(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject jsonObj = new JSONObject();
		
		String email       = WebUtil.replaceParam(request.getParameter("email"), "");
		log.info("[" + email + "][C] 비밀번호 초기화");
		
		String userip = request.getRemoteHost();
		
		//비밀번호 초기화
		Map map = new HashMap();
		map.put("email", email);
		map.put("access_ip", userip);
		
		CaseInsensitiveMap resultInfo = userInfoService.initThwd(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + email + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
		
		log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 회원가입
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/login/join")
	public void join(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject jsonObj = new JSONObject();
		
		String joinEmail = WebUtil.replaceParam(request.getParameter("join_email"), "");
		String joinThwd = WebUtil.replaceParam(request.getParameter("join_thwd"), "");
		String joinNickname = WebUtil.replaceParam(request.getParameter("join_nickname"), "");
		String userip = request.getRemoteHost();
		
		log.info("[" + userip + "][C] 회원가입");
		
		String thwd = joinThwd;
		try {
			thwd = CommonUtils.sha256(joinThwd);			
		} catch (Exception e) {
			log.info("[" + userip + "]\tSHA256 Exception : " + e.getMessage());	
		}
		
		Map map = new HashMap();
		map.put("email", joinEmail);
		map.put("thwd", thwd);
		map.put("nickname", joinNickname);
		map.put("access_ip", userip);
		
		log.debug("[" + userip + "]\tEmail 중복체크");
		int isExist = userInfoService.checkDuplEmail(map);
		if (isExist > 0) {
			
			jsonObj.put("status", "isExist");
			jsonObj.put("msg", "등록된 이메일이 존재합니다.");
			log.info("[" + userip + "]\t" + "등록된 이메일이 존재합니다.");
		} else {
			
			boolean exist = true;
			
			// GetUserKey 2020.04.12
			String key = "";
			do {
				// 키 생성
				log.debug("[" + userip + "]\tUser Key 생성");
				key = userInfoService.getUserKey();
				
				// 존재여부 확인 (true:존재, false:존재하지 않음)
				log.debug("[" + userip + "]\tUser Key 중복체크");
				exist = userInfoService.checkDuplUserKey(key);
				
			} while (exist);
			map.put("userKey", key);
			
			log.debug("[" + userip + "]\t회원가입 처리");
			int i = userInfoService.join(map);
			if (i > 0) {
				log.info("[" + userip + "]\t" + "회원가입 완료");
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "회원가입이 완료되었습니다. 다시 로그인하세요.");
		}
		
		
		log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}	
}
