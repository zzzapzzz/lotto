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
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.ServiceApplyDto;
import com.lotto.spring.domain.dto.UserInfoDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.InfoService;
import com.lotto.spring.service.LottoDataService;
import com.lotto.spring.service.MyLottoService;
import com.lotto.spring.service.PatternAnalysisService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class MyLottoController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private MyLottoService myLottoService;
	
	@Autowired
	private SysmngService sysmngService;
	
	@Autowired
	private LottoDataService lottoDataService;
	
	@Autowired
	private PatternAnalysisService patternAnalysisService;
	
	@Autowired
	private InfoService infoService;
	
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
			
			setModelMapWithAuthCheck(modelMap, request);
			
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
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
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
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
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
				
				// 당첨번호가 있으면 결과비교 처리
				WinDataDto param = new WinDataDto();
				param.setWin_count(Integer.parseInt(exCount));
				WinDataDto winData = sysmngService.getWinData(param);
				if (winData != null) {
					lottoDataService.getMyDataResult(myLottoSaveNumList, winData);
					
					// TODO 당첨결과가 존재하면 알림처리 (Email, SMS 등)
					
				}
				
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
	 * MY로또저장번호 NEW 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/mylotto/saveNumNewList")
	public void saveNumNewList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
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
			log.info("[" + loginUserId + "][C] MY로또저장번호 NEW 목록 조회");
			
			Map map = new HashMap();
			map.put("ex_count", exCount);
			map.put("user_no", userInfo.getUser_no());
			map.put("startNum", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(rows))));
			map.put("endNum", Integer.toString(Integer.parseInt(page)*Integer.parseInt(rows)));
			map.put("sidx", sidx);
			map.put("sord", sord);
			
			int myLottoSaveNumListCnt = myLottoService.getSaveNumNewListCnt(map);
			ArrayList<MyLottoSaveNumDto> myLottoSaveNumList = new ArrayList<MyLottoSaveNumDto>();
			
			int total_pages = 0;
			if( myLottoSaveNumListCnt > 0 ) {
				total_pages = (int) Math.ceil((double)myLottoSaveNumListCnt/Double.parseDouble(rows));
				myLottoSaveNumList = myLottoService.getSaveNumNewList(map);
				
				// 당첨번호가 있으면 결과비교 처리
				WinDataDto param = new WinDataDto();
				param.setWin_count(Integer.parseInt(exCount));
				WinDataDto winData = sysmngService.getWinData(param);
				if (winData != null) {
					lottoDataService.getMyDataResult(myLottoSaveNumList, winData);
					
					// TODO 당첨결과가 존재하면 알림처리 (Email, SMS 등)
					
				}
				
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
	 * MY로또저장번호 목록 조회 (For 원영)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/mylotto/saveNumListForWy")
	public void getExData30List(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String rows = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "usr_id");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");
		
		JSONObject json = new JSONObject();
		
		if (userInfo != null) {
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 목록 조회 (For 원영)");
			String accessip = request.getRemoteHost();
			
			MyLottoSaveNumDto dto = new MyLottoSaveNumDto();
			dto.setReg_user_no(3);
//		dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			
			// 등록된 데이터 삭제
			myLottoService.deleteMyData(dto);
			
			int maxSaveCnt = 10; 	// 기본값 설정
			int myLottoSaveNumListCnt = 0;
			
			Map<String, Integer> numberMap = new HashMap<String, Integer>();	//숫자를 저장할 HashMap
			Map<String, Integer> notContainMap = new HashMap<String, Integer>();	//제외할 숫자를 저장할 HashMap
			
			// set data
			List<String> exptDataList = new ArrayList<String>();
			exptDataList.add("3,9,14,18,26,37");
			exptDataList.add("8,21,35,36,39,41");
			exptDataList.add("2,11,14,34,35,41");
			exptDataList.add("8,10,12,32,38,40");
			exptDataList.add("8,18,20,27,28,33");
			
			exptDataList.add("5,9,20,21,38,41");
			exptDataList.add("8,11,12,21,38,41");
			exptDataList.add("4,10,13,18,20,40");
			exptDataList.add("14,18,20,29,31,44");
			exptDataList.add("1,4,11,12,25,33");
			
			exptDataList.add("11,19,20,38,42,45");
			exptDataList.add("2,12,20,31,39,40");
			exptDataList.add("7,8,12,23,39,40");
			exptDataList.add("11,16,24,34,38,43");
			exptDataList.add("5,8,16,18,31,45");
			
			exptDataList.add("14,20,23,33,36,41");
			exptDataList.add("13,14,28,36,37,40");
			exptDataList.add("12,16,25,33,40,43");
			exptDataList.add("14,18,21,37,40,43");
			exptDataList.add("7,11,14,20,33,38");
			
			exptDataList.add("8,10,27,37,42,43");
			exptDataList.add("10,12,23,35,39,40");
			exptDataList.add("13,20,24,33,38,40");
			exptDataList.add("5,7,12,28,35,42");
			exptDataList.add("12,14,19,20,27,34");
			
			exptDataList.add("14,16,25,37,42,43");
			exptDataList.add("7,10,16,23,34,39");
			exptDataList.add("9,13,20,24,28,43");
			exptDataList.add("4,14,27,30,38,41");
			exptDataList.add("11,19,20,38,42,45");
			
			exptDataList.add("2,12,20,31,39,40");
			exptDataList.add("7,8,12,23,39,40");
			exptDataList.add("11,16,24,34,38,43");
			exptDataList.add("11,13,20,36,40,45");
			exptDataList.add("1,9,28,33,38,41");
			
			exptDataList.add("9,14,16,24,25,41");
			exptDataList.add("5,9,10,20,25,43");
			exptDataList.add("2,3,10,19,34,40");
			exptDataList.add("4,9,11,16,33,37");
			exptDataList.add("8,10,19,25,33,43");
			
			exptDataList.add("12,14,28,36,37,41");
			exptDataList.add("14,20,29,31,33,40");
			exptDataList.add("11,14,19,25,27,36");
			
			// 최근회차 조회
			log.info("[" + loginUserNo + "]\t당첨번호 전체 목록 내림차순 조회");
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			/*********************************************************
			 * 제외수 적용 번호추출
			 *********************************************************/
			//제외수를 저장할 list
			List<Integer> deleteTargetList = new ArrayList<Integer>();
			//제외수를 저장할 map
			Map<Integer, Integer> deleteTargetMap = new HashMap<Integer, Integer>();
			
			// 제외수 조회
			ExDataDto exDataDto = new ExDataDto();
			exDataDto.setEx_count(winDataList.get(0).getWin_count()+1);
			
			ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
			String excludeNum = excludeDto.getExclude_num();
			excludeNum = excludeNum.replaceAll(" ", "");
			String[] arrExcludeNum = excludeNum.split(",");
			for (int i = 0; i < arrExcludeNum.length; i++) {
				notContainMap.put(arrExcludeNum[i], Integer.parseInt(arrExcludeNum[i]));
				deleteTargetList.add(Integer.parseInt(arrExcludeNum[i]));
				deleteTargetMap.put(Integer.parseInt(arrExcludeNum[i]), Integer.parseInt(arrExcludeNum[i]));
			}
			
			//제외수를 포함하지 않는 회차합 숫자들
			List<Integer> numberOfContainList = new ArrayList<Integer>(); 
			List<Integer> numberOfNotContainList = new ArrayList<Integer>(); 
			
			log.info("[" + loginUserNo + "]\t추출번호 대상 목록");			
			for (int i = 0; i < exptDataList.size(); i++) {
				String[] datas = exptDataList.get(i).split(",");
				for (int j = 0; j < datas.length; j++) {
					if (!numberMap.containsKey(datas[j]) 
							&& !notContainMap.containsKey(datas[j])	// 제외수 등록 체크
							) {
						log.info("[" + loginUserNo + "]\t\t" + datas[j]);			
						numberMap.put(datas[j], Integer.parseInt(datas[j]));
						numberOfContainList.add(Integer.parseInt(datas[j]));
					}
				}
			}
			
			
			
			ExptPtrnAnlyDto exptPtrnAnlyInfo = new ExptPtrnAnlyDto();
			exptPtrnAnlyInfo.setCont_cnt(6);
			exptPtrnAnlyInfo.setNot_cont_cnt(0);
			
			List<ExDataDto> expectDataList = lottoDataService.getExDataList(exptPtrnAnlyInfo, numberOfContainList, numberOfNotContainList, deleteTargetMap);
			
			
			
			// 당첨번호 전체 목록 조회
			List<WinDataAnlyDto> winDataAnlyList = sysmngService.getWinDataAnlyList(winDataDto);
			
			// 예상번호 목록 삭제
			int exCount = winDataAnlyList.get(winDataAnlyList.size()-1).getWin_count()+1;
			sysmngService.deleteExDataList(exCount);
			
			// 번호간 차이 설정
			lottoDataService.setDifNumbers(winDataAnlyList);
			
			// 당첨번호 배열 설정
			lottoDataService.setNumbers(winDataAnlyList);
			
			// 표준 끝수합 범위 설정
			int[] lowHighEndNumData = lottoDataService.getEndNumberBaseDistribution(winDataAnlyList);
			/** 번호간 범위 결과 목록 */
			ArrayList<HashMap<String, Integer>> numbersRangeList = lottoDataService.getNumbersRangeList(winDataAnlyList);
			/** 숫자별 출현번호 결과 목록 */
			ArrayList<ArrayList<Integer>> groupByNumbersList = lottoDataService.getGroupByNumbersList(winDataAnlyList);
			/** 번호별 궁합/불협수 */
			Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumberByAnly(winDataAnlyList);
			
			
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
			
			List saveList = new ArrayList();	// 저장할 목록
			
			
			if (expectDataList != null && expectDataList.size() > 0) {
				log.info("추출된 목록 건수 = " + expectDataList.size());
				//추출된 번호 전체를 패턴과 비교하여 예측패턴과 가장 많이 일치하는 번호 목록을 구한다.
				
				long startTime = System.currentTimeMillis();
				int excuteCnt = 0;
				Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
				
				do {
					
					int randomSeq = (int) (Math.random() * expectDataList.size());
					if (excutedRandomMap.containsKey(randomSeq)) {
						continue;
					} else {
						excutedRandomMap.put(randomSeq, randomSeq);
						exDataDto.setSeq(randomSeq);
					}
					
					log.info("proc idx = " + excuteCnt);
					ExDataDto exData = expectDataList.get(randomSeq);
					
					//예상패턴 일치개수 설정
					int ptrnMatchCnt = lottoDataService.getExptPtrnMatchCnt(exData, winDataAnlyList, exptPtrnAnlyInfo, lowHighEndNumData, numbersRangeList, groupByNumbersList, mcNumberMap);
					exData.setPtrn_match_cnt(ptrnMatchCnt);
					
					
					if (ptrnMatchCnt >= 5) {
						// 저장번호 등록
						boolean result = lottoDataService.compareExptPtrn(exData, winDataAnlyList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
						if (result) {
							MyLottoSaveNumDto mlsnDto = new MyLottoSaveNumDto();
							mlsnDto.setEx_count(dto.getEx_count());
							//					mlsnDto.setUser_no(loginUserNo);
							mlsnDto.setUser_no(3);
							mlsnDto.setNum1(exData.getNum1());
							mlsnDto.setNum2(exData.getNum2());
							mlsnDto.setNum3(exData.getNum3());
							mlsnDto.setNum4(exData.getNum4());
							mlsnDto.setNum5(exData.getNum5());
							mlsnDto.setNum6(exData.getNum6());
							saveList.add(mlsnDto);
						}
						
						myLottoSaveNumListCnt++;
						
						// 진행도 출력
						int d_cnt = excuteCnt;
						int d_total = maxSaveCnt;						 
						double percent = LottoUtil.getPercent(d_cnt, d_total);
						log.info("추출횟수 = " + saveList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent) + "%)");
						
						if (saveList.size() >= maxSaveCnt || expectDataList.size() == excuteCnt) {
							break;
						}
					}
					
					excuteCnt++;
					
				} while(true);
				
				long endTime = System.currentTimeMillis();
				long resutTime = endTime - startTime;
				
				log.info("전체 " + expectDataList.size() + "건 소요시간  : " + resutTime/1000 + "(ms)");
			} // end if numberList check
			
			if (saveList.size() > 0) {
				Map paramMap = new HashMap();
				paramMap.put("list", saveList);
				myLottoService.insertMyData(paramMap);
			}
			
			ArrayList<MyLottoSaveNumDto> myLottoSaveNumList = new ArrayList<MyLottoSaveNumDto>();
			
			int total_pages = 0;
			if( myLottoSaveNumListCnt > 0 ) {
				total_pages = (int) Math.ceil((double)myLottoSaveNumListCnt/Double.parseDouble(rows));
				
				for (int i = 0; i < saveList.size(); i++) {
					myLottoSaveNumList.add((MyLottoSaveNumDto) saveList.get(i));
				}
				
				// 당첨번호가 있으면 결과비교 처리
				WinDataDto param = new WinDataDto();
				param.setWin_count(exCount);
				WinDataDto winData = sysmngService.getWinData(param);
				if (winData != null) {
					lottoDataService.getMyDataResult(myLottoSaveNumList, winData);
					
					// TODO 당첨결과가 존재하면 알림처리 (Email, SMS 등)
					
				}
				
			} else { 
				total_pages = 0; 
			} 
			
			json.put("cnt", myLottoSaveNumList.size());
			json.put("total", total_pages);
			json.put("page", page);
			json.put("records", myLottoSaveNumListCnt);
			json.put("rows", myLottoSaveNumList);		
			
		} else {
			json.put("status", "usernotfound");
			json.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");		
		}
		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * MY로또저장번호 목록 조회 (For 태화강)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/mylotto/autoAddibm1077")
	public void autoAddibm1077(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String rows = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "usr_id");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");
		
		JSONObject json = new JSONObject();
		
		if (userInfo != null) {
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 목록 조회 (For 태화강)");
			String accessip = request.getRemoteHost();
			
			MyLottoSaveNumDto dto = new MyLottoSaveNumDto();
			dto.setReg_user_no(3);
//		dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			
			// 등록된 데이터 삭제
			myLottoService.deleteMyData(dto);
			
			int maxSaveCnt = 10; 	// 기본값 설정
			int myLottoSaveNumListCnt = 0;
			
			Map<String, Integer> numberMap = new HashMap<String, Integer>();	//숫자를 저장할 HashMap
			Map<String, Integer> notContainMap = new HashMap<String, Integer>();	//제외할 숫자를 저장할 HashMap
			
			
			
			/*********************************************************
			 * 예상 회차합 번호추출
			 *********************************************************/
			//예상 회차합이 낮을 경우, 7~10으로 임의 설정
			int expectContainCnt = 10;
			
			log.info("[" + loginUserNo + "]\t당첨번호 전체 목록 오름차순 조회");
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDtoAsc = new WinDataDto();
			winDataDtoAsc.setSord("ASC");
			List<WinDataAnlyDto> winDataListAsc = sysmngService.getWinDataAnlyList(winDataDtoAsc);
			
			//입력한 회차합 출현숫자
			List<Integer> numberOfInputCountList = lottoDataService.getNumbersOfInputCount(winDataListAsc, expectContainCnt);		
			//입력한 회차합 미출현 숫자
			List<Integer> numberOfNotInputCountList = lottoDataService.getNumbersOfInputCount(numberOfInputCountList);
			
			
			
			
			// 최근회차 조회
			log.info("[" + loginUserNo + "]\t당첨번호 전체 목록 내림차순 조회");
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			/*********************************************************
			 * 제외수 적용 번호추출
			 *********************************************************/
			//제외수를 저장할 list
			List<Integer> deleteTargetList = new ArrayList<Integer>();
			//제외수를 저장할 map
			Map<Integer, Integer> deleteTargetMap = new HashMap<Integer, Integer>();
			
			// 제외수 조회
			ExDataDto exDataDto = new ExDataDto();
			exDataDto.setEx_count(winDataList.get(0).getWin_count()+1);
			
			dto.setEx_count(winDataList.get(0).getWin_count()+1);
			
			ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
			String excludeNum = excludeDto.getExclude_num();
			excludeNum = excludeNum.replaceAll(" ", "");
			String[] arrExcludeNum = excludeNum.split(",");
			for (int i = 0; i < arrExcludeNum.length; i++) {
				notContainMap.put(arrExcludeNum[i], Integer.parseInt(arrExcludeNum[i]));
				deleteTargetList.add(Integer.parseInt(arrExcludeNum[i]));
				deleteTargetMap.put(Integer.parseInt(arrExcludeNum[i]), Integer.parseInt(arrExcludeNum[i]));
			}
			
			//제외수를 포함하지 않는 회차합 숫자들
			List<Integer> numberOfContainList = lottoDataService.getListWithExcludedNumber(numberOfInputCountList, deleteTargetList); 
			List<Integer> numberOfNotContainList = lottoDataService.getListWithExcludedNumber(numberOfNotInputCountList, deleteTargetList); 
			
			
			ExptPtrnAnlyDto exptPtrnAnlyInfo = new ExptPtrnAnlyDto();
			exptPtrnAnlyInfo.setCont_cnt(6);
			exptPtrnAnlyInfo.setNot_cont_cnt(0);
			
			List<ExDataDto> expectDataList = lottoDataService.getExDataList(exptPtrnAnlyInfo, numberOfContainList, numberOfNotContainList, deleteTargetMap);
			
			
			
			// 당첨번호 전체 목록 조회
			List<WinDataAnlyDto> winDataAnlyList = sysmngService.getWinDataAnlyList(winDataDto);
			
			// 예상번호 목록 삭제
			int exCount = winDataAnlyList.get(winDataAnlyList.size()-1).getWin_count()+1;
			sysmngService.deleteExDataList(exCount);
			
			// 번호간 차이 설정
			lottoDataService.setDifNumbers(winDataAnlyList);
			
			// 당첨번호 배열 설정
			lottoDataService.setNumbers(winDataAnlyList);
			
			// 표준 끝수합 범위 설정
			int[] lowHighEndNumData = lottoDataService.getEndNumberBaseDistribution(winDataAnlyList);
			/** 번호간 범위 결과 목록 */
			ArrayList<HashMap<String, Integer>> numbersRangeList = lottoDataService.getNumbersRangeList(winDataAnlyList);
			/** 숫자별 출현번호 결과 목록 */
			ArrayList<ArrayList<Integer>> groupByNumbersList = lottoDataService.getGroupByNumbersList(winDataAnlyList);
			/** 번호별 궁합/불협수 */
			Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumberByAnly(winDataAnlyList);
			
			
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
				
				if (percent * 100 >= AIM_PER) {
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
			
			List saveList = new ArrayList();	// 저장할 목록
			
			
			if (expectDataList != null && expectDataList.size() > 0) {
				log.info("추출된 목록 건수 = " + expectDataList.size());
				//추출된 번호 전체를 패턴과 비교하여 예측패턴과 가장 많이 일치하는 번호 목록을 구한다.
				
				long startTime = System.currentTimeMillis();
				int excuteCnt = 0;
				Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
				
				do {
					
					int randomSeq = (int) (Math.random() * expectDataList.size());
					if (excutedRandomMap.containsKey(randomSeq)) {
						continue;
					} else {
						excutedRandomMap.put(randomSeq, randomSeq);
						exDataDto.setSeq(randomSeq);
					}
					
					log.info("proc idx = " + excuteCnt);
					ExDataDto exData = expectDataList.get(randomSeq);
					
					
					// 고정수 체크
					boolean is17 = false;
					boolean is29 = false;
					int[] numbers = exData.getNumbers();
					for (int i = 0; i < numbers.length; i++) {
						if (numbers[i] == 17) {
							is17 = true;
						} else if (numbers[i] == 29) {
							is29 = true;
						}
					}
					
					if (!is17 || !is29) {
						log.info("고정수 미포함");
						continue;
					}
					
					
					//예상패턴 일치개수 설정
					int ptrnMatchCnt = lottoDataService.getExptPtrnMatchCnt(exData, winDataAnlyList, exptPtrnAnlyInfo, lowHighEndNumData, numbersRangeList, groupByNumbersList, mcNumberMap);
					exData.setPtrn_match_cnt(ptrnMatchCnt);
					
					
					if (ptrnMatchCnt >= 5) {
						// 저장번호 등록
						boolean result = lottoDataService.compareExptPtrn(exData, winDataAnlyList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
						if (result) {
							MyLottoSaveNumDto mlsnDto = new MyLottoSaveNumDto();
							mlsnDto.setEx_count(dto.getEx_count());
							//					mlsnDto.setUser_no(loginUserNo);
							mlsnDto.setUser_no(3);
							mlsnDto.setNum1(exData.getNum1());
							mlsnDto.setNum2(exData.getNum2());
							mlsnDto.setNum3(exData.getNum3());
							mlsnDto.setNum4(exData.getNum4());
							mlsnDto.setNum5(exData.getNum5());
							mlsnDto.setNum6(exData.getNum6());
							saveList.add(mlsnDto);
						}
						
						myLottoSaveNumListCnt++;
						
						// 진행도 출력
						int d_cnt = excuteCnt;
						int d_total = maxSaveCnt;
						double percent = LottoUtil.getPercent(d_cnt, d_total);
						log.info("추출횟수 = " + saveList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent) + "%)");
						
						if (saveList.size() >= maxSaveCnt || expectDataList.size() == excuteCnt) {
							break;
						}
					}
					
					excuteCnt++;
					
				} while(true);
				
				long endTime = System.currentTimeMillis();
				long resutTime = endTime - startTime;
				
				log.info("전체 " + expectDataList.size() + "건 소요시간  : " + resutTime/1000 + "(ms)");
			} // end if numberList check
			
			if (saveList.size() > 0) {
				Map paramMap = new HashMap();
				paramMap.put("list", saveList);
				myLottoService.insertMyData(paramMap);
			}
			
			ArrayList<MyLottoSaveNumDto> myLottoSaveNumList = new ArrayList<MyLottoSaveNumDto>();
			
			int total_pages = 0;
			if( myLottoSaveNumListCnt > 0 ) {
				total_pages = (int) Math.ceil((double)myLottoSaveNumListCnt/Double.parseDouble(rows));
				
				for (int i = 0; i < saveList.size(); i++) {
					myLottoSaveNumList.add((MyLottoSaveNumDto) saveList.get(i));
				}
				
				// 당첨번호가 있으면 결과비교 처리
				WinDataDto param = new WinDataDto();
				param.setWin_count(exCount);
				WinDataDto winData = sysmngService.getWinData(param);
				if (winData != null) {
					lottoDataService.getMyDataResult(myLottoSaveNumList, winData);
					
					// TODO 당첨결과가 존재하면 알림처리 (Email, SMS 등)
					
				}
				
			} else { 
				total_pages = 0; 
			} 
			
			json.put("cnt", myLottoSaveNumList.size());
			json.put("total", total_pages);
			json.put("page", page);
			json.put("records", myLottoSaveNumListCnt);
			json.put("rows", myLottoSaveNumList);		
		} else {
			json.put("status", "usernotfound");
			json.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");		
		}
		
		json.put("status", "success");		
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

			int exCount = dto.getEx_count();
			
			String[] seqArr = dto.getSeqs().split(",");
			
			for (int i = 0; i < seqArr.length; i++) {
				MyLottoSaveNumDto delDto = new MyLottoSaveNumDto();
				delDto.setEx_count(exCount);
				delDto.setUser_no(loginUserNo);
				delDto.setSeq(Integer.parseInt(seqArr[i]));
				boolean result = myLottoService.deleteMyData(delDto);
				log.info("[" + loginUserNo + "]\t" + seqArr[i] + "번째 저장번호가 삭제되었습니다.");
			}
			
			
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
	 * MY로또저장번호 NEW 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/mylotto/deleteMyDataNew")
	public void deleteMyDataNew(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 NEW 삭제");
			
			int exCount = dto.getEx_count();
			
			String[] seqArr = dto.getSeqs().split(",");
			
			for (int i = 0; i < seqArr.length; i++) {
				MyLottoSaveNumDto delDto = new MyLottoSaveNumDto();
				delDto.setEx_count(exCount);
				delDto.setUser_no(loginUserNo);
				delDto.setSeq(Integer.parseInt(seqArr[i]));
				boolean result = myLottoService.deleteMyDataNew(delDto);
				log.info("[" + loginUserNo + "]\t" + seqArr[i] + "번째 저장번호가 삭제되었습니다.");
			}
			
			
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
	 * MY로또저장번호 등록 체크
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/mylotto/autoAddCheck")
	public void autoAddCheck(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 등록 체크");
			
			int saveCnt = 0; 	// 기본값 설정
					
			dto.setUser_no(loginUserNo);
			saveCnt = myLottoService.checkSaveMyData(dto);
				
			if (saveCnt > 0) {
				jsonObj.put("status", "exist");
				jsonObj.put("msg", "등록된 데이터를 삭제하고 자동등록 하시겠습니까?");
			} else {
				jsonObj.put("status", "notexist");
				jsonObj.put("msg", "등록된 번호조합이 없습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * MY로또저장번호 NEW 등록 체크
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/mylotto/autoAddNewCheck")
	public void autoAddNewCheck(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 NEW 등록 체크");
			
			int saveCnt = 0; 	// 기본값 설정
			
			dto.setUser_no(loginUserNo);
			saveCnt = myLottoService.checkSaveMyDataNew(dto);
			
			if (saveCnt > 0) {
				jsonObj.put("status", "exist");
				jsonObj.put("msg", "등록된 데이터를 삭제하고 자동등록 하시겠습니까?");
			} else {
				jsonObj.put("status", "notexist");
				jsonObj.put("msg", "등록된 번호조합이 없습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * MY로또저장번호 자동등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/mylotto/autoAdd")
	public void autoAdd(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 자동등록");
			
			// 등록된 데이터 삭제
			dto.setUser_no(loginUserNo);
			myLottoService.deleteMyData(dto);
			
			// 예상번호 NEW 검증 삭제
			log.info("[" + loginUserNo + "]\t예상번호_NEW 검증 삭제");
			sysmngService.deleteExptNumNewVari();
						
			int maxSaveCnt = 10; 	// 기본값 설정
			if (dto.getMaxSaveCnt() > 0) {
				maxSaveCnt = dto.getMaxSaveCnt(); 
			}
			
			/* 
			 * 사용자의 랜덤 저장개수 확인
			 * 확인된 숫자만큼 자동 저장 처리
			 */
			
			// 랜덤 예상번호 조회
			ExDataDto exDataDto = new ExDataDto();
			exDataDto.setEx_count(dto.getEx_count());
			int exDataListCnt = sysmngService.getExDataListCnt(exDataDto);
			log.info("예상번호 총 건수 = " + exDataListCnt);
			
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
			
//			Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
			int excuteCnt = 0;	// 추출횟수
			List saveList = new ArrayList();	// 저장할 목록
			
			
			// 개선된 제외수 목록 설정 2020.02.07
			Map enhancedRulesMap = lottoDataService.getEnhancedRules(loginUserNo, dto.getEx_count());
			List<Integer> allExcludeNumList = (List<Integer>) enhancedRulesMap.get("allExcludeNumList");
			Map<Integer, Integer> allExcludeNumMap = (Map<Integer, Integer>) enhancedRulesMap.get("allExcludeNumMap");
			int excludeNumOfMcNum = (int) enhancedRulesMap.get("excludeNumOfMcNum");
			
			log.info("장기 미출수 >>> " + excludeNumOfMcNum);
			
			List<Integer> exportNumberList = new ArrayList<Integer>();
			for (int i = 1; i <= 45; i++) {
				if (!allExcludeNumMap.containsKey(i)) {
					exportNumberList.add(i);
				}
			}
			
			/************************************************************
			 * 마지막 출현번호 등장 패턴으로 필터추가 (from 나대길)
			 * 적용기준 최근 30회 이내 당첨번호 목록에서 80% 이상 적중 시 적용
			 * 2020.02.29
			 ************************************************************/
			// 목표 적중률
			int aimMatchedPer = 80;
			// 체크할 이전회차 수
			int fromCount = 30;
			boolean isCheck = false;
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDtoForFilter = new WinDataDto();
			winDataDtoForFilter.setSord("ASC");
			winDataDtoForFilter.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataListForFilter = sysmngService.getWinDataList(winDataDtoForFilter);
			
			// 마지막 당첨번호
			WinDataDto lastWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
			int[] lastWinNnumbers = LottoUtil.getNumbers(lastWinDataDto);
			
			// 필터0
			boolean isFilter0 = false;
			// 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
			isCheck = lottoDataService.isCheckFilter0(winDataListForFilter);
			if (isCheck) {
				isFilter0 = lottoDataService.getMatchedPer(0, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
						
			// 필터1
			boolean isFilter1 = false;
			// 최근 당첨번호 중 10차이나는 수 사이에 다른 숫자가 있는지 확인
			for (int i = 0; i < lastWinNnumbers.length - 2; i++) {
				int num1 = lastWinNnumbers[i];
				int num2 = lastWinNnumbers[i+1];
				int num3 = lastWinNnumbers[i+2];
				
				if (num3 - num1 == 10) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter1 = lottoDataService.getMatchedPer(1, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터2
			boolean isFilter2 = false;
			// 최근 당첨번호에서 단번대 멸 & 첫수가 10번대인지 확인
			int[] zeroCntRangeOfLastWinData = lottoDataService.getZeroCntRangeData(lastWinDataDto);
			if (zeroCntRangeOfLastWinData[0] == 0) {
				// 첫번째 수가 10번대 체크
				if (10 <= lastWinNnumbers[0] && lastWinNnumbers[0] <= 19) {
					isCheck = true;
				}
			}
			
			if (isCheck) {
				isFilter2 = lottoDataService.getMatchedPer(2, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터3
			boolean isFilter3 = false;
			// 최근 당첨번호에서 20번대(20~29) 2개 & 30번대(30~39) 2개 출현여부 확인
			int appear2RangeCnt = 0;
			int appear3RangeCnt = 0;
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (20 <= lastWinNnumbers[i] && lastWinNnumbers[i] <= 29) {
					appear2RangeCnt++;
				}
				if (30 <= lastWinNnumbers[i] && lastWinNnumbers[i] <= 39) {
					appear3RangeCnt++;
				}
			}
			if (appear2RangeCnt == 2 && appear3RangeCnt == 2) {
				isCheck = true;
			}
			
			if (isCheck) {
				isFilter3 = lottoDataService.getMatchedPer(3, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터4
			boolean isFilter4 = false;
			// 최근 당첨번호에서 42번 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 42) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter4 = lottoDataService.getMatchedPer(4, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터5
			boolean isFilter5 = false;
			// 최근 당첨번호에서 28번 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 28) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter5 = lottoDataService.getMatchedPer(5, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터6
			boolean isFilter6 = false;
			// 최근 당첨번호에서 19번 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 19) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter6 = lottoDataService.getMatchedPer(6, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터7
			boolean isFilter7 = false;
			// 최근 당첨번호에서 쌍수(11,22,33,44) 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 11
						|| lastWinNnumbers[i] == 22
						|| lastWinNnumbers[i] == 33
						|| lastWinNnumbers[i] == 44
						) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter7 = lottoDataService.getMatchedPer(7, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터8
			boolean isFilter8 = false;
			// 최근 당첨번호에서 8번 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 8) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter8 = lottoDataService.getMatchedPer(8, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터9
			boolean isFilter9 = false;
			// 최근 당첨번호에서 3연속수 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length - 2; i++) {
				if (lastWinNnumbers[i+1] - lastWinNnumbers[i] == 1) {
					// 끝수 존재여부 체크
					if (i+2 < lastWinNnumbers.length) {
						if(lastWinNnumbers[i+2] - lastWinNnumbers[i+1] == 1) {
							// 다음수가 3연속 확인
							isCheck = true;
							break;
						}
					}
				}
			}			
						
			if (isCheck) {
				isFilter9 = lottoDataService.getMatchedPer(9, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			
			// 필터10
			boolean isFilter10 = false;
			// 최근 당첨번호에서 40번대 멸 여부 확인
			if (zeroCntRangeOfLastWinData[4] == 0) {
				isCheck = true;
			}
			
			if (isCheck) {
				isFilter10 = lottoDataService.getMatchedPer(10, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터11
			boolean isFilter11 = false;
			// 최근 당첨번호에서 10구간(10~19) 3수 출현여부 확인
			isCheck = lottoDataService.checkRange3Numbers(lastWinNnumbers, 1);
			
			if (isCheck) {
				isFilter11 = lottoDataService.getMatchedPer(11, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터12
			boolean isFilter12 = false;
			// 최근 당첨번호에서 38번 출현여부 확인
			for (int i = 0; i < lastWinNnumbers.length; i++) {
				if (lastWinNnumbers[i] == 38) {
					isCheck = true;
					break;
				}
			}
			
			if (isCheck) {
				isFilter12 = lottoDataService.getMatchedPer(12, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터13
			boolean isFilter13 = false;
			// 최근 당첨번호에서 4회차 전부터 1씩 감소하는 수가 4회 출현 후 5회차에서 2 적은수가 출현 확인
			isCheck = lottoDataService.isCheckFilter13(winDataListForFilter);
			
			if (isCheck) {
				isFilter13 = lottoDataService.getMatchedPer(13, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터14
			boolean isFilter14 = false;
			// 최근 당첨번호에서 3회차 전부터 1씩 감소하는 수가 3회동안 출현 확인
			isCheck = lottoDataService.isCheckFilter14(winDataListForFilter);
			
			if (isCheck) {
				isFilter14 = lottoDataService.getMatchedPer(14, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			// 필터15, 16은 확률이 낮아서 pass
			
			
			// 필터17
			boolean isFilter17 = false;
			// 단번대가 1개씩 3회연속 출현 확인
			isCheck = lottoDataService.isCheckFilter17(winDataListForFilter);
			
			if (isCheck) {
				isFilter17 = lottoDataService.getMatchedPer(17, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터18
			boolean isFilter18 = false;
			// 5와 8끝수 출현 확인
			isCheck = lottoDataService.isCheckFilter18(winDataListForFilter);
			
			if (isCheck) {
				isFilter18 = lottoDataService.getMatchedPer(18, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터 6회귀
			boolean isFilter6Regression = false;
			// 6회귀 연속 3회 전멸 확인
			isFilter6Regression = lottoDataService.isCheckFilter6Regression(winDataListForFilter);
			
			
			// 필터19
			boolean isFilter19 = false;
			// 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현 확인
			isCheck = lottoDataService.isCheckFilter19(winDataListForFilter);
			if (isCheck) {
				isFilter19 = lottoDataService.getMatchedPer(19, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터20
			boolean isFilter20 = false;
			// 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
			isCheck = lottoDataService.isCheckFilter20(winDataListForFilter);
			if (isCheck) {
				isFilter20 = lottoDataService.getMatchedPer(20, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터21
			boolean isFilter21 = false;
			// 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
			isCheck = lottoDataService.isCheckFilter21(winDataListForFilter);
			if (isCheck) {
				isFilter21 = lottoDataService.getMatchedPer(21, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터22
			boolean isFilter22 = false;
			// 필터23
			boolean isFilter23 = false;
			// 3끝수가 4회연속 출현 확인
			isCheck = lottoDataService.isCheckFilter22(winDataListForFilter);
			if (isCheck) {
				isFilter22 = lottoDataService.getMatchedPer(22, winDataListForFilter, fromCount, aimMatchedPer);
				isFilter23 = lottoDataService.getMatchedPer(23, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터24~26
			boolean isFilter24 = false;
			boolean isFilter25 = false;
			boolean isFilter26 = false;
			/*
			 * 같은번호 3개 존재 확인
			 * 특정구간 (range) 
			 * 0: 1~9
			 * 1: 10~19
			 * 2: 20~29
			 * 3: 30~39
			 * 4: 40~45
			 */
			int range = 2;	// 20번대(20~29) 3구간 출현 확인
			boolean isRange3Numbers = lottoDataService.checkRange3Numbers(lastWinNnumbers, range); 
			if (isRange3Numbers) {
				isFilter24 = lottoDataService.getMatchedPer(24, winDataListForFilter, fromCount, aimMatchedPer);
				isFilter25 = lottoDataService.getMatchedPer(25, winDataListForFilter, fromCount, aimMatchedPer);
				isFilter26 = lottoDataService.getMatchedPer(26, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터27
			boolean isFilter27 = false;
			// 10번대 연번이 출현 확인
			isCheck = lottoDataService.isCheckFilter27(winDataListForFilter);
			if (isCheck) {
				isFilter27 = lottoDataService.getMatchedPer(27, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터28
			boolean isFilter28 = false;
			// 15번 출현 확인
			isCheck = lottoDataService.isCheckFilter28(winDataListForFilter);
			if (isCheck) {
				isFilter28 = lottoDataService.getMatchedPer(28, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터29
			boolean isFilter29 = false;
			// 28번이 출현 확인
			isCheck = lottoDataService.isCheckFilter29(winDataListForFilter);
			if (isCheck) {
				isFilter29 = lottoDataService.getMatchedPer(29, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 필터30
			boolean isFilter30 = false;
			// 보너스번호가 40번대(40~45) 출현 확인
			isCheck = lottoDataService.isCheckFilter30(winDataListForFilter);
			if (isCheck) {
				isFilter30 = lottoDataService.getMatchedPer(30, winDataListForFilter, fromCount, aimMatchedPer);
			}
			isCheck = false;	//체크여부 초기화
			
			
			// 자동목록 조회, 예상패턴 비교, 등록
			// 추출된 번호목록 Map 2020.02.07
			Map<String, String> exportNumberMap = new HashMap<String, String>();
				
			// 예상번호 추출
			do {
//				int randomSeq = (int) (Math.random() * exDataListCnt) + 1;
//				if (excutedRandomMap.containsKey(randomSeq)) {
//					continue;
//				} else {
//					excutedRandomMap.put(randomSeq, randomSeq);
//					exDataDto.setSeq(randomSeq);
//				}
				List<Integer> excuteNumberList = new ArrayList<Integer>();
				excuteCnt++;
				
				if (saveList.size() >= maxSaveCnt || 500000 == excuteCnt) {
					String excuteNumbers = "";
					for (int i = 0; i < excuteNumberList.size(); i++) {
						if (!"".equals(excuteNumbers)) {
							excuteNumbers += ",";
						}
						excuteNumbers += excuteNumberList.get(i);
					}
					log.info("예상번호 선별 번호 >>> " + excuteNumbers); 
					break;
				}
				
				// 개선된 제외수를 뺀 번호들 중에서 랜덤으로 6회 추출
				Map<Integer, Integer> numberMap = new HashMap<Integer, Integer>();
				List<Integer> numberList = new ArrayList<Integer>();	// 선택된 번호
				for (int i = 0; i < exportNumberList.size(); i++) {
					if (exportNumberList.get(i) == excludeNumOfMcNum
							|| exportNumberList.get(i) % 10 == 5	// 2020.02.07 5단위 제외처리 (임시 적용)
							) {
						continue;
					}
					excuteNumberList.add(exportNumberList.get(i));
				}
				
				// 장기 미출수 (1 포함)
				numberMap.put(excludeNumOfMcNum, excludeNumOfMcNum);
				numberList.add(excludeNumOfMcNum);
				
				do {
					int randomSeq = (int) (Math.random() * excuteNumberList.size());
					int number = excuteNumberList.get(randomSeq);
					if (!numberMap.containsKey(number)) {
						numberMap.put(number, number);
						numberList.add(number);
						
						// 선택한 번호는 목록에서 삭제처리
						excuteNumberList.remove(randomSeq);
					}
				} while (numberList.size() < 6);
				
//				ExDataDto exData = sysmngService.getExDataInfo(exDataDto);
				// 2020.02.07 변경
				ExDataDto exData = new ExDataDto();
				numberList = (List<Integer>) LottoUtil.dataSort(numberList);
				// 번호를 Map에 담기
				String numbers = "";
				for (int i = 0; i < numberList.size(); i++) {
					if (i > 0) {
						numbers += ",";
					}
					numbers += numberList.get(i);
				}
				
				log.info(excuteCnt + ") 예상번호 비교 >>> " + numbers);
				
				if (exportNumberMap.containsKey(numbers)) {
					continue;
				} else {
					exportNumberMap.put(numbers, numbers);
					
					exData.setNum1(numberList.get(0));
					exData.setNum2(numberList.get(1));
					exData.setNum3(numberList.get(2));
					exData.setNum4(numberList.get(3));
					exData.setNum5(numberList.get(4));
					exData.setNum6(numberList.get(5));
					
					exData.setNumbers(LottoUtil.getNumbers(exData));
					exData.setDifNumbers(LottoUtil.getDifNumbers(exData.getNumbers()));
					
					exData.setTotal(LottoUtil.getTotal(exData));
					exData.setSum_end_num(LottoUtil.getSumEndNumber(exData));
				}
				
				boolean result = lottoDataService.compareExptPtrn(exData, winDataList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
				if (result) {
					// 추가 필터 체크여부 확인
					if (isFilter0) {
						log.info("추가 필터0 체크합니다.");
						result = lottoDataService.isMatchedFilter0(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터0 실패.");
							continue;
						} else {
							log.info("\t추가 필터0 성공.");
						}
					}
					
					if (isFilter1) {
						log.info("추가 필터1 체크합니다.");
						result = lottoDataService.isMatchedFilter1(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터1 실패.");
							continue;
						} else {
							log.info("\t추가 필터1 성공.");
						}
					}
					
					if (isFilter2) {
						log.info("추가 필터2 체크합니다.");
						result = lottoDataService.isMatchedFilter2(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터2 실패.");
							continue;
						} else {
							log.info("\t추가 필터2 성공.");
						}
					}
					
					if (isFilter3) {
						log.info("추가 필터3 체크합니다.");
						result = lottoDataService.isMatchedFilter3(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터3 실패.");
							continue;
						} else {
							log.info("\t추가 필터3 성공.");
						}
					}
					
					if (isFilter4) {
						log.info("추가 필터4 체크합니다.");
						result = lottoDataService.isMatchedFilter4(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터4 실패.");
							continue;
						} else {
							log.info("\t추가 필터4 성공.");
						}
					}
					
					if (isFilter5) {
						log.info("추가 필터5 체크합니다.");
						result = lottoDataService.isMatchedFilter5(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터5 실패.");
							continue;
						} else {
							log.info("\t추가 필터5 성공.");
						}
					}
					
					if (isFilter6) {
						log.info("추가 필터6 체크합니다.");
						result = lottoDataService.isMatchedFilter6(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터6 실패.");
							continue;
						} else {
							log.info("\t추가 필터6 성공.");
						}
					}
					
					if (isFilter7) {
						log.info("추가 필터7 체크합니다.");
						result = lottoDataService.isMatchedFilter7(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터7 실패.");
							continue;
						} else {
							log.info("\t추가 필터7 성공.");
						}
					}
					
					if (isFilter8) {
						log.info("추가 필터8 체크합니다.");
						result = lottoDataService.isMatchedFilter8(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터8 실패.");
							continue;
						} else {
							log.info("\t추가 필터8 성공.");
						}
					}
					
					if (isFilter9) {
						log.info("추가 필터9 체크합니다.");
						result = lottoDataService.isMatchedFilter9(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터9 실패.");
							continue;
						} else {
							log.info("\t추가 필터9 성공.");
						}
					}
					
					if (isFilter10) {
						log.info("추가 필터10 체크합니다.");
						result = lottoDataService.isMatchedFilter10(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터10 실패.");
							continue;
						} else {
							log.info("\t추가 필터10 성공.");
						}
					}
					
					if (isFilter11) {
						log.info("추가 필터11 체크합니다.");
						result = lottoDataService.isMatchedFilter11(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터11 실패.");
							continue;
						} else {
							log.info("\t추가 필터11 성공.");
						}
					}
					
					if (isFilter12) {
						log.info("추가 필터12 체크합니다.");
						result = lottoDataService.isMatchedFilter12(lastWinNnumbers, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터12 실패.");
							continue;
						} else {
							log.info("\t추가 필터12 성공.");
						}
					}
					
					if (isFilter13) {
						log.info("추가 필터13 체크합니다.");
						result = lottoDataService.isMatchedFilter13(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터13 실패.");
							continue;
						} else {
							log.info("\t추가 필터13 성공.");
						}
					}
					
					if (isFilter14) {
						log.info("추가 필터14 체크합니다.");
						result = lottoDataService.isMatchedFilter14(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터14 실패.");
							continue;
						} else {
							log.info("\t추가 필터14 성공.");
						}
					}
					
					if (isFilter17) {
						log.info("추가 필터17 체크합니다.");
						result = lottoDataService.isMatchedFilter17(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터17 실패.");
							continue;
						} else {
							log.info("\t추가 필터17 성공.");
						}
					}
					
					if (isFilter18) {
						log.info("추가 필터18 체크합니다.");
						result = lottoDataService.isMatchedFilter18(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터18 실패.");
							continue;
						} else {
							log.info("\t추가 필터18 성공.");
						}
					}
					
					if (isFilter6Regression) {
						log.info("추가 필터 6회귀 체크합니다.(6회귀 존재확인)");
						result = lottoDataService.isMatchedFilter6Regression(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터 6회귀 실패.");
							continue;
						} else {
							log.info("\t추가 필터 6회귀 성공.");
						}
					}
					
					if (isFilter19) {
						log.info("추가 필터19 체크합니다.");
						result = lottoDataService.isMatchedFilter19(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터19 실패.");
							continue;
						} else {
							log.info("\t추가 필터19 성공.");
						}
					}
					
					if (isFilter20) {
						log.info("추가 필터20 체크합니다.");
						result = lottoDataService.isMatchedFilter20(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터20 실패.");
							continue;
						} else {
							log.info("\t추가 필터20 성공.");
						}
					}
					
					if (isFilter21) {
						log.info("추가 필터21 체크합니다.");
						result = lottoDataService.isMatchedFilter21(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터21 실패.");
							continue;
						} else {
							log.info("\t추가 필터21 성공.");
						}
					}
					
					if (isFilter22) {
						log.info("추가 필터22 체크합니다.");
						result = lottoDataService.isMatchedFilter22(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터22 실패.");
							continue;
						} else {
							log.info("\t추가 필터22 성공.");
						}
					}
					
					if (isFilter23) {
						log.info("추가 필터23 체크합니다.");
						result = lottoDataService.isMatchedFilter23(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터23 실패.");
							continue;
						} else {
							log.info("\t추가 필터23 성공.");
						}
					}
					
					if (isFilter24) {
						log.info("추가 필터24 체크합니다.");
						result = lottoDataService.isMatchedFilter24(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터24 실패.");
							continue;
						} else {
							log.info("\t추가 필터24 성공.");
						}
					}
					
					if (isFilter25) {
						log.info("추가 필터25 체크합니다.");
						result = lottoDataService.isMatchedFilter25(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터25 실패.");
							continue;
						} else {
							log.info("\t추가 필터25 성공.");
						}
					}
					
					if (isFilter26) {
						log.info("추가 필터26 체크합니다.");
						result = lottoDataService.isMatchedFilter26(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터26 실패.");
							continue;
						} else {
							log.info("\t추가 필터26 성공.");
						}
					}
					
					if (isFilter27) {
						log.info("추가 필터27 체크합니다.");
						result = lottoDataService.isMatchedFilter27(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터27 실패.");
							continue;
						} else {
							log.info("\t추가 필터27 성공.");
						}
					}
					
					if (isFilter28) {
						log.info("추가 필터28 체크합니다.");
						result = lottoDataService.isMatchedFilter28(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터28 실패.");
							continue;
						} else {
							log.info("\t추가 필터28 성공.");
						}
					}
					
					if (isFilter29) {
						log.info("추가 필터29 체크합니다.");
						result = lottoDataService.isMatchedFilter29(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터29 실패.");
							continue;
						} else {
							log.info("\t추가 필터29 성공.");
						}
					}
					
					if (isFilter30) {
						log.info("추가 필터30 체크합니다.");
						result = lottoDataService.isMatchedFilter30(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터30 실패.");
							continue;
						} else {
							log.info("\t추가 필터30 성공.");
						}
					}
					
					// 제외번호 포함여부 확인 2020.02.01
					if (this.checkExcludeNum(exData, allExcludeNumList, excludeNumOfMcNum)) {
						log.info("예상번호 등록 처리");
						MyLottoSaveNumDto mlsnDto = new MyLottoSaveNumDto();
						mlsnDto.setEx_count(dto.getEx_count());
						mlsnDto.setUser_no(loginUserNo);
						mlsnDto.setNum1(exData.getNum1());
						mlsnDto.setNum2(exData.getNum2());
						mlsnDto.setNum3(exData.getNum3());
						mlsnDto.setNum4(exData.getNum4());
						mlsnDto.setNum5(exData.getNum5());
						mlsnDto.setNum6(exData.getNum6());
						saveList.add(mlsnDto);
						
						excuteCnt = 0;	// 실행횟수 초기화
					}
				}
				
				// 진행도 출력
				int d_cnt = excuteCnt;
				int d_total = maxSaveCnt;
				double percent = LottoUtil.getPercent(d_cnt, d_total);
				log.info("추출횟수 = " + saveList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent) + "%)");
				
//				if (saveList.size() >= maxSaveCnt || exDataListCnt == excuteCnt) {
				
			} while (true);

			if (saveList.size() > 0) {
				Map paramMap = new HashMap();
				paramMap.put("list", saveList);
				myLottoService.insertMyData(paramMap);
				
				jsonObj.put("status", "success");
				jsonObj.put("msg", saveList.size() + "조합이 등록되었습니다.");
			} else {
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "자동저장할 알맞는 번호조합이 없습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * MY로또저장번호 NEW 자동등록
	 * 
	 * 예상번호 NEW 테이블의 조합번호를 등록하도록 개선
	 * 2020.04.04
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/mylotto/autoAddNew")
	public void autoAddNew(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyLottoSaveNumDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] MY로또저장번호 NEW 자동등록");
			
			// 등록된 데이터 삭제
			dto.setUser_no(loginUserNo);
			myLottoService.deleteMyDataNew(dto);
						
			// 등록된 매핑 데이터 삭제 for New
			dto.setUser_no(loginUserNo);
			myLottoService.deleteMyNewMpngData(dto);
			
			
			// 예상번호 NEW 검증 삭제
			// TODO 실제 반영시에는 제거할 것
			log.info("[" + loginUserNo + "]\t예상번호 NEW 검증 삭제");
			sysmngService.deleteExptNumNewVari();
			
			
			int maxSaveCnt = 10; 	// 기본값 설정
			if (dto.getMaxSaveCnt() > 0) {
				maxSaveCnt = dto.getMaxSaveCnt(); 
			}
						
			List<ExDataDto> exList = new ArrayList<ExDataDto>(); // 추출한 목록
			List saveList = new ArrayList(); // 저장할 목록
			
			/*
			 * 예상번호 NEW 조합 매핑에 사용되지 않은 번호가 있는지 확인
			 * NEW조합을 사용자가 중복해서 사용하지 않도록 하기 위함.
			 * 모든 번호가 매핑될 경우 무시하고 매핑할 수 있도록 해야함.
			 */
			
			// 번호 저장 시 추가 필터를 통해 실행회수 개념을 추가함. 2020.04.08
			// 실행회수
			int excuteCnt = 0;
			// 랜덤조회 실행기준 회수
			int START_RANDOM_BASE_COUNT = 100;
			// 실행 제한 횟수
			int limitCnt = 500000;
			// 저장건수
			int saveCnt = 0;
			// 중복확인 Map
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
			
			
			
			// 미사용조합이 예상번호보다 많은 경우 랜덤 추출
			// 미사용조합 조회
			log.info("[" + loginUserNo + "]\t1. 미사용조합 조회 후 랜덤조합 추출");
			dto.setOnlyUnused("Y");
			List<ExDataDto> exDataList = myLottoService.getExptNumNewList(dto);
			if (exDataList != null) {
				log.info("[" + loginUserNo + "]\t\t> 미사용 조회 건수=" + exDataList.size());
				
				// 조회건수가 랜덤조회 실행기준 회수보다 크다면 랜덤조합 실행
				if (exDataList.size() > START_RANDOM_BASE_COUNT) {
					// 랜덤 조회
					do {
						int randomSeq = (int) (Math.random() * exDataList.size());
						ExDataDto exDataDto = exDataList.get(randomSeq);
						int exDataSeq = exDataDto.getSeq();
						
						if (!map.containsKey(exDataSeq)) {
							map.put(exDataSeq, exDataSeq);
							
							// 추가 필터 체크
							boolean check = lottoDataService.checkAddFilter(exDataDto, winDataList);
							if (check) {
								exDataDto.setUser_no(loginUserNo);
								exList.add(exDataDto);
								saveCnt++;
							}
						}
						
						excuteCnt++;
						if (excuteCnt == limitCnt) {
							break;
						}
						
					} while (maxSaveCnt > saveCnt);
					
					log.info("[" + loginUserNo + "]\t\t> 랜덤조합 건수=" + exList.size());
				} else {
					log.info("[" + loginUserNo + "]\t\t> 순차조합 전환");
					
					for (ExDataDto exDataDto : exDataList) {
						int exDataSeq = exDataDto.getSeq();					
						map.put(exDataSeq, exDataSeq);
						
						// 추가 필터 체크
						boolean check = lottoDataService.checkAddFilter(exDataDto, winDataList);
						if (check) {
							exDataDto.setUser_no(loginUserNo);
							exList.add(exDataDto);
							saveCnt++;
						}
					}
					log.info("[" + loginUserNo + "]\t\t> 미사용 조합 건수=" + exList.size());
				}
			} else {
				log.info("[" + loginUserNo + "]\t\t> 미사용 조합 조회 건수=0");
			}
				
			// 추출 조합수에 못 미치면 전체를 대상으로 추가조회 2020.04.08
			if (maxSaveCnt > saveCnt) {
				log.info("[" + loginUserNo + "]\t2. 전체 예상번호 조회");
				
				// 전체 조합 조회
				dto.setOnlyUnused("N");
				List<ExDataDto> exDataAllList = myLottoService.getExptNumNewList(dto);
				if (exDataAllList != null) {
				
					// 조회건수가 랜덤조회 실행기준 회수보다 크다면 랜덤조합 실행
					if (exDataAllList.size() > START_RANDOM_BASE_COUNT) {
						do {
							int randomSeq = (int) (Math.random() * exDataAllList.size());
							ExDataDto exDataDto = exDataAllList.get(randomSeq);
							int exDataSeq = exDataDto.getSeq();					
							
							if (!map.containsKey(exDataSeq)) {
								map.put(exDataSeq, exDataSeq);
								
								// 추가 필터 체크
								boolean check = lottoDataService.checkAddFilter(exDataDto, winDataList);
								if (check) {
									exDataDto.setUser_no(loginUserNo);
									exList.add(exDataDto);
									saveCnt++;
								}
							}
							
							excuteCnt++;
							if (excuteCnt == limitCnt) {
								break;
							}
							
						} while (maxSaveCnt > saveCnt);
						log.info("[" + loginUserNo + "]\t\t> 전체 중 랜덤조합 포함 건수=" + exList.size());	
					}
					
					// 실행제한으로 종료되면 미추출 대상으로 추가조회 2020.04.11
					if (maxSaveCnt > saveCnt) {					
						log.info("[" + loginUserNo + "]\t2-1. 순차조합 추출");
						
						for (ExDataDto exDataDto : exDataAllList) {
							int exDataSeq = exDataDto.getSeq();
							
							if (!map.containsKey(exDataSeq)) {
								map.put(exDataSeq, exDataSeq);
								
								// 추가 필터 체크
								boolean check = lottoDataService.checkAddFilter(exDataDto, winDataList);
								if (check) {
									exDataDto.setUser_no(loginUserNo);
									exList.add(exDataDto);
									saveCnt++;
								}
							}
							
							if (maxSaveCnt == saveCnt) {
								break;
							}
							
						}
						
						log.info("[" + loginUserNo + "]\t\t> 순차조합 포함 추출건수=" + exList.size());
					}
				} //end if exDataAllList null check 
			}
			
			/*
			 * 조회된 예상번호 SEQ 목록을 랜덤으로 저장할 수만큼 반복등록 처리
			 * 다른 사용자와 중복되어도 상관없음. (MY로또 저장시간 기준)
			 */
			log.info("\t추출한 예상번호 목록 설정 처리");
			for (int i = 0; i < exList.size(); i++) {
				ExDataDto exData = exList.get(i);
				
				MyLottoSaveNumDto mlsnDto = new MyLottoSaveNumDto();
				mlsnDto.setEx_count(dto.getEx_count());
				mlsnDto.setUser_no(loginUserNo);
				mlsnDto.setSeq(exData.getSeq());	// 추출된 seq로 설정
				mlsnDto.setNum1(exData.getNum1());
				mlsnDto.setNum2(exData.getNum2());
				mlsnDto.setNum3(exData.getNum3());
				mlsnDto.setNum4(exData.getNum4());
				mlsnDto.setNum5(exData.getNum5());
				mlsnDto.setNum6(exData.getNum6());
				saveList.add(mlsnDto);
			}
						
			if (saveList.size() > 0) {
				// MY로또 등록
				log.info("\tMY로또 등록");
				Map paramMap = new HashMap();
				paramMap.put("list", saveList);
				myLottoService.insertMyDataNew(paramMap);
				
				// MY로또 매핑데이터 등록
				log.info("\tMY로또 매핑데이터 등록");
				Map paramMap2 = new HashMap();
				paramMap2.put("list", exList);
				myLottoService.insertMyNewMpngData(paramMap2);
				
				jsonObj.put("status", "success");
				jsonObj.put("msg", saveList.size() + "조합이 등록되었습니다.");
				log.info("\t추출한 예상번호 " + saveList.size() + "조합이 등록되었습니다.");
			} else {
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "자동저장할 알맞는 번호조합이 없습니다.");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 제외할 번호가 포함되어 있는지 체크
	 * 2020.02.01
	 * 
	 * @param exData
	 * @return true : 미포함, false : 포함
	 */
	private boolean checkExcludeNum(ExDataDto exData) {
		log.info("제외할 번호가 포함되어 있는지 체크");
		boolean result = true;
		
		int[] numbers = exData.getNumbers();
		
		// 포함되어 있는지 체크할 번호
		int[] checkNumbers = {
			5, 8, 10, 13, 14, 15, 18, 20, 24, 27, 31, 33, 34, 35, 37, 39, 40, 43, 44	
		};
		
		boolean exist = false;
		for (int i = 0; i < numbers.length; i++) {
			int num = numbers[i];
			exist = false;
			
			for (int j = 0; j < checkNumbers.length; j++) {
				if (num == checkNumbers[j]) {
					log.info("\t 제외수가 포함되어 있음.[ " + checkNumbers[j] + " ]");
					exist = true;
					break;
				}
			}
			
			if (exist) {
				break;
			}
		}
		
		if (exist) {
			result = !result;
		}
		
		return result;
	}
	
	/**
	 * 개선된 제외수가 포함되어 있는지 체크
	 * 
	 * 2020.02.07
	 * 
	 * @param exData 예상번호
	 * @param allExcludeNumList 개선된 제외수 목록
	 * @param excludeNumOfMcNum 장기 미출수 
	 * @return true : 미포함, false : 포함
	 */
	private boolean checkExcludeNum(ExDataDto exData, List<Integer> allExcludeNumList, int excludeNumOfMcNum) {
		boolean result = true;
		
		int[] numbers = exData.getNumbers();
		
		boolean exist = false;
		
		// 1. 개선된 제외수가 포함되어 있는지 체크
		log.info("1. 개선된 제외수가 포함되어 있는지 체크");
		for (int i = 0; i < numbers.length; i++) {
			int num = numbers[i];
			exist = false;
			
			for (int j = 0; j < allExcludeNumList.size(); j++) {
				if (num == allExcludeNumList.get(j)) {
					log.info("\t 제외수가 포함되어 있음.[ " + num + " ]");
					exist = true;
					break;
				}
			}
			
			if (exist) {
				break;
			}
		}
		
		if (exist) {
			// 개선된 제외수가 포함되어 있으면 실패 처리
			result = !result;
		} else {
			// 2. 장기 미출수 포함여부 확인
			exist = false;
			log.info("2. 장기 미출수 포함여부 확인");
			for (int i = 0; i < numbers.length; i++) {
				int num = numbers[i];
				
				if (num == excludeNumOfMcNum) {
					log.info("\t 장기 미출수 포함.[ " + num + " ]");
					exist = true;
					break;
				}
				
				if (exist) {
					break;
				}
			}
			
			if (!exist) {
				// 장기 미출수가 포함되어 있지 않으면 실패 처리
				result = !result;
			}
		}
		
		return result;
	}
	
	/**
	 * MY로또저장번호 필터등록 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/filterAddajax")
	public String filterAddajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또저장번호 필터등록 화면 호출(ajax)");
			
			String exCount = WebUtil.replaceParam(request.getParameter("ex_count"), "");
			modelMap.addAttribute("ex_count", exCount);
			
			setModelMap(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/MyDataFilteredInsert");
			modelMap.addAttribute("isAjax", "Y");
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * MY로또저장번호 필터등록 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mylotto/filterAddplugin")
	public String filterAddplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] MY로또저장번호 필터등록 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/mylotto/mylotto"));
			
			modelMap.addAttribute(CONTENT_PAGE, "mylotto/plugins/MyDataFilteredInsert_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
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
			
			// 사용자정보 조회
			UserInfoDto userInfoDto = new UserInfoDto();
			userInfoDto.setUser_no(loginUserId);
			userInfoDto =  sysmngService.getUserInfo(userInfoDto);
			modelMap.addAttribute("MyProfile", userInfoDto);
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
			
			// 서비스정보 조회
			ServiceApplyDto serviceApplyDto = new ServiceApplyDto();
			serviceApplyDto.setUser_no(loginUserId);
			serviceApplyDto =  infoService.getServiceApply(serviceApplyDto);
			modelMap.addAttribute("ServiceApply", serviceApplyDto);
			
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
