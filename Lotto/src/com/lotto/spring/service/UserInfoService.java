package com.lotto.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chello.base.common.util.StringUtil;
import com.chello.base.spring.core.DefaultService;
import com.lotto.common.CommonUtils;
import com.lotto.spring.domain.dao.UserSession;

@Service("userInfoService")
public class UserInfoService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired(required = true)
    private CommonService commonService;
	
	/**
	 * 로그인 호출 (DB 프로시저)
	 * 
	 * @unused 2020.04.14
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @return
	 */
//	public CaseInsensitiveMap loginProc(String email, String thwd, String access_ip) {
//		log.info("[" + email + "]\t[S] 로그인 호출 (DB 프로시저)");
//		log.info("[" + email + "]\t\temail=" + email);
//		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
//		
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", email);
//		map.put("thwd", thwd);
//		map.put("access_ip", access_ip);
//		
//		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.userLogin", map);
//	}
	
	/**
	 * 로그인
	 * 
	 * 2020.04.12
	 * cafe24의 경우 기본 호스팅으로 DB 함수, 프로시저 사용불가
	 * 그래서 서비스로 프로세스를 처리하도록 구현
	 * 
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @param isAdmin 
	 * @return
	 */
	public Map login(String email, String thwd, String access_ip, boolean isAdmin) {
		log.info("[" + email + "]\t[S] 로그인");
		log.info("[" + email + "]\t\temail=" + email);
		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("access_ip", access_ip);
		map.put("isAdmin", isAdmin);
		
		/* 1. 아이디 비밀번호 확인 */
		if (StringUtil.isEmpty(email) || StringUtil.isEmpty(thwd)) {
			map.put("result","F01");
			return map;
		}
		
		/* 2. 사용자 존재여부 확인 */
		int exist = this.checkDuplEmail(map);
		if (exist == 0) {
			map.put("result","F02");
			return map;
		}
		
		/* 3. 로그인 사용자정보 조회 */
		UserSession userSession = this.getUserInfo(email);
		map.put("user_no", userSession.getUser_no());			
		
		/* 4. 계정잠김상태 확인 */
		if ("N".equals(userSession.getUse_yn())) {
			
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "미사용 회원입니다. 계정을 활성화 하시겠습니까?");
			map.put("msg_s", "계정이 잠긴 사용자 로그인시도");			
			commonService.insertLoginAccess(map);
			
			map.put("result","F03");
			return map;
		}
		
		/* 5. 비밀번호 실패횟수 확인 */
		if (userSession.getLogin_fail_cnt() > 5) {
			
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "비밀번호 오류 횟수가 5회 초과하였습니다. 비밀번호를 다시 설정하시겠습니까?");
			map.put("msg_s", "비밀번호 오류 횟수 초과");			
			commonService.insertLoginAccess(map);
			
			map.put("result","F05");
			return map;
		}
		
		String encEmail = null;
		String encThwd = null;
		try {
			encEmail = CommonUtils.sha256(email);
			encThwd = CommonUtils.sha256(thwd);
			
			/* 6. 비밀번호 초기화 확인 */
			if (StringUtil.isNotEmpty(encEmail)
					&& encEmail.equals(userSession.getThwd())) {
			
				map.put("log_type", "LOGIN_FAIL");
				map.put("msg_l", "초기화되거나 유효기간이 만료된 비밀번호입니다.");
				map.put("msg_s", "초기화되거나 유효기간이 만료된 비밀번호");			
				commonService.insertLoginAccess(map);
				
				map.put("result","T01");
				return map;
			}
			
			/* 7. 비밀번호 일치 확인 */
			if (StringUtil.isNotEmpty(encThwd)
					&& !encThwd.equals(userSession.getThwd())) {
				
				map.put("log_type", "LOGIN_FAIL");
				map.put("msg_l", "아이디 또는 비밀번호가 일치하지 않습니다.");
				map.put("msg_s", "비밀번호 미일치");			
				commonService.insertLoginAccess(map);
				
				map.put("result","F02");
				return map;
			}
			
		} catch (Exception e) {
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "Encoding Exception =" + e.getMessage());
			map.put("msg_s", "비밀번호 미일치");			
			commonService.insertLoginAccess(map);
			
			log.info("[" + email + "]\t\tEncoding Exception =" + e.getMessage());
			map.put("result","T99");
			return map;
		}
		
		/* 8. 업무권한 체크 */
		if (StringUtil.isEmpty(userSession.getAuth_task())) {
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "사용 권한이 없습니다. 관리자에게 문의바랍니다.");
			map.put("msg_s", "업무권한 미설정");			
			commonService.insertLoginAccess(map);
			
			map.put("result","T05");
			return map;
		}
		
		/* 9. 메뉴권한 체크 */
		List<CaseInsensitiveMap> menuList = this.getMenuAuthUrlListForUser(map);
		if (menuList == null || menuList.size() == 0) {
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "접속 권한이 없습니다. 관리자에게 문의바랍니다.");
			map.put("msg_s", "메뉴권한 미설정");			
			commonService.insertLoginAccess(map);
			
			map.put("result","T06");
			return map;
		}
		
		/* 10. 비밀번호 변경이력 확인 (3개월) */
		// 이후에 필요시 개발할 예정 2020.04.12
		
		// 로그인 성공 처리
		map.put("result","T");
		return map;
	}
		
	
	/**
	 * SNS 로그인 호출
	 * 
	 * @unused 2020.04.14
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @return
	 */
//	@SuppressWarnings("rawtypes")
//	public CaseInsensitiveMap snsLoginProc(Map map) {
//		String email = (String) map.get("email");
//		String access_ip = (String) map.get("access_ip");
//		log.info("[" + email + "]\t[S] SNS 로그인 호출");
//		log.info("[" + email + "]\t\temail=" + email);
//		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
//		
//		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.userSnsLogin", map);
//	}
	
	/**
	 * SNS 로그인
	 * 
	 * 2020.04.12
	 * cafe24의 경우 기본 호스팅으로 DB 함수, 프로시저 사용불가
	 * 그래서 서비스로 프로세스를 처리하도록 구현
	 * 
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @param isAdmin 
	 * @return
	 */
	public Map<String, Object> snsLogin(Map<String, Object> map) {
		String email = (String) map.get("email");
		String access_ip = (String) map.get("access_ip");
		String sns_type = (String) map.get("sns_type");
		log.info("[" + email + "]\t[S] SNS 로그인");
		log.info("[" + email + "]\t\temail=" + email);
		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
		
		/* 1. 아이디 확인 */
		if (StringUtil.isEmpty(email)) {
			map.put("result","F01");
			return map;
		}
		
		/* 2. 사용자 존재여부 확인 */
		int exist = this.checkDuplEmail(map);
		if (exist == 0) {
			// 자동가입 처리
//			Map map = new HashMap();
//			map.put("email", email);
//			map.put("email_varify_yn", emailVarifyYn);
//			if (!"".equals(userId)) {
//				map.put("user_id", Integer.parseInt(userId));
//			}
//			map.put("nickname", nickname);
//			map.put("thumbnail_image", thumbnailImage);
//			map.put("sns_type", snsType);
//			map.put("access_ip", userIp);
			try {
				map.put("thwd", CommonUtils.sha256(email));
			} catch (Exception e) {
				log.info("[" + email + "]\t\tSNS Login Encoding Exception =" + e.getMessage());
				map.put("result","T99");
				return map;
			}
			
			boolean isExist = false;
			
			// GetUserKey 2020.04.12
			String key = "";
			do {
				// 키 생성
				log.debug("[" + email + "]\tUser Key 생성");
				key = this.getUserKey();
				
				// 존재여부 확인 (true:존재, false:존재하지 않음)
				log.debug("[" + email + "]\tUser Key 중복체크");
				isExist = this.checkDuplUserKey(key);
				
			} while (isExist);
			map.put("userKey", key);
				
			log.debug("[" + email + "]\t회원 자동가입 처리");
			int i = this.join(map);
			if (i > 0) {
				log.info("[" + email + "]\t\t>" + "회원가입 완료");
			}
				
		}
		
		/* 3. 로그인 사용자정보 조회 */
		UserSession userSession = this.getUserInfo(email);
		map.put("user_no", userSession.getUser_no());			
		
		/* 4. SNS 사용자확인 및 미등록사용자 등록 */
		boolean result = this.checkDuplSnsUser(map, sns_type);
		if (!result) {
			// 미등록사용자 등록 처리
			if (UserSession.SNS_NAVER.equals(sns_type)) {
				
				// 네이버 등록 확인
				exist = this.checkDuplNaver(map);
				if (exist == 0) {
					this.insertNaverUser(map);
					log.info("[" + email + "]\t\t>" + sns_type + " 회원가입 완료");
				}
				
			} else if (UserSession.SNS_KAKAO.equals(sns_type)) {
				
				// 카카오 등록 확인
				exist = this.checkDuplKakao(map);
				if (exist == 0) {
					this.insertKakaoUser(map);
					log.info("[" + email + "]\t\t>" + sns_type + " 회원가입 완료");
				}
			}
		}
		
		/* 4. 계정잠김상태 확인 */
		if ("N".equals(userSession.getUse_yn())) {
			
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "미사용 회원입니다. 계정을 활성화 하시겠습니까?");
			map.put("msg_s", "계정이 잠긴 사용자 로그인시도");			
			commonService.insertLoginAccess(map);
			
			map.put("result","F03");
			return map;
		}
		
		/* 5. 업무권한 체크 */
		if (StringUtil.isEmpty(userSession.getAuth_task())) {
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "사용 권한이 없습니다. 관리자에게 문의바랍니다.");
			map.put("msg_s", "업무권한 미설정");			
			commonService.insertLoginAccess(map);
			
			map.put("result","T05");
			return map;
		}
		
		/* 6. 메뉴권한 체크 */
		List<CaseInsensitiveMap> menuList = this.getMenuAuthUrlListForUser(map);
		if (menuList == null || menuList.size() == 0) {
			map.put("log_type", "LOGIN_FAIL");
			map.put("msg_l", "접속 권한이 없습니다. 관리자에게 문의바랍니다.");
			map.put("msg_s", "메뉴권한 미설정");			
			commonService.insertLoginAccess(map);
			
			map.put("result","T06");
			return map;
		}
		
		/* 10. 비밀번호 변경이력 확인 (3개월) */
		// 이후에 필요시 개발할 예정 2020.04.12
		
		
		// 로그인 성공 처리
		map.put("result","T");
		return map;
	}
	
	/**
	 * 사용자 정보 조회
	 * 
	 * @param user_id
	 * @return
	 */
	public UserSession getUserInfo(String email) {
		log.info("[" + email + "]\t[S] 사용자 정보 조회");
		log.info("[" + email + "]\t\temail=" + email);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);		
		
		return (UserSession) baseDao.getSingleRow("userAuthMapper.getUserInfo", map);
	}

	/**
	 * 시스템관리자 정보 조회
	 * 
	 * @param email
	 * @return
	 */
	public UserSession getAdminUserInfo(String email) {
		UserSession userSession = new UserSession();
		
		userSession.setEmail(email);
		userSession.setNickname("시스템관리자");
		userSession.setAuth_menu("admin");
		userSession.setAuth_task("admin");
		userSession.setAdmin(true);
		
		return userSession;
	}
	
	/**
	 * 일반 사용자 메뉴접근 URL 정보 조회
	 * 
	 * @param userid
	 *            로그인ID
	 * @return
	 */
	public List<CaseInsensitiveMap>  getMenuAuthUrlListForUser(Map map) {
		return (List<CaseInsensitiveMap> ) baseDao.getList("userAuthMapper.getMenuAuthUrlListForUser", map);
	}
	
	/**
	 * 사용자별 메뉴접근 URL 정보 조회
	 * 
	 * @param userid
	 *            로그인ID
	 * @return
	 */
	public List<CaseInsensitiveMap>  getMenuAuthUrlList(Map map) {
		return (List<CaseInsensitiveMap> ) baseDao.getList("userAuthMapper.getMenuAuthUrlList", map);
	}
	
	/**
	 * 사용자 로그 기록
	 *  
	 * @param log_type
	 * @param user_no
	 * @param user_ip
	 * @param msgS
	 * @param msgL
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertLogAgent(String log_type, int user_no, String user_ip, String msgS, String msgL) {
		HashMap map = new HashMap();
		map.put("log_type", log_type);
		map.put("user_no", user_no);
		map.put("user_ip", user_ip);
		map.put("msg_s", msgS);
		map.put("msg_l", msgL);

		int result = baseDao.insert("userAuthMapper.insertLogAgent", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if (result > 0) {
			log.debug("\t\tSUCCESS.");
//		} else {
//			log.debug("\t\tFAIL.");
//		}
	}

	/**
	 * 사용자 초기정보 등록 
	 * 
	 * @param map
	 * @return
	 */
	public CaseInsensitiveMap setUserInfo(Map map) {
		// 프로시저 호출
//		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.setUserInfo", map);
		int i = baseDao.update("userAuthMapper.setUserInfo", map);
		log.debug("\t\t사용자 초기정보 등록 결과 = " + i);
		
		CaseInsensitiveMap caseInsensitiveMap = new CaseInsensitiveMap();
		caseInsensitiveMap.put("result", "success");
		caseInsensitiveMap.put("msg", "사용자 초기정보 등록 완료");
		return caseInsensitiveMap;
	}
	
	/**
	 * 사용자 비밀번호 변경
	 * 
	 * @param map
	 * @return
	 */
	public CaseInsensitiveMap changeThwd(Map map) {
		// 프로시저 호출
//		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.changeThwd", map);
		int i = baseDao.update("userAuthMapper.changeThwd", map);
		log.debug("\t\t사용자 비밀번호 변경 결과 = " + i);
		
		CaseInsensitiveMap caseInsensitiveMap = new CaseInsensitiveMap();
		caseInsensitiveMap.put("result", "success");
		caseInsensitiveMap.put("msg", "사용자 초기정보 등록 완료");
		return caseInsensitiveMap;
	}
	
	/**
	 * 사용자 비밀번호 초기화
	 * 
	 * @param map
	 * @return
	 */
	public CaseInsensitiveMap initThwd(Map map) {
		// 프로시저 호출
//		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.initThwd", map);
		int i = baseDao.update("userAuthMapper.changeThwd", map);
		log.debug("\t\t사용자 초기정보 등록 결과 = " + i);
		
		CaseInsensitiveMap caseInsensitiveMap = new CaseInsensitiveMap();
		caseInsensitiveMap.put("result", "success");
		caseInsensitiveMap.put("msg", "사용자 초기정보 등록 완료");
		return caseInsensitiveMap;
	}
	
	/**
	 * 사용자 등록여부 체크
	 * 
	 * @param map
	 * @return
	 */
	public int checkDuplEmail(Map map) {
		return (int) baseDao.getSingleRow("userAuthMapper.checkDuplEmail", map);
	}
	
	/**
	 * 사용자 등록여부 체크 (naver)
	 * 2020.04.13
	 * 
	 * @param map
	 * @return
	 */
	public int checkDuplNaver(Map map) {
		return (int) baseDao.getSingleRow("userAuthMapper.checkDuplNaver", map);
	}
	
	/**
	 * 사용자 등록여부 체크 (kakao)
	 * 2020.04.13
	 * 
	 * @param map
	 * @return
	 */
	public int checkDuplKakao(Map map) {
		return (int) baseDao.getSingleRow("userAuthMapper.checkDuplKakao", map);
	}
	
	/**
	 * SMS 사용자 등록여부 체크
	 * 2020.04.12
	 * 
	 * @param map
	 * @param snsType
	 * @return true: 존재함, false: 존재하지 않음
	 */
	public boolean checkDuplSnsUser(Map map, String snsType) {
		boolean isExist = false;
		int i = 0;
		if (UserSession.SNS_NAVER.equals(snsType)) {
			i = (int) baseDao.getSingleRow("userAuthMapper.checkDuplSnsNaver", map);
		} else if (UserSession.SNS_NAVER.equals(snsType)) {
			i = (int) baseDao.getSingleRow("userAuthMapper.checkDuplSnsKakao", map);
		}
		
		if (i > 0) {
			isExist = true;
		}
		
		return isExist;
	}
	
	/**
	 * 회원가입
	 * @param map
	 * @return
	 */
	public int join(Map map) {
		return (int) baseDao.insert("userAuthMapper.join", map);
	}

	/**
	 * 회원등록 (naver)
	 * 2020.04.13
	 * 
	 * @param map
	 * @return
	 */
	public int insertNaverUser(Map map) {
		return (int) baseDao.insert("userAuthMapper.insertNaverUser", map);
	}
	
	/**
	 * 회원등록 (kakao)
	 * 2020.04.13
	 * 
	 * @param map
	 * @return
	 */
	public int insertKakaoUser(Map map) {
		return (int) baseDao.insert("userAuthMapper.insertKakaoUser", map);
	}
	
	/**
	 * 접속권한 체크
	 * @param map email : 접속자 이메일, authCd : 접속자 권한코드, menuUrl : 현재 접속 URL
	 * @return true : 권한있음. false : 권한없음.
	 */
	public boolean checkAuth(Map map) {
		String email = (String) map.get("email");
		log.info("[" + email + "]\t[S] 접속권한 체크");
		int isExist =  (int) baseDao.getSingleRow("userAuthMapper.checkAuth", map);
		if (isExist > 0) {
			log.info("[" + email + "]\t\t권한 있음.");			
			return true;
		} else {
			log.info("[" + email + "]\t\t권한 없음.");			
			return false;
		}
	}

	/**
	 * USER KEY 생성 (Random Key 16자리)
	 * 2020.04.12
	 * 
	 * @return
	 */
	public String getUserKey() {
		String key = "";
		
		String[][] arrTempKey = {
			/* 대문자 포함 (I,O 제외) */
			/* upper case - 26 (24개)*/
	        {"A","1"},{"B","1"},{"C","1"},{"D","1"},{"E","1"},{"F","1"},{"G","1"},{"H","1"},{"J","1"},
	        {"K","1"},{"L","1"},{"M","1"},{"N","1"},{"P","1"},{"Q","1"},{"R","1"},{"S","1"},{"T","1"},
	        {"U","1"},{"V","1"},{"W","1"},{"X","1"},{"Y","1"},{"Z","1"},
	        /* 소문자 포함 (i,l,o 제외) */
	        /* lower case - 26 (23개) */
	        {"a","2"},{"b","2"},{"c","2"},{"d","2"},{"e","2"},{"f","2"},{"g","2"},{"h","2"},{"j","2"},
	        {"k","2"},{"m","2"},{"n","2"},{"p","2"},{"q","2"},{"r","2"},{"s","2"},{"t","2"},{"u","2"},
	        {"v","2"},{"w","2"},{"x","2"},{"y","2"},{"z","2"},
	        /* 숫자 포함 (0, 1 제외) */
	        /* number - 10 (8개) */
	        {"2","3"},{"3","3"},{"4","3"},{"5","3"},{"6","3"},{"7","3"},{"8","3"},{"9","3"}
		};
		
		// 1. 첫글자는 문자로 설정
		int randomSeq = (int) (Math.random() * (24 + 23));
		key += arrTempKey[randomSeq][0];
		
		// 2. 남은 글자 추출
		while (key.length() < 16) {
			randomSeq = (int) (Math.random() * (24 + 23 + 8));
			key += arrTempKey[randomSeq][0];
		}
			
		return key;
	}

	/**
	 * User Key 중복 체크
	 * 2020.04.12
	 * 
	 * @param key
	 * @return
	 */
	public boolean checkDuplUserKey(String key) {
		boolean exist = false;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userKey", key);
		int cnt = (int) baseDao.getSingleRow("userAuthMapper.checkDuplUserKey", map);
		if (cnt > 0) {
			exist = true;
		}
		
		return exist;
	}

}
