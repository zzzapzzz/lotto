package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;

@Service("myLottoService")
public class MyLottoService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * MY로또저장번호 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	public ArrayList<MyLottoSaveNumDto> getSaveNumList(Map map) {
		return (ArrayList<MyLottoSaveNumDto>) baseDao.getList("myLottoMapper.getSaveNumList", map);
	}
	
	/**
	 * MY로또저장번호 목록 개수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getSaveNumListCnt(Map map) {
		return (Integer) baseDao.getSingleRow("myLottoMapper.getSaveNumListCnt", map);
	}

	/**
	 * MY로또저장번호 일반등록
	 * @param dto
	 */
	public boolean insertMyData(Map map) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("myLottoMapper.insertMyData", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * MY로또저장번호 삭제
	 * @param dto
	 * @return
	 */
	public boolean deleteMyData(MyLottoSaveNumDto dto) {
		boolean flag = false;		
		int i = (Integer) baseDao.delete("myLottoMapper.deleteMyData", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}

	/**
	 * MY로또저장번호 등록체크
	 * 
	 * @param dto
	 * @return
	 */
	public int checkSaveMyData(MyLottoSaveNumDto dto) {
		return (Integer) baseDao.getSingleRow("myLottoMapper.checkSaveMyData", dto);
	}
}
