package com.lotto.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.MenuInfoDto;
import com.lotto.spring.domain.dto.TaskInfoDto;
import com.lotto.spring.domain.dto.UserInfoDto;
import com.lotto.spring.service.CommonService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class SysmngController extends DefaultSMController {
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private SysmngController() {
		super();
	}

	/**
	 * 회원관리 화면 호출
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
	@RequestMapping("/sysmng/usermng")
	public String usermng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/UserMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/UserMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 회원관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/usermngajax")
	public String usermngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/UserMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 회원관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/usermngplugin")
	public String usermngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/UserMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 회원정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/userList")
	public void userList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String searchKey       = WebUtil.replaceParam(request.getParameter("searchKey"), "");
		String authTask       = WebUtil.replaceParam(request.getParameter("authTask"), "");
		     			
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String limit = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "usr_id");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");	
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			sord = sord.toUpperCase();
  		}
  		
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 회원정보 목록 조회");
		
		Map map = new HashMap();
		map.put("search_key", searchKey);
		map.put("auth_task", authTask);
		map.put("page", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(limit))));
		map.put("scale", Integer.toString(Integer.parseInt(page)*Integer.parseInt(limit)));
		map.put("sidx", sidx);
		map.put("sord", sord);
		
//		ArrayList<CaseInsensitiveMap> userList = sysmngService.getUserList(map);
		ArrayList<UserInfoDto> userList = sysmngService.getUserList2(map);
		int userListCnt = sysmngService.getUserListCnt(map);

		int total_pages = 0;
		if( userListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)userListCnt/Double.parseDouble(limit));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", userList.size());
		json.put("total", total_pages);
		json.put("page", page);
		json.put("records", userListCnt);
		json.put("rows", userList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	} 
	
	/**
	 * 회원정보 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/createUserInfo")
	public void createUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			String accessip = request.getRemoteHost();
			log.info("[" + loginUserNo + "][C] 회원정보 등록");
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 등록
			log.info("[" + loginUserNo + "] > 회원정보 등록");
			CaseInsensitiveMap resultInfo = sysmngService.createUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회원정보 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/modifyUserInfo")
	public void modifyUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회원정보 수정");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 수정
			log.info("[" + loginUserNo + "] > 회원정보 수정");
			CaseInsensitiveMap resultInfo = sysmngService.modifyUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회원정보 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/deleteUserInfo")
	public void deleteUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회원정보 삭제");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 삭제
			log.info("[" + loginUserNo + "] > 회원정보 삭제");
			CaseInsensitiveMap resultInfo = sysmngService.deleteUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 당첨번호관리 화면 호출
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
	@RequestMapping("/sysmng/windatamng")
	public String windatamng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/WinDataMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 당첨번호관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/windatamngajax")
	public String windatamngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/windatamngplugin")
	public String windatamngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/WinDataMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 예상번호관리 화면 호출
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
	@RequestMapping("/sysmng/exptdatamng")
	public String exptdatamng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExptDataMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/ExptDataMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 예상번호관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/exptdatamngajax")
	public String exptdatamngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExptDataMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 예상번호관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/exptdatamngplugin")
	public String exptdatamngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ExptDataMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스관리 화면 호출
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
	@RequestMapping("/sysmng/servicemng")
	public String servicemng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/ServiceMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 서비스관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/servicemngajax")
	public String servicemngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/servicemngplugin")
	public String servicemngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ServiceMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 프로모션관리 화면 호출
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
	@RequestMapping("/sysmng/promotionmng")
	public String promotionmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/PromotionMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/PromotionMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 프로모션관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/promotionmngajax")
	public String promotionmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/PromotionMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 프로모션관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/promotionmngplugin")
	public String promotionmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/PromotionMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 사용자요청관리 화면 호출
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
	@RequestMapping("/sysmng/requestmng")
	public String requestmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/RequestMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/RequestMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 사용자요청관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/requestmngajax")
	public String requestmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/RequestMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 사용자요청관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/requestmngplugin")
	public String requestmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/RequestMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 업무권한관리 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmng")
	public String authtaskmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthTaskMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/AuthTaskMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 업무권한관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmngajax")
	public String authtaskmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthTaskMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 업무권한관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmngplugin")
	public String authtaskmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/AuthTaskMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 권한코드 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/authCodeList")
	public void authCodeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String searchKey       = WebUtil.replaceParam(request.getParameter("searchKey"), "");
		     			
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String limit = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "auth_cd");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");	
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			sord = sord.toUpperCase();
  		}
  		
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 권한코드 목록 조회");
		
		Map map = new HashMap();
		map.put("search_key", searchKey);
		map.put("page", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(limit))));
		map.put("scale", Integer.toString(Integer.parseInt(page)*Integer.parseInt(limit)));
		map.put("sidx", sidx);
		map.put("sord", sord);
		
		ArrayList<CaseInsensitiveMap> authTaskList = sysmngService.getAuthCodeList(map);
		int authTaskListCnt = sysmngService.getAuthCodeListCnt(map);

		int total_pages = 0;
		if( authTaskListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)authTaskListCnt/Double.parseDouble(limit));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		

		json.put("cnt", authTaskList.size());
		json.put("total", total_pages);
		json.put("page", page);
		json.put("records", authTaskListCnt);
		json.put("rows", authTaskList);		
		json.put("status", "success");
		writeJSON(response, json); 
	} 
	
	/**
	 * 업무권한정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/authTaskInfoList")
	public void authTaskInfoList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
		     			
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 업무권한정보 목록 조회");
		
		Map map = new HashMap();
		map.put("auth_cd", authCd);

		ArrayList<TaskInfoDto> authTaskInfoList = sysmngService.getAuthTaskInfoList(map);

		//Tree Data 설정
//		JSONArray treeData = sysmngService.getAuthTaskInfoTree(authTaskInfoList, 0, authTaskInfoListCnt, 1);
		JSONArray treeData = null;
		
		int authTaskInfoListCnt = 0;
		if (authTaskInfoList != null  && authTaskInfoList.size() > 0) {
			authTaskInfoListCnt = authTaskInfoList.size();
			treeData = sysmngService.getAuthTaskInfoTree(authTaskInfoList, 0, authTaskInfoListCnt, 1);
		}
		
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("id", "root");
		jsonRtn.put("text", "전체");
		jsonRtn.put("value", "전체");
		jsonRtn.put("showcheck", true);
		jsonRtn.put("complete", true);
		jsonRtn.put("isexpand", true);
		jsonRtn.put("hasChildren", true);
		if (treeData != null && treeData.size() > 0) {
			jsonRtn.put("ChildNodes", treeData);
		}
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONArray jsonRtnArray = new JSONArray();
		jsonRtnArray.add(jsonRtn);
		
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		
		
		log.info("[" + loginUserId + "]> treedata : " + jsonRtnArray.toString());
		
		writeJsonArray(response, jsonRtnArray);
		
		
	}
	
	/**
	 * 권한코드 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/createAuthCode")
	public void createAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 등록");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);
			
			//권한코드 중복체크
			log.info("[" + loginUserId + "] > 권한코드 중복체크");
			boolean result = sysmngService.dupCheckAuthCode(map);
			if (result) {
				log.info("[" + loginUserId + "]\t이미 등록된 권한코드입니다.");
				jsonObj.put("status", "isDuplicate");
				jsonObj.put("msg", "이미 등록된 권한코드입니다.");
			} else {
				//권한코드 등록
				log.info("[" + loginUserId + "] > 권한코드 등록");
				result = sysmngService.createAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t등록 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "등록 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t등록에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "등록에 실패했습니다.");
				}
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 권한코드 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/modifyAuthCode")
	public void modifyAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 수정");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
			String bfAuthCd       = WebUtil.replaceParam(request.getParameter("bf_auth_cd"), "");
			String bfAuthNm       = WebUtil.replaceParam(request.getParameter("bf_auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);
			map.put("bf_auth_cd", bfAuthCd);
			map.put("bf_auth_nm", bfAuthNm);
			
			boolean result = false;
			
			if (bfAuthCd.equals(authCd)) {
				//권한코드 수정
				log.info("[" + loginUserId + "] > 권한코드 수정");
				result = sysmngService.modifyAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t수정 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "수정 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t수정에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "수정에 실패했습니다.");
				}
			} else {
				jsonObj.put("status", "notequalauthcode");
				jsonObj.put("msg", "동일한 권한코드가 아닙니다.");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 권한코드 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/deleteAuthCode")
	public void deleteAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 삭제");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);

			//기본권한 삭제 불가 처리
			if (!"normal".equals(authCd)) {
				//권한코드 삭제
				log.info("[" + loginUserId + "] > 권한코드 삭제");
				boolean result = sysmngService.deleteAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t삭제 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "삭제 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t삭제에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "삭제에 실패했습니다.");
				}
			} else {
				jsonObj.put("status", "isbaseauth");
				jsonObj.put("msg", "기본권한은 삭제할 수 없습니다.");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 업무권한 매핑정보 저장
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/saveTaskAuthInfo")
	public void saveTaskAuthInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 업무권한 매핑정보 저장");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String tmpAuthCd       = WebUtil.replaceParam(request.getParameter("tmp_auth_cd"), "");
			tmpAuthCd = tmpAuthCd.toLowerCase();
			
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			
			//기존 업무권한 매핑정보 삭제
			log.info("[" + loginUserId + "] > 기존 업무권한 매핑정보 삭제");
			boolean result = sysmngService.deleteTaskAuthInfo(map);
			
			//권한설정 정보 parsing
			List insertDataList = new ArrayList();
			String[] datas = tmpAuthCd.split("@");
			for (int i = 0; i < datas.length; i++) {
				
				Map dataMap = new HashMap();
				dataMap.put("auth_cd", authCd);
				
				if (datas[i].indexOf("_") == -1) {
					//_ 가 없는 경우, 상위권한코드임.
					//하위권한코드가 존재할 경우 skip
					//하위권한코드가 존재하지 않으면, 자체 권한코드임.
					if (tmpAuthCd.indexOf(datas[i]+"_") > -1) {
						System.out.println(datas[i] + " : SKIP");
						continue;
					} else {
						dataMap.put("task_1_cd", datas[i]);
					}
				} else {
					
					String[] menus = datas[i].split("_");
					for (int j = 0; j < menus.length; j++) {
						System.out.println( menus[j]);
						if (j == 0) {
							dataMap.put("task_1_cd", menus[j]);
						} else {
							dataMap.put("task_2_cd", menus[j]);
						}
					}
					
				}
				
				insertDataList.add(dataMap);
			}
			map.put("list", insertDataList);
			
			
			//업무권한 매핑정보 저장
			log.info("[" + loginUserId + "] > 업무권한 매핑정보 저장");
			result = sysmngService.saveTaskAuthInfo(map);
			if (result) {
				log.info("[" + loginUserId + "]\t저장 되었습니다.");
				jsonObj.put("status", "success");
				jsonObj.put("msg", "저장 되었습니다.");
			} else {
				log.info("[" + loginUserId + "]\t저장에 실패했습니다.");
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "저장에 실패했습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 메뉴권한관리 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumng")
	public String authmenumng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthMenuMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/AuthMenuMain_Plugin");
			modelMap.addAttribute("isAjax", "N");

			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 메뉴권한관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumngajax")
	public String authmenumngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthMenuMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 메뉴권한관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumngplugin")
	public String authmenumngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/AuthMenuMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 메뉴권한정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/authMenuInfoList")
	public void authMenuInfoList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
		     			
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 메뉴권한정보 목록 조회");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("auth_cd", authCd);

		ArrayList<MenuInfoDto> authMenuInfoList = sysmngService.getAuthMenuInfoList(map);

		int authMenuInfoListCnt = 0;
		if (authMenuInfoList != null  && authMenuInfoList.size() > 0) {
			authMenuInfoListCnt = authMenuInfoList.size();
		}
		
		/*
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("status", "success");
		jsonRtn.put("AuthTaskInfoList", authTaskInfoList);
		
		writeJSON(response, jsonRtn);
		*/
		
		//Tree Data 설정
		JSONArray treeData = sysmngService.getAuthMenuInfoTree(authMenuInfoList, 0, authMenuInfoListCnt, 1);
		
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("id", "root");
		jsonRtn.put("text", "전체");
		jsonRtn.put("value", "root");
		jsonRtn.put("showcheck", true);
		jsonRtn.put("complete", true);
		jsonRtn.put("isexpand", true);
		jsonRtn.put("hasChildren", true);
		if (treeData != null && treeData.size() > 0) {
			jsonRtn.put("ChildNodes", treeData);
		}
		int checkCnt = sysmngService.getAuthMenuInfoCheckCnt(authMenuInfoList, 0, authMenuInfoListCnt);
		jsonRtn.put("checkstate", ((checkCnt == authMenuInfoListCnt) ? 1 : 0));
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONArray jsonRtnArray = new JSONArray();
		jsonRtnArray.add(jsonRtn);
		
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		
		
		log.info("[" + loginUserId + "]> treedata : " + jsonRtnArray.toString());
		
		writeJsonArray(response, jsonRtnArray);
		
		
	}
	
	/**
	 * 메뉴권한 매핑정보 저장
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/saveMenuAuthInfo")
	public void saveMenuAuthInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 메뉴권한 매핑정보 저장");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String tmpMenuId       = WebUtil.replaceParam(request.getParameter("tmp_menu_id"), "");
			tmpMenuId = tmpMenuId.toLowerCase();
			
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			
			//기존 업무권한 매핑정보 삭제
			log.info("[" + loginUserId + "] > 기존 메뉴권한 매핑정보 삭제");
			boolean result = sysmngService.deleteMenuAuthInfo(map);
			
			//권한설정 정보 parsing
			List insertDataList = new ArrayList();
			String[] datas = tmpMenuId.split("@");
			for (int i = 0; i < datas.length; i++) {
				
				//root 예외처리
				if ("root".equals(datas[i])) {
					continue;
				}
				
				Map dataMap = new HashMap();
				dataMap.put("auth_cd", authCd);
				dataMap.put("menu_id", datas[i]);
				
				insertDataList.add(dataMap);
			}
			map.put("list", insertDataList);
			
			
			//업무권한 매핑정보 저장
			log.info("[" + loginUserId + "] > 메뉴권한 매핑정보 저장");
			result = sysmngService.saveMenuAuthInfo(map);
			if (result) {
				log.info("[" + loginUserId + "]\t저장 되었습니다.");
				jsonObj.put("status", "success");
				jsonObj.put("msg", "저장 되었습니다.");
			} else {
				log.info("[" + loginUserId + "]\t저장에 실패했습니다.");
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "저장에 실패했습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
}
