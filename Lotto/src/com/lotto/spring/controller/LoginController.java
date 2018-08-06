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

		modelMap.addAttribute("SystemInfo", systemInfo);
		if (!"".equals(systemInfo.getHtml_target())) {
			return LOGIN+"/"+systemInfo.getHtml_target();
		} else {
			return LOGIN;
		}
	}
	
	
	@RequestMapping
	public void loginProc(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResourceManager resManagerForAuth = ResourceManager.getInstance();
		
//        String userid = WebUtil.replaceParam(request.getParameter("uid"),"");
//        String thwd = WebUtil.replaceParam(request.getParameter("thwd"),"");
        String usr_id = WebUtil.replaceParam(request.getParameter("uid"),"");
        String thwd = WebUtil.replaceParam(request.getParameter("thwd"),"");
        String userIp = WebUtil.getUser_IP(request, response); 
        
        log.info("[" + usr_id + "][C]  로그인 프로세스 시작");
        log.debug("[" + usr_id + "] > userid=" + usr_id );
        log.debug("[" + usr_id + "] > thwd=" + thwd );
        
        //개발자 체크
		String admList = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SYSTEM"), "");
		String approot = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "");
		
		String[] arrAdm = admList.split(",");
		boolean isAdmin = false;
		
		for (int i=0;i<arrAdm.length;i++) {
			if (usr_id.equals(arrAdm[i])) {
				isAdmin = true;				
				break;
			}
		}
		
        LoginController loginManager = LoginController.getInstance();
        HttpSession session = request.getSession();
        
        JSONObject jsonObj = new JSONObject();
        JSONArray rsltJSN = new JSONArray();
	        
			
		// 로그인 아이디 - 로그 출력
		String loginUserId = null;
		// 아이디 재설정 후 로그인 아이디로 설정
		loginUserId = usr_id;
		log.info("[" + loginUserId + "] >  로그인 수행");
			
        CaseInsensitiveMap resultInfo = userInfoService.loginProc(usr_id, thwd, userIp);
        if (resultInfo != null) {
        	log.debug("result::"+resultInfo.get("RESULT").toString());
        	log.debug("result data::"+resultInfo.toString());
        	
        	//2018.01.24 cremazer
        	if (resultInfo.containsKey("question")) {
        		String question = (String)resultInfo.get("question");
        		jsonObj.put("question", question);
        	}
        	
        	
        	if (resultInfo != null && resultInfo.size() > 0 && resultInfo.get("RESULT").toString().startsWith("T")) {
	        	String srvMsgCd  = (String)resultInfo.get("RESULT");
	        	String srvMsgTxt = (String)resultInfo.get("MSG");
	        	
	        	if ("T01".equals(srvMsgCd) 
	        			|| "T02".equals(srvMsgCd) 
	        			|| "T03".equals(srvMsgCd) 
	        			|| "T04".equals(srvMsgCd)
	        			|| "T05".equals(srvMsgCd)
	        			|| "T06".equals(srvMsgCd)
	        			) {
	        		log.info("[" + usr_id + "] > " + srvMsgTxt);
	        		
	        		jsonObj.put("status", "fail");
		        	jsonObj.put("result", srvMsgCd);
		        	jsonObj.put("msg", srvMsgTxt);
		        	
	        	} else {
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
		        		userSession = userInfoService.getAdminUserInfo(usr_id);
		        	} else {
		        		userSession = userInfoService.getUserInfo(usr_id);
		        	}
			        	
	        		//사용자
	        		log.info("[" + usr_id + "] ===> 사용자 정보 설정 시작.");
		        		
		        		
	        		resultInfo.put("RESULT", srvMsgCd);
	        		resultInfo.put("MSG", srvMsgTxt);
		        		
		        		
//		        		GNBmenulist = userInfoService.userMenuDepth1(userid);
	        		Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("usr_id", usr_id);
						
					log.info("[" + usr_id + "] > 사용자 메뉴 조회");
					GNBmenulist = userInfoService.getMenuAuthUrlList(paramMap);
			        	
		        	if (GNBmenulist != null && GNBmenulist.size() > 0) {
		        		for(int i = 0 ; i < GNBmenulist.size() ; i++) {
		        			CaseInsensitiveMap menuOne = (CaseInsensitiveMap) GNBmenulist.get(i);
		        			int pMenuId = Integer.parseInt(String.valueOf(menuOne.get("p_menu_id")));
		        			if (pMenuId == 0) {
		        				boolean hasChild = false;
		        				for(int j = 0 ; j < GNBmenulist.size() ; j++) {
		        					CaseInsensitiveMap menuTwo = (CaseInsensitiveMap) GNBmenulist.get(j);
		        					int subPMenuId = Integer.parseInt(String.valueOf(menuOne.get("p_menu_id")));
		        					if (pMenuId != 0 && pMenuId == subPMenuId) {
		        						hasChild = true;
		        						break;
		        					}
		        				}
		        				
		        				menuOne.put("hasChild", hasChild?"Y":"N");
		        				GNBmenulist.set(i, menuOne);
		        			}
		        		}
		        	}
			        	
		        	log.info("[" + usr_id + "] ===> 사용자 정보 설정 종료.");
		        	

		        	// AuthInfo.setUserInfo(request, response, list, GNBmenulist);
		        	
		        	if (srvMsgCd.startsWith("F")) {
		        		log.info("[" + usr_id + "] > 로그인 실패 : " + srvMsgCd);
		        		jsonObj.put("status", "fail");
			        	jsonObj.put("result", srvMsgCd);
			        	jsonObj.put("msg", srvMsgTxt);
		        	} else {
		        		log.debug("JSONObject::"+(rsltJSN.fromObject(resultInfo)).toString());
		        		
						jsonObj.put("status", "success");
						jsonObj.put("data", (rsltJSN.fromObject(resultInfo)).toString());
						
						if ("T".equals(resultInfo.get("RESULT"))) {
							//2016.04.04
							//승인 모듈은 없으므로 주석처리함.
							String uAth = null;
//							String uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "approval");
//						    if (uAth != null && !"".equals(uAth)) {
//						    	jsonObj.put("listenReqCnt", commonService.getReqListenCount(userid));
//						    } else {
//						    	jsonObj.put("listenReqCnt", 0);
//						    }
							
							log.info("[" + usr_id + "] > 사용자 메뉴 URL 권한 체크");
//						    uAth = userInfoService.userMenuURLAuthCheck(userid, "search", "main");
						    
						    if (uAth == null || "".equals(uAth)) {
						    	/*
						    	if (isAdmin) {
						    		jsonObj.put("goto", approot+"/base/main.do");
						    	} else {
						    		jsonObj.put("goto", approot+"/base/main.do");
//						    		jsonObj.put("goto", approot+userInfoService.userMenuFirstURL1(userid));
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
//						    else {
//						    	jsonObj.put("goto", approot+"/base/main.do");
//						    }
						    
						    
						}
						
						//세션 정보
						if (userSession != null) {
			        		session.setAttribute("login.id", userSession.getUsr_id());
			        		session.setAttribute("login.nm", userSession.getUsr_nm());
			        		session.setAttribute("login.ip", userIp);
							
							userSession.setGNBmenulist(GNBmenulist);
				        	userSession.setLnbMenuAuthList(lnbMenuAuthList);
				        	userSession.setTaskAuth(taskAuth);
				        	userSession.setMenuAuthUrlList(menuAuthUrlList);
				        	
				        	userSession.setBeforeUrl("/fhrmdlsapdls.do");
				        	String nextUrl = jsonObj.getString("goto");
				        	userSession.setNextUrl(nextUrl);
						}
						
			        	modelMap.addAttribute("UserInfo", userSession);
			        	
			        	SystemSession systemInfo = AuthInfo.getSystemSetting();
			            modelMap.addAttribute("SystemInfo", systemInfo);
			        	
		        	}
		        	
	        	}
	        } else {
	        	//로그인 실패 시
	        	
	        	if (resultInfo.size() > 0) {
	        		log.info("[" + usr_id + "] > DB 조회결과 있음. " + resultInfo.get("MSG"));
	        		jsonObj.put("status", "fail");
	        		jsonObj.put("result", resultInfo.get("RESULT"));
	        		jsonObj.put("msg", resultInfo.get("MSG"));
	        	} else {
	        		log.info("[" + usr_id + "] > DB 조회결과 없음. 아이디 또는 비밀번호가 일치하지 않습니다.");
	        		jsonObj.put("status", "fail");
	        		jsonObj.put("result", "noauth");
	        		jsonObj.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        	}
	        	
	        }
        	
        } else {
        	log.info("[" + loginUserId + "] > DB 조회결과 없음.");
        	jsonObj.put("status", "fail");
        	jsonObj.put("result", "noauth");
        	jsonObj.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
		
//        log.debug("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
//        String redirectUrl = "";
//        if (jsonObj.containsKey("goto")) {
//        	redirectUrl = jsonObj.getString("goto");
//        } else {
//        	redirectUrl = "/fhrmdlsapdls.do";
//        }
//        
//        return "redirect:" + approot + redirectUrl;
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
		String loginUserId = null;
		String loginUserNm = null;
		if (userInfo != null) {
			loginUserId = userInfo.getUsr_id();
			loginUserNm = userInfo.getUsr_nm();
			
			//개발자 체크
			String admList = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SYSTEM"), "");
			String approot = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "");
			
			String[] arrAdm = admList.split(",");
			boolean isAdmin = false;
			
			for (int i=0;i<arrAdm.length;i++) {
				if (loginUserId.equals(arrAdm[i])) {
					isAdmin = true;				
					break;
				}
			}
			
			if (!isAdmin) {
				log.info("[" + loginUserId + "][C] 로그아웃");
				
				// 로그기록 호출
				String msg = "로그아웃";
				userInfoService.insertLogAgent("LOGOUT", loginUserId, loginUserNm, userIp, msg);
				
				log.info("[" + loginUserId + "] > 사용자 정보를 삭제합니다.");
			}
			
			session.invalidate();
			sessionStastus.setComplete();
		}

        return "redirect:/fhrmdlsapdls.do";	//로그인메인
	} 
	
	/**
	 * 사용자 초기정보 설정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/login/setUserInfo")
	public void setUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		String usrId       = WebUtil.replaceParam(request.getParameter("set_usr_id"), "");
		String loginUserId = usrId;
		log.info("[" + loginUserId + "][C] 사용자 초기정보 설정");
		
		String usrThwd       = WebUtil.replaceParam(request.getParameter("new_thwd"), "");
		String thwdQ       = WebUtil.replaceParam(request.getParameter("thwd_q"), "");
		String thwdA       = WebUtil.replaceParam(request.getParameter("thwd_a"), "");
		String userip = request.getRemoteHost();
			
		Map map = new HashMap();
		map.put("usr_id", usrId);
		map.put("access_ip", userip);
		map.put("usr_thwd", usrThwd);
		map.put("thwd_q", thwdQ);
		map.put("thwd_a", thwdA);
		
		
		
		//사용자 초기정보 설정
		log.info("[" + loginUserId + "] > 사용자 초기정보 설정");
		CaseInsensitiveMap resultInfo = userInfoService.setUserInfo(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + loginUserId + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
				
		System.out.println("JSONObject::"+jsonObj.toString());
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
	@RequestMapping("/login/changeThwd")
	public void changeThwd(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		String usrId       = WebUtil.replaceParam(request.getParameter("change_usr_id"), "");
		String loginUserId = usrId;
		log.info("[" + loginUserId + "][C] 사용자 비밀번호 변경");
		
		String thwdA       = WebUtil.replaceParam(request.getParameter("thwd_a"), "");
		String usrThwd       = WebUtil.replaceParam(request.getParameter("change_thwd"), "");
		String userip = request.getRemoteHost();
		
		Map map = new HashMap();
		map.put("usr_id", usrId);
		map.put("access_ip", userip);
		map.put("usr_thwd", usrThwd);
		map.put("thwd_a", thwdA);
		
		//사용자 비밀번호 변경
		log.info("[" + loginUserId + "] > 사용자 비밀번호 변경");
		CaseInsensitiveMap resultInfo = userInfoService.changeThwd(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + loginUserId + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
		
		System.out.println("JSONObject::"+jsonObj.toString());
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
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		String usrId       = WebUtil.replaceParam(request.getParameter("search_usr_id"), "");
		String loginUserId = usrId;
		log.info("[" + loginUserId + "][C] 비밀번호 찾기");
		
		//비밀번호 찾기
		log.info("[" + loginUserId + "] > 사원정보 조회");
		UserSession userSession = userInfoService.getUserInfo(usrId);
		String status = "";
		String question = "";
		String msg = "";
		if (userSession != null && usrId.equals(userSession.getUsr_id())) {
			status = "success";
			question = userSession.getThwd_q();
			msg = "다음 단계를 진행해 주세요.";
		} else {
			status = "fail";
			msg = "등록되지 않은 사용자입니다.";
		}
		
		log.info("[" + loginUserId + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("question", question);
		jsonObj.put("msg", msg);
		
		System.out.println("JSONObject::"+jsonObj.toString());
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
	@RequestMapping("/login/initThwd")
	public void initThwd(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		String usrId       = WebUtil.replaceParam(request.getParameter("search_usr_id"), "");
		String loginUserId = usrId;
		log.info("[" + loginUserId + "][C] 비밀번호 초기화");
		
		String userip = request.getRemoteHost();
		
		//비밀번호 초기화
		Map map = new HashMap();
		map.put("usr_id", usrId);
		map.put("access_ip", userip);
		
		CaseInsensitiveMap resultInfo = userInfoService.initThwd(map);
		String status = (String) resultInfo.get("result");
		String msg = (String) resultInfo.get("msg");
		
		log.info("[" + loginUserId + "]\t" + msg);
		jsonObj.put("status", status);
		jsonObj.put("msg", msg);
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}	
}
