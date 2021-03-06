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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.common.LottoUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.NumberStatDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class PatternController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	private PatternController() {
		super();
	}

	/**
	 * 예상패턴 화면 호출
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
	@RequestMapping("/pattern/exptptrn")
	public String exptptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/ExptPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/ExptPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 예상패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/exptptrnajax")
	public String exptptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/ExptPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 예상패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/exptptrnplugin")
	public String exptptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/ExptPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 홀짝패턴 화면 호출
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
	@RequestMapping("/pattern/oddevenptrn")
	public String oddevenptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/OddEvenPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/OddEvenPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 홀짝패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/oddevenptrnajax")
	public String oddevenptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/OddEvenPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 홀짝패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/oddevenptrnplugin")
	public String oddevenptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/OddEvenPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 홀짝패턴 데이터 조회
	 * 2020.02.29
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/oddevenptrndatas")
	public void oddevenptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 홀짝패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataDto> list = new ArrayList<WinDataDto>();
			String[] ratioTitle = LottoUtil.getRatioTitle();
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				for (int j = 0; j < ratioTitle.length; j++) {
					if (ratioTitle[j].equals(dataDto.getOdd_even())) {
						dataDto.setyAxis(j+1);
						break;
					}
				}
				list.add(dataDto);
			}
			
			jsonObj.put("datas", list);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 저고패턴 화면 호출
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
	@RequestMapping("/pattern/lowhighptrn")
	public String lowhighptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/LowHighPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/LowHighPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 저고패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/lowhighptrnajax")
	public String lowhighptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/LowHighPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 저고패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/lowhighptrnplugin")
	public String lowhighptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/LowHighPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 저고패턴 데이터 조회
	 * 2020.03.01
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/lowhighptrndatas")
	public void lowhighptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 저고패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataDto> list = new ArrayList<WinDataDto>();
			String[] ratioTitle = LottoUtil.getRatioTitle();
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				for (int j = 0; j < ratioTitle.length; j++) {
					if (ratioTitle[j].equals(dataDto.getLow_high())) {
						dataDto.setyAxis(j+1);
						break;
					}
				}
				list.add(dataDto);
			}
			
			jsonObj.put("datas", list);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 총합패턴 화면 호출
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
	@RequestMapping("/pattern/totalptrn")
	public String totalptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/TotalPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/TotalPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 총합패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/totalptrnajax")
	public String totalptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/TotalPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 총합패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/totalptrnplugin")
	public String totalptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/TotalPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 총합패턴 데이터 조회
	 * 2020.03.01
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/totalptrndatas")
	public void totalptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 총합패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataDto> list = new ArrayList<WinDataDto>();
			
			int minValue = 0;
			int maxValue = 0;
			int stepSize = 20;
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				
				if (minValue == 0 || minValue > dataDto.getTotal()) {
					minValue = dataDto.getTotal();
				}
				
				if (maxValue < dataDto.getTotal()) {
					maxValue = dataDto.getTotal();
				}
				
				list.add(dataDto);
			}
			
			jsonObj.put("datas", list);
			jsonObj.put("minValue", minValue - (minValue < stepSize ? 0 : stepSize));
			jsonObj.put("maxValue", maxValue + stepSize);
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 끝수합패턴 화면 호출
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
	@RequestMapping("/pattern/endnumptrn")
	public String endnumptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request); 
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/EndNumPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/EndNumPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 끝수합패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/endnumptrnajax")
	public String endnumptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/EndNumPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 끝수합패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/endnumptrnplugin")
	public String endnumptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/EndNumPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 끝수합패턴 데이터 조회
	 * 2020.03.01
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/endnumptrndatas")
	public void endnumptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 끝수합패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataDto> list = new ArrayList<WinDataDto>();
			
			int minValue = 0;
			int maxValue = 0;
			int stepSize = 10;
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				
				if (minValue == 0 || minValue > dataDto.getSum_end_num()) {
					minValue = dataDto.getSum_end_num();
				}
				
				if (maxValue < dataDto.getSum_end_num()) {
					maxValue = dataDto.getSum_end_num();
				}
				
				list.add(dataDto);
			}
			
			jsonObj.put("datas", list);
			jsonObj.put("minValue", minValue - (minValue < stepSize ? 0 : stepSize));
			jsonObj.put("maxValue", maxValue + stepSize);
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * AC패턴 화면 호출
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
	@RequestMapping("/pattern/acptrn")
	public String acptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] AC패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/AcPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/AcPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * AC패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/acptrnajax")
	public String acptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] AC패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/AcPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * AC패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/acptrnplugin")
	public String acptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] AC패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/AcPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * AC패턴 데이터 조회
	 * 2020.03.01
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/acptrndatas")
	public void acptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] AC패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataDto> list = new ArrayList<WinDataDto>();
			
			int minValue = 0;
			int maxValue = 0;
			int stepSize = 2;
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				
				if (minValue == 0 || minValue > dataDto.getAc()) {
					minValue = dataDto.getAc();
				}
				
				if (maxValue < dataDto.getAc()) {
					maxValue = dataDto.getAc();
				}
				
				list.add(dataDto);
			}
			
			jsonObj.put("datas", list);
			jsonObj.put("minValue", minValue - (minValue < stepSize ? 0 : stepSize));
			jsonObj.put("maxValue", maxValue + stepSize);
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 회차합패턴 화면 호출
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
	@RequestMapping("/pattern/countsumptrn")
	public String countsumptrn(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회차합패턴 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/CountSumPtrnMain");
			modelMap.addAttribute(PLUGIN_PAGE, "pattern/plugins/CountSumPtrnMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 회차합패턴 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/countsumptrnajax")
	public String countsumptrnajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회차합패턴 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/CountSumPtrnMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 회차합패턴 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/countsumptrnplugin")
	public String countsumptrnplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회차합패턴 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "pattern/plugins/CountSumPtrnMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 회차합패턴 데이터 조회
	 * 2020.03.01
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pattern/countsumptrndatas")
	public void countsumptrndatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회차합패턴 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			List<WinDataAnlyDto> list = new ArrayList<WinDataAnlyDto>();
			
			int minValue = 0;
			int maxValue = 0;
			int stepSize = 2;
			
			for (int i = winDataList.size() -1 - 10 ; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				CountSumDto countSumData = sysmngService.getCountSumInfo(dataDto);
				
				if (minValue == 0 || minValue > countSumData.getCount_sum()) {
					minValue = countSumData.getCount_sum();
				}
				
				if (maxValue < countSumData.getCount_sum()) {
					maxValue = countSumData.getCount_sum();
				}
				
				WinDataAnlyDto winDataAnlyDto = new WinDataAnlyDto();
				winDataAnlyDto.setWin_count(dataDto.getWin_count());
				winDataAnlyDto.setCount_sum(countSumData.getCount_sum());
				
				list.add(winDataAnlyDto);
			}
			
			jsonObj.put("datas", list);
			jsonObj.put("minValue", minValue - (minValue < stepSize ? 0 : stepSize));
			jsonObj.put("maxValue", maxValue + stepSize);
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
}
