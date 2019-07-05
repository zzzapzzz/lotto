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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.common.LottoUtil;
import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.MyLottoService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class MyLottoController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private MyLottoService myLottoService;
	
	private MyLottoController() {
		super();
	}

	/**
	 * MY로또 화면 호출
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
	@RequestMapping("/mylotto/mylotto")
	public String mylotto(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/MyLottoMain");
			modelMap.addAttribute(PLUGIN_PAGE, "mylotto/plugins/MyLottoMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * MY로또 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/mylottoajax")
	public String mylottoajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/MyLottoMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * MY로또 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/mylottoplugin")
	public String mylottoplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/plugins/MyLottoMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * MY로또저장번호 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/mylotto/saveNumList")
	public void saveNumList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String exCount       = WebUtil.replaceParam(request.getParameter("ex_count"), "");
		     			
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String rows = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "usr_id");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");	
		
		JSONObject json = new JSONObject();
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] MY로또저장번호 목록 조회");
			
			Map map = new HashMap();
			map.put("ex_count", exCount);
			map.put("user_no", userInfo.getUser_no());
			map.put("startNum", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(rows))));
			map.put("endNum", Integer.toString(Integer.parseInt(page)*Integer.parseInt(rows)));
			map.put("sidx", sidx);
			map.put("sord", sord);
			
			int myLottoSaveNumListCnt = myLottoService.getSaveNumListCnt(map);
			ArrayList<MyLottoSaveNumDto> myLottoSaveNumList = new ArrayList<MyLottoSaveNumDto>();
			
			int total_pages = 0;
			if( myLottoSaveNumListCnt > 0 ) {
				total_pages = (int) Math.ceil((double)myLottoSaveNumListCnt/Double.parseDouble(rows));
				myLottoSaveNumList = myLottoService.getSaveNumList(map);
			} else { 
				total_pages = 0; 
			}  
			
			//토탈 값 구하기 끝
			// Content Page - File which will included in tiles definition
			
			
//		JSONArray jsonArr = JSONArray.fromObject(userList);
			
			
			json.put("cnt", myLottoSaveNumList.size());
			json.put("total", total_pages);
			json.put("page", page);
			json.put("records", myLottoSaveNumListCnt);
			json.put("rows", myLottoSaveNumList);		
			json.put("status", "success");		
		} else {
			json.put("status", "usernotfound");
			json.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");		
		}
		writeJSON(response, json); 
	} 
	
	/**
	 * MY로또저장번호 일반등록 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/writeMyDataajax")
	public String writeMyDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또저장번호 일반등록 화면 호출(ajax)");
			
			String exCount = WebUtil.replaceParam(request.getParameter("ex_count"), "");
			modelMap.addAttribute("ex_count", exCount);
			
			setModelMap(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/MyDataInsert");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * MY로또저장번호 일반등록 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/writeMyDataplugin")
	public String writeMyDataplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또저장번호 일반등록 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/plugins/MyDataInsert_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * MY로또저장번호 일반등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/mylotto/insertMyData")
	public void insertWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 일반등록");
			
			Map map = new HashMap();
			List list = new ArrayList();
			
			dto.setUser_no(loginUserNo);
			
			list.add(dto);
			map.put("list", list);
			
			myLottoService.insertMyData(map);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "등록했습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * MY로또저장번호 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/mylotto/deleteMyData")
	public void deleteMyData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 삭제");
			
			boolean result = myLottoService.deleteMyData(dto);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "삭제했습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 사용자정보 화면 호출
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
	@RequestMapping("/mylotto/profile")
	public String profile(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자정보 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/ProfileMain");
			modelMap.addAttribute(PLUGIN_PAGE, "mylotto/plugins/ProfileMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 사용자정보 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/profileajax")
	public String profileajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자정보 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/ProfileMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 사용자정보 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/profileplugin")
	public String profileplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자정보 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/plugins/ProfileMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스정보 화면 호출
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
	@RequestMapping("/mylotto/service")
	public String service(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/ServiceMain");
			modelMap.addAttribute(PLUGIN_PAGE, "mylotto/plugins/ServiceMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 서비스정보 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/serviceajax")
	public String serviceajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/ServiceMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스정보 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/serviceplugin")
	public String serviceplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/plugins/ServiceMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
}
