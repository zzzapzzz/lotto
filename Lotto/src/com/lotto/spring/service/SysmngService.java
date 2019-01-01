package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
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

	/**
	 * 사용자 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<CaseInsensitiveMap> getUserList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getUserList", map);
	}
	
	public ArrayList<UserInfoDto> getUserList2(Map map) {
		return (ArrayList<UserInfoDto>) baseDao.getList("sysmngMapper.getUserList2", map);
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
		return (ArrayList<WinDataAnlyDto>) baseDao.getList("sysmngMapper.getWinDataAnlyList", dto);
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
		
		CountSumDto dto = this.getLastContainCnt(winDataList);
		
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
	 * <div id=description><b>회차별 회차합 정보 구하기</b></div ><br>
     * <div id=detail>회차별 회차합 정보를 추출하고, 포함/미포함 번호의 개수를 설정한다.</div ><br>
     * 
	 * @param winDataList 전체데이터
	 * @return
	 */
	private CountSumDto getLastContainCnt(List<WinDataDto> winDataList) {
		CountSumDto dto = new CountSumDto();
		
		int lastWinCountIdx = winDataList.size()-1;	// 마지막 회차의 list index
		
		WinDataDto sourceData = winDataList.get(lastWinCountIdx);	//해당 회차정보
		// 회차번호 설정
		int win_count = sourceData.getWin_count();
		dto.setWin_count(win_count);
		
		int count_sum = 0;	//회차합
		int lastContainCnt = 0;	//10회차 내 마지막 포함숫자가 있는 회차합
		int[] sourceNumbers = LottoUtil.getNumbers(sourceData);
		int cont_cnt = 0;	//포함개수
		int not_cont_cnt = sourceNumbers.length - cont_cnt;	//미포함개수(6 - 포함개수)
		
		String cont_num = "";	// 포함숫자 목록
		String not_cont_num = "";	// 미포함숫자 목록
		
		HashMap<Integer, Integer> containMap = new HashMap<Integer, Integer>();
		
		// 이전 회차에서 10회차까지 포함정보를 구한다.
		int beforeLastWinCountIdx = lastWinCountIdx - 1;	// 이전 회차 list index
		for (int targetIdx = beforeLastWinCountIdx; targetIdx > beforeLastWinCountIdx - 10; targetIdx--) {
			//회차합 증가
			count_sum++;
			
			WinDataDto targetData = winDataList.get(targetIdx);	//과거 회차정보
			int[] targetNumbers = LottoUtil.getNumbers(targetData);
			
			// 해당회차의 번호와 대상회차의 번호를 비교
			for (int i = 0; i < sourceNumbers.length; i++) {
				if(containMap.containsKey(sourceNumbers[i])){
					continue;
				}
				
				for (int number : targetNumbers) {
					if(sourceNumbers[i] == number){
						containMap.put(sourceNumbers[i], number);
						cont_cnt++;
						not_cont_cnt = sourceNumbers.length - cont_cnt; 
						lastContainCnt = count_sum;
						break;
					}
				}
			}//source for
			
			
			//과거 10회차 이내 포함개수가 6개이면 반복 종료
			if(cont_cnt == 6){
				break;
			}
		}
		
		for (int number = 1; number <= 45; number++) {
			if (containMap.containsKey(number)) {
				// 포함숫자 설정
				if (!"".equals(cont_num)) {
					cont_num = cont_num + ",";
				}
				cont_num = cont_num + number;
			} else {
				// 미포함숫자 설정
				if (!"".equals(not_cont_num)) {
					not_cont_num = not_cont_num + ",";
				}
				not_cont_num = not_cont_num + number;
			}
		}
		
		//회차합 정보 설정
		dto.setCount_sum(lastContainCnt);
		dto.setCont_cnt(cont_cnt);
		dto.setNot_cont_cnt(not_cont_cnt);
		dto.setCont_num(cont_num);
		dto.setNot_cont_num(not_cont_num);
		
		log.info("### 회차합 설정정보 ###");
		log.info("wincount = " + dto.getWin_count());
		log.info("lastContainCnt = " + dto.getCount_sum());
		log.info("cont_cnt = " + dto.getCont_cnt());
		log.info("not_cont_cnt = " + dto.getNot_cont_cnt());
		log.info("cont_num = " + dto.getCont_num());
		log.info("not_cont_num = " + dto.getNot_cont_num());
		
		return dto;
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
		List<ArrayList<Integer>> getNotContainNumbers = this.getNotContainNumbers(winDataList);
		int[] rule = {0,0,0,0,0,0};
    	String excludeNum = "";	//제외수    	
    	if (getNotContainNumbers != null && getNotContainNumbers.size() > 0 ) {
    		log.info("\t>> 개수 : " + getNotContainNumbers.size());
    		rule = this.getNotContainNumbersRule(getNotContainNumbers);
    		excludeNum = this.getNotContainNumbersRuleMsg(rule, lastData);
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
	 * 제외수 규칙 분석
	 * 
	 * @param winDataList
	 * @return
	 */
	private List<ArrayList<Integer>> getNotContainNumbers(List<WinDataDto> winDataList) {
		List<ArrayList<Integer>> allRuleList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> dataList;
		
		//제외수 규칙 설정
		for(int index = 0 ; index < 6 ; index++){
			
			dataList = new ArrayList<Integer>();
			
			int minNum = 0;	//최소번호
			int maxNum = 0;	//최대번호
			
			int disMin = 0;	//최소차			
			int disMax = 0;	//최대차
			
			//각 번호순별 최소/최대번호 설정
			for(WinDataDto data : winDataList){
				int num = LottoUtil.getNumbers(data)[index];			
				
				if(minNum == 0 || maxNum == 0){
					//초기값 설정
					minNum = num;
					maxNum = num;
				}else if(num < minNum){
					//숫자가 최소번호보다 작다면 최소번호로 변경
					minNum = num;
				}else if(num > maxNum){
					//숫자가 최소번호보다 작지 않다면 최대번호로 변경
					maxNum = num;
				}				
			}
			
			//차이값 구하기
			disMin = 1 - minNum;
			disMax = 45 - maxNum;
			
			//첫번째 숫자 제외수 - Mod
			if(index == 0){
				setNotContainModNumbers(dataList, winDataList, minNum);
			}else{
				int cnt1 = 0;
				int cnt2 = 0;
				
				//전회차순서별숫자 + 최소차 값이 다음차수의 번호중에 일치하는 번호가 있는지 여부확인
				if(disMin != 0){	//첫번째 번호일 경우 가장 작은 번호가 1이라면 최소차는 0이 될 수 있음.
					cnt1 = setNotContainDataNumbers(dataList, winDataList, index, disMin, 0);
				}
				
				if(disMax != 0){	//여섯번째 번호일 경우 가장 큰 번호가 45이라면 최대차는 0이 될 수 있음.
					cnt2 = setNotContainDataNumbers(dataList, winDataList, index, 1, disMax+1);
				}
				
				if(dataList.size() > 1){
					if(cnt1 > cnt2){
						dataList.remove(1);
					}else{
						dataList.remove(0);
					}
				}
			}
			
			allRuleList.add(dataList);
			
		}//end for
		
		
		return allRuleList;
	}
	
	/**
	 * @description <div id=description><b>첫번째 숫자 제외수 - Mod</b></div >
     *              <div id=detail>첫번째 숫자를 45로 나눈 나머지를 더하여 중복되는 수가 있는지 찾는다.</div >
     * @param dataList
	 * @param winDataList
	 * @param minNum 
	 */
	private void setNotContainModNumbers(ArrayList<Integer> dataList, List<WinDataDto> winDataList, int minNum){
		boolean isExist = false;
		int maxCnt = 0;
		int maxMod = 0;
		
		for(int mod = 1 ; mod < 45 ; mod++){
			isExist = false;
			int cnt = 0;
			
			for(int dataIndex = 0 ; dataIndex < winDataList.size()-1 ; dataIndex++){
				boolean result = false;
				
				int sourceNumber = LottoUtil.getNumbers(winDataList.get(dataIndex))[0];
				
				if(sourceNumber + mod > 45 ){
//					System.out.println(dataIndex + " : " + sourceNumber + " + " + mod + " : " + (sourceNumber + mod));
					continue;
				}
				
				int[] targetNum = LottoUtil.getNumbers(winDataList.get(dataIndex+1));
				
				//다음차수의 번호들을 비교한다.
				for(int targetIndex = 0 ; targetIndex < 6 ; targetIndex++){
					if(sourceNumber + mod == targetNum[targetIndex]){
						result = true;
					}
				}
				
				if(!result){	//다음회차에 포함되는 수가 없다면 cnt 1 증가
					cnt++;
				}
				
				if(cnt == winDataList.size()-1){
					isExist = true;
				}
				
				if(dataIndex == winDataList.size() -2){
//					System.out.println("************** mod : " + mod + " / cnt : " + cnt);
					if(cnt > maxCnt){
						maxCnt = cnt;
						maxMod = mod;
					}
				}
			}
			
			//sourceNumber + mod 가 한번도 다음회차에 나오지 않았다면
			if(isExist){
				System.out.println(mod);
			}
			
		}
		
//		System.out.println("*** End ***");
//		System.out.println("maxCnt : " + maxCnt + ", maxMod : " + maxMod);
		
		dataList.add(maxMod);
		
	}
	
	/**
	 * @description <div id=description><b>제외수 설정</b></div >
     *              <div id=detail>첫 번째 숫자를 제외한 나머지 숫자들의 규칙을 찾는다.</div >
     * @param dataList 제외수를 저장할 list
	 * @param list 데이터를 가지고 있는 list
	 * @param index 현재 구하는 순번위치
	 * @param fromNum 초기 비교규칙
	 * @param toNum 마지막 비교규칙
	 */
	private int setNotContainDataNumbers(ArrayList<Integer> dataList, List<WinDataDto> list, int index, int fromNum, int toNum){
		boolean isExist = false;	//제외수 규칙에 의해 포함된 숫자가 있는지에 대한 여부
		int maxCnt = 0;
		int maxIdx = 0;
		
		for(int idx = fromNum ; idx < toNum ; idx++){
			isExist = false;
			int cnt = 0;
			
			for(int dataIndex = 0 ; dataIndex < list.size()-1 ; dataIndex++){
				boolean result = false;
				
				int sourceNumber = LottoUtil.getNumbers(list.get(dataIndex))[index];
				
				int[] targetNum = LottoUtil.getNumbers(list.get(dataIndex+1));
				
				//다음차수의 번호들을 비교한다.
				for(int targetIndex = 0 ; targetIndex < 6 ; targetIndex++){
					if((sourceNumber + idx) == targetNum[targetIndex]){
						result = true;
					}
				}
				
				if(!result){	//다음회차에 포함되는 수가 없다면 cnt 1 증가
					cnt++;
				}
				
				if(cnt == list.size()-1){
					isExist = true;
				}
				
				if(dataIndex == list.size() -2){
//					System.out.println("************** mod : " + idx + " / cnt : " + cnt);
					if(cnt > maxCnt){
						maxCnt = cnt;
						maxIdx = idx;
					}
				}
			}
			
			if(isExist){
				System.out.println(idx);
			}
		}
		
//		System.out.println("*** End ***");
//		System.out.println("maxCnt : " + maxCnt + ", maxIdx : " + maxIdx);
		
		dataList.add(maxIdx);
		
		return maxCnt;
	}

	/**
	 * 제외수 규칙 배열 가져오기
	 * 
	 * @param getNotContainNumbers
	 * @return
	 */
	public int[] getNotContainNumbersRule(List<ArrayList<Integer>> getNotContainNumbers) {
		int[] rule = {0,0,0,0,0,0};
		
		for (int i = 0; i < getNotContainNumbers.size(); i++) {
    		ArrayList<Integer> arrList = getNotContainNumbers.get(i);
    		if (arrList != null && arrList.size() > 0) {
    			rule[i] = arrList.get(0);
    		}
		}
		
		return rule;
	}
	
	/**
	 * 제외수 규칙 문자열 가져오기
	 * 
	 * @param rule
	 * @return
	 */
	public String getNotContainNumbersRuleMsg(int[] rule, WinDataDto lastData) {
		String excludeNum = "";
		
		int[] lastDataNumber = LottoUtil.getNumbers(lastData);
		Map<Integer, Integer> deleteTargetMap = new HashMap<Integer, Integer>();
		
		for(int i = 0 ; i < lastDataNumber.length ; i++){
			int printNum = 0;
			printNum = lastDataNumber[i] + rule[i];
			
			if(deleteTargetMap.containsKey(printNum))
				continue;
			
			deleteTargetMap.put(printNum, printNum);
			excludeNum += (i==0?"":", ") + printNum;
		}
		
		return excludeNum;
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
		int[] arrTotalRange = this.getLowTotalHighTotal(winDataList);
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
	 * @description <div id=description><b>총합 범위 구하기</b></div >
	 *              <div id=detail>전체 총합분포 중 상위 10%와 하위 10%를 제외한 최저 총합과 최고 총합의 범위를 구한다.</div >
	 * @param winDataList 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int[] getLowTotalHighTotal(List<WinDataDto> winDataList){
		int[] data = {0,0};
		int totalCnt = winDataList.size();	//전체횟수
		List<Integer> totalList = new ArrayList<Integer>();	//총합의 종류를 저장한다.
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();	//총합의 종류를 판별한다.
		
		//총합리스트 설정
		for(WinDataDto winData : winDataList){
			int total = winData.getTotal();
			if(!map.containsKey(total)){
				totalList.add(total);
				map.put(total, total);
			}
		}
		
//		System.out.println("totalList : " + totalList.size());
		//총합리스트 정렬
		totalList = (List<Integer>)LottoUtil.dataSort(totalList);
		
		
		//총합리스트 횟수에 대한 초기값 설정
		int[] cntData = new int[totalList.size()];
		for(int index = 0 ; index < cntData.length ; index++){
			cntData[index] = 0;
		}		
		
		
		//총합별 포함횟수 설정
		for(WinDataDto winData : winDataList){
			int total = winData.getTotal();
			
			for(int index = 0 ; index < totalList.size() ; index++){
				if(totalList.get(index) == total){
					cntData[index] += 1;
					break;
				}
			}
		
		}
		
		//최저총합 구하기
		int cnt = 0;
		for(int index = 0 ; index < cntData.length ; index++){
			cnt += cntData[index];
			
			if(LottoUtil.getRatio(cnt, totalCnt) > 10.0){
				data[0] = totalList.get(index);
				break;
			}
		}
		
		//최고총합 구하기
		cnt = 0;
		for(int index = cntData.length-1 ; index > 0 ; index--){
			cnt += cntData[index];
			
			if(LottoUtil.getRatio(cnt, totalCnt) > 10.0){
				data[1] = totalList.get(index);
				break;
			}
		}
		
		return data;
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
		
		int[] arrEndNumRange = this.getLowEndNumHighEndNum(winDataList);
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
	 * @description <div id=description><b>끝수합 범위 구하기</b></div >
	 *              <div id=detail>전체 끝수합의 범위를 구한다.</div >
	 * @param winDataList
	 * @return
	 */
	public int[] getLowEndNumHighEndNum(List<WinDataDto> winDataList){
		int[] data = {0,0};
		
		//끝수합 설정
		for(WinDataDto winData : winDataList){
			int[] numbers = LottoUtil.getNumbers(winData);
			int total = 0;
			
			for(int index = 0 ; index < numbers.length ; index++){
				total += numbers[index] % 10;
			}
			
			if(data[0] == 0){	//초기값 설정
				data[0] = total;
				data[1] = total;
			}else{
				if(data[0] > total){	//최저 끝수합 설정
					data[0] = total;
				}
				
				if(data[1] < total){	//최고 끝수합 설정
					data[1] = total;
				}
			}
		}
		
		return data;
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
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = this.getMcNumber(winDataList);
    	
		Map mcInfoMap = this.getMCInfoMap(mcNumberMap, lastData);
		
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.insertMCNumInfo", mcInfoMap);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getMCInfoMap(Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap, WinDataDto lastData) {
		Map mcInfoMap = new HashMap();
    	List mcInfoList = new ArrayList();
    	for (int num = 1; num <= 45; num++) {
    		Map dataMap = new HashMap();	//등록할 객체
    		
			Map<String, ArrayList<Integer>> mcMap = mcNumberMap.get(num);
			ArrayList<Integer> mcList = mcMap.get("mc");
			ArrayList<Integer> notMcList = mcMap.get("notMc");
			String mcInfo = "";
			String notMcInfo = "";
			
			//궁합수 출력
			for (int i = 0; i < mcList.size(); i++) {
				if (!"".equals(mcInfo)) {
					mcInfo += ",";
				}
				mcInfo += mcList.get(i);
			}
			
			//불협수 출력
			for (int i = 0; i < notMcList.size(); i++) {
				if (!"".equals(notMcInfo)) {
					notMcInfo += ",";
				}
				notMcInfo += notMcList.get(i);
			}
			
			//객체 설정
			dataMap.put("win_count", lastData.getWin_count());
			dataMap.put("num", num);
			dataMap.put("mc_num", mcInfo);
			dataMap.put("not_mc_num", notMcInfo);
			
			mcInfoList.add(dataMap);
		}
    	mcInfoMap.put("mcInfoList", mcInfoList);
		return mcInfoMap;
	}

	/**
	 * @description <div id=description><b>궁합/불협수 설정</b></div >
	 *              <div id=detail>각 숫자별로 가장 많이 나온 3수를 궁합수, 가장 나오지 않은 수를 불협수로 설정한다.</div >
	 * @param winDataList 전체리스트
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<Integer, Map<String, ArrayList<Integer>>> getMcNumber(List<WinDataDto> winDataList) {
		/** 번호별 전체 저장 변수 */
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = new HashMap<Integer, Map<String,ArrayList<Integer>>>();
		/** 해당 번호의 궁합/불협수 목록 저장 변수 */
		Map<String, ArrayList<Integer>> mcMap;
		
		/*
		 * 1 ~ 45까지의 숫자를 전체데이터로부터 함께 나온 수와 나오지 않는 수를 찾는다.
		 */
		for (int number = 1; number <= 45; number++) {
			mcMap = new HashMap<String, ArrayList<Integer>>();
			
			/** 함께 당첨번호로 출현한 번호별 개수 저장 배열 */
			int[] existCnt = new int[45];
			for (int i = 0; i < existCnt.length; i++) {
				existCnt[i] = 0;
			}
			
			/** 가장 많은 개수를 저장할 배열 */
			int[] bestMcCnt = new int[3];
			for (int i = 0; i < bestMcCnt.length; i++) {
				bestMcCnt[i] = 0;
			}
			
			/** 가장 많은 개수에 해당하는 숫자를 저장할 배열 */
			int[] bestMcNumbers = new int[3];
			for (int i = 0; i < bestMcNumbers.length; i++) {
				bestMcNumbers[i] = 0;
			}
			
			
			//함께 당첨번호로 출현한 번호 저장 맵
			Map<Integer, Integer> existMap = new HashMap<Integer, Integer>();
			//궁합수 저장 맵
			Map<Integer, Integer> existMcMap = new HashMap<Integer, Integer>();
			//궁합수 저장 리스트(가장 좋은 3수)
			ArrayList<Integer> mcList = new ArrayList<Integer>();
			//불협수 저장 리스트 : 한번도 함께 나온적이 없는 번호
			ArrayList<Integer> notMcList = new ArrayList<Integer>();
			
			//전체 데이터 검색
			for (int i = 0; i < winDataList.size(); i++) {
				//번호 존재 여부
				boolean isExist = false;
				WinDataDto data = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(data);
				
				//번호 비교
				for (int j = 0; j < numbers.length; j++) {
					if(number == numbers[j]){
						isExist = true;
						break;
					}
				}
				
				//번호가 없으면 다음회차 검색
				if(!isExist) 
					continue;
				
				//번호가 있으면 함께 나온 숫자 저장
				for (int j = 0; j < numbers.length; j++) {
					if(number == numbers[j]){
						//같은 번호는 skip
						continue;
					}else{
						//번호가 이미 저장된 번호인지 확인 
						if(existMap.containsKey(numbers[j])){
							//있으면 개수만 추가한다.
							existCnt[numbers[j]-1] += 1;							
						}else{
							//없으면 등록하고 개수를 추가한다.
							existMap.put(numbers[j], numbers[j]);
							existCnt[numbers[j]-1] += 1; 
						}
					}
				}//숫자 저장 for
			}//전체 데이터 for
			
			//궁합수 저장
			//가장 많이 나온 숫자 3개 저장
			for (int num = 0; num < existCnt.length; num++) {
				int cnt = existCnt[num];
				
				//숫자별 개수 체크
//				if(count == 1)
//					System.out.println( (num+1) + " : " + cnt);
				
				if(cnt >= bestMcCnt[0]){	//첫 번째 큰수
					//개수 설정
					bestMcCnt[2] = bestMcCnt[1];
					bestMcCnt[1] = bestMcCnt[0];
					bestMcCnt[0] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = bestMcNumbers[1];
					bestMcNumbers[1] = bestMcNumbers[0];
					bestMcNumbers[0] = num+1;
					
				}else if(cnt >= bestMcCnt[1]){	//두 번째 큰수
					//개수 설정
					bestMcCnt[2] = bestMcCnt[1];
					bestMcCnt[1] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = bestMcNumbers[1];
					bestMcNumbers[1] = num+1;
				}else if(cnt >= bestMcCnt[2]){	//세 번째 큰수
					//개수 설정
					bestMcCnt[2] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = num+1;
				}
			}
			
			//가장 많이 나온 상위 3가지 개수에 해당하는 번호를 모두 저장한다.
			for(int i = 0; i < bestMcCnt.length; i++) {
				for (int num = 0; num < existCnt.length; num++) {
					if(bestMcCnt[i] == existCnt[num]){
						//저장되어 있지 않은 궁합수는 궁합수 목록에 추가한다.
						if(!existMcMap.containsKey(num+1)){
							mcList.add(num+1);
							existMcMap.put(num+1, num+1);
						}
					}
				}
				
			}
			
			//불협수 저장
			for(int i = 1; i <= 45; i++) {
				if(!existMap.containsKey(i) 
						&& number != i){
					notMcList.add(i);
				}
			}
			
			//저장
			mcMap.put("mc", (ArrayList<Integer>)LottoUtil.dataSort(mcList));
			mcMap.put("notMc", (ArrayList<Integer>)LottoUtil.dataSort(notMcList));
			mcNumberMap.put(number, mcMap);
			
		}//숫자별 for
		
		return mcNumberMap;
	}

	/**
	 * @description <div id=description><b>궁합/불협수 설정</b></div >
	 *              <div id=detail>각 숫자별로 가장 많이 나온 3수를 궁합수, 가장 나오지 않은 수를 불협수로 설정한다.</div >
	 * @param winDataList 전체리스트
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<Integer, Map<String, ArrayList<Integer>>> getMcNumberByAnly(List<WinDataAnlyDto> winDataList) {
		/** 번호별 전체 저장 변수 */
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = new HashMap<Integer, Map<String,ArrayList<Integer>>>();
		/** 해당 번호의 궁합/불협수 목록 저장 변수 */
		Map<String, ArrayList<Integer>> mcMap;
		
		/*
		 * 1 ~ 45까지의 숫자를 전체데이터로부터 함께 나온 수와 나오지 않는 수를 찾는다.
		 */
		for (int number = 1; number <= 45; number++) {
			mcMap = new HashMap<String, ArrayList<Integer>>();
			
			/** 함께 당첨번호로 출현한 번호별 개수 저장 배열 */
			int[] existCnt = new int[45];
			for (int i = 0; i < existCnt.length; i++) {
				existCnt[i] = 0;
			}
			
			/** 가장 많은 개수를 저장할 배열 */
			int[] bestMcCnt = new int[3];
			for (int i = 0; i < bestMcCnt.length; i++) {
				bestMcCnt[i] = 0;
			}
			
			/** 가장 많은 개수에 해당하는 숫자를 저장할 배열 */
			int[] bestMcNumbers = new int[3];
			for (int i = 0; i < bestMcNumbers.length; i++) {
				bestMcNumbers[i] = 0;
			}
			
			
			//함께 당첨번호로 출현한 번호 저장 맵
			Map<Integer, Integer> existMap = new HashMap<Integer, Integer>();
			//궁합수 저장 맵
			Map<Integer, Integer> existMcMap = new HashMap<Integer, Integer>();
			//궁합수 저장 리스트(가장 좋은 3수)
			ArrayList<Integer> mcList = new ArrayList<Integer>();
			//불협수 저장 리스트 : 한번도 함께 나온적이 없는 번호
			ArrayList<Integer> notMcList = new ArrayList<Integer>();
			
			//전체 데이터 검색
			for (int i = 0; i < winDataList.size(); i++) {
				//번호 존재 여부
				boolean isExist = false;
				WinDataAnlyDto data = winDataList.get(i);
				int[] numbers = LottoUtil.getNumbers(data);
				
				//번호 비교
				for (int j = 0; j < numbers.length; j++) {
					if(number == numbers[j]){
						isExist = true;
						break;
					}
				}
				
				//번호가 없으면 다음회차 검색
				if(!isExist) 
					continue;
				
				//번호가 있으면 함께 나온 숫자 저장
				for (int j = 0; j < numbers.length; j++) {
					if(number == numbers[j]){
						//같은 번호는 skip
						continue;
					}else{
						//번호가 이미 저장된 번호인지 확인 
						if(existMap.containsKey(numbers[j])){
							//있으면 개수만 추가한다.
							existCnt[numbers[j]-1] += 1;							
						}else{
							//없으면 등록하고 개수를 추가한다.
							existMap.put(numbers[j], numbers[j]);
							existCnt[numbers[j]-1] += 1; 
						}
					}
				}//숫자 저장 for
			}//전체 데이터 for
			
			//궁합수 저장
			//가장 많이 나온 숫자 3개 저장
			for (int num = 0; num < existCnt.length; num++) {
				int cnt = existCnt[num];
				
				//숫자별 개수 체크
//				if(count == 1)
//					System.out.println( (num+1) + " : " + cnt);
				
				if(cnt >= bestMcCnt[0]){	//첫 번째 큰수
					//개수 설정
					bestMcCnt[2] = bestMcCnt[1];
					bestMcCnt[1] = bestMcCnt[0];
					bestMcCnt[0] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = bestMcNumbers[1];
					bestMcNumbers[1] = bestMcNumbers[0];
					bestMcNumbers[0] = num+1;
					
				}else if(cnt >= bestMcCnt[1]){	//두 번째 큰수
					//개수 설정
					bestMcCnt[2] = bestMcCnt[1];
					bestMcCnt[1] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = bestMcNumbers[1];
					bestMcNumbers[1] = num+1;
				}else if(cnt >= bestMcCnt[2]){	//세 번째 큰수
					//개수 설정
					bestMcCnt[2] = cnt;
					
					//번호 설정
					bestMcNumbers[2] = num+1;
				}
			}
			
			//가장 많이 나온 상위 3가지 개수에 해당하는 번호를 모두 저장한다.
			for(int i = 0; i < bestMcCnt.length; i++) {
				for (int num = 0; num < existCnt.length; num++) {
					if(bestMcCnt[i] == existCnt[num]){
						//저장되어 있지 않은 궁합수는 궁합수 목록에 추가한다.
						if(!existMcMap.containsKey(num+1)){
							mcList.add(num+1);
							existMcMap.put(num+1, num+1);
						}
					}
				}
				
			}
			
			//불협수 저장
			for(int i = 1; i <= 45; i++) {
				if(!existMap.containsKey(i) 
						&& number != i){
					notMcList.add(i);
				}
			}
			
			//저장
			mcMap.put("mc", (ArrayList<Integer>)LottoUtil.dataSort(mcList));
			mcMap.put("notMc", (ArrayList<Integer>)LottoUtil.dataSort(notMcList));
			mcNumberMap.put(number, mcMap);
			
		}//숫자별 for
		
		return mcNumberMap;
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
    	String[] arrLowHigh = this.getLowHighRatio(winDataList, headTitle);
    	
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
	 * @description <div id=description><b>고저 비율 구하기</b></div >
     *              <div id=detail>고저 비율을 구한다.</div >
     * @param winDataList
	 * @param ratioTitle
	 * @return
	 */
	public String[] getLowHighRatio(List<WinDataDto> winDataList, String[] ratioTitle){
		String[] result = new String[ratioTitle.length];
		int[] count = new int[ratioTitle.length];
		
		//비율의 누적횟수 초기화
		for(int index = 0 ; index < count.length ; index++){
			count[index] = 0;
		}
		
		//해당비율 누적 구하기
		for(WinDataDto winData : winDataList){
			for(int index = 0 ; index < ratioTitle.length ; index++){
				if(winData.getLow_high().equals(ratioTitle[index])){
					count[index] += 1;
				}
			}
		}
		
		for(int index = 0 ; index < count.length ; index++){
			result[index] = ""+LottoUtil.getRatio(count[index], winDataList.size());
		}
		
		return result;
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
		String[] arrOddEven = this.getOddEvenRatio(winDataList, headTitle);
		
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
	 * @description <div id=description><b>홀짝 비율 구하기</b></div >
     *              <div id=detail>홀짝 비율을 구한다.</div >
     * @param winDataList
	 * @param ratioTitle
	 * @return
	 */
	public String[] getOddEvenRatio(List<WinDataDto> winDataList, String[] ratioTitle){
		String[] result = new String[ratioTitle.length];
		int[] count = new int[ratioTitle.length];
		
		//비율의 누적횟수 초기화
		for(int index = 0 ; index < count.length ; index++){
			count[index] = 0;
		}
		
		//해당비율 누적 구하기
		for(WinDataDto winData : winDataList){
			for(int index = 0 ; index < ratioTitle.length ; index++){
				if(winData.getOdd_even().equals(ratioTitle[index])){
					count[index] += 1;
				}
			}
		}
		
		for(int index = 0 ; index < count.length ; index++){
			result[index] = ""+LottoUtil.getRatio(count[index], winDataList.size());
		}
		
		return result;
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
		
		int[] containGroupCnt = this.getZeroCntRangeData(lastData);
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
	 * 미출현 번호대 구간 분석자료 가져오기
	 * 2018.12.15
	 * 
	 * @param lastData
	 * @return
	 */
	public int[] getZeroCntRangeData(WinDataDto lastData) {
		int[] numbers = LottoUtil.getNumbers(lastData);
		
		/** 각 자리의 포함개수 */
		int[] containGroupCnt = {0,0,0,0,0};
		
		//각 자리의 포함개수를 구한다.
		for(int index = 0 ; index < numbers.length ; index++){
			int mok = numbers[index]/10;
			/*
			 * 10의자리수는 작은 자리 수로 설정한다.
			 * 10 : 1의 자리
			 * 20 : 10의 자리
			 * 30 : 20의 자리
			 * 40 : 30의 자리
			 */
			if(mok > 0 && (numbers[index] % 10 == 0)){
				mok -= 1;
			}
			containGroupCnt[mok] = containGroupCnt[mok] + 1;
		}
		
		return containGroupCnt;
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
	 * @description <div id=description><b>10회차합 구하기</b></div >
     *              <div id=detail>지난 10회동안 출현했던 번호들을 구한다.</div >
     * @param winDataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getContain10List(List<WinDataDto> winDataList) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Integer> containList = new ArrayList<Integer>();
		
		for(int index = winDataList.size() - 10 ; index < winDataList.size() ; index++){
			int[] numbers = LottoUtil.getNumbers(winDataList.get(index));
			
			for(int number : numbers){
				if(!map.containsKey(number)){
					containList.add(number);
					map.put(number, number);
				}
			}
		}
		
		return (List<Integer>) LottoUtil.dataSort(containList);
	}
	
	/**
	 * @description <div id=description><b>10회차동안 출현하지 않은 번호 구하기</b></div >
     *              <div id=detail>10회차동안 출현하지 않은 번호들을 구한다.</div >
     * @param contain10List
	 * @return
	 */
	public List<Integer> getNotContain10List(List<Integer> contain10List){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Integer> notContainList = new ArrayList<Integer>();
		
		for(int number : contain10List){
			map.put(number, number);
		}
		
		for(int number = 1 ; number <= 45 ; number++){
			if(!map.containsKey(number)){
				notContainList.add(number);
			}
		}
		
		return notContainList;
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
	 * 궁합수 매칭정보 확인
	 * 
	 * @param winData 당첨번호 
	 * @param mcNumList 궁합수 목록
	 * @return 궁합수 매칭정보
	 */
	public String getMcMatchedData(WinDataDto winData, List<MCNumDto> mcNumList) {
		String result = "";
		
		//당첨번호 설정
		int[] numbers = LottoUtil.getNumbers(winData);
		int matchedCount = 0;	//맞은 궁합수 개수
		
		Map<Integer, ArrayList<Integer>> mcMap = this.getMcNumberMap(mcNumList);
		
		for (int i = 0; i < numbers.length; i++) {
			ArrayList<Integer> mcList = mcMap.get(numbers[i]);
			
			//궁합수 출력
			for (int j = 0; j < numbers.length; j++) {
				if (i == j) {
					continue;
				} else {
					//궁합수에 당첨번호가 포함되어 있을 경우
					if (mcList.contains(numbers[j])) {
						matchedCount++;	//맞은 궁함수 개수 증가
						//출력결과 설정
						if (!"".equals(result)) {
							result += ", ";
						}
						result += numbers[i] + "-" + numbers[j];
					}
				}
			}
		}
		
		if (matchedCount > 0) {
			result = matchedCount+"개 : " + result; 
		}
		return result;
	}
	
	/**
	 * 궁합수 Map 데이터 조회
	 *  
	 * @param mcNumList
	 * @return
	 */
	private Map<Integer, ArrayList<Integer>> getMcNumberMap(List<MCNumDto> mcNumList) {
		Map<Integer, ArrayList<Integer>> mcMap = new HashMap<Integer, ArrayList<Integer>>();
		
		for (int i = 1; i <= mcNumList.size(); i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			MCNumDto dto = mcNumList.get(i-1);
			String mcNum = dto.getMc_num();
			String[] arrMcNum = mcNum.split(",");
			for (int j = 0; j < arrMcNum.length; j++) {
				list.add(Integer.parseInt(arrMcNum[j]));
			}
			
			mcMap.put(i, list);
		}
		
		return mcMap;
	}
	
	/**
	 * 예상번호 추출 및 등록
	 * 
	 * @param winDataList 전체데이터 (오름차순) 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 */
	public void insertExptNum(List<WinDataAnlyDto> winDataList, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		
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
		List<Integer> numberOfInputCountList = this.getNumbersOfInputCount(winDataList, expectContainCnt);		
		//입력한 회차합 미출현 숫자
		List<Integer> numberOfNotInputCountList = this.getNumbersOfInputCount(numberOfInputCountList);	
		
		
		/*********************************************************
		 * 제외수 적용 번호추출
		 *********************************************************/
		//제외수를 저장할 list
		List<Integer> deleteTargetList = new ArrayList<Integer>();
		// 제외수 규칙 조회
		ExDataDto exDataDto = new ExDataDto();
		exDataDto.setEx_count(exptPtrnAnlyInfo.getEx_count());
		ExcludeDto excludeDto = this.getExcludeInfo(exDataDto);
		String[] excludeNum = excludeDto.getExclude_num().replaceAll(" ", "").split(",");
		for (int i = 0; i < excludeNum.length; i++) {
			deleteTargetList.add(Integer.parseInt(excludeNum[i]));
		}
		
		//제외수를 포함하지 않는 회차합 숫자들
		List<Integer> numberOfContainList = this.getListWithExcludedNumber(numberOfInputCountList, deleteTargetList);
		//제외수를 포함하지 않는 회차합이 아닌 숫자들
		List<Integer> numberOfNotContainList = this.getListWithExcludedNumber(numberOfNotInputCountList, deleteTargetList);
		
	}

	private List<Integer> getListWithExcludedNumber(List<Integer> list,
			List<Integer> deleteTargetList) {
		List<Integer> controlList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			//제외수 존재여부
			boolean isExist = false;
			for (int j = 0; j < deleteTargetList.size(); j++) {
				//번호가 제외수인지 확인한다.
				if(list.get(i) == deleteTargetList.get(j)){
					isExist = true;
					break;
				}
			}
			
			if(!isExist){
				controlList.add(list.get(i));
			}
		}
		
		return controlList;
	}

	/**
	 *  @description <div id=description><b>입력한 회차동안 출현하지 않은 번호 구하기</b></div >
     *              <div id=detail>입력한 회차동안 출현하지 않은 번호들을 구한다.</div >
     *              
	 * @param numberOfInputCountList 입력한 회차합 출현숫자 목록
	 * @return
	 */
	public List<Integer> getNumbersOfInputCount(List<Integer> numberOfInputCountList) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Integer> notContainList = new ArrayList<Integer>();
		
		for(int number : numberOfInputCountList){
			map.put(number, number);
		}
		
		for(int number = 1 ; number <= 45 ; number++){
			if(!map.containsKey(number)){
				notContainList.add(number);
			}
		}
		
		return notContainList;
	}

	/**
	 * @description <div id=description><b>입력한 회차합 구하기</b></div >
     *              <div id=detail>입력한 지난 회전까지 출현했던 번호들을 구한다.</div >
     *              
	 * @param winDataList 전체데이터 (오름차순)
	 * @param expectContainCnt 예상 회차합
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getNumbersOfInputCount(List<WinDataAnlyDto> winDataList, int expectContainCnt) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Integer> containList = new ArrayList<Integer>();
		
		for(int index = winDataList.size() - expectContainCnt ; index < winDataList.size() ; index++){
			int[] numbers = LottoUtil.getNumbers(winDataList.get(index));
			
			for(int number : numbers){
				if(!map.containsKey(number)){
					containList.add(number);
					map.put(number, number);
				}
			}
		}
		
		return (List<Integer>) LottoUtil.dataSort(containList);
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
			
//			System.out.println("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
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
			
//			System.out.println("i="+i);
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
			
			System.out.println(i + " data = " + one.toString());
			
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
			
//			System.out.println("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
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
			
//			System.out.println("i="+i);
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
