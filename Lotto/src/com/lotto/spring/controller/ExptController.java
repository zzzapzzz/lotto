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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.SysmngService;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class ExptController extends DefaultSMController {
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private ExptController() {
		super();
	}

	/**
	 * 예상번호 화면 호출
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
	@RequestMapping("/expt/expt")
	public String expt(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "expt/ExptMain");
			modelMap.addAttribute(PLUGIN_PAGE, "expt/plugins/ExptMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 예상번호 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/expt/exptajax")
	public String exptajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			// 최근 당첨번호
			WinDataDto lastData = winDataList.get(0);
			modelMap.addAttribute("ex_count", lastData.getWin_count()+1);
						
			modelMap.addAttribute(CONTENT_PAGE, "expt/ExptMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 예상번호 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/expt/exptplugin")
	public String exptplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "expt/plugins/ExptMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * SM예상번호 화면 호출
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
	@RequestMapping("/expt/smexpt")
	public String smexpt(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] SM예상번호 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "expt/SmExptMain");
			modelMap.addAttribute(PLUGIN_PAGE, "expt/plugins/SmExptMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * SM예상번호 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/expt/smexptajax")
	public String smexptajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] SM예상번호 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "expt/SmExptMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * SM예상번호 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/expt/smexptplugin")
	public String smexptplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] SM예상번호 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "expt/plugins/SmExptMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
}
