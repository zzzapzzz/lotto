package com.lotto.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

import com.lotto.common.LottoUtil;
import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.LottoDataService;
import com.lotto.spring.service.PatternAnalysisService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class ExptController extends DefaultSMController {
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private PatternAnalysisService patternAnalysisService;
	
	@Autowired(required = true)
	private LottoDataService lottoDataService;
	
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
	 * 예상번호 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/expt/getExDataList")
	public void getExDataList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		JSONObject json = new JSONObject();
		
		String searchCount = WebUtil.replaceParam(request.getParameter("search_count"), "10");
		int iSearchCount = Integer.parseInt(searchCount);
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
  		}
  		
		// 로그인 아이디
		int loginUserNo = userInfo.getUser_no();
		log.info("[" + loginUserNo + "][C] 예상번호 30목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("ASC");
		List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
		
		// 예상번호 패턴정보 조회
		ExptPtrnAnlyDto exptPtrnAnlyDto = new ExptPtrnAnlyDto();
		exptPtrnAnlyDto.setEx_count(dto.getEx_count());
		ExptPtrnAnlyDto exptPtrnAnlyInfo = patternAnalysisService.getExptPtrnAnlyInfo(exptPtrnAnlyDto); 

		// 총합 일치
		int cnt = 2;
		double AIM_PER = 10.0;	// 목표율
		Map map = new HashMap();
		map.put("cnt", cnt);
		List<CaseInsensitiveMap> totalGroupCntList = null;
		Map<Integer, Integer> totalGroupCntMap = new HashMap<Integer, Integer>();
		
		do {
			map.put("cnt", cnt);
			int totalGroupSumCnt = sysmngService.getTotalGroupSumCnt(map);
			int d_cnt = totalGroupSumCnt;
			int d_total = winDataList.get(winDataList.size()-1).getWin_count();
			double percent = LottoUtil.getPercent(d_cnt, d_total);
			
			if (percent >= AIM_PER) {
				totalGroupCntList = sysmngService.getTotalGroupCntList(map);
				break;
			}
			// 목표율에 도달하지 못할 경우, 다음 개수 증가
			cnt++;
		} while (true);
		
		if (totalGroupCntList != null && totalGroupCntList.size() > 0) {
			
			for (int i = 0; i < totalGroupCntList.size(); i++) {
				CaseInsensitiveMap totalGroupCnt = totalGroupCntList.get(i);
				String total = String.valueOf(totalGroupCnt.get("total"));
				String totalCnt = String.valueOf(totalGroupCnt.get("cnt"));
				totalGroupCntMap.put(Integer.parseInt(total), Integer.parseInt(totalCnt));
			}
		}
		
		// 끝수합 일치
		cnt = 2;
		map = new HashMap();
		map.put("cnt", cnt);
		List<CaseInsensitiveMap> endnumGroupCntList = null;
		Map<Integer, Integer> endnumGroupCntMap = new HashMap<Integer, Integer>();
		
		do {
			map.put("cnt", cnt);
			int endnumGroupSumCnt = sysmngService.getEndnumGroupSumCnt(map);
			int d_cnt = endnumGroupSumCnt;
			int d_total = winDataList.get(winDataList.size()-1).getWin_count();
			double percent = LottoUtil.getPercent(d_cnt, d_total);
			
			if (percent >= AIM_PER) {
				endnumGroupCntList = sysmngService.getEndnumGroupCntList(map);
				break;
			}
			// 목표율에 도달하지 못할 경우, 다음 개수 증가
			cnt++;
		} while (true);
		
		if (endnumGroupCntList != null && endnumGroupCntList.size() > 0) {
			for (int i = 0; i < endnumGroupCntList.size(); i++) {
				CaseInsensitiveMap endnumGroupCnt = endnumGroupCntList.get(i);
				String total = String.valueOf(endnumGroupCnt.get("sum_end_num"));
				String endnumCnt = String.valueOf(endnumGroupCnt.get("cnt"));
				endnumGroupCntMap.put(Integer.parseInt(total), Integer.parseInt(endnumCnt));
			}
		}
				
				
		// 예상번호 30조합 추출
		int exDataListCnt = sysmngService.getExDataListCnt(dto);
		log.info("예상번호 총 건수 = " + exDataListCnt);
		List<ExDataDto> exDataList = new ArrayList<ExDataDto>();
		Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
		int excuteCnt = 0;	// 추출횟수
		
		// 30목록 추출
		do {
			int randomSeq = (int) (Math.random() * exDataListCnt) + 1;
			if (excutedRandomMap.containsKey(randomSeq)) {
				continue;
			} else {
				excutedRandomMap.put(randomSeq, randomSeq);
				dto.setSeq(randomSeq);
			}
			
			ExDataDto exData = sysmngService.getExDataInfo(dto);
			boolean result = lottoDataService.compareExptPtrn(exData, winDataList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
			if (result) {
				exDataList.add(exData);
			}
			
			excuteCnt++;
			
			// 진행도 출력
			int d_cnt = excuteCnt;
			int d_total = exDataListCnt;
			double percent = LottoUtil.getPercent(d_cnt, d_total);
			log.info("추출횟수 = " + exDataList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent) + "%)");
			
			if (exDataList.size() >= iSearchCount || exDataListCnt == excuteCnt) {
				break;
			}
			
		} while (true);
		
		if (exDataList != null && exDataList.size() > 0) {
			json.put("ex_numbers_cnt", exDataList.size());
//			JSONArray jsonArr = JSONArray.fromObject(userList);
			json.put("ex_numbers", exDataList);
		} else {
			json.put("ex_numbers_cnt", 0);
		}
		
		json.put("status", "success");
		writeJSON(response, json); 
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
