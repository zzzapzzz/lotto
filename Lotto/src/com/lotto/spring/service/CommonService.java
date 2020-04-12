package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;

@Service("commonService")
public class CommonService extends DefaultService {

//	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 코드 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<CaseInsensitiveMap> getCodeList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("commonMapper.getCodeList", map);
	}
	
	/**
	 * 최근 로그 조회
	 * 2018.04.15
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public CaseInsensitiveMap getLastLog(Map map) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("commonMapper.getLastLog", map);
	}
	
	/**
	 * 로그 등록
	 * 2018.04.15
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean logInsert(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("commonMapper.logInsert", map);
		//2018.04.25 리턴값 버그로 true 처리
		//if(i > 0) {
			flag = true;		
		//}
		return flag;				
	}
	
	/**
	 * 로그인 로그등록
	 * 2020.04.12
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertLoginAccess(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("commonMapper.insertLoginAccess", map);
		//2018.04.25 리턴값 버그로 true 처리
		//if(i > 0) {
		flag = true;		
		//}
		return flag;				
	}
}
