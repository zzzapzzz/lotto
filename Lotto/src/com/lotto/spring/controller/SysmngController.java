package com.lotto.spring.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.lotto.common.FileUtils;
import com.lotto.common.LottoUtil;
import com.lotto.common.WebUtil;
import com.lotto.spring.core.DefaultSMController;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.EndNumDto;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.LowHighDto;
import com.lotto.spring.domain.dto.MCNumDto;
import com.lotto.spring.domain.dto.MenuInfoDto;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.ServiceInfoDto;
import com.lotto.spring.domain.dto.TaskInfoDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.UserInfoDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.domain.dto.ZeroRangeDto;
import com.lotto.spring.service.ExcelService;
import com.lotto.spring.service.LottoDataService;
import com.lotto.spring.service.PatternAnalysisService;
import com.lotto.spring.service.SysmngService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@SessionAttributes({"UserInfo", "SystemInfo"})
public class SysmngController extends DefaultSMController {
	
	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private LottoDataService lottoDataService;
	
	@Autowired(required = true)
	private ExcelService excelService;
	
	@Autowired(required = true)
	private PatternAnalysisService patternAnalysisService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private SysmngController() {
		super();
	}

	/**
	 * 회원관리 화면 호출
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
	@RequestMapping("/sysmng/usermng")
	public String usermng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/UserMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/UserMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 회원관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/usermngajax")
	public String usermngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/UserMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 회원관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/usermngplugin")
	public String usermngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 회원관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/UserMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 회원정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/userList")
	public void userList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String searchKey       = WebUtil.replaceParam(request.getParameter("searchKey"), "");
		String authTask       = WebUtil.replaceParam(request.getParameter("authTask"), "");
		     			
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String rows = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "usr_id");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");	
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			sord = sord.toUpperCase();
  		}
  		
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 회원정보 목록 조회");
		
		Map map = new HashMap();
		map.put("search_key", searchKey);
		map.put("auth_task", authTask);
		map.put("startNum", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(rows))));
		map.put("endNum", Integer.toString(Integer.parseInt(page)*Integer.parseInt(rows)));
		map.put("sidx", sidx);
		map.put("sord", sord);
		
		ArrayList<UserInfoDto> userList = sysmngService.getUserList(map);
		int userListCnt = sysmngService.getUserListCnt(map);

		int total_pages = 0;
		if( userListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)userListCnt/Double.parseDouble(rows));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", userList.size());
		json.put("total", total_pages);
		json.put("page", page);
		json.put("records", userListCnt);
		json.put("rows", userList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	} 
	
	/**
	 * 회원정보 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/createUserInfo")
	public void createUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			String accessip = request.getRemoteHost();
			log.info("[" + loginUserNo + "][C] 회원정보 등록");
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 등록
			log.info("[" + loginUserNo + "] > 회원정보 등록");
			CaseInsensitiveMap resultInfo = sysmngService.createUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회원정보 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/modifyUserInfo")
	public void modifyUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회원정보 수정");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 수정
			log.info("[" + loginUserNo + "] > 회원정보 수정");
			CaseInsensitiveMap resultInfo = sysmngService.modifyUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회원정보 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/deleteUserInfo")
	public void deleteUserInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회원정보 삭제");
			String accessip = request.getRemoteHost();
			
			dto.setReg_user_no(loginUserNo);
			dto.setAccess_ip(accessip);
			
			//회원정보 삭제
			log.info("[" + loginUserNo + "] > 회원정보 삭제");
			CaseInsensitiveMap resultInfo = sysmngService.deleteUserInfo(dto);
			String status = (String) resultInfo.get("result");
			String msg = (String) resultInfo.get("msg");
			
			log.info("[" + loginUserNo + "]\t" + msg);
			jsonObj.put("status", status);
			jsonObj.put("msg", msg);
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 당첨번호관리 화면 호출
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
	@RequestMapping("/sysmng/windatamng")
	public String windatamng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/WinDataMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 당첨번호관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/windatamngajax")
	public String windatamngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/windatamngplugin")
	public String windatamngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/WinDataMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 당첨번호정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/getWinDataList")
	public void getWinDataList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 당첨번호정보 목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		List<WinDataDto> winDataList = sysmngService.getWinDataList(dto);
		int winDataListCnt = sysmngService.getWinDataListCnt(dto);

		int total_pages = 0;
		if( winDataListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)winDataListCnt/Double.parseDouble(dto.getRows()));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", winDataList.size());
		json.put("total", total_pages);
		json.put("page", dto.getPage());
		json.put("records", winDataListCnt);
		json.put("rows", winDataList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * 당첨번호 등록 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/writeWinDataajax")
	public String writeWinDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 등록 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataInsert");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호 등록 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/writeWinDataplugin")
	public String writeWinDataplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 등록 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/WinDataInsert_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 당첨번호목록 등록 (File)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/uploadFileForWinData")
	public void uploadFileForUser(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		String userIp = WebUtil.getUser_IP(request, response);
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			long loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 당첨번호목록 등록 (File)");
			
		    int requestFileCnt = FileUtils.getRequestFileCnt(request);
		    if (requestFileCnt > 0) {
		    	String fileExt = FileUtils.getFileExt(request);
	    		if ((".xls").equals(fileExt) || (".xlsx").equals(fileExt)) {
	    			
	    			//2018.06.06 cremazer
	    			//파일을 임시로 저장함.
	    			log.info("[" + loginUserNo + "]\t> 임시파일 저장");
	    			File file = excelService.getFileInfo(request);
	    			
	    			log.info("[" + loginUserNo + "]\t> 엑셀파일 읽기");	    			
	    			List list = null;
	    			if ((".xls").equals(fileExt)) {
	    				list = excelService.xlsExcelReader(file, "WinData");
	    			} else {
	    				list = excelService.xlsxExcelReader(file, "WinData");
	    			}
	    			
	    			if (list != null && list.size() > 0) {
	    				
	    				Map map = new HashMap();
	    				map.put("list", list);
	    				
	    				// 기존정보 삭제
	    				log.info("[" + loginUserNo + "]\t기존정보 삭제");
	    				log.info("[" + loginUserNo + "]\t\t당첨번호정보 전체 삭제");
	    				sysmngService.deleteWinData(new WinDataDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t회차합정보 전체 삭제");
	    				sysmngService.deleteCountSumInfo(new CountSumDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t제외수정보 전체 삭제");
	    				sysmngService.deleteExcludeInfo(new ExcludeDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t총합정보 전체 삭제");
	    				sysmngService.deleteTotalInfo(new TotalDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t끝수합정보 전체 삭제");
	    				sysmngService.deleteEndNumInfo(new EndNumDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t궁합수정보 전체 삭제");
	    				sysmngService.deleteMCNumInfo(new MCNumDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t저고비율정보 전체 삭제");
	    				sysmngService.deleteLowHighInfo(new LowHighDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t홀짝비율정보 전체 삭제");
	    				sysmngService.deleteOddEvenInfo(new OddEvenDto());
	    				
	    				log.info("[" + loginUserNo + "]\t\t미출현구간대 정보 전체 삭제");
	    				sysmngService.deleteZeroRangeInfo(new ZeroRangeDto());
	    				
	    				
	    				log.info("[" + loginUserNo + "]\t당첨번호 목록 등록");
	    				sysmngService.insertWinDataList(map);
	    				
	    				// 당첨번호 전체 목록 오름차순 조회
	    				log.info("[" + loginUserNo + "]\t당첨번호 전체 목록 오름차순 조회");
	    				WinDataDto winDataDto = new WinDataDto();
	    				winDataDto.setSord("ASC");
	    				winDataDto.setPage("1");	// 전체조회 설정
	    				List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
	    				List<WinDataDto> procWinDataList = new ArrayList<WinDataDto>(); 
	    				
	    				
	    				// 부가정보 일괄 등록
	    				log.info("[" + loginUserNo + "]\t부가정보 일괄 등록");
	    				
	    				for (int i = 0; i < winDataList.size(); i++) {
	    					log.info("[" + loginUserNo + "]\t\t" + (i+1) + "회차 등록 처리");
	    					
	    					procWinDataList.add(winDataList.get(i));

	    					// 저고비율정보 등록
	    					sysmngService.insertLowHighInfo(procWinDataList);
	    					
	    					// 홀짝비율정보 등록
	    					sysmngService.insertOddEvenInfo(procWinDataList);
	    					
	    					// 총합정보 등록
		    				sysmngService.insertTotalInfo(procWinDataList);
		    				
		    				// 끝수합정보 등록
		    				sysmngService.insertEndNumInfo(procWinDataList);

		    				// AC정보 등록
		    				sysmngService.insertAcInfo(procWinDataList.get(procWinDataList.size()-1).getWin_count());

		    				if (i >= 10) {
		    					// 회차합정보 등록
		    					// 10회차합부터 처리되므로 10회차 이전 데이터는 처리하지 않음.
		    					sysmngService.insertCountSumInfo(procWinDataList);
		    					
		    					// 제외수정보 등록
		    					// 이전 데이터를 비교해야 하므로, 11회차부터 등록 처리함.
		    					sysmngService.insertExcludeInfo(procWinDataList);
		    				}
		    				
		    				// 궁합수정보 등록
		    				sysmngService.insertMCNumInfo(procWinDataList);		    				
		    				
		    				//미출현구간대 정보 등록
		    				sysmngService.insertZeroRangeInfo(procWinDataList);
						}
	    				
	    				log.info("[" + loginUserNo + "]\t모든 데이터가 업로드 되었습니다.");
	    				jsonObj.put("status", "success");
	    				jsonObj.put("msg", "모든 데이터가 업로드 되었습니다.");
	    			} else {
	    				log.info("[" + loginUserNo + "]\t등록할 내용이 없습니다.");
	    				jsonObj.put("status", "fail");
	    				jsonObj.put("msg", "등록할 내용이 없습니다.");
	    			}
	    			
	    			log.info("[" + loginUserNo + "]\t> 임시파일 삭제");
	    			file.delete();
	    			
	    		} else {
	    			log.info("[" + loginUserNo + "]\t파일 업로드에 실패했습니다. 파일유형 오류.");
	    			jsonObj.put("status", "fail");
	    			jsonObj.put("msg", "파일 업로드에 실패했습니다. 엑셀파일만 업로드할 수 있습니다.");
	    		}
		    		
		    	
		    } else {
		    	log.info("[" + loginUserNo + "]\t파일 업로드에 실패했습니다. 파일전달 실패.");
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "파일 업로드에 실패했습니다. 파일전달 실패.");
		    }
		    
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 마지막 당첨번호 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/getLastWinData")
	public void getLastWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 마지막 당첨번호 조회");
			
			WinDataDto lastWinData = sysmngService.getLastWinData();
			
			jsonObj.put("data", lastWinData);
			jsonObj.put("status", "success");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 당첨번호 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/insertWinData")
	public void insertWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 당첨번호 등록");
			
			//추가정보 생성
			int iTotal = LottoUtil.getTotal(dto);
	    	String sLowHigh = LottoUtil.getLowHigh(dto);  
	    	String sOddEven = LottoUtil.getOddEven(dto);  
	    	int iSumEndNum = LottoUtil.getSumEndNumber(dto);
	    	int iAc = LottoUtil.getAc(dto);
	    	
	    	dto.setTotal(iTotal);					//총합(보너스번호 제외)
			dto.setLow_high(sLowHigh);				//고저
			dto.setOdd_even(sOddEven);				//홀짝
			dto.setSum_end_num(iSumEndNum);			//끝수합
			dto.setAc(iAc);							//AC
			
			// 당첨번호 등록
			sysmngService.insertWinData(dto);
			
			// 당첨번호 전체 목록 오름차순 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 저고비율정보 등록
			sysmngService.insertLowHighInfo(winDataList);
			
			// 홀수짝수비율정보 등록
			sysmngService.insertOddEvenInfo(winDataList);
			
			// 총합정보 등록
			sysmngService.insertTotalInfo(winDataList);
			
			// 끝수합정보 등록
			sysmngService.insertEndNumInfo(winDataList);
			
			// AC정보 등록
			sysmngService.insertAcInfo(winDataList.get(winDataList.size()-1).getWin_count());
			
			// 회차합정보 등록
			sysmngService.insertCountSumInfo(winDataList);
			
			// 제외수정보 등록
			sysmngService.insertExcludeInfo(winDataList);
			
			// 궁합수정보 등록
			sysmngService.insertMCNumInfo(winDataList);
			
			//미출현번호대 구간정보 등록
			sysmngService.insertZeroRangeInfo(winDataList);
			
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
	 * 당첨번호 수정 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/modifyWinDataajax")
	public String modifyWinDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses, @ModelAttribute WinDataDto dto) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 수정 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataModify");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
			//수정할 당첨번호 조회
			WinDataDto winData = sysmngService.getWinData(dto);
			modelMap.addAttribute("winData", winData);
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호 수정 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/modifyWinDataplugin")
	public String modifyWinDataplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 수정 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/WinDataModify_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 당첨번호 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/modifyWinData")
	public void modifyWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 당첨번호 수정");
			
			//추가정보 생성
			int iTotal = LottoUtil.getTotal(dto);
	    	String sLowHigh = LottoUtil.getLowHigh(dto);  
	    	String sOddEven = LottoUtil.getOddEven(dto);  
	    	int iSumEndNum = LottoUtil.getSumEndNumber(dto);
	    	int iAc = LottoUtil.getAc(dto);
	    	
	    	dto.setTotal(iTotal);					//총합(보너스번호 제외)
			dto.setLow_high(sLowHigh);				//고저
			dto.setOdd_even(sOddEven);				//홀짝
			dto.setSum_end_num(iSumEndNum);			//끝수합
			dto.setAc(iAc);							//AC
			
			boolean result = sysmngService.modifyWinData(dto);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "수정했습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 당첨번호 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/deleteWinData")
	public void deleteWinData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 당첨번호 삭제");
			
			boolean result = sysmngService.deleteWinData(dto);
			
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
	 * 당첨번호 분석 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/analysisWinDataajax")
	public String analysisWinDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses, @ModelAttribute ExDataDto dto) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 분석 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근 당첨번호
			WinDataDto lastData = winDataList.get(0);
			
			// 분석정보 설정
			modelMap.addAttribute("last_count", lastData.getWin_count());
			modelMap.addAttribute("ex_count", lastData.getWin_count()+1);
			
			String winNumbers = "";
			int[] lastWinNumbers = LottoUtil.getNumbers(lastData);
			for (int i = 0; i < lastWinNumbers.length; i++) {
				if (i > 0) {
					winNumbers += ", ";
				}
				winNumbers += lastWinNumbers[i];
			}
			modelMap.addAttribute("winNumbers", winNumbers);
			modelMap.addAttribute("bonusNumber", lastData.getBonus_num());
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/WinDataAnalysis");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 당첨번호 분석 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/analysisWinDataplugin")
	public String analysisWinDataplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 당첨번호 분석 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/windatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/WinDataAnalysis_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 예상번호관리 화면 호출
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
	@RequestMapping("/sysmng/exptdatamng")
	public String exptdatamng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExptDataMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/ExptDataMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 예상번호관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/exptdatamngajax")
	public String exptdatamngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);

			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);

			// 최근 당첨번호
			WinDataDto lastData = winDataList.get(0);
			int lastWinCount = lastData.getWin_count();
					
			// 최근 당첨번호 Calendar
			Calendar lastCrDtCal = LottoUtil.getLastDataCalendar(lastData);
			
			// 다음 발표일자 Calendar (조회시간 기준)
			Calendar nextAnnounceCal = LottoUtil.getNextAccounceCalendar();
			Date date = nextAnnounceCal.getTime();             
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일");
			String nextAnnounceDate = sdf.format(date);  
			modelMap.addAttribute("nextAnnounceDate", nextAnnounceDate);
			
			// 두 날짜 비교
			long diffSec = (nextAnnounceCal.getTimeInMillis() - lastCrDtCal.getTimeInMillis()) / 1000;		
			long diffDays = diffSec / (24*60*60);
			
			// 다음 예상번호
			int nextWinCount = lastWinCount + ((int)diffDays / 7) + ((int)diffDays % 7 > 0 ? 1 : 0);
//			System.out.println("다음 예상번호 : " + nextWinCount);
			modelMap.addAttribute("nextWinCount", nextWinCount);		
			
			
			// 미입력 회차 확인
			if ((int)diffDays / 7 >= 1) {
				if ((int)diffDays % 7 > 0	// 1의 경우 당일날짜일 수 있음.
						|| (int)diffDays / 7 >= 2
						) {
//					System.out.println("미입력된 당첨번호가 존재합니다.");
					modelMap.addAttribute("isUnregistered", "Y");
					modelMap.addAttribute("unregisteredMsg", "미입력된 당첨번호가 존재합니다.");
					modelMap.addAttribute("lastWinCount", lastWinCount);
				}
			}
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExptDataMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 예상번호관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/exptdatamngplugin")
	public String exptdatamngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ExptDataMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 예상번호정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/getExDataList")
	public void getExDataList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 예상번호정보 목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("DESC");
		winDataDto.setPage("1");	// 전체조회 설정
		List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);

		// 예상 당첨번호
		dto.setEx_count(winDataList.get(0).getWin_count()+1);
					
		List<ExDataDto> exDataList = sysmngService.getExDataList(dto);
		int exDataListCnt = sysmngService.getExDataListCnt(dto);

		int total_pages = 0;
		if( exDataListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)exDataListCnt/Double.parseDouble(dto.getRows()));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", exDataList.size());
		json.put("total", total_pages);
		json.put("page", dto.getPage());
		json.put("records", exDataListCnt);
		json.put("rows", exDataList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * 예상번호 NEW 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/getExDataNewList")
	public void getExDataNewList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 예상번호 NEW 목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		// 당첨번호 전체 목록 조회
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("DESC");
		winDataDto.setPage("1");	// 전체조회 설정
		List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
		
		// 예상 당첨번호
		dto.setEx_count(winDataList.get(0).getWin_count()+1);
		
		List<ExDataDto> exDataList = sysmngService.getExDataNewList(dto);
		int exDataListCnt = sysmngService.getExDataNewListCnt(dto);
		
		int total_pages = 0;
		if( exDataListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)exDataListCnt/Double.parseDouble(dto.getRows()));
		} else { 
			total_pages = 0; 
		}  
		
		//토탈 값 구하기 끝
		// Content Page - File which will included in tiles definition
		
		JSONObject json = new JSONObject();
		
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		
		
		json.put("cnt", exDataList.size());
		json.put("total", total_pages);
		json.put("page", dto.getPage());
		json.put("records", exDataListCnt);
		json.put("rows", exDataList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * 예상번호 추출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/extractExpectedNumber")
	public void extractExpectedNumber(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 예상번호 추출");
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
			if (winDataList != null && winDataList.size() > 0) {
				
				// 예상번호 목록 삭제
				int exCount = winDataList.get(winDataList.size()-1).getWin_count()+1;
				sysmngService.deleteExDataList(exCount);
				
				// 번호간 차이 설정
				lottoDataService.setDifNumbers(winDataList);
				
				// 당첨번호 배열 설정
				lottoDataService.setNumbers(winDataList);
				
				// 예측패턴 조회
				ExptPtrnAnlyDto exptPtrnAnlyInfo = patternAnalysisService.getExpectPattern(winDataList);
				
				// 예상번호 추출 및 등록
				sysmngService.insertExptNumList(winDataList, exptPtrnAnlyInfo);

				
				jsonObj.put("status", "success");
				jsonObj.put("msg", "예상번호를 추출했습니다.");
			} else {
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "예상번호를 추출 실패.(당첨번호 목록 없음.)");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 예상번호 NEW 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/insertNewExpectNumbers")
	public void insertNewExpectNumbers(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 예상번호 NEW 등록");
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			List<WinDataAnlyDto> winDataList = sysmngService.getWinDataAnlyList(winDataDto);
			
			// 예상번호
			int lastWinCount = winDataList.get(winDataList.size()-1).getWin_count(); 
			int exCount = lastWinCount + 1;

			log.info("[" + loginUserNo + "]\t예상번호 NEW 삭제");
			ExDataDto delExData = new ExDataDto();
			delExData.setEx_count(exCount);
			sysmngService.deleteExptNumNew(delExData);
			
			// 예상번호 NEW 검증 삭제
			log.info("[" + loginUserNo + "]\t예상번호 NEW 검증 삭제");
			sysmngService.deleteExptNumNewVari();
			
			
			ExptPtrnAnlyDto exptPtrnAnlyDto = new ExptPtrnAnlyDto();
			exptPtrnAnlyDto.setEx_count(exCount);
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
					// 목표율을 넘으면 멈춤
//					totalGroupCntList = sysmngService.getTotalGroupCntList(map);
					break;
				} else {
					// 목표율을 넘기 전까지 목록을 설정함. 2020.04.04
					totalGroupCntList = sysmngService.getTotalGroupCntList(map);
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
					// 목표율을 넘으면 멈춤
//					endnumGroupCntList = sysmngService.getEndnumGroupCntList(map);
					break;
				} else {
					// 목표율을 넘기 전까지 목록을 설정함. 2020.04.04
					endnumGroupCntList = sysmngService.getEndnumGroupCntList(map);
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
			
//						Map<Integer, Integer> excutedRandomMap = new HashMap<Integer, Integer>();
			int excuteCnt = 0;	// 추출횟수
			
			
			// 개선된 제외수 목록 설정 2020.02.07
//			Map enhancedRulesMap = lottoDataService.getEnhancedRules(loginUserNo, exCount);
//			List<Integer> allExcludeNumList = (List<Integer>) enhancedRulesMap.get("allExcludeNumList");
//			Map<Integer, Integer> allExcludeNumMap = (Map<Integer, Integer>) enhancedRulesMap.get("allExcludeNumMap");
//			int excludeNumOfMcNum = (int) enhancedRulesMap.get("excludeNumOfMcNum");
//			log.info("장기 미출수 >>> " + excludeNumOfMcNum);

			
			// 개선된 제외수로 번호가 추출되지 않아 일반 제외수로 적용 2020.04.04
			// 일반제외수 조회 (심서방로또)
			ExDataDto exDataDto = new ExDataDto();
			exDataDto.setEx_count(exCount);
			ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
			
			// 제외수 문자열을 int 배열로 변환
			int[] excludeNumbers = LottoUtil.getNumbers(excludeDto.getExclude_num().replaceAll(" ", ""));
			Map<Integer, Integer> allExcludeNumMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < excludeNumbers.length; i++) {
				if (!allExcludeNumMap.containsKey(excludeNumbers[i])) {
					allExcludeNumMap.put(excludeNumbers[i], 1);
				}
			}
			
			// 번호 목록에서 제외수를 포함하지 않도록 설정
			List<Integer> exportNumberList = new ArrayList<Integer>();
			for (int i = 1; i <= 45; i++) {
				if (!allExcludeNumMap.containsKey(i)) {
					exportNumberList.add(i);
				}
			}
			
			// 조합에 활용할 숫자 목록 출력
			String datas = "";
			for (int i = 0; i < exportNumberList.size(); i++) {
				if (i > 0) {
					datas += ",";
				}
				datas += exportNumberList.get(i);
			}
			log.info("전체 번호들 >>> " + datas);
			
			
			/** 조합숫자 목록 */
			// 제외수 적용
			List<ExDataDto> expectDataList = lottoDataService.getExDataList(exportNumberList, exCount);
			// 제외수 미적용
//			List<ExDataDto> expectDataList = lottoDataService.getExDataList(exCount);
			if (expectDataList.size() > 0) {
				log.info("전체 조합수 >>> " + expectDataList.size());
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
				if (10 <= lastWinNnumbers[0] || lastWinNnumbers[0] <= 19) {
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
			
			// 예상번호 NEW 검증 삭제
			log.info("[" + loginUserNo + "]\t예상번호_NEW 검증 삭제");
			sysmngService.deleteExptNumNewVari();
			
			// 예상번호 필터 체크 후 등록
			for (int i = 0; i < expectDataList.size(); i++) {
				ExDataDto exData = expectDataList.get(i);
				int[] arrNumbers = exData.getNumbers();
				// 번호를 Map에 담기
				String numbers = "";
				for (int j = 0; j < arrNumbers.length; j++) {
					if (j > 0) {
						numbers += ",";
					}
					numbers += arrNumbers[j];
				}
				
//				log.info(excuteCnt + ") 예상번호 조합 >>> " + numbers);
				
				boolean result = lottoDataService.compareExptPtrnNew(exData, winDataList, exptPtrnAnlyInfo, totalGroupCntMap, endnumGroupCntMap);
				if (result) {
					// 추가 필터 체크여부 확인
					if (isFilter0) {
						log.info("추가 필터0 체크합니다.");
						result = lottoDataService.isMatchedFilter0(winDataListForFilter, exData.getNumbers());
						if (!result) {
							log.info("\t추가 필터0 실패.");
							String contents = "추가 필터0 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터1 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터2 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터3 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터4 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터5 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터6 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터7 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터8 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터9 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터10 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터11 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터12 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터13 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터14 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터17 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터18 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터 6회귀 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터19 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터20 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터21 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터22 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터23 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터24 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터25 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터26 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터27 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터28 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터29 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
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
							String contents = "추가 필터30 실패.";
							sysmngService.insertExptNumNewVari(exData, contents);
							continue;
						} else {
							log.info("\t추가 필터30 성공.");
						}
					}
					
					log.info("예상번호 등록 처리");
					sysmngService.insertExptNumNew(exData);
					
					excuteCnt++;	// 등록횟수 추가
				}
				
				// 진행도 출력
				int d_cnt = (i+1);
				int d_total = expectDataList.size();
				double percent = LottoUtil.getPercent(d_cnt, d_total);
				
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> [" + numbers + "] " + (i+1) + " / " + expectDataList.size() + " / 예상번호 등록 처리 회수 = " + excuteCnt + " (" + (percent) + "%)");
			}
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "예상번호 NEW를 총 " + excuteCnt + "건 등록했습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString()); 
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 예상번호 분석 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/analysisExDataajax")
	public String analysisExDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses, @ModelAttribute ExDataDto dto) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호 분석 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근 당첨번호
			WinDataDto lastData = winDataList.get(0);
			
			// 예상회차 설정
			modelMap.addAttribute("ex_count", dto.getEx_count());
			modelMap.addAttribute("nextAnnounceDate", dto.getNextAnnounceDate());
			
			// 분석정보 설정
			modelMap.addAttribute("last_count", lastData.getWin_count());
			modelMap.addAttribute("last_lowhigh", lastData.getLow_high());			
			modelMap.addAttribute("last_oddeven", lastData.getOdd_even());
			
			// 비율 타이틀 설정
			modelMap.addAttribute("ratioTitle", LottoUtil.getRatioTitle());
			
			// 총합 정보 설정
			TotalDto totalDto = sysmngService.getTotalInfo(lastData);
			modelMap.addAttribute("total_range", totalDto.getTotal_range());
			
			// 끝수합 정보 설정
			EndNumDto endNumDto = sysmngService.getEndNumInfo(lastData);
			modelMap.addAttribute("endnum_range", endNumDto.getEndnum_range());
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/exptdatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExDataAnalysis");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 예상번호 분석 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/analysisExDataplugin")
	public String analysisExDataplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 예상번호 분석 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/exptdatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ExDataAnalysis_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 전회차 매칭결과 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/resultExDataajax")
	public String resultExDataajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 전회차 매칭결과 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("DESC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 최근 당첨번호
			WinDataDto lastData = winDataList.get(0);
			modelMap.addAttribute("last_count", lastData.getWin_count());
			
			// 예상번호 목록 조회
			ExDataDto dto = new ExDataDto();
			dto.setEx_count(lastData.getWin_count());
			dto.setSord("ASC");
			dto.setPage("1");	// 전체조회 설정
			List<ExDataDto> exDataList = sysmngService.getExDataList(dto);
			
			// 당첨결과 설정
			lottoDataService.getExDataResult(modelMap, lastData, exDataList);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/exptdatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ExDataResult");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 전회차 매칭결과 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/resultExDataplugin")
	public String plugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 전회차 매칭결과 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/exptdatamng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ExDataResult_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 예상번호 30목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/getExData30List")
	public void getExData30List(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		JSONObject json = new JSONObject();
		
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
			
			if (exDataList.size() >= 30 || exDataListCnt == excuteCnt) {
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
	 * 최근 당첨회차 저고비율 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/getLowHighData")
	public void getLowHighData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 최근 당첨회차 저고비율 조회");
			
			// 최근 당첨회차 저고비율 조회
			List<LowHighDto> lowHighDataList = sysmngService.getLowHighDataList(dto);
			
			jsonObj.put("lowHighDataList", lowHighDataList);
			jsonObj.put("status", "success");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 최근 당첨회차 홀짝비율 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/getOddEvenData")
	public void getOddEvenData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 최근 당첨회차 홀짝비율 조회");
			
			// 최근 당첨회차 홀짝비율 조회
			List<OddEvenDto> oddEvenDataList = sysmngService.getOddEvenDataList(dto);
			
			jsonObj.put("oddEvenDataList", oddEvenDataList);
			jsonObj.put("status", "success");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 회차합 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/getCountSumDataList")
	public void getCountSumData(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 회차합 조회");
			
			// 당첨번호 전체 목록 조회
			WinDataDto winDataDto = new WinDataDto();
			winDataDto.setSord("ASC");
			winDataDto.setPage("1");	// 전체조회 설정
			List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
			
			// 10회차 포함번호 목록 조회
			List<Integer> contain10List = lottoDataService.getContain10List(winDataList);
			// 10회차 미포함번호 목록 조회
			List<Integer> notContain10List = lottoDataService.getNotContain10List(contain10List);
			
			jsonObj.put("contain10List", contain10List);
			jsonObj.put("notContain10List", notContain10List);
			jsonObj.put("status", "success");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 제외수 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/sysmng/getExcludeNumberList")
	public void getExcludeNumberList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ExDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 제외수 목록 조회");
			
			List<Integer> excludeNumberListNormal = new ArrayList<Integer>(); 
			int excludeNumberListNormalCnt = 0;
			
			ExcludeDto excludeDtoNormal = sysmngService.getExcludeInfo(dto);
			String strExcludeNum = excludeDtoNormal.getExclude_num();
			strExcludeNum = strExcludeNum.replaceAll(" ", "");
			String[] excludeNumbersNormal = strExcludeNum.split(",");
			int[] iArrExcludeNum = new int[excludeNumbersNormal.length];
			for (int i = 0; i < excludeNumbersNormal.length; i++) {
				iArrExcludeNum[i] = Integer.parseInt(excludeNumbersNormal[i]);
			}
			iArrExcludeNum = (int[]) LottoUtil.dataSort(iArrExcludeNum);
			
			// 일반 제외수 목록 설정
			for (int i = 0; i < iArrExcludeNum.length; i++) {
				excludeNumberListNormal.add(iArrExcludeNum[i]);
			}
			
			excludeNumberListNormalCnt = excludeNumberListNormal.size();
			
			
			/**********************************************************
			 *  개선된 제외수 목록 설정 2020.03.29
			 **********************************************************/
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
			
			int exCount = lastWinData.getWin_count() + 1;
			
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
			
			
			WinDataDto wdd = winDataList.get(winDataList.size()-1);
			
			/*****************************************************
			 * 1. 최근 10회동안 미출한 번호들의 궁합수 목록을 구함
			 *****************************************************/
			// 10회차 포함번호 목록 조회
			List<Integer> contain10List = lottoDataService.getContain10List(winDataList, winDataList.size()-1);
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
						for (int l = (winDataList.size() - 1) - 1; l >= 0; l--) {
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
				log.info("[" + loginUserNo + "]\t\t " + exCount + "회차 장기 미출수는 제외수에서 미포함 : " + excludeNumOfMcNum);
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
			
			/************************************************************
			 * 기본제외수 추가
			 * : 5주 이내 2번 이상 출현 수 제외수로 추가
			 * 2020.04.02
			 ************************************************************/
			List<Integer> addExcludeNumbers = lottoDataService.getAddExcludeNumbersFromDtoList(winDataList);
			String strAddExcludeNumbers = ""; 
			for (int i = 0; i < addExcludeNumbers.size(); i++) {
				int excludeNumber = addExcludeNumbers.get(i);
				if (!"".equals(strAddExcludeNumbers)) {
					strAddExcludeNumbers += ",";
				}
				strAddExcludeNumbers += excludeNumber;
			}			
			log.info("[" + loginUserNo + "]\t\t추가된 기본제외수 (5주 이내 2번 이상 출현) >>> " + strAddExcludeNumbers);

			// allExcludeNumList에서 확인 후 없으면 추가 처리
			boolean checkAdd = false;
			for (int j = 0; j < addExcludeNumbers.size(); j++) {
				int addExcludeNumber = addExcludeNumbers.get(j);
				for (int i = 0; i < allExcludeNumList.size(); i++) {
					if (addExcludeNumber == allExcludeNumList.get(i)) {
						continue;
					}
				}
				allExcludeNumList.add(addExcludeNumber);
				checkAdd = true;
			}
			
			// 내용이 추가되면 다시 정렬
			if (checkAdd) {
				allExcludeNumList = (List<Integer>) LottoUtil.dataSort(allExcludeNumList);
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
			log.info("[" + loginUserNo + "]\t\t " + wdd.getWin_count() + "회차 개선된 제외수 : " + modiExcludeNum);
				
			jsonObj.put("excludeNumberListNormal", excludeNumberListNormal);
			jsonObj.put("excludeNumberListNormalCnt", excludeNumberListNormalCnt);
			jsonObj.put("modiExcludeNum", modiExcludeNum);
			jsonObj.put("status", "success");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 궁합수 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/getMcNumberList")
	public void getMcNumberList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute WinDataDto dto) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 궁합수 목록 조회");
			
			List<MCNumDto> mcNumList = sysmngService.getMcNumList(dto);
			if (mcNumList != null && mcNumList.size() > 0) {
				jsonObj.put("mcNumList", mcNumList);
				jsonObj.put("status", "success");
			} else {
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "궁합수 목록이 없습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	
	/**
	 * 서비스관리 화면 호출
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
	@RequestMapping("/sysmng/servicemng")
	public String servicemng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/ServiceMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 서비스관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/servicemngajax")
	public String servicemngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/servicemngplugin")
	public String servicemngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ServiceMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스정보 목록 조회
	 * 2020.03.14
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param dto
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/getServiceInfoList")
	public void getServiceList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceInfoDto dto) throws SQLException {
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
		log.info("[" + loginUserNo + "][C] 서비스정보 목록 조회");
		String accessip = request.getRemoteHost();
		
		dto.setReg_user_no(loginUserNo);
		dto.setAccess_ip(accessip);
		
		List<ServiceInfoDto> serviceInfoList = sysmngService.getServiceInfoList(dto);
		int serviceInfoListCnt = sysmngService.getServiceInfoListCnt(dto);

		int total_pages = 0;
		if( serviceInfoListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)serviceInfoListCnt/Double.parseDouble(dto.getRows()));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
//		JSONArray jsonArr = JSONArray.fromObject(userList);
		

		json.put("cnt", serviceInfoList.size());
		json.put("total", total_pages);
		json.put("page", dto.getPage());
		json.put("records", serviceInfoListCnt);
		json.put("rows", serviceInfoList);		
		json.put("status", "success");		
		writeJSON(response, json); 
	}
	
	/**
	 * 서비스정보 등록 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/writeServiceInfoajax")
	public String writeServiceInfoajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 등록 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/servicemng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceInfoInsert");
			modelMap.addAttribute("sub_menu_nm", "서비스정보 등록");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스정보 등록 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/writeServiceInfoplugin")
	public String writeServiceInfoplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 등록 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/servicemng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ServiceInfoInsert_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스정보 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/insertServiceInfo")
	public void insertServiceInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 서비스정보 등록");
			
			// 서비스코드 중복체크
			boolean isDup = sysmngService.dupCheckServiceCode(dto); 
			if (isDup) {
				// 중복
				jsonObj.put("status", "isDuplicate");
				jsonObj.put("msg", "이미 등록된 서비스코드입니다.");
			} else {
				// 중복없음
				// 서비스정보 등록
				sysmngService.insertServiceInfo(dto);
				
				jsonObj.put("status", "success");
				jsonObj.put("msg", "등록 되었습니다.");
			}
			
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}

	/**
	 * 서비스정보 수정 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/modifyServiceInfoajax")
	public String modifyServiceInfoajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses, @ModelAttribute ServiceInfoDto dto) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 수정 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/servicemng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/ServiceInfoModify");
			modelMap.addAttribute("sub_menu_nm", "서비스정보 수정");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
			//수정할 당첨번호 조회
			ServiceInfoDto serviceInfo = sysmngService.getServiceInfo(dto);
			modelMap.addAttribute("ServiceInfo", serviceInfo);
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 서비스정보 수정 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/modifyServiceInfoplugin")
	public String modifyServiceInfoplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			long loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 서비스정보 수정 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			//CurrMenuInfo overwrite
			modelMap.addAttribute("CurrMenuInfo", getCurrMenuInfo(userInfo, "/sysmng/servicemng"));
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/ServiceInfoModify_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 서비스정보 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/modifyServiceInfo")
	public void modifyServiceInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 서비스정보 수정");
			
			boolean result = sysmngService.modifyServiceInfo(dto);
			
			jsonObj.put("status", "success");
			jsonObj.put("msg", "수정했습니다.");
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 서비스정보 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sysmng/deleteServiceInfo")
	public void deleteServiceInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute ServiceInfoDto dto) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserNo = userInfo.getUser_no();
			log.info("[" + loginUserNo + "][C] 서비스정보 삭제");
			
			boolean result = sysmngService.deleteServiceInfo(dto);
			
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
	 * 프로모션관리 화면 호출
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
	@RequestMapping("/sysmng/promotionmng")
	public String promotionmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/PromotionMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/PromotionMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 프로모션관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/promotionmngajax")
	public String promotionmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/PromotionMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 프로모션관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/promotionmngplugin")
	public String promotionmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 프로모션관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/PromotionMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 사용자요청관리 화면 호출
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
	@RequestMapping("/sysmng/requestmng")
	public String requestmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, ServletException, IOException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/RequestMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/RequestMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 사용자요청관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/requestmngajax")
	public String requestmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/RequestMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 사용자요청관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/requestmngplugin")
	public String requestmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 사용자요청관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/RequestMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 업무권한관리 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmng")
	public String authtaskmng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthTaskMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/AuthTaskMain_Plugin");
			modelMap.addAttribute("isAjax", "N");
			
			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 업무권한관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmngajax")
	public String authtaskmngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthTaskMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 업무권한관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authtaskmngplugin")
	public String authtaskmngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 업무권한관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/AuthTaskMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 권한코드 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/authCodeList")
	public void authCodeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String searchKey       = WebUtil.replaceParam(request.getParameter("searchKey"), "");
		     			
		String page = WebUtil.replaceParam(request.getParameter("page"), "1");
		String rows = WebUtil.replaceParam(request.getParameter("rows"), "100");
		String sidx = WebUtil.replaceParam(request.getParameter("sidx"), "auth_cd");
		String sord = WebUtil.replaceParam(request.getParameter("sord"), "ASC");	
		
		//2016.05.23 cremazer
  		//ORACLE 인 경우 대문자 설정
  		if ("ORACLE".equals(systemInfo.getDatabase())) {
  			sord = sord.toUpperCase();
  		}
  		
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 권한코드 목록 조회");
		
		Map map = new HashMap();
		map.put("search_key", searchKey);
		map.put("startNum", Integer.toString(1+((Integer.parseInt(page)-1)*Integer.parseInt(rows))));
		map.put("endNum", Integer.toString(Integer.parseInt(page)*Integer.parseInt(rows)));
		map.put("sidx", sidx);
		map.put("sord", sord);
		
		ArrayList<CaseInsensitiveMap> authTaskList = sysmngService.getAuthCodeList(map);
		int authTaskListCnt = sysmngService.getAuthCodeListCnt(map);

		int total_pages = 0;
		if( authTaskListCnt > 0 ) {
			total_pages = (int) Math.ceil((double)authTaskListCnt/Double.parseDouble(rows));
		} else { 
			total_pages = 0; 
		}  
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONObject json = new JSONObject();
  
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		

		json.put("cnt", authTaskList.size());
		json.put("total", total_pages);
		json.put("page", page);
		json.put("records", authTaskListCnt);
		json.put("rows", authTaskList);		
		json.put("status", "success");
		writeJSON(response, json); 
	} 
	
	/**
	 * 업무권한정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/authTaskInfoList")
	public void authTaskInfoList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
		     			
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 업무권한정보 목록 조회");
		
		Map map = new HashMap();
		map.put("auth_cd", authCd);

		ArrayList<TaskInfoDto> authTaskInfoList = sysmngService.getAuthTaskInfoList(map);

		//Tree Data 설정
//		JSONArray treeData = sysmngService.getAuthTaskInfoTree(authTaskInfoList, 0, authTaskInfoListCnt, 1);
		JSONArray treeData = null;
		
		int authTaskInfoListCnt = 0;
		if (authTaskInfoList != null  && authTaskInfoList.size() > 0) {
			authTaskInfoListCnt = authTaskInfoList.size();
			treeData = sysmngService.getAuthTaskInfoTree(authTaskInfoList, 0, authTaskInfoListCnt, 1);
		}
		
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("id", "root");
		jsonRtn.put("text", "전체");
		jsonRtn.put("value", "전체");
		jsonRtn.put("showcheck", true);
		jsonRtn.put("complete", true);
		jsonRtn.put("isexpand", true);
		jsonRtn.put("hasChildren", true);
		if (treeData != null && treeData.size() > 0) {
			jsonRtn.put("ChildNodes", treeData);
		}
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONArray jsonRtnArray = new JSONArray();
		jsonRtnArray.add(jsonRtn);
		
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		
		
		log.info("[" + loginUserId + "]> treedata : " + jsonRtnArray.toString());
		
		writeJsonArray(response, jsonRtnArray);
		
		
	}
	
	/**
	 * 권한코드 등록
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/createAuthCode")
	public void createAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 등록");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);
			
			//권한코드 중복체크
			log.info("[" + loginUserId + "] > 권한코드 중복체크");
			boolean result = sysmngService.dupCheckAuthCode(map);
			if (result) {
				log.info("[" + loginUserId + "]\t이미 등록된 권한코드입니다.");
				jsonObj.put("status", "isDuplicate");
				jsonObj.put("msg", "이미 등록된 권한코드입니다.");
			} else {
				//권한코드 등록
				log.info("[" + loginUserId + "] > 권한코드 등록");
				result = sysmngService.createAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t등록 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "등록 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t등록에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "등록에 실패했습니다.");
				}
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 권한코드 수정
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/modifyAuthCode")
	public void modifyAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 수정");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
			String bfAuthCd       = WebUtil.replaceParam(request.getParameter("bf_auth_cd"), "");
			String bfAuthNm       = WebUtil.replaceParam(request.getParameter("bf_auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);
			map.put("bf_auth_cd", bfAuthCd);
			map.put("bf_auth_nm", bfAuthNm);
			
			boolean result = false;
			
			if (bfAuthCd.equals(authCd)) {
				//권한코드 수정
				log.info("[" + loginUserId + "] > 권한코드 수정");
				result = sysmngService.modifyAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t수정 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "수정 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t수정에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "수정에 실패했습니다.");
				}
			} else {
				jsonObj.put("status", "notequalauthcode");
				jsonObj.put("msg", "동일한 권한코드가 아닙니다.");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 권한코드 삭제
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/deleteAuthCode")
	public void deleteAuthCode(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 권한코드 삭제");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String authNm       = WebUtil.replaceParam(request.getParameter("auth_nm"), "");
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			map.put("auth_nm", authNm);

			//기본권한 삭제 불가 처리
			if (!"normal".equals(authCd)) {
				//권한코드 삭제
				log.info("[" + loginUserId + "] > 권한코드 삭제");
				boolean result = sysmngService.deleteAuthCode(map);
				if (result) {
					log.info("[" + loginUserId + "]\t삭제 되었습니다.");
					jsonObj.put("status", "success");
					jsonObj.put("msg", "삭제 되었습니다.");
				} else {
					log.info("[" + loginUserId + "]\t삭제에 실패했습니다.");
					jsonObj.put("status", "fail");
					jsonObj.put("msg", "삭제에 실패했습니다.");
				}
			} else {
				jsonObj.put("status", "isbaseauth");
				jsonObj.put("msg", "기본권한은 삭제할 수 없습니다.");
			}
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
		
	}
	
	/**
	 * 업무권한 매핑정보 저장
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/saveTaskAuthInfo")
	public void saveTaskAuthInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 업무권한 매핑정보 저장");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String tmpAuthCd       = WebUtil.replaceParam(request.getParameter("tmp_auth_cd"), "");
			tmpAuthCd = tmpAuthCd.toLowerCase();
			
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			
			//기존 업무권한 매핑정보 삭제
			log.info("[" + loginUserId + "] > 기존 업무권한 매핑정보 삭제");
			boolean result = sysmngService.deleteTaskAuthInfo(map);
			
			//권한설정 정보 parsing
			List insertDataList = new ArrayList();
			String[] datas = tmpAuthCd.split("@");
			for (int i = 0; i < datas.length; i++) {
				
				Map dataMap = new HashMap();
				dataMap.put("auth_cd", authCd);
				
				if (datas[i].indexOf("_") == -1) {
					//_ 가 없는 경우, 상위권한코드임.
					//하위권한코드가 존재할 경우 skip
					//하위권한코드가 존재하지 않으면, 자체 권한코드임.
					if (tmpAuthCd.indexOf(datas[i]+"_") > -1) {
						System.out.println(datas[i] + " : SKIP");
						continue;
					} else {
						dataMap.put("task_1_cd", datas[i]);
					}
				} else {
					
					String[] menus = datas[i].split("_");
					for (int j = 0; j < menus.length; j++) {
						System.out.println( menus[j]);
						if (j == 0) {
							dataMap.put("task_1_cd", menus[j]);
						} else {
							dataMap.put("task_2_cd", menus[j]);
						}
					}
					
				}
				
				insertDataList.add(dataMap);
			}
			map.put("list", insertDataList);
			
			
			//업무권한 매핑정보 저장
			log.info("[" + loginUserId + "] > 업무권한 매핑정보 저장");
			result = sysmngService.saveTaskAuthInfo(map);
			if (result) {
				log.info("[" + loginUserId + "]\t저장 되었습니다.");
				jsonObj.put("status", "success");
				jsonObj.put("msg", "저장 되었습니다.");
			} else {
				log.info("[" + loginUserId + "]\t저장에 실패했습니다.");
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "저장에 실패했습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
	/**
	 * 메뉴권한관리 화면 호출
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumng")
	public String authmenumng(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthMenuMain");
			modelMap.addAttribute(PLUGIN_PAGE, "sysmng/plugins/AuthMenuMain_Plugin");
			modelMap.addAttribute("isAjax", "N");

			//2018.05.02
			//권한에 의한 초기화면 호출시에는 PLUGIN으로 설정해야 함.
			return BASE_PLUGIN;
		} else {
			return "redirect:/fhrmdlsapdls.do";			
			
		}
	}
	
	/**
	 * 메뉴권한관리 화면 호출(ajax)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumngajax")
	public String authmenumngajax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출(ajax)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/AuthMenuMain");
			modelMap.addAttribute("isAjax", "Y");
			modelMap.addAttribute("isLogin", userInfo.getIsLogin());
			
		} else {
			modelMap.addAttribute(CONTENT_PAGE, "base/Main");
		}
		return POPUP;
	}
	
	/**
	 * 메뉴권한관리 화면 호출(plugin)
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param ses
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sysmng/authmenumngplugin")
	public String authmenumngplugin(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession ses) throws SQLException, UnsupportedEncodingException {
		
		UserSession userInfo = (UserSession) ses.getAttribute("UserInfo");
		
		if (userInfo != null) {
			
			int loginUserId = userInfo.getUser_no();
			log.info("["+loginUserId+"][C] 메뉴권한관리 화면 호출(plugin)");
			
			setModelMapWithAuthCheck(modelMap, request);
			
			modelMap.addAttribute(CONTENT_PAGE, "sysmng/plugins/AuthMenuMain_Plugin");
			
			return POPUP;
		} else {
			return "redirect:/fhrmdlsapdls.do";
			
		}
	}
	
	/**
	 * 메뉴권한정보 목록 조회
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/sysmng/authMenuInfoList")
	public void authMenuInfoList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
		     			
		// 로그인 아이디
		int loginUserId = userInfo.getUser_no();
		log.info("[" + loginUserId + "][C] 메뉴권한정보 목록 조회");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("auth_cd", authCd);

		ArrayList<MenuInfoDto> authMenuInfoList = sysmngService.getAuthMenuInfoList(map);

		int authMenuInfoListCnt = 0;
		if (authMenuInfoList != null  && authMenuInfoList.size() > 0) {
			authMenuInfoListCnt = authMenuInfoList.size();
		}
		
		/*
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("status", "success");
		jsonRtn.put("AuthTaskInfoList", authTaskInfoList);
		
		writeJSON(response, jsonRtn);
		*/
		
		//Tree Data 설정
		JSONArray treeData = sysmngService.getAuthMenuInfoTree(authMenuInfoList, 0, authMenuInfoListCnt, 1);
		
		JSONObject jsonRtn = new JSONObject();
		jsonRtn.put("id", "root");
		jsonRtn.put("text", "전체");
		jsonRtn.put("value", "root");
		jsonRtn.put("showcheck", true);
		jsonRtn.put("complete", true);
		jsonRtn.put("isexpand", true);
		jsonRtn.put("hasChildren", true);
		if (treeData != null && treeData.size() > 0) {
			jsonRtn.put("ChildNodes", treeData);
		}
		int checkCnt = sysmngService.getAuthMenuInfoCheckCnt(authMenuInfoList, 0, authMenuInfoListCnt);
		jsonRtn.put("checkstate", ((checkCnt == authMenuInfoListCnt) ? 1 : 0));
		
        //토탈 값 구하기 끝
        // Content Page - File which will included in tiles definition
 
		JSONArray jsonRtnArray = new JSONArray();
		jsonRtnArray.add(jsonRtn);
		
		//JSONArray jsonArr = JSONArray.fromObject(usermngList);
		
		
		log.info("[" + loginUserId + "]> treedata : " + jsonRtnArray.toString());
		
		writeJsonArray(response, jsonRtnArray);
		
		
	}
	
	/**
	 * 메뉴권한 매핑정보 저장
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sysmng/saveMenuAuthInfo")
	public void saveMenuAuthInfo(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
	    UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		JSONObject jsonObj = new JSONObject();
		
		if (userInfo != null) {
			int loginUserId = userInfo.getUser_no();
			log.info("[" + loginUserId + "][C] 메뉴권한 매핑정보 저장");
			
			String authCd       = WebUtil.replaceParam(request.getParameter("auth_cd"), "");
			String tmpMenuId       = WebUtil.replaceParam(request.getParameter("tmp_menu_id"), "");
			tmpMenuId = tmpMenuId.toLowerCase();
			
//			String userip = request.getRemoteHost();
			
			Map map = new HashMap();
			map.put("auth_cd", authCd);
			
			//기존 업무권한 매핑정보 삭제
			log.info("[" + loginUserId + "] > 기존 메뉴권한 매핑정보 삭제");
			boolean result = sysmngService.deleteMenuAuthInfo(map);
			
			//권한설정 정보 parsing
			String[] datas = tmpMenuId.split("@");
			
			log.info("[" + loginUserId + "] > 메뉴권한 매핑정보 저장");
			for (int i = 0; i < datas.length; i++) {
				
				//root 예외처리
				if ("root".equals(datas[i])) {
					continue;
				}
				
				Map dataMap = new HashMap();
				dataMap.put("auth_cd", authCd);
				dataMap.put("menu_id", datas[i]);
				
				//업무권한 매핑정보 저장
				result = sysmngService.saveMenuAuthInfo(dataMap);
			}
			
			if (result) {
				log.info("[" + loginUserId + "]\t저장 되었습니다.");
				jsonObj.put("status", "success");
				jsonObj.put("msg", "저장 되었습니다.");
			} else {
				log.info("[" + loginUserId + "]\t저장에 실패했습니다.");
				jsonObj.put("status", "fail");
				jsonObj.put("msg", "저장에 실패했습니다.");
			}
			
		} else {
			jsonObj.put("status", "usernotfound");
			jsonObj.put("msg", "세션이 종료되었거나 로그인 상태가 아닙니다.");
		}
		
		System.out.println("JSONObject::"+jsonObj.toString());
		writeJSON(response, jsonObj);
        
	}
	
}
