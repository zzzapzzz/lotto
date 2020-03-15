package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dto.ServiceApplyDto;
import com.lotto.spring.domain.dto.ServiceInfoDto;

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
	
	/**
	 * 서비스 신청정보 조회
	 * 
	 * @param dto
	 * @return
	 */
	public ServiceApplyDto getServiceApply(ServiceApplyDto dto) {
		return (ServiceApplyDto) baseDao.getSingleRow("infoMapper.getServiceApply", dto);
	}
	
	/**
	 * 서비스 신청정보 목록 조회
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceApplyDto> getServiceApplyList(ServiceApplyDto dto) {
		return (ArrayList<ServiceApplyDto>) baseDao.getList("infoMapper.getServiceApplyList", dto);
	}
	
	/**
	 * 서비스 신청정보 목록 건수 조회
	 * 
	 * @param dto
	 * @return
	 */
	public int getServiceApplyListCnt(ServiceApplyDto dto) {
		return (Integer) baseDao.getSingleRow("infoMapper.getServiceApplyListCnt", dto);
	}
}