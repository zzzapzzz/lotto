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
import com.lotto.spring.domain.dto.ColorStatDto;
import com.lotto.spring.domain.dto.EndNumDto;
import com.lotto.spring.domain.dto.LowHighDto;
import com.lotto.spring.domain.dto.NumberStatDto;
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.RangeStatDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class StatisticController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	private StatisticController() {
		super();
	}

	/**
	 * 번호별통계 화면 호출
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
	@RequestMapping("/statistic/numberstat")
	public String numberstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 번호별통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/NumberStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/NumberStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 번호별통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/numberstatajax")
	public String numberstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 번호별통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/NumberStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 번호별통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/numberstatplugin")
	public String numberstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 번호별통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/NumberStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 번호별통계 데이터 조회
	 * 2020.02.08
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/numberstatdatas")
	public void numberstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 번호별통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			Map<Integer, NumberStatDto> numberMap = new HashMap<Integer, NumberStatDto>();
			List<NumberStatDto> numberList = new ArrayList<NumberStatDto>();
					
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(dataDto);
				
				for (int j = 0; j < numbers.length; j++) {
					int number = numbers[j];
					if (numberMap.containsKey(number)) {
						// 존재하면 건수 누적
						NumberStatDto numberDto = numberMap.get(number);
						numberDto.setAppearCnt(numberDto.getAppearCnt()+1);
						numberMap.put(number, numberDto);
					} else {
						// 없으면 신규 생성
						NumberStatDto numberDto = new NumberStatDto();
						numberDto.setNumber(number);
						numberDto.setAppearCnt(1);
						numberMap.put(number, numberDto);
					}
				}
			}
			
			// 데이터 설정
			for (int i = 1; i <= 45; i++) {
				numberList.add(numberMap.get(i));
				
				if (maxValue < numberMap.get(i).getAppearCnt()) {
					maxValue = numberMap.get(i).getAppearCnt();
				}
			}
			
			// Chart Max Range 설정
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", numberList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 색상별통계 화면 호출
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
	@RequestMapping("/statistic/colorstat")
	public String colorstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 색상별통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/ColorStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/ColorStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 색상별통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/colorstatajax")
	public String colorstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 색상별통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/ColorStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 색상별통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/colorstatplugin")
	public String colorstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 색상별통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/ColorStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 번호별통계 데이터 조회
	 * 2020.02.09
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/colorstatdatas")
	public void colorstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 번호별통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<ColorStatDto> colorStatList = new ArrayList<ColorStatDto>();
			
			int[] appearCnt = {0,0,0,0,0};
			int totalCnt = 0;
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(dataDto);
				
				for (int j = 0; j < numbers.length; j++) {
					int number = numbers[j];
					
					if (number <= 10) {
						appearCnt[0] += 1;
					} else if (number <= 20) {
						appearCnt[1] += 1;
					} else if (number <= 30) {
						appearCnt[2] += 1;
					} else if (number <= 40) {
						appearCnt[3] += 1;
					} else {
						appearCnt[4] += 1;
					}
					totalCnt++;
				}
			}
			
			ColorStatDto colorStat1 = new ColorStatDto();
			colorStat1.setRange("1~10");
			colorStat1.setAppearPercent(Math.round(appearCnt[0]*1.0 / totalCnt * 100));
			
			ColorStatDto colorStat2 = new ColorStatDto();
			colorStat2.setRange("11~20");
			colorStat2.setAppearPercent(Math.round(appearCnt[1]*1.0 / totalCnt * 100));
			
			ColorStatDto colorStat3 = new ColorStatDto();
			colorStat3.setRange("21~30");
			colorStat3.setAppearPercent(Math.round(appearCnt[2]*1.0 / totalCnt * 100));
			
			ColorStatDto colorStat4 = new ColorStatDto();
			colorStat4.setRange("31~40");
			colorStat4.setAppearPercent(Math.round(appearCnt[3]*1.0 / totalCnt * 100));
			
			ColorStatDto colorStat5 = new ColorStatDto();
			colorStat5.setRange("41~45");
			colorStat5.setAppearPercent(Math.round(appearCnt[4]*1.0 / totalCnt * 100));
			
			colorStatList.add(colorStat1);
			colorStatList.add(colorStat2);
			colorStatList.add(colorStat3);
			colorStatList.add(colorStat4);
			colorStatList.add(colorStat5);
			
			jsonObj.put("datas", colorStatList);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 구간별통계 화면 호출
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
	@RequestMapping("/statistic/rangestat")
	public String rangestat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 구간별통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/RangeStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/RangeStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 구간별통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/rangestatajax")
	public String rangestatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 구간별통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/RangeStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 구간별통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/rangestatplugin")
	public String rangestatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 구간별통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/RangeStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 구간별통계 데이터 조회
	 * 2020.02.10
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/rangestatdatas")
	public void rangestatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 구간별통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
			
			String[] rangeStat = {"1~10","11~20","21~30","31~40","41~45"};
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<RangeStatDto> rangeStatList = new ArrayList<RangeStatDto>();
			
			int[] appearCnt = {0,0,0,0,0};
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(dataDto);
				
				for (int j = 0; j < numbers.length; j++) {
					int number = numbers[j];
					
					if (number <= 10) {
						appearCnt[0] += 1;
					} else if (number <= 20) {
						appearCnt[1] += 1;
					} else if (number <= 30) {
						appearCnt[2] += 1;
					} else if (number <= 40) {
						appearCnt[3] += 1;
					} else {
						appearCnt[4] += 1;
					}
				}
			}
			
			for (int i = 0; i < rangeStat.length; i++) {
				RangeStatDto rangeStatDto = new RangeStatDto();
				rangeStatDto.setRange(rangeStat[i]);
				rangeStatDto.setAppearCnt(appearCnt[i]);
				rangeStatList.add(rangeStatDto);
				
				if (maxValue < appearCnt[i]) {
					maxValue = appearCnt[i];
				}
			}
			
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", rangeStatList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 미출현번호통계 화면 호출
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
	@RequestMapping("/statistic/notcontstat")
	public String notcontstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 미출현번호통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/NotContStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/NotContStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 미출현번호통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/notcontstatajax")
	public String notcontstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 미출현번호통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/NotContStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 미출현번호통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/notcontstatplugin")
	public String notcontstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 미출현번호통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/NotContStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 미출현번호통계 데이터 조회
	 * 2020.02.10
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/notcontstatdatas")
	public void notcontstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 미출현번호통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			Map<Integer, Integer> containMap = new HashMap(); 
			List<Integer> notContainList = new ArrayList<Integer>();
			
			// 출현번호 설정
			for (int i = 0; i < 10; i++) {
				WinDataDto dataDto = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(dataDto);
				
				for (int j = 0; j < numbers.length; j++) {
					int number = numbers[j];
					
					if (!containMap.containsKey(number)) {
						containMap.put(number, number);
					}
				}
			}
			
			//미출현번호설정
			for (int i = 1; i <= 45; i++) {
				if (!containMap.containsKey(i)) {
					notContainList.add(i);
				}
			}
			
			jsonObj.put("datas", notContainList);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 홀짝비율통계 화면 호출
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
	@RequestMapping("/statistic/oddevenstat")
	public String oddevenstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝비율통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/OddEvenStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/OddEvenStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 홀짝비율통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/oddevenstatajax")
	public String oddevenstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝비율통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/OddEvenStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 홀짝비율통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/oddevenstatplugin")
	public String oddevenstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 홀짝비율통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/OddEvenStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 홀짝비율통계 데이터 조회
	 * 2020.02.10
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/oddevenstatdatas")
	public void oddevenstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 홀짝비율통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
						
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<OddEvenDto> oddEvenStatList = new ArrayList<OddEvenDto>(); 
			
			String[] ratioTitle = LottoUtil.getRatioTitle();
			int[] appearCnt = {0,0,0,0,0,0,0};
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				String oddEven = dataDto.getOdd_even();
				
				for (int j = 0; j < ratioTitle.length; j++) {
					if (ratioTitle[j].equals(oddEven)) {
						appearCnt[j] += 1;
					}
				}
				
			}
			
			for (int i = 0; i < ratioTitle.length; i++) {
				OddEvenDto oddEvenDto = new OddEvenDto();
				oddEvenDto.setOddeven_type(ratioTitle[i]);
				oddEvenDto.setAppearCnt(appearCnt[i]);
				oddEvenStatList.add(oddEvenDto);
				
				if (maxValue < appearCnt[i]) {
					maxValue = appearCnt[i];
				}
			}
			
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", oddEvenStatList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 연속번호출현 화면 호출
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
	@RequestMapping("/statistic/continumstat")
	public String continumstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 연속번호출현 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/ContiNumStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/ContiNumStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 연속번호출현 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/continumstatajax")
	public String continumstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 연속번호출현 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/ContiNumStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			modelMap.addAttribute("winDataList", winDataList);
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 연속번호출현 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/continumstatplugin")
	public String continumstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 연속번호출현 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/ContiNumStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 연속번호출현 데이터 조회
	 * 2020.02.12
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/continumstatdatas")
	public void continumstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 연속번호출현 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			jsonObj.put("datas", winDataList);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 저고비율통계 화면 호출
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
	@RequestMapping("/statistic/lowhighstat")
	public String lowhighstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고비율통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/LowHighStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/LowHighStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 저고비율통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/lowhighstatajax")
	public String lowhighstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고비율통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/LowHighStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 저고비율통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/lowhighstatplugin")
	public String lowhighstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 저고비율통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/LowHighStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 저고비율통계 데이터 조회
	 * 2020.02.22
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/lowhighstatdatas")
	public void lowhighstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 저고비율통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
						
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<LowHighDto> lowHighStatList = new ArrayList<LowHighDto>(); 
			
			String[] ratioTitle = LottoUtil.getRatioTitle();
			int[] appearCnt = {0,0,0,0,0,0,0};
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				String lowHigh = dataDto.getLow_high();
				
				for (int j = 0; j < ratioTitle.length; j++) {
					if (ratioTitle[j].equals(lowHigh)) {
						appearCnt[j] += 1;
					}
				}
				
			}
			
			for (int i = 0; i < ratioTitle.length; i++) {
				LowHighDto lowHighDto = new LowHighDto();
				lowHighDto.setLowhigh_type(ratioTitle[i]);
				lowHighDto.setAppearCnt(appearCnt[i]);
				lowHighStatList.add(lowHighDto);
				
				if (maxValue < appearCnt[i]) {
					maxValue = appearCnt[i];
				}
			}
			
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", lowHighStatList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 끝수합통계 화면 호출
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
	@RequestMapping("/statistic/endnumstat")
	public String endnumstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/EndNumStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/EndNumStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 끝수합통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/endnumstatajax")
	public String endnumstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/EndNumStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 끝수합통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/endnumstatplugin")
	public String endnumstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 끝수합통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/EndNumStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 끝수합통계 데이터 조회
	 * 2020.02.22
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/statistic/endnumstatdatas")
	public void endnumstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 끝수합통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
						
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<EndNumDto> endNumStatList = new ArrayList<EndNumDto>(); 
			
			// 끝수합 목록
			List<Integer> sumEndNumList = new ArrayList<Integer>();
			Map<Integer, Integer> sumEndNumCntMap = new HashMap<Integer, Integer>();  
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				int sumEndNum = dataDto.getSum_end_num();
				
				if (sumEndNumCntMap.containsKey(sumEndNum)) {
					sumEndNumCntMap.put(sumEndNum, sumEndNumCntMap.get(sumEndNum) + 1);
				} else {
					sumEndNumCntMap.put(sumEndNum, 1);
					sumEndNumList.add(sumEndNum);
				}
			}
			
			sumEndNumList = (List<Integer>) LottoUtil.dataSort(sumEndNumList);
			
			int minSumEndNum = sumEndNumList.get(0);
			int maxSumEndNum = sumEndNumList.get(sumEndNumList.size()-1);
			
			for (int i = minSumEndNum; i <= maxSumEndNum; i++) {
				EndNumDto endNumDto = new EndNumDto();
				endNumDto.setSumEndNum(i);
				if (sumEndNumCntMap.containsKey(i)) {
					endNumDto.setAppearCnt(sumEndNumCntMap.get(i));
				} else {
					endNumDto.setAppearCnt(0);
				}
				endNumStatList.add(endNumDto);
				
				if (maxValue < endNumDto.getAppearCnt()) {
					maxValue = endNumDto.getAppearCnt();
				}
			}
			
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", endNumStatList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 총합통계 화면 호출
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
	@RequestMapping("/statistic/totalstat")
	public String totalstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/TotalStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/TotalStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 총합통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/totalstatajax")
	public String totalstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/TotalStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 총합통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/totalstatplugin")
	public String totalstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 총합통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/TotalStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 총합통계 데이터 조회
	 * 2020.02.23
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/statistic/totalstatdatas")
	public void totalstatdatas(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 총합통계 데이터 조회");
			String accessip = request.getRemoteHost();
			
			// 통계 노출용
			int maxValue = 0; // data max value
			double axesMaxValue = 0.0;	// chart max range
						
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			List<TotalDto> totalStatList = new ArrayList<TotalDto>(); 
			
			// 총합 목록
			List<Integer> totalList = new ArrayList<Integer>();
			Map<Integer, Integer> totalCntMap = new HashMap<Integer, Integer>();  
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataDto dataDto = winDataList.get(i);
				int total = dataDto.getTotal();
				
				if (totalCntMap.containsKey(total)) {
					totalCntMap.put(total, totalCntMap.get(total) + 1);
				} else {
					totalCntMap.put(total, 1);
					totalList.add(total);
				}
			}
			
			totalList = (List<Integer>) LottoUtil.dataSort(totalList);
			
			/*
			 * 총합 통계는 데이터 범위가 넓어 존재하는 값만 노출 처리
			int minTotal = totalList.get(0);
			int maxTotal = totalList.get(totalList.size()-1);
			
			for (int i = minTotal; i <= maxTotal; i++) {
				TotalDto totalDto = new TotalDto();
				totalDto.setTotal(i);
				if (totalCntMap.containsKey(i)) {
					totalDto.setAppearCnt(totalCntMap.get(i));
				} else {
					totalDto.setAppearCnt(0);
				}
				totalStatList.add(totalDto);
				
				if (maxValue < totalDto.getAppearCnt()) {
					maxValue = totalDto.getAppearCnt();
				}
			}
			*/
			
			for (int i = 0; i < totalList.size(); i++) {
				TotalDto totalDto = new TotalDto();
				totalDto.setTotal(totalList.get(i));
				totalDto.setAppearCnt(totalCntMap.get(totalList.get(i)));
				totalStatList.add(totalDto);
				
				if (maxValue < totalDto.getAppearCnt()) {
					maxValue = totalDto.getAppearCnt();
				}
			}
			
			double stepSize = LottoUtil.getTicksStepSize(maxValue);
			int iStepSize = (int) stepSize;
			if (maxValue % iStepSize > iStepSize / 2) {
				axesMaxValue = ((maxValue / iStepSize) + 2) * iStepSize * 1.0;
			} else {
				axesMaxValue = ((maxValue / iStepSize) + 1) * iStepSize * 1.0;
			}
			
			jsonObj.put("datas", totalStatList);		
			jsonObj.put("axesMaxValue", axesMaxValue);		
			jsonObj.put("status", "success");		
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 접속자수통계 화면 호출
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
	@RequestMapping("/statistic/logincntstat")
	public String logincntstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 접속자수통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/LoginCntStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/LoginCntStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 접속자수통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/logincntstatajax")
	public String logincntstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 접속자수통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/LoginCntStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 접속자수통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/logincntstatplugin")
	public String logincntstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 접속자수통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/LoginCntStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 결제건수통계 화면 호출
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
	@RequestMapping("/statistic/paymentcntstat")
	public String paymentcntstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 결제건수통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/PaymentCntStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/PaymentCntStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 결제건수통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/paymentcntstatajax")
	public String paymentcntstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 결제건수통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/PaymentCntStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 결제건수통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/paymentcntstatplugin")
	public String paymentcntstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 결제건수통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/PaymentCntStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 매출액통계 화면 호출
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
	@RequestMapping("/statistic/takestat")
	public String takestat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 매출액통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/TakeStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/TakeStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 매출액통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/takestatajax")
	public String takestatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 매출액통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/TakeStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 매출액통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/takestatplugin")
	public String takestatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 매출액통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/TakeStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 추천인통계 화면 호출
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
	@RequestMapping("/statistic/recommenderstat")
	public String recommenderstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 추천인통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/RcmdStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/RcmdStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 추천인통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/recommenderstatajax")
	public String recommenderstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 추천인통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/RcmdStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 추천인통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/recommenderstatplugin")
	public String recommenderstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 추천인통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/RcmdStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 후원금통계 화면 호출
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
	@RequestMapping("/statistic/sponsorshipstat")
	public String sponsorshipstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 후원금통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/SpssStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/SpssStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 후원금통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/sponsorshipstatajax")
	public String sponsorshipstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 후원금통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/SpssStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 후원금통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/sponsorshipstatplugin")
	public String sponsorshipstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 후원금통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/SpssStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 투자금통계 화면 호출
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
	@RequestMapping("/statistic/investmentstat")
	public String investmentstat(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 투자금통계 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/IvstStatMain");
			modelMap.addAttribute(PLUGIN_PAGE, "statistic/plugins/IvstStatMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 투자금통계 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/investmentstatajax")
	public String investmentstatajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 투자금통계 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/IvstStatMain");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 투자금통계 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/statistic/investmentstatplugin")
	public String investmentstatplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 투자금통계 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "statistic/plugins/IvstStatMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
}
