package com.lotto.spring.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.common.LottoUtil;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.EndNumDto;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.LowHighDto;
import com.lotto.spring.domain.dto.MCNumDto;
import com.lotto.spring.domain.dto.MenuInfoDto;
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.TaskInfoDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.UserInfoDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.domain.dto.ZeroRangeDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("sysmngService")
public class SysmngService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired(required = true)
    private LottoDataService lottoDataService;
	
	/**
	 * 사용자 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	public ArrayList<UserInfoDto> getUserList(Map map) {
		return (ArrayList<UserInfoDto>) baseDao.getList("sysmngMapper.getUserList", map);
	}
	
	/**
	 * 사용자 목록 개수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getUserListCnt(Map map) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getUserListCnt", map);
	}
	
	/**
	 * 사번 중복체크
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean dupCheckUserId(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.getSingleRow("sysmngMapper.dupCheckUserId", map);
		if(i > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 사용자정보 등록
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap createUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.createUserInfo", dto);
	}
	
	/**
	 * 사용자정보 수정
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap modifyUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.modifyUserInfo", dto);
	}
	
	/**
	 * 사용자정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap deleteUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.deleteUserInfo", dto);
	}
	
	/**
	 * 사용자목록 등록
	 * 2018.01.28
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertUserInfoList(Map map) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("sysmngMapper.insertUserInfoList", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 당첨번호 목록 조회 (Dto)
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WinDataDto> getWinDataList(WinDataDto dto) {
		return (ArrayList<WinDataDto>) baseDao.getList("sysmngMapper.getWinDataList", dto);
	}
	
	/**
	 * 당첨번호 분석정보 목록 조회 (Dto)
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WinDataAnlyDto> getWinDataAnlyList(WinDataDto dto) {
		
		ArrayList<WinDataAnlyDto> winDataList = (ArrayList<WinDataAnlyDto>) baseDao.getList("sysmngMapper.getWinDataAnlyList", dto);
		if (winDataList != null && winDataList.size() > 0) {
			
			for (int i = 0; i < winDataList.size(); i++) {
				WinDataAnlyDto winData = winDataList.get(i);
				winData.setNumbers(LottoUtil.getNumbers(winData));
				winData.setDifNumbers(LottoUtil.getDifNumbers(winData.getNumbers()));
				
			}
		}
		
		return winDataList;
	}
	
	/**
	 * 당첨번호 목록 건수 조회
	 * 
	 * @param dto
	 * @return
	 */
	public int getWinDataListCnt(WinDataDto dto) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getWinDataListCnt", dto);
	}
	
	/**
	 * 당첨번호 조회
	 * 
	 * @param dto
	 * @return
	 */
	public WinDataDto getWinData(WinDataDto dto) {
		return (WinDataDto) baseDao.getSingleRow("sysmngMapper.getWinData", dto);
	}
	
	/**
	 * 마지막 당첨번호 조회
	 * 
	 * @param dto
	 * @return
	 */
	public WinDataDto getLastWinData() {
		return (WinDataDto) baseDao.getSingleRow("sysmngMapper.getLastWinData");
	}
	
	/**
	 * 당첨번호 목록 등록
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertWinDataList(Map map) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("sysmngMapper.insertWinDataList", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 당첨번호 등록
	 * 
	 * @param dto
	 * @return
	 */
	public boolean insertWinData(WinDataDto dto) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("sysmngMapper.insertWinData", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 당첨번호 수정
	 * 
	 * @param dto
	 * @return
	 */
	public boolean modifyWinData(WinDataDto dto) {
		boolean flag = false;		
		int i = (Integer) baseDao.update("sysmngMapper.modifyWinData", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 당첨번호 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteWinData(WinDataDto dto) {
		boolean flag = false;		
		int i = (Integer) baseDao.delete("sysmngMapper.deleteWinData", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 회차합정보 등록
	 * 
	 * @param dto
	 * @return
	 */
	public boolean insertCountSumInfo(List<WinDataDto> winDataList) {
		
		CountSumDto dto = lottoDataService.getLastContainCnt(winDataList);
		
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertCountSumInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 회차합정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteCountSumInfo(CountSumDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteCountSumInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 제외수정보 등록
	 * 
	 * @param dto
	 * @return
	 */
	public boolean insertExcludeInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
//		CountSumDto dto = this.getLastContainCnt(winDataList);
		// 제외수 규칙 분석
		List<ArrayList<Integer>> getNotContainNumbers = lottoDataService.getNotContainNumbers(winDataList);
		int[] rule = {0,0,0,0,0,0};
    	String excludeNum = "";	//제외수    	
    	if (getNotContainNumbers != null && getNotContainNumbers.size() > 0 ) {
    		log.info("\t>> 개수 : " + getNotContainNumbers.size());
    		rule = lottoDataService.getNotContainNumbersRule(getNotContainNumbers);
    		excludeNum = lottoDataService.getNotContainNumbersRuleMsg(rule, lastData);
    		log.info("\t>> 제외수 : " + excludeNum);
    	} else {
    		log.info("\t>> 개수 : 0");
    	}
		
    	// 제외수정보 등록
    	ExcludeDto dto = new ExcludeDto();
    	int exCount = lastData.getWin_count() + 1;	//예상회차
    	dto.setEx_count(exCount);
    	dto.setNum1_rule(rule[0]);
    	dto.setNum2_rule(rule[1]);
    	dto.setNum3_rule(rule[2]);
    	dto.setNum4_rule(rule[3]);
    	dto.setNum5_rule(rule[4]);
    	dto.setNum6_rule(rule[5]);
    	dto.setExclude_num(excludeNum);
    	
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertExcludeInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}

	/**
	 * 제외수정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteExcludeInfo(ExcludeDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteExcludeInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	
	/**
	 * 총합정보 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	public boolean insertTotalInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		// 총합 범위 구하기
//		int[] arrTotalRange = lottoDataService.getLowTotalHighTotal(winDataList);
		int[] arrTotalRange = {0,0};
		// 총합 출현번호를 통해 설정하도록 추가 2019.01.18
		List<CaseInsensitiveMap> totalGroupCntList = this.getLowTotalHighTotal(winDataList);
		if (totalGroupCntList != null && totalGroupCntList.size() > 0) {
			arrTotalRange[0] = Integer.parseInt(String.valueOf(totalGroupCntList.get(0).get("total")));
			arrTotalRange[1] = Integer.parseInt(String.valueOf(totalGroupCntList.get(totalGroupCntList.size()-1).get("total")));
		} else {
			arrTotalRange = lottoDataService.getLowTotalHighTotal(winDataList);
		}
		String totalRange = arrTotalRange[0] + "~" + arrTotalRange[1];
		
		TotalDto dto = new TotalDto();
		dto.setWin_count(lastData.getWin_count());
		dto.setLow_total(arrTotalRange[0]);
		dto.setHigh_total(arrTotalRange[1]);
		dto.setTotal_range(totalRange);
		
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertTotalInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	
	/**
	 * @description <div id=description><b>총합 범위 구하기</b></div>
	 *              <div id=detail>전체 총합분포 중 90%출현된 총합의 범위를 구한다.</div>
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CaseInsensitiveMap> getLowTotalHighTotal(List<WinDataDto> winDataList) {
		// 최근 당첨번호
		WinDataDto lastData = winDataList.get(0);
		int lastWinCount = lastData.getWin_count();
		
		// 총합 일치
		int cnt = 2;
		double AIM_PER = 10.0;	// 목표율
		Map map = new HashMap();
		map.put("cnt", cnt);
		List<CaseInsensitiveMap> totalGroupCntList = null;
				
		do {
			map.put("cnt", cnt);
			int totalGroupSumCnt = this.getTotalGroupSumCnt(map);
			double d_cnt = totalGroupSumCnt;
			double d_total = lastWinCount;
			DecimalFormat df = new DecimalFormat("#.##"); 
			double percent = Double.parseDouble(df.format( d_cnt/d_total ));
			
			if (percent * 100 >= AIM_PER) {
				totalGroupCntList = this.getTotalGroupCntList(map);
				break;
			}
			// 목표율에 도달하지 못할 경우, 다음 개수 증가
			cnt++;
		} while (true);
		
		return totalGroupCntList;
	}

	/**
	 * 총합정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteTotalInfo(TotalDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteTotalInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 끝수합정보 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	public boolean insertEndNumInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		int[] arrEndNumRange = lottoDataService.getLowEndNumHighEndNum(winDataList);
    	String endNumRange = arrEndNumRange[0] + "~" + arrEndNumRange[1];
    	
    	EndNumDto dto = new EndNumDto();
    	dto.setWin_count(lastData.getWin_count());
    	dto.setLow_endnum(arrEndNumRange[0]);
    	dto.setHigh_endnum(arrEndNumRange[1]);
    	dto.setEndnum_range(endNumRange);
    	
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertEndNumInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 끝수합정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteEndNumInfo(EndNumDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteEndNumInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 궁합수정보 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertMCNumInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		//번호별 궁합/불협수
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumber(winDataList);
    	
		Map mcInfoMap = lottoDataService.getMCInfoMap(mcNumberMap, lastData);
		
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertMCNumInfo", mcInfoMap);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	
	
	/**
	 * 궁합수정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteMCNumInfo(MCNumDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteMCNumInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 저고비율정보 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insertLowHighInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		String[] headTitle = LottoUtil.getRatioTitle();
    	String[] arrLowHigh = lottoDataService.getLowHighRatio(winDataList, headTitle);
    	
    	Map lowHighMap = new HashMap();
    	List lowHighList = new ArrayList();
    	for (int i = 0; i < arrLowHigh.length; i++) {
    		Map dataMap = new HashMap();	//등록할 객체
			dataMap.put("win_count", lastData.getWin_count());
			dataMap.put("lowhigh_type", headTitle[i]);
			dataMap.put("ratio", arrLowHigh[i]);
			
			lowHighList.add(dataMap);
		}
    	lowHighMap.put("lowHighList", lowHighList);
    	
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertLowHighInfo", lowHighMap);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
		flag = true;
//		}
		return flag;
	}
	
	/**
	 * 저고비율정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteLowHighInfo(LowHighDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteLowHighInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 홀수짝수비율 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insertOddEvenInfo(List<WinDataDto> winDataList) {
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		String[] headTitle = LottoUtil.getRatioTitle();
		String[] arrOddEven = lottoDataService.getOddEvenRatio(winDataList, headTitle);
		
		Map oddEvenMap = new HashMap();
    	List oddEvenList = new ArrayList();
    	for (int i = 0; i < arrOddEven.length; i++) {
    		Map dataMap = new HashMap();	//등록할 객체
			dataMap.put("win_count", lastData.getWin_count());
			dataMap.put("oddeven_type", headTitle[i]);
			dataMap.put("ratio", arrOddEven[i]);
			
			oddEvenList.add(dataMap);
		}
    	oddEvenMap.put("oddEvenList", oddEvenList);
    	
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertOddEvenInfo", oddEvenMap);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
		flag = true;
//		}
		return flag;
	}
	
	
	
	/**
	 * 홀짝비율정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteOddEvenInfo(OddEvenDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteOddEvenInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 미출현구간대 정보 등록
	 * 
	 * @param winDataList
	 * @return
	 */
	public boolean insertZeroRangeInfo(List<WinDataDto> winDataList) {
		
		ZeroRangeDto dto = new ZeroRangeDto();
		
		WinDataDto lastData = winDataList.get(winDataList.size()-1);
		
		int[] containGroupCnt = lottoDataService.getZeroCntRangeData(lastData);
		/** 숫자가 없는 자리 수의 개수 */
		int zeroCnt = 0;
		String zeroRange = "";
		
		//숫자가 포함되지 않은 자릿수의 개수를 구한다.
		for (int index = 0; index < containGroupCnt.length; index++) {

			if (containGroupCnt[index] == 0) {
				zeroCnt++;

				if (index == 0) {
					zeroRange += "1~10";
				} else if (index == 1) {
					if (zeroRange.length() > 0) {
						zeroRange += ", ";
					}
					zeroRange += "11~20";
				} else if (index == 2) {
					if (zeroRange.length() > 0) {
						zeroRange += ", ";
					}
					zeroRange += "21~30";
				} else if (index == 3) {
					if (zeroRange.length() > 0) {
						zeroRange += ", ";
					}
					zeroRange += "31~40";
				} else if (index == 4) {
					if (zeroRange.length() > 0) {
						zeroRange += ", ";
					}
					zeroRange += "41~45";
				}
			}
		}
				
		dto.setWin_count(lastData.getWin_count());
		dto.setRange1(containGroupCnt[0]);
		dto.setRange2(containGroupCnt[1]);
		dto.setRange3(containGroupCnt[2]);
		dto.setRange4(containGroupCnt[3]);
		dto.setRange5(containGroupCnt[4]);
		dto.setZero_cnt(zeroCnt);
		dto.setZero_range(zeroRange);
		
		
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertZeroRangeInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
		flag = true;
//		}
		return flag;
	}

	/**
	 * 미출현구간대 정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public boolean deleteZeroRangeInfo(ZeroRangeDto dto) {
		
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteZeroRangeInfo", dto);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 미출현구간대 정보 조회
	 * 
	 * @param dto
	 * @return
	 */
	public ZeroRangeDto getZeroRangeInfo(WinDataDto dto) {
		return (ZeroRangeDto) baseDao.getSingleRow("sysmngMapper.getZeroRangeInfo", dto);
	}
	
	/**
	 * 예상번호 정보 조회 (Dto)
	 * 
	 * @param dto
	 * @return
	 */
	public ExDataDto getExDataInfo(ExDataDto dto) {
		ExDataDto exData = (ExDataDto) baseDao.getSingleRow("sysmngMapper.getExDataInfo", dto);
		
		if (exData != null) {
			exData.setNumbers(LottoUtil.getNumbers(exData));
			exData.setDifNumbers(LottoUtil.getDifNumbers(exData.getNumbers()));
		}
		
		return exData;
	}
	
	/**
	 * 예상번호 목록 조회 (Dto)
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExDataDto> getExDataList(ExDataDto dto) {
		return (ArrayList<ExDataDto>) baseDao.getList("sysmngMapper.getExDataList", dto);
	}
	
	/**
	 * 예상번호 목록 건수 조회
	 * 
	 * @param dto
	 * @return
	 */
	public int getExDataListCnt(ExDataDto dto) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getExDataListCnt", dto);
	}
	
	/**
	 * 최근 당첨회차 저고비율 조회
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LowHighDto> getLowHighDataList(WinDataDto dto) {
		return (ArrayList<LowHighDto>) baseDao.getList("sysmngMapper.getLowHighDataList", dto);
	}
	
	/**
	 * 최근 당첨회차 홀짝비율 조회
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OddEvenDto> getOddEvenDataList(WinDataDto dto) {
		return (ArrayList<OddEvenDto>) baseDao.getList("sysmngMapper.getOddEvenDataList", dto);
	}
	
	/**
	 * 회차합 조회
	 * 
	 * @param dto
	 * @return
	 */
	public CountSumDto getCountSumInfo(WinDataDto dto) {
		return (CountSumDto) baseDao.getSingleRow("sysmngMapper.getCountSumInfo", dto);
	}
	
	/**
	 * 총합정보 조회
	 * 
	 * @param lastData
	 * @return
	 */
	public TotalDto getTotalInfo(WinDataDto lastData) {
		return (TotalDto) baseDao.getSingleRow("sysmngMapper.getTotalInfo", lastData);
	}
	
	/**
	 * 끝수합정보 조회
	 * 
	 * @param lastData
	 * @return
	 */
	public EndNumDto getEndNumInfo(WinDataDto lastData) {
		return (EndNumDto) baseDao.getSingleRow("sysmngMapper.getEndNumInfo", lastData);
	}
	
	/**
	 * 제외수정보 조회
	 * 
	 * @param dto
	 * @return
	 */
	public ExcludeDto getExcludeInfo(ExDataDto dto) {
		return (ExcludeDto) baseDao.getSingleRow("sysmngMapper.getExcludeInfo", dto);
	}
	
	/**
	 * 궁합수 목록 조회
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MCNumDto> getMcNumList(WinDataDto dto) {
		return (ArrayList<MCNumDto>) baseDao.getList("sysmngMapper.getMcNumList", dto);
	}
	
	/**
	 * 예상번호 목록 추출 및 등록
	 * 
	 * @param winDataList 전체데이터 (오름차순) 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 */
	public void insertExptNumList(List<WinDataAnlyDto> winDataList, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		
		/*********************************************************
		 * 예상 회차합 번호추출
		 *********************************************************/
		//예상 회차합이 낮을 경우, 7~10으로 임의 설정
		int expectContainCnt = 0;
		if (exptPtrnAnlyInfo != null) {
			// 예상 회차합
			expectContainCnt = exptPtrnAnlyInfo.getCount_sum();
			if (expectContainCnt < 7) {
	//			expectContainCnt = 10;
				expectContainCnt = LottoUtil.getRandomContainCnt();
				log.warn("예상 회차합 (7미만) : " + exptPtrnAnlyInfo.getCount_sum());
			} else {
				//2017.06.12
				expectContainCnt = LottoUtil.getRandomContainCnt();
			}
		} else {
			expectContainCnt = LottoUtil.getRandomContainCnt();
		}

		//입력한 회차합 출현숫자
		List<Integer> numberOfInputCountList = lottoDataService.getNumbersOfInputCount(winDataList, expectContainCnt);		
		//입력한 회차합 미출현 숫자
		List<Integer> numberOfNotInputCountList = lottoDataService.getNumbersOfInputCount(numberOfInputCountList);	
		
		
		/*********************************************************
		 * 제외수 적용 번호추출
		 *********************************************************/
		//제외수를 저장할 list
		List<Integer> deleteTargetList = new ArrayList<Integer>();
		//제외수를 저장할 map
		Map<Integer, Integer> deleteTargetMap = new HashMap<Integer, Integer>();
		// 제외수 규칙 조회
		ExDataDto exDataDto = new ExDataDto();
		exDataDto.setEx_count(exptPtrnAnlyInfo.getEx_count());
		ExcludeDto excludeDto = this.getExcludeInfo(exDataDto);
		String[] excludeNum = excludeDto.getExclude_num().replaceAll(" ", "").split(",");
		for (int i = 0; i < excludeNum.length; i++) {
			deleteTargetList.add(Integer.parseInt(excludeNum[i]));
			deleteTargetMap.put(Integer.parseInt(excludeNum[i]), Integer.parseInt(excludeNum[i]));
		}
		
		//제외수를 포함하지 않는 회차합 숫자들
		List<Integer> numberOfContainList = lottoDataService.getListWithExcludedNumber(numberOfInputCountList, deleteTargetList);
		//제외수를 포함하지 않는 회차합이 아닌 숫자들
		List<Integer> numberOfNotContainList = lottoDataService.getListWithExcludedNumber(numberOfNotInputCountList, deleteTargetList);
		
		/*********************************************************
		 * 예상데이터 랜덤 조합 추출 및 등록
		 *********************************************************/
		/** 추출된 조합숫자 목록 */
		List<ExDataDto> expectDataList = lottoDataService.getExDataList(exptPtrnAnlyInfo, numberOfContainList, numberOfNotContainList, deleteTargetMap);
		
		
		// 표준 끝수합 범위 설정
		int[] lowHighEndNumData = lottoDataService.getEndNumberBaseDistribution(winDataList);
		/** 번호간 범위 결과 목록 */
		ArrayList<HashMap<String, Integer>> numbersRangeList = lottoDataService.getNumbersRangeList(winDataList);
		/** 숫자별 출현번호 결과 목록 */
		ArrayList<ArrayList<Integer>> groupByNumbersList = lottoDataService.getGroupByNumbersList(winDataList);
		/** 번호별 궁합/불협수 */
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumberByAnly(winDataList);
		
		if (expectDataList != null && expectDataList.size() > 0) {
			log.info("추출된 목록 건수 = " + expectDataList.size());
			//추출된 번호 전체를 패턴과 비교하여 예측패턴과 가장 많이 일치하는 번호 목록을 구한다.
			
			// 기존 예상번호 삭제 (예상회차 -2회차 이전 데이터 모두 삭제 처리)
			Map<String, Object> deleteParamMap = new HashMap<String, Object>();
			int pastExCount = exptPtrnAnlyInfo.getEx_count() - 2;
			deleteParamMap.put("past_ex_count", pastExCount);
			baseDao.delete("sysmngMapper.deletePastExptNumList", deleteParamMap);
			
			
			long startTime = System.currentTimeMillis();
			
			/*
			 * 2019.01.05 foreach 등록 테스트
			 * 1. 110만건 java.lang.OutOfMemoryError: Java heap space 오류발생함.
			 * 2. 10만건으로 줄임. java.lang.OutOfMemoryError: Java heap space
			 */
			List<ExDataDto> expectDataPartList = new ArrayList<ExDataDto>();
			int INSERT_COUNT = 100;
			for (int i = 0; i < expectDataList.size(); i++) {
		
				log.info("proc idx = " + i);
				ExDataDto exData = expectDataList.get(i);
				
				//예상패턴 일치개수 설정
				int ptrnMatchCnt = lottoDataService.getExptPtrnMatchCnt(exData, winDataList, exptPtrnAnlyInfo, lowHighEndNumData, numbersRangeList, groupByNumbersList, mcNumberMap);
				exData.setPtrn_match_cnt(ptrnMatchCnt);
				
				expectDataPartList.add(exData);
				
				if ( (i+1) % INSERT_COUNT == 0) {
					log.info("부분 등록건수 = " + expectDataPartList.size());
					
					long partStartTime = System.currentTimeMillis();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", expectDataPartList);
					baseDao.insert("sysmngMapper.insertExptNumList", map);
					
					long partEndTime = System.currentTimeMillis();
					long partResutTime = partStartTime - partEndTime;
					
					log.info("부분처리 " + INSERT_COUNT + "건 소요시간  : " + partResutTime/1000 + "(ms)");
					
					// 등록처리 후 list 초기화
					expectDataPartList = new ArrayList<ExDataDto>();
				}
			}
			
			// 
			if (expectDataPartList.size() > 0) {
				log.info("잔여 예상번호 등록 처리");
				log.info("부분 등록건수 = " + expectDataPartList.size());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", expectDataPartList);
				baseDao.insert("sysmngMapper.insertExptNumList", map);
			}
			
				
			long endTime = System.currentTimeMillis();
			long resutTime = endTime - startTime;
			
			log.info("전체 " + expectDataList.size() + "건 소요시간  : " + resutTime/1000 + "(ms)");
		} // end if numberList check
	}
	
	/**
	 * 예상패턴이 적용된 예상번호 추출 및 등록
	 * 
	 * @param winDataList 전체데이터 (오름차순) 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 */
	public void insertExptNumApplyPattern(List<WinDataAnlyDto> winDataList, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		
		// 검증여부
		boolean verification = false;
		
		/*********************************************************
		 * 예상 회차합 번호추출
		 *********************************************************/
		//예상 회차합이 낮을 경우, 7~10으로 임의 설정
		int expectContainCnt = 0;
		if (exptPtrnAnlyInfo != null) {
			// 예상 회차합
			expectContainCnt = exptPtrnAnlyInfo.getCount_sum();
			if (expectContainCnt < 7) {
				//			expectContainCnt = 10;
				expectContainCnt = LottoUtil.getRandomContainCnt();
				log.warn("예상 회차합 (7미만) : " + exptPtrnAnlyInfo.getCount_sum());
			} else {
				//2017.06.12
				expectContainCnt = LottoUtil.getRandomContainCnt();
			}
		} else {
			expectContainCnt = LottoUtil.getRandomContainCnt();
		}
		
		//입력한 회차합 출현숫자
		List<Integer> numberOfInputCountList = lottoDataService.getNumbersOfInputCount(winDataList, expectContainCnt);		
		//입력한 회차합 미출현 숫자
		List<Integer> numberOfNotInputCountList = lottoDataService.getNumbersOfInputCount(numberOfInputCountList);	
		
		
		/*********************************************************
		 * 제외수 적용 번호추출
		 *********************************************************/
		//제외수를 저장할 list
		List<Integer> deleteTargetList = new ArrayList<Integer>();
		//제외수를 저장할 map
		Map<Integer, Integer> deleteTargetMap = new HashMap<Integer, Integer>();
		// 제외수 규칙 조회
		ExDataDto exDataDto = new ExDataDto();
		exDataDto.setEx_count(exptPtrnAnlyInfo.getEx_count());
		ExcludeDto excludeDto = this.getExcludeInfo(exDataDto);
		String[] excludeNum = excludeDto.getExclude_num().replaceAll(" ", "").split(",");
		for (int i = 0; i < excludeNum.length; i++) {
			deleteTargetList.add(Integer.parseInt(excludeNum[i]));
			deleteTargetMap.put(Integer.parseInt(excludeNum[i]), Integer.parseInt(excludeNum[i]));
		}
		
		//제외수를 포함하지 않는 회차합 숫자들
		List<Integer> numberOfContainList = lottoDataService.getListWithExcludedNumber(numberOfInputCountList, deleteTargetList);
		//제외수를 포함하지 않는 회차합이 아닌 숫자들
		List<Integer> numberOfNotContainList = lottoDataService.getListWithExcludedNumber(numberOfNotInputCountList, deleteTargetList);
		
		/** 번호간 범위 결과 목록 */
		ArrayList<HashMap<String, Integer>> numbersRangeList = lottoDataService.getNumbersRangeList(winDataList);
		
		/** 숫자별 출현번호 결과 목록 */
		ArrayList<ArrayList<Integer>> groupByNumbersList = lottoDataService.getGroupByNumbersList(winDataList);
		
		/** 번호별 궁합/불협수 */
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumberByAnly(winDataList);
		
		/*********************************************************
		 * 예상데이터 랜덤 조합 추출 및 등록
		 *********************************************************/
		boolean result = false;
		boolean isEqual = false;
		/** 예상데이터 */
		List<ExDataDto> expectDataList = new ArrayList<ExDataDto>();
		/** 조합수 */
		int cnt = 0;
		/** 일치개수 */
		int equalCnt = 0;
		/** 최대 일치개수 */
		int maxEqualCnt = 0;
		
		/** 추출된 조합숫자 목록 */
		List<int[]> numberList = lottoDataService.getRandomCombinationList(exptPtrnAnlyInfo, numberOfContainList, numberOfNotContainList, deleteTargetMap);
		/** 최대 일치개수 데이터 목록 */
		List<ExDataDto> maxEqualDataList = new ArrayList<ExDataDto>();
		/** 정렬된 데이터 목록 */
		List<ExDataDto> orderedDataList = new ArrayList<ExDataDto>();
		/** 정렬된 일치개수 목록 */
		ArrayList<Integer> orderedEqualCntList = new ArrayList<Integer>();
		
		// 표준 끝수합 범위 설정
		int[] lowHighEndNumData = lottoDataService.getEndNumberBaseDistribution(winDataList);
		/** 최저끝수 */
		int lowEndNumber = lowHighEndNumData[0];
		/** 최고끝수 */
		int highEndNumber = lowHighEndNumData[1];
		
		
		if (numberList != null && numberList.size() > 0) {
			log.info("추출된 조합숫자 목록 = " + numberList.size());
			//추출된 번호 전체를 패턴과 비교하여 예측패턴과 가장 많이 일치하는 번호 목록을 구한다.
			for (int i = 0; i < numberList.size(); i++) {
				//일치여부,개수 초기화
				isEqual = false;
				equalCnt = 0;
				
				ExDataDto data = new ExDataDto();
				
				//회차 설정
				data.setEx_count(exptPtrnAnlyInfo.getEx_count());
				//data 번호 설정
				int[] numbers = numberList.get(i);
				data.setNumbers(numbers);
				//data 번호간 차이값 설정
				data.setDifNumbers(lottoDataService.getDifNumbers(numbers));
				
				//1. 전회차 추출번호 예측 비교
				result = lottoDataService.existOfPrevCount(winDataList, data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("1. 전회차 추출번호 예측 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//2. 저고 비율 비교
				result = lottoDataService.existLowHighRatio(LottoUtil.getRatioTitle(), data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("2. 저고 비율 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//3. 홀짝 비율 비교
				result = lottoDataService.existOddEvenRatio(LottoUtil.getRatioTitle(), data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("3. 홀짝 비율 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//4. 총합 비교
				result = lottoDataService.existTotalRange(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("4. 총합 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//5. 연속되는 수 비교
				result = lottoDataService.existConsecutivelyNumbers(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("5. 연속되는 수 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//6. 끝수합 비교
				result = lottoDataService.existSumEndNumberRange(data, lowEndNumber, highEndNumber);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("6. 끝수합 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//7. 그룹 내 포함개수 비교
				result = lottoDataService.existGroup(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("7. 그룹 내 포함개수 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//8. 끝자리가 같은 수 비교
				result = lottoDataService.existEndNumberCount(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("8. 끝자리가 같은 수 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//9. 소수 1개이상 포함 비교
				result = lottoDataService.existSotsu(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("9. 소수 1개이상 포함 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//10. 3의 배수 포함 비교
				result = lottoDataService.existNumberOf3(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("10. 3의 배수 포함 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//11. 합성수 포함 비교 : 합성수란 소수와 3의 배수가 아닌 수
				result = lottoDataService.existNumberOfNot3(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("11. 합성수 포함 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//12. AC 비교(7 ~ 10)
				result = lottoDataService.isContainAc(data, exptPtrnAnlyInfo);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("12. AC 비교 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				//13. 궁합도 매치
				//2016.02.19
				result = lottoDataService.isMcMatched(data, mcNumberMap);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("13. 궁합도 매치 : " + equalCnt);
					}
				} else {
					if(!result) equalCnt++;	//일치함.
				}
				
				
				/*********************************************************
				 * 진행도 출력
				 *********************************************************/
				double d_cnt = i + 1;
				double d_total = numberList.size();
				DecimalFormat df = new DecimalFormat("#.##"); 
				double percent = Double.parseDouble(df.format( d_cnt/d_total ));
				if (!verification) {
					//검증이 아닐경우 진행도 출력
					log.info("index : " + (i+1) + " / " + numberList.size() + " [ equalCnt : " + equalCnt + " ] --- 진행률 : " + (percent*100) + "%");
				}
				
				//2016.08.05
				//제외수가 포함되어 있어서 한번 더 비교하여 있으면  skip 함
				boolean isExists = false; 
				for (int j = 0; j < numbers.length; j++) {
					if (deleteTargetMap.containsKey(numbers[j])) {
						isExists = true;
						break;
					}
				}
				if (isExists) {
					log.info("13-1. 제외수가 포함된 예상번호임. SKIP.(" + Arrays.toString(numbers)+ ")");
					continue;
				}
				
				/*
				 * 14. 번호간 차이값 체크
				 * 번호간 차이값이 평균범위 포함되어 있는지 체크한다.
				 */
				result = lottoDataService.isContainRange(data, numbersRangeList);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("14. 번호간 차이값 체크 : OK - " + equalCnt);
					}
				} else {
					if(!result) continue;
				}
				
				/*
				 * 15. 숫자별 출현번호 체크
				 * 숫자별 평균출현번호인지 체크한다.
				 */
				result = lottoDataService.isContainGroup(data, groupByNumbersList);
				if (verification && isEqual) {
					if(!result) {
						equalCnt++;	//일치함.
						log.info("15. 숫자별 출현번호 체크 : OK - " + equalCnt);
					}
				} else {
					if(!result) continue;
				}
				
				// 일치개수가 최대인 목록만 저장한다.
				// 최대 일치개수가 변경되면 최대 일치개수 데이터 목록을 초기화한다.
				if (maxEqualCnt < equalCnt) {
					maxEqualCnt = equalCnt;
					maxEqualDataList = new ArrayList<ExDataDto>();
					maxEqualDataList.add(data);
				} else {
					// 일치개수가 최대 일치개수보다 작거나 같을 경우
					
					// 최대 일치개수 데이터 목록이 10,000개 이하일 경우,
					// 최대 일치개수보다 1 작은 개수까지 저장한다.
					if (maxEqualDataList.size() <= 10000) {
						if (equalCnt < (maxEqualCnt - 1)) {
							// 일치개수가 허용 일치개수보다 작으면 다음 데이터를 설정한다.
							continue;
						} else if (equalCnt == maxEqualCnt) {
							maxEqualDataList.add(data);
						}
					} else {
						if (equalCnt < maxEqualCnt) {
							// 일치개수가 허용 일치개수보다 작으면 다음 데이터를 설정한다.
							continue;
						} else if (equalCnt == maxEqualCnt) {
							maxEqualDataList.add(data);
						}
					}
				}
				
				// 목록에 저장
				if (orderedEqualCntList.size() == 0) {
					orderedEqualCntList.add(equalCnt);
					orderedDataList.add(data);
				} else {
					lottoDataService.insertData(orderedEqualCntList, equalCnt, orderedDataList, data);
				}
			} // end for 추출 번호 중 일치하는 번호 목록 구하기
		} // end if numberList check
	}
	
	/**
	 * 총합 출현건수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getTotalGroupSumCnt(Map map) {
		return (int) baseDao.getSingleRow("sysmngMapper.getTotalGroupSumCnt", map);
	}
	
	/**
	 * 총합 출현건수 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<CaseInsensitiveMap> getTotalGroupCntList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getTotalGroupCntList", map);
	}
	
	/**
	 * 끝수합 출현건수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getEndnumGroupSumCnt(Map map) {
		return (int) baseDao.getSingleRow("sysmngMapper.getEndnumGroupSumCnt", map);
	}
	
	/**
	 * 끝수합 출현건수 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<CaseInsensitiveMap> getEndnumGroupCntList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getEndnumGroupCntList", map);
	}
	
	
	
	
	/**
	 * 권한코드 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<CaseInsensitiveMap> getAuthCodeList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getAuthCodeList", map);
	}
	
	/**
	 * 권한코드 목록 개수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthCodeListCnt(Map map) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getAuthCodeListCnt", map);
	}
	
	/**
	 *업무권한정보 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<TaskInfoDto> getAuthTaskInfoList(Map map) {
		return (ArrayList<TaskInfoDto>) baseDao.getList("sysmngMapper.getAuthTaskInfoList", map);
	}
	
	/**
	 * 권한코드 중복체크
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean dupCheckAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.getSingleRow("sysmngMapper.dupCheckAuthCode", map);
		if(i > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 권한코드 등록
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean createAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.createAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 권한코드 수정
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean modifyAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.update("sysmngMapper.modifyAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 권한코드 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 기존 업무권한 매핑정보 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteTaskAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteTaskAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 업무권한 매핑정보 저장
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveTaskAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.saveTaskAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * Tree Data 설정 (업무권한)
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx 
	 * @param depth 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray getAuthTaskInfoTree(List list, int startIdx, int endIdx, int depth) {
		JSONArray jsonRtnArr = new JSONArray();
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			TaskInfoDto one = (TaskInfoDto) list.get(i);
			
//			String task_1_cd = (String) one.get("task_1_cd");
//			String task_2_cd = (String) one.get("task_2_cd");
//			String task_1_nm = (String) one.get("task_1_nm");
//			String task_2_nm = (String) one.get("task_2_nm");
//			String checked = (String) one.get("checked");
//			int cnt = (int) one.get("cnt");
			
			String task_1_cd = (String) one.getTask_1_cd();
			String task_2_cd = (String) one.getTask_2_cd();
			String task_1_nm = (String) one.getTask_1_nm();
			String task_2_nm = (String) one.getTask_2_nm();
			String checked = (String) one.getChecked();
			int cnt = one.getCnt();
			
//			log.info("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
			//depth JSON 메뉴 생성
			JSONObject depthJson = new JSONObject();
			
			if (1 == depth) {
				depthJson.put("id", task_1_cd);
				depthJson.put("text", task_1_nm);
				depthJson.put("value", task_1_cd);
				
				if (cnt > 1) {
					depthJson.put("hasChildren", true);
					depthJson.put("ChildNodes", this.getAuthTaskInfoTree(list, i, i + cnt, 2));
					int checkCnt = this.getAuthTaskInfoCheckCnt(list, i, i + cnt);
					depthJson.put("checkstate", ((checkCnt == cnt) ? 1 : 0));
					i += (cnt - 1);
				} else {
					depthJson.put("hasChildren", false);
					depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
				}
			} else {
				depthJson.put("id", task_1_cd+"_"+task_2_cd);
				depthJson.put("text", task_2_nm);
				depthJson.put("value", task_1_cd+"_"+task_2_cd);
				
				depthJson.put("hasChildren", false);
				depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
			}
			
			depthJson.put("showcheck", true);
			depthJson.put("complete", true);
			depthJson.put("isexpand", true);
			
			jsonRtnArr.add(depthJson);
			
//			log.info("i="+i);
		}
		
		return jsonRtnArr;
	}

	/**
	 * 업무권한 체크 건수 가져오기
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthTaskInfoCheckCnt(List list, int startIdx, int endIdx) {
		int checkCnt = 0;
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			TaskInfoDto one = (TaskInfoDto) list.get(i);
			
			String checked = (String) one.getChecked();
			if ("Y".equals(checked)) {
				checkCnt++;
			}
		}
		
		return checkCnt;
	}
	
	/**
	 * 메뉴권한정보 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<MenuInfoDto> getAuthMenuInfoList(Map map) {
		return (ArrayList<MenuInfoDto>) baseDao.getList("sysmngMapper.getAuthMenuInfoList", map);
	}
	
	/**
	 * Tree Data 설정 (메뉴권한)
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx 
	 * @param depth 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray getAuthMenuInfoTree(List list, int startIdx, int endIdx, int depth) {
		JSONArray jsonRtnArr = new JSONArray();
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			MenuInfoDto one = (MenuInfoDto) list.get(i);
			
			log.info(i + " data = " + one.toString());
			
			//java.lang.ClassCastException: java.lang.Short cannot be cast to java.lang.Integer			
//			int menu_id = Integer.parseInt(String.valueOf(one.get("menu_id")));
//			int p_menu_id = Integer.parseInt(String.valueOf(one.get("p_menu_id")));
//			String menu_nm = (String) one.get("menu_nm");
//			String checked = (String) one.get("checked");
//			int cnt = (int) one.get("cnt");
			
			int menu_id = one.getMenu_id();
			int p_menu_id = one.getP_menu_id();
			String menu_nm = one.getMenu_nm();
			String checked = one.getChecked();
			int cnt = one.getCnt();
			
//			log.info("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
			//depth JSON 메뉴 생성
			JSONObject depthJson = new JSONObject();
			
			if (1 == depth) {
				depthJson.put("id", String.valueOf(menu_id));
				depthJson.put("text", menu_nm);
				depthJson.put("value", String.valueOf(menu_id));
				
				if (0 == p_menu_id) {
					depthJson.put("hasChildren", true);
					depthJson.put("ChildNodes", this.getAuthMenuInfoTree(list, (i+1), (i+1) + cnt, 2));
					int checkCnt = this.getAuthMenuInfoCheckCnt(list, (i+1), (i+1) + cnt);
					depthJson.put("checkstate", ((checkCnt == cnt) ? 1 : 0));
					i += cnt;
				} else {
					depthJson.put("hasChildren", false);
					depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
				}
			} else {
				depthJson.put("id", String.valueOf(menu_id));
				depthJson.put("text", menu_nm);
				depthJson.put("value", String.valueOf(menu_id));
				
				depthJson.put("hasChildren", false);
				depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
			}
			
			depthJson.put("showcheck", true);
			depthJson.put("complete", true);
			depthJson.put("isexpand", true);
			
			jsonRtnArr.add(depthJson);
			
//			log.info("i="+i);
		}
		
		return jsonRtnArr;
	}
	
	/**
	 * 메뉴권한 체크 건수 가져오기
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthMenuInfoCheckCnt(List list, int startIdx, int endIdx) {
		int checkCnt = 0;
		
		for (int i = startIdx; i < endIdx; i++) {
			MenuInfoDto one = (MenuInfoDto) list.get(i);
			
			String checked = one.getChecked();
			if ("Y".equals(checked)) {
				checkCnt++;
			}
		}
		
		return checkCnt;
	}

	/**
	 * 기존 메뉴권한 매핑정보 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteMenuAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteMenuAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 메뉴권한 매핑정보 저장
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveMenuAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.saveMenuAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}

	

}
