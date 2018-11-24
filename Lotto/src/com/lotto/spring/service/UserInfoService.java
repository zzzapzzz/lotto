package com.lotto.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dao.UserSession;
import com.lotto.spring.domain.dto.UserInfoDto;

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

}
