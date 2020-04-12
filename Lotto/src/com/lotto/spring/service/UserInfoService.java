package com.lotto.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dao.UserSession;

@Service("userInfoService")
public class UserInfoService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 로그인 호출
	 * 
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @return
	 */
	public CaseInsensitiveMap loginProc(String email, String thwd, String access_ip) {
		log.info("[" + email + "]\t[S] 로그인 호출");
		log.info("[" + email + "]\t\temail=" + email);
		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("thwd", thwd);
		map.put("access_ip", access_ip);
		
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.userLogin", map);
	}
	
	/**
	 * SNS 로그인 호출
	 * 
	 * @param email
	 * @param thwd
	 * @param access_ip
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public CaseInsensitiveMap snsLoginProc(Map map) {
		String email = (String) map.get("email");
		String access_ip = (String) map.get("access_ip");
		log.info("[" + email + "]\t[S] SNS 로그인 호출");
		log.info("[" + email + "]\t\temail=" + email);
		log.info("[" + email + "]\t\taccess_ip=" + access_ip);
		
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.userSnsLogin", map);
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
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.setUserInfo", map);
	}
	
	/**
	 * 사용자 비밀번호 변경
	 * 
	 * @param map
	 * @return
	 */
	public CaseInsensitiveMap changeThwd(Map map) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.changeThwd", map);
	}
	
	/**
	 * 사용자 비밀번호 초기화
	 * 
	 * @param map
	 * @return
	 */
	public CaseInsensitiveMap initThwd(Map map) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.initThwd", map);
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
	 * 회원가입
	 * @param map
	 * @return
	 */
	public int join(Map map) {
		return (int) baseDao.insert("userAuthMapper.join", map);
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
