package com.lotto.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.MCNumDto;
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
	
//	@Autowired(required = true)
//	private MailService mailService;
	
//	@Autowired(required = true)
//	private JavaMailSender mailSender;
	
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
	
	/**
	 * 제외수 테스트
	 * 2020.02.02
	 * 
	 * <ol>
	 * <li>제외수의 궁합수를 포함한 번호들을 제외번호들로 설정.</li>
	 * <li>제외수 설정 시작회차(12회차)부터 비교하여 적중률(%)을 계산.</li>
	 * <li>전체 회차의 적중률을 평균치를 계산하여 기준률(90%)을 비교.</li>
	 * <li>1.의 목록에서 당첨번호가 출현했다면, 이전 회차의 무엇과 연관성이 있는지 추가분석.</li>
	 * <li>4.를 통해 연광성 있는 번호는 1.의 목록에서 remove 처리.</li>
	 * <li>1~3.의 과정을 반복.</li>
	 * </ol>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/test/testExclude20200202")
	public void testExclude20200202(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
			log.info("[" + loginUserNo + "][C] 제외수 테스트");
			String accessip = request.getRemoteHost();
			log.info("[" + loginUserNo + "] accessip = " + accessip);
			
			
			// 당첨번호 전체 목록 조회
			log.info("[" + loginUserNo + "]\t 당첨번호 전체 목록 조회 (오름차순)");
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근회차 조회 
			WinDataDto winData = winDataList.get(winDataList.size()-1);
			log.info("[" + loginUserNo + "]\t 최근회차 조회 : " + winData.getWin_count());
						
			// 궁합수 조회 (최근회차)
			log.info("[" + loginUserNo + "]\t 궁합수 조회 (최근회차)");
			WinDataDto dtoForMC = new WinDataDto();
			dtoForMC.setWin_count(winData.getWin_count());
			List<MCNumDto> mcNumList = sysmngService.getMcNumList(dtoForMC);
			
			// 전체 평균 적중률 계산
			int checkWinCount = 0;
			double totRate = 0.0;
			
			// 90% 이상 적중회수
			double baseRate = 90.0;
			int succCount = 0;
			
			// 당첨회차 순으로 적중률 확인
			log.info("[" + loginUserNo + "]\t 당첨회차 순으로 적중률 확인");			
			for (int i = 0; i < winDataList.size(); i++) {
				if (i < 11) {
					log.info("[" + loginUserNo + "]\t\t " + (i+1) + "회차 SKIP");
					continue;
				}
				
				// 전체제외수 중복제크 Map, List
				Map checkAllExcludeNumber = new HashMap();
				List allExcludeNumberList = new ArrayList();
				
				
				WinDataDto wdd = winDataList.get(i);
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 계산 시작");				
				
				// 제외수 조회
				ExDataDto exDataDto = new ExDataDto();
				exDataDto.setEx_count(wdd.getWin_count());
				ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
				
				// 제외수 문자열을 int 배열로 변환
				int[] excludeNumbers = LottoUtil.getNumbers(excludeDto.getExclude_num().replaceAll(" ", ""));
				
				// 제외수의 궁합수를 매칭하여 전체 제외수 목록을 추출
				for (int j = 0; j < excludeNumbers.length; j++) {
					int excludeNum = excludeNumbers[j];
					
					if (!checkAllExcludeNumber.containsKey(excludeNum)) {
						checkAllExcludeNumber.put(excludeNum, excludeNum);
						allExcludeNumberList.add(excludeNum);
					}
					
					for (int k = 0; k < mcNumList.size(); k++) {
						MCNumDto mCNumDto = mcNumList.get(k);
						int mcNum = mCNumDto.getNum();
						if (excludeNum == mcNum) {
							// 궁합수 문자열을 int 배열로 변환
							int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
							
							for (int l = 0; l < mcNumbers.length; l++) {
								int mcNumOfexcludeNum = mcNumbers[l];
								
								// 제외수의 궁합수를 전체제외수에 등록
								if (!checkAllExcludeNumber.containsKey(mcNumOfexcludeNum)) {
									checkAllExcludeNumber.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
									allExcludeNumberList.add(mcNumOfexcludeNum);
								}
							}
							
							// 현재 제외수의 궁합수 반복 중단처리
							break;
						}
					}
					
				} // end 제외수 목록 반복
				
				// 적중률 계산
				// 전체제외수 중 당첨번호 출현 확인
				int appearCnt = 0;
				String appearNumbers = "";
				int[] winNumbers = LottoUtil.getNumbersFromObj(wdd);
				for (int j = 0; j < winNumbers.length; j++) {
					for (int j2 = 0; j2 < allExcludeNumberList.size(); j2++) {
						if (winNumbers[j] == (int) allExcludeNumberList.get(j2)) {
							appearCnt++;
							
							if (!"".equals(appearNumbers)) {
								appearNumbers += ",";
							}
							appearNumbers = "" + appearNumbers + winNumbers[j];
							break;
						}
					}
				}
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 제외수 : " + excludeDto.getExclude_num());
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 출현번호 : " + appearNumbers);
				
				// 적중률 누적
				double rate = 100 - (appearCnt * 1.0 / allExcludeNumberList.size() * 100);				
				totRate += rate;
				
				// 적중회수 확인
				if (rate > baseRate) {
					succCount++;
				}
				
				checkWinCount++;
			} // end 당첨회차 순으로 적중률 확인
			
			// 전체 적중률 확인
			double totAvgRate = totRate / checkWinCount;
			log.info("[" + loginUserNo + "]\t " + baseRate + "% 이상 적중 회수 = " + succCount);
			log.info("[" + loginUserNo + "]\t 전체 적중률 = " + totAvgRate);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "테스트했습니다.");
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 제외수 테스트
	 * 2020.02.03
	 * 
	 * <ol>
	 * <li>최근 10회동안 미출한 번호들의 궁합수 목록을 구함.</li>
	 * <li>1번 중 미출수에 없는 번호 선별.</li>
	 * <li>미출수의 궁합수 중 2번째까지만 선별.</li>
	 * <li>제외수의 궁합수 목록을 구함.</li>
	 * <li>제외수 궁합수 목록에서 미출수에 포함된 번호 중 가장 오래된 번호는 제외.</li>
	 * <li>5번의 나머지 번호중 미출수에 있는 번호는 추가.</li>
	 * <li>2+3+6의 목록을 구함.</li>
	 * <li>적중률 계산.</li>
	 * <li>비교 시작은 30회 부터 시작. (최하 20회 이상 미출수로 선별해야함.).</li>
	 * </ol>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/test/testExclude")
	public void testExclude(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
			log.info("[" + loginUserNo + "][C] 제외수 테스트");
			String accessip = request.getRemoteHost();
			log.info("[" + loginUserNo + "] accessip = " + accessip);
			
			
			// 당첨번호 전체 목록 조회
			log.info("[" + loginUserNo + "]\t 당첨번호 전체 목록 조회 (오름차순)");
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근회차 조회 
			WinDataDto winData = winDataList.get(winDataList.size()-1);
			log.info("[" + loginUserNo + "]\t 최근회차 조회 : " + winData.getWin_count());
			
			// 궁합수 조회 (최근회차)
			log.info("[" + loginUserNo + "]\t 궁합수 조회 (최근회차)");
			WinDataDto dtoForMC = new WinDataDto();
			dtoForMC.setWin_count(winData.getWin_count());
			List<MCNumDto> mcNumList = sysmngService.getMcNumList(dtoForMC);
			
			// 시작회차
//			int startIndex = 30;
			int startIndex = 896 - 20;
			
			// 전체 평균 적중률 계산
			int checkWinCount = 0;
			double totRate = 0.0;
			
			// 90% 이상 적중회수
			double baseRate = 90.0;
			int succCount = 0;
			
			// 당첨회차 순으로 적중률 확인
			log.info("[" + loginUserNo + "]\t 당첨회차 순으로 적중률 확인");			
			for (int i = startIndex - 1; i < winDataList.size(); i++) {
				if (i < 11) {
					log.info("[" + loginUserNo + "]\t\t " + (i+1) + "회차 SKIP");
					continue;
				}
				
				
				// 미출궁합수 중복제크 Map, List
				Map checkMcListOfNotContMap = new HashMap();
				List<Integer> mcListOfNotContList = new ArrayList<Integer>();
				
				// 제외수 중복제크 Map, List
				Map checkExcludeNumber = new HashMap();
				List<Integer> excludeNumberList = new ArrayList<Integer>();
				
				// 미출궁합수에 없는 미출수목록1
				List<Integer> excludeList1 = new ArrayList<Integer>();
				
				// 미출수목록1의 궁합수 중복제크 Map, List
				Map excludeList2Map = new HashMap();
				List<Integer> excludeList2 = new ArrayList<Integer>();
				
				// 제외수 궁합수 중 미출수
				int excludeNumOfMcNum = 0;
				int maxCount = 0;
				
				// 제외수 궁합수 중 포함할 미출수
				Map excludeList3Map = new HashMap();
				List<Integer> excludeList3 = new ArrayList<Integer>();
				
				// 전체제외수 중복제크 Map, List
				Map checkAllExcludeNumMap = new HashMap();
				List<Integer> allExcludeNumList = new ArrayList<Integer>();
				
				
				WinDataDto wdd = winDataList.get(i);
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 계산 시작");				
				
				/*****************************************************
				 * 1. 최근 10회동안 미출한 번호들의 궁합수 목록을 구함
				 *****************************************************/
				// 10회차 포함번호 목록 조회
				List<Integer> contain10List = lottoDataService.getContain10List(winDataList, i);
				// 10회차 미포함번호 목록 조회
				List<Integer> notContain10List = lottoDataService.getNotContain10List(contain10List);
				
				for (int j = 0; j < notContain10List.size(); j++) {
					int notContainNum = notContain10List.get(j);
					
					for (int k = 0; k < mcNumList.size(); k++) {
						MCNumDto mCNumDto = mcNumList.get(k);
						int mcNum = mCNumDto.getNum();
						if (notContainNum == mcNum) {
							// 궁합수 문자열을 int 배열로 변환
							int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
							
							for (int l = 0; l < mcNumbers.length; l++) {
								int mcNumOfexcludeNum = mcNumbers[l];
								
								// 미출수의 궁합수를 미출궁합수 목록에 등록
								if (!checkMcListOfNotContMap.containsKey(mcNumOfexcludeNum)) {
									checkMcListOfNotContMap.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
									mcListOfNotContList.add(mcNumOfexcludeNum);
								}
							}
							
							// 현재 제외수의 궁합수 반복 중단처리
							break;
						}
					}
				}
				mcListOfNotContList = (List<Integer>) LottoUtil.dataSort(mcListOfNotContList);
				
				/*****************************************************
				 * 2. 1번 중 미출수에 없는 번호 선별
				 *****************************************************/
				for (int j = 0; j < notContain10List.size(); j++) {
					boolean exist = false;
					int notContainNum = notContain10List.get(j);
					
					for (int k = 0; k < mcListOfNotContList.size(); k++) {
						int mcNum = mcListOfNotContList.get(k);
						
						if (notContainNum == mcNum) {
							exist = true;
							break;
						}
					}
					
					if (!exist) {
						excludeList1.add(notContainNum);
					}
				}
				
				/*****************************************************
				 * 3. 미출수의 궁합수 중 2번째까지만 선별
				 *****************************************************/
				for (int j = 0; j < excludeList1.size(); j++) {
					int excludeNum = excludeList1.get(j);
					
					for (int k = 0; k < mcNumList.size(); k++) {
						MCNumDto mCNumDto = mcNumList.get(k);
						int mcNum = mCNumDto.getNum();
						if (excludeNum == mcNum) {
							// 궁합수 문자열을 int 배열로 변환
							int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
							
							// 2번째까지만 설정
							for (int l = 0; l < (mcNumbers.length > 2 ? 2 : mcNumbers.length); l++) {
								int mcNumOfexcludeNum = mcNumbers[l];
								
								// 미출수목록1의 궁합수를 미출수목록2에 등록
								if (!excludeList2Map.containsKey(mcNumOfexcludeNum)) {
									excludeList2Map.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
									excludeList2.add(mcNumOfexcludeNum);
								}
							}
							
							// 현재 제외수의 궁합수 반복 중단처리
							break;
						}
					}
				}
				
				/*****************************************************
				 * 4. 제외수의 궁합수 목록을 구함.
				 *****************************************************/
				// 제외수 조회
				ExDataDto exDataDto = new ExDataDto();
				exDataDto.setEx_count(wdd.getWin_count());
				ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
				
				// 제외수 문자열을 int 배열로 변환
				int[] excludeNumbers = LottoUtil.getNumbers(excludeDto.getExclude_num().replaceAll(" ", ""));
				
				// 제외수의 궁합수 목록을 추출
				for (int j = 0; j < excludeNumbers.length; j++) {
					int excludeNum = excludeNumbers[j];
					
					if (!checkExcludeNumber.containsKey(excludeNum)) {
						checkExcludeNumber.put(excludeNum, excludeNum);
						excludeNumberList.add(excludeNum);
					}
					
					for (int k = 0; k < mcNumList.size(); k++) {
						MCNumDto mCNumDto = mcNumList.get(k);
						int mcNum = mCNumDto.getNum();
						if (excludeNum == mcNum) {
							// 궁합수 문자열을 int 배열로 변환
							int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
							
							for (int l = 0; l < mcNumbers.length; l++) {
								int mcNumOfexcludeNum = mcNumbers[l];
								
								// 제외수의 궁합수를 목록에 등록
								if (!checkExcludeNumber.containsKey(mcNumOfexcludeNum)) {
									checkExcludeNumber.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
									excludeNumberList.add(mcNumOfexcludeNum);
								}
							}
							
							// 현재 제외수의 궁합수 반복 중단처리
							break;
						}
					}
					
				} // end 제외수 목록 반복
				
				/*****************************************************
				 * 5. 제외수 궁합수 목록에서 미출수에 포함된 번호 중 가장 오래된 번호는 제외
				 * 6. 5번의 나머지 번호중 미출수에 있는 번호는 추가
				 *****************************************************/
				for (int j = 0; j < excludeNumberList.size(); j++) {
					int excludeNum = excludeNumberList.get(j);
					
					for (int k = 0; k < notContain10List.size(); k++) {
						int notContainNum = notContain10List.get(k);
						
						if (excludeNum == notContainNum) {
							
							// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
							if (!excludeList3Map.containsKey(notContainNum)) {
								excludeList3Map.put(notContainNum, notContainNum);
								excludeList3.add(notContainNum);
							}
							
							int checkCount = 0;
							
							// 이전 출현회수 비교하여 가장 오래된 미출수 선별
							for (int l = i - 1; l >= 0; l--) {
								checkCount++;
								
								WinDataDto bfWinData = winDataList.get(l);
								
								if (notContainNum == bfWinData.getNum1()
										|| notContainNum == bfWinData.getNum2()
										|| notContainNum == bfWinData.getNum3()
										|| notContainNum == bfWinData.getNum4()
										|| notContainNum == bfWinData.getNum5()
										|| notContainNum == bfWinData.getNum6()
										) {
									break;
								}
							}
							
							if (excludeNumOfMcNum == 0) {
								excludeNumOfMcNum = notContainNum;
								maxCount = checkCount;
							} else {
								// 기존과 비교 (가장 오래된 미출수 선별)
								if (maxCount < checkCount) {
									excludeNumOfMcNum = notContainNum;
									maxCount = checkCount;
								}
							}
							
							break;
						}
					}
				}
				
				/*****************************************************
				 * 7. 전체 제외수 목록을 구함.
				 *    (미출수 & 미출수 궁합수 + 제외수)
				 *****************************************************/
				for (int j = 0; j < excludeList1.size(); j++) {
					int excludeNum = excludeList1.get(j);
					
					// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
					if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
						checkAllExcludeNumMap.put(excludeNum, excludeNum);
						allExcludeNumList.add(excludeNum);
					}
				}
				
				for (int j = 0; j < excludeList2.size(); j++) {
					int excludeNum = excludeList2.get(j);
					
					// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
					if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
						checkAllExcludeNumMap.put(excludeNum, excludeNum);
						allExcludeNumList.add(excludeNum);
					}
				}
				
				for (int j = 0; j < excludeList3.size(); j++) {
					int excludeNum = excludeList3.get(j);
					
					// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
					if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
						checkAllExcludeNumMap.put(excludeNum, excludeNum);
						allExcludeNumList.add(excludeNum);
					}
				}
				
				for (int j = 0; j < excludeNumbers.length; j++) {
					int excludeNum = excludeNumbers[j]; 
					
					// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
					if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
						checkAllExcludeNumMap.put(excludeNum, excludeNum);
						allExcludeNumList.add(excludeNum);
					}
				}
				
				allExcludeNumList = (List<Integer>) LottoUtil.dataSort(allExcludeNumList);
				
				// 존재하면, 제외수 궁합수 중 포함할 미출수 목록에서 제거 
				if (excludeNumOfMcNum > 0) {
					for (int k = 0; k < allExcludeNumList.size(); k++) {
						int excludeNum = allExcludeNumList.get(k);
						if (excludeNumOfMcNum == excludeNum) {
							allExcludeNumList.remove(k);
							break;
						}
					}
					log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 장기 미출수는 제외수에서 미포함 : " + excludeNumOfMcNum);
				}
				
				
				// 결과 확인
				String modiExcludeNum = "";
				for (int j = 0; j < allExcludeNumList.size(); j++) {
					int excludeNum = allExcludeNumList.get(j);
					if (!"".equals(modiExcludeNum)) {
						modiExcludeNum += ",";
					}
					modiExcludeNum = "" + modiExcludeNum + excludeNum;
				}
				
				// 적중률 계산
				// 전체제외수 중 당첨번호 출현 확인
				int appearCnt = 0;
				String appearNumbers = "";
				int[] winNumbers = LottoUtil.getNumbersFromObj(wdd);
				for (int j = 0; j < winNumbers.length; j++) {
					for (int j2 = 0; j2 < allExcludeNumList.size(); j2++) {
						if (winNumbers[j] == (int) allExcludeNumList.get(j2)) {
							appearCnt++;
							
							if (!"".equals(appearNumbers)) {
								appearNumbers += ",";
							}
							appearNumbers = "" + appearNumbers + winNumbers[j];
							break;
						}
					}
				}
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 제외수 : " + excludeDto.getExclude_num());
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 개선된 제외수 : " + modiExcludeNum);
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 출현번호 : " + appearNumbers);
				
				// 적중률 누적
				double rate = 100 - (appearCnt * 1.0 / allExcludeNumList.size() * 100);				
				totRate += rate;
				
				
				log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 제외수 적중률 : " + rate + "%");
				// 적중회수 확인
				if (rate > baseRate) {
					succCount++;
				}
				
				checkWinCount++;
			} // end 당첨회차 순으로 적중률 확인
			
			// 전체 적중률 확인
			double totAvgRate = totRate / checkWinCount;
			log.info("[" + loginUserNo + "]\t " + baseRate + "% 이상 적중 회수 = " + succCount);
			log.info("[" + loginUserNo + "]\t 전체 적중률 = " + totAvgRate);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "테스트했습니다.");
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 특정회차 제외수 테스트
	 * 2020.02.06
	 * 
	 * <ol>
	 * <li>최근 10회동안 미출한 번호들의 궁합수 목록을 구함.</li>
	 * <li>1번 중 미출수에 없는 번호 선별.</li>
	 * <li>미출수의 궁합수 중 2번째까지만 선별.</li>
	 * <li>제외수의 궁합수 목록을 구함.</li>
	 * <li>제외수 궁합수 목록에서 미출수에 포함된 번호 중 가장 오래된 번호는 제외.</li>
	 * <li>5번의 나머지 번호중 미출수에 있는 번호는 추가.</li>
	 * <li>2+3+6의 목록을 구함.</li>
	 * <li>적중률 계산.</li>
	 * <li>비교 시작은 30회 부터 시작. (최하 20회 이상 미출수로 선별해야함.).</li>
	 * </ol>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/test/testExcludeCount")
	public void testExcludeCount(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
			
			String ex_count       = WebUtil.replaceParam(request.getParameter("ex_count"), "");
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 특정회차 제외수 테스트");
			String accessip = request.getRemoteHost();
			log.info("[" + loginUserNo + "] accessip = " + accessip);
			
			
			// 당첨번호 전체 목록 조회
			log.info("[" + loginUserNo + "]\t 당첨번호 전체 목록 조회 (오름차순)");
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근회차 조회 
			WinDataDto lastWinData = winDataList.get(winDataList.size()-1);
			log.info("[" + loginUserNo + "]\t 최근회차 조회 : " + lastWinData.getWin_count());
			
			// 궁합수 조회 (최근회차)
			log.info("[" + loginUserNo + "]\t 궁합수 조회 (최근회차)");
			WinDataDto dtoForMC = new WinDataDto();
			dtoForMC.setWin_count(lastWinData.getWin_count());
			List<MCNumDto> mcNumList = sysmngService.getMcNumList(dtoForMC);
			
			int exCount = 0;
			
			if ("".equals(ex_count)) {
				exCount = lastWinData.getWin_count() + 1;
			} else {
				exCount = Integer.parseInt(ex_count);
			}
			
			// 미출궁합수 중복제크 Map, List
			Map checkMcListOfNotContMap = new HashMap();
			List<Integer> mcListOfNotContList = new ArrayList<Integer>();
			
			// 제외수 중복제크 Map, List
			Map checkExcludeNumber = new HashMap();
			List<Integer> excludeNumberList = new ArrayList<Integer>();
			
			// 미출궁합수에 없는 미출수목록1
			List<Integer> excludeList1 = new ArrayList<Integer>();
			
			// 미출수목록1의 궁합수 중복제크 Map, List
			Map excludeList2Map = new HashMap();
			List<Integer> excludeList2 = new ArrayList<Integer>();
			
			// 제외수 궁합수 중 미출수
			int excludeNumOfMcNum = 0;
			int maxCount = 0;
			
			// 제외수 궁합수 중 포함할 미출수
			Map excludeList3Map = new HashMap();
			List<Integer> excludeList3 = new ArrayList<Integer>();
			
			// 전체제외수 중복제크 Map, List
			Map checkAllExcludeNumMap = new HashMap();
			List<Integer> allExcludeNumList = new ArrayList<Integer>();
				
				
			/*****************************************************
			 * 1. 최근 10회동안 미출한 번호들의 궁합수 목록을 구함
			 *****************************************************/
			// 10회차 포함번호 목록 조회
			List<Integer> contain10List = lottoDataService.getContain10List(winDataList, lastWinData.getWin_count());
			// 10회차 미포함번호 목록 조회
			List<Integer> notContain10List = lottoDataService.getNotContain10List(contain10List);
			
			for (int j = 0; j < notContain10List.size(); j++) {
				int notContainNum = notContain10List.get(j);
				
				for (int k = 0; k < mcNumList.size(); k++) {
					MCNumDto mCNumDto = mcNumList.get(k);
					int mcNum = mCNumDto.getNum();
					if (notContainNum == mcNum) {
						// 궁합수 문자열을 int 배열로 변환
						int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
						
						for (int l = 0; l < mcNumbers.length; l++) {
							int mcNumOfexcludeNum = mcNumbers[l];
							
							// 미출수의 궁합수를 미출궁합수 목록에 등록
							if (!checkMcListOfNotContMap.containsKey(mcNumOfexcludeNum)) {
								checkMcListOfNotContMap.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
								mcListOfNotContList.add(mcNumOfexcludeNum);
							}
						}
						
						// 현재 제외수의 궁합수 반복 중단처리
						break;
					}
				}
			}
			mcListOfNotContList = (List<Integer>) LottoUtil.dataSort(mcListOfNotContList);
			
			/*****************************************************
			 * 2. 1번 중 미출수에 없는 번호 선별
			 *****************************************************/
			for (int j = 0; j < notContain10List.size(); j++) {
				boolean exist = false;
				int notContainNum = notContain10List.get(j);
				
				for (int k = 0; k < mcListOfNotContList.size(); k++) {
					int mcNum = mcListOfNotContList.get(k);
					
					if (notContainNum == mcNum) {
						exist = true;
						break;
					}
				}
				
				if (!exist) {
					excludeList1.add(notContainNum);
				}
			}
			
			/*****************************************************
			 * 3. 미출수의 궁합수 중 2번째까지만 선별
			 *****************************************************/
			for (int j = 0; j < excludeList1.size(); j++) {
				int excludeNum = excludeList1.get(j);
				
				for (int k = 0; k < mcNumList.size(); k++) {
					MCNumDto mCNumDto = mcNumList.get(k);
					int mcNum = mCNumDto.getNum();
					if (excludeNum == mcNum) {
						// 궁합수 문자열을 int 배열로 변환
						int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
						
						// 2번째까지만 설정
						for (int l = 0; l < (mcNumbers.length > 2 ? 2 : mcNumbers.length); l++) {
							int mcNumOfexcludeNum = mcNumbers[l];
							
							// 미출수목록1의 궁합수를 미출수목록2에 등록
							if (!excludeList2Map.containsKey(mcNumOfexcludeNum)) {
								excludeList2Map.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
								excludeList2.add(mcNumOfexcludeNum);
							}
						}
						
						// 현재 제외수의 궁합수 반복 중단처리
						break;
					}
				}
			}
			
			/*****************************************************
			 * 4. 제외수의 궁합수 목록을 구함.
			 *****************************************************/
			// 제외수 조회
			ExDataDto exDataDto = new ExDataDto();
			exDataDto.setEx_count(exCount);
			ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
			
			// 제외수 문자열을 int 배열로 변환
			int[] excludeNumbers = LottoUtil.getNumbers(excludeDto.getExclude_num().replaceAll(" ", ""));
			
			// 제외수의 궁합수 목록을 추출
			for (int j = 0; j < excludeNumbers.length; j++) {
				int excludeNum = excludeNumbers[j];
				
				if (!checkExcludeNumber.containsKey(excludeNum)) {
					checkExcludeNumber.put(excludeNum, excludeNum);
					excludeNumberList.add(excludeNum);
				}
				
				for (int k = 0; k < mcNumList.size(); k++) {
					MCNumDto mCNumDto = mcNumList.get(k);
					int mcNum = mCNumDto.getNum();
					if (excludeNum == mcNum) {
						// 궁합수 문자열을 int 배열로 변환
						int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
						
						for (int l = 0; l < mcNumbers.length; l++) {
							int mcNumOfexcludeNum = mcNumbers[l];
							
							// 제외수의 궁합수를 목록에 등록
							if (!checkExcludeNumber.containsKey(mcNumOfexcludeNum)) {
								checkExcludeNumber.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
								excludeNumberList.add(mcNumOfexcludeNum);
							}
						}
						
						// 현재 제외수의 궁합수 반복 중단처리
						break;
					}
				}
				
			} // end 제외수 목록 반복
			
			/*****************************************************
			 * 5. 제외수 궁합수 목록에서 미출수에 포함된 번호 중 가장 오래된 번호는 제외
			 * 6. 5번의 나머지 번호중 미출수에 있는 번호는 추가
			 *****************************************************/
			for (int j = 0; j < excludeNumberList.size(); j++) {
				int excludeNum = excludeNumberList.get(j);
				
				for (int k = 0; k < notContain10List.size(); k++) {
					int notContainNum = notContain10List.get(k);
					
					if (excludeNum == notContainNum) {
						
						// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
						if (!excludeList3Map.containsKey(notContainNum)) {
							excludeList3Map.put(notContainNum, notContainNum);
							excludeList3.add(notContainNum);
						}
						
						int checkCount = 0;
						
						// 이전 출현회수 비교하여 가장 오래된 미출수 선별
						for (int l = lastWinData.getWin_count() - 1; l >= 0; l--) {
							checkCount++;
							
							WinDataDto bfWinData = winDataList.get(l);
							
							if (notContainNum == bfWinData.getNum1()
									|| notContainNum == bfWinData.getNum2()
									|| notContainNum == bfWinData.getNum3()
									|| notContainNum == bfWinData.getNum4()
									|| notContainNum == bfWinData.getNum5()
									|| notContainNum == bfWinData.getNum6()
									) {
								break;
							}
						}
						
						if (excludeNumOfMcNum == 0) {
							excludeNumOfMcNum = notContainNum;
							maxCount = checkCount;
						} else {
							// 기존과 비교 (가장 오래된 미출수 선별)
							if (maxCount < checkCount) {
								excludeNumOfMcNum = notContainNum;
								maxCount = checkCount;
							}
						}
						
						break;
					}
				}
			}
			
			/*****************************************************
			 * 7. 전체 제외수 목록을 구함.
			 *    (미출수 & 미출수 궁합수 + 제외수)
			 *****************************************************/
			for (int j = 0; j < excludeList1.size(); j++) {
				int excludeNum = excludeList1.get(j);
				
				// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
				if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
					checkAllExcludeNumMap.put(excludeNum, excludeNum);
					allExcludeNumList.add(excludeNum);
				}
			}
			
			for (int j = 0; j < excludeList2.size(); j++) {
				int excludeNum = excludeList2.get(j);
				
				// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
				if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
					checkAllExcludeNumMap.put(excludeNum, excludeNum);
					allExcludeNumList.add(excludeNum);
				}
			}
			
			for (int j = 0; j < excludeList3.size(); j++) {
				int excludeNum = excludeList3.get(j);
				
				// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
				if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
					checkAllExcludeNumMap.put(excludeNum, excludeNum);
					allExcludeNumList.add(excludeNum);
				}
			}
			
			for (int j = 0; j < excludeNumbers.length; j++) {
				int excludeNum = excludeNumbers[j]; 
				
				// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
				if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
					checkAllExcludeNumMap.put(excludeNum, excludeNum);
					allExcludeNumList.add(excludeNum);
				}
			}
			
			allExcludeNumList = (List<Integer>) LottoUtil.dataSort(allExcludeNumList);
			
			// 존재하면, 제외수 궁합수 중 포함할 미출수 목록에서 제거 
			if (excludeNumOfMcNum > 0) {
				for (int k = 0; k < allExcludeNumList.size(); k++) {
					int excludeNum = allExcludeNumList.get(k);
					if (excludeNumOfMcNum == excludeNum) {
						allExcludeNumList.remove(k);
						break;
					}
				}
				log.info("[" + loginUserNo + "]\t\t " + exCount + "회차의 장기 미출수는 제외수에서 미포함 : " + excludeNumOfMcNum);
			}
			
			
			/*****************************************************
			 * 8. 연번 규칙 적용
			 *    (연번 출현 시 다음회차의 +2, -2 번호 1개는 출현)
			 *****************************************************/
			int[] numbers = LottoUtil.getNumbers(lastWinData);
			for (int i = 0; i < numbers.length-1; i++) {
				if (numbers[i+1] - numbers[i] == 1) {
					// 끝수 존재여부 체크
					if (i+2 < numbers.length) {
						if(numbers[i+2] - numbers[i+1] == 1) {
							// 다음수가 3연속은 규칙 제외
							continue;
						}
					} else if (i-1 >= 0) {
						if(numbers[i] - numbers[i-1] == 1) {
							// 이전수가 3연속은 규칙 제외
							continue;
						}
					}
					
					// 연번
					List<Integer> list = new ArrayList<Integer>();
					list.add(numbers[i] - 2);
					list.add(numbers[i] + 2);
					list.add(numbers[i+1] - 2);
					list.add(numbers[i+1] + 2);
					
					for (int j = 0; j < list.size(); j++) {
						int consecutivelyNumber = list.get(j);
						
						for (int k = 0; k < allExcludeNumList.size(); k++) {
							int excludeNum = allExcludeNumList.get(k);
							if (consecutivelyNumber == excludeNum) {
								allExcludeNumList.remove(k);
								break;
							}
						}
					}
				}
			}
			
			/*****************************************************
			 * 9. 2회 전회차의 1구간 3개수 규칙 적용
			 *    (연번 출현 시 다음회차의 +2, -2 번호 1개는 출현)
			 *****************************************************/
			WinDataDto checkDto = winDataList.get(winDataList.size()-2);
			int[] containGroupCnt = lottoDataService.getZeroCntRangeData(checkDto);
			boolean isCheck = false;
			int checkRangeCnt = 0;
			for (int i = 0; i < containGroupCnt.length; i++) {
				if (containGroupCnt[i] == 3) {
					isCheck = true;
					checkRangeCnt = i;
					break;
				}
			}

			if (isCheck) {
				int[] bf2CountNumbers = LottoUtil.getNumbers(checkDto);
				int[] compareNumbers = new int[3];
				int cnt = 0;
				
				int startIdx = 10 * checkRangeCnt;
				if (checkRangeCnt < 4) {
					int endIdx = 10 * (checkRangeCnt+1);
					
					for (int i = 0; i < bf2CountNumbers.length; i++) {
						if (startIdx < bf2CountNumbers[i] 
								&& bf2CountNumbers[i] <= endIdx) {
							compareNumbers[cnt++] = bf2CountNumbers[i];
						}
					}
				} else {
					int endIdx = 10 * checkRangeCnt + 5;
					for (int i = 0; i < bf2CountNumbers.length; i++) {
						if (startIdx < bf2CountNumbers[i] 
								&& bf2CountNumbers[i] <= endIdx) {
							compareNumbers[cnt++] = bf2CountNumbers[i];
						}
					}
				}
				
				// 3연속 수의 차이수
				List<Integer> list = new ArrayList<Integer>();
				list.add(compareNumbers[2] - compareNumbers[1]);
				list.add(compareNumbers[1] - compareNumbers[0]);
				
				for (int j = 0; j < list.size(); j++) {
					int difNumber = list.get(j);
					
					for (int k = 0; k < allExcludeNumList.size(); k++) {
						int excludeNum = allExcludeNumList.get(k);
						if (difNumber == excludeNum) {
							allExcludeNumList.remove(k);
							break;
						}
					}
				}
			}
			
			// 결과 확인
			String modiExcludeNum = "";
			for (int j = 0; j < allExcludeNumList.size(); j++) {
				int excludeNum = allExcludeNumList.get(j);
				if (!"".equals(modiExcludeNum)) {
					modiExcludeNum += ",";
				}
				modiExcludeNum = "" + modiExcludeNum + excludeNum;
			}
			
			log.info("[" + loginUserNo + "]\t\t " + exCount + "회차 제외수 : " + excludeDto.getExclude_num());
			log.info("[" + loginUserNo + "]\t\t " + exCount + "회차 개선된 제외수 : " + modiExcludeNum);
				
			jsonObj.put("status", "success");
			jsonObj.put("msg", "테스트했습니다.");
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설1 검증
	 * 
	 * 가설1. 10차이나는 수 사이에 다른 숫자가 있으면, 다음회차에서 큰 수에서 중간수를 뺀 끝수가 출현한다. 
	 * 
	 * 2020.02.22 검증
	 * 1회부터 확인 : 전체출현횟수 = 194, 일치횟수 = 107, 정확도 55%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 8, 일치횟수 = 7, 정확도 88%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory1")
	public void testTheory1(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
	  		//ORACLE 인 경우 대문자 설정
	  		if ("ORACLE".equals(systemInfo.getDatabase())) {
	  			dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
	  		}
	  		
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설1 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;
				int appearCnt = 0;
				boolean isCheckAppear = false;
				boolean isCheckMatch = false;
				for (int i = 0; i < sourceNumbers.length - 2; i++) {
					isCheckAppear = false;
					int num1 = sourceNumbers[i];
					int num2 = sourceNumbers[i+1];
					int num3 = sourceNumbers[i+2];
					
					if (num3 - num1 == 10) {
						log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 " + num1 + ", " + num3 + " 존재");
						isAppear = true;
						isCheckAppear = true;
						appearCnt++;
						
						int difNum = num3 - num2;
						int difAppearCnt = 0;
						
						log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 차이수 = " + difNum);
						for (int j = 0; j < targetNumbers.length; j++) {
							if (targetNumbers[j] % 10 == difNum) {
								log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 " + targetNumbers[j] + " 존재함");
								difAppearCnt++;
							}
						}
						
						if (difAppearCnt > 0) { 
							matchedCnt++;
							isCheckMatch = true;
						}
					}
					
					if (isCheckAppear) {
						if (isCheckMatch) {
							log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 일치");
						} else {
							log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 불일치");
						}
					}
				}
				
				if (isAppear) {
					log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 10차이나는 수 출현(" + appearCnt + "개)");
					log.info("[" + loginUserNo + "] ==============================================================");
					allAppearCnt++;
				}
				
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설2 검증
	 * 
	 * 가설2. 단번대 멸 & 첫수가 10번대 라면, 당첨숫자 4, 5, 6번째 중 1개가 이월된다. 
	 * 
	 * 2020.02.22 검증
	 * 1회부터 확인 : 전체출현횟수 = 196, 일치횟수 = 82, 정확도 42%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 6, 일치횟수 = 5, 정확도 83%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory2")
	public void testTheory2(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
	  		//ORACLE 인 경우 대문자 설정
	  		if ("ORACLE".equals(systemInfo.getDatabase())) {
	  			dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
	  		}
	  		
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설2 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] sourceZeroCntRange = lottoDataService.getZeroCntRangeData(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				String numbers = ""; 
				
				// 단번대 멸 체크
				
				if (sourceZeroCntRange[0] == 0) {
					for (int i = 0; i < sourceNumbers.length; i++) {
						if (!"".equals(numbers)) {
							numbers += ",";
						}
						numbers += sourceNumbers[i];
					}
					
					// 첫번째 수가 10번대 체크
//					if (11 <= targetNumbers[0] || targetNumbers[0] <= 20) {
					if (10 <= sourceNumbers[0] || sourceNumbers[0] <= 19) {
						allAppearCnt++;
						
						//지난 당첨번호 3수
						int num4 = sourceNumbers[3];
						int num5 = sourceNumbers[4];
						int num6 = sourceNumbers[5];
						
						//이월여부 체크
						for (int i = 0; i < targetNumbers.length; i++) {
							if (targetNumbers[i] == num4
									|| targetNumbers[i] == num5
									|| targetNumbers[i] == num6
									) {
								isAppear = true;
								
								if (!"".equals(appearNumber)) {
									appearNumber += ",";
								}
								appearNumber += targetNumbers[i]; 
							}
						}
						
						if (isAppear) {
							matchedCnt++;
							log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 (" + numbers + ")");
							log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 이월수 출현(" + appearNumber + ")");
							log.info("[" + loginUserNo + "] ==============================================================");		
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설3 검증
	 * 
	 * 가설3. 20번대(20~29) 2개 & 30번대(30~39) 2개가 나오면, 다음 회차에서는 0끝수가 1개 이상 출현한다. 
	 * 
	 * 2020.02.22 검증
	 * 1회부터 확인 : 전체출현횟수 = 66, 일치횟수 = 33, 정확도 50%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 2, 일치횟수 = 2, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory3")
	public void testTheory3(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설3 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				int appear2RangeCnt = 0;
				int appear3RangeCnt = 0;
				
				String numbers = "";
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (20 <= sourceNumbers[i] && sourceNumbers[i] <= 29) {
						appear2RangeCnt++;
					}
					if (30 <= sourceNumbers[i] && sourceNumbers[i] <= 39) {
						appear3RangeCnt++;
					}
					
					if (!"".equals(numbers)) {
						numbers += ",";
					}
					numbers += sourceNumbers[i];
				}
				
				
				if (appear2RangeCnt == 2 && appear3RangeCnt == 2) {
					allAppearCnt++;
					
					log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 (" + numbers + ")");
					log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 구간수 출현(" + appear2RangeCnt + ", " + appear3RangeCnt + ")");
					
					// 0끝수 출현여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == 0) {
							isAppear = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (isAppear) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 0끝수 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설4 검증
	 * 
	 * 가설4. 42번이 나오면 42의 앞 2번째 수 -1이 출현한다. 
	 * 
	 * 2020.02.22 검증
	 * 1회부터 확인 : 전체출현횟수 = 115, 일치횟수 = 21, 정확도 18%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 6, 일치횟수 = 4, 정확도 67%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory4")
	public void testTheory4(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설4 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				boolean isAppear42 = false;	// 42출현여부
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 42) {
						isAppear42 = true;
						if (i >= 2) {
							// 앞 2번째 수 - 1
							checkNumber = sourceNumbers[i-2] - 1; 
						}
						break;
					}
				}
				
				if (isAppear42) {
					allAppearCnt++;
					
					// 앞 2번째 수-1 출현여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] == checkNumber) {
							isAppear = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (isAppear) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 앞 2번째 수-1 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설5 검증
	 * 
	 * 가설5. 28번이 출현하면, 4끝수가 출현한다.
	 * 
	 * 2020.02.22 검증
	 * 1회부터 확인 : 전체출현횟수 = 112, 일치횟수 = 112, 정확도 100%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 5, 일치횟수 = 5, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory5")
	public void testTheory5(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설5 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 28) {
						isAppear = true;
						break;
					}
				}
				
				if (isAppear) {
					allAppearCnt++;
					
					boolean isAppear2 = false;	// 출현여부
					// 4끝수 출현여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == 4) {
							isAppear2 = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (isAppear2) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 4끝수 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설6 검증
	 * 
	 * 가설6. 19번호 이후 0끝 번호가 출현한다. 
	 * 		  뒤에 (1구간 3수)가 나오면 뒤에는 0끝이 출현하지 않는다. (2020.02.29)
	 * 
	 * 2020.02.23 검증
	 * 1회부터 확인 : 전체출현횟수 = 126, 일치횟수 = 57, 정확도 45%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 5, 일치횟수 = 2, 정확도 40%
	 * 
	 * 2020.02.29 2차 검증
	 * 1회부터 확인 : 전체출현횟수 = 126, 일치횟수 = 72, 정확도 57%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 5, 일치횟수 = 4, 정확도 80%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory6")
	public void testTheory6(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설6 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 19) {
						isAppear = true;
						break;
					}
				}
				
				if (isAppear) {
					allAppearCnt++;
				
					boolean isAppear2 = false;	// 출현여부
					// 0끝수 출현여부 체크
					boolean is1Range3CNumbers = lottoDataService.check1Range3Numbers(sourceNumbers);
//					boolean is3ConsecutivelyNumbers = lottoDataService.check3ConsecutivelyNumbers(sourceNumbers);
					String msg = "";
					if (is1Range3CNumbers) {
						// 3연수 있음.
						int appearCnt = 0;
						for (int i = 0; i < targetNumbers.length; i++) {
							if (targetNumbers[i] % 10 == 0) {
								appearCnt++;
							}
						}
						
						if (appearCnt == 0) {
							isAppear2 = true;
							
							msg = "3연수 있음. 0끝수 미출현";
						}
						
					} else {
						// 3연수 없음.
						for (int i = 0; i < targetNumbers.length; i++) {
							if (targetNumbers[i] % 10 == 0) {
								isAppear2 = true;
								
								if (!"".equals(appearNumber)) {
									appearNumber += ",";
								}
								appearNumber += targetNumbers[i];
								break;
							}
						}
						
						msg = "0끝수 출현(" + appearNumber + ")";
					}
					
					if (isAppear2) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 " + msg);
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설7 검증
	 * 
	 * 가설7. 쌍수(11,22,33,44)가 생겼는데 부작용이 생겨, 다시 한 번 자기번호나 가족번호(끝수가 같은)가 출현한다. 
	 * 
	 * 2020.02.23 검증
	 * 1회부터 확인 : 전체출현횟수 = 385, 일치횟수 = 224, 정확도 58%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 8, 일치횟수 = 8, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory7")
	public void testTheory7(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설7 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 11
							|| sourceNumbers[i] == 22
							|| sourceNumbers[i] == 33
							|| sourceNumbers[i] == 44
							) {
						isAppear = true;
						checkNumber = sourceNumbers[i]; 
						break;
					}
				}
				
				if (isAppear) {
					allAppearCnt++;
					
					boolean isAppear2 = false;	// 출현여부
					// 0끝수 출현여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == checkNumber % 10) {
							isAppear2 = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (isAppear2) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 쌍수의 가족수 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설8 검증
	 * 
	 * 가설8. 8번이 출현하면, 8배수가 출현한다. 
	 * 
	 * 2020.02.23 검증
	 * 1회부터 확인 : 전체출현횟수 = 121, 일치횟수 = 72, 정확도 60%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 1, 일치횟수 = 1, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory8")
	public void testTheory8(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설8 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 8) {
						isAppear = true;
						break;
					}
				}
				
				if (isAppear) {
					allAppearCnt++;
					
					boolean isAppear2 = false;	// 출현여부
					// 0끝수 출현여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 8 == 0) {
							isAppear2 = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (targetWinDataDto.getBonus_num() % 8 == 0) {
						isAppear2 = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetWinDataDto.getBonus_num(); 
					}
					
					if (isAppear2) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 8배수 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설9 검증
	 * 
	 * 가설9. 3연속수가 출현하면, 1구간 3수가 출현한다. 
	 * 
	 * 2020.02.23 검증
	 * 1회부터 확인 : 전체출현횟수 = 50, 일치횟수 = 20, 정확도 40%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 1, 일치횟수 = 1, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory9")
	public void testTheory9(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설9 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				int[] targetZeroCntRange = lottoDataService.getZeroCntRangeData(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String consecutivelyNumbers = ""; 
				String appearNumber = ""; 
				
				int checkNumber = 0;
				
				for (int i = 0; i < sourceNumbers.length - 2; i++) {
					if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
						consecutivelyNumbers += sourceNumbers[i];
						
						if (!"".equals(consecutivelyNumbers)) {
							consecutivelyNumbers += ",";
						}
						consecutivelyNumbers += sourceNumbers[i+1];
						
						// 끝수 존재여부 체크
						if (i+2 < sourceNumbers.length) {
							if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
								// 다음수가 3연속 확인
								isAppear = true;
								
								if (!"".equals(consecutivelyNumbers)) {
									consecutivelyNumbers += ",";
								}
								consecutivelyNumbers += sourceNumbers[i+2];
								
								break;
							}
						}
					}
				}
				
				if (isAppear) {
					allAppearCnt++;
					
					boolean isAppear2 = false;	// 출현여부
					
					// 1구간 3수 출현여부 체크
					String[] rangeTitle = {"1~10","11~20","21~30","31~40","41~45"};
					int appearIndex = 0;
					for (int i = 0; i < targetZeroCntRange.length; i++) {
						if (targetZeroCntRange[i] == 3) {
							isAppear2 = true;
							appearIndex = i;
						}
					}
					
					if (isAppear2) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 " + rangeTitle[appearIndex] + "구간 3수 출현");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설10 검증
	 * 
	 * 가설10. 40번대 멸 이면, 당첨번호의 앞뒤 바뀐수가 출현한다.(예: 21 <-> 12)
	 * 
	 * 2020.02.29 검증
	 * 1회부터 확인 : 전체출현횟수 = 424, 일치횟수 = 133, 정확도 31%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 8, 일치횟수 = 4, 정확도 50%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory10")
	public void testTheory10(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설10 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] sourceZeroCntRange = lottoDataService.getZeroCntRangeData(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				// 40대 멸 체크
				if (sourceZeroCntRange[4] == 0) {
					allAppearCnt++;
					
					// 출현번호의 역번호 Map 설정 (출현여부 확인용)
					Map<Integer, Integer> reverseNumberMap = new HashMap<Integer, Integer>(); 
					for (int i = 0; i < sourceNumbers.length; i++) {
						int number = sourceNumbers[i];
						// 2자리수만 확인
						if (number >= 10) {
							String strNumber = String.valueOf(number);
							String strReverseNumber = strNumber.substring(1, 2) + strNumber.substring(0, 1);
							int reverseNumber = Integer.parseInt(strReverseNumber);
							if (reverseNumber <= 45) {
								reverseNumberMap.put(reverseNumber, reverseNumber);
							}
						}
					}
					
					for (int i = 0; i < targetNumbers.length; i++) {
						if (reverseNumberMap.containsKey(targetNumbers[i])) {
							isAppear = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
						}
					}
					
					if (isAppear) {
						matchedCnt++;
						log.info("[" + loginUserNo + "] " + (countIdx+1) + "회 40번대 멸 (" + (sourceZeroCntRange[4] == 0) + ")");
						log.info("[" + loginUserNo + "] " + (countIdx+2) + "회 역번호 출현(" + appearNumber + ")");
						log.info("[" + loginUserNo + "] ==============================================================");		
					}
				}
				
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설11 검증
	 * 
	 * 가설11. 10구간 3수 출현하면, 마지막 수 합의 배수 출현(예: 18 -> 18, 27, 36, 45)
	 * 
	 * 2020.03.06 검증
	 * 단번수 미포함
	 * 1회부터 확인 : 전체출현횟수 = 86, 일치횟수 = 35, 정확도 41%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 1, 일치횟수 = 1, 정확도 100%
	 * 
	 * 단번수 포함
	 * 1회부터 확인 : 전체출현횟수 = 86, 일치횟수 = 40, 정확도 47%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 1, 일치횟수 = 1, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory11")
	public void testTheory11(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설11 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 체크
				boolean isAppear = false;	// 출현여부
				String appearNumber = ""; 
				
				boolean isRange3Numbers = lottoDataService.checkRange3Numbers(sourceNumbers, 1);
				// 10구간(10 ~ 19) 3수 출현 확인
				if (isRange3Numbers) {
					allAppearCnt++;
					
					int max10RangeNumber = 0; 
					for (int i = 0; i < sourceNumbers.length; i++) {
						int number = sourceNumbers[i];
						// 10구간의 가장 큰 수 설정
						if (10 <= number && number <= 19 ) {
							if (max10RangeNumber < number) {
								max10RangeNumber = number;
							}
						}
					}
					
					String strNumber = String.valueOf(max10RangeNumber);
					int sumNumber = Integer.parseInt(strNumber.substring(1, 2)) + Integer.parseInt(strNumber.substring(0, 1));
					for (int i = 0; i < targetNumbers.length; i++) {
						if (10 <= targetNumbers[i] && targetNumbers[i] % sumNumber == 0) {
//						if (targetNumbers[i] % sumNumber == 0) {
							isAppear = true;
							break;
						}
					}
					
					if (isAppear) {
						matchedCnt++;
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설12 검증
	 * 
	 * 가설12. 38번이 출현하면, 26 또는 29가 출현한다.
	 * 
	 * 2020.03.06 검증
	 * 1회부터 확인 : 전체출현횟수 = 118, 일치횟수 = 33, 정확도 28%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 4, 일치횟수 = 4, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory12")
	public void testTheory12(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설12 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 38번 출현 확인
				boolean isAppear = false;
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 38) {
						allAppearCnt++;
						isAppear = true;
						break;
					}
				}
					
				if (isAppear) {
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] == 26 || targetNumbers[i] == 29) {
							matchedCnt++;
							break;
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설13 검증
	 * 
	 * 가설13. 4회차 전부터 1씩 감소하는 수가 4회 출현 후 다음 2 적은수가 출현하면, 다음 회차에서 빠진수가 출현한다.
	 * 
	 * 2020.03.06 검증
	 * 1회부터 확인 : 전체출현횟수 = 2, 일치횟수 = 1, 정확도 50%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 1, 일치횟수 = 1, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory13")
	public void testTheory13(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설13 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 5; countIdx++) {
				
				WinDataDto source5WinDataDto = winDataList.get(countIdx);
				WinDataDto source4WinDataDto = winDataList.get(countIdx+1);
				WinDataDto source3WinDataDto = winDataList.get(countIdx+2);
				WinDataDto source2WinDataDto = winDataList.get(countIdx+3);
				WinDataDto source1WinDataDto = winDataList.get(countIdx+4);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+5);
				
				int[] source5Numbers = LottoUtil.getNumbers(source5WinDataDto);
				int[] source4Numbers = LottoUtil.getNumbers(source4WinDataDto);
				int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
				int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
				int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 4회차 전부터 1씩 감소하고 다음 2 적은 수 출현여부 확인
				// 시작회차의 첫번째수부터 차례대로 확인
				boolean isAppear = false;
				for (int h = 0; h < source5Numbers.length; h++) {
					for (int i = 0; i < source4Numbers.length; i++) {
						if (source5Numbers[h] - 1 == source4Numbers[i] || source5WinDataDto.getBonus_num() - 1 == source4Numbers[i]) {
							for (int j = 0; j < source3Numbers.length; j++) {
								if (source4Numbers[i] - 1 == source3Numbers[j]) {
									for (int k = 0; k < source2Numbers.length; k++) {
										if (source3Numbers[j] - 1 == source2Numbers[k]) {
											for (int l = 0; l < source1Numbers.length; l++) {
												// 2 적은 수 확인
												if (source2Numbers[k] - 2 == source1Numbers[l]) {
													allAppearCnt++;
													
													// 다음회차에서 빠진수가 출현
													for (int m = 0; m < targetNumbers.length; m++) {
														if (source2Numbers[k] - 1 == targetNumbers[m]) {
															matchedCnt++;
															break;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설14 검증
	 * 
	 * 가설14. 3회차 전부터 1씩 감소하는 수가 3회동안 출현하면, 다음 회차에서 8끝수가 출현한다.
	 * 
	 * 2020.03.07 검증
	 * 1회부터 확인 : 전체출현횟수 = 200, 일치횟수 = 90, 정확도 45%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 30, 일치횟수 = 16, 정확도 53%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory14")
	public void testTheory14(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설14 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 3; countIdx++) {
				
				WinDataDto source3WinDataDto = winDataList.get(countIdx);
				WinDataDto source2WinDataDto = winDataList.get(countIdx+1);
				WinDataDto source1WinDataDto = winDataList.get(countIdx+2);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+3);
				
				int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
				int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
				int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				// 3회차 전부터 1씩 감소하는 수 출현여부 확인
				// 시작회차의 첫번째수부터 차례대로 확인
				boolean isAppear = false;
				for (int j = 0; j < source3Numbers.length; j++) {
					for (int k = 0; k < source2Numbers.length; k++) {
						if (source3Numbers[j] - 1 == source2Numbers[k] || source3WinDataDto.getBonus_num() - 1 == source2Numbers[k]) {
							for (int l = 0; l < source1Numbers.length; l++) {
								if (source2Numbers[k] - 1 == source1Numbers[l]) {
									allAppearCnt++;
									
									// 다음회차에서 8끝수가 출현
									for (int m = 0; m < targetNumbers.length; m++) {
										if (targetNumbers[m] % 10 == 8) {
											matchedCnt++;
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설15 검증
	 * 
	 * 가설15. 28, 42가 출현하면 40번대가 3회연속 출현하지 않음. 보너스 볼까지도 안나옴.4번째 40번대 출현한다.
	 * 
	 * 2020.03.13 검증
	 * 1회부터 확인 : 전체출현횟수 = 14, 일치횟수 = 1, 정확도 7%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 2, 일치횟수 = 0, 정확도 0%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory15")
	public void testTheory15(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설15 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 4; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto1 = winDataList.get(countIdx+1);
				WinDataDto targetWinDataDto2 = winDataList.get(countIdx+2);
				WinDataDto targetWinDataDto3 = winDataList.get(countIdx+3);
				WinDataDto targetWinDataDto4 = winDataList.get(countIdx+4);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers1 = LottoUtil.getNumbers(targetWinDataDto1);
				int[] targetNumbers2 = LottoUtil.getNumbers(targetWinDataDto2);
				int[] targetNumbers3 = LottoUtil.getNumbers(targetWinDataDto3);
				int[] targetNumbers4 = LottoUtil.getNumbers(targetWinDataDto4);
				
				int[] targetZeroCntRange1 = lottoDataService.getZeroCntRangeData(targetWinDataDto1);
				int[] targetZeroCntRange2 = lottoDataService.getZeroCntRangeData(targetWinDataDto2);
				int[] targetZeroCntRange3 = lottoDataService.getZeroCntRangeData(targetWinDataDto3);
				int[] targetZeroCntRange4 = lottoDataService.getZeroCntRangeData(targetWinDataDto4);
				
				
				// 28, 42번 출현 확인
				boolean isAppear = false;
				boolean isAppear28 = false;
				boolean isAppear42 = false;
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 28) {
						isAppear28 = true;
					}
					if (sourceNumbers[i] == 42) {
						isAppear42 = true;
					}
				}
				if (isAppear28 && isAppear42) {
					allAppearCnt++;
					isAppear = true;
					log.info("[" + loginUserNo + "]\t" + sourceWinDataDto.getWin_count() + "회 출현");
				}
					
				if (isAppear) {
					if (targetZeroCntRange1[4] == 0
							&& targetZeroCntRange2[4] == 0
							&& targetZeroCntRange3[4] == 0
							) {
						if (targetZeroCntRange4[4] == 0) {
							log.info("[" + loginUserNo + "]\t" + targetWinDataDto4.getWin_count() + "회 출현!!!");
							matchedCnt++;
						}
					} else {
						log.info("[" + loginUserNo + "]\t\t> 뒤 3회중 40번대가 출현함.");
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설16 검증
	 * 
	 * 가설16. 28, 42가 출현하면 40번대가 3회연속 출현하지 않음. 보너스 볼까지도 안나옴.4번째 쌍수가 출현한다.
	 * 
	 * 2020.03.13 검증
	 * 1회부터 확인 : 전체출현횟수 = 14, 일치횟수 = 2, 정확도 14%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 2, 일치횟수 = 0, 정확도 0%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory16")
	public void testTheory16(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설16 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 4; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto1 = winDataList.get(countIdx+1);
				WinDataDto targetWinDataDto2 = winDataList.get(countIdx+2);
				WinDataDto targetWinDataDto3 = winDataList.get(countIdx+3);
				WinDataDto targetWinDataDto4 = winDataList.get(countIdx+4);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers1 = LottoUtil.getNumbers(targetWinDataDto1);
				int[] targetNumbers2 = LottoUtil.getNumbers(targetWinDataDto2);
				int[] targetNumbers3 = LottoUtil.getNumbers(targetWinDataDto3);
				int[] targetNumbers4 = LottoUtil.getNumbers(targetWinDataDto4);
				
				int[] targetZeroCntRange1 = lottoDataService.getZeroCntRangeData(targetWinDataDto1);
				int[] targetZeroCntRange2 = lottoDataService.getZeroCntRangeData(targetWinDataDto2);
				int[] targetZeroCntRange3 = lottoDataService.getZeroCntRangeData(targetWinDataDto3);
				int[] targetZeroCntRange4 = lottoDataService.getZeroCntRangeData(targetWinDataDto4);
				
				
				// 28, 42번 출현 확인
				boolean isAppear = false;
				boolean isAppear28 = false;
				boolean isAppear42 = false;
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 28) {
						isAppear28 = true;
					}
					if (sourceNumbers[i] == 42) {
						isAppear42 = true;
					}
				}
				if (isAppear28 && isAppear42) {
					allAppearCnt++;
					isAppear = true;
					log.info("[" + loginUserNo + "]\t" + sourceWinDataDto.getWin_count() + "회 출현");
				}
				
				if (isAppear) {
					if (targetZeroCntRange1[4] == 0
							&& targetZeroCntRange2[4] == 0
							&& targetZeroCntRange3[4] == 0
							) {
						for (int i = 0; i < targetNumbers4.length; i++) {
							if (targetNumbers4[i] == 11
									|| targetNumbers4[i] == 22
									|| targetNumbers4[i] == 33
									|| targetNumbers4[i] == 44
									) {
								log.info("[" + loginUserNo + "]\t" + targetWinDataDto4.getWin_count() + "회 쌍수 출현!!!");
								matchedCnt++;
								break;
							}
						}
					} else {
						log.info("[" + loginUserNo + "]\t\t> 뒤 3회중 40번대가 출현함.");
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설17 검증
	 * 
	 * 가설17. 단번대가 1개씩 3회연속 출현하면,4회째에 단번대의 배수가 출현한다.
	 * 
	 * 2020.03.13 검증
	 * 1회부터 확인 : 전체출현횟수 = 44, 일치횟수 = 41, 정확도 93%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 0, 일치횟수 = 0, 정확도 0%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory17")
	public void testTheory17(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설17 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 3; countIdx++) {
				
				WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);
				WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+1);
				WinDataDto sourceWinDataDto3 = winDataList.get(countIdx+2);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+3);
				
				int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
				int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
				int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				int[] sourceZeroCntRange1 = lottoDataService.getZeroCntRangeData(sourceWinDataDto1);
				int[] sourceZeroCntRange2 = lottoDataService.getZeroCntRangeData(sourceWinDataDto2);
				int[] sourceZeroCntRange3 = lottoDataService.getZeroCntRangeData(sourceWinDataDto3);
				
				
				// 단번대 1개씩 3회 출현 확인
				boolean isAppear = false;
				
				if (sourceZeroCntRange1[0] == 1
						&& sourceZeroCntRange2[0] == 1
						&& sourceZeroCntRange3[0] == 1
						) {
					allAppearCnt++;
					isAppear = true;
					log.info("[" + loginUserNo + "]\t단번대 1개씩 3회 출현 확인");
				}
				
				if (isAppear) {
					int num1 = sourceNumbers1[0];
					int num2 = sourceNumbers2[0];
					int num3 = sourceNumbers3[0];
					
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % num1 == 0
								|| targetNumbers[i] % num2 == 0
								|| targetNumbers[i] % num3 == 0
								) {
							log.info("[" + loginUserNo + "]\t\t>" + targetWinDataDto.getWin_count() + "회 단번대 배수 출현!!!");
							matchedCnt++;
							break;
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	/**
	 * 나대길 가설18 검증
	 * 
	 * 가설18. 5와 8끝수가 출현하면, 다음회 6끝수가 출현한다.
	 * 
	 * 2020.03.13 검증
	 * 1회부터 확인 : 전체출현횟수 = 38, 일치횟수 = 20, 정확도 53%
	 * 최근 30회 전부터 확인 : 전체출현횟수 = 2, 일치횟수 = 2, 정확도 100%
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/test/testTheory18")
	public void testTheory18(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			String fromCheckCount       = WebUtil.replaceParam(request.getParameter("fromCheckCount"), "");
			
			//2016.05.23 cremazer
			//ORACLE 인 경우 대문자 설정
			if ("ORACLE".equals(systemInfo.getDatabase())) {
				dto.setSord(WebUtil.replaceParam(dto.getSord(),"").toUpperCase());
			}
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 나대길 가설18 검증");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			int allAppearCnt = 0;
			int matchedCnt = 0;
			double matchedPer = 0.0; 
			int fromCount = 0;
			if (!"".equals(fromCheckCount)) {
				try {
					fromCount = winDataList.size() - Integer.parseInt(fromCheckCount);
				} catch (Exception e) {
					e.printStackTrace();
					fromCount = 0;
				}
			}
			
			// 마지막 회차의 전회차까지만 반복해야함.
			for (int countIdx = 0 + fromCount ; countIdx < winDataList.size() - 1; countIdx++) {
				
				WinDataDto sourceWinDataDto = winDataList.get(countIdx);
				WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
				
				int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
				int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
				
				
				// 5번과 8끝수 출현 확인
				boolean isAppear = false;
				boolean isAppear5 = false;
				boolean isAppear8End = false;
				
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] == 5) {
						isAppear5 = true;
					}
					if (sourceNumbers[i] % 10 == 8) {
						isAppear8End = true;
					}
				}
				
				if (isAppear5 && isAppear8End) {
					allAppearCnt++;
					isAppear = true;
					log.info("[" + loginUserNo + "]\t5번과 8끝수 출현 확인");
				}
				
				if (isAppear) {
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == 6) {
							log.info("[" + loginUserNo + "]\t\t>" + targetWinDataDto.getWin_count() + "회 6끝수 출현!!!");
							matchedCnt++;
							break;
						}
					}
				}
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("allAppearCnt", allAppearCnt);
			jsonObj.put("matchedCnt", matchedCnt);
			
			if (allAppearCnt > 0) {
				matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
			}
			jsonObj.put("matchedPer", matchedPer);
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
	
	@RequestMapping("/test/sendEmail")
	public void sendEmail(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			
			// 로그인 아이디
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 이메일 발송 테스트");
			String accessip = request.getRemoteHost();
			
			// TEST1
//			EmailVO vo = new EmailVO();
//			vo.setTitle("메일발송테스트");
//			vo.setContent("1,2,3,4,5,6,");
//			vo.setSender("smlotto@naver.com");
//			vo.setReceiver("cremazer@gmail.com");
////			mailService.sendEmail(vo);		
//			
//			JavaMailSenderImpl sender = new JavaMailSenderImpl();
//			sender.setHost("smtp.naver.com");
//			sender.setPort(587);
//			sender.setUsername("smlotto");
//			sender.setPassword("Qudrkfl!813152");
//			
//			Properties prop = new Properties();
//			prop.setProperty("mail.smtp.auth", "true");
//			prop.setProperty("mail.smtp.starttls.enable", "true");
//			prop.setProperty("mail.smtps.ssl.checkserveridentity", "true");
//			prop.setProperty("mail.smtps.ssl.trust", "*");
//			prop.setProperty("mail.debug", "true");
//			prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			sender.setJavaMailProperties(prop);
//			
//			MimeMessage message = sender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message);
//			helper.setFrom(vo.getSender());
//			helper.setTo(vo.getReceiver());
//			helper.setText(vo.getContent());
//			helper.setSubject(vo.getTitle());
//			sender.send(message);
			
			
			
			// TEST2
//			String host = "smtp.naver.com"; // 네이버일 경우 네이버 계정
//			String user = "smlotto";
//			String password = "Qudrkfl!813152";
//			String to = "cremazer@gmail.com";
//			
//			// SMTP 서버 정보를 설정한다. 
//			Properties props = new Properties(); 
//			props.put("mail.smtp.host", host); 
////			props.put("mail.smtp.port", 587); 
//			props.put("mail.smtp.port", 465); 
//			props.put("mail.smtp.auth", "true"); 
//			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.ssl.enable", "true");
////			props.put("mail.smtp.ssl.trust", host);
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
//			
//			Session session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() { 
//				protected PasswordAuthentication getPasswordAuthentication() { 
//					return new PasswordAuthentication(user, password); 
//				} 
//			}); 
//			session2.setDebug(true);
//			
//			try { 
//				MimeMessage message = new MimeMessage(session2); 
//				message.setFrom(new InternetAddress(user)); 
//				
//				// 단건 보내기
//				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//				
//				// 다중 메일보내기
////				InternetAddress[] addArray = new InternetAddress[5];
////				addArray[0] = new InternetAddress("ktko0@ktko0.com");
////				addArray[1] = new InternetAddress("ktko1@ktko1.com"); 
////				addArray[2] = new InternetAddress("ktko2@ktko2.com"); 
////				addArray[3] = new InternetAddress("ktko3@ktko3.com"); 
////				addArray[4] = new InternetAddress("ktko4@ktko4.com"); 
////				message.addRecipients(Message.RecipientType.TO, addArray);
//
//				
//				// 메일 제목 
//				message.setSubject("KTKO SMTP TEST1111"); 
//				
//				// 메일 내용 
//				message.setText("KTKO Success!!"); 
//				
//				// send the message 
//				Transport.send(message); 
//				System.out.println("Success Message Send"); 
//			} catch (MessagingException e) { 
//				e.printStackTrace(); 
//			}
			
			
			// TEST3
			// 메일 관련 정보
			String host = "smtp.naver.com";
			final String username = "smlotto";
			final String password = "Qudrkfl!813152";
			int port = 465;
//			int port = 587;
			
			// 메일 내용
			String recipient = "cremazer@gmail.com";
			String subject = "SM Lotto 메일테스트";
			String body = "1,2,3,4,5,6";
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host);
			
			
			Session session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un = username;
				String pw = password;
				protected PasswordAuthentication getPasswordAuthentication() { 
					return new PasswordAuthentication(un, pw); 
				} 
			});
			session2.setDebug(true);
			
			MimeMessage message = new MimeMessage(session2); 
			message.setFrom(new InternetAddress("smlotto@naver.com")); 
			
			// 단건 보내기
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			
			message.setSubject(subject); 
			
			// 메일 내용 
			message.setText(body); 
			
			// send the message 
			Transport.send(message); 
				
			jsonObj.put("status", "success");
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
	}
}
