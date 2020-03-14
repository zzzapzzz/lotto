package com.lotto.spring.service;

import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dto.ServiceApplyDto;

@Service("infoService")
public class InfoService extends DefaultService {

//	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 서비스 신청하기
	 * 
	 * @param map
	 * @return
	 */
	public boolean applyService(ServiceApplyDto dto) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("infoMapper.applyService", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
}