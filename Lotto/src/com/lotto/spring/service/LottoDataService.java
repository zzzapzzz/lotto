package com.lotto.spring.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.chello.base.spring.core.DefaultService;
import com.lotto.common.LottoUtil;
import com.lotto.spring.domain.dto.AcDto;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.EndNumDto;
import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExcludeDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.LowHighDto;
import com.lotto.spring.domain.dto.MCNumDto;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.domain.dto.ZeroRangeDto;

@Service("lottoDataService")
public class LottoDataService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired(required = true)
    private SysmngService sysmngService;
	
	/**
	 * @description <div id=description><b>고저 비율 구하기</b></div>
     *              <div id=detail>고저 비율을 구한다.</div>
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
	 * @description <div id=description><b>홀짝 비율 구하기</b></div>
     *              <div id=detail>홀짝 비율을 구한다.</div>
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
	 * @description <div id=description><b>총합 범위 구하기</b></div>
	 *              <div id=detail>전체 총합분포 중 상위 10%와 하위 10%를 제외한 최저 총합과 최고 총합의 범위를 구한다.</div>
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
		
//		log.info("totalList : " + totalList.size());
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
	 * @description <div id=description><b>끝수합 범위 구하기</b></div>
	 *              <div id=detail>전체 끝수합의 범위를 구한다.</div>
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int[] getLowEndNumHighEndNum(List winDataList){
		int[] data = {0,0};
		
		//끝수합 설정
		for (int i = 0; i < winDataList.size(); i++) {
			int total = 0;
			int[] numbers = null;
			
			// List의 type에 따른 분기처리 2019.01.04
			if (winDataList.get(i) instanceof WinDataDto) {
				numbers = LottoUtil.getNumbers((WinDataDto)winDataList.get(i));
			} else if (winDataList.get(i) instanceof WinDataAnlyDto) {	
				numbers = LottoUtil.getNumbers((WinDataAnlyDto)winDataList.get(i));
			}
			
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
	 * @description <div id=description><b>표준 끝수합 범위 설정</b></div>
	 *              <div id=detail>끝수합 분포도를 10단위로 보여준다.</div>
	 * 
	 * @param winDataList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int[] getEndNumberBaseDistribution(List winDataList){
		
		int[] arrLowHighEndNumData = this.getLowEndNumHighEndNum(winDataList);
		int lowEndNum = arrLowHighEndNumData[0]; 
		int highEndNum = arrLowHighEndNumData[1]; 
		int[] arrReturnData = {0,0};
		
		/** 개별 개수 */
		int[] rangeCnt = new int[54];
		for (int index = 0; index < rangeCnt.length; index++) {
			rangeCnt[index] = 0;
		}
		/** 하위범위(10%) 초과여부 */
		boolean minExceed = false;
		/** 상위범위(90%) 초과여부 */
		boolean maxExceed = false;
		/** 개별누적개수 */
		int accumulatedCnt = 0;
		
		//끝수합 개별 개수 구하기
		for (int i = 0; i < winDataList.size(); i++) {
			if (winDataList.get(i) instanceof WinDataDto) {
				WinDataDto winData = (WinDataDto)winDataList.get(i); 
				rangeCnt[winData.getSum_end_num() -1] += 1;
			} else if (winDataList.get(i) instanceof WinDataAnlyDto) {
				WinDataAnlyDto winData = (WinDataAnlyDto)winDataList.get(i);
				rangeCnt[winData.getSum_end_num() -1] += 1;
			}
		}

		//표준 끝수합 범위 계산
		for (int index = lowEndNum -1 ; index < highEndNum ; index++) {
			accumulatedCnt += rangeCnt[index];  
			
			//최저범위 계산
			//최저범위 10%를 초과하면 index를 최저범위로 설정
			double range = LottoUtil.getRatio(accumulatedCnt, winDataList.size());
			if(range > 11.0 && !minExceed){
				arrReturnData[0] = index;
				minExceed = true;
			}
			
			//최고범위 계산
			if(range > 91.0 && !maxExceed){
				arrReturnData[1] = index - 1;
				maxExceed = true;
				break;
			}
			
		}
		
		return arrReturnData;
	}
	
	/**
	 * <div id=description><b>회차별 회차합 정보 구하기</b></div><br>
     * <div id=detail>회차별 회차합 정보를 추출하고, 포함/미포함 번호의 개수를 설정한다.</div><br>
     * 
	 * @param winDataList 전체데이터
	 * @return
	 */
	public CountSumDto getLastContainCnt(List<WinDataDto> winDataList) {
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
	 * 제외수 규칙 분석
	 * 
	 * @param winDataList
	 * @return
	 */
	public List<ArrayList<Integer>> getNotContainNumbers(List<WinDataDto> winDataList) {
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
				
				if(minNum == 0 && maxNum == 0){
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
				this.setNotContainModNumbers(dataList, winDataList, minNum);
			}else{
				int cnt1 = 0;
				int cnt2 = 0;
				
				//전회차순서별숫자 + 최소차 값이 다음차수의 번호중에 일치하는 번호가 있는지 여부확인
				if(disMin != 0){	//첫번째 번호일 경우 가장 작은 번호가 1이라면 최소차는 0이 될 수 있음.
					cnt1 = this.setNotContainDataNumbers(dataList, winDataList, index, disMin, 0);
				}
				
				if(disMax != 0){	//여섯번째 번호일 경우 가장 큰 번호가 45이라면 최대차는 0이 될 수 있음.
					cnt2 = this.setNotContainDataNumbers(dataList, winDataList, index, 1, disMax+1);
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
	 * @description <div id=description><b>첫번째 숫자 제외수 - Mod</b></div>
     *              <div id=detail>첫번째 숫자를 45로 나눈 나머지를 더하여 중복되는 수가 있는지 찾는다.</div>
     * @param dataList
	 * @param winDataList
	 * @param minNum 
	 */
	public void setNotContainModNumbers(ArrayList<Integer> dataList, List<WinDataDto> winDataList, int minNum){
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
//					log.info(dataIndex + " : " + sourceNumber + " + " + mod + " : " + (sourceNumber + mod));
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
//					log.info("************** mod : " + mod + " / cnt : " + cnt);
					if(cnt > maxCnt){
						maxCnt = cnt;
						maxMod = mod;
					}
				}
			}
			
			//sourceNumber + mod 가 한번도 다음회차에 나오지 않았다면
			if(isExist){
				log.info(mod);
			}
			
		}
		
//		log.info("*** End ***");
//		log.info("maxCnt : " + maxCnt + ", maxMod : " + maxMod);
		
		dataList.add(maxMod);
		
	}
	
	/**
	 * @description <div id=description><b>제외수 설정</b></div>
     *              <div id=detail>첫 번째 숫자를 제외한 나머지 숫자들의 규칙을 찾는다.</div>
     * @param dataList 제외수를 저장할 list
	 * @param list 데이터를 가지고 있는 list
	 * @param index 현재 구하는 순번위치
	 * @param fromNum 초기 비교규칙
	 * @param toNum 마지막 비교규칙
	 */
	public int setNotContainDataNumbers(ArrayList<Integer> dataList, List<WinDataDto> list, int index, int fromNum, int toNum){
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
//					log.info("************** mod : " + idx + " / cnt : " + cnt);
					if(cnt > maxCnt){
						maxCnt = cnt;
						maxIdx = idx;
					}
				}
			}
			
			if(isExist){
				log.info(idx);
			}
		}
		
//		log.info("*** End ***");
//		log.info("maxCnt : " + maxCnt + ", maxIdx : " + maxIdx);
		
		dataList.add(maxIdx);
		
		return maxCnt;
	}

	/**
	 * @description <div id=description><b>궁합/불협수 설정</b></div>
	 *              <div id=detail>각 숫자별로 가장 많이 나온 3수를 궁합수, 가장 나오지 않은 수를 불협수로 설정한다.</div>
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
//					log.info( (num+1) + " : " + cnt);
				
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
	 * 등록할 궁합수 정보 가져오기
	 * 
	 * @param mcNumberMap 번호별 궁합수 정보
	 * @param lastData 최근 당첨번호 정보
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMCInfoMap(Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap, WinDataDto lastData) {
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
	 * 미출현 번호대 구간 분석자료 가져오기
	 * 2018.12.15
	 * 
	 * @param lastData
	 * @return
	 */
//	public int[] getZeroCntRangeData(WinDataDto lastData) {
	public int[] getZeroCntRangeData(Object lastData) {
		int[] numbers = LottoUtil.getNumbersFromObj(lastData);
		
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
	 * @description <div id=description><b>궁합/불협수 설정</b></div>
	 *              <div id=detail>각 숫자별로 가장 많이 나온 3수를 궁합수, 가장 나오지 않은 수를 불협수로 설정한다.</div>
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
//					log.info( (num+1) + " : " + cnt);
				
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
	 * @description <div id=description><b>10회차합 구하기</b></div>
     *              <div id=detail>지난 10회동안 출현했던 번호들을 구한다.</div>
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
	 * @description <div id=description><b>10회차합 구하기</b></div>
     *              <div id=detail>지정된 회차 이전의 지난 10회동안 출현했던 번호들을 구한다.</div>
     *              
	 * @param winDataList 전체 당첨번호 (오름차순)
	 * @param winCount 기준회차
	 * @return
	 */
	public List<Integer> getContain10List(List<WinDataDto> winDataList, int winCount) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<Integer> containList = new ArrayList<Integer>();
		
		for(int index = winCount - 10 ; index < winCount ; index++){
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
	 * @description <div id=description><b>10회차동안 출현하지 않은 번호 구하기</b></div>
     *              <div id=detail>10회차동안 출현하지 않은 번호들을 구한다.</div>
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
	 * 궁합수 매칭정보 확인
	 * 
	 * @param winData 당첨번호 
	 * @param mcNumList 궁합수 목록
	 * @return 궁합수 매칭정보 String
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
		
//		if (matchedCount > 0) {
//			result = matchedCount+"개 : " + result; 
//		}
		return result;
	}
	
	/**
	 * 궁합수 매칭정보 확인
	 * 
	 * @param winData 당첨번호 
	 * @param mcNumList 궁합수 목록
	 * @return 궁합수 매칭정보 Map
	 */
	public Map<String, Object> getMcMatchedDataMap(WinDataDto winData, List<MCNumDto> mcNumList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
		
		resultMap.put("matchedCount", matchedCount);
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	/**
	 * 궁합수 Map 데이터 조회
	 *  
	 * @param mcNumList
	 * @return
	 */
	public Map<Integer, ArrayList<Integer>> getMcNumberMap(List<MCNumDto> mcNumList) {
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
	 * @description <div id=description><b>번호간 차이값 설정하기</b></div>
     *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div>
	 * 
	 * @param winDataList
	 */
	public void setDifNumbers(List<WinDataAnlyDto> winDataList) {
		for (int i = 0; i < winDataList.size(); i++) {
			WinDataAnlyDto winData = winDataList.get(i);
			int[] difNumbers = this.getDifNumbers(LottoUtil.getNumbers(winData));
			winData.setDifNumbers(difNumbers);
		}
	}
	
	/**
	 * @description <div id=description><b>당첨번호 배열 설정하기</b></div>
	 * 
	 * @param winDataList
	 */
	public void setNumbers(List<WinDataAnlyDto> winDataList) {
		for (int i = 0; i < winDataList.size(); i++) {
			WinDataAnlyDto winData = winDataList.get(i);
			winData.setNumbers(LottoUtil.getNumbers(winData));
		}
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값 계산하기</b></div>
     *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div>
	 * @param numbers
	 * @return
	 */
	public int[] getDifNumbers(int[] numbers) {
		int[] difnumbers = {0,0,0,0,0};
		
		for (int i = 0; i < numbers.length -1 ; i++) {
			difnumbers[i] = Math.abs(numbers[i] - numbers[i+1]);
		}
		
		return difnumbers;
	}
	
	/**
	 * 회차합으로 조합할 수 있는 모든 번호목록을 조회한다.
	 * 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 * @param numberOfNotContainList 제외수를 제거한 출현번호 목록
	 * @param numberOfContainList 제외수를 제거한 미출현번호 목록
	 * @return
	 */
	public List<int[]> getRandomCombinationList(ExptPtrnAnlyDto exptPtrnAnlyInfo, 
												List<Integer> numberOfContainList, 
												List<Integer> numberOfNotContainList) {
		/** 추출한 숫자 목록 */
		List<int[]> numberList = new ArrayList<int[]>();
		/** 출현번호 중 포함숫자 조합 */
		List<String> containNumbers = new ArrayList<String>();
		/** 출현번호 중 미포함숫자 조합 */
		List<String> notContainNumbers = new ArrayList<String>();
		/** 숫자 조합 중복 체크 Map */
		Map<String, int[]> dupCheckMap = new HashMap<String, int[]>();
		/** 숫자 조합 */
		int[] numbers = new int[6];
		
		//패턴에 의해 결정된 포함개수
		int CONTAIN_CNT = exptPtrnAnlyInfo.getCont_cnt();
		//패턴에 의해 결정된 미포함개수
		int NOT_CONTAIN_CNT = exptPtrnAnlyInfo.getNot_cont_cnt();	
		
		//출현번호 중 선출할 목록
		List<Integer> controlList = new ArrayList<Integer>();
		//미출현번호 중 선출할 목록
		List<Integer> controlList2 = new ArrayList<Integer>();
		
		for (int i = 0; i < numberOfContainList.size(); i++) {
			controlList.add(numberOfContainList.get(i));
		}
		
		for (int i = 0; i < numberOfNotContainList.size(); i++) {
			controlList2.add(numberOfNotContainList.get(i));
		}
		
		//출현번호 중 포함 숫자 조합 추출하기
		//포함개수만큼 반복하여 숫자를 조합한다.
		if (CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList.size() - (CONTAIN_CNT - 1); i++) {
				if (CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList.size() - (CONTAIN_CNT - 2); j++) {
						if (CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList.size() - (CONTAIN_CNT - 3); k++) {
								if (CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList.size() - (CONTAIN_CNT - 4); l++) {
										if (CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList.size() - (CONTAIN_CNT - 5); m++) {
												if (CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList.size()
															- (CONTAIN_CNT - 6); n++) {
														containNumbers.add("" + controlList.get(i) + ","
																+ controlList.get(j) + "," + controlList.get(k) + ","
																+ controlList.get(l) + "," + controlList.get(m) + ","
																+ controlList.get(n));
													}
												} else {
													containNumbers.add("" + controlList.get(i) + ","
															+ controlList.get(j) + "," + controlList.get(k) + ","
															+ controlList.get(l) + "," + controlList.get(m));
												}
											}
										} else {
											containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
													+ controlList.get(k) + "," + l);
										}
									}
								} else {
									containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
											+ controlList.get(k));
								}
							}
						} else {
							containNumbers.add("" + controlList.get(i) + "," + controlList.get(j));
						}
					}
				} else {
					containNumbers.add("" + controlList.get(i));
				}
			}
		}
			
		//미출현번호 중 미포함 숫자 조합 추출하기
		//미포함개수만큼 반복하여 숫자를 조합한다.
		if (NOT_CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList2.size() - (NOT_CONTAIN_CNT - 1); i++) {
				if (NOT_CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList2.size() - (NOT_CONTAIN_CNT - 2); j++) {
						if (NOT_CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList2.size() - (NOT_CONTAIN_CNT - 3); k++) {
								if (NOT_CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList2.size() - (NOT_CONTAIN_CNT - 4); l++) {
										if (NOT_CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList2.size() - (NOT_CONTAIN_CNT - 5); m++) {
												if (NOT_CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList2.size()
															- (NOT_CONTAIN_CNT - 6); n++) {
														notContainNumbers.add("" + controlList2.get(i) + ","
																+ controlList2.get(j) + "," + controlList2.get(k) + ","
																+ controlList2.get(l) + "," + controlList2.get(m) + ","
																+ controlList2.get(n));
													}
												} else {
													notContainNumbers.add("" + controlList2.get(i) + ","
															+ controlList2.get(j) + "," + controlList2.get(k) + ","
															+ controlList2.get(l) + "," + controlList2.get(m));
												}
											}
										} else {
											notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j)
													+ "," + controlList2.get(k) + "," + l);
										}
									}
								} else {
									notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j) + ","
											+ controlList2.get(k));
								}
							}
						} else {
							notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j));
						}
					}
				} else {
					notContainNumbers.add("" + controlList2.get(i));
				}
			}
		}
				
		//포함숫자 목록과 미포함숫자 목록을 조합하여 
		//배열에 넣은 후 정렬한 다음 중복체크를 하여 
		//중복되지 않는 숫자는 리스트에 추가한다.
		if (containNumbers.size() > 0) {
			for (int i = 0; i < containNumbers.size(); i++) {
				if (notContainNumbers.size() > 0) {
					for (int j = 0; j < notContainNumbers.size(); j++) {
						String strNumber = containNumbers.get(i) + "," + notContainNumbers.get(j);
						numbers = new int[6];

						String[] strNumbers = strNumber.split(",");
						for (int k = 0; k < strNumbers.length; k++) {
							numbers[k] = Integer.parseInt(strNumbers[k]);
						}

						if (dupCheckMap.containsKey(strNumber)) {
							// 이미 등록된 번호
							continue;
						} else {
							numbers = (int[]) LottoUtil.dataSort(numbers);
							numberList.add(numbers);
							dupCheckMap.put(strNumber, numbers);
						}
					}
				} else {
					String strNumber = containNumbers.get(i);
					numbers = new int[6];

					String[] strNumbers = strNumber.split(",");
					for (int k = 0; k < strNumbers.length; k++) {
						numbers[k] = Integer.parseInt(strNumbers[k]);
					}

					if (dupCheckMap.containsKey(strNumber)) {
						// 이미 등록된 번호
						continue;
					} else {
						numbers = (int[]) LottoUtil.dataSort(numbers);
						numberList.add(numbers);
						dupCheckMap.put(strNumber, numbers);
					}
				}
			}
		} else {
			for (int i = 0; i < notContainNumbers.size(); i++) {
				String strNumber = notContainNumbers.get(i);
				numbers = new int[6];

				String[] strNumbers = strNumber.split(",");
				for (int k = 0; k < strNumbers.length; k++) {
					numbers[k] = Integer.parseInt(strNumbers[k]);
				}

				if (dupCheckMap.containsKey(strNumber)) {
					// 이미 등록된 번호
					continue;
				} else {
					numbers = (int[]) LottoUtil.dataSort(numbers);
					numberList.add(numbers);
					dupCheckMap.put(strNumber, numbers);
				}
			}
		}
		
		return numberList;
	}
	
	/**
	 * 조합할 수 있는 모든 번호목록을 조회한다.
	 * @param exCount 
	 * 
	 * @param numberList 제외수를 제거한 번호 전체목록
	 * @return
	 */
	public List<ExDataDto> getExDataList(List<Integer> controlList, int exCount) {
		/** 추출한 예상데이터 목록 */
		List<ExDataDto> exDataList = new ArrayList<ExDataDto>();
		
		/** 숫자 조합 목록 */
		List<String> numbersList = new ArrayList<String>();
		
		for (int i = 0; i < controlList.size() - 5; i++) {
			for (int j = i + 1; j < controlList.size() - 4; j++) {
				for (int k = j + 1; k < controlList.size() - 3; k++) {
					for (int l = k + 1; l < controlList.size() - 2; l++) {
						for (int m = l + 1; m < controlList.size() - 1; m++) {
							for (int n = m + 1; n < controlList.size(); n++) {
								numbersList.add("" + controlList.get(i) + ","
										+ controlList.get(j) + "," + controlList.get(k) + ","
										+ controlList.get(l) + "," + controlList.get(m) + ","
										+ controlList.get(n));
							}
						}
					}
				}
			}
		}
		
		int[] numbers = new int[6];
		for (int i = 0; i < numbersList.size(); i++) {
			String strNumber = numbersList.get(i);
			String[] strNumbers = strNumber.split(",");
			for (int k = 0; k < strNumbers.length; k++) {
				numbers[k] = Integer.parseInt(strNumbers[k]);
				if (numbers[k] == 5) {
					System.out.println(5);
				}
			}
			
			numbers = (int[]) LottoUtil.dataSort(numbers);
			
			// 예상데이터 등록
			ExDataDto exData = this.getExData(exCount, i + 1, numbers);
			exDataList.add(exData);
			
		}
		
		
		return exDataList;
	}
	
	/**
	 * 모든 번호조합 목록을 조회한다.
	 * @param exCount 
	 * 
	 * @param numberList 제외수를 제거한 번호 전체목록
	 * @return
	 */
	public List<ExDataDto> getExDataList(int exCount) {
		/** 추출한 예상데이터 목록 */
		List<ExDataDto> exDataList = new ArrayList<ExDataDto>();
		
		/** 숫자 조합 목록 */
		List<String> numbersList = new ArrayList<String>();
		
		// 중복조합 체크 Map
		Map<String, String> duplCheckMap = new HashMap<String, String>();
		
		for (int i = 1; i <= 45 - 5; i++) {
			for (int j = i + 1; j <= 45 - 4; j++) {
				for (int k = j + 1; k <= 45 - 3; k++) {
					for (int l = k + 1; l <= 45 - 2; l++) {
						for (int m = l + 1; m <= 45 - 1; m++) {
							for (int n = m + 1; n <= 45; n++) {
								String numbers = "" + i + "," + j + "," + k + ","
										+ l + "," + m + "," + n;
								if (!duplCheckMap.containsKey(numbers)) {
									duplCheckMap.put(numbers, "Y");
									numbersList.add(numbers);
								}
							}
						}
					}
				}
			}
		}
		
		int[] numbers = new int[6];
		for (int i = 0; i < numbersList.size(); i++) {
			String strNumber = numbersList.get(i);
			String[] strNumbers = strNumber.split(",");
			for (int k = 0; k < strNumbers.length; k++) {
				numbers[k] = Integer.parseInt(strNumbers[k]);
			}
			
			numbers = (int[]) LottoUtil.dataSort(numbers);
			
			// 예상데이터 등록
			ExDataDto exData = this.getExData(exCount, i + 1, numbers);
			exDataList.add(exData);
			
		}
		
		
		return exDataList;
	}
	
	/**
	 * 회차합으로 조합할 수 있는 모든 번호목록을 조회한다.
	 * 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 * @param numberOfNotContainList 제외수를 제거한 출현번호 목록
	 * @param numberOfContainList 제외수를 제거한 미출현번호 목록
	 * @param deleteTargetMap 제외수 map
	 * @return
	 */
	public List<ExDataDto> getExDataList(ExptPtrnAnlyDto exptPtrnAnlyInfo, 
			List<Integer> numberOfContainList, 
			List<Integer> numberOfNotContainList,
			Map<Integer, Integer> deleteTargetMap) {
		
		/** 추출한 예상데이터 목록 */
		List<ExDataDto> exDataList = new ArrayList<ExDataDto>();
		
		/** 출현번호 중 포함숫자 조합 */
		List<String> containNumbers = new ArrayList<String>();
		/** 출현번호 중 미포함숫자 조합 */
		List<String> notContainNumbers = new ArrayList<String>();
		/** 숫자 조합 중복 체크 Map */
		Map<String, int[]> dupCheckMap = new HashMap<String, int[]>();
		/** 숫자 조합 */
		int[] numbers = new int[6];
		
		//패턴에 의해 결정된 포함개수
		int CONTAIN_CNT = exptPtrnAnlyInfo.getCont_cnt();
		//패턴에 의해 결정된 미포함개수
		int NOT_CONTAIN_CNT = exptPtrnAnlyInfo.getNot_cont_cnt();	
		
		//출현번호 중 선출할 목록
		List<Integer> controlList = new ArrayList<Integer>();
		//미출현번호 중 선출할 목록
		List<Integer> controlList2 = new ArrayList<Integer>();
		
		for (int i = 0; i < numberOfContainList.size(); i++) {
			controlList.add(numberOfContainList.get(i));
		}
		
		for (int i = 0; i < numberOfNotContainList.size(); i++) {
			controlList2.add(numberOfNotContainList.get(i));
		}
		
		//출현번호 중 포함 숫자 조합 추출하기
		//포함개수만큼 반복하여 숫자를 조합한다.
		if (CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList.size() - (CONTAIN_CNT - 1); i++) {
				if (CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList.size() - (CONTAIN_CNT - 2); j++) {
						if (CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList.size() - (CONTAIN_CNT - 3); k++) {
								if (CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList.size() - (CONTAIN_CNT - 4); l++) {
										if (CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList.size() - (CONTAIN_CNT - 5); m++) {
												if (CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList.size()
															- (CONTAIN_CNT - 6); n++) {
														containNumbers.add("" + controlList.get(i) + ","
																+ controlList.get(j) + "," + controlList.get(k) + ","
																+ controlList.get(l) + "," + controlList.get(m) + ","
																+ controlList.get(n));
													}
												} else {
													containNumbers.add("" + controlList.get(i) + ","
															+ controlList.get(j) + "," + controlList.get(k) + ","
															+ controlList.get(l) + "," + controlList.get(m));
												}
											}
										} else {
											containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
													+ controlList.get(k) + "," + l);
										}
									}
								} else {
									containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
											+ controlList.get(k));
								}
							}
						} else {
							containNumbers.add("" + controlList.get(i) + "," + controlList.get(j));
						}
					}
				} else {
					containNumbers.add("" + controlList.get(i));
				}
			}
		}
		
		//미출현번호 중 미포함 숫자 조합 추출하기
		//미포함개수만큼 반복하여 숫자를 조합한다.
		if (NOT_CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList2.size() - (NOT_CONTAIN_CNT - 1); i++) {
				if (NOT_CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList2.size() - (NOT_CONTAIN_CNT - 2); j++) {
						if (NOT_CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList2.size() - (NOT_CONTAIN_CNT - 3); k++) {
								if (NOT_CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList2.size() - (NOT_CONTAIN_CNT - 4); l++) {
										if (NOT_CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList2.size() - (NOT_CONTAIN_CNT - 5); m++) {
												if (NOT_CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList2.size()
															- (NOT_CONTAIN_CNT - 6); n++) {
														notContainNumbers.add("" + controlList2.get(i) + ","
																+ controlList2.get(j) + "," + controlList2.get(k) + ","
																+ controlList2.get(l) + "," + controlList2.get(m) + ","
																+ controlList2.get(n));
													}
												} else {
													notContainNumbers.add("" + controlList2.get(i) + ","
															+ controlList2.get(j) + "," + controlList2.get(k) + ","
															+ controlList2.get(l) + "," + controlList2.get(m));
												}
											}
										} else {
											notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j)
											+ "," + controlList2.get(k) + "," + l);
										}
									}
								} else {
									notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j) + ","
											+ controlList2.get(k));
								}
							}
						} else {
							notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j));
						}
					}
				} else {
					notContainNumbers.add("" + controlList2.get(i));
				}
			}
		}
		
		//포함숫자 목록과 미포함숫자 목록을 조합하여 
		//배열에 넣은 후 정렬한 다음 중복체크를 하여 
		//중복되지 않는 숫자는 리스트에 추가한다.
		if (containNumbers.size() > 0) {
			for (int i = 0; i < containNumbers.size(); i++) {
				if (notContainNumbers.size() > 0) {
					for (int j = 0; j < notContainNumbers.size(); j++) {
						String strNumber = containNumbers.get(i) + "," + notContainNumbers.get(j);
						numbers = new int[6];
						
						String[] strNumbers = strNumber.split(",");
						for (int k = 0; k < strNumbers.length; k++) {
							numbers[k] = Integer.parseInt(strNumbers[k]);
						}
						
						if (dupCheckMap.containsKey(strNumber)) {
							// 이미 등록된 번호
							continue;
						} else {
							numbers = (int[]) LottoUtil.dataSort(numbers);
							
							//2016.08.05
							//제외수가 포함 비교하여 있으면 skip 함
							boolean isProsess = true;
							for (int l = 0; l < numbers.length; l++) {
								if (deleteTargetMap.containsKey(numbers[l])) {
									isProsess = false;
									break;
								}
							}
							
							if (!isProsess) {
								continue;
							}
							
							/*
							 * 동일번호 존재여부 체크
							 * 2019.07.24
							 */
							Map<Integer, Integer> numbersMap = new HashMap<Integer, Integer>();
							for (int idx = 0; idx < numbers.length; idx++) {
								if (numbersMap.containsKey(numbers[idx])) {
									isProsess = false;
									break;
								} else {
									numbersMap.put(numbers[idx], numbers[idx]);
								}
							}
							
							if (!isProsess) {
								continue;
							}
							
							// 예상데이터 등록
							ExDataDto exData = this.getExData(exptPtrnAnlyInfo.getEx_count(), exDataList.size() + 1, numbers);
							exDataList.add(exData);
							
							dupCheckMap.put(strNumber, numbers);
						}
					}
				} else {
					String strNumber = containNumbers.get(i);
					numbers = new int[6];
					
					String[] strNumbers = strNumber.split(",");
					for (int k = 0; k < strNumbers.length; k++) {
						numbers[k] = Integer.parseInt(strNumbers[k]);
					}
					
					if (dupCheckMap.containsKey(strNumber)) {
						// 이미 등록된 번호
						continue;
					} else {
						numbers = (int[]) LottoUtil.dataSort(numbers);
						
						//2016.08.05
						//제외수가 포함 비교하여 있으면 skip 함
						boolean isProsess = true;
						for (int l = 0; l < numbers.length; l++) {
							if (deleteTargetMap.containsKey(numbers[l])) {
								isProsess = false;
								break;
							}
						}
						
						if (!isProsess) {
							continue;
						}
						
						/*
						 * 동일번호 존재여부 체크
						 * 2019.07.24
						 */
						Map<Integer, Integer> numbersMap = new HashMap<Integer, Integer>();
						for (int idx = 0; idx < numbers.length; idx++) {
							if (numbersMap.containsKey(numbers[idx])) {
								isProsess = false;
								break;
							} else {
								numbersMap.put(numbers[idx], numbers[idx]);
							}
						}
						
						if (!isProsess) {
							continue;
						}
						
						// 예상데이터 등록
						ExDataDto exData = this.getExData(exptPtrnAnlyInfo.getEx_count(), exDataList.size() + 1, numbers);
						exDataList.add(exData);
						
						dupCheckMap.put(strNumber, numbers);
					}
				}
			}
		} else {
			for (int i = 0; i < notContainNumbers.size(); i++) {
				String strNumber = notContainNumbers.get(i);
				numbers = new int[6];
				
				String[] strNumbers = strNumber.split(",");
				for (int k = 0; k < strNumbers.length; k++) {
					numbers[k] = Integer.parseInt(strNumbers[k]);
				}
				
				if (dupCheckMap.containsKey(strNumber)) {
					// 이미 등록된 번호
					continue;
				} else {
					numbers = (int[]) LottoUtil.dataSort(numbers);
					
					//2016.08.05
					//제외수가 포함 비교하여 있으면 skip 함
					boolean isProsess = true;
					for (int l = 0; l < numbers.length; l++) {
						if (deleteTargetMap.containsKey(numbers[l])) {
							isProsess = false;
							break;
						}
					}
					
					if (!isProsess) {
						continue;
					}
					
					/*
					 * 동일번호 존재여부 체크
					 * 2019.07.24
					 */
					Map<Integer, Integer> numbersMap = new HashMap<Integer, Integer>();
					for (int idx = 0; idx < numbers.length; idx++) {
						if (numbersMap.containsKey(numbers[idx])) {
							isProsess = false;
							break;
						} else {
							numbersMap.put(numbers[idx], numbers[idx]);
						}
					}
					
					if (!isProsess) {
						continue;
					}
					
					// 예상데이터 등록
					ExDataDto exData = this.getExData(exptPtrnAnlyInfo.getEx_count(), exDataList.size() + 1, numbers);
					exDataList.add(exData);
					
					dupCheckMap.put(strNumber, numbers);
				}
			}
		}
		
		return exDataList;
	}
	
	/**
	 * 예상데이터 설정하기
	 * 
	 * @param ex_count 예상회차
	 * @param seq 번호 순번
	 * @param numbers 예상번호 배열
	 * @return 예상데이터 Dto
	 */
	public ExDataDto getExData(int ex_count, int seq, int[] numbers) {
		// 예상데이터 등록
		ExDataDto exData = new ExDataDto();
		//회차 설정
		exData.setEx_count(ex_count);
		exData.setSeq(seq);
		exData.setNum1(numbers[0]);
		exData.setNum2(numbers[1]);
		exData.setNum3(numbers[2]);
		exData.setNum4(numbers[3]);
		exData.setNum5(numbers[4]);
		exData.setNum6(numbers[5]);
		exData.setTotal(LottoUtil.getTotal(exData));
		exData.setSum_end_num(LottoUtil.getSumEndNumber(exData));
		exData.setLow_high(LottoUtil.getLowHigh(exData));
		exData.setOdd_even(LottoUtil.getOddEven(exData));
		exData.setAc(LottoUtil.getAc(exData));
		
		exData.setNumbers(LottoUtil.getNumbers(exData));
		exData.setDifNumbers(LottoUtil.getDifNumbers(exData));
		
		return exData;
	}

	/**
	 * 회차합으로 조합할 수 있는 모든 번호목록을 조회한다.
	 * 
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 * @param numberOfNotContainList 제외수를 제거한 출현번호 목록
	 * @param numberOfContainList 제외수를 제거한 미출현번호 목록
	 * @param deleteTargetMap 제외수 map
	 * @return
	 */
	public List<int[]> getRandomCombinationList(ExptPtrnAnlyDto exptPtrnAnlyInfo, 
			List<Integer> numberOfContainList, 
			List<Integer> numberOfNotContainList,
			Map<Integer, Integer> deleteTargetMap) {
		/** 추출한 숫자 목록 */
		List<int[]> numberList = new ArrayList<int[]>();
		/** 출현번호 중 포함숫자 조합 */
		List<String> containNumbers = new ArrayList<String>();
		/** 출현번호 중 미포함숫자 조합 */
		List<String> notContainNumbers = new ArrayList<String>();
		/** 숫자 조합 중복 체크 Map */
		Map<String, int[]> dupCheckMap = new HashMap<String, int[]>();
		/** 숫자 조합 */
		int[] numbers = new int[6];
		
		//패턴에 의해 결정된 포함개수
		int CONTAIN_CNT = exptPtrnAnlyInfo.getCont_cnt();
		//패턴에 의해 결정된 미포함개수
		int NOT_CONTAIN_CNT = exptPtrnAnlyInfo.getNot_cont_cnt();	
		
		//출현번호 중 선출할 목록
		List<Integer> controlList = new ArrayList<Integer>();
		//미출현번호 중 선출할 목록
		List<Integer> controlList2 = new ArrayList<Integer>();
		
		for (int i = 0; i < numberOfContainList.size(); i++) {
			controlList.add(numberOfContainList.get(i));
		}
		
		for (int i = 0; i < numberOfNotContainList.size(); i++) {
			controlList2.add(numberOfNotContainList.get(i));
		}
		
		//출현번호 중 포함 숫자 조합 추출하기
		//포함개수만큼 반복하여 숫자를 조합한다.
		if (CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList.size() - (CONTAIN_CNT - 1); i++) {
				if (CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList.size() - (CONTAIN_CNT - 2); j++) {
						if (CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList.size() - (CONTAIN_CNT - 3); k++) {
								if (CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList.size() - (CONTAIN_CNT - 4); l++) {
										if (CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList.size() - (CONTAIN_CNT - 5); m++) {
												if (CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList.size()
															- (CONTAIN_CNT - 6); n++) {
														containNumbers.add("" + controlList.get(i) + ","
																+ controlList.get(j) + "," + controlList.get(k) + ","
																+ controlList.get(l) + "," + controlList.get(m) + ","
																+ controlList.get(n));
													}
												} else {
													containNumbers.add("" + controlList.get(i) + ","
															+ controlList.get(j) + "," + controlList.get(k) + ","
															+ controlList.get(l) + "," + controlList.get(m));
												}
											}
										} else {
											containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
													+ controlList.get(k) + "," + l);
										}
									}
								} else {
									containNumbers.add("" + controlList.get(i) + "," + controlList.get(j) + ","
											+ controlList.get(k));
								}
							}
						} else {
							containNumbers.add("" + controlList.get(i) + "," + controlList.get(j));
						}
					}
				} else {
					containNumbers.add("" + controlList.get(i));
				}
			}
		}
		
		//미출현번호 중 미포함 숫자 조합 추출하기
		//미포함개수만큼 반복하여 숫자를 조합한다.
		if (NOT_CONTAIN_CNT >= 1) {
			for (int i = 0; i < controlList2.size() - (NOT_CONTAIN_CNT - 1); i++) {
				if (NOT_CONTAIN_CNT >= 2) {
					for (int j = i + 1; j < controlList2.size() - (NOT_CONTAIN_CNT - 2); j++) {
						if (NOT_CONTAIN_CNT >= 3) {
							for (int k = j + 1; k < controlList2.size() - (NOT_CONTAIN_CNT - 3); k++) {
								if (NOT_CONTAIN_CNT >= 4) {
									for (int l = k + 1; l < controlList2.size() - (NOT_CONTAIN_CNT - 4); l++) {
										if (NOT_CONTAIN_CNT >= 5) {
											for (int m = l + 1; m < controlList2.size() - (NOT_CONTAIN_CNT - 5); m++) {
												if (NOT_CONTAIN_CNT >= 6) {
													for (int n = m + 1; n < controlList2.size()
															- (NOT_CONTAIN_CNT - 6); n++) {
														notContainNumbers.add("" + controlList2.get(i) + ","
																+ controlList2.get(j) + "," + controlList2.get(k) + ","
																+ controlList2.get(l) + "," + controlList2.get(m) + ","
																+ controlList2.get(n));
													}
												} else {
													notContainNumbers.add("" + controlList2.get(i) + ","
															+ controlList2.get(j) + "," + controlList2.get(k) + ","
															+ controlList2.get(l) + "," + controlList2.get(m));
												}
											}
										} else {
											notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j)
											+ "," + controlList2.get(k) + "," + l);
										}
									}
								} else {
									notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j) + ","
											+ controlList2.get(k));
								}
							}
						} else {
							notContainNumbers.add("" + controlList2.get(i) + "," + controlList2.get(j));
						}
					}
				} else {
					notContainNumbers.add("" + controlList2.get(i));
				}
			}
		}
		
		//포함숫자 목록과 미포함숫자 목록을 조합하여 
		//배열에 넣은 후 정렬한 다음 중복체크를 하여 
		//중복되지 않는 숫자는 리스트에 추가한다.
		if (containNumbers.size() > 0) {
			for (int i = 0; i < containNumbers.size(); i++) {
				if (notContainNumbers.size() > 0) {
					for (int j = 0; j < notContainNumbers.size(); j++) {
						String strNumber = containNumbers.get(i) + "," + notContainNumbers.get(j);
						numbers = new int[6];
						
						String[] strNumbers = strNumber.split(",");
						for (int k = 0; k < strNumbers.length; k++) {
							numbers[k] = Integer.parseInt(strNumbers[k]);
						}
						
						if (dupCheckMap.containsKey(strNumber)) {
							// 이미 등록된 번호
							continue;
						} else {
							numbers = (int[]) LottoUtil.dataSort(numbers);
							
							//2016.08.05
							//제외수가 포함 비교하여 있으면 skip 함
							for (int l = 0; l < numbers.length; l++) {
								if (deleteTargetMap.containsKey(numbers[l])) {
									continue;
								}
							}
							
							numberList.add(numbers);
							dupCheckMap.put(strNumber, numbers);
						}
					}
				} else {
					String strNumber = containNumbers.get(i);
					numbers = new int[6];
					
					String[] strNumbers = strNumber.split(",");
					for (int k = 0; k < strNumbers.length; k++) {
						numbers[k] = Integer.parseInt(strNumbers[k]);
					}
					
					if (dupCheckMap.containsKey(strNumber)) {
						// 이미 등록된 번호
						continue;
					} else {
						numbers = (int[]) LottoUtil.dataSort(numbers);
						
						//2016.08.05
						//제외수가 포함 비교하여 있으면 skip 함
						for (int l = 0; l < numbers.length; l++) {
							if (deleteTargetMap.containsKey(numbers[l])) {
								continue;
							}
						}
						
						numberList.add(numbers);
						dupCheckMap.put(strNumber, numbers);
					}
				}
			}
		} else {
			for (int i = 0; i < notContainNumbers.size(); i++) {
				String strNumber = notContainNumbers.get(i);
				numbers = new int[6];
				
				String[] strNumbers = strNumber.split(",");
				for (int k = 0; k < strNumbers.length; k++) {
					numbers[k] = Integer.parseInt(strNumbers[k]);
				}
				
				if (dupCheckMap.containsKey(strNumber)) {
					// 이미 등록된 번호
					continue;
				} else {
					numbers = (int[]) LottoUtil.dataSort(numbers);
					
					//2016.08.05
					//제외수가 포함 비교하여 있으면 skip 함
					for (int l = 0; l < numbers.length; l++) {
						if (deleteTargetMap.containsKey(numbers[l])) {
							continue;
						}
					}
					
					numberList.add(numbers);
					dupCheckMap.put(strNumber, numbers);
				}
			}
		}
		
		return numberList;
	}
	
	/**
	 * @description <div id=description><b>숫자별 출현번호의 목록 구하기</b></div>
	 *              <div id=detail>각 숫자별 출현번호가 80% 이상 출현한 숫자의 범위를 구한다.</div>
	 * @param dataList
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getGroupByNumbersList(List<WinDataAnlyDto> winDataList) {
		
		/** 숫자별 출현번호 결과 목록 */
		ArrayList<ArrayList<Integer>> groupByNumbersList = new ArrayList<ArrayList<Integer>>();
		 
		for (int i = 0; i < winDataList.get(0).getNumbers().length; i++) {
			
			//번호별 개수 설정
			HashMap<Integer, Integer> countMap = new HashMap<Integer, Integer>();
			ArrayList<Integer> numberList = new ArrayList<Integer>();
			int[] count = new int[45];
			for (int j = 0; j < count.length; j++) {
				count[j] = 0;
			}
			
			for (int j = 0; j < winDataList.size(); j++) {
				WinDataAnlyDto data = winDataList.get(j);
				
				//숫자별 출현번호 설정
				if(!countMap.containsKey(data.getNumbers()[i])){
					countMap.put(data.getNumbers()[i], 1);
					numberList.add(data.getNumbers()[i]);
					count[data.getNumbers()[i]-1] = 1;
				}else{
					//업데이트 : 카운트 증가
					int cnt = count[data.getNumbers()[i]-1];
					countMap.put(data.getNumbers()[i], ++cnt);
					count[data.getNumbers()[i]-1] = cnt;
				}
				
			}
			
			ArrayList<Integer> resultNumbers = this.getGroupByNumberList(count, numberList, winDataList);
			//숫자별 출현번호 설정
			groupByNumbersList.add(resultNumbers);
		}
		
		return groupByNumbersList;
	}
	
	/**
	 * 번호 구간의 출현률이 80%이상인 번호목록을 구한다.
	 * 
	 * @param count
	 * @param numberList
	 * @param dataList
	 * @return
	 */
	public ArrayList<Integer> getGroupByNumberList(int[] count, ArrayList<Integer> numberList, List<WinDataAnlyDto> dataList) {
		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		
		int[][] countData = new int[numberList.size()][2];
		//초기값 설정
		for (int i = 0; i < numberList.size(); i++) {
			countData[i][0] = numberList.get(i);
			countData[i][1] = count[numberList.get(i)-1];
		}
		
		//내침차순 으로 정렬
		for (int i = 0; i < countData.length - 1; i++) {
			for(int j = i+1; j < countData.length; j++){
				if(countData[i][1] < countData[j][1]){
					//앞자리 숫자 변경
					int temp = countData[i][0];
					countData[i][0] = countData[j][0];
					countData[j][0] = temp;
					
					//카운트 변경
					int temp2 = countData[i][1];
					countData[i][1] = countData[j][1];
					countData[j][1] = temp2;
				}
			}
			
		}
		
		//진행도 출력
		int total = dataList.size();
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			double percent = LottoUtil.getPercent(countData[i][1], total);
//			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent) + "%");
			
			numbersList.add(countData[i][0]);
			sumPercent += percent;			
			
			//80%이상이면 종료
			if(sumPercent > 80.0){
//				log.info("총 출현률 : " + sumPercent + "%");
				break;
			}
		}
		
//		log.info("================================");
		
		return numbersList;
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값의 표준범위 구하기</b></div>
	 *              <div id=detail>각 번호간 차이값을 구해 80% 이상 출현한 차이값 범위를 구한다.</div>
	 * @param winDataList
	 * @return
	 */
	public ArrayList<HashMap<String, Integer>> getNumbersRangeList(List<WinDataAnlyDto> winDataList) {
		
		ArrayList<HashMap<String,Integer>> numbersRangeList = new ArrayList<HashMap<String,Integer>>();
		
		for (int i = 0; i < winDataList.get(0).getNumbers().length - 1 ; i++) {
			//번호간 최대 최소 구간 설정
			HashMap<Integer, Integer> rangeMap = new HashMap<Integer, Integer>();
			ArrayList<Integer> difList = new ArrayList<Integer>();
			int[] rangeCount = new int[45];
			for (int j = 0; j < rangeCount.length; j++) {
				rangeCount[j] = 0;
			}
			
			for (int j = 0; j < winDataList.size(); j++) {
				WinDataAnlyDto data = winDataList.get(j);
				
				//번호간 최대 최소 설정
				if(!rangeMap.containsKey(data.getDifNumbers()[i])){
					//신규 등록
					rangeMap.put(data.getDifNumbers()[i], 1);
					difList.add(data.getDifNumbers()[i]);
					rangeCount[data.getDifNumbers()[i]-1] = 1;
				}else{
					//업데이트 : 카운트 증가
					int cnt = rangeCount[data.getDifNumbers()[i]-1];
					rangeMap.put(data.getDifNumbers()[i], ++cnt);
					rangeCount[data.getDifNumbers()[i]-1] = cnt;
				}
				
			}
			
			// 번호값 차이값 목록을 최대최소에서 표준범위로 변경 2019.01.31
//			numbersRangeList.add(this.getMaxMinByRangeDif(rangeCount, difList, winDataList));
			numbersRangeList.add(this.getDifNumberMap(rangeCount, difList, winDataList));
			
		}
		
		return numbersRangeList;
	}
	
	/**
	 * 번호간 차이값의 최대 최소 범위를 구한다.
	 * 
	 * @param rangeCount
	 * @param difList
	 * @param winDataList
	 * @return
	 */
	public HashMap<String, Integer> getMaxMinByRangeDif(int[] rangeCount, ArrayList<Integer> difList, List<WinDataAnlyDto> winDataList) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int min = 0;
		int max = 0;
		
		
		int[][] countData = new int[difList.size()][2];
		//초기값 설정
		for (int i = 0; i < difList.size(); i++) {
			countData[i][0] = difList.get(i);
			countData[i][1] = rangeCount[difList.get(i)-1];
		}
		
		//내침차순 으로 정렬
		for (int i = 0; i < countData.length - 1; i++) {
			for(int j = i+1; j < countData.length; j++){
				if(countData[i][1] < countData[j][1]){
					//앞자리 숫자 변경
					int temp = countData[i][0];
					countData[i][0] = countData[j][0];
					countData[j][0] = temp;
					
					//카운트 변경
					int temp2 = countData[i][1];
					countData[i][1] = countData[j][1];
					countData[j][1] = temp2;
				}
			}
		}
		
		
		//진행도 출력
		int total = winDataList.size();
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			if(min == 0 || min > countData[i][0]){
				min = countData[i][0];
			}
			
			if(max == 0 || max < countData[i][0]){
				max = countData[i][0];
			}
			
			double percent = LottoUtil.getPercent(countData[i][1], total);
			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent) + "%");
			
			sumPercent += percent;			
			
			//80%이상이면 종료
			if(sumPercent > 80.0){
//				log.info("총 출현률 : " + sumPercent + "%");
				break;
			}
		}
				
//		log.info("================================");
		map.put("min", min);
		map.put("max", max);
		
		return map;
	}
	
	/**
	 * 번호간 차이값의 (일정기준) 표준 출현범위를 구한다.
	 * 
	 * 2019.01.31 테스트
	 * 80% -> 28% 일치
	 * 90% -> 59% 일치
	 * 95% -> 77% 일치
	 * 96% -> 82% 일치
	 * 97% -> 86% 일치 <--- 출현률 1.0% 이내 제외
	 * 97.5% -> 92% 일치
	 * 98% -> 92% 일치
	 * 100% -> 100% 일치
	 * 
	 * @param rangeCount
	 * @param difList
	 * @param winDataList
	 * @return
	 */
	public HashMap<String, Integer> getDifNumberMap(int[] rangeCount, ArrayList<Integer> difList, List<WinDataAnlyDto> winDataList) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		double MATCH_PER = 97;
		
		int[][] countData = new int[difList.size()][2];
		//초기값 설정
		for (int i = 0; i < difList.size(); i++) {
			countData[i][0] = difList.get(i);
			countData[i][1] = rangeCount[difList.get(i)-1];
		}
		
		//내침차순 으로 정렬
		for (int i = 0; i < countData.length - 1; i++) {
			for(int j = i+1; j < countData.length; j++){
				if(countData[i][1] < countData[j][1]){
					//앞자리 숫자 변경
					int temp = countData[i][0];
					countData[i][0] = countData[j][0];
					countData[j][0] = temp;
					
					//카운트 변경
					int temp2 = countData[i][1];
					countData[i][1] = countData[j][1];
					countData[j][1] = temp2;
				}
			}
		}
		
		
		//진행도 출력
		int total = winDataList.size();
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			
			map.put(String.valueOf(countData[i][0]), countData[i][1]);
			
			double percent = LottoUtil.getPercent(countData[i][1], total);
//			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent) + "%");
			
			sumPercent += percent;			
			
			if(sumPercent >= MATCH_PER){
//				log.info("총 출현률 : " + sumPercent + "%");
				break;
			}
		}
		
//		log.info("================================");
		
		return map;
	}
	
	/**
	 * 제외수를 포함하지 않는 회차합 숫자들
	 * 
	 * @param list
	 * @param deleteTargetList
	 * @return
	 */
	public List<Integer> getListWithExcludedNumber(List<Integer> list,
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
	 *  @description <div id=description><b>입력한 회차동안 출현하지 않은 번호 구하기</b></div>
     *              <div id=detail>입력한 회차동안 출현하지 않은 번호들을 구한다.</div>
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
	 * @description <div id=description><b>입력한 회차합 구하기</b></div>
     *              <div id=detail>입력한 지난 회전까지 출현했던 번호들을 구한다.</div>
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
	 * @description <div id=description><b>전회차 추출번호 비교</b></div>
	 *              <div id=detail>전회차의 추출번호를 비교하여 2개보다 많으면 예상번호로 선택하지 않는다.(true)</div>
	 * @param list
	 * @param numbers
	 * @param expectPatternMap 예측패턴
	 * @return true : 일치하지 않음, false : 일치함.
	 */
	public boolean existOfPrevCount(List<WinDataAnlyDto> list, ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo){		
		
		boolean pcDebug = false;
//		boolean pcDebug = true;
		
		boolean result = false;
		
		WinDataAnlyDto lastData = list.get(list.size()-1);
		int[] numbers = data.getNumbers();
		
		// 두 데이터를 비교하여 같은값의 개수를 조회한다.
		int equalNum = this.matchData(lastData.getNumbers(), numbers, 0);
		// 두 데이터를 비교하여 전회차 출현번호+1 과 같은 값의 개수를 조회한다.
		int nextNum = this.matchData(lastData.getNumbers(), numbers, 1);
		// 두 데이터를 비교하여 전회차 출현번호-1 과 같은 값의 개수를 조회한다.
		int prevNum = this.matchData(lastData.getNumbers(), numbers, -1);
		
		if(pcDebug){
			log.info("=== 전회차 추출번호 확인 ===");
			log.info("예상번호 패턴 : " + equalNum + "/" + nextNum + "/" + prevNum);
			log.info("예측패턴 : " + exptPtrnAnlyInfo.getSame_num_cnt() + "/" + exptPtrnAnlyInfo.getUp_1_cnt() + "/" + exptPtrnAnlyInfo.getDown_1_cnt());
			log.info("");
		}
		
		//전회차 추출번호가 예측번호 중 같은 번호의 개수를 예측 패턴의 개수와 비교한다.
		if(equalNum != exptPtrnAnlyInfo.getSame_num_cnt()){	
			return true;
		}
		
		//전회차의 추출번호가 예측번호보다 + 1 큰 숫자의 개수를 예측 패턴의 개수와 비교한다.
		if(nextNum != exptPtrnAnlyInfo.getUp_1_cnt()){
			return true;
		}
		
		//전회차의 추출번호보다 1 큰 수도 포함하지 않을 경우 전회차 당첨번호보다 - 1 작은 숫자가 있는지 비교한다.
		if(prevNum  != exptPtrnAnlyInfo.getDown_1_cnt()){
			return true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>두 데이터의 값을 비교한다.</b></div>
     *              <div id=detail>두 데이터를 비교하여 같은값의 개수를 조회한다.</div>
     * @param source 비교 번호배열
	 * @param target 대상 번호배열
	 * @param incDecValue 증감값
	 * @return
	 */
	public int matchData(int[] source, int[] target, int incDecValue){
		int cnt = 0;
		
		for(int targetIndex = 0 ; targetIndex < target.length ; targetIndex++){
			for(int sourceIndex = 0 ; sourceIndex < source.length ; sourceIndex++){
				if(target[targetIndex] == source[sourceIndex] + incDecValue){
					cnt++;
				}
			}
		}
		
		return cnt;
	}
	
	/**
	 * @description <div id=description><b>해당 고저비율에 포함되는지 여부확인</b></div>
	 *              <div id=detail>데이터의 고저비율을 계산하여 해당비율에 포함되는지 비교한다.</div>
	 * @param numbers
	 * @param ratioTitle
	 * @param expectPatternMap 예측패턴
	 * @return
	 */
	public boolean existLowHighRatio(String[] ratioTitle, ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo){
		
		boolean lhrDebug = false;
//		boolean lhrDebug = true;
		
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		String lowHigh = this.getLowHigh(numbers); 
		
		if(lhrDebug){
			System.out.println("=== 저고비율 확인 ===");
			System.out.println("예측번호 : " + lowHigh);
			System.out.println("예측패턴 : " + exptPtrnAnlyInfo.getLow_high());
			System.out.println("비교 : " + (lowHigh.equals(exptPtrnAnlyInfo.getLow_high())));
			System.out.println();
		}
		
		//예측번호의 고저비율과 예측 패턴의 고저비율이 같은지 비교한다.
		data.setLow_high(lowHigh);
		if(!lowHigh.equals(exptPtrnAnlyInfo.getLow_high())){
			return true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>고저 계산</b></div>
     *              <div id=detail>6개 번호의 낮고 높은 비율을 계산한다.</div>
     * @param data
	 * @return
	 */
	public String getLowHigh(Object data){
		int low = 0;
		int high = 0;
		
		int[] datas = null;
		
		if (data instanceof WinDataDto) {
			datas = LottoUtil.getNumbers((WinDataDto) data);
		} else if (data instanceof int[]) {
			datas = (int[]) data;
		}
		
		for(int num : datas){
			if(num < 23){
				low++;
			}else{
				high++;
			}
		}
		
		return low + ":" + high;
	}

	/**
	 * @description <div id=description><b>해당 홀짝비율에 포함되는지 여부확인</b></div>
	 *              <div id=detail>데이터의 홀짝비율을 계산하여 해당비율에 제외패턴에 포함되는지 비교한다.(로또9단#1)</div>
	 *              
	 * @param ratioTitle
	 * @param data
	 * @return true: 제외패턴 포함, false: 미포함
	 */
	public boolean existExcludeOddEvenRatio(String[] ratioTitle, ExDataDto data) {
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		String oddEven = this.getOddEven(numbers); 
		data.setOdd_even(oddEven);
		if(oddEven.equals(ratioTitle[0])
				|| oddEven.equals(ratioTitle[6])){
			return true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>해당 홀짝비율에 포함되는지 여부확인</b></div>
	 *              <div id=detail>데이터의 홀짝비율을 계산하여 해당비율에 포함되는지 비교한다.</div>
	 *              
	 * @param ratioTitle
	 * @param data
	 * @param exptPtrnAnlyInfo 예측패턴
	 * @return
	 */
	public boolean existOddEvenRatio(String[] ratioTitle, ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		boolean oerDebug = false;
		
		int[] numbers = data.getNumbers();
		String oddEven = this.getOddEven(numbers); 
		
		if(oerDebug){
			System.out.println("=== 홀짝비율 확인 ===");
			System.out.println("예측번호 : " + oddEven);
			System.out.println("예측패턴 : " + exptPtrnAnlyInfo.getOdd_even());
			System.out.println("비교 : " + (oddEven.equals(exptPtrnAnlyInfo.getOdd_even())));
			System.out.println();
		}
		
		data.setOdd_even(oddEven);
		if(!oddEven.equals(exptPtrnAnlyInfo.getOdd_even())){
			return true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>홀짝 계산</b></div>
     *              <div id=detail>6개 번호의 홀수,짝수 비율을 계산한다.</div>
     * @param data
	 * @return
	 */
	public String getOddEven(Object data){
		int odd = 0;
		int even = 0;
		
		int[] datas = null;
		
		if(data instanceof WinDataDto){
			datas = LottoUtil.getNumbers((WinDataDto)data);
		} else if(data instanceof ExDataDto){
				datas = LottoUtil.getNumbers((ExDataDto)data);
		}else if(data instanceof int[]){
			datas = (int[])data;
		}
		
		for(int num : datas){
			if(num%2 == 0){
				even++;		//짝수
			}else{
				odd++;		//홀수
			}
		}
		
		return odd + ":" + even;
	}

	/**
	 * @description <div id=description><b>총합범위 포함확인</b></div>
	 *              <div id=detail>데이터의 총합을 계산하여 표준총합범위에 포함하는지 확인한다.</div>
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existTotalRange(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean trDebug = false;

		boolean result = false;

		int[] datas = data.getNumbers();
		int total = this.getTotal(datas);

		/**
		 * 총합범위 
		 * 1: 100 이하 (최소 21) 
		 * 2: 101 ~ 150 
		 * 3: 151 ~ 200 
		 * 4: 201 이상 (최대 255)
		 */
		int lowTotal = 0;
		int highTotal = 0;
		if (exptPtrnAnlyInfo.getTotal_range_type() == 1) {
			lowTotal = 21;
			highTotal = 100;
		} else if (exptPtrnAnlyInfo.getTotal_range_type() == 2) {
			lowTotal = 101;
			highTotal = 150;
		} else if (exptPtrnAnlyInfo.getTotal_range_type() == 3) {
			lowTotal = 151;
			highTotal = 200;
		} else {
			lowTotal = 200;
			highTotal = 255;
		}

		if (trDebug) {
			System.out.println("=== 총합 범위 확인 ===");
			System.out.println("예측번호 : " + total);
			System.out.println("예측패턴 : " + lowTotal + "~" + highTotal);
			System.out.println();
		}

		data.setTotal(total);
		if (total >= lowTotal && total <= highTotal) {
		} else {
			return true;
		}

		return result;
	}
	
	/**
	 * @description <div id=description><b>총합 계산</b></div>
     *              <div id=detail>6개 번호의 총합을 계산한다.</div>
     * @param data
	 * @return
	 */
	public int getTotal(Object data){
		int total = 0;		
		
		int[] datas = null;
		if(data instanceof WinDataDto){
			datas = LottoUtil.getNumbers((WinDataDto)data);
		}else if(data instanceof int[]){
			datas = (int[])data;
		}
		
		for(int index = 0 ; index < datas.length ; index++){			
			total += datas[index];
		}
		
		return total;
	}

	/**
	 * @description <div id=description><b>연속된 수 확인</b></div>
	 *              <div id=detail>연속되는 수가 있는지 확인한다.</div>
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existConsecutivelyNumbers(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean cnDebug = false;
//		boolean cnDebug = true;
		
		boolean result = false;
		
		int[] numbers = data.getNumbers();		
		int cnt = 0;
		
		for(int index = 0 ; index < numbers.length - 1 ; index++){
			if( (numbers[index] + 1) == numbers[index+1] ){
				cnt++;
			}
		}
		
		if(cnDebug){
			System.out.println("=== 연속되는 수 확인 ===");
			System.out.println("예측번호 : " + cnt);
			System.out.println("예측패턴 : " + exptPtrnAnlyInfo.getC_num_cnt());
			System.out.println();
		}
		
		if(cnt != exptPtrnAnlyInfo.getC_num_cnt()){
			return true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>끝수합 범위 포함확인</b></div>
	 *              <div id=detail>데이터의 끝수합을 계산하여 범위에 포함하는지 확인한다.</div>
	 *              
	 * @param data
	 * @param lowEndNumber
	 * @param highEndNumber
	 * @return
	 */
	public boolean existSumEndNumberRange(ExDataDto data, int lowEndNumber, int highEndNumber) {
		boolean result = false;
		int sum_end_num = this.getSumEndNumber(data);
		
		data.setSum_end_num(sum_end_num);
		if(sum_end_num >= lowEndNumber && sum_end_num <= highEndNumber){
		}else{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>끝수합 구하기</b></div>
	 *              <div id=detail>끝수합을 구한다.</div>
	 * @param data
	 * @return
	 */
	public int getSumEndNumber(Object data){
		int sumEndNum = 0;
		int[] numbers = null;
		
		if (data instanceof WinDataDto) {
			numbers = LottoUtil.getNumbers((WinDataDto)data);
		} else if (data instanceof ExDataDto) {
			numbers = ((ExDataDto)data).getNumbers();
		}
		
		for(int index = 0 ; index < numbers.length ; index++){
			sumEndNum += numbers[index] % 10;
		}
		
		return sumEndNum;
	}

	/**
	 * @description <div id=description><b>그룹 내 포함개수 비교</b></div>
	 *              <div id=detail>데이터가 한 그룹 내 4개 이상 포함하는지 확인한다.</div>
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existGroup(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;	//존재여부
		ArrayList<Integer> zeroCntRange = exptPtrnAnlyInfo.getZeroCntRange();
		
		int[] numbers = data.getNumbers();
		int[] cnt = {0,0,0,0,0};	//각 자리의 포함개수
		
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
			cnt[mok] = cnt[mok] + 1;
		}
		
		//예측 패턴과 숫자 존재여부 확인
		//숫자가 나오지 않는 범위를 비교한다.
		//zeroCntRange 는 숫자가 나오지 않는 범위(index)+1.
		for (int i = 0; i < zeroCntRange.size(); i++) {
			if(cnt[zeroCntRange.get(i)-1] != 0){
				return true;
			}
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>끝자리가 같은 숫자의 개수 비교</b></div >
	 *              <div id=detail>데이터의 끝자리가 같은 수의 개수가 예측한 개수와 같은지 확인한다.</div >
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existEndNumberCount(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		int[] cnt = {0,0,0,0,0,0,0,0,0,0};	//각 자리의 포함개수(0~9)
		int maxCnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			int nmj = numbers[index] % 10;
			cnt[nmj] = cnt[nmj] + 1;
		}
		
		for(int index = 0 ; index < cnt.length ; index++){
			if(cnt[index] > maxCnt){
				maxCnt = cnt[index]; 
			}
		}
		
		//같은 끌자리 개수가 예측한 최대 끝자리 개수와 같지 않으면 무효처리
		if(maxCnt != exptPtrnAnlyInfo.getEnd_num_same_cnt()){
			result = true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>소수 포함 여부 확인</b></div >
	 *              <div id=detail>데이터 중 소수가 1개 이상 포함되어 있는지 확인한다.</div >
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existSotsu(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		int cnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			if(numbers[index] >= 2 && LottoUtil.getSotsu(numbers[index])){
				cnt++;
			}
		}
		
		//소수 개수가 예측패턴의 개수와 같지 않으면 무효처리
		if(cnt != exptPtrnAnlyInfo.getP_num_cnt()){
			result = true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>3의 배수 포함 여부 확인</b></div >
	 *              <div id=detail>데이터 중 3의 배수가 1 ~ 3개 포함되어 있는지 확인한다.</div >
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existNumberOf3(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		int numberOf3Cnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			if(numbers[index] % 3 == 0){
				numberOf3Cnt++;
			}
		}
		
		//3의 배수의 개수가 예측한 개수와 일치하지 않으면 무효처리
		if(numberOf3Cnt != exptPtrnAnlyInfo.getMulti_3_cnt()){
			result = true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>합성수 포함 여부 확인</b></div >
	 *              <div id=detail>데이터 중 합성수(소수 또는 3의 배수가 아닌 수)가 1 ~ 3개 포함되어 있는지 확인한다.</div >
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean existNumberOfNot3(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		
		int[] numbers = data.getNumbers();
		int[] hapsu = {1,4,8,10,14,16,20,22,25,26,28,32,34,35,38,40,44};
		int numberOfNot3Cnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			for(int i = 0 ; i < hapsu.length ; i++){
				if(numbers[index] == hapsu[i]){
					numberOfNot3Cnt++;
					break;
				}
			}
		}
		
		//합성수의 개수가 예측한 합성수의 개수와 일치하지 않으면 무효처리
		if(numberOfNot3Cnt != exptPtrnAnlyInfo.getComp_num_cnt()){
			result = true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>AC 포함 여부 확인</b></div >
	 *              <div id=detail>데이터 중 AC가 6 ~ 10 사이인지 확인한다.(범위는 표준범위)</div >
	 *              <div id=detail>동행복권으로 변경되어 min범위를 7에서 6으로 변경(로또9단) 2020.03.30</div >
	 *              
	 * @param data
	 * @return
	 */
	public boolean isContainAc(ExDataDto data) {
		boolean result = false;
		
		data.setAc(LottoUtil.getAc(data));
		
		int ac = data.getAc();
		
		// 표준범위로 체크
		if (6 <= ac && ac <= 10) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * @description <div id=description><b>AC 포함 여부 확인</b></div >
	 *              <div id=detail>데이터 중 AC가 6 ~ 10 사이인지 확인한다.(범위는 표준범위)</div >
	 *              <div id=detail>동행복권으로 변경되어 min범위를 7에서 6으로 변경(로또9단) 2020.03.30</div >
	 *              
	 * @param data
	 * @param exptPtrnAnlyInfo
	 * @return
	 */
	public boolean isContainAc(ExDataDto data, ExptPtrnAnlyDto exptPtrnAnlyInfo) {
		boolean result = false;
		
		data.setAc(LottoUtil.getAc(data));
		
		int ac = data.getAc();
		
		//AC가 예측패턴과 같지 않으면 무효처리
//		if(ac != exptPtrnAnlyInfo.getAc()){
//			result = true;
//		}
		
		// 표준범위로 체크
		if (6 <= ac && ac <= 10) {
			result = true;
		}
		
		return result;
	}

	/**
	 * @description <div id=description><b>궁합도 매치</b></div >
	 *              <div id=detail>예측 번호에 궁합도가 포함되어 있는지 확인한다.(4개 이상)</div >
	 *              
	 * @param data
	 * @param mcNumberMap
	 * @return
	 */
	public boolean isMcMatched(ExDataDto data, Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap) {
		boolean validation = false;
		
		int[] dataNumbers = data.getNumbers();
		int matchedCnt = 0;
		
		//각 번호별 궁합수가 있는지 확인
		for (int count = 0; count < dataNumbers.length; count++) {
			Map<String, ArrayList<Integer>> mcMap = mcNumberMap.get(dataNumbers[count]);
			ArrayList<Integer> mcList = mcMap.get("mc");
//			ArrayList<Integer> notMcList = mcMap.get("notMc");
			
			//2016.02.19 체크로직 변경
			for (int j = 0; j < dataNumbers.length; j++) {
				//같은번호는 skip
				if (dataNumbers[count] == dataNumbers[j]) {
					continue;
				}
				
				if (mcList.contains(dataNumbers[j])) {
					matchedCnt++;
				}
			}
			
			//궁합수 조회
//			for (int i = 0; i < mcList.size(); i++) {
//				boolean isExist = false;
//				//예측번호 모두 비교
//				for (int j = 0; j < dataNumbers.length; j++) {
//					if(dataNumbers[j] == mcList.get(i)){
//						//궁합수가 존재한다면
//						matchedCnt++;
//						isExist = true;
//						break;
//					}
//					
//				}
//				
//				if(isExist){
//					break;
//				}
//			}
//			
//			//불협수 체크하고 불협수가 존재한다면 재조합 실행
//			if(notMcList.size() != 0){
//				for (int i = 0; i < notMcList.size(); i++) {
//					//예측번호 모두 비교
//					for (int j = 0; j < dataNumbers.length; j++) {
//						if(dataNumbers[j] == notMcList.get(i)){
//							return true;
//						}
//						
//					}
//				}
//			}
			
		}

		//테스트 : 2013.11.07 궁합도 7개는 나오지 않음.
		/*
		 * 689회 분석기준
        	궁합수 개수 : 2 / 출현횟수 : 112
			궁합수 개수 : 3 / 출현횟수 : 98
			궁합수 개수 : 4 / 출현횟수 : 114
			궁합수 개수 : 5 / 출현횟수 : 99
			궁합수 개수 : 6 / 출현횟수 : 79
		 */
		if (validation) {
//			if(matchedCnt >= 4)
			if (matchedCnt < 2 || matchedCnt > 6) {
				System.out.println("궁합 매치 개수 : " + matchedCnt);
			}
		}
		// 매치 개수가 4개 미만일 경우 재조합
		// 매치 개수가 2 ~ 6이 아닐 경우 재조합. 2016.02.19
		if (matchedCnt < 2 || matchedCnt > 6) {
			return true;
		}
		return false;
	}

	/**
	 * @description <div id=description><b>번호간 차이값 체크</b></div >
	 *              <div id=detail>번호간 차이값이 평균범위 포함되어 있는지 체크한다.</div >
	 * 
	 * 표준범위에 포함되는 개수로 체크하도록 변경 2019.01.31
	 * 
	 * @param data
	 * @param numbersRangeList
	 * @return
	 */
	public boolean isContainRange(ExDataDto data, ArrayList<HashMap<String, Integer>> numbersRangeList) {
		int[] numbers = data.getDifNumbers();
		int containCnt = 0;
		int MATCH_CNT = 5;	//매치 개수
		
		for (int i = 0; i < numbers.length; i++) {
			HashMap<String, Integer> map = numbersRangeList.get(i);
			if (map.containsKey(String.valueOf(numbers[i]))) {
				containCnt++;
			}
		}
		
		if (containCnt < MATCH_CNT) {
			return false;
		}
		
		return true;
	}

	/**
	 * 숫자별 출현번호 체크
	 * 숫자별 평균출현번호인지 체크한다.
	 * 
	 * @description <div id=description><b>번호간 차이값 체크</b></div >
	 *              <div id=detail>번호간 차이값이 평균범위 포함되어 있는지 체크한다.</div >
	 *              
	 * @param data
	 * @param groupByNumbersList
	 * @return
	 */
	public boolean isContainGroup(ExDataDto data, ArrayList<ArrayList<Integer>> groupByNumbersList) {
		int[] numbers = data.getNumbers();

		for (int i = 0; i < numbers.length; i++) {
			boolean equal = false;
			ArrayList<Integer> group = groupByNumbersList.get(i);
			for (int j = 0; j < group.size(); j++) {
				// 번호가 그룹의 번호와 같으면 다음 번호를 비교한다.
				if (numbers[i] == group.get(j)) {
					equal = true;
					break;
				}
			}

			// 번호가 그룹에 포함되어 있지 않으면 예상번호로 선택하지 않는다.
			if (!equal) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 데이터 삽입하기
	 * 
	 * @param orderedEqualCntList 정렬된 일치개수 목록 
	 * @param equalCnt 일치개수
	 * @param orderedDataList 정렬된 데이터 목록
	 * @param data 예상데이터
	 */
	public void insertData(ArrayList<Integer> orderedEqualCntList, int equalCnt, List<ExDataDto> orderedDataList,
			ExDataDto data) {
		int length = orderedEqualCntList.size();

		for (int i = 0; i < length; i++) {
			if (orderedEqualCntList.get(i) < equalCnt) {
				// 기존 데이터가 새로운 일치개수보다 작으면
				// 그 자리에 새로운 일치개수 정보를 삽입한다.

				/** 이동할 일치개수 목록 */
				ArrayList<Integer> subEqualCntList = new ArrayList<Integer>();
				/** 이동할 데이터 목록 */
				ArrayList<ExDataDto> subDataList = new ArrayList<ExDataDto>();

				/** 기존 데이터 크기 */
				int listSize = orderedEqualCntList.size();

				// 기존 데이터를 subList에 저장
				for (int j = i; j < listSize; j++) {
					subEqualCntList.add(orderedEqualCntList.get(j));
					subDataList.add(orderedDataList.get(j));
				}

				// 기존 데이터 삭제 (뒤에서부터 삭제)
				for (int j = listSize - 1; j >= i; j--) {
					orderedEqualCntList.remove(j);
					orderedDataList.remove(j);
				}

				// 새로운 데이터 추가
				orderedEqualCntList.add(equalCnt);
				orderedDataList.add(data);

				// 기존 데이터 추가
				for (int j = 0; j < subEqualCntList.size(); j++) {
					orderedEqualCntList.add(subEqualCntList.get(j));
					orderedDataList.add(subDataList.get(j));
				}

				return;
			} else {
				if (i == (length - 1)) {
					orderedEqualCntList.add(equalCnt);
					orderedDataList.add(data);
				}
			}
		}
		
	}

	/**
	 * 전회차 예상번호 매칭결과
	 * 
	 * @param modelMap 
	 * @param lastData 마지막 당첨번호 정보
	 * @param exDataList 지난회 예상번호 목록
	 */
	public void getExDataResult(ModelMap modelMap, WinDataDto lastData, List<ExDataDto> exDataList) {
		//2016.01.26 총 결과
		int[] resultCnt = new int[]{0,0,0,0,0};
		
		for(ExDataDto exData : exDataList){
			
			String result = this.getWinResult(exData, lastData);
			if(!result.equals("낙첨!")){
				if ("1등".equals(result)) {
					resultCnt[0]++;
				} else if ("2등".equals(result)) {
					resultCnt[1]++;
				} else if ("3등".equals(result)) {
					resultCnt[2]++;
				} else if ("4등".equals(result)) {
					resultCnt[3]++;
				} else if ("5등".equals(result)) {
					resultCnt[4]++;
				}
				
			}
			
		}
	
		//최종 결산 내역 출력
		modelMap.addAttribute("win1Result", resultCnt[0]);
		modelMap.addAttribute("win2Result", resultCnt[1]);
		modelMap.addAttribute("win3Result", resultCnt[2]);
		modelMap.addAttribute("win4Result", resultCnt[3]);
		modelMap.addAttribute("win5Result", resultCnt[4]);
		modelMap.addAttribute("exDataListCnt", exDataList.size());
	}
	
	/**
	 * MY로또 매칭결과
	 * 
	 * @param modelMap 
	 * @param lastData 당첨번호 정보
	 * @param myDataList MY로 목록
	 */
	public void getMyDataResult(List<MyLottoSaveNumDto> myDataList, WinDataDto lastData) {
		for(MyLottoSaveNumDto myData : myDataList){
			String result = this.getWinResult(myData, lastData);
			myData.setWin_rslt(result);
		}
	}

	/**
	 * @description <div id=description><b>등수확인</b></div >
	 *              <div id=detail>예상데이터와 실제데이터를 비교하여 등수를 계산한다.</div >
	 *              
	 * 2020.04.15
	 * 2등 체크로직 개선
	 *              
	 * @param exData 예상번호 정보
	 * @param lastWinData 당첨번호 정보
	 * @return
	 */
	public String getWinResult(ExDataDto exData, WinDataDto lastWinData) {
		String result = "";

		int[] checkNumbers = LottoUtil.getNumbers(exData);
		int[] lastWinDataNumbers = LottoUtil.getNumbers(lastWinData);
		int bonusNumber = lastWinData.getBonus_num();
		
		int matchNumbers = 0;

		// 최근 당첨번호 비교 Map 설정
		Map<Integer, Integer> winNumberMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < lastWinDataNumbers.length; i++) {
			winNumberMap.put(lastWinDataNumbers[i], lastWinDataNumbers[i]);
		}
		
		String numbers = "";
		
		
		// 번호 비교
		Map<Integer, Integer> exNumberMap = new HashMap<Integer, Integer>();		
		for (int i = 0; i < checkNumbers.length; i++) {
			if (winNumberMap.containsKey(checkNumbers[i])) {
				matchNumbers++;
				exNumberMap.put(checkNumbers[i], 1);
			}
			
			if (i > 0) {
				numbers += ",";
			}
			numbers += checkNumbers[i];
		}
		
		if (matchNumbers == 6) {
			result = "1등";
		} else if (matchNumbers == 5) {			
			result = "3등";
			
			// 보너스 포함여부 확인
			if (!exNumberMap.containsKey(bonusNumber)
					&& winNumberMap.containsKey(bonusNumber)) {
				result = "2등";
			}
		
			if (numbers.indexOf(bonusNumber) > -1) {
				log.info(">>>>>>>>>>>>>>>>>>>>>> 2등 : " + numbers);
			}
			
		} else if (matchNumbers == 4) {
			result = "4등";
		} else if (matchNumbers == 3) {
			result = "5등";
		} else {
			result = "낙첨!";
		}

		return result;
	}
	
	/**
	 * @description <div id=description><b>등수확인</b></div >
	 *              <div id=detail>예상데이터와 실제데이터를 비교하여 등수를 계산한다.</div >
	 *              
	 * @param exData 예상번호 정보
	 * @param lastWinData 당첨번호 정보
	 * @return
	 */
	public String getWinResult(Object data, WinDataDto lastWinData) {
		String result = "";
		
		int[] lastNumbers = LottoUtil.getNumbersFromObj(data);
		int[] lastDataNumbers = LottoUtil.getNumbers(lastWinData);
		int bonusNumber = lastWinData.getBonus_num();
		
		int winNumbers = 0;
		List<Integer> list = new ArrayList<Integer>();
		
		for (int index = 0; index < lastNumbers.length; index++) {
			for (int i = 0; i < lastDataNumbers.length; i++) {
				if (lastNumbers[index] == lastDataNumbers[i]) {
					winNumbers++;
					list.add(lastDataNumbers[i]);
				}
			}
		}
		
		if (winNumbers == 6) {
			result = "1등";
		} else if (winNumbers == 5) {
			for (int index = 0; index < lastNumbers.length; index++) {
				if (lastNumbers[index] == bonusNumber) {
					result = "2등";
				} else {
					result = "3등";
				}
			}
		} else if (winNumbers == 4) {
			result = "4등";
		} else if (winNumbers == 3) {
			result = "5등";
		} else {
			result = "낙첨!";
		}
		
		return result;
	}

	/**
	 * 예상패턴 비교
	 * 
	 * @param exData 예상 데이터
	 * @param winDataList 전체 당첨번호 정보 목록
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 * @param endnumGroupCntMap 
	 * @param totalGroupCntMap 
	 * @return 대상가능여부 (true: 일치하지 않음, false: 일치함)
	 */
	public boolean compareExptPtrn(ExDataDto exData, List<WinDataAnlyDto> winDataList,
			ExptPtrnAnlyDto exptPtrnAnlyInfo, Map<Integer, Integer> totalGroupCntMap, Map<Integer, Integer> endnumGroupCntMap) {
		boolean isPossible = false;
		
		boolean result = false;
		boolean verification = false;
		boolean isEqual = false;
		int equalCnt = 0;
		/** 매칭예상 개수 */
		int EXPT_MATCH_CNT = 10;
		/** 첫 번째 번호 출현빈도 (Frequency of appearance) */
		int FOA_PER = 50;
		Map<Integer, Integer> num1AppearMapOver50 = this.getNumberMap(winDataList, 1, FOA_PER);
		
		// 표준 끝수합 범위 설정
		int[] lowHighEndNumData = this.getEndNumberBaseDistribution(winDataList);
		/** 최저끝수 */
		int lowEndNumber = lowHighEndNumData[0];
		/** 최고끝수 */
		int highEndNumber = lowHighEndNumData[1];
		
		/** 번호간 범위 결과 목록 */
		ArrayList<HashMap<String, Integer>> numbersRangeList = this.getNumbersRangeList(winDataList);
		/** 숫자별 출현번호 결과 목록 */
		ArrayList<ArrayList<Integer>> groupByNumbersList = this.getGroupByNumbersList(winDataList);
		/** 번호별 궁합/불협수 */
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = this.getMcNumberByAnly(winDataList);		
		
		/** 최대 출현횟수별 설정 */
		List<Map> appearNumbersList = this.getAppearNumbersList(winDataList);
		
		//1. 전회차 추출번호 예측 일치여부 비교
//		result = this.existOfPrevCount(winDataList, exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				System.out.println("1. 전회차 추출번호 예측 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//2. 저고 비율 비교
//		result = this.existLowHighRatio(LottoUtil.getRatioTitle(), exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("2. 저고 비율 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//3. 홀짝 비율 비교
//		result = this.existOddEvenRatio(LottoUtil.getRatioTitle(), exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("3. 홀짝 비율 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//4. 총합 비교
//		result = this.existTotalRange(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("4. 총합 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//5. 연속되는 수 비교
//		result = this.existConsecutivelyNumbers(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("5. 연속되는 수 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//6. 끝수합 비교
//		result = this.existSumEndNumberRange(exData, lowEndNumber, highEndNumber);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("6. 끝수합 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//7. 그룹 내 포함개수 비교
//		result = this.existGroup(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("7. 그룹 내 포함개수 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//8. 끝자리가 같은 수 비교
//		result = this.existEndNumberCount(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("8. 끝자리가 같은 수 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//9. 소수 1개이상 포함 비교
//		result = this.existSotsu(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("9. 소수 1개이상 포함 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//10. 3의 배수 포함 비교
//		result = this.existNumberOf3(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("10. 3의 배수 포함 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//11. 합성수 포함 비교 : 합성수란 소수와 3의 배수가 아닌 수
//		result = this.existNumberOfNot3(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("11. 합성수 포함 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
		//12. AC 비교(7 ~ 10)
//		result = this.isContainAc(exData, exptPtrnAnlyInfo);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("12. AC 비교 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		//13. 궁합도 매치
//		//2016.02.19
//		result = this.isMcMatched(exData, mcNumberMap);
//		if (verification && isEqual) {
//			if(!result) {
//				equalCnt++;	//일치함.
//				log.info("13. 궁합도 매치 : " + equalCnt);
//			}
//		} else {
//			if(!result) equalCnt++;	//일치함.
//		}
//		
//		/*
//		 * 14. 번호간 차이값 체크
//		 * 번호간 차이값이 평균범위 포함되어 있는지 체크한다.
//		 */
//		result = this.isContainRange(exData, numbersRangeList);
//		if (verification && isEqual) {
//			if(result) {
//				equalCnt++;	//일치함.
//				log.info("14. 번호간 차이값 체크 : OK - " + equalCnt);
//			}
//		} else {
//			if(result) equalCnt++;	//일치함.
//		}
//		
//		/*
//		 * 15. 숫자별 출현번호 체크
//		 * 숫자별 평균출현번호인지 체크한다.
//		 */
//		result = this.isContainGroup(exData, groupByNumbersList);
//		if (verification && isEqual) {
//			if(result) {
//				equalCnt++;	//일치함.
//				log.info("15. 숫자별 출현번호 체크 : OK - " + equalCnt);
//			}
//		} else {
//			if(result) equalCnt++;	//일치함.
//		}
//		
//		/*
//		 * 16. 첫번째 숫자 출현번호의 출현빈도 체크
//		 */
//		result = num1AppearMapOver50.containsKey(exData.getNum1());
//		if (verification && isEqual) {
//			if(result) {
//				equalCnt++;	//일치함.
//				log.info("16. 첫번째 숫자 출현번호의 출현빈도 체크 : OK - " + equalCnt);
//			}
//		} else {
//			if(result) equalCnt++;	//일치함.
//		}
//		
//		
//		log.info("예상패턴 일치 개수 : " + equalCnt);
//		if (equalCnt == EXPT_MATCH_CNT) {
//			isPossible = true;
//		}
		
		
		//1. 총합 범위 포함 비교
		if (!totalGroupCntMap.containsKey(exData.getTotal())) {
			log.info("총합 범위 미포함 제외 : " + exData.getTotal());
			return false;
		}
		
		//1-1. 총합 범위 포함 비교
		if (exData.getTotal() < 100
				|| 175 < exData.getTotal()) {
			log.info("총합 100미만, 175초과 제외");
			return false;
		}
		
		//2. 끝수합 범위 포함 비교
		if (!endnumGroupCntMap.containsKey(exData.getSum_end_num())) {
			log.info("끝수합 범위 미포함 제외 : " + exData.getSum_end_num());
			return false;
		}
				
		/*
		 * 3. 출현번호 매치여부로 예상번호 설정
		 * 
		 * 예상번호 NEW 추출 시 제외처리 함. 2020.04.04
		// 건수는 얼마 안됨.
		 */
//		result = this.matchAppearNumbers(exData, appearNumbersList);
//		if (!result) {
//			log.info("출현번호 매치여부로 예상번호 설정 제외");
//			String contents = "출현번호 매치여부로 예상번호 설정 제외";
//			sysmngService.insertExptNumNewVari(exData, contents);
//			return false;
//		}
		
		/*
		 * 4. 번호간 차이값 체크
		 * 번호간 차이값이 평균범위 포함되어 있는지 체크한다.
		 * 
		 * 예상번호 NEW 추출 시 제외처리 함. 2020.04.04
		 */		
//		result = this.isContainRange(exData, numbersRangeList);
//		if (!result) {
//			log.info("번호간 차이값 너무 큼 제외");
//			return false;
//		}
		
		//5. AC 비교(6 ~ 10)
		result = this.isContainAc(exData, exptPtrnAnlyInfo);
		if(!result) {
			log.info("AC 평균구간 미포함 제외");
			return false;
		}
		
		/*
		 * 6. 미출현구간대 설정
		 * 
		 * 844회 기준 일치율
		 * 0	92	  10.9%
		 * 1	444	  52.6%
		 * 2	279	  33.0%
		 * 3	29	  3.4%
		 * 
		 * 3구간 미출현은 제외처리함.
		 */
		result = this.checkZeroCntRange(exData);
		if (!result) {
			log.info("3구간 미출현은 제외");
			return false;
		}
		
		// 7. 한 구간 4개 출현 제외
		int[] containGroupCnt = this.getZeroCntRangeData(exData);
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] > 3) {
				log.info("한 구간 4개 출현 제외");
				return false;
			}
		}
		
		// 8. 홀짝 비율 제외 패턴 확인
		result = this.existExcludeOddEvenRatio(LottoUtil.getRatioTitle(), exData);
		if(result) {
			log.info("홀짝비율 제외패턴(0:6, 6:0) 포함 제외");
			return false;
		}
		
		// 9. 시작번호, 끝번호 범위 확인
		result = this.existStartEndNumber(exData);
		if(!result) {
			log.info("시작번호(1~14), 끝번호(31이상) 범위 미포함 제외");
			return false;
		}

		// 2020.04.04 TEST 종료
		// 2020.04.12 예상번호 분석 30조합 추출 시 활용하도록 주석 제거 -> 추출되지 않음.
		// 10. 앞줄 4줄 패턴 확인 (로또용지기준)
//		result = this.existFront4Cols(exData);
//		if(result) {
//			log.info("앞줄 4줄 패턴 범위 포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 11. 뒷줄 4줄 패턴 확인 (로또용지기준)
//		result = this.existBackend4Cols(exData);
//		if(result) {
//			log.info("뒷줄 4줄 패턴 범위 포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 12. 색상별 제외 범위(1~2, 5색상) 포함 확인
//		result = this.checkExcludeColorCount(exData);
//		if(result) {
//			log.info("색상별 제외 범위(1~2, 5색상) 포함 제외");
//			return false;
//		}
//				
//		// 13. 모서리영역 포함 확인 (로또용지기준)
//		result = this.existEdgeRange(exData);
//		if(!result) {
//			log.info("모서리영역 미포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 14. 연속 3줄패턴 포함 확인 (로또용지기준)
//		result = this.checkLinesRange(exData, 3);
//		if(result) {
//			log.info("연속 3줄패턴 포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 15. 연속 4줄패턴 포함 확인 (로또용지기준)
//		// TODO 적중율 패턴분석 필요
//		result = this.checkLinesRange(exData, 4);
//		if(result) {
//			log.info("연속 4줄패턴 포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 16. 3연번 제외
//		result = this.check3ConsecutivelyNumber(exData);
//		if(result) {
//			log.info("3연번 포함 제외");
//			return false;
//		}
//		
//		// 17. 좌우2줄 패턴 제외 (로또용지기준)
//		result = this.checkLeftRightLinesRange(exData);
//		if(result) {
//			log.info("좌우2줄 포함 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 18. 가로 6줄 패턴 1개씩 출현 제외 (로또용지기준)
//		result = this.checkRows6LinesRange(exData);
//		if(result) {
//			log.info("가로 6줄 패턴 1개씩 출현 제외 (로또용지기준)");
//			return false;
//		}
//		
//		// 19. 6번째 수는 42,43,44,45 포함 확인
//		// TODO 패턴분석을 통해 개선해야함.
//		result = this.checkFortyNumbers(exData);
//		if(!result) {
//			log.info("6번째 수는 42,43,44,45 미포함 제외");
//			return false;
//		}
//		
//		// 20. 1,2,4,5열에서만 출현 제외
//		result = this.check12And45Cols(exData);
//		if(result) {
//			log.info("1,2,4,5열에서만 출현 제외");
//			return false;
//		}
//		
//		// 21. 3,4,6,7열에서만 출현 제외
//		result = this.check34And67Cols(exData);
//		if(result) {
//			log.info("3,4,6,7열에서만 출현 제외");
//			return false;
//		}
//		
//		// 22. AC 6이상 포함 확인
//		result = this.isContainAc(exData);
//		if(!result) {
//			log.info("AC 6이상 미포함 제외");
//			return false;
//		}		
//		
//		// 23. 좌상 삼각패턴만 포함 제외
//		result = this.checkUpperLeftTriangle(exData);
//		if(!result) {
//			log.info("좌상 삼각패턴만 포함 제외");
//			return false;
//		}
//		
//		// 24. 좌하 삼각패턴만 포함 제외
//		result = this.checkLowerLeftTriangle(exData);
//		if(!result) {
//			log.info("좌하 삼각패턴만 포함 제외");
//			return false;
//		}
//		
//		// 25. 우상 삼각패턴만 포함 제외
//		result = this.checkUpperRightTriangle(exData);
//		if(!result) {
//			log.info("우상 삼각패턴만 포함 제외");
//			return false;
//		}
//		
//		// 26. 우하 삼각패턴만 포함 제외
//		result = this.checkLowerRightTriangle(exData);
//		if(!result) {
//			log.info("우하 삼각패턴만 포함 제외");
//			return false;
//		}
//		
//		// 27. 최근 10회 이내 포함개수 부적합 제외
//		result = this.checkLast10WinDatas(exData, winDataList);
//		if(!result) {
//			log.info("최근 10회 이내 포함개수 부적합 제외");
//			return false;
//		}
//		
//		// 28. 40회 이내 6번 이상 출현수 제외
//		result = this.checkLast40MoreThan6Appear(exData, winDataList);
//		if(result) {
//			log.info("40회 이내 6번 이상 출현수 제외");
//			return false;
//		}
		
		
		return true;
	}
	
	/**
	 * 예상패턴 비교 NEW
	 * 
	 * 2020.04.12 수정
	 * 로또9단의 기본 제외 분석기법으로 조합을 비교하도록 처리.
	 * 
	 * @param exData 예상 데이터
	 * @param winDataList 전체 당첨번호 정보 목록
	 * @return 대상가능여부 (true: 일치하지 않음, false: 일치함)
	 */
	public boolean compareExptPtrnNew(ExDataDto exData, List<WinDataAnlyDto> winDataList) {

		boolean result = false;
		
		// 1-1. 홀짝 비율 제외 패턴 확인
		result = this.existExcludeOddEvenRatio(LottoUtil.getRatioTitle(), exData);
		if(result) {
			String comments = "홀짝비율 제외패턴(0:6, 6:0) 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 1-2. 총합 범위 포함 비교
		if (exData.getTotal() < 100
				|| 175 < exData.getTotal()) {
			String comments = "총합 100미만, 175초과 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		// 2-1. 시작번호, 끝번호 범위 확인
		result = this.existStartEndNumber(exData);
		if(!result) {
			String comments = "시작번호(1~14), 끝번호(31이상) 범위 미포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}

		// 2-2. 앞줄 4줄 패턴 확인 (로또용지기준)
		result = this.existFront4Cols(exData);
		if(result) {
			String comments = "앞줄 4줄 패턴 범위 포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 2-3. 뒷줄 4줄 패턴 확인 (로또용지기준)
		result = this.existBackend4Cols(exData);
		if(result) {
			String comments = "뒷줄 4줄 패턴 범위 포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		/*
		 * 3-1. 색상별 제외 범위(1~2, 5색상) 포함 확인
		 *      보통 1구간 멸한다. (모든구간 포함 제외)
		 */
		result = this.checkExcludeColorCount(exData);
		if(result) {
			String comments = "색상별 제외 범위(1~2, 5색상) 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 3-2. 모서리영역 포함 확인 (로또용지기준)
		result = this.existEdgeRange(exData);
		if(!result) {
			String comments = "모서리영역 (1~4개) 미포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		// 4-1. 연속 3줄패턴 포함 확인 (로또용지기준)
		result = this.checkLinesRange(exData, 3);
		if(result) {
			String comments = "연속 3줄패턴 포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 4-2. 연속 4줄패턴 포함 확인 (로또용지기준)
		// TODO 적중율 패턴분석 필요
		result = this.checkLinesRange(exData, 4);
		if(result) {
			String comments = "연속 4줄패턴 포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		// 5-1. 3연번 포함 제외
		result = this.check3ConsecutivelyNumber(exData);
		if(result) {
			String comments = "3연번 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 5-2. 좌우2줄 패턴 제외 (로또용지기준)
		result = this.checkLeftRightLinesRange(exData);
		if(result) {
			String comments = "좌우2줄 포함 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
			
		
		// 6-1. 가로 6줄 패턴 1개씩 출현 제외 (로또용지기준)
		result = this.checkRows6LinesRange(exData);
		if(result) {
			String comments = "가로 6줄 패턴 1개씩 출현 제외 (로또용지기준)";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
//		// 6-2. 6번째 수는 42,43,44,45 포함 확인
//		// TODO 패턴분석을 통해 개선해야함. <<<<<<< 패턴분석으로 적용하도록 해야함. 2020.04.12
//		result = this.checkFortyNumbers(exData);
//		if(!result) {
//			String comments = "6번째 수는 40,41,42,43,44,45 미포함 제외";
//			log.info(comments);
//			sysmngService.insertExptNumNewVari(exData, comments);
//			return false;
//		}
		
		
		// 7-1. 1,2,4,5열에서만 출현 제외
		result = this.check12And45Cols(exData);
		if(result) {
			String comments = "1,2,4,5열에서만 출현 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 7-2. 3,4,6,7열에서만 출현 제외
		result = this.check34And67Cols(exData);
		if(result) {
			String comments = "3,4,6,7열에서만 출현 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		// 8. AC 6이상 포함 확인
		// https://www.youtube.com/watch?v=Mj-CRAStT50
		result = this.isContainAc(exData);
		if(!result) {
			String comments = "AC 6이상 미포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		
		// 9-1. 좌상 삼각패턴만 포함 제외
		result = this.checkUpperLeftTriangle(exData);
		if(result) {
			String comments = "좌상 삼각패턴만 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 9-2. 좌하 삼각패턴만 포함 제외
		result = this.checkLowerLeftTriangle(exData);
		if(result) {
			String comments = "좌하 삼각패턴만 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 9-3. 우상 삼각패턴만 포함 제외
		result = this.checkUpperRightTriangle(exData);
		if(result) {
			String comments = "우상 삼각패턴만 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
		
		// 9-4. 우하 삼각패턴만 포함 제외
		result = this.checkLowerRightTriangle(exData);
		if(result) {
			String comments = "우하 삼각패턴만 포함 제외";
			log.info(comments);
			sysmngService.insertExptNumNewVari(exData, comments);
			return false;
		}
				
		return true;
	}
	
	/**
	 * 40회 이내 6번 이상 출현수 제외
	 * 2020.04.04
	 * 
	 * @param exData
	 * @param winDataList
	 * @return
	 */
	private boolean checkLast40MoreThan6Appear(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = false;
		
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 출현회수 체크할 배열 선언
		int[] appearCnt = new int[45];
		for (int i = 0; i < appearCnt.length; i++) {
			appearCnt[i] = 0;
		}
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		
		
		// 최근회차부터 설정
		int startIndex = winDataList.size() - 1;
		int lastCheckCount = 40;
		for (int i = startIndex; i > winDataList.size() - lastCheckCount; i--) {
			WinDataAnlyDto winData = winDataList.get(i);
			int[] checkNumbers = LottoUtil.getNumbers(winData);
			for (int j = 0; j < checkNumbers.length; j++) {
				int number = checkNumbers[j];
				int appearIndex = number - 1;
				appearCnt[appearIndex] = appearCnt[appearIndex] + 1;
				
				// 6번 이상 출현수를 체크대상으로 설정
				if (appearCnt[appearIndex] >= 6) {
					checkMap.put(number, appearCnt[appearIndex]);
				}
			}
		}
		
		// 예상회차 조합번호 중 체크대상 포함 체크
		for (int i = 0; i < numbers.length; i++) {
			if (checkMap.containsKey(numbers[i])) {
				check = true;
				break;
			}
		}
		
		return check;
	}

	/**
	 * 최근 10회 이내 포함개수 부적합 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @param winDataList
	 * @return
	 */
	private boolean checkLast10WinDatas(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		
		int check1Cnt = 0;
		int check2Cnt = 0;
		
		// 1. 1,2구, 5,6구 번호들 중 3~4개 포함 확인
		Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
		int lastCheckCount = 10;
		int startIndex = winDataList.size() - 1;
		for (int i = startIndex; i > winDataList.size() - lastCheckCount; i--) {
			WinDataAnlyDto winData = winDataList.get(i);
			int[] checkNumbers = LottoUtil.getNumbers(winData);
			for (int j = 0; j < checkNumbers.length; j++) {
				// 2. 3,4구는 최근 5회까지만 포함 (4개 이상 포함 확인)
				if (i > winDataList.size() - 5) {
					if (!map2.containsKey(checkNumbers[j])) {
						map2.put(checkNumbers[j], 1);	// 중복방지 입력
					}	
				} else {
					// 3,4구 체크 제외
					if (j == 2 || j == 3) {
						continue;
					}
				}
				
				if (!map1.containsKey(checkNumbers[j])) {
					map1.put(checkNumbers[j], 1);	// 중복방지 입력
				}
				if (!map2.containsKey(checkNumbers[j])) {
					map2.put(checkNumbers[j], 1);	// 중복방지 입력
				}
			}
		}
		
		// 포함개수 확인
		for (int i = 0; i < numbers.length; i++) {
			if (map1.containsKey(numbers[i])) {
				check1Cnt++;
			}
			if (map2.containsKey(numbers[i])) {
				check2Cnt++;
			}
		}
		
		if (check1Cnt == 3 || check1Cnt == 4) {
			log.info("\t최근 10회 이내 1,2구, 5,6구 번호들 중 3~4개 포함 확인");
			
			if (check2Cnt >= 4) {
				log.info("\t최근 10회 이내 1,2구, 5,6구 번호들 과 3,4구는 최근 5회까지만 4개 이상 포함 확인");
				check = true;
			} else {
				log.info("\t최근 10회 이내 1,2구, 5,6구 번호들 과 3,4구는 최근 5회까지만 4개 이상 미포함");
			}
		} else {
			log.info("\t최근 10회 이내 1,2구, 5,6구 번호들 중 3~4개 미포함");
		}
		
		return check;
	}

	/**
	 * 우하 삼각패턴만 포함 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkLowerRightTriangle(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			int startColIndex = arrNumbers[row].length - row - 1;
			for (int col = 0 + startColIndex; col < arrNumbers[row].length; col++) {
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 우상 삼각패턴만 포함 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkUpperRightTriangle(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			int startColIndex = arrNumbers[row].length - (arrNumbers[row].length - row);
			for (int col = 0 + startColIndex; col < arrNumbers[row].length; col++) {
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 좌하 삼각패턴만 포함 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkLowerLeftTriangle(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			int endColIndex = arrNumbers[row].length - row;
			for (int col = 0; col < arrNumbers[row].length - endColIndex + 1; col++) {
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 좌상 삼각패턴만 포함 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkUpperLeftTriangle(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length - row; col++) {
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 3,4,6,7열에서만 출현 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean check34And67Cols(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;		
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length; col++) {
				// 1,2,5열 체크 체크 제외
				if (0 == col || 1 == col || 4 == col) {
					continue;
				}
				
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 1,2,4,5열에서만 출현 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean check12And45Cols(ExDataDto exData) {
		boolean check = false;

		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;		
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length; col++) {
				// 3,6,7열 체크 제외
				if (2 == col || 5 == col || 6 == col) {
					continue;
				}
				
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 6번째 수는 42,43,44,45 포함 확인
	 * 
	 * 40, 41 추가. 2020.04.05
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkFortyNumbers(ExDataDto exData) {
		boolean check = false;
		
		if (42 <= exData.getNum6() && exData.getNum6() <= 45) {
			// 포함됨
			check = true;
		}
		
		return check;
	}

	/**
	 * 가로 6줄 패턴 1개씩 출현 제외 (로또용지기준)
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkRows6LinesRange(ExDataDto exData) {
		boolean check = false;
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		// 체크할 대상 row 수
		int checkLineCnt = 6;
		// 체크할 row count
		int[] checkLineArray = {0,0,0,0,0,0};
		// 체크할 row 인덱스
		int checkRowIndex = 0;
		
		// 반복회수
		int repeatCnt = 7 - (checkLineCnt - 1);
		// 체크한도 라인수
		int limitCheckLine = arrNumbers.length - checkLineCnt;

		// 가로패턴 체크
		int checkCnt = 0;
		for (int i = 0; i < repeatCnt; i++) {
			// 체크수 초기화
			checkCnt = 0;
			checkRowIndex = 0;
			for (int j = 0; j < checkLineArray.length; j++) {
				checkLineArray[j] = 0;
			}
			
			for (int row = 0 + i; row < arrNumbers.length - (limitCheckLine - i); row++) {
				for (int col = 0; col < arrNumbers[row].length; col++) {
					if (arrNumbers[row][col] > 0) {
						// 체크할 row count를 1로 설정
						checkLineArray[checkRowIndex] = 1;
					}
				}
				// 1~7열 체크 후 체크할 row수 인덱스 증가
				checkRowIndex++;
			}

			// 결과체크
			for (int j = 0; j < checkLineArray.length; j++) {
				checkCnt += checkLineArray[j];
			}
			
			if (checkCnt == 6) {
				break;
			}
		}

		// 결과체크
		if (checkCnt == 6) {
			// 가로 연속 라인 패턴 포함
			log.info("\t가로 " + checkLineCnt + "줄패턴 각 1줄 포함");
			check = true;
		}
		
		return check;
	}

	/**
	 * 좌우2줄 패턴 제외 (1,2,6,7열)
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkLeftRightLinesRange(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;		
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length; col++) {
				// 3,4,5열 체크 제외
				if (2 <= col && col <= 4) {
					continue;
				}
				
				if (map.containsKey(arrNumbers[row][col])) {
					checkCnt++;
				}
			}
			
			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}
		
		// 결과체크
		if (checkCnt == 6) {
			check = true;
		}
		
		
		return check;
	}

	/**
	 * 3연번 제외
	 * 2020.04.01
	 * 
	 * @param exData
	 * @return
	 */
	private boolean check3ConsecutivelyNumber(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		for (int i = 0; i < numbers.length - 2; i++) {
			int firtNumber = numbers[i];
			int secondNumber = numbers[i+1];
			int thirdNumber = numbers[i+2];
			
			if (secondNumber - firtNumber == 1
					&& thirdNumber - secondNumber == 1) {
				check = true;
				break;
			}
		}
		
		return check;
	}

	/**
	 * 연속 라인 패턴 포함 확인 (로또용지기준)
	 * 2020.04.01
	 * 
	 * @param exData
	 * @param checkLineCnt 체크할 라인수
	 * @return
	 */
	private boolean checkLinesRange(ExDataDto exData, int checkLineCnt) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i],1);
		}
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		// 반복회수
		int repeatCnt = 7 - (checkLineCnt - 1);
		// 체크한도 라인수
		int limitCheckLine = arrNumbers.length - checkLineCnt;

		// 가로패턴 체크
		int checkCnt = 0;
		for (int i = 0; i < repeatCnt; i++) {
			// 체크수 초기화
			checkCnt = 0;

			for (int row = 0 + i; row < arrNumbers.length - (limitCheckLine - i); row++) {
				for (int col = 0; col < arrNumbers[row].length; col++) {
					if (map.containsKey(arrNumbers[row][col])) {
						checkCnt++;
					}
				}
			}

			// 결과체크
			if (checkCnt == 6) {
				break;
			}
		}

		// 가로줄 결과체크
		if (checkCnt == 6) {
			// 가로 연속 라인 패턴 포함
			log.info("\t가로 " + checkLineCnt + "줄패턴 포함");
			check = true;
		} else {
			// 세로패턴 체크
			for (int i = 0; i < repeatCnt; i++) {
				// 체크수 초기화
				checkCnt = 0;

				for (int row = 0; row < arrNumbers.length; row++) {
					for (int col = 0 + i; col < arrNumbers[row].length - (limitCheckLine - i); col++) {
						if (map.containsKey(arrNumbers[row][col])) {
							checkCnt++;
						}
					}
				}

				// 결과체크
				if (checkCnt == 6) {
					break;
				}
			}
			
			// 세로줄 결과체크
			if (checkCnt == 6) {
				// 세로 연속 라인 패턴 포함
				log.info("\t세로 " + checkLineCnt + "줄패턴 포함");
				check = true;
			}
		}
		
		return check;
	}

	/**
	 * 모서리영역 포함 확인 (로또용지기준)
	 * 1~4개 포함 확인 (적당다는 의견 - 로또9단)
	 * 
	 * TODO 적중율 패턴분석 필요
	 * 
	 * 2020.03.31
	 * 
	 * @param exData
	 * @return
	 */
	private boolean existEdgeRange(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 모서리패턴 check Map 설정
		int[] checkNumbers = {1,2,8,9,6,7,13,14,29,30,36,37,34,35,41,42,43,44,45};
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < checkNumbers.length; i++) {
			map.put(checkNumbers[i],1);
		}
		
		int checkCnt = 0;
		for (int i = 0; i < numbers.length; i++) {
			if (map.containsKey(numbers[i])) {
				checkCnt++;
			}
		}
		
		if (1 <= checkCnt && checkCnt <= 4) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 색상별 제외 범위(1~2, 5색상) 포함 확인
	 * 2020.03.31
	 * 
	 * @param exData
	 * @return true: 제외대상, false: 통과
	 */
	private boolean checkExcludeColorCount(ExDataDto exData) {
		boolean check = false;
		
		int[] containGroupCnt = LottoUtil.getArrayFromColor(exData);
		
		int checkCnt = 0;
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] > 0) {
				checkCnt++;
			}
		}
		
		// TODO 전체 패턴을 분석하여 적중률로 개선이 필요함.
		// 3~4개 색상으로 선택된 조합으로 선택되도록 설정
		if (checkCnt == 1 || checkCnt == 2 || checkCnt == 5) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 뒷줄 4줄 패턴 확인 (로또용지기준)
	 * 2020.03.31
	 * 
	 * @param exData
	 * @return
	 */
	private boolean existBackend4Cols(ExDataDto exData) {
		boolean check = false;
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length; col++) {
				// 뒤 4줄 확인
				if (2 < col) {
					if (arrNumbers[row][col] > 0) {
						checkCnt++;
					}
				}
			}
			
			if (checkCnt == 6) {
				break;
			}
		}
		
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}
	
	/**
	 * 앞줄 4줄 패턴 확인 (로또용지기준)
	 * 2020.03.31
	 * 
	 * @param exData
	 * @return
	 */
	private boolean existFront4Cols(ExDataDto exData) {
		boolean check = false;
		
		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		int checkCnt = 0;
		for (int row = 0; row < arrNumbers.length; row++) {
			for (int col = 0; col < arrNumbers[row].length; col++) {
				// 앞 4줄 확인
				if (col < 4) {
					if (arrNumbers[row][col] > 0) {
						checkCnt++;
					}
				} else {
					break;
				}
			}
			
			if (checkCnt == 6) {
				break;
			}
		}
		
		if (checkCnt == 6) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 시작번호(1~14), 끝번호(31이상) 범위 확인 (로또9단)
	 * 2020.03.30
	 * 
	 * @param exData
	 * @return true: 일치, false: 불일치
	 */
	private boolean existStartEndNumber(ExDataDto exData) {
		boolean check = false;
		
		int[] numbers = LottoUtil.getNumbers(exData); 
		if ((1 <= numbers[0] && numbers[0] <= 14)
				&& numbers[5] >= 31) {
			check = true;
		}
		return check;
	}

	/**
	 * 임시 예외처리
	 * 2019.03.08
	 * 
	 * @param exData
	 * @param winDataList 
	 * @return
	 */
	private boolean checkCustomException(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		// 미출구간 체크
		// 850회 체크하지 않음. 2019.03.15
		int[] containGroupCnt = this.getZeroCntRangeData(exData);
//		if (containGroupCnt[4] != 0) {
//			return false;	
//		}
//		if (containGroupCnt[1] == 0) {
//			return false;	
//		}
		
		
		// 이전번호 출현 체크
		WinDataAnlyDto lastWinData = winDataList.get(winDataList.size()-1);
		int[] exDataNumbers = LottoUtil.getNumbers(exData);
		int[] lastNumbers = LottoUtil.getNumbers(lastWinData);
		int equalCnt = 0;
		for (int i = 0; i < exDataNumbers.length; i++) {
			for (int j = 0; j < lastNumbers.length; j++) {
				if (exDataNumbers[i] == lastNumbers[j]) {
					equalCnt++;
				}
			}	
		}		
		if (equalCnt != 1) {
			// 이전 번호가 1개 없으면 제외
			logger.debug("이전 번호가 1개 없으면 제외");
			return false;
		}
		
		// 저고비율 체크
		String lowHigh = exData.getLow_high();
		if (!"2:4".equals(lowHigh)
				&& !"3:3".equals(lowHigh)
				&& !"4:2".equals(lowHigh)
				) {
			return false;
		}
		
		// 홀짝비율 체크
		String oddEven = exData.getOdd_even();
		if (!"2:4".equals(oddEven)
				&& !"3:3".equals(oddEven)
				&& !"4:2".equals(oddEven)
				) {
			return false;
		}
		
		return true;
	}

	/**
	 * 미출현구간 수 체크
	 * 2019.03.08
	 * 
	 * @param exData
	 * @return
	 */
	private boolean checkZeroCntRange(ExDataDto exData) {
		int[] containGroupCnt = this.getZeroCntRangeData(exData);
		int zeroCnt = 0;
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] == 0) {
				zeroCnt++;
			}
		}
		if (zeroCnt >= 3) {
			return false;
		}
		
		return true;
	}

	/**
	 * 예상패턴 일치건수
	 * 
	 * @param exData 예상 데이터
	 * @param winDataList 전체 당첨번호 정보 목록
	 * @param exptPtrnAnlyInfo 예상패턴분석정보
	 * @return 대상가능여부 (true: 일치하지 않음, false: 일치함)
	 */
	public int getExptPtrnMatchCnt(ExDataDto exData, 
								   List<WinDataAnlyDto> winDataList,
								   ExptPtrnAnlyDto exptPtrnAnlyInfo,
								   int[] lowHighEndNumData,
								   ArrayList<HashMap<String, Integer>> numbersRangeList,
								   ArrayList<ArrayList<Integer>> groupByNumbersList,
								   Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap
								   ) {
//		boolean isPossible = false;
		
		boolean result = false;
		boolean verification = false;
		boolean isEqual = false;
		int equalCnt = 0;
		/** 매칭예상 개수 */
//		int EXPT_MATCH_CNT = 10;
		/** 첫 번째 번호 출현빈도 (Frequency of appearance) */
//		int FOA_PER = 83;
//		Map<Integer, Integer> num1AppearMapOver50 = this.getNumberMap(winDataList, 1, FOA_PER);
		
		// 표준 끝수합 범위 설정
//		int[] lowHighEndNumData = this.getEndNumberBaseDistribution(winDataList);
		/** 최저끝수 */
		int lowEndNumber = lowHighEndNumData[0];
		/** 최고끝수 */
		int highEndNumber = lowHighEndNumData[1];
		
		/** 번호간 범위 결과 목록 */
//		ArrayList<HashMap<String, Integer>> numbersRangeList = this.getNumbersRangeList(winDataList);
		/** 숫자별 출현번호 결과 목록 */
//		ArrayList<ArrayList<Integer>> groupByNumbersList = this.getGroupByNumbersList(winDataList);
		/** 번호별 궁합/불협수 */
//		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = this.getMcNumberByAnly(winDataList);		
		
		/** 최대 출현횟수별 설정 */
//		List<Map> appearNumbersList = this.getAppearNumbersList(winDataList);
		
		//1. 전회차 추출번호 예측 일치여부 비교
		result = this.existOfPrevCount(winDataList, exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				System.out.println("1. 전회차 추출번호 예측 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//2. 저고 비율 비교
		result = this.existLowHighRatio(LottoUtil.getRatioTitle(), exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("2. 저고 비율 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//3. 홀짝 비율 비교
		result = this.existOddEvenRatio(LottoUtil.getRatioTitle(), exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("3. 홀짝 비율 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//4. 총합 비교
		result = this.existTotalRange(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("4. 총합 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//5. 연속되는 수 비교
		result = this.existConsecutivelyNumbers(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("5. 연속되는 수 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//6. 끝수합 비교
		result = this.existSumEndNumberRange(exData, lowEndNumber, highEndNumber);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("6. 끝수합 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//7. 그룹 내 포함개수 비교
		result = this.existGroup(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("7. 그룹 내 포함개수 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//8. 끝자리가 같은 수 비교
		result = this.existEndNumberCount(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("8. 끝자리가 같은 수 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//9. 소수 1개이상 포함 비교
		result = this.existSotsu(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("9. 소수 1개이상 포함 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//10. 3의 배수 포함 비교
		result = this.existNumberOf3(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("10. 3의 배수 포함 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//11. 합성수 포함 비교 : 합성수란 소수와 3의 배수가 아닌 수
		result = this.existNumberOfNot3(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("11. 합성수 포함 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		//12. AC 비교(7 ~ 10)
		result = this.isContainAc(exData, exptPtrnAnlyInfo);
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
		result = this.isMcMatched(exData, mcNumberMap);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("13. 궁합도 매치 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
		}
		
		/*
		 * 14. 번호간 차이값 체크
		 * 번호간 차이값이 평균범위 포함되어 있는지 체크한다.
		 */
		result = this.isContainRange(exData, numbersRangeList);
		if (verification && isEqual) {
			if(result) {
				equalCnt++;	//일치함.
				log.info("14. 번호간 차이값 체크 : OK - " + equalCnt);
			}
		} else {
			if(result) equalCnt++;	//일치함.
		}
		
		/*
		 * 15. 숫자별 출현번호 체크
		 * 숫자별 평균출현번호인지 체크한다.
		 */
		result = this.isContainGroup(exData, groupByNumbersList);
		if (verification && isEqual) {
			if(result) {
				equalCnt++;	//일치함.
				log.info("15. 숫자별 출현번호 체크 : OK - " + equalCnt);
			}
		} else {
			if(result) equalCnt++;	//일치함.
		}
		
		/*
		 * 16. 첫번째 숫자 출현번호의 출현빈도 체크
		 */
//		result = num1AppearMapOver50.containsKey(exData.getNum1());
//		if (verification && isEqual) {
//			if(result) {
//				equalCnt++;	//일치함.
//				log.info("16. 첫번째 숫자 출현번호의 출현빈도 체크 : OK - " + equalCnt);
//			}
//		} else {
//			if(result) equalCnt++;	//일치함.
//		}
		
		
		log.info("예상패턴 일치 개수 : " + equalCnt);
//		if (equalCnt == EXPT_MATCH_CNT) {
//			isPossible = true;
//		}
		
		// TODO 일치개수는 좀 더 분석 후 적용해야함.
		if (result) {
			
			// 출현번호 매치여부로 예상번호 설정
//			isPossible = this.matchAppearNumbers(exData, appearNumbersList);
			
		}
		
		return equalCnt;
	}

	/**
	 * 출현번호 매치여부
	 * 
	 * @param exData 예상데이터
	 * @param appearNumbersList 출현번호 제한 목록
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean matchAppearNumbers(ExDataDto exData, List<Map> appearNumbersList) {
		
		boolean result = false;
		int MATCH_CNT = 5;	//매치 개수
		int containCnt = 0;
		
		if (appearNumbersList.get(0).containsKey(exData.getNum1())) {
			containCnt++;
		}
		if (appearNumbersList.get(1).containsKey(exData.getNum2())) {
			containCnt++;
		}
		if (appearNumbersList.get(2).containsKey(exData.getNum3())) {
			containCnt++;
		}
		if (appearNumbersList.get(3).containsKey(exData.getNum4())) {
			containCnt++;
		}
		if (appearNumbersList.get(4).containsKey(exData.getNum5())) {
			containCnt++;
		}
		if (appearNumbersList.get(5).containsKey(exData.getNum6())) {
			containCnt++;
		}
		
		if (containCnt >= MATCH_CNT) {
			result = true;
		}
		
		return result;
	}

	/**
	 * 숫자별 최대 출현횟수 설정
	 * 
	 * @param winDataList 당첨번호 정보 전체
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getAppearNumbersList(List<WinDataAnlyDto> winDataList) {
		List<Map> appearNumbersList = new ArrayList<Map>();
		/** 출현제한 백분율(%) */
		int FOA_PER = 83;
		
		Map<Integer, Integer> num1AppearMap = this.getNumberMap(winDataList, 1, FOA_PER);
		Map<Integer, Integer> num2AppearMap = this.getNumberMap(winDataList, 2, FOA_PER);
		Map<Integer, Integer> num3AppearMap = this.getNumberMap(winDataList, 3, FOA_PER);
		Map<Integer, Integer> num4AppearMap = this.getNumberMap(winDataList, 4, FOA_PER);
		Map<Integer, Integer> num5AppearMap = this.getNumberMap(winDataList, 5, FOA_PER);
		Map<Integer, Integer> num6AppearMap = this.getNumberMap(winDataList, 6, FOA_PER);
		
		appearNumbersList.add(num1AppearMap);
		appearNumbersList.add(num2AppearMap);
		appearNumbersList.add(num3AppearMap);
		appearNumbersList.add(num4AppearMap);
		appearNumbersList.add(num5AppearMap);
		appearNumbersList.add(num6AppearMap);
		
		return appearNumbersList;
	}

	/**
	 * 출현번호 Map 가져오기
	 * 
	 * @param winDataList 당첨번호 정보 전체
	 * @param seq 숫자 출현 순서
	 * @param FOA_PER 제한 백분율(%)
	 * @return
	 */
	public Map<Integer, Integer> getNumberMap(List<WinDataAnlyDto> winDataList, int seq, int FOA_PER) {
		Map<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int numCnt = 0;
		for (WinDataAnlyDto winData : winDataList) {
			int seqNum = winData.getNumbers()[seq-1];
			
//			System.out.println("firstNum : " + seqNum);
			if (map.containsKey(seqNum)) {
				int cnt = map.get(seqNum) + 1;
//				System.out.println("firstNum : " + seqNum + "/cnt : " + cnt);
				map.put(seqNum, cnt);
			} else {
//				System.out.println("firstNum : " + seqNum + "/cnt : 1");
				map.put(seqNum, 1);
				numCnt++;	//번호종류가 추가될 때마다 1 증가
			}
		}
		
		/**
		 * 2. 첫 번째 수 출현횟수 배열 설정
		 * 
		 * col 설명
		 * 0 : 출현숫자
		 * 1 : 출현횟수
		 * 2 : 백분율(%)
		 */
		int[][] maxAppearCnt = new int[numCnt][3];
		int rowIdx = 0;
		Iterator<Integer> data = map.keySet().iterator();
		while (data.hasNext()) {
			int key = (Integer) data.next();
			int value = map.get(key);
			maxAppearCnt[rowIdx][0] = key;
			maxAppearCnt[rowIdx][1] = value;
			
			int cnt = value;
			int count = winDataList.size();
			double per = ((double)cnt/(double)count)*100;			
			int percent = (int)Math.floor(per);
			maxAppearCnt[rowIdx][2] = percent;
			
			rowIdx++;
		}
		
		//3. 출현횟수 배열 정렬 (내림차순:출현횟수)
		for (int i = 0; i < maxAppearCnt.length-1; i++) {
			for (int j = i+1; j < maxAppearCnt.length; j++) {
				int preCnt = maxAppearCnt[i][1];
				int nextCnt = maxAppearCnt[j][1];
				
				//뒷 번호 회수가 앞 번호 횟수보다 크다면 SWAP
				if (preCnt < nextCnt) {
					int[] temp = maxAppearCnt[j];
					maxAppearCnt[j] = maxAppearCnt[i];
					maxAppearCnt[i] = temp;
				}
			}
		}
		
		int totPer = 0;	//누적 백분율
		for (int i = 0; i < maxAppearCnt.length-1; i++) {
			returnMap.put(maxAppearCnt[i][0], maxAppearCnt[i][0]);
			totPer += maxAppearCnt[i][2];	//백분율 누적
			if (totPer >= FOA_PER) {
				break;
			}
		}
		
		return returnMap;
	}

	/**
	 * 포스팅 조사 처리
	 * 2018.03.25
	 * 
	 * @param data
	 * @return
	 */
	private String getPostPosition(Object data) {
		String sData = "";
		String lastData = "";
		String postPosition = "로";
		
		if (data instanceof String) {
			sData = (String) data;
			
		} else if (data instanceof Integer) {
			sData = String.valueOf(data);
		}
		
		lastData = sData.substring(sData.length()-1, sData.length());
		if ("0".equals(lastData)
				|| "3".equals(lastData)
				|| "6".equals(lastData)
				) {
			postPosition = "으로";
		}
		
		return postPosition;
	}
	
	/**
	 * 포스팅 조사 처리<br>
	 * 로(으로)<br>
	 * 은(는)<br>
	 * 와(과)<br>
	 * 을(를)<br>
	 * 2018.05.31<br>
	 * 
	 * @param data
	 * @param str "로","은","와","을"
	 * @return
	 */
	private String getPostPosition(Object data, String str) {
		String sData = "";
		String lastData = "";
		// 로(으로), 은(는)
		String postPosition = "";
		
		if (data instanceof String) {
			sData = (String) data;
			
		} else if (data instanceof Integer) {
			sData = String.valueOf(data);
		}
		
		lastData = sData.substring(sData.length()-1, sData.length());
		if ("로".equals(str)) {
			if ("0".equals(lastData)
					|| "3".equals(lastData)
					|| "6".equals(lastData)
					) {
				postPosition = "으로";
			} else {
				postPosition = "로";
			}
		} else if ("은".equals(str)) {
			if ("2".equals(lastData)
					|| "4".equals(lastData)
					|| "5".equals(lastData)
					|| "9".equals(lastData)
					) {
				postPosition = "는";
			} else {
				postPosition = "은";
			}
		} else if ("와".equals(str)) {
			if ("2".equals(lastData)
					|| "4".equals(lastData)
					|| "5".equals(lastData)
					|| "9".equals(lastData)
					) {
				postPosition = "와";
			} else {
				postPosition = "과";
			}
		} else if ("을".equals(str)) {
			if ("2".equals(lastData)
					|| "4".equals(lastData)
					|| "5".equals(lastData)
					|| "9".equals(lastData)
					) {
				postPosition = "를";
			} else {
				postPosition = "을";
			}
		}
		
		return postPosition;
	}
	
	/**
	 * 포스팅 저고 비율 메세지 설정
	 * 
	 * @param winData
	 * @param lowHighDataList
	 * @return
	 */
	public String getLowHighMsg(WinDataDto winData, List<LowHighDto> lowHighDataList) {
		String lowHighMsg = "";
		lowHighMsg += "<br>";
		lowHighMsg += "<strong>저고 비율</strong>은<br>";
		for (int i = 0; i < lowHighDataList.size(); i++) {
			if (winData.getLow_high().equals(lowHighDataList.get(i).getLowhigh_type())) {
				lowHighMsg += "평균 " + lowHighDataList.get(i).getRatio()+ "%인<br>";
				break;
			}
		}
		lowHighMsg += winData.getLow_high() + this.getPostPosition(winData.getLow_high()) + " 분석되었습니다.";
		
		return lowHighMsg;
	}
	
	/**
	 * 포스팅 홀짝 비율 메세지 설정
	 * 
	 * @param winData
	 * @param oddEvenDataList
	 * @return
	 */
	public String getOddEvenMsg(WinDataDto winData, List<OddEvenDto> oddEvenDataList) {
		String oddEvenMsg = "";
		oddEvenMsg += "<br>";
		oddEvenMsg += "<strong>홀짝 비율</strong>은<br>";
		for (int i = 0; i < oddEvenDataList.size(); i++) {
			if (winData.getOdd_even().equals(oddEvenDataList.get(i).getOddeven_type())) {
				oddEvenMsg += "평균 " + oddEvenDataList.get(i).getRatio()+ "%인<br>";
				break;
			}
		}
		oddEvenMsg += winData.getOdd_even() + this.getPostPosition(winData.getOdd_even()) + " 분석되었습니다.";
		
		return oddEvenMsg;
	}
	
	/**
	 * 포스팅 총합 메세지 설정
	 * 
	 * @param winData
	 * @param totalInfo
	 * @return
	 */
	public String getTotalMsg(WinDataDto winData, TotalDto totalInfo) {
		String totalMsg = "";
		if (totalInfo.getLow_total() <= winData.getTotal()
				&& totalInfo.getHigh_total() >= winData.getTotal()) {
			totalMsg += "<br>";
			totalMsg += "<strong>총합</strong>은<br>";
			totalMsg += "표준 범위 " + totalInfo.getTotal_range() + "내인<br>";
			totalMsg += winData.getTotal() + this.getPostPosition(winData.getTotal()) + " 분석되었습니다.";
			totalMsg += "<br>";	
		} else {
			totalMsg += "<br>";
			totalMsg += "<strong>총합</strong>은<br>";
			totalMsg += "표준 범위 " + totalInfo.getTotal_range() + "를 벗어난<br>";
			totalMsg += winData.getTotal() + this.getPostPosition(winData.getTotal()) + " 예측하지 못한 결과로<br>";
			totalMsg += "분석되었습니다.";
			totalMsg += "<br>";
		}
		return totalMsg;
	}

	/**
	 * 포스팅 끝수합 메세지 설정
	 * 
	 * @param winData
	 * @param endNumInfo
	 * @return
	 */
	public String getEndnumMsg(WinDataDto winData, EndNumDto endNumInfo) {
		String endnumMsg = "";
		if (endNumInfo.getLow_endnum() <= winData.getSum_end_num()
				&& endNumInfo.getHigh_endnum() >= winData.getSum_end_num()) {
			endnumMsg += "<br>";
			endnumMsg += "<strong>끝수합</strong>은<br>";
			endnumMsg += "표준 범위 " + endNumInfo.getEndnum_range() + "내인<br>";
			endnumMsg += winData.getSum_end_num() + this.getPostPosition(winData.getSum_end_num()) + " 분석되었습니다.";
			endnumMsg += "<br>";	
		} else {
			endnumMsg += "<br>";
			endnumMsg += "<strong>끝수합</strong>은<br>";
			endnumMsg += "표준 범위 " + endNumInfo.getEndnum_range() + "를 벗어난<br>";
			endnumMsg += winData.getSum_end_num() + this.getPostPosition(winData.getSum_end_num()) + " 예측하지 못한 결과로<br>";
			endnumMsg += "분석되었습니다.";
			endnumMsg += "<br>";
		}
		return endnumMsg;
	}
	
	/**
	 * 포스팅 AC 메세지 설정
	 * 
	 * @param winData
	 * @param endNumInfo
	 * @return
	 */
	public String getAcMsg(WinDataDto winData, AcDto acInfo) {
		String acMsg = "";
		if (acInfo.getLow_ac() <= winData.getAc()
				&& acInfo.getHigh_ac() >= winData.getAc()) {
			acMsg += "<br>";
			acMsg += "<strong>AC</strong> 역시<br>";
			acMsg += "표준 범위 " + acInfo.getAc_range() + " 내인<br>";
			acMsg += winData.getAc() + this.getPostPosition(winData.getAc()) + " 분석되었습니다.";
			acMsg += "<br>";	
		} else {
			acMsg += "<br>";
			acMsg += "이번 <strong>AC</strong>는 특이하게<br>";
			acMsg += "표준 범위 " + acInfo.getAc_range() + this.getPostPosition(acInfo.getAc_range(), "을") + " 벗어난<br>";
			acMsg += winData.getAc() + this.getPostPosition(winData.getAc()) + "로 분석되었습니다.<br>";
			acMsg += "<br>";
		}
		return acMsg;
	}

	/**
	 * 포스팅 제외수 메세지 설정
	 * 
	 * @param winData
	 * @param excludeInfo
	 * @return
	 */
	public String getExcludeMsg(WinDataDto winData, ExcludeDto excludeInfo) {
		String excludeMsg = "";
		String strExcludeList = "";
		// 조사처리를 위한 변수
		String lastData = ""; 
		
		// 확인할 당첨번호 목록 설정
		int[] numbers = LottoUtil.getNumbers(winData);
		Map<String, Integer> deleteTargetMap = new HashMap<String, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			deleteTargetMap.put(String.valueOf(numbers[i]), numbers[i]);
		}
		
		// 전회차 제외수 목록 설정
		String excludeNum = excludeInfo.getExclude_num();
		String[] datas = excludeNum.split(", ");
		for (int i = 0; i < datas.length; i++) {
			if (!"".equals(strExcludeList)) {
				strExcludeList += ", ";
			}
			
			if (!deleteTargetMap.containsKey(datas[i])) {
				strExcludeList += datas[i];
				lastData = datas[i];	// 마지막 숫자로 설정
			}
		}
		
		excludeMsg += "<br>";
		excludeMsg += "<strong>제외수</strong>는<br>";
		excludeMsg += "그동안 출현한 규칙에 의해 예측한<br>";
		excludeMsg += strExcludeList + this.getPostPosition(lastData, "은") + "<br>";
		excludeMsg += "출현하지 않았습니다.<br>";
		excludeMsg += "<br>";
		
		return excludeMsg;
	}

	/**
	 * 출현번호/미출현번호 메세지 설정
	 * 
	 * @param winDataList
	 * @return
	 */
	public Map<String, String> getContainInfo(List<WinDataDto> winDataList) {
		// 메세지 설정
		Map<String, String> containInfo = new HashMap<String, String>();
		String containMsg = "";		
		String notContainMsg = "";
		
		// 최근 당첨정보
		WinDataDto lastData = winDataList.get(0);
		int[] winNumbers = LottoUtil.getNumbers(lastData);
		String containList = "";		
		String winContainList = "";		
		int containListCnt = 0;
		String notContainList = "";
		String winNotContainList = "";
		int notContainListCnt = 0;
		
		
		// 출현번호 목록 Map
		Map<Integer, Integer> containNumbersMap = new HashMap<Integer, Integer>();
		List<Integer> containNumberList = new ArrayList<Integer>();
		// 미출현번호 목록 Map
		Map<Integer, Integer> notContainNumbersMap = new HashMap<Integer, Integer>();
		
		// 1. 출현번호 설정
		// 이전회차 ~ 10회차까지
		for(int index = 1 ; index <= 10 ; index++){
			int[] numbers = LottoUtil.getNumbers(winDataList.get(index));
			for(int number : numbers){
				if(!containNumbersMap.containsKey(number)){
					containNumberList.add(number);
					containNumbersMap.put(number, number);
				}
			}
		}
		
		LottoUtil.dataSort(containNumberList);
		for (int i = 0; i < containNumberList.size(); i++) {
			if (i > 0) {
				containList += ", ";
			}
			containList += containNumberList.get(i);
		}
		
		// 2. 미출현번호 설정
		for (int i = 1; i <= 45; i++) {
			if (!containNumbersMap.containsKey(i)) {
				notContainNumbersMap.put(i, i);
				if (!"".equals(notContainList)) {
					notContainList += ", ";
				}
				notContainList += i;
			}
		}
		
		// 3. 메세지 설정
		for (int number : winNumbers) {
			if (containNumbersMap.containsKey(number)) {
				if (!"".equals(winContainList)) {
					winContainList += ", ";
				}
				winContainList += number;
				containListCnt++;
			} else {
				if (!"".equals(winNotContainList)) {
					winNotContainList += ", ";
				}
				winNotContainList += number;
				notContainListCnt++;
			}
		}
		
		containMsg += "<br>";
		containMsg += "최근 10회차 동안 출현한 번호들 중에서는<br>";
		containMsg += containList + "중<br>";
		if (containListCnt > 0) {
			containMsg += "[" + winContainList + "]<br>";
			containMsg += "총 " + containListCnt + "개의 번호가 " + (containListCnt==6?"모두 ":"") + "출현하였고,<br>";
		} else {
			containMsg += "당첨번호가 존재하지 않았으며,<br>";
		}
		containMsg += "<br>";
		
		notContainMsg += "<br>";
		notContainMsg += "최근 10회차 동안 출현하지 않은 번호들 중에서는<br>";
		notContainMsg += notContainList + "중<br>";
		if (notContainListCnt > 0) {
			notContainMsg += "[" + winNotContainList + "]<br>";
			notContainMsg += "총 " + notContainListCnt + "개의 번호가 당첨번호로 " + (notContainListCnt==6?"모두 ":"") + "출현했습니다.<br>";
		} else {
			notContainMsg += "당첨번호는 출현하지 않았습니다.<br>";
		}
		notContainMsg += "<br>";
		
		containInfo.put("containMsg", containMsg);
		containInfo.put("notContainMsg", notContainMsg);
		
		return containInfo;
	}

	/**
	 * 궁합수 메세지 설정
	 * 
	 * @param winData
	 * @param mcNumList
	 * @return
	 */
	public String getMcMatchedMsg(WinDataDto winData, List<MCNumDto> mcNumList) {
		String mcMatchedMsg = "";
		Map<String, Object> mcMatchedDataMap = this.getMcMatchedDataMap(winData, mcNumList);
		int matchedCount = (int) mcMatchedDataMap.get("matchedCount");
		String result = (String) mcMatchedDataMap.get("result");
		
		mcMatchedMsg += "<br>";
		mcMatchedMsg += "궁합 수는<br>";
		if (matchedCount > 0) {
			mcMatchedMsg += result + this.getPostPosition(result, "와") + " 같이<br>";
			mcMatchedMsg += "각 당첨번호 간 궁합 수가 존재했습니다.<br>";
		} else {
			mcMatchedMsg += "이번 회차에서는 존재하지 않았습니다.<br>";
		}
		mcMatchedMsg += "<br>";
		
		return mcMatchedMsg;
	}

	/**
	 * 미출현 번호대 구간 메세지 설정
	 * 
	 * @param zeroRangeInfo
	 * @return
	 */
	public String getZeroRangeMsg(ZeroRangeDto zeroRangeInfo) {
		String zeroRangeMsg = "";
		
		zeroRangeMsg += "<br>";
		if (zeroRangeInfo.getZero_cnt() > 0) {
			zeroRangeMsg += "미출현 번호대 구간은<br>";
			zeroRangeMsg += zeroRangeInfo.getZero_range() + " 구간에서<br>";
			zeroRangeMsg += "당첨번호가 출현하지 않았습니다.<br>";
		} else {
			zeroRangeMsg += "이번 회차에서는<br>";
			zeroRangeMsg += "전체 번호대 구간에서<br>";
			zeroRangeMsg += "당첨번호가 출현했습니다.<br>";
		}
		zeroRangeMsg += "<br>";
		
		return zeroRangeMsg;
	}

	/**
	 * 향상된 규칙들 조회
	 * 2020.02.06
	 * 
	 * @param exCount
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getEnhancedRules(int loginUserNo, int exCount) {
		Map enhancedRulesMap = new HashMap();
		
		// 당첨번호 전체 목록 조회
		log.info("[" + loginUserNo + "]\t 당첨번호 전체 목록 조회 (오름차순)");
		WinDataDto winDataDto = new WinDataDto();
		winDataDto.setSord("ASC");
		winDataDto.setPage("1");	// 전체조회 설정
		List<WinDataDto> winDataList = sysmngService.getWinDataList(winDataDto);
		
		// 최근회차 조회 
		WinDataDto lastWinData = winDataList.get(winDataList.size()-1);
		log.info("[" + loginUserNo + "]\t 최근회차 조회 : " + lastWinData.getWin_count());
		
		// 궁합수 조회 (최근회차)
		log.info("[" + loginUserNo + "]\t 궁합수 조회 (최근회차)");
		WinDataDto dtoForMC = new WinDataDto();
		dtoForMC.setWin_count(lastWinData.getWin_count());
		List<MCNumDto> mcNumList = sysmngService.getMcNumList(dtoForMC);
		

		// 미출궁합수 중복제크 Map, List
		Map checkMcListOfNotContMap = new HashMap();
		List<Integer> mcListOfNotContList = new ArrayList<Integer>();
		
		// 제외수 중복제크 Map, List
		Map checkExcludeNumber = new HashMap();
		List<Integer> excludeNumberList = new ArrayList<Integer>();
		
		// 미출궁합수에 없는 미출수목록1
		List<Integer> excludeList1 = new ArrayList<Integer>();
		
		// 미출수목록1의 궁합수 중복제크 Map, List
		Map excludeList2Map = new HashMap();
		List<Integer> excludeList2 = new ArrayList<Integer>();
		
		// 제외수 궁합수 중 미출수
		int excludeNumOfMcNum = 0;
		int maxCount = 0;
		
		// 제외수 궁합수 중 포함할 미출수
		Map excludeList3Map = new HashMap();
		List<Integer> excludeList3 = new ArrayList<Integer>();
		
		// 전체제외수 중복제크 Map, List
		Map checkAllExcludeNumMap = new HashMap();
		List<Integer> allExcludeNumList = new ArrayList<Integer>();
		Map<Integer, Integer> allExcludeNumMap = new HashMap<Integer, Integer>();
			
			
		/*****************************************************
		 * 1. 최근 10회동안 미출한 번호들의 궁합수 목록을 구함
		 *****************************************************/
		// 10회차 포함번호 목록 조회
		List<Integer> contain10List = this.getContain10List(winDataList, lastWinData.getWin_count());
		// 10회차 미포함번호 목록 조회
		List<Integer> notContain10List = this.getNotContain10List(contain10List);
		
		for (int j = 0; j < notContain10List.size(); j++) {
			int notContainNum = notContain10List.get(j);
			
			for (int k = 0; k < mcNumList.size(); k++) {
				MCNumDto mCNumDto = mcNumList.get(k);
				int mcNum = mCNumDto.getNum();
				if (notContainNum == mcNum) {
					// 궁합수 문자열을 int 배열로 변환
					int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
					
					for (int l = 0; l < mcNumbers.length; l++) {
						int mcNumOfexcludeNum = mcNumbers[l];
						
						// 미출수의 궁합수를 미출궁합수 목록에 등록
						if (!checkMcListOfNotContMap.containsKey(mcNumOfexcludeNum)) {
							checkMcListOfNotContMap.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
							mcListOfNotContList.add(mcNumOfexcludeNum);
						}
					}
					
					// 현재 제외수의 궁합수 반복 중단처리
					break;
				}
			}
		}
		mcListOfNotContList = (List<Integer>) LottoUtil.dataSort(mcListOfNotContList);
		
		/*****************************************************
		 * 2. 1번 중 미출수에 없는 번호 선별
		 *****************************************************/
		for (int j = 0; j < notContain10List.size(); j++) {
			boolean exist = false;
			int notContainNum = notContain10List.get(j);
			
			for (int k = 0; k < mcListOfNotContList.size(); k++) {
				int mcNum = mcListOfNotContList.get(k);
				
				if (notContainNum == mcNum) {
					exist = true;
					break;
				}
			}
			
			if (!exist) {
				excludeList1.add(notContainNum);
			}
		}
		
		/*****************************************************
		 * 3. 미출수의 궁합수 중 2번째까지만 선별
		 *****************************************************/
		for (int j = 0; j < excludeList1.size(); j++) {
			int excludeNum = excludeList1.get(j);
			
			for (int k = 0; k < mcNumList.size(); k++) {
				MCNumDto mCNumDto = mcNumList.get(k);
				int mcNum = mCNumDto.getNum();
				if (excludeNum == mcNum) {
					// 궁합수 문자열을 int 배열로 변환
					int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
					
					// 2번째까지만 설정
					for (int l = 0; l < (mcNumbers.length > 2 ? 2 : mcNumbers.length); l++) {
						int mcNumOfexcludeNum = mcNumbers[l];
						
						// 미출수목록1의 궁합수를 미출수목록2에 등록
						if (!excludeList2Map.containsKey(mcNumOfexcludeNum)) {
							excludeList2Map.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
							excludeList2.add(mcNumOfexcludeNum);
						}
					}
					
					// 현재 제외수의 궁합수 반복 중단처리
					break;
				}
			}
		}
		
		/*****************************************************
		 * 4. 제외수의 궁합수 목록을 구함.
		 *****************************************************/
		// 제외수 조회
		ExDataDto exDataDto = new ExDataDto();
		exDataDto.setEx_count(exCount);
		ExcludeDto excludeDto = sysmngService.getExcludeInfo(exDataDto);
		
		// 제외수 문자열을 int 배열로 변환
		int[] excludeNumbers = LottoUtil.getNumbers(excludeDto.getExclude_num().replaceAll(" ", ""));
		
		// 제외수의 궁합수 목록을 추출
		for (int j = 0; j < excludeNumbers.length; j++) {
			int excludeNum = excludeNumbers[j];
			
			if (!checkExcludeNumber.containsKey(excludeNum)) {
				checkExcludeNumber.put(excludeNum, excludeNum);
				excludeNumberList.add(excludeNum);
			}
			
			for (int k = 0; k < mcNumList.size(); k++) {
				MCNumDto mCNumDto = mcNumList.get(k);
				int mcNum = mCNumDto.getNum();
				if (excludeNum == mcNum) {
					// 궁합수 문자열을 int 배열로 변환
					int[] mcNumbers = LottoUtil.getNumbers(mCNumDto.getMc_num().replaceAll(" ", ""));
					
					for (int l = 0; l < mcNumbers.length; l++) {
						int mcNumOfexcludeNum = mcNumbers[l];
						
						// 제외수의 궁합수를 목록에 등록
						if (!checkExcludeNumber.containsKey(mcNumOfexcludeNum)) {
							checkExcludeNumber.put(mcNumOfexcludeNum, mcNumOfexcludeNum);
							excludeNumberList.add(mcNumOfexcludeNum);
						}
					}
					
					// 현재 제외수의 궁합수 반복 중단처리
					break;
				}
			}
			
		} // end 제외수 목록 반복
		
		/*****************************************************
		 * 5. 제외수 궁합수 목록에서 미출수에 포함된 번호 중 가장 오래된 번호는 제외
		 * 6. 5번의 나머지 번호중 미출수에 있는 번호는 추가
		 *****************************************************/
		for (int j = 0; j < excludeNumberList.size(); j++) {
			int excludeNum = excludeNumberList.get(j);
			
			for (int k = 0; k < notContain10List.size(); k++) {
				int notContainNum = notContain10List.get(k);
				
				if (excludeNum == notContainNum) {
					
					// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
					if (!excludeList3Map.containsKey(notContainNum)) {
						excludeList3Map.put(notContainNum, notContainNum);
						excludeList3.add(notContainNum);
					}
					
					int checkCount = 0;
					
					// 이전 출현회수 비교하여 가장 오래된 미출수 선별
					for (int l = lastWinData.getWin_count() - 1; l >= 0; l--) {
						checkCount++;
						
						WinDataDto bfWinData = winDataList.get(l);
						
						if (notContainNum == bfWinData.getNum1()
								|| notContainNum == bfWinData.getNum2()
								|| notContainNum == bfWinData.getNum3()
								|| notContainNum == bfWinData.getNum4()
								|| notContainNum == bfWinData.getNum5()
								|| notContainNum == bfWinData.getNum6()
								) {
							break;
						}
					}
					
					if (excludeNumOfMcNum == 0) {
						excludeNumOfMcNum = notContainNum;
						maxCount = checkCount;
					} else {
						// 기존과 비교 (가장 오래된 미출수 선별)
						if (maxCount < checkCount) {
							excludeNumOfMcNum = notContainNum;
							maxCount = checkCount;
						}
					}
					
					break;
				}
			}
		}
		
		/*****************************************************
		 * 7. 전체 제외수 목록을 구함.
		 *    (미출수 & 미출수 궁합수 + 제외수)
		 *****************************************************/
		for (int j = 0; j < excludeList1.size(); j++) {
			int excludeNum = excludeList1.get(j);
			
			// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
			if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
				checkAllExcludeNumMap.put(excludeNum, excludeNum);
				allExcludeNumList.add(excludeNum);
			}
		}
		
		for (int j = 0; j < excludeList2.size(); j++) {
			int excludeNum = excludeList2.get(j);
			
			// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
			if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
				checkAllExcludeNumMap.put(excludeNum, excludeNum);
				allExcludeNumList.add(excludeNum);
			}
		}
		
		for (int j = 0; j < excludeList3.size(); j++) {
			int excludeNum = excludeList3.get(j);
			
			// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
			if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
				checkAllExcludeNumMap.put(excludeNum, excludeNum);
				allExcludeNumList.add(excludeNum);
			}
		}
		
		for (int j = 0; j < excludeNumbers.length; j++) {
			int excludeNum = excludeNumbers[j]; 
			
			// 제외수의 궁합수 중 미포함수는 미출수목록3에 등록
			if (!checkAllExcludeNumMap.containsKey(excludeNum)) {
				checkAllExcludeNumMap.put(excludeNum, excludeNum);
				allExcludeNumList.add(excludeNum);
			}
		}
		
		allExcludeNumList = (List<Integer>) LottoUtil.dataSort(allExcludeNumList);
		
		// 존재하면, 제외수 궁합수 중 포함할 미출수 목록에서 제거 
		if (excludeNumOfMcNum > 0) {
			for (int k = 0; k < allExcludeNumList.size(); k++) {
				int excludeNum = allExcludeNumList.get(k);
				if (excludeNumOfMcNum == excludeNum) {
					allExcludeNumList.remove(k);
					break;
				}
			}
			log.info("[" + loginUserNo + "]\t\t " + exCount + "회차의 장기 미출수는 제외수에서 미포함 : " + excludeNumOfMcNum);
		}
		
		/*****************************************************
		 * 8. 연번 규칙 적용
		 *    (연번 출현 시 다음회차의 +2, -2 번호 1개는 출현)
		 *****************************************************/
		int[] numbers = LottoUtil.getNumbers(lastWinData);
		for (int i = 0; i < numbers.length-1; i++) {
			if (numbers[i+1] - numbers[i] == 1) {
				// 끝수 존재여부 체크
				if (i+2 < numbers.length) {
					if(numbers[i+2] - numbers[i+1] == 1) {
						// 다음수가 3연속은 규칙 제외
						continue;
					}
				} else if (i-1 >= 0) {
					if(numbers[i] - numbers[i-1] == 1) {
						// 이전수가 3연속은 규칙 제외
						continue;
					}
				}
				
				// 연번
				List<Integer> list = new ArrayList<Integer>();
				list.add(numbers[i] - 2);
				list.add(numbers[i] + 2);
				list.add(numbers[i+1] - 2);
				list.add(numbers[i+1] + 2);
				
				for (int j = 0; j < list.size(); j++) {
					int consecutivelyNumber = list.get(j);
					
					for (int k = 0; k < allExcludeNumList.size(); k++) {
						int excludeNum = allExcludeNumList.get(k);
						if (consecutivelyNumber == excludeNum) {
							allExcludeNumList.remove(k);
							break;
						}
					}
				}
			}
		}
		
		/*****************************************************
		 * 9. 2회 전회차의 1구간 3개수 규칙 적용
		 *    (연번 출현 시 다음회차의 +2, -2 번호 1개는 출현)
		 *****************************************************/
		WinDataDto checkDto = winDataList.get(winDataList.size()-2);
		int[] containGroupCnt = this.getZeroCntRangeData(checkDto);
		boolean isCheck = false;
		int checkRangeCnt = 0;
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] == 3) {
				isCheck = true;
				checkRangeCnt = i;
				break;
			}
		}

		if (isCheck) {
			int[] bf2CountNumbers = LottoUtil.getNumbers(checkDto);
			int[] compareNumbers = new int[3];
			int cnt = 0;
			
			int startIdx = 10 * checkRangeCnt;
			if (checkRangeCnt < 4) {
				int endIdx = 10 * (checkRangeCnt+1);
				
				for (int i = 0; i < bf2CountNumbers.length; i++) {
					if (startIdx < bf2CountNumbers[i] 
							&& bf2CountNumbers[i] <= endIdx) {
						compareNumbers[cnt++] = bf2CountNumbers[i];
					}
				}
			} else {
				int endIdx = 10 * checkRangeCnt + 5;
				for (int i = 0; i < bf2CountNumbers.length; i++) {
					if (startIdx < bf2CountNumbers[i] 
							&& bf2CountNumbers[i] <= endIdx) {
						compareNumbers[cnt++] = bf2CountNumbers[i];
					}
				}
			}
			
			// 3연속 수의 차이수
			List<Integer> list = new ArrayList<Integer>();
			list.add(compareNumbers[2] - compareNumbers[1]);
			list.add(compareNumbers[1] - compareNumbers[0]);
			
			for (int j = 0; j < list.size(); j++) {
				int difNumber = list.get(j);
				
				for (int k = 0; k < allExcludeNumList.size(); k++) {
					int excludeNum = allExcludeNumList.get(k);
					if (difNumber == excludeNum) {
						allExcludeNumList.remove(k);
						break;
					}
				}
			}
		}
		
		/************************************************************
		 * 기본제외수 추가
		 * : 5주 이내 2번 이상 출현 수 제외수로 추가
		 * 2020.04.02
		 ************************************************************/
		List<Integer> addExcludeNumbersFromDtoList = this.getAddExcludeNumbersFromDtoList(winDataList);
		String strAddExcludeNumbers = ""; 
		for (int i = 0; i < addExcludeNumbersFromDtoList.size(); i++) {
			int excludeNumber = addExcludeNumbersFromDtoList.get(i);
			if (!"".equals(strAddExcludeNumbers)) {
				strAddExcludeNumbers += ",";
			}
			strAddExcludeNumbers += excludeNumber;
		}			
		log.info("[" + loginUserNo + "]\t\t추가된 기본제외수 (5주 이내 2번 이상 출현) >>> " + strAddExcludeNumbers);

		// allExcludeNumList에서 확인 후 없으면 추가 처리
		boolean checkAdd = false;
		for (int j = 0; j < addExcludeNumbersFromDtoList.size(); j++) {
			int addExcludeNumber = addExcludeNumbersFromDtoList.get(j);
			boolean exist = false;
			for (int i = 0; i < allExcludeNumList.size(); i++) {
				if (addExcludeNumber == allExcludeNumList.get(i)) {
					exist = true;
					break;
				}
			}
			if (exist) {
				continue;
			} else {
				allExcludeNumList.add(addExcludeNumber);
				checkAdd = true;
			}
		}
		
		/************************************************************
		 * 미출수 출현 시 제외수 추가
		 * : 11주 이상 미출구간부터 최근회차 당첨번호가 출현한 
		 * 동일한 미출기간 번호는 제외한다.
		 * : 직전회차 당첨구간의 동일한 위치(인덱스) 숫자는 제외
		 * 2020.04.03
		 ************************************************************/
		List<Integer> addExcludeNumbersFromNotContain = this.getAddExcludeNumbersFromNotContain(winDataList);
		strAddExcludeNumbers = ""; 
		for (int i = 0; i < addExcludeNumbersFromNotContain.size(); i++) {
			int excludeNumber = addExcludeNumbersFromNotContain.get(i);
			if (!"".equals(strAddExcludeNumbers)) {
				strAddExcludeNumbers += ",";
			}
			strAddExcludeNumbers += excludeNumber;
		}			
		log.info("[" + loginUserNo + "]\t\t추가된 기본제외수 (미출수 중 동일한 포지션 출현) >>> " + strAddExcludeNumbers);

		// allExcludeNumList에서 확인 후 없으면 추가 처리
		boolean checkAdd2 = false;
		for (int j = 0; j < addExcludeNumbersFromNotContain.size(); j++) {
			int addExcludeNumber = addExcludeNumbersFromNotContain.get(j);
			boolean exist = false;
			for (int i = 0; i < allExcludeNumList.size(); i++) {
				if (addExcludeNumber == allExcludeNumList.get(i)) {
					exist = true;
					break;
				}
			}
			if (exist) {
				continue;
			} else {
				allExcludeNumList.add(addExcludeNumber);
				checkAdd2 = true;
			}
		}		
		
		
		// 내용이 추가되면 다시 정렬
		if (checkAdd || checkAdd2) {
			allExcludeNumList = (List<Integer>) LottoUtil.dataSort(allExcludeNumList);
		}
		
		
		// 결과 확인
		String modiExcludeNum = "";
		for (int j = 0; j < allExcludeNumList.size(); j++) {
			int excludeNum = allExcludeNumList.get(j);
			allExcludeNumMap.put(excludeNum, excludeNum);
			if (!"".equals(modiExcludeNum)) {
				modiExcludeNum += ",";
			}
			modiExcludeNum = "" + modiExcludeNum + excludeNum;
		}
		
		log.info("[" + loginUserNo + "]\t\t " + exCount + "회차 제외수 : " + excludeDto.getExclude_num());
		log.info("[" + loginUserNo + "]\t\t " + exCount + "회차 개선된 제외수 : " + modiExcludeNum);
					
		// 개선된 제외수
		enhancedRulesMap.put("allExcludeNumList", allExcludeNumList);	
		enhancedRulesMap.put("allExcludeNumMap", allExcludeNumMap);	
		// 장기 미출수 (1 포함)
		enhancedRulesMap.put("excludeNumOfMcNum", excludeNumOfMcNum);			
					
					
		return enhancedRulesMap;
	}

	/**
	 * 3연속 수 존재 확인
	 * 2020.02.29
	 * 
	 * @param sourceNumbers
	 * @return
	 */
	public boolean check3ConsecutivelyNumbers(int[] sourceNumbers) {
		// 체크
		boolean isAppear = false;	// 출현여부
		
		for (int i = 0; i < sourceNumbers.length - 2; i++) {
			if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
				
				// 끝수 존재여부 체크
				if (i+2 < sourceNumbers.length) {
					if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
						// 다음수가 3연속 확인
						isAppear = true;
						
						break;
					}
				}
			}
		}
		return isAppear;
	}

	/**
	 * 같은번호 3개 존재 확인
	 * 2020.02.29
	 * 
	 * @param sourceNumbers
	 * @return
	 */
	public boolean check1Range3Numbers(int[] sourceNumbers) {
		// 체크
		boolean isAppear = false;	// 출현여부
		int[] rangeCnt = {0,0,0,0,0};
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] < 10 ) {
				rangeCnt[0] += 1; 
			} else if (sourceNumbers[i] < 20 ) {
				rangeCnt[1] += 1;
			} else if (sourceNumbers[i] < 30 ) {
				rangeCnt[2] += 1;
			} else if (sourceNumbers[i] < 40 ) {
				rangeCnt[3] += 1;
			} else {
				rangeCnt[4] += 1;
			}
		}
		
		for (int i = 0; i < rangeCnt.length; i++) {
			if (rangeCnt[i] == 3) {
				isAppear = true;
				break;
			}
		}
		
		return isAppear;
	}
	
	/**
	 * 같은번호 3개 존재 확인
	 * 2020.02.29
	 * 
	 * @param sourceNumbers
	 * @param range 특정구간 <br>0: 1~9<br>1: 10~19<br>2: 20~29<br>3: 30~39<br>4: 40~45
	 * @return true:존재함, false: 존재하지않음
	 */
	public boolean checkRange3Numbers(int[] sourceNumbers, int range) { 
		// 체크
		boolean isAppear = false;	// 출현여부
		int[] rangeCnt = {0,0,0,0,0};
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] < 10 ) {
				rangeCnt[0] += 1; 
			} else if (sourceNumbers[i] < 20 ) {
				rangeCnt[1] += 1;
			} else if (sourceNumbers[i] < 30 ) {
				rangeCnt[2] += 1;
			} else if (sourceNumbers[i] < 40 ) {
				rangeCnt[3] += 1;
			} else {
				rangeCnt[4] += 1;
			}
		}
		
		for (int i = 0; i < rangeCnt.length; i++) {
			if (rangeCnt[i] == 3 && i == range) {
				isAppear = true;
				break;
			}
		}
		
		return isAppear;
	}
	
	/**
	 * 구간별 출현회수 목록 조회
	 * 2020.03.28
	 * 
	 * @param numbers
	 * @param range 특정구간 <br>0: 1~9<br>1: 10~19<br>2: 20~29<br>3: 30~39<br>4: 40~45
	 * @return
	 */
	public int[] getRange3Numbers(int[] numbers) { 
		// 체크
		int[] rangeCnt = {0,0,0,0,0};
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] < 10 ) {
				rangeCnt[0] += 1; 
			} else if (numbers[i] < 20 ) {
				rangeCnt[1] += 1;
			} else if (numbers[i] < 30 ) {
				rangeCnt[2] += 1;
			} else if (numbers[i] < 40 ) {
				rangeCnt[3] += 1;
			} else {
				rangeCnt[4] += 1;
			}
		}
		
		return rangeCnt;
	}
	
	/**
	 * 같은번호 3개 존재 확인
	 * checkNumber이 19일 때는 30,40 구간만 확인
	 * 2020.02.29
	 * 
	 * @param sourceNumbers
	 * @return
	 */
	public boolean check1Range3Numbers(int[] sourceNumbers, int checkNumber) {
		// 체크
		boolean isAppear = false;	// 출현여부
		int[] rangeCnt = {0,0};
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] < 10 ) {
				continue; 
			} else if (sourceNumbers[i] < 20 ) {
				continue;
			} else if (sourceNumbers[i] < 30 ) {
				continue;
			} else if (sourceNumbers[i] < 40 ) {
				rangeCnt[0] += 1;
			} else {
				rangeCnt[1] += 1;
			}
		}
		
		if (rangeCnt[0] == 3 || rangeCnt[1] == 3) {
			isAppear = true;
		}
		
		return isAppear;
	}

	/**
	 * 필터 추가 (from 나대길)
	 * 2020.02.29
	 * 
	 * @param theoryNumber 가설번호
	 * @param winDataList 당첨번호 전체 목록 (오름차순)
	 * @param fromCount 체크할 이전회차 수
	 * @param aimMatchedPer 목표적중율
	 * @return true : 필터적용, false : 필터무시
	 */
	public boolean getMatchedPer(int theoryNumber, List<WinDataDto> winDataList, int fromCount, int aimMatchedPer) {
		double matchedPer = 0.0; 
		
		if (theoryNumber == 0) {
			matchedPer = getTheory0MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory0MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 1) {
			matchedPer = getTheory1MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory1MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 2) {
			matchedPer = getTheory2MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory2MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 3) {
			matchedPer = getTheory3MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory3MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 4) {
			matchedPer = getTheory4MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory4MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 5) {
			matchedPer = getTheory5MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory5MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 6) {
			matchedPer = getTheory6MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory6MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 7) {
			matchedPer = getTheory7MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory7MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 8) {
			matchedPer = getTheory8MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory8MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 9) {
			matchedPer = getTheory9MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory9MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 10) {
			matchedPer = getTheory10MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory10MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 11) {
			matchedPer = getTheory11MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory11MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 12) {
			matchedPer = getTheory12MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory12MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 13) {
			matchedPer = getTheory13MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory13MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 14) {
			matchedPer = getTheory14MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory14MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 17) {
			matchedPer = getTheory17MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory17MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 18) {
			matchedPer = getTheory18MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory18MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 19) {
			matchedPer = getTheory19MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory19MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 20) {
			matchedPer = getTheory20MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory20MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 21) {
			matchedPer = getTheory21MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory21MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 22) {
			matchedPer = getTheory22MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory22MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 23) {
			matchedPer = getTheory23MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory23MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 24) {
			matchedPer = getTheory24MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory24MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 25) {
			matchedPer = getTheory25MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory25MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 26) {
			matchedPer = getTheory26MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory26MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 27) {
			matchedPer = getTheory27MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory27MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 28) {
			matchedPer = getTheory28MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory28MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 29) {
			matchedPer = getTheory29MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory29MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		} else if (theoryNumber == 30) {
			matchedPer = getTheory30MatchedPer(winDataList, fromCount);
			log.info("[가설 " + theoryNumber + "] 1차 최근 " + fromCount + "회 적중율 = " + matchedPer + "%");
			
			// 목표적중율 미달 시 전체 대상으로 매칭율 조회
			if (aimMatchedPer > (int) matchedPer) {
				fromCount = 0;
				matchedPer = getTheory30MatchedPer(winDataList, fromCount);
				log.info("[가설 " + theoryNumber + "] 2차 매칭적중율 = " + matchedPer + "%");
			}
			log.info("[가설 " + theoryNumber + "]\t> 최종 매칭적중율 = " + matchedPer + "%");
		}
		
		return aimMatchedPer <= (int) matchedPer;
	}
	
	/**
	 * 가설30. 
	 * 40번대(40~45) 보너스가 출현하면, 구간수 3개가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory30MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;

			// 최근회차에서 보너스번호 40번대 출현 확인
			int sourceBonusNumber = sourceWinDataDto.getBonus_num(); 
			if (40 <= sourceBonusNumber && sourceBonusNumber <= 45) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				// 구간별 출현회수 목록 조회
				int[] rangeCnt = this.getRange3Numbers(targetNumbers);
				for (int i = 0; i < rangeCnt.length; i++) {
					if (rangeCnt[i] == 3) {
						matchedCnt++;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설29. 
	 * 28번이 출현하면, 28번이 이월되지 않는다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory29MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;
			
			// 최근회차에서 28번 출현 확인
			for (int i = 0; i < sourceNumbers.length-1; i++) {
				if (sourceNumbers[i] == 28) {
					allAppearCnt++;
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				boolean isAppear28 = false;
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] == 28) {
						isAppear28 = true;
						break;
					}
				}
				
				if (!isAppear28) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설28. 
	 * 15번이 출현하면, 19번이 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory28MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;
			
			// 최근회차에서 15번 출현 확인
			for (int i = 0; i < sourceNumbers.length-1; i++) {
				if (sourceNumbers[i] == 15) {
					allAppearCnt++;
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] == 19) {
						matchedCnt++;
						break;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설27. 
	 * 10번대 연번이 출현하면, 1끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory27MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 최근회차에서 10번대 연번 출현 확인
			boolean isConsecutively = false;	// 전회차 연번 포함여부
			for (int i = 0; i < sourceNumbers.length-1; i++) {
				if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
					// 끝수 존재여부 체크
					if (i+2 < sourceNumbers.length) {
						if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
							// 다음수가 3연속은 규칙 제외
							isConsecutively = false;
							break;
						}
					} else if (i-1 >= 0) {
						if(sourceNumbers[i] - sourceNumbers[i-1] == 1) {
							// 이전수가 3연속은 규칙 제외
							isConsecutively = false;
							break;
						}
					}
					
					// 10번대 연번 체크
					if ((10 <= sourceNumbers[i] && sourceNumbers[i] < 20) 
							&& (10 <= sourceNumbers[i+1] && sourceNumbers[i+1] < 20)) {
						allAppearCnt++;
						isConsecutively = true;
						break;
					}
				}
			}
			
			if (isConsecutively) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 1) {
						matchedCnt++;
						break;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설26. 
	 * 20구간 3개 출현하면, 단번대(1~9)가 출현하지 않는다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory26MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;
			/*
			 * 같은번호 3개 존재 확인
			 * 특정구간 (range) 
			 * 0: 1~9
			 * 1: 10~19
			 * 2: 20~29
			 * 3: 30~39
			 * 4: 40~45
			 */
			int range = 2;
			boolean isRange3Numbers = this.checkRange3Numbers(sourceNumbers, range);
			// 20구간(20 ~ 29) 3수 출현 확인
			if (isRange3Numbers) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				// 구간별 출현회수 목록 조회
				int[] rangeCnt = this.getRange3Numbers(targetNumbers);
				// 단번대(1~9) 미출 체크
				if (rangeCnt[0] == 0) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설25. 
	 * 20구간 3개 출현하고 8,2,4 끝수가 출현하면 다음회차에 8,2,4끝수가 출현하지 않는다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory25MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;
			/*
			 * 같은번호 3개 존재 확인
			 * 특정구간 (range) 
			 * 0: 1~9
			 * 1: 10~19
			 * 2: 20~29
			 * 3: 30~39
			 * 4: 40~45
			 */
			int range = 2;
			boolean isRange3Numbers = this.checkRange3Numbers(sourceNumbers, range);
			// 20구간(20 ~ 29) 3수 출현 확인
			if (isRange3Numbers) {
				// 최근회차 출현 확인
				boolean isAppearEndNum8 = false;
				boolean isAppearEndNum2 = false;
				boolean isAppearEndNum4 = false;
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (sourceNumbers[i] % 10 == 2) {
						isAppearEndNum2 = true;
					}
					if (sourceNumbers[i] % 10 == 4) {
						isAppearEndNum4 = true;
					}
					if (sourceNumbers[i] % 10 == 8) {
						isAppearEndNum8 = true;
					}
				}
				
				if (isAppearEndNum2 && isAppearEndNum4 && isAppearEndNum8) {
					allAppearCnt++;
					isAppear = true;
				}
			}
			
			if (isAppear) {
				boolean isAppearEndNum8 = false;
				boolean isAppearEndNum2 = false;
				boolean isAppearEndNum4 = false;
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 2) {
						isAppearEndNum2 = true;
					}
					if (targetNumbers[i] % 10 == 4) {
						isAppearEndNum4 = true;
					}
					if (targetNumbers[i] % 10 == 8) {
						isAppearEndNum8 = true;
					}
				}
				
				if (!isAppearEndNum2 && !isAppearEndNum4 && !isAppearEndNum8) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설24. 
	 * 20구간 3개 출현시, 8,2,4 끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory24MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			boolean isAppear = false;
			/*
			 * 같은번호 3개 존재 확인
			 * 특정구간 (range) 
			 * 0: 1~9
			 * 1: 10~19
			 * 2: 20~29
			 * 3: 30~39
			 * 4: 40~45
			 */
			int range = 2;
			boolean isRange3Numbers = this.checkRange3Numbers(sourceNumbers, range);
			// 20구간(20 ~ 29) 3수 출현 확인
			if (isRange3Numbers) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				boolean isAppearEndNum8 = false;
				boolean isAppearEndNum2 = false;
				boolean isAppearEndNum4 = false;
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 2) {
						isAppearEndNum2 = true;
					}
					if (targetNumbers[i] % 10 == 4) {
						isAppearEndNum4 = true;
					}
					if (targetNumbers[i] % 10 == 8) {
						isAppearEndNum8 = true;
					}
				}
				
				if (isAppearEndNum2 && isAppearEndNum4 && isAppearEndNum8) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설23. 
	 * 3끝수가 4회연속 출현하면, 동일한 끝수(가족수)가 2개이상 출현한다
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory23MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 4; countIdx++) {
			
			WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);	// 3회차 전
			WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+1);	// 2회차 전
			WinDataDto sourceWinDataDto3 = winDataList.get(countIdx+2);	// 1회차 전
			WinDataDto sourceWinDataDto4 = winDataList.get(countIdx+3);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+4);	// 다음회차
			
			int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
			int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
			int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
			int[] sourceNumbers4 = LottoUtil.getNumbers(sourceWinDataDto4);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 3끝수 연속 4회 출현 확인
			boolean isAppear = false;
			boolean isAppear1EndNum3 = false;
			boolean isAppear2EndNum3 = false;
			boolean isAppear3EndNum3 = false;
			boolean isAppear4EndNum3 = false;
			
			for (int i = 0; i < sourceNumbers1.length; i++) {
				if (sourceNumbers1[i] % 10 == 3) {
					isAppear1EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers2.length; i++) {
				if (sourceNumbers2[i] % 10 == 3) {
					isAppear2EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers3.length; i++) {
				if (sourceNumbers3[i] % 10 == 3) {
					isAppear3EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers4.length; i++) {
				if (sourceNumbers4[i] % 10 == 3) {
					isAppear4EndNum3 = true;
					break;
				}
			}
			
			if (isAppear1EndNum3 && isAppear2EndNum3 && isAppear3EndNum3 && isAppear4EndNum3) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				int appearCnt = 0;
				Map<Integer, Integer> endNumMap = new HashMap<Integer, Integer>();
				for (int i = 0; i < targetNumbers.length; i++) {
					int endNum = targetNumbers[i] % 10;
					if (endNumMap.containsKey(endNum)) {
						// 가족수 존재함
						appearCnt = endNumMap.get(endNum) + 1;
					} else {
						endNumMap.put(endNum, 1);
					}
				}
				
				if (appearCnt > 1) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설22. 
	 * 3끝수가 4회연속 출현하면, 4회째 출현번호 중 1개가 다시 출현한다
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory22MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 4; countIdx++) {
			
			WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);	// 3회차 전
			WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+1);	// 2회차 전
			WinDataDto sourceWinDataDto3 = winDataList.get(countIdx+2);	// 1회차 전
			WinDataDto sourceWinDataDto4 = winDataList.get(countIdx+3);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+4);	// 다음회차
			
			int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
			int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
			int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
			int[] sourceNumbers4 = LottoUtil.getNumbers(sourceWinDataDto4);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 3끝수 연속 4회 출현 확인
			boolean isAppear = false;
			boolean isAppear1EndNum3 = false;
			boolean isAppear2EndNum3 = false;
			boolean isAppear3EndNum3 = false;
			boolean isAppear4EndNum3 = false;
			
			for (int i = 0; i < sourceNumbers1.length; i++) {
				if (sourceNumbers1[i] % 10 == 3) {
					isAppear1EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers2.length; i++) {
				if (sourceNumbers2[i] % 10 == 3) {
					isAppear2EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers3.length; i++) {
				if (sourceNumbers3[i] % 10 == 3) {
					isAppear3EndNum3 = true;
					break;
				}
			}
			for (int i = 0; i < sourceNumbers4.length; i++) {
				if (sourceNumbers4[i] % 10 == 3) {
					isAppear4EndNum3 = true;
					break;
				}
			}
			
			if (isAppear1EndNum3 && isAppear2EndNum3 && isAppear3EndNum3 && isAppear4EndNum3) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				int appearCnt = 0;
				for (int i = 0; i < targetNumbers.length; i++) {
					for (int j = 0; j < sourceNumbers4.length; j++) {
						if (targetNumbers[i] == sourceNumbers4[j]) {
							isAppear4EndNum3 = true;
							appearCnt++;
							break;
						}
					}	
				}
				if (appearCnt > 0) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설21. 
	 * 24번호 출현시(보너스 포함), 2,8,7 끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory21MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 최근회차 24 출현 확인
			boolean isAppear = false;
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 24) {
					allAppearCnt++;
					isAppear = true;
				}
			}
			
			if (!isAppear) {
				if (sourceWinDataDto.getBonus_num() == 24) {
					allAppearCnt++;
					isAppear = true;
				}
			}
			
			if (isAppear) {
				boolean isAppearEndNum2 = false;
				boolean isAppearEndNum7 = false;
				boolean isAppearEndNum8 = false;
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 2) {
						isAppearEndNum2 = true;
					}
					if (targetNumbers[i] % 10 == 7) {
						isAppearEndNum7 = true;
					}
					if (targetNumbers[i] % 10 == 8) {
						isAppearEndNum8 = true;
					}
				}
				
				if (isAppearEndNum2 && isAppearEndNum7 && isAppearEndNum8) {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설20. 
	 * 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현하면, 1회차 전의 번호 중 1개가 다시 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory20MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 3; countIdx++) {
			
			WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);	// 1회차 전
			WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+1);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+2);	// 다음회차
			
			int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
			int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
			int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
			int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 1회차 전 10번대, 20번대 번호 설정
			int[] bf10Num = new int[sourceZeroCntRange1[1]];					
			int[] bf20Num = new int[sourceZeroCntRange1[2]];
			
			// 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
			boolean isAppear = false;
			boolean isAppear10ConsecutivelyNumber = false;
			boolean isAppear20ConsecutivelyNumber = false;
			
			// 10번대 20번대 출현여부 체크
			if (sourceZeroCntRange1[1] >= 1 && sourceZeroCntRange1[2] >= 1
					&& sourceZeroCntRange2[1] >= 1 && sourceZeroCntRange2[2] >= 1
					) {
				int bf10Index = 0;
				int bf20Index = 0;
				for (int i = 0; i < sourceNumbers1.length; i++) {
					if (10 < sourceNumbers1[i] && sourceNumbers1[i] <= 20) {
						bf10Num[bf10Index++] = sourceNumbers1[i]; 
					} else if (20 < sourceNumbers1[i] && sourceNumbers1[i] <= 30) {
						bf20Num[bf20Index++] = sourceNumbers1[i]; 
					}
				}
				
				// 최근회차에서 연번으로 출현여부 체크
				for (int i = 0; i < sourceNumbers2.length; i++) {
					if (10 < sourceNumbers2[i] && sourceNumbers2[i] <= 20) {
						// 10번대 연번 출현 확인
						for (int j = 0; j < bf10Num.length; j++) {
							if (sourceNumbers2[i] == (bf10Num[j] + 1)) {
								isAppear10ConsecutivelyNumber = true;									
							}
						} 
					} else if (20 < sourceNumbers2[i] && sourceNumbers2[i] <= 30) {
						// 20번대 연번 출현 확인
						for (int j = 0; j < bf20Num.length; j++) {
							if (sourceNumbers2[i] == (bf20Num[j] + 1)) {
								isAppear20ConsecutivelyNumber = true;									
							}
						}
					}
				}
				
				if (isAppear10ConsecutivelyNumber
						&& isAppear20ConsecutivelyNumber) {
					allAppearCnt++;
					isAppear = true;
				}
			}
			
			if (isAppear) {
				for (int i = 0; i < targetNumbers.length; i++) {
					boolean isAppear2 = false;
					
					// 10번대 번호 재출현 여부 체크
					for (int j = 0; j < bf10Num.length; j++) {
						if (targetNumbers[i] == bf10Num[j]) {
							matchedCnt++;
							isAppear2 = true;
							break;
						}
					}
					
					if (isAppear2) {
						break;
					} else {
						// 20번대 번호 재출현 여부 체크
						for (int j = 0; j < bf20Num.length; j++) {
							if (targetNumbers[i] == bf20Num[j]) {
								matchedCnt++;
								isAppear2 = true;
								break;
							}
						}
						
						if (isAppear2) {
							break;
						}
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설19. 
	 * 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현하면, 3끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory19MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 3; countIdx++) {
			
			WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);	// 2회차 전
			WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+2);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+3);	// 다음회차
			
			int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
			int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			
			// 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현 확인
			boolean isAppear = false;
			
			// 단번대 출현 체크
			if (sourceZeroCntRange1[0] >= 1
					&& sourceZeroCntRange2[0] >= 1) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 3) {
						matchedCnt++;
						break;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설18. 
	 * 5와 8끝수가 출현하면, 다음회 6끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory18MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			
			// 5번과 8끝수 출현 확인
			boolean isAppear = false;
			boolean isAppear5 = false;
			boolean isAppear8End = false;
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 5) {
					isAppear5 = true;
				}
				if (sourceNumbers[i] % 10 == 8) {
					isAppear8End = true;
				}
			}
			
			if (isAppear5 && isAppear8End) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 6) {
						matchedCnt++;
						break;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설17. 
	 * 단번대가 1개씩 3회연속 출현하면,4회째에 단번대의 배수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory17MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 출현빈도가 낮아 전체를 대상으로 목표치 측정하도록 처리
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 3; countIdx++) {
			
			WinDataDto sourceWinDataDto1 = winDataList.get(countIdx);
			WinDataDto sourceWinDataDto2 = winDataList.get(countIdx+1);
			WinDataDto sourceWinDataDto3 = winDataList.get(countIdx+2);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+3);
			
			int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
			int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
			int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
			int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
			int[] sourceZeroCntRange3 = this.getZeroCntRangeData(sourceWinDataDto3);
			
			
			// 단번대 1개씩 3회 출현 확인
			boolean isAppear = false;
			
			if (sourceZeroCntRange1[0] == 1
					&& sourceZeroCntRange2[0] == 1
					&& sourceZeroCntRange3[0] == 1
					) {
				allAppearCnt++;
				isAppear = true;
			}
			
			if (isAppear) {
				int num1 = sourceNumbers1[0];
				int num2 = sourceNumbers2[0];
				int num3 = sourceNumbers3[0];
				
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % num1 == 0
							|| targetNumbers[i] % num2 == 0
							|| targetNumbers[i] % num3 == 0
							) {
						matchedCnt++;
						break;
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설14. 
	 * 3회차 전부터 1씩 감소하는 수가 3회동안 출현하면, 다음 회차에서 8끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory14MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 3; countIdx++) {
			
			WinDataDto source3WinDataDto = winDataList.get(countIdx);
			WinDataDto source2WinDataDto = winDataList.get(countIdx+1);
			WinDataDto source1WinDataDto = winDataList.get(countIdx+2);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+3);
			
			int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
			int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
			int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 3회차 전부터 1씩 감소하는 수 출현여부 확인
			// 시작회차의 첫번째수부터 차례대로 확인
			boolean isAppear = false;
			for (int j = 0; j < source3Numbers.length; j++) {
				for (int k = 0; k < source2Numbers.length; k++) {
					if (source3Numbers[j] - 1 == source2Numbers[k] || source3WinDataDto.getBonus_num() - 1 == source2Numbers[k]) {
						for (int l = 0; l < source1Numbers.length; l++) {
							if (source2Numbers[k] - 1 == source1Numbers[l]) {
								allAppearCnt++;
								
								// 다음회차에서 8끝수가 출현
								for (int m = 0; m < targetNumbers.length; m++) {
									if (targetNumbers[m] % 10 == 8) {
										matchedCnt++;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설13. 
	 * 4회차 전부터 1씩 감소하는 수가 4회 출현 후 다음 2 적은수가 출현하면, 다음 회차에서 빠진수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory13MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 5; countIdx++) {
			
			WinDataDto source5WinDataDto = winDataList.get(countIdx);
			WinDataDto source4WinDataDto = winDataList.get(countIdx+1);
			WinDataDto source3WinDataDto = winDataList.get(countIdx+2);
			WinDataDto source2WinDataDto = winDataList.get(countIdx+3);
			WinDataDto source1WinDataDto = winDataList.get(countIdx+4);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+5);
			
			int[] source5Numbers = LottoUtil.getNumbers(source5WinDataDto);
			int[] source4Numbers = LottoUtil.getNumbers(source4WinDataDto);
			int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
			int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
			int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 4회차 전부터 1씩 감소하고 다음 2 적은 수 출현여부 확인
			// 시작회차의 첫번째수부터 차례대로 확인
			boolean isAppear = false;
			for (int h = 0; h < source5Numbers.length; h++) {
				for (int i = 0; i < source4Numbers.length; i++) {
					if (source5Numbers[h] - 1 == source4Numbers[i] || source5WinDataDto.getBonus_num() - 1 == source4Numbers[i]) {
						for (int j = 0; j < source3Numbers.length; j++) {
							if (source4Numbers[i] - 1 == source3Numbers[j]) {
								for (int k = 0; k < source2Numbers.length; k++) {
									if (source3Numbers[j] - 1 == source2Numbers[k]) {
										for (int l = 0; l < source1Numbers.length; l++) {
											// 2 적은 수 확인
											if (source2Numbers[k] - 2 == source1Numbers[l]) {
												allAppearCnt++;
												
												// 다음회차에서 빠진수가 출현
												for (int m = 0; m < targetNumbers.length; m++) {
													if (source2Numbers[k] - 1 == targetNumbers[m]) {
														matchedCnt++;
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설12. 
	 * 38번이 출현하면, 26 또는 29가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory12MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 38번 출현 확인
			boolean isAppear = false;
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 38) {
					allAppearCnt++;
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] == 26 || targetNumbers[i] == 29) {
						matchedCnt++;
						break;
					}
				}
			}
			
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설11. 
	 * 10구간 3수 출현하면, 마지막 수 합의 배수 출현(예: 18 -> 18, 27, 36, 45)
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory11MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			
			boolean isRange3Numbers = this.checkRange3Numbers(sourceNumbers, 1);
			// 10구간(10 ~ 19) 3수 출현 확인
			if (isRange3Numbers) {
				allAppearCnt++;
				
				int max10RangeNumber = 0; 
				for (int i = 0; i < sourceNumbers.length; i++) {
					int number = sourceNumbers[i];
					// 10구간의 가장 큰 수 설정
					if (10 <= number && number <= 19 ) {
						if (max10RangeNumber < number) {
							max10RangeNumber = number;
						}
					}
				}
				
				String strNumber = String.valueOf(max10RangeNumber);
				int sumNumber = Integer.parseInt(strNumber.substring(1, 2)) + Integer.parseInt(strNumber.substring(0, 1));
				for (int i = 0; i < targetNumbers.length; i++) {
					// 단번대 미포함
					if (10 <= targetNumbers[i] && targetNumbers[i] % sumNumber == 0) {
//					if (targetNumbers[i] % sumNumber == 0) {
						isAppear = true;
						break;
					}
				}
				
				if (isAppear) {
					matchedCnt++;
				}
			}
			
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}

	/**
	 * 가설10. 
	 * 40번대 멸 이면, 당첨번호의 앞뒤 바뀐수가 출현한다.(예: 21 <-> 12)
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory10MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] sourceZeroCntRange = this.getZeroCntRangeData(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			// 40대 멸 체크
			if (sourceZeroCntRange[4] == 0) {
				allAppearCnt++;
				
				// 출현번호의 역번호 Map 설정 (출현여부 확인용)
				Map<Integer, Integer> reverseNumberMap = new HashMap<Integer, Integer>(); 
				for (int i = 0; i < sourceNumbers.length; i++) {
					int number = sourceNumbers[i];
					// 2자리수만 확인
					if (number >= 10) {
						String strNumber = String.valueOf(number);
						String strReverseNumber = strNumber.substring(1, 2) + strNumber.substring(0, 1);
						int reverseNumber = Integer.parseInt(strReverseNumber);
						if (reverseNumber <= 45) {
							reverseNumberMap.put(reverseNumber, reverseNumber);
						}
					}
				}
				
				for (int i = 0; i < targetNumbers.length; i++) {
					if (reverseNumberMap.containsKey(targetNumbers[i])) {
						isAppear = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (isAppear) {
					matchedCnt++;
//					log.info((countIdx+1) + "회 40번대 멸 (" + (sourceZeroCntRange[4] == 0) + ")");
//					log.info((countIdx+2) + "회 역번호 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
			
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설9.
	 * 3연속수가 출현하면, 1구간 3수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory9MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
//			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			int[] targetZeroCntRange = this.getZeroCntRangeData(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String consecutivelyNumbers = ""; 
//			String appearNumber = ""; 
			
			for (int i = 0; i < sourceNumbers.length - 2; i++) {
				if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
					consecutivelyNumbers += sourceNumbers[i];
					
					if (!"".equals(consecutivelyNumbers)) {
						consecutivelyNumbers += ",";
					}
					consecutivelyNumbers += sourceNumbers[i+1];
					
					// 끝수 존재여부 체크
					if (i+2 < sourceNumbers.length) {
						if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
							// 다음수가 3연속 확인
							isAppear = true;
							
							if (!"".equals(consecutivelyNumbers)) {
								consecutivelyNumbers += ",";
							}
							consecutivelyNumbers += sourceNumbers[i+2];
							
							break;
						}
					}
				}
			}
			
			if (isAppear) {
				allAppearCnt++;
				
				boolean isAppear2 = false;	// 출현여부
				
				// 1구간 3수 출현여부 체크
				String[] rangeTitle = {"1~10","11~20","21~30","31~40","41~45"};
				int appearIndex = 0;
				for (int i = 0; i < targetZeroCntRange.length; i++) {
					if (targetZeroCntRange[i] == 3) {
						isAppear2 = true;
						appearIndex = i;
					}
				}
				
				if (isAppear2) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 " + rangeTitle[appearIndex] + "구간 3수 출현");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설8.
	 * 8번이 출현하면, 8배수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory8MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 8) {
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				allAppearCnt++;
				
				boolean isAppear2 = false;	// 출현여부
				// 0끝수 출현여부 체크
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 8 == 0) {
						isAppear2 = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (targetWinDataDto.getBonus_num() % 8 == 0) {
					isAppear2 = true;
					
					if (!"".equals(appearNumber)) {
						appearNumber += ",";
					}
					appearNumber += targetWinDataDto.getBonus_num(); 
				}
				
				if (isAppear2) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 8배수 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설7.
	 * 쌍수(11,22,33,44)가 나오면, 자기번호나 가족번호(끝수가 같은)가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory7MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			int checkNumber = 0;
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 11
						|| sourceNumbers[i] == 22
						|| sourceNumbers[i] == 33
						|| sourceNumbers[i] == 44
						) {
					isAppear = true;
					checkNumber = sourceNumbers[i]; 
					break;
				}
			}
			
			if (isAppear) {
				allAppearCnt++;
				
				boolean isAppear2 = false;	// 출현여부
				// 0끝수 출현여부 체크
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == checkNumber % 10) {
						isAppear2 = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (isAppear2) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 쌍수의 가족수 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설6. 19번호 이후 0끝 번호가 출현한다. 
	 * 		  뒤에 (1구간 3수)가 나오면 뒤에는 0끝이 출현하지 않는다. (2020.02.29)
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory6MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 19) {
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				allAppearCnt++;
			
				boolean isAppear2 = false;	// 출현여부
				// 0끝수 출현여부 체크
				boolean is1Range3CNumbers = this.check1Range3Numbers(sourceNumbers);
				String msg = "";
				if (is1Range3CNumbers) {
					// 3연수 있음.
					int appearCnt = 0;
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == 0) {
							appearCnt++;
						}
					}
					
					if (appearCnt == 0) {
						isAppear2 = true;
						
						msg = "3연수 있음. 0끝수 미출현";
					}
					
				} else {
					// 3연수 없음.
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] % 10 == 0) {
							isAppear2 = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i];
							break;
						}
					}
					
					msg = "0끝수 출현(" + appearNumber + ")";
				}
				
				if (isAppear2) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 " + msg);
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설5. 
	 * 28번이 출현하면, 4끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory5MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			int checkNumber = 0;
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 28) {
					isAppear = true;
					break;
				}
			}
			
			if (isAppear) {
				allAppearCnt++;
				
				boolean isAppear2 = false;	// 출현여부
				// 4끝수 출현여부 체크
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 4) {
						isAppear2 = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (isAppear2) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 4끝수 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설4.
	 * 42번이 나오면 42의 앞 2번째 수 -1이 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory4MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			boolean isAppear42 = false;	// 42출현여부
			String appearNumber = ""; 
			
			int checkNumber = 0;
			
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (sourceNumbers[i] == 42) {
					isAppear42 = true;
					if (i >= 2) {
						// 앞 2번째 수 - 1
						checkNumber = sourceNumbers[i-2] - 1; 
					}
					break;
				}
			}
			
			if (isAppear42) {
				allAppearCnt++;
				
				// 앞 2번째 수-1 출현여부 체크
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] == checkNumber) {
						isAppear = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (isAppear) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 앞 2번째 수-1 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설3.
	 * 20번대(20~29) 2개 & 30번대(30~39) 2개가 나오면, 다음 회차에서는 0끝수가 1개 이상 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory3MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			
			int appear2RangeCnt = 0;
			int appear3RangeCnt = 0;
			
			String numbers = "";
			for (int i = 0; i < sourceNumbers.length; i++) {
				if (20 <= sourceNumbers[i] && sourceNumbers[i] <= 29) {
					appear2RangeCnt++;
				}
				if (30 <= sourceNumbers[i] && sourceNumbers[i] <= 39) {
					appear3RangeCnt++;
				}
				
				if (!"".equals(numbers)) {
					numbers += ",";
				}
				numbers += sourceNumbers[i];
			}
			
			
			if (appear2RangeCnt == 2 && appear3RangeCnt == 2) {
				allAppearCnt++;
				
//				log.info((countIdx+1) + "회 (" + numbers + ")");
//				log.info((countIdx+1) + "회 구간수 출현(" + appear2RangeCnt + ", " + appear3RangeCnt + ")");
				
				// 0끝수 출현여부 체크
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == 0) {
						isAppear = true;
						
						if (!"".equals(appearNumber)) {
							appearNumber += ",";
						}
						appearNumber += targetNumbers[i];
					}
				}
				
				if (isAppear) {
					matchedCnt++;
//					log.info((countIdx+2) + "회 0끝수 출현(" + appearNumber + ")");
//					log.info("==============================================================");		
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}
	
	/**
	 * 가설2.
	 * 단번대 멸 & 첫수가 10번대 라면, 당첨숫자 4, 5, 6번째 중 1개가 이월된다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory2MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0;
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] sourceZeroCntRange = this.getZeroCntRangeData(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;	// 출현여부
			String appearNumber = ""; 
			String numbers = ""; 
			
			// 단번대 멸 체크
			
			if (sourceZeroCntRange[0] == 0) {
				for (int i = 0; i < sourceNumbers.length; i++) {
					if (!"".equals(numbers)) {
						numbers += ",";
					}
					numbers += sourceNumbers[i];
				}
				
				// 첫번째 수가 10번대 체크
//							if (11 <= targetNumbers[0] || targetNumbers[0] <= 20) {
				if (10 <= sourceNumbers[0] && sourceNumbers[0] <= 19) {
					allAppearCnt++;
					
					//지난 당첨번호 3수
					int num4 = sourceNumbers[3];
					int num5 = sourceNumbers[4];
					int num6 = sourceNumbers[5];
					
					//이월여부 체크
					for (int i = 0; i < targetNumbers.length; i++) {
						if (targetNumbers[i] == num4
								|| targetNumbers[i] == num5
								|| targetNumbers[i] == num6
								) {
							isAppear = true;
							
							if (!"".equals(appearNumber)) {
								appearNumber += ",";
							}
							appearNumber += targetNumbers[i]; 
						}
					}
					
					if (isAppear) {
						matchedCnt++;
//						log.info((countIdx+1) + "회 (" + numbers + ")");
//						log.info((countIdx+2) + "회 이월수 출현(" + appearNumber + ")");
//						log.info("==============================================================");		
					}
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
					
		return matchedPer;
	}

	/**
	 * 가설1.
	 * 10차이나는 수 사이에 다른 숫자가 있으면, 다음회차에서 큰 수에서 중간수를 뺀 끝수가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory1MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0; 
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			boolean isAppear = false;
//			int appearCnt = 0;
//			boolean isCheckAppear = false;
//			boolean isCheckMatch = false;
			for (int i = 0; i < sourceNumbers.length - 2; i++) {
//				isCheckAppear = false;
				int num1 = sourceNumbers[i];
				int num2 = sourceNumbers[i+1];
				int num3 = sourceNumbers[i+2];
				
				if (num3 - num1 == 10) {
//					log.info((countIdx+1) + "회 " + num1 + ", " + num3 + " 존재");
					isAppear = true;
//					isCheckAppear = true;
//					appearCnt++;
					
					int difNum = num3 - num2;
					int difAppearCnt = 0;
					
//					log.info((countIdx+1) + "회 차이수 = " + difNum);
					for (int j = 0; j < targetNumbers.length; j++) {
						if (targetNumbers[j] % 10 == difNum) {
//							log.info((countIdx+2) + "회 " + targetNumbers[j] + " 존재함");
							difAppearCnt++;
						}
					}
					
					if (difAppearCnt > 0) { 
						matchedCnt++;
//						isCheckMatch = true;
					}
				}
				
//				if (isCheckAppear) {
//					if (isCheckMatch) {
//						log.info((countIdx+2) + "회 일치");
//					} else {
//						log.info((countIdx+2) + "회 불일치");
//					}
//				}
			}
			
			if (isAppear) {
//				log.info((countIdx+1) + "회 10차이나는 수 출현(" + appearCnt + "개)");
//				log.info("==============================================================");
				allAppearCnt++;
			}
			
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 가설0.
	 * 최근회차에서 전체 구간에서 출현하고 단번대가 2개 출현하고 연번도 출현하면, 다음회차에서 연번의 +2, -2 숫자가 출현한다.
	 * 
	 * @param winDataList
	 * @param fromCount
	 * @return
	 */
	private double getTheory0MatchedPer(List<WinDataDto> winDataList, int fromCount) {
		int allAppearCnt = 0;
		int matchedCnt = 0;
		double matchedPer = 0.0; 
		
		int addIndex = fromCount > 0 ? winDataList.size() - 1 - fromCount : 0;
		
		// 마지막 회차의 전회차까지만 반복해야함.
		for (int countIdx = 0 + addIndex ; countIdx < winDataList.size() - 1; countIdx++) {
			
			WinDataDto sourceWinDataDto = winDataList.get(countIdx);	// 최근회차
			WinDataDto targetWinDataDto = winDataList.get(countIdx+1);	// 다음회차
			
			int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
			int[] targetNumbers = LottoUtil.getNumbers(targetWinDataDto);
			
			// 체크
			// 최근회차에서 전체 구간 출현 확인
			int[] containGroupCnt = this.getZeroCntRangeData(sourceWinDataDto);
			int isAllCnt = 0;
			for (int i = 0; i < containGroupCnt.length; i++) {
				if (containGroupCnt[i] > 0) {
					isAllCnt++;
				}
			}
			
			// 최근회차에서 단번대 2개 출현 확인
			if (isAllCnt == 5) {
//				log.info("이전 회차의 전체구간 출현. 단번대 번호 수 체크 : 2개 출현 확인");
				containGroupCnt = this.getZeroCntRangeData(sourceWinDataDto);
//				if (containGroupCntExData[0] > 2 || containGroupCntExData[0] == 0) {
				if (containGroupCnt[0] != 2 ) {
//					log.info("단번대 번호 수 불충족 : " + containGroupCnt[0]);
					continue;
				}
			} else {
				continue;
			}
			
			// 최근회차에서 연번 출현 확인
			boolean bfIsConsecutively = false;	// 전회차 연번 포함여부
			String consecutivelyNumbers = "";
			List<Integer> consecutivelyNumberList = new ArrayList<Integer>();
			for (int i = 0; i < sourceNumbers.length-1; i++) {
				if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
					// 끝수 존재여부 체크
					if (i+2 < sourceNumbers.length) {
						if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
							// 다음수가 3연속은 규칙 제외
							bfIsConsecutively = false;
							consecutivelyNumbers = "";
							consecutivelyNumberList = new ArrayList<Integer>();
							break;
						}
					} else if (i-1 >= 0) {
						if(sourceNumbers[i] - sourceNumbers[i-1] == 1) {
							// 이전수가 3연속은 규칙 제외
							bfIsConsecutively = false;
							consecutivelyNumbers = "";
							consecutivelyNumberList = new ArrayList<Integer>();
							break;
						}
					}
					
					
					// 연번
					consecutivelyNumberList.add(sourceNumbers[i]);
					consecutivelyNumbers += sourceNumbers[i];
					consecutivelyNumberList.add(sourceNumbers[i+1]);
					consecutivelyNumbers += ",";
					consecutivelyNumbers += sourceNumbers[i+1];
					
					bfIsConsecutively = true;
				}
			}
			
			if (bfIsConsecutively) {
				allAppearCnt++;
				
				boolean isContainConsecutivelyNumber = false;
				for (int j = 0; j < consecutivelyNumberList.size(); j++) {
					int consecutivelyNumber = consecutivelyNumberList.get(j);
					
					for (int k = 0; k < targetNumbers.length; k++) {
						int num = targetNumbers[k];
						if (consecutivelyNumber + 2 == num
								|| consecutivelyNumber - 2 == num
								) {
							isContainConsecutivelyNumber = true;
							break;
						}
					}
					
					if (isContainConsecutivelyNumber) {
						break;
					}
				}
				
				if (!isContainConsecutivelyNumber) {
					// 연번 출현규칙이 없으면 다음번호 조합
					continue;
				} else {
					matchedCnt++;
				}
			}
		}
		
		if (allAppearCnt > 0) {
			matchedPer = Math.round(matchedCnt * 1.0 / allAppearCnt * 100);
		}
		
		return matchedPer;
	}
	
	/**
	 * 필터0. 
	 * 최근회차에서 전체 구간에서 출현하고 단번대가 2개 출현하고 연번도 출현하면, 다음회차에서 연번의 +2, -2 숫자가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter0(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);	// 최근회차
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		// 최근회차에서 연번 출현 확인
		boolean bfIsConsecutively = false;	// 전회차 연번 포함여부
//		String consecutivelyNumbers = "";
		List<Integer> consecutivelyNumberList = new ArrayList<Integer>();
		for (int i = 0; i < sourceNumbers.length-1; i++) {
			if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
				// 끝수 존재여부 체크
				if (i+2 < sourceNumbers.length) {
					if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
						// 다음수가 3연속은 규칙 제외
						bfIsConsecutively = false;
//						consecutivelyNumbers = "";
						consecutivelyNumberList = new ArrayList<Integer>();
						break;
					}
				} else if (i-1 >= 0) {
					if(sourceNumbers[i] - sourceNumbers[i-1] == 1) {
						// 이전수가 3연속은 규칙 제외
						bfIsConsecutively = false;
//						consecutivelyNumbers = "";
						consecutivelyNumberList = new ArrayList<Integer>();
						break;
					}
				}
				
				
				// 연번
				consecutivelyNumberList.add(sourceNumbers[i]);
//				consecutivelyNumbers += sourceNumbers[i];
				consecutivelyNumberList.add(sourceNumbers[i+1]);
//				consecutivelyNumbers += ",";
//				consecutivelyNumbers += sourceNumbers[i+1];
				
				bfIsConsecutively = true;
			}
		}
		
		if (bfIsConsecutively) {
			boolean isContainConsecutivelyNumber = false;
			for (int j = 0; j < consecutivelyNumberList.size(); j++) {
				int consecutivelyNumber = consecutivelyNumberList.get(j);
				
				for (int k = 0; k < targetNumbers.length; k++) {
					int num = targetNumbers[k];
					if (consecutivelyNumber + 2 == num
							|| consecutivelyNumber - 2 == num
							) {
						isContainConsecutivelyNumber = true;
						break;
					}
				}
				
				if (isContainConsecutivelyNumber) {
					break;
				}
			}
			
			if (isContainConsecutivelyNumber) {
				isMatched = true;
			}
		}
		
		return isMatched;
	}

	/**
	 * 필터1.
	 * 10차이나는 수 사이에 다른 숫자가 있으면, 다음회차에서 큰 수에서 중간수를 뺀 끝수가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter1(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		for (int i = 0; i < sourceNumbers.length - 2; i++) {
			int num1 = sourceNumbers[i];
			int num2 = sourceNumbers[i+1];
			int num3 = sourceNumbers[i+2];
			
			if (num3 - num1 == 10) {
				int difNum = num3 - num2;
				int difAppearCnt = 0;
				
				for (int j = 0; j < targetNumbers.length; j++) {
					if (targetNumbers[j] % 10 == difNum) {
						difAppearCnt++;
					}
				}
				
				if (difAppearCnt > 0) { 
					isMatched = true;
					break;
				}
			}
			
		}
		
		return isMatched;
	}
	
	/**
	 * 필터2. 
	 * 단번대 멸 & 첫수가 10번대 라면, 당첨숫자 4, 5, 6번째 중 1개가 이월된다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter2(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		//지난 당첨번호 3수
		int num4 = sourceNumbers[3];
		int num5 = sourceNumbers[4];
		int num6 = sourceNumbers[5];
		
		//이월여부 체크
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] == num4
					|| targetNumbers[i] == num5
					|| targetNumbers[i] == num6
					) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터3. 
	 * 20번대(20~29) 2개 & 30번대(30~39) 2개가 나오면, 다음 회차에서는 0끝수가 1개 이상 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter3(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 0끝수 출현여부 체크
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 0) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터4. 
	 * 42번이 나오면 42의 앞 2번째 수 -1이 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter4(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		int checkNumber = 0;
		
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] == 42) {
				if (i >= 2) {
					// 앞 2번째 수 - 1
					checkNumber = sourceNumbers[i-2] - 1; 
				}
				break;
			}
		}
		
		// 앞 2번째 수-1 출현여부 체크
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] == checkNumber) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터5. 
	 * 28번이 출현하면, 4끝수가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter5(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 4끝수 출현여부 체크
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 4) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터6. 
	 * 19번호 이후 0끝 번호가 출현한다. 
	 * 뒤에 (1구간 3수)가 나오면 뒤에는 0끝이 출현하지 않는다. (2020.02.29)
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter6(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		boolean isAppear2 = false;	// 출현여부
		// 0끝수 출현여부 체크
		boolean is1Range3CNumbers = this.check1Range3Numbers(sourceNumbers);
		if (is1Range3CNumbers) {
			// 3연수 있음.
			int appearCnt = 0;
			for (int i = 0; i < targetNumbers.length; i++) {
				if (targetNumbers[i] % 10 == 0) {
					appearCnt++;
				}
			}
			
			if (appearCnt == 0) {
				isMatched = true;
			}
			
		} else {
			// 3연수 없음.
			for (int i = 0; i < targetNumbers.length; i++) {
				if (targetNumbers[i] % 10 == 0) {
					isMatched = true;
					break;
				}
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터7. 
	 * 쌍수(11,22,33,44)가 나오면, 자기번호나 가족번호(끝수가 같은)가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter7(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		int[] checkNumber = {0,0,0,0};
		
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] == 11) {
				checkNumber[0] = 1;
			} else if (sourceNumbers[i] == 22) {
				checkNumber[1] = 2;
			} else if (sourceNumbers[i] == 33) {
				checkNumber[2] = 3;
			} else if (sourceNumbers[i] == 44) {
				checkNumber[3] = 4;
			}
		}
		
		// 0끝수 출현여부 체크
		for (int j = 0; j < checkNumber.length; j++) {
			if (checkNumber[j] > 0) {
				for (int i = 0; i < targetNumbers.length; i++) {
					if (targetNumbers[i] % 10 == checkNumber[j]) {
						isMatched = true;
					}
				}
			}
			if (isMatched) {
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터8. 
	 * 8번이 출현하면, 8배수가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter8(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 0끝수 출현여부 체크
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 8 == 0) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터9. 
	 * 3연속수가 출현하면, 1구간 3수가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter9(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		/** 각 자리의 포함개수 */
		int[] containGroupCnt = {0,0,0,0,0};
		
		//각 자리의 포함개수를 구한다.
		for(int index = 0 ; index < targetNumbers.length ; index++){
			int mok = targetNumbers[index]/10;
			/*
			 * 10의자리수는 작은 자리 수로 설정한다.
			 * 10 : 1의 자리
			 * 20 : 10의 자리
			 * 30 : 20의 자리
			 * 40 : 30의 자리
			 */
			if(mok > 0 && (targetNumbers[index] % 10 == 0)){
				mok -= 1;
			}
			containGroupCnt[mok] = containGroupCnt[mok] + 1;
		}
		
		// 1구간 3수 출현여부 체크
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] == 3) {
				isMatched = true;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터10. 
	 * 40번대 멸 이면, 당첨번호의 앞뒤 바뀐수가 출현한다.(예: 21 <-> 12)
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter10(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 출현번호의 역번호 Map 설정 (출현여부 확인용)
		Map<Integer, Integer> reverseNumberMap = new HashMap<Integer, Integer>(); 
		for (int i = 0; i < sourceNumbers.length; i++) {
			int number = sourceNumbers[i];
			// 2자리수만 확인
			if (number >= 10) {
				String strNumber = String.valueOf(number);
				String strReverseNumber = strNumber.substring(1, 2) + strNumber.substring(0, 1);
				int reverseNumber = Integer.parseInt(strReverseNumber);
				if (reverseNumber <= 45) {
					reverseNumberMap.put(reverseNumber, reverseNumber);
				}
			}
		}
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (reverseNumberMap.containsKey(targetNumbers[i])) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터11. 
	 * 10구간 3수 출현하면, 마지막 수 합의 배수 출현(예: 18 -> 18, 27, 36, 45)
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter11(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		int max10RangeNumber = 0; 
		for (int i = 0; i < sourceNumbers.length; i++) {
			int number = sourceNumbers[i];
			// 10구간의 가장 큰 수 설정
			if (10 <= number && number <= 19 ) {
				if (max10RangeNumber < number) {
					max10RangeNumber = number;
				}
			}
		}
		
		String strNumber = String.valueOf(max10RangeNumber);
		int sumNumber = Integer.parseInt(strNumber.substring(1, 2)) + Integer.parseInt(strNumber.substring(0, 1));
		for (int i = 0; i < targetNumbers.length; i++) {
			// 단번대 미포함
			if (10 <= targetNumbers[i] && targetNumbers[i] % sumNumber == 0) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터12. 
	 * 38번이 출현하면, 26 또는 29가 출현한다.
	 * 
	 * @param sourceNumbers
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter12(int[] sourceNumbers, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] == 26 || targetNumbers[i] == 29) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터13. 
	 * 4회차 전부터 1씩 감소하는 수가 4회 출현 후 다음 2 적은수가 출현하면, 다음 회차에서 빠진수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter13(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		WinDataDto source5WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-5);
		WinDataDto source4WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-4);
		WinDataDto source3WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto source2WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto source1WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] source5Numbers = LottoUtil.getNumbers(source5WinDataDto);
		int[] source4Numbers = LottoUtil.getNumbers(source4WinDataDto);
		int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
		int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
		int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
		
		// 4회차 전부터 1씩 감소하고 다음 2 적은 수 출현여부 확인
		// 시작회차의 첫번째수부터 차례대로 확인
		for (int h = 0; h < source5Numbers.length; h++) {
			for (int i = 0; i < source4Numbers.length; i++) {
				if (source5Numbers[h] - 1 == source4Numbers[i] || source5WinDataDto.getBonus_num() - 1 == source4Numbers[i]) {
					for (int j = 0; j < source3Numbers.length; j++) {
						if (source4Numbers[i] - 1 == source3Numbers[j]) {
							for (int k = 0; k < source2Numbers.length; k++) {
								if (source3Numbers[j] - 1 == source2Numbers[k]) {
									for (int l = 0; l < source1Numbers.length; l++) {
										// 2 적은 수 확인
										if (source2Numbers[k] - 2 == source1Numbers[l]) {
											
											// 다음회차에서 빠진수가 출현
											for (int m = 0; m < targetNumbers.length; m++) {
												if (source2Numbers[k] - 1 == targetNumbers[m]) {
													isMatched = true;
													break;
												}
											}
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터14. 
	 * 3회차 전부터 1씩 감소하는 수가 3회동안 출현하면, 다음 회차에서 8끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter14(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 다음회차에서 8끝수가 출현
		for (int m = 0; m < targetNumbers.length; m++) {
			if (targetNumbers[m] % 10 == 8) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터17. 
	 * 단번대가 1개씩 3회연속 출현하면,4회째에 단번대의 배수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter17(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 다음회차에서 8끝수가 출현
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto sourceWinDataDto3 = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
		int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
		int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
		
		int num1 = sourceNumbers1[0];
		int num2 = sourceNumbers2[0];
		int num3 = sourceNumbers3[0];
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % num1 == 0
					|| targetNumbers[i] % num2 == 0
					|| targetNumbers[i] % num3 == 0
					) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터18. 
	 * 5와 8끝수가 출현하면, 다음회 6끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter18(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 6) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터19. 
	 * 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현하면, 3끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter19(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 3) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터20. 
	 * 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현하면, 1회차 전의 번호 중 1개가 다시 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter20(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-2);	// 1회차 전
		
		int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
		
		int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
		
		// 1회차 전 10번대, 20번대 번호 설정
		int[] bf10Num = new int[sourceZeroCntRange1[1]];					
		int[] bf20Num = new int[sourceZeroCntRange1[2]];
		
		int bf10Index = 0;
		int bf20Index = 0;
		for (int i = 0; i < sourceNumbers1.length; i++) {
			if (10 < sourceNumbers1[i] && sourceNumbers1[i] <= 20) {
				bf10Num[bf10Index++] = sourceNumbers1[i]; 
			} else if (20 < sourceNumbers1[i] && sourceNumbers1[i] <= 30) {
				bf20Num[bf20Index++] = sourceNumbers1[i]; 
			}
		}
		
		for (int i = 0; i < targetNumbers.length; i++) {
			boolean isAppear2 = false;
			
			// 10번대 번호 재출현 여부 체크
			for (int j = 0; j < bf10Num.length; j++) {
				if (targetNumbers[i] == bf10Num[j]) {
					isAppear2 = true;
					isMatched = true;
					break;
				}
			}
			
			if (isAppear2) {
				break;
			} else {
				// 20번대 번호 재출현 여부 체크
				for (int j = 0; j < bf20Num.length; j++) {
					if (targetNumbers[i] == bf20Num[j]) {
						isAppear2 = true;
						isMatched = true;
						break;
					}
				}
				
				if (isAppear2) {
					break;
				}
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터21. 
	 * 24번호 출현시(보너스 포함), 2,8,7 끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter21(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		boolean isAppearEndNum2 = false;
		boolean isAppearEndNum7 = false;
		boolean isAppearEndNum8 = false;
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 2) {
				isAppearEndNum2 = true;
			}
			if (targetNumbers[i] % 10 == 7) {
				isAppearEndNum7 = true;
			}
			if (targetNumbers[i] % 10 == 8) {
				isAppearEndNum8 = true;
			}
		}
		
		if (isAppearEndNum2 && isAppearEndNum7 && isAppearEndNum8) {
			isMatched = true;
		}
		
		return isMatched;
	}
	
	/**
	 * 필터22. 
	 * 3끝수가 4회연속 출현하면, 4회째 출현번호 중 1개가 다시 출현한다
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter22(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		WinDataDto sourceWinDataDto4 = winDataListForFilter.get(winDataListForFilter.size()-1);	// 최근회차
		int[] sourceNumbers4 = LottoUtil.getNumbers(sourceWinDataDto4);
		
		for (int i = 0; i < targetNumbers.length; i++) {
			for (int j = 0; j < sourceNumbers4.length; j++) {
				if (targetNumbers[i] == sourceNumbers4[j]) {
					isMatched = true;
					break;
				}
			}	
			
			if (isMatched) {
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터23. 
	 * 3끝수가 4회연속 출현하면, 동일한 끝수(가족수)가 2개이상 출현한다
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter23(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		Map<Integer, Integer> endNumMap = new HashMap<Integer, Integer>();
		int appearCnt = 0;
		for (int i = 0; i < targetNumbers.length; i++) {
			int endNum = targetNumbers[i] % 10;
			if (endNumMap.containsKey(endNum)) {
				// 가족수 존재함
				appearCnt = endNumMap.get(endNum) + 1;
			} else {
				endNumMap.put(endNum, 1);
			}
		}
		
		if (appearCnt > 1) {
			isMatched = true;
		}
		
		return isMatched;
	}
	
	/**
	 * 필터24. 
	 * 8,2,4 끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter24(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		boolean isAppearEndNum8 = false;
		boolean isAppearEndNum2 = false;
		boolean isAppearEndNum4 = false;
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 2) {
				isAppearEndNum2 = true;
			}
			if (targetNumbers[i] % 10 == 4) {
				isAppearEndNum4 = true;
			}
			if (targetNumbers[i] % 10 == 8) {
				isAppearEndNum8 = true;
			}
		}
		
		if (isAppearEndNum2 && isAppearEndNum4 && isAppearEndNum8) {
			isMatched = true;
		}
		
		return isMatched;
	}
	
	/**
	 * 필터25. 
	 * 8,2,4끝수가 출현하지 않는다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter25(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		boolean isAppearEndNum8 = false;
		boolean isAppearEndNum2 = false;
		boolean isAppearEndNum4 = false;
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 2) {
				isAppearEndNum2 = true;
			}
			if (targetNumbers[i] % 10 == 4) {
				isAppearEndNum4 = true;
			}
			if (targetNumbers[i] % 10 == 8) {
				isAppearEndNum8 = true;
			}
		}
		
		if (!isAppearEndNum2 && !isAppearEndNum4 && !isAppearEndNum8) {
			isMatched = true;
		}
		
		return isMatched;
	}
	
	/**
	 * 필터26. 
	 * 단번대(1~9)가 출현하지 않는다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter26(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 구간별 출현회수 목록 조회
		int[] rangeCnt = this.getRange3Numbers(targetNumbers);
		// 단번대(1~9) 미출 체크
		if (rangeCnt[0] == 0) {
			isMatched = true;
		}

		return isMatched;
	}
	
	/**
	 * 필터27. 
	 * 1끝수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter27(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] % 10 == 1) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터28. 
	 * 19번이 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter28(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] == 19) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터29. 
	 * 28번이 출현하지 않는다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter29(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		boolean isAppear28 = false;
		for (int i = 0; i < targetNumbers.length; i++) {
			if (targetNumbers[i] == 28) {
				isAppear28 = true;
				break;
			}
		}
		
		if (!isAppear28) {
			isMatched = true;
		}
		
		return isMatched;
	}
	
	/**
	 * 필터30. 
	 * 구간수 3개가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter30(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		// 구간별 출현회수 목록 조회
		int[] rangeCnt = this.getRange3Numbers(targetNumbers);
		for (int i = 0; i < rangeCnt.length; i++) {
			if (rangeCnt[i] == 3) {
				isMatched = true;
				break;
			}
		}
		
		return isMatched;
	}
	
	/**
	 * 필터 6회귀. 
	 * 6회귀 연속 3회 전멸하면, 6회귀수가 출현한다.
	 * 
	 * @param winDataListForFilter
	 * @param targetNumbers
	 * @return
	 */
	public boolean isMatchedFilter6Regression(List<WinDataDto> winDataListForFilter, int[] targetNumbers) {
		// 체크
		boolean isMatched = false;
		
		WinDataDto sourceWinDataDtoBf6 = winDataListForFilter.get(winDataListForFilter.size()-6);
		int[] sourceNumbersBf6 = LottoUtil.getNumbers(sourceWinDataDtoBf6);
		
		for (int i = 0; i < targetNumbers.length; i++) {
			for (int j = 0; j < sourceNumbersBf6.length; j++) {
				if (targetNumbers[i] == sourceNumbersBf6[i]) {
					isMatched = true;
					break;
				}
			}
			
		}
		
		return isMatched;
	}

	/**
	 * 최근 당첨번호에서 4회차 전부터 1씩 감소하는 수가 4회 출현 후 5회차에서 2 적은수가 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter13(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		WinDataDto source5WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-5);
		WinDataDto source4WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-4);
		WinDataDto source3WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto source2WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto source1WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] source5Numbers = LottoUtil.getNumbers(source5WinDataDto);
		int[] source4Numbers = LottoUtil.getNumbers(source4WinDataDto);
		int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
		int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
		int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
		
		// 4회차 전부터 1씩 감소하고 다음 2 적은 수 출현여부 확인
		// 시작회차의 첫번째수부터 차례대로 확인
		for (int h = 0; h < source5Numbers.length; h++) {
			for (int i = 0; i < source4Numbers.length; i++) {
				if (source5Numbers[h] - 1 == source4Numbers[i] || source5WinDataDto.getBonus_num() - 1 == source4Numbers[i]) {
					for (int j = 0; j < source3Numbers.length; j++) {
						if (source4Numbers[i] - 1 == source3Numbers[j]) {
							for (int k = 0; k < source2Numbers.length; k++) {
								if (source3Numbers[j] - 1 == source2Numbers[k]) {
									for (int l = 0; l < source1Numbers.length; l++) {
										// 2 적은 수 확인
										if (source2Numbers[k] - 2 == source1Numbers[l]) {
											isCheck = true;
											break;
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return isCheck;
	}

	/**
	 * 최근 당첨번호에서 3회차 전부터 1씩 감소하는 수가 3회동안 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter14(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto source4WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-4);
		WinDataDto source3WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto source2WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto source1WinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] source3Numbers = LottoUtil.getNumbers(source3WinDataDto);
		int[] source2Numbers = LottoUtil.getNumbers(source2WinDataDto);
		int[] source1Numbers = LottoUtil.getNumbers(source1WinDataDto);
		
		// 3회차 전부터 1씩 감소하는 수 출현여부 확인
		// 시작회차의 첫번째수부터 차례대로 확인
		boolean isAppear = false;
		for (int j = 0; j < source3Numbers.length; j++) {
			for (int k = 0; k < source2Numbers.length; k++) {
				if (source3Numbers[j] - 1 == source2Numbers[k] || source3WinDataDto.getBonus_num() - 1 == source2Numbers[k]) {
					for (int l = 0; l < source1Numbers.length; l++) {
						if (source2Numbers[k] - 1 == source1Numbers[l]) {
							isCheck = true;
							break;
						}
					}
				}
			}
		}
		return isCheck;
	}
	
	/**
	 * 단번대가 1개씩 3회연속 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter17(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto sourceWinDataDto3 = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
		int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
		int[] sourceZeroCntRange3 = this.getZeroCntRangeData(sourceWinDataDto3);
		
		
		// 단번대 1개씩 3회 출현 확인
		if (sourceZeroCntRange1[0] == 1
				&& sourceZeroCntRange2[0] == 1
				&& sourceZeroCntRange3[0] == 1
				) {
			isCheck = true;
		}
				
		return isCheck;
	}
	
	/**
	 * 5와 8끝수 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter18(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		// 5번과 8끝수 출현 확인
		boolean isAppear5 = false;
		boolean isAppear8End = false;
		
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] == 5) {
				isAppear5 = true;
			}
			if (sourceNumbers[i] % 10 == 8) {
				isAppear8End = true;
			}
		}
		
		if (isAppear5 && isAppear8End) {
			isCheck = true;
		}
		
		return isCheck;
	}
	
	/**
	 * 6회귀 연속 3회 전멸 확인
	 * 
	 * 6회귀 : 6회차 전 당첨번호가 1개라도 다시 출현한다는 의미
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter6Regression(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto sourceWinDataDto3 = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-1);
		WinDataDto sourceWinDataDto3Bf6 = winDataListForFilter.get(winDataListForFilter.size()-3-6);
		WinDataDto sourceWinDataDto2Bf6 = winDataListForFilter.get(winDataListForFilter.size()-2-6);
		WinDataDto sourceWinDataDto1Bf6 = winDataListForFilter.get(winDataListForFilter.size()-1-6);
		
		int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
		int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
		int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
		int[] sourceNumbers1Bf6 = LottoUtil.getNumbers(sourceWinDataDto1Bf6);
		int[] sourceNumbers2Bf6 = LottoUtil.getNumbers(sourceWinDataDto2Bf6);
		int[] sourceNumbers3Bf6 = LottoUtil.getNumbers(sourceWinDataDto3Bf6);
		
		// 6회귀 연속 3회 전멸 확인
		for (int i = 0; i < sourceNumbers1.length; i++) {
			for (int j = 0; j < sourceNumbers1Bf6.length; j++) {
				if (sourceNumbers1[i] == sourceNumbers1Bf6[j]) {
					//6회귀 번호가 출현함.
					return false;
				}
			}
		}
		
		for (int i = 0; i < sourceNumbers2.length; i++) {
			for (int j = 0; j < sourceNumbers2Bf6.length; j++) {
				if (sourceNumbers2[i] == sourceNumbers2Bf6[j]) {
					//6회귀 번호가 출현함.
					return false;
				}
			}
		}
		
		for (int i = 0; i < sourceNumbers3.length; i++) {
			for (int j = 0; j < sourceNumbers3Bf6.length; j++) {
				if (sourceNumbers3[i] == sourceNumbers3Bf6[j]) {
					//6회귀 번호가 출현함.
					return false;
				}
			}
		}
		
		isCheck = true;
		
		return isCheck;
	}
	
	/**
	 * 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter19(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
		int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
		
		// 마지막 회차의 2회전 단번대 출현, 최근 회차에서 단번대 출현 확인
		// 단번대 출현 체크
		if (sourceZeroCntRange1[0] >= 1
				&& sourceZeroCntRange2[0] >= 1) {
			isCheck = true;
		}
		
		return isCheck;
	}
	
	/**
	 * 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter20(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceZeroCntRange1 = this.getZeroCntRangeData(sourceWinDataDto1);
		int[] sourceZeroCntRange2 = this.getZeroCntRangeData(sourceWinDataDto2);
		
		int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
		int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
		
		// 1회차 전 10번대, 20번대 번호 설정
		int[] bf10Num = new int[sourceZeroCntRange1[1]];					
		int[] bf20Num = new int[sourceZeroCntRange1[2]];
		
		// 1회차 전에서의 10번대, 20번대 번호가 최근 회차에서 연번으로 출현 확인
		boolean isAppear10ConsecutivelyNumber = false;
		boolean isAppear20ConsecutivelyNumber = false;
		
		// 10번대 20번대 출현여부 체크
		if (sourceZeroCntRange1[1] >= 1 && sourceZeroCntRange1[2] >= 1
				&& sourceZeroCntRange2[1] >= 1 && sourceZeroCntRange2[2] >= 1
				) {
			int bf10Index = 0;
			int bf20Index = 0;
			for (int i = 0; i < sourceNumbers1.length; i++) {
				if (10 < sourceNumbers1[i] && sourceNumbers1[i] <= 20) {
					bf10Num[bf10Index++] = sourceNumbers1[i]; 
				} else if (20 < sourceNumbers1[i] && sourceNumbers1[i] <= 30) {
					bf20Num[bf20Index++] = sourceNumbers1[i]; 
				}
			}
			
			// 최근회차에서 연번으로 출현여부 체크
			for (int i = 0; i < sourceNumbers2.length; i++) {
				if (10 < sourceNumbers2[i] && sourceNumbers2[i] <= 20) {
					// 10번대 연번 출현 확인
					for (int j = 0; j < bf10Num.length; j++) {
						if (sourceNumbers2[i] == (bf10Num[j] + 1)) {
							isAppear10ConsecutivelyNumber = true;									
						}
					} 
				} else if (20 < sourceNumbers2[i] && sourceNumbers2[i] <= 30) {
					// 20번대 연번 출현 확인
					for (int j = 0; j < bf20Num.length; j++) {
						if (sourceNumbers2[i] == (bf20Num[j] + 1)) {
							isAppear20ConsecutivelyNumber = true;									
						}
					}
				}
			}
			
			if (isAppear10ConsecutivelyNumber
					&& isAppear20ConsecutivelyNumber) {
				isCheck = true;
			}
		}
		
		return isCheck;
	}
	
	/**
	 * 최근회차 24 출현 확인 (보너스 포함)
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter21(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		for (int i = 0; i < sourceNumbers.length; i++) {
			if (sourceNumbers[i] == 24) {
				isCheck = true;
				break;
			}
		}
		
		if (!isCheck) {
			if (sourceWinDataDto.getBonus_num() == 24) {
				isCheck = true;
			}
		}
		
		return isCheck;
	}
	
	/**
	 * 최근회차 전체구간, 단번대 2개, 연번 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter0(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		// 최근회차에서 전체 구간 출현 확인
		int[] containGroupCnt = this.getZeroCntRangeData(sourceWinDataDto);
		int isAllCnt = 0;
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] > 0) {
				isAllCnt++;
			}
		}
		
		// 최근회차에서 단번대 2개 출현 확인
		if (isAllCnt == 5) {
//			log.info("이전 회차의 전체구간 출현. 단번대 번호 수 체크 : 2개 출현 확인");
			containGroupCnt = this.getZeroCntRangeData(sourceWinDataDto);
//			if (containGroupCntExData[0] > 2 || containGroupCntExData[0] == 0) {
			if (containGroupCnt[0] != 2 ) {
//				log.info("단번대 번호 수 불충족 : " + containGroupCnt[0]);
				return false;
			}
		} else {
			return false;
		}
		
		// 최근회차에서 연번 출현 확인
		for (int i = 0; i < sourceNumbers.length-1; i++) {
			if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
				// 끝수 존재여부 체크
				if (i+2 < sourceNumbers.length) {
					if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
						// 다음수가 3연속은 규칙 제외
						return false;
					}
				} else if (i-1 >= 0) {
					if(sourceNumbers[i] - sourceNumbers[i-1] == 1) {
						// 이전수가 3연속은 규칙 제외
						return false;
					}
				}
				
				isCheck = true;
			}
		}
		
		return isCheck;
	}
	
	/**
	 * 3끝수가 4회연속 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter22(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto1 = winDataListForFilter.get(winDataListForFilter.size()-4);
		WinDataDto sourceWinDataDto2 = winDataListForFilter.get(winDataListForFilter.size()-3);
		WinDataDto sourceWinDataDto3 = winDataListForFilter.get(winDataListForFilter.size()-2);
		WinDataDto sourceWinDataDto4 = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers1 = LottoUtil.getNumbers(sourceWinDataDto1);
		int[] sourceNumbers2 = LottoUtil.getNumbers(sourceWinDataDto2);
		int[] sourceNumbers3 = LottoUtil.getNumbers(sourceWinDataDto3);
		int[] sourceNumbers4 = LottoUtil.getNumbers(sourceWinDataDto4);
		
		// 3끝수 연속 4회 출현 확인
		boolean isAppear1EndNum3 = false;
		boolean isAppear2EndNum3 = false;
		boolean isAppear3EndNum3 = false;
		boolean isAppear4EndNum3 = false;
		
		for (int i = 0; i < sourceNumbers1.length; i++) {
			if (sourceNumbers1[i] % 10 == 3) {
				isAppear1EndNum3 = true;
				break;
			}
		}
		for (int i = 0; i < sourceNumbers2.length; i++) {
			if (sourceNumbers2[i] % 10 == 3) {
				isAppear2EndNum3 = true;
				break;
			}
		}
		for (int i = 0; i < sourceNumbers3.length; i++) {
			if (sourceNumbers3[i] % 10 == 3) {
				isAppear3EndNum3 = true;
				break;
			}
		}
		for (int i = 0; i < sourceNumbers4.length; i++) {
			if (sourceNumbers4[i] % 10 == 3) {
				isAppear4EndNum3 = true;
				break;
			}
		}
		
		if (isAppear1EndNum3 && isAppear2EndNum3 && isAppear3EndNum3 && isAppear4EndNum3) {
			isCheck = true;
		}
		
		return isCheck;
	}
	
	/**
	 * 10번대 연번 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter27(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		
		// 최근회차에서 10번대 연번 출현 확인
		boolean isConsecutively = false;	// 전회차 연번 포함여부
		String consecutivelyNumbers = "";
		List<Integer> consecutivelyNumberList = new ArrayList<Integer>();
		for (int i = 0; i < sourceNumbers.length-1; i++) {
			if (sourceNumbers[i+1] - sourceNumbers[i] == 1) {
				// 끝수 존재여부 체크
				if (i+2 < sourceNumbers.length) {
					if(sourceNumbers[i+2] - sourceNumbers[i+1] == 1) {
						// 다음수가 3연속은 규칙 제외
						isConsecutively = false;
						consecutivelyNumbers = "";
						consecutivelyNumberList = new ArrayList<Integer>();
						break;
					}
				} else if (i-1 >= 0) {
					if(sourceNumbers[i] - sourceNumbers[i-1] == 1) {
						// 이전수가 3연속은 규칙 제외
						isConsecutively = false;
						consecutivelyNumbers = "";
						consecutivelyNumberList = new ArrayList<Integer>();
						break;
					}
				}
				
				
				// 연번
				consecutivelyNumberList.add(sourceNumbers[i]);
				consecutivelyNumbers += sourceNumbers[i];
				consecutivelyNumberList.add(sourceNumbers[i+1]);
				consecutivelyNumbers += ",";
				consecutivelyNumbers += sourceNumbers[i+1];
				
				// 10번대 연번 체크
				if ((10 <= sourceNumbers[i] && sourceNumbers[i] < 20) 
						&& (10 <= sourceNumbers[i+1] && sourceNumbers[i+1] < 20)) {
					isConsecutively = true;
					break;
				}
			}
		}
		
		if (isConsecutively) {
			isCheck = true;
		}
		
		return isCheck;
	}
	
	/**
	 * 15번 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter28(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		// 최근회차에서 15번 출현 확인
		for (int i = 0; i < sourceNumbers.length-1; i++) {
			if (sourceNumbers[i] == 15) {
				isCheck = true;
				break;
			}
		}
		
		return isCheck;
	}
	
	/**
	 * 28번 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter29(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int[] sourceNumbers = LottoUtil.getNumbers(sourceWinDataDto);
		
		// 최근회차에서 28번 출현 확인
		for (int i = 0; i < sourceNumbers.length-1; i++) {
			if (sourceNumbers[i] == 28) {
				isCheck = true;
				break;
			}
		}
		
		return isCheck;
	}
	
	/**
	 * 보너스번호가 40번대(40~45) 출현 확인
	 * 
	 * @param winDataListForFilter
	 * @return
	 */
	public boolean isCheckFilter30(List<WinDataDto> winDataListForFilter) {
		boolean isCheck = false;
		
		// 최근회차
		WinDataDto sourceWinDataDto = winDataListForFilter.get(winDataListForFilter.size()-1);
		
		int sourceBonusNumber = sourceWinDataDto.getBonus_num(); 
		if (40 <= sourceBonusNumber && sourceBonusNumber <= 45) {
			isCheck = true;
		}
		
		return isCheck;
	}

	/**
	 * 추가 기본제외수 조회
	 * 조건 : 최근 5주 이내 2번 이상 출현한 수는 제외한다.(로또9단)
	 * 2020.04.02
	 * 
	 * @param winDataList
	 * @return
	 */
	public List<Integer> getAddExcludeNumbers(List<WinDataAnlyDto> winDataList) {
		List<Integer> excludeNumbers = new ArrayList<Integer>();
		// 중복체크
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = winDataList.size() - 1; i > winDataList.size() - 1 - 5; i--) {
			WinDataAnlyDto winDataDto = winDataList.get(i);
			int[] numbers = LottoUtil.getNumbers(winDataDto);
			for (int j = 0; j < numbers.length; j++) {
				int checkNumber = numbers[j];
				if (map.containsKey(checkNumber)) {
					// 2회 이상 출현 수 설정
					excludeNumbers.add(checkNumber);
				} else {
					map.put(checkNumber, 1);
				}
			}
		}
		
		return excludeNumbers;
	}
	
	/**
	 * 추가 기본제외수 조회
	 * 조건 : 최근 5주 이내 2번 이상 출현한 수는 제외한다.(로또9단)
	 * 2020.04.02
	 * 
	 * @param winDataList
	 * @return
	 */
	public List<Integer> getAddExcludeNumbersFromDtoList(List<WinDataDto> winDataList) {
		List<Integer> excludeNumbers = new ArrayList<Integer>();
		// 중복체크
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = winDataList.size() - 1; i > winDataList.size() - 1 - 5; i--) {
			WinDataDto winDataDto = winDataList.get(i);
			int[] numbers = LottoUtil.getNumbers(winDataDto);
			for (int j = 0; j < numbers.length; j++) {
				int checkNumber = numbers[j];
				if (map.containsKey(checkNumber)) {
					// 2회 이상 출현 수 설정
					excludeNumbers.add(checkNumber);
				} else {
					map.put(checkNumber, 1);
				}
			}
		}
		
		return excludeNumbers;
	}
	
	/**
	 * 미출수 출현 시 제외수 추가
	 * : 11주 이상 미출구간부터 최근회차 당첨번호가 출현한 
	 * 동일한 미출기간 번호는 제외한다. 
	 * 조건 : 직전회차 미출수 당첨번호와 동일한 위치(인덱스) 숫자는 제외
	 * 2020.04.03
	 * 
	 * @param winDataList
	 * @return
	 */
	public List<Integer> getAddExcludeNumbersFromNotContain(List<WinDataDto> winDataList) {
		List<Integer> excludeNumbers = new ArrayList<Integer>();
		
		// 중복체크
		Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> map3 = new HashMap<Integer, Integer>();
		
		// 번호집계 배열
		int[][] arrayLastWin = new int[30][6];	// 최근회차 기준
		int[][] arrayBeforeWin = new int[30][6];// 직전회차 기준
		
		//등록하는 index
		int targetRow = 0;
		int targetCol = 0;
		
		/*
		 * 최근회차 기준
		 * 30회 이내 출현번호 배열 집계
		 * 중복된 번호는 0 처리한다.
		 */
		for (int i = winDataList.size() - 1; i > winDataList.size() - 1 - 30; i--) {
			WinDataDto winDataDto = winDataList.get(i);
			int[] numbers = LottoUtil.getNumbers(winDataDto);
			// 열번호 초기화 (한 행이 집계가 끝나면 열번호를 초기화한다.)
			targetCol = 0;
					
			for (int j = 0; j < numbers.length; j++) {
				int checkNumber = numbers[j];
				if (map1.containsKey(checkNumber)) {
					continue;
				} else {
					map1.put(checkNumber, 1);
					arrayLastWin[targetRow][targetCol] = checkNumber;
				}
			}
			// 행번호 증가
			targetRow++;
		}
		
		//등록하는 index 초기화
		targetRow = 0;
		targetCol = 0;
				
		/*
		 * 직전회차 기준
		 * 30회 이내 출현번호 배열 집계
		 * 중복된 번호는 0 처리한다.
		 */
		for (int i = winDataList.size() - 1 - 1; i > winDataList.size() - 1 - 30 - 1; i--) {
			WinDataDto winDataDto = winDataList.get(i);
			int[] numbers = LottoUtil.getNumbers(winDataDto);
			// 열번호 초기화 (한 행이 집계가 끝나면 열번호를 초기화한다.)
			targetCol = 0;
			
			for (int j = 0; j < numbers.length; j++) {
				int checkNumber = numbers[j];
				if (map2.containsKey(checkNumber)) {
					continue;
				} else {
					map2.put(checkNumber, 1);
					arrayBeforeWin[targetRow][targetCol] = checkNumber;
				}
			}
			// 행번호 증가
			targetRow++;
		}
		
		// 직전회차 기준배열에서 11주 이상 미출구간부터 최근회차 당첨번호가 출현개수 확인
		WinDataDto winDataDto = winDataList.get(winDataList.size() - 1);
		int[] numbers = LottoUtil.getNumbers(winDataDto);
		for (int i = 0; i < numbers.length; i++) {
			if (!map3.containsKey(numbers[i])) {
				map3.put(numbers[i], 1);
			}
		}
		
		// 11주 이상 출현회수
		int appearCount = 0;
		for (int row = 0; row < arrayBeforeWin.length; row++) {
			// 11회 미만은 카운팅 제외
			if (row < 10) {
				continue;
			}
			for (int col = 0; col < arrayBeforeWin[row].length; col++) {
				int number = arrayBeforeWin[row][col];
				if (number > 0 && map3.containsKey(number)) {
					appearCount++;
				}
			}
		}
		
		// 직전회차 기준 11주 이상 출현회수가 있으면 직전회차기준 출현 인덱스 추출
		if (appearCount > 0) {
			// 인덱스 배열 선언
			int[][] excludePosition = new int[appearCount][2];
			int setCount = 0;
			
			// 최근회차 기준으로 출현 인덱스에 해당하는 미출수를 제외수로 설정
			for (int row = 0; row < arrayBeforeWin.length; row++) {
				// 11회 미만은 카운팅 제외
				if (row < 10) {
					continue;
				}
				for (int col = 0; col < arrayBeforeWin[row].length; col++) {
					int number = arrayBeforeWin[row][col];
					if (number > 0 && map3.containsKey(number)) {

						excludePosition[setCount][0] = row;
						excludePosition[setCount][1] = col;
						setCount++;
					}
				}
			}

			// 최근회차 기준 해당인덱스 번호를 제외수로 설정
			for (int i = 0; i < excludePosition.length; i++) {
				int row = excludePosition[i][0];
				int col = excludePosition[i][1];
				
				if (arrayLastWin[row][col] > 0) {
					excludeNumbers.add(arrayLastWin[row][col]);
				}
			} 
		}
		
		return excludeNumbers;
	}

	/**
	 * 자동번호 추출 시 추가 필터체크 (Reference 닥터존)
	 * 2020.04.08
	 * 
	 * @param exDataDto
	 * @param winDataList
	 * @return true : 통과, false : 조합제외
	 */
	public boolean checkAddFilter(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = true;
		
		boolean result = false;
		
		// 27. 최근 10회 이내 포함개수 부적합 제외
		result = this.checkLast10WinDatas(exData, winDataList);
		if(!result) {
			String comments = "추가 필터 : 최근 10회 이내 포함개수 부적합 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);
			
			return false;
		}
				
		// 29. 소수 0개, 4개 이상 포함 제외
		// https://namu.wiki/w/소수(수론)
		result = this.checkPrimeNumberCount(exData);
		if (result) {
			String comments = "추가 필터 : 소수 0개, 4개 이상 포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}

		// 30. 동일끝수 2개이상 미포함 제외
		result = this.checkSameEndNumberCount(exData);
		if (!result) {
			String comments = "추가 필터 : 동일끝수 2개이상 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}

		// 31. 당첨번호의 이웃수(+-1) 1개이상 미포함 제외
		result = this.checkNeighborhoodNumberCount(exData, winDataList);
		if (result) {
			String comments = "추가 필터 : 당첨번호의 이웃수(+-1) 1개이상 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}

		// 32. 마지막 끝수 35이상 미포함 제외
		result = this.check6thNumber(exData);
		if (result) {
			String comments = "추가 필터 : 마지막 끝수 35이상 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}

		// 33. 3의배수 적어도 1개 이상 미포함 제외
		result = this.checkMultiplesOf3Number(exData);
		if (result) {
			String comments = "추가 필터 : 3의배수 적어도 1개 이상 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}

		// 34. 모든구간 포함 제외
		result = this.checkAllRange(exData);
		if (result) {
			String comments = "추가 필터 : 모든구간 포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}
		
		// 35. 번호대 3개 이상 포함 제외
		for (int i = 0; i < 5; i++) {
			result = this.checkRange3Numbers(exData, i);
			if (result) {
				String msg = "번호대";
				if (i == 0) {
					msg = "단번대";
				} else {
					msg = i + "0번대";
				}
				String comments = "추가 필터 : " + msg + " 3개 이상 포함 제외";
				log.info(comments);

				// TODO 실제 반영시에는 제거할 것
				sysmngService.insertExptNumNewVari(exData, comments);

				return false;
			}
		}

		// 36. 10번대 미포함 제외
		result = this.checkRange10To19(exData);
		if (result) {
			String comments = "추가 필터 : 10번대 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}
		
		// 37. 장기미출수 1개 이상 미포함 제외
		result = this.checkNotContain10Numbers(exData, winDataList);
		if (!result) {
			String comments = "추가 필터 : 장기미출수 1개 이상 미포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}
		
		// 38. 세로줄 3개 또는 가로줄 3개이상 포함 제외 (로또용지기준)
		result = this.check3NumbersInLine(exData);
		if (result) {
			String comments = "추가 필터 : 세로줄 3개 또는 가로줄 3개이상 포함 제외";
			log.info(comments);

			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);

			return false;
		}
		
		// 39. 이월수 2개이상 제외
		result = this.checkLastWinNumber(exData, winDataList);
		if (result) {
			String comments = "추가 필터 : 이월수 2개이상 제외";
			log.info(comments);
			
			// TODO 실제 반영시에는 제거할 것
			sysmngService.insertExptNumNewVari(exData, comments);
			
			return false;
		}
				
		
		// TODO 10주간 3회이상 출현번호 제외 확률
		// 906회 추출 기준 7, 16, 18, 21, 26, 38 확인 필요.
		// https://www.youtube.com/watch?v=hZ501YWUotA 참고
		
		
		return check;
	}
	
	/**
	 * 이월수 2개이상 제외
	 * 2020.04.11
	 * 
	 * @param exData
	 * @param winDataList
	 * @return true:포함, false:미포함
	 */
	private boolean checkLastWinNumber(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = false;
		
		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 최근회차 조합번호
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		int[] lastWinNumbers = LottoUtil.getNumbers(winDataList.get(winDataList.size()-1));
		for (int i = 0; i < lastWinNumbers.length; i++) {
			checkMap.put(lastWinNumbers[i],1);
		}
		
		for (int i = 0; i < numbers.length; i++) {
			if (checkMap.containsKey(numbers[i])) {
				checkCnt++;
			}
		}
		
		if (checkCnt >= 2) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 세로줄 3개 또는 가로줄 3개이상 포함 제외 (로또용지기준)
	 * 2020.04.11
	 * 
	 * @param exData
	 * true:포함, false:미포함
	 */
	private boolean check3NumbersInLine(ExDataDto exData) {
		boolean check = false;

		int[][] arrNumbers = LottoUtil.getArrayLikePaper(exData);
		
		// 가로줄 체크
		for (int row = 0; row < arrNumbers.length; row++) {
			int checkRowCnt = 0;
			for (int col = 0; col < arrNumbers[row].length; col++) {
				if (arrNumbers[row][col] > 0) {
					// 체크할 row count를 1로 설정
					checkRowCnt++;
				}
			}
			if (checkRowCnt >= 3) {
				return true;
			}
		}
		
		// 세로줄 체크
		for (int col = 0; col < arrNumbers[0].length; col++) {
			int checkColCnt = 0;
			for (int row = 0; row < arrNumbers.length; row++) {
				if (arrNumbers[row][col] > 0) {
					// 체크할 row count를 1로 설정
					checkColCnt++;
				}
			}
			if (checkColCnt >= 3) {
				return true;
			}
		}

		return check;
	}

	/**
	 * 장기미출수 1개 이상 미포함 제외
	 * : 최근 10회차 이내 출현숫자가 아닌 숫자들이 포함되어 있는지 확인	 * 
	 * 2020.04.11
	 * 
	 * @param exData
	 * @param winDataList 
	 * @return true:포함, false:미포함
	 */
	private boolean checkNotContain10Numbers(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = false;
		
		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		Map<Integer, Integer> checkContainNumbersMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> checkNotContainNumbersMap = new HashMap<Integer, Integer>();
		
		// 최근 10회차 이내 출현숫자 확인
		for (int i = winDataList.size() - 1 - 10; i < winDataList.size(); i++) {
			int[] winNumbers = LottoUtil.getNumbers(winDataList.get(i));
			for (int j = 0; j < winNumbers.length; j++) {
				if (!checkContainNumbersMap.containsKey(winNumbers[j])) {
					checkContainNumbersMap.put(winNumbers[j],1);
				}
			}
		}
		
		// 10회 초과 미출수 설정
		for (int i = 1; i <= 45; i++) {
			if (!checkContainNumbersMap.containsKey(i)) {
				checkNotContainNumbersMap.put(i,1);
			}
		}
		
		// 미출수 포함 체크
		for (int i = 0; i < numbers.length; i++) {
			if (checkNotContainNumbersMap.containsKey(i)) {
				return true;
			}
		}
		
		return check;
	}

	/**
	 * 10번대 미포함 제외
	 * 2020.04.10
	 * 
	 * @param exData
	 * @return true:미포함, false:포함
	 */
	private boolean checkRange10To19(ExDataDto exData) {
		boolean check = false;

		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 10번대 설정
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		for (int i = 10; i <= 19; i++) {
			checkMap.put(i, 1);
		}
		
		// 1개이상 존재여부 확인
		for (int i = 0; i < numbers.length; i++) {
			int number = numbers[i];
			if (checkMap.containsKey(number)) {
				checkCnt++;
			}
		}
		
		if (checkCnt == 0) {
			check = true;
		}
				
		return check;
	}

	/**
	 * 번호대 3개 이상 포함 제외
	 * 2020.04.10
	 * 
	 * @param exData
	 * @param range 특정구간 <br>0: 1~9<br>1: 10~19<br>2: 20~29<br>3: 30~39<br>4: 40~45
	 * @return true: 포함, false: 미포함
	 */
	private boolean checkRange3Numbers(ExDataDto exData, int range) {
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		return this.checkRange3Numbers(numbers, range);
	}

	/**
	 * 모든구간 포함 제외
	 * 2020.04.10
	 * 
	 * @param exData
	 * @return true: 포함, false: 미포함
	 */
	private boolean checkAllRange(ExDataDto exData) {
		boolean check = false;
		
		int[] containGroupCnt = LottoUtil.getArrayFromColor(exData);
		
		int checkCnt = 0;
		for (int i = 0; i < containGroupCnt.length; i++) {
			if (containGroupCnt[i] > 0) {
				checkCnt++;
			}
		}
		
		if (checkCnt == 5) {
			check = true;
		}
		
		return check;
	}
	
	/**
	 * 3의배수 적어도 1개 이상 미포함 제외
	 * 2020.04.10
	 * 
	 * @param exData
	 * @return true: 미포함, false: 포함
	 */
	private boolean checkMultiplesOf3Number(ExDataDto exData) {
		boolean check = false;
		
		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 3의 배수 설정
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		for (int i = 1; i <= 45; i++) {
			if (i % 3 == 0) {
				checkMap.put(i, 1);
			}
		}
		
		// 1개이상 존재여부 확인
		for (int i = 0; i < numbers.length; i++) {
			int number = numbers[i];
			if (checkMap.containsKey(number)) {
				checkCnt++;
			}
		}
		
		if (checkCnt == 0) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 마지막 끝수 35이상 미포함 제외
	 * 예상번호 추출 시 31이상 숫자로 설정했으나, 당첨번호 추출 시 한번 더 체크함.
	 * 2020.04.10
	 * 
	 * @param exData
	 * @return true: 미포함, false: 포함
	 */
	private boolean check6thNumber(ExDataDto exData) {
		boolean check = false;
		
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		int num6 = numbers[5];
		
		if (num6 < 35) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 당첨번호의 이웃수(+-1) 1개이상 미포함 제외
	 * 2020.04.10
	 * 
	 * @param exData
	 * @param winDataList 
	 * @return true: 미포함, false: 포함
	 */
	private boolean checkNeighborhoodNumberCount(ExDataDto exData, List<WinDataAnlyDto> winDataList) {
		boolean check = false;
		
		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
				
		// 최근당첨번호의 이웃수(+-) 설정
		WinDataAnlyDto lastWinData = winDataList.get(winDataList.size()-1);
		int[] lastWinDataNumbers = LottoUtil.getNumbers(lastWinData);
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < lastWinDataNumbers.length; i++) {
			int number = lastWinDataNumbers[i];
			// 1큰수 설정
			if (!checkMap.containsKey(number+1)) {
				checkMap.put(number+1, 1);
			}
			
			// 1작은수 설정
			if (!checkMap.containsKey(number-1)) {
				checkMap.put(number-1, 1);
			}
		}
		
		// 1개이상 존재여부 확인
		for (int i = 0; i < numbers.length; i++) {
			int number = numbers[i];
			if (checkMap.containsKey(number)) {
				checkCnt++;
			}
		}
		
		if (checkCnt == 0) {
			check = true;
		}
		
		return check;
	}

	/**
	 * 동일끝수 2개이상 미포함 제외
	 * 2020.04.08
	 * 
	 * @param exData
	 * @return true: 통과, false: 제외 
	 */
	private boolean checkSameEndNumberCount(ExDataDto exData) {
		boolean check = false;
		
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		// 끝수 , 끝수 개수
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		List<Integer> checkList = new ArrayList<Integer>();
		
		for (int i = 0; i < numbers.length; i++) {
			int endNumber = numbers[i] % 10;
			if (checkMap.containsKey(endNumber)) {
				int count = checkMap.get(endNumber);
				checkMap.put(endNumber, ++count);
			} else {
				checkMap.put(endNumber, 1);
				checkList.add(endNumber);
			}
		}
		
		// 체크
		for (int i = 0; i < checkList.size(); i++) {
			int checkNumber = checkList.get(i);
			int checkCnt = checkMap.get(checkNumber);
			
			// 2개이상 존재하면 통과
			if (checkCnt >= 2) {
				check = true;
				break;
			}
		}
		
		return check;
	}

	/**
	 * 소수 0개, 4개 이상 포함 제외
	 * 2020.04.08
	 * 
	 * @param exData
	 * @return true: 제외, false: 통과
	 */
	private boolean checkPrimeNumberCount(ExDataDto exData) {
		boolean check = false;

		int checkCnt = 0;
		// 예상회차 조합번호
		int[] numbers = LottoUtil.getNumbers(exData);
		
		int[] primeNumbers = {2,3,5,7,11,13,17,19,23,29,31,37,41,43};
		Map<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < primeNumbers.length; i++) {
			checkMap.put(primeNumbers[i], primeNumbers[i]);
		}
		
		for (int i = 0; i < numbers.length; i++) {
			if (checkMap.containsKey(numbers[i])) {
				checkCnt++;
			}
		}
		
		if (checkCnt == 0 || checkCnt >= 4) {
			check = true;
		}
		
		
		return check;
	}
}
