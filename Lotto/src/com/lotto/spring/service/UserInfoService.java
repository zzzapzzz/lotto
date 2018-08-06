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
	 * @param user_id
	 * @param user_pwd
	 * @param user_ip
	 * @return
	 */
	public CaseInsensitiveMap loginProc(String user_id, String user_pwd, String user_ip) {
		String loginUserId = user_id;
		log.info("[" + loginUserId + "]\t[S] 로그인 호출");
		log.info("[" + loginUserId + "]\t\tuser_id=" + user_id);
		log.info("[" + loginUserId + "]\t\tuser_ip=" + user_ip);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("user_pwd", user_pwd);
		map.put("user_ip", user_ip);
		return (CaseInsensitiveMap) baseDao.getSingleRow("userAuthMapper.userLogin", map);
	}
	
	/**
	 * 사용자 정보 조회
	 * 
	 * @param user_id
	 * @return
	 */
	public UserSession getUserInfo(String usr_id) {
		String loginUserId = usr_id;
		log.info("[" + loginUserId + "]\t[S] 사용자 정보 조회");
		log.info("[" + loginUserId + "]\t\tuserid=" + loginUserId);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("usr_id", usr_id);		
		
		return (UserSession) baseDao.getSingleRow("userAuthMapper.getUserInfo", map);
	}
	
	/**
	 * 시스템관리자 정보 조회
	 * 
	 * @return
	 */
	public UserSession getAdminUserInfo(String usr_id) {
		UserSession userSession = new UserSession();
		
		userSession.setUsr_id(usr_id);
		userSession.setUsr_nm("시스템관리자");
		userSession.setAuth_menu("system");
		userSession.setAuth_task("system");
		
		return userSession;
	}
	
	/**
	 * 사용자별 메뉴접근 URL 정보 조회
	 * 
	 * @param userid
	 *            로그인ID
	 * @return
	 */
	public List<CaseInsensitiveMap>  getMenuAuthUrlList(Map<String, String> map) {
		String loginUserId = map.get("user_id");
		log.info("[" + loginUserId + "]\t[S] 사용자별 메뉴접근 URL 정보 조회");
		log.info("[" + loginUserId + "]\t\tuserid=" + loginUserId);

		return (List<CaseInsensitiveMap> ) baseDao.getList("userAuthMapper.getMenuAuthUrlList", map);
	}
	
	/**
	 * 사용자 로그 기록
	 * 
	 * @param log_type 로그유형
	 * @param user_id 사용자ID
	 * @param user_ip 접속IP
	 * @param msg 메시지
	 * @return
	 */
	public void insertLogAgent(String log_type, String user_id, String user_nm, String user_ip, String msg) {
		log.info("[" + user_id + "]\t[S] 사용자 로그 기록");
		log.info("[" + user_id + "]\t\tlog_type=" + log_type);
		log.info("[" + user_id + "]\t\tuser_ip=" + user_ip);
		log.info("[" + user_id + "]\t\tmsg=" + msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("log_type", log_type);
		map.put("user_id", user_id);
		map.put("user_nm", user_nm);
		map.put("user_ip", user_ip);
		map.put("msg", msg);

		int result = baseDao.insert("userAuthMapper.insertLogAgent", map);
		if (result > 0) {
			log.debug("\t\tSUCCESS.");
		} else {
			log.debug("\t\tFAIL.");
		}
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
