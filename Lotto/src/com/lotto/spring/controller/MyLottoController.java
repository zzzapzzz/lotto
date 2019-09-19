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

import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
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
			double d_cnt = totalGroupSumCnt;
			double d_total = winDataList.get(winDataList.size()-1).getWin_count();
			DecimalFormat df = new DecimalFormat("#.##"); 
			double percent = Double.parseDouble(df.format( d_cnt/d_total ));
			
			if (percent * 100 >= AIM_PER) {
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
			double d_cnt = endnumGroupSumCnt;
			double d_total = winDataList.get(winDataList.size()-1).getWin_count();
			DecimalFormat df = new DecimalFormat("#.##"); 
			double percent = Double.parseDouble(df.format( d_cnt/d_total ));
			
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
					double d_cnt = excuteCnt;
					double d_total = maxSaveCnt;
					DecimalFormat df = new DecimalFormat("#.##"); 
					double percent = Double.parseDouble(df.format( d_cnt/d_total ));
					log.info("추출횟수 = " + saveList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent*100) + "%)");
					
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
	 * MY로또저장번호 자동등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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
			
			int maxSaveCnt = 5; 	// 기본값 설정
			/* 
			 * TODO 
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
				double d_cnt = totalGroupSumCnt;
				double d_total = winDataList.get(winDataList.size()-1).getWin_count();
				DecimalFormat df = new DecimalFormat("#.##"); 
				double percent = Double.parseDouble(df.format( d_cnt/d_total ));
				
				if (percent * 100 >= AIM_PER) {
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
				double d_cnt = endnumGroupSumCnt;
				double d_total = winDataList.get(winDataList.size()-1).getWin_count();
				DecimalFormat df = new DecimalFormat("#.##"); 
				double percent = Double.parseDouble(df.format( d_cnt/d_total ));
				
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
			
			Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
			int excuteCnt = 0;	// 추출횟수
			List saveList = new ArrayList();	// 저장할 목록
			
			// 자동목록 조회, 예상패턴 비교, 등록
				
			// 예상번호 추출
			do {
				int randomSeq = (int) (Math.random() * exDataListCnt) + 1;
				if (excutedRandomMap.containsKey(randomSeq)) {
					continue;
				} else {
					excutedRandomMap.put(randomSeq, randomSeq);
					exDataDto.setSeq(randomSeq);
				}
				
				ExDataDto exData = sysmngService.getExDataInfo(exDataDto);
				boolean result = lottoDataService.compareExptPtrn(exData, winDataList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
				if (result) {
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
				}
				
				excuteCnt++;
				
				// 진행도 출력
				double d_cnt = excuteCnt;
				double d_total = maxSaveCnt;
				DecimalFormat df = new DecimalFormat("#.##"); 
				double percent = Double.parseDouble(df.format( d_cnt/d_total ));
				log.info("추출횟수 = " + saveList.size() + " / 실행횟수 = " + excuteCnt + " (" + (percent*100) + "%)");
				
				if (saveList.size() >= maxSaveCnt || exDataListCnt == excuteCnt) {
					break;
				}
				
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
