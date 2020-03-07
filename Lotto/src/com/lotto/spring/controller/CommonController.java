package com.lotto.spring.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.service.CommonService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class CommonController extends DefaultSMController {
	
	@Autowired(required = true)
    private CommonService commonService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private CommonController() {
		super();
	}

	/**
	 * 코드 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/common/getCodeList")
	public void getCodeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			// 로그인 아이디
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 코드 목록 조회");
			
			String codeType       = WebUtil.replaceParam(request.getParameter("code_type"), "");
			
			Map map = new HashMap();
			map.put("code_type", codeType);
			
			ArrayList<CaseInsensitiveMap> codeList = commonService.getCodeList(map);
			
			jsonObj.put("status", "success");	
			jsonObj.put("rows", codeList);	
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 최근 로그 조회
	 * 2018.04.15
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/common/getLastCodeInfo")
	public void getLastCodeInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			// 로그인 아이디
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 최근 로그 조회");
			
			String logType       = WebUtil.replaceParam(request.getParameter("log_type"), "");
			
			Map map = new HashMap();
			map.put("log_type", logType);
			
			CaseInsensitiveMap loginfo = commonService.getLastLog(map);
			
			if (loginfo != null) {
				jsonObj.put("status", "success");	
				jsonObj.put("data", loginfo);	
			} else {
				jsonObj.put("status", "fail");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * Email 보내기
	 * 2020.03.04
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws SQLException
	 */
	@RequestMapping("/common/sendEmail")
	public void sendEmail(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			// 로그인 아이디
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] Email 보내기");
			
			String logType       = WebUtil.replaceParam(request.getParameter("log_type"), "");
			
			Map map = new HashMap();
			map.put("log_type", logType);
			
			CaseInsensitiveMap loginfo = commonService.getLastLog(map);
			
			if (loginfo != null) {
				jsonObj.put("status", "success");	
				jsonObj.put("data", loginfo);	
			} else {
				jsonObj.put("status", "fail");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
}
