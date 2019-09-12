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
import com.lotto.spring.domain.dto.AcDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.service.LottoDataService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class TestController extends DefaultSMController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private TestController() {
		super();
	}

	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private LottoDataService lottoDataService;
	
	/**
	 * 테스트 화면 호출
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
	@RequestMapping("/test/test")
	public String win(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 테스트 화면 호출");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "test/TestMain");
			modelMap.addAttribute(PLUGIN_PAGE, "test/plugins/TestMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 테스트 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/test/testajax")
	public String winajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 테스트 화면 호출(ajax)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "test/TestMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 테스트 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/test/testplugin")
	public String winplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 테스트 화면 호출(plugin)");
			
			setModelMap(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "test/plugins/TestMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 총합 테스트
	 * 
	 * 당첨결과를 비교하여 총 90%의 일치율을 나타내는 총합을 범위를 도출함. 
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/test/totalTest")
	public void totalTest(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 총합 테스트");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("DESC");
		winDataDto.setPage("1");	// 전체조회 설정
		List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);

		// 최근 당첨번호
		WinDataDto lastData = winDataList.get(0);
		int lastWinCount = lastData.getWin_count();
		
		
		// 총합 일치
		int cnt = 2;
		double AIM_PER = 10.0;	// 목표율
		Map map = new HashMap();
		map.put("cnt", cnt);
		List<CaseInsensitiveMap> totalGroupCntList = null;
		Map<Integer, Integer> totalGroupCntMap = new HashMap<Integer, Integer>();
		// 메세지 처리
		List<String> msgList = new ArrayList<String>();
		int[] arrTotalRange = {0,0};
		
		do {
			map.put("cnt", cnt);
			int totalGroupSumCnt = sysmngService.getTotalGroupSumCnt(map);
			double d_cnt = totalGroupSumCnt;
			double d_total = lastWinCount;
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
			arrTotalRange[0] = Integer.parseInt(String.valueOf(totalGroupCntList.get(0).get("total")));
			arrTotalRange[1] = Integer.parseInt(String.valueOf(totalGroupCntList.get(totalGroupCntList.size()-1).get("total")));
			
			for (int i = 0; i < totalGroupCntList.size(); i++) {
				CaseInsensitiveMap totalGroupCnt = totalGroupCntList.get(i);
				String total = String.valueOf(totalGroupCnt.get("total"));
				String totalCnt = String.valueOf(totalGroupCnt.get("cnt"));
				totalGroupCntMap.put(Integer.parseInt(total), Integer.parseInt(totalCnt));
			}
			
			for (int i = winDataList.size() - 1; i >= 0 ; i--) {
				WinDataDto winData = winDataList.get(i);
				String msg = winData.getWin_count() + ":<font color='red'><b>X</b></font>";
				if (totalGroupCntMap.containsKey(winData.getTotal())) {
					msg = winData.getWin_count() + ":일치";
				}
				msgList.add(msg);
			}
		}
		
		String totalRange = arrTotalRange[0] + "~" + arrTotalRange[1];
		
		JSONObject json = new JSONObject();
		json.put("rows", msgList);		
		json.put("totalRange", totalRange);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * 최대 출현횟수별 테스트
	 * 
	 * 당첨결과를 비교하여 최대출현횟수 일치율을 비교하여 횟수값을 도출함. 
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/appearNumbersTest")
	public void appearNumbersTest(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 최대 출현횟수별 테스트");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("ASC");
		List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
		
		/** 최대 출현횟수별 설정 */
		List<Map> appearNumbersList = lottoDataService.getAppearNumbersList(winDataList);
		for (int i = 0; i < appearNumbersList.size(); i++) {
			Map data = appearNumbersList.get(i);
			log.info(i + " : " + data.toString());
		}
		
		boolean result = false;
		int MATCH_CNT = 5;	//매치 개수
		int isMatchCnt = 0;
		
		// 당첨번호 일치율 확인
		for (int i = 0; i < winDataList.size(); i++) {
			int containCnt = 0;
			
			WinDataAnlyDto data = winDataList.get(i);
			if (appearNumbersList.get(0).containsKey(data.getNum1())) {
				containCnt++;
			}
			if (appearNumbersList.get(1).containsKey(data.getNum2())) {
				containCnt++;
			}
			if (appearNumbersList.get(2).containsKey(data.getNum3())) {
				containCnt++;
			}
			if (appearNumbersList.get(3).containsKey(data.getNum4())) {
				containCnt++;
			}
			if (appearNumbersList.get(4).containsKey(data.getNum5())) {
				containCnt++;
			}
			if (appearNumbersList.get(5).containsKey(data.getNum6())) {
				containCnt++;
			}
			
			if (containCnt >= MATCH_CNT) {
				isMatchCnt++;
			}
		}
		// 당첨확률 일치율
		double d_cnt = isMatchCnt;
		double d_total = winDataList.size();
		DecimalFormat df = new DecimalFormat("#.##"); 
		double percent = Double.parseDouble(df.format( d_cnt/d_total ));
		log.info("매칭개수 : " + isMatchCnt + ", 일치율 : " + (percent * 100) + "%"); 
		
		
		JSONObject json = new JSONObject();
		json.put("status", "success");		
		json.put("isMatchCnt", isMatchCnt);		
		json.put("isMatchPer", (percent * 100) + "%");		
		writeJSON(response, json); 
	}
	
	/**
	 * 번호간 범위 테스트
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/numbersRangeTest")
	public void numbersRangeTest(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 번호간 범위 테스트");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("ASC");
		List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
		
		/** 번호간 범위 결과 목록 */
		ArrayList<HashMap<String, Integer>> numbersRangeList = lottoDataService.getNumbersRangeList(winDataList);
		
		int MATCH_CNT = 5;	//매치 개수
		int isMatchCnt = 0;
		
		// 당첨번호 일치율 확인
		for (int idx = 0; idx < winDataList.size(); idx++) {
			WinDataAnlyDto data = winDataList.get(idx);
			int containCnt = 0;
			int[] numbers = data.getDifNumbers();
			
			for (int i = 0; i < numbers.length; i++) {
				HashMap<String, Integer> map = numbersRangeList.get(i);
				//번호간 차이값이 평균범위의 최소값보다 작거나, 최대값보다 크면 data를 예상번호로 선택하지 않는다.
				if (map.containsKey(String.valueOf(numbers[i]))) {
					containCnt++;
				}
			}
			
			if (containCnt >= MATCH_CNT) {
				isMatchCnt++;
			}
		}
		
		// 확인
		for (int i = 0; i < numbersRangeList.size(); i++) {
			HashMap<String, Integer> map = numbersRangeList.get(i);
			log.info(i + " : " + map.toString());
		}
		
		// 번호간 범위 일치율
		/*
		 * 2019.01.30
		 * 최대최소 출현 매칭률 80% 설정 시 일치율 : 66%
		 * 최대최소 출현 매칭률 90% 설정 시 일치율 : 31%
		 */
		double d_cnt = isMatchCnt;
		double d_total = winDataList.size();
		DecimalFormat df = new DecimalFormat("#.##"); 
		double percent = Double.parseDouble(df.format( d_cnt/d_total ));
		log.info("매칭개수 : " + isMatchCnt + ", 일치율 : " + (percent * 100) + "%"); 
		
		
		JSONObject json = new JSONObject();
		json.put("status", "success");
		json.put("isMatchCnt", isMatchCnt);		
		json.put("isMatchPer", (percent * 100) + "%");
		writeJSON(response, json); 
	}
	
	/**
	 * 미출현구간 테스트
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/zeroCntRangeTest")
	public void zeroCntRangeTest(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 미출현구간 테스트");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("ASC");
		List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
		
		int MATCH_CNT = 5;	//매치 개수
		int isMatchCnt = 0;
		
		Map<Integer, Integer[]> appearRangeByCount = new HashMap<Integer, Integer[]>(); 
		
		// 번호별 출현건수
		Integer[] arrAppearNumberCnt = new Integer[45];
		for (int i = 0; i < arrAppearNumberCnt.length; i++) {
			arrAppearNumberCnt[i] = 0;
		}
		
		// 당첨번호 일치율 확인
		for (int idx = 0; idx < winDataList.size(); idx++) {
			
			if (idx == 0) {
				WinDataAnlyDto data = winDataList.get(idx);
				int[] numbers = LottoUtil.getNumbers(data);
				for (int i = 0; i < numbers.length; i++) {
					int number = numbers[i];
					arrAppearNumberCnt[number-1] += 1;
				}
				continue;
			}
			
			WinDataAnlyDto bfData = winDataList.get(idx - 1);
			WinDataAnlyDto data = winDataList.get(idx);
			/** 번호간 범위 결과 목록 */
			int[] containGroupCnt = lottoDataService.getZeroCntRangeData(data);	
			
			int[] bfNumbers = LottoUtil.getNumbers(bfData);
			
			for (int i = 0; i < bfNumbers.length; i++) {
				int key = bfNumbers[i];
				
				//출현번호 카운트 누적
				arrAppearNumberCnt[key-1] += 1;
				
				/*
				 * TC1. 
				 * 번호별 다음 출현구간 확인
				 * 번호/출현구간 누적건수
				 */
//				if (appearRangeByCount.containsKey(key)) {
//					Integer[] containCntByCount = appearRangeByCount.get(key);
//					
//					if (key == 10) {
//						System.out.println("===============================================");
//						System.out.print((idx+1) + "회 ");
//						for (int j = 0; j < containCntByCount.length; j++) {
//							System.out.print(containCntByCount[j] + (j<containCntByCount.length-1?",":"")  );
//						}
//						System.out.println();
//						System.out.println("---");
//					}
//					
//					for (int j = 0; j < containGroupCnt.length; j++) {
//						if (key == 10) {
//							System.out.print(containGroupCnt[j] + (j<containGroupCnt.length-1?",":"")  );
//						}
//						containCntByCount[j] += containGroupCnt[j];
//					}
//					
//					if (key == 10) {
//						System.out.println();
//						System.out.println("---");
//					}
//					
//					if (key == 10) {
//						System.out.print((idx+1) + "회 ");
//						for (int j = 0; j < containCntByCount.length; j++) {
//							System.out.print(containCntByCount[j] + (j<containCntByCount.length-1?",":"")  );
//						}
//						System.out.println();
//						System.out.println("===============================================");
//					}
//					
//					appearRangeByCount.put(key, containCntByCount);
//					
//				} else {
//					
//					// 값 초기화
//					Integer[] arrCnt = new Integer[5];
//					for (int j = 0; j < arrCnt.length; j++) {
//						arrCnt[j] = 0;
//					}
//					
//					for (int j = 0; j < containGroupCnt.length; j++) {
//						if (key == 10) {
//							System.out.print(containGroupCnt[j] + (j<containGroupCnt.length-1?",":"")  );
//						}
//						arrCnt[j] += containGroupCnt[j];
//					}
//					
//					if (key == 10) {
//						System.out.println("===============================================");
//						System.out.print((idx+1) + "회 ");
//						for (int j = 0; j < arrCnt.length; j++) {
//							System.out.print(arrCnt[j] + (j<arrCnt.length-1?",":"")  );
//						}
//						System.out.println();
//						System.out.println("===============================================");
//					}
//					
//					appearRangeByCount.put(key, arrCnt);
//				}
				
				/*
				 * TC2. 2019.03.05 
				 * 번호별 다음 미출현구간 확인
				 * 번호/미출현구간 누적건수
				 */
				if (appearRangeByCount.containsKey(key)) {
					Integer[] arrNotAppearCntByNumber = appearRangeByCount.get(key);
					// 미출현 구간 카운트 누적
					for (int j = 0; j < containGroupCnt.length; j++) {
						if (0 == containGroupCnt[j]) {
							arrNotAppearCntByNumber[j] += 1;
						}
					}
					appearRangeByCount.put(key, arrNotAppearCntByNumber);
				} else {
					// 값 초기화
					Integer[] arrCnt = new Integer[5];
					for (int j = 0; j < arrCnt.length; j++) {
						arrCnt[j] = 0;
					}
					
					// 미출현 구간 카운트 누적
					for (int j = 0; j < containGroupCnt.length; j++) {
						if (0 == containGroupCnt[j]) {
							arrCnt[j] += 1;
						}
					}
					appearRangeByCount.put(key, arrCnt);
				}
				
			}
		}
		
		// TC1.확인
//		for (int i = 1; i <= 45; i++) {
//			Integer[] containCntByCount = appearRangeByCount.get(i);
//			log.info(i + " : " + containCntByCount[0] + ", " + containCntByCount[1] + ", " + containCntByCount[2] + ", " + containCntByCount[3] + ", " + containCntByCount[4]);
//		}
		
		// TC2.확인
		for (int i = 1; i <= 45; i++) {
			Integer[] arrNotAppearCntByNumber = appearRangeByCount.get(i);
			String cntByNumber = "";
			String perByNumber = "";
			for (int j = 0; j < arrNotAppearCntByNumber.length; j++) {
				cntByNumber += arrNotAppearCntByNumber[j] + ", ";
				perByNumber += LottoUtil.getPercent(arrNotAppearCntByNumber[j],arrAppearNumberCnt[i-1]) + (j < arrNotAppearCntByNumber.length-1 ?", ":"");
			}
			cntByNumber += " (출현횟수=" + arrAppearNumberCnt[i-1] + ")";
			log.info(i + " : " + cntByNumber);
			log.info(i + " : " + perByNumber);
		}
		
		
		JSONObject json = new JSONObject();
		json.put("status", "success");
		writeJSON(response, json); 
	}
	
	/**
	 * AC 분석정보 등록
	 * 
	 * 전체 회차의 AC분석정보를 등록한다. 
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/insertAllAcInfo")
	public void insertAllAcInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			//2016.05.23 cremazer
	  		//ORACLE 인 경우 대문자 설정
	  		if ("ORACLE".equals(systemInfo.getDatabase())) {
	  			dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
	  		}
	  		
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] AC 분석정보 등록");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// AC정보 전체 삭제
			AcDto acDto = new AcDto();
			sysmngService.deleteAcInfo(acDto);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			for (int countIdx = 0; countIdx < winDataList.size(); countIdx++) {
				int winCount = winDataList.get(countIdx).getWin_count();
				sysmngService.insertAcInfo(winCount);			
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "등록했습니다.");
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
}
