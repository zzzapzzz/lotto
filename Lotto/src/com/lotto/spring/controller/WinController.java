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

import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.AcDto;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.EndNumDto;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.LowHighDto;
import com.lotto.spring.domain.dto.MCNumDto;
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.domain.dto.ZeroRangeDto;
import com.lotto.spring.service.LottoDataService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class WinController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private WinController() {
		super();
	}

	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private LottoDataService lottoDataService;
	
	/**
	 * 당첨번호 화면 호출
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
	@RequestMapping("/win/win")
	public String win(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			String loginUserId = null;
			if (userInfo.getUser_no() != 0) {
				loginUserId = String.valueOf(userInfo.getUser_no());
			} else {
				loginUserId = String.valueOf(userInfo.getAccess_no());
			}
			log.info("["+loginUserId+"][C] 당첨번호 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "win/WinMain");
			modelMap.addAttribute(PLUGIN_PAGE, "win/plugins/WinMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";
		}
	}
	
	/**
	 * 당첨번호 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/win/winajax")
	public String winajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			String loginUserId = null;
			if (userInfo.getUser_no() != 0) {
				loginUserId = String.valueOf(userInfo.getUser_no());
			} else {
				loginUserId = String.valueOf(userInfo.getAccess_no());
			}
			log.info("["+loginUserId+"][C] 당첨번호 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "win/WinMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/win/winplugin")
	public String winplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			String loginUserId = null;
			if (userInfo.getUser_no() != 0) {
				loginUserId = String.valueOf(userInfo.getUser_no());
			} else {
				loginUserId = String.valueOf(userInfo.getAccess_no());
			}
			log.info("["+loginUserId+"][C] 당첨번호 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "win/plugins/WinMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 당첨번호 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/win/getWinData")
	public void getWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			String loginUserId = null;
			if (userInfo.getUser_no() != 0) {
				loginUserId = String.valueOf(userInfo.getUser_no());
			} else {
				loginUserId = String.valueOf(userInfo.getAccess_no());
			}
			log.info("["+loginUserId+"][C] 당첨번호 조회");
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
						
			if (winDataList != null && winDataList.size() > 0) {
				// 최근 당첨번호정보 조회
				WinDataDto winData = winDataList.get(0);
				jsonObj.put("data", winData);
				
				// 회차합 조회
				CountSumDto countSumInfo = sysmngService.getCountSumInfo(dto);
				jsonObj.put("countSumInfo", countSumInfo);
				
				// 궁합수 조회
				List<MCNumDto> mcNumList = sysmngService.getMcNumList(dto);
				String mcMatchedData = lottoDataService.getMcMatchedData(winData, mcNumList);
				jsonObj.put("mcMatchedData", mcMatchedData);
				
				// 미출현번호대 조회
				ZeroRangeDto zeroRangeInfo = sysmngService.getZeroRangeInfo(dto);
				jsonObj.put("zeroRange", zeroRangeInfo.getZero_range());
				
				// 저고비율 정보 설정
				List<LowHighDto> lowHighDataList = sysmngService.getLowHighDataList(dto);
				jsonObj.put("lowhigh_msg", lottoDataService.getLowHighMsg(winData, lowHighDataList));
				
				// 홀짝비율 정보 설정
				List<OddEvenDto> oddEvenDataList = sysmngService.getOddEvenDataList(dto);
				jsonObj.put("oddeven_msg", lottoDataService.getOddEvenMsg(winData, oddEvenDataList));
				
				// 총합 정보 설정
				TotalDto totalInfo = sysmngService.getTotalInfo(winData);
				jsonObj.put("total_range", totalInfo.getTotal_range());
				jsonObj.put("total_msg", lottoDataService.getTotalMsg(winData, totalInfo));
				
				// 끝수합 정보 설정
				EndNumDto endNumInfo = sysmngService.getEndNumInfo(winData);
				jsonObj.put("endnum_range", endNumInfo.getEndnum_range());
				jsonObj.put("endnum_msg", lottoDataService.getEndnumMsg(winData, endNumInfo));
				
				// AC 정보 설정
				AcDto acInfo = sysmngService.getAcInfo(winData);
				jsonObj.put("ac_range", acInfo.getAc_range());
				jsonObj.put("ac_msg", lottoDataService.getAcMsg(winData, acInfo));
				
				// 제외수 설정
				ExDataDto lastWinData = new ExDataDto();
				lastWinData.setEx_count(winData.getWin_count());	
				// 지난 회차의 제외수 정보 조회
				ExcludeDto excludeInfo = sysmngService.getExcludeInfo(lastWinData);
				jsonObj.put("exclude_msg", lottoDataService.getExcludeMsg(winData, excludeInfo));
				
				// 출현번호/미출현번호 설정
				Map<String, String> containInfo = lottoDataService.getContainInfo(winDataList);
				jsonObj.put("contain_msg", containInfo.get("containMsg"));
				jsonObj.put("not_contain_msg", containInfo.get("notContainMsg"));
				
				// 궁합수 설정
				jsonObj.put("mc_matched_msg", lottoDataService.getMcMatchedMsg(winData, mcNumList));
				
				// 미출현 번호대 구간 설정
				jsonObj.put("zero_range_msg", lottoDataService.getZeroRangeMsg(zeroRangeInfo));
				
				
				
			} else {
				jsonObj.put("status", "datanotfound");
				jsonObj.put("msg", "당첨번호가 없습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회차합 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/win/getCountSumInfo")
	public void getCountSumInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			String loginUserId = null;
			if (userInfo.getUser_no() != 0) {
				loginUserId = String.valueOf(userInfo.getUser_no());
			} else {
				loginUserId = String.valueOf(userInfo.getAccess_no());
			}
			log.info("["+loginUserId+"][C] 회차합 조회");
			
			CountSumDto countSumData = sysmngService.getCountSumInfo(dto);
			
			jsonObj.put("data", countSumData);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
}
