package com.lotto.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.lotto.spring.domain.dto.ServiceApplyDto;
import com.lotto.spring.domain.dto.ServiceInfoDto;
import com.lotto.spring.service.InfoService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class InfoController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private InfoService infoService;
	
	private InfoController() {
		super();
	}

	/**
	 * 서비스 화면 호출
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
	@RequestMapping("/info/service")
	public String board(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "info/ServiceMain");
			modelMap.addAttribute(PLUGIN_PAGE, "info/plugins/ServiceMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 서비스 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/info/serviceajax")
	public String boardajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "info/ServiceMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/info/serviceplugin")
	public String boardplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "info/plugins/ServiceMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스 신청하기
	 * 2020.03.07
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws IOException
	 */
	@RequestMapping("/info/applyService")
	public void applyService(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceApplyDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 서비스 신청하기");
			
			// TODO 서비스종류를 선택하여 설정되도록 개선해야함.
			if (dto.getApply_type() == 1) {
				dto.setSvc_cd("SVC_B2C_P1");
			} else if (dto.getApply_type() == 2) {
				dto.setSvc_cd("SVC_B2C_U1");
			}
			
			dto.setUser_no(loginUserNo);
			boolean result = infoService.applyService(dto);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "신청 되었습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 서비스 신청목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/info/getServiceApplyList")
	public void getServiceList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceApplyDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
  		}
  		
		// 로그인 아이디
		int loginUserNo = userInfo.getUser_no();
		log.info("[" + loginUserNo + "][C] 서비스 신청목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		dto.setUser_no(loginUserNo);
		
		List<ServiceApplyDto> serviceApplyList = infoService.getServiceApplyList(dto);
		int serviceApplyListCnt = infoService.getServiceApplyListCnt(dto);

		int total_pages = 0;
		if( serviceApplyListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)serviceApplyListCnt/Double.parseDouble(dto.getRows()));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", serviceApplyList.size());
		json.put("total", total_pages);
		json.put("page", dto.getPage());
		json.put("records", serviceApplyListCnt);
		json.put("rows", serviceApplyList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
}
