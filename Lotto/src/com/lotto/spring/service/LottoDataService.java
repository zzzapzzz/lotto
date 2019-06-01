package com.lotto.spring.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.lotto.spring.domain.dto.OddEvenDto;
import com.lotto.spring.domain.dto.TotalDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;
import com.lotto.spring.domain.dto.ZeroRangeDto;

@Service("lottoDataService")
public class LottoDataService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());

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
		int[] difNubmers = {0,0,0,0,0};
		
		for (int i = 0; i < numbers.length -1 ; i++) {
			difNubmers[i] = Math.abs(numbers[i] - numbers[i+1]);
		}
		
		return difNubmers;
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
							for (int l = 0; l < numbers.length; l++) {
								if (deleteTargetMap.containsKey(numbers[l])) {
									continue;
								}
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
						for (int l = 0; l < numbers.length; l++) {
							if (deleteTargetMap.containsKey(numbers[l])) {
								continue;
							}
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
					for (int l = 0; l < numbers.length; l++) {
						if (deleteTargetMap.containsKey(numbers[l])) {
							continue;
						}
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
		double total = dataList.size();
		DecimalFormat df = new DecimalFormat("#.##"); 
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			double percent = Double.parseDouble(df.format( (double)countData[i][1] /total ));
//			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent*100) + "%");
			
			numbersList.add(countData[i][0]);
			sumPercent += percent*100;			
			
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
		double total = winDataList.size();
		DecimalFormat df = new DecimalFormat("#.##"); 
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			if(min == 0 || min > countData[i][0]){
				min = countData[i][0];
			}
			
			if(max == 0 || max < countData[i][0]){
				max = countData[i][0];
			}
			
			double percent = Double.parseDouble(df.format( (double)countData[i][1] /total ));
			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent*100) + "%");
			
			sumPercent += percent*100;			
			
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
		double total = winDataList.size();
		DecimalFormat df = new DecimalFormat("#.##"); 
		double sumPercent = 0.0;
		
		for (int i = 0; i < countData.length ; i++) {
			
			map.put(String.valueOf(countData[i][0]), countData[i][1]);
			
			double percent = Double.parseDouble(df.format( (double)countData[i][1] /total ));
//			log.info( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent*100) + "%");
			
			sumPercent += percent*100;			
			
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
	 *              <div id=detail>데이터 중 AC가 7 ~ 10 사이인지 확인한다.(범위는 표준범위)</div >
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
		if (ac >= 7 && ac <= 10) {
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
	 * @description <div id=description><b>등수확인</b></div >
	 *              <div id=detail>예상데이터와 실제데이터를 비교하여 등수를 계산한다.</div >
	 *              
	 * @param exData 예상번호 정보
	 * @param lastWinData 당첨번호 정보
	 * @return
	 */
	public String getWinResult(ExDataDto exData, WinDataDto lastWinData) {
		String result = "";

		int[] lastNumbers = LottoUtil.getNumbers(exData);
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
//		//12. AC 비교(7 ~ 10)
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
			return false;
		}
		
		//2. 끝수합 범위 포함 비교
		if (!endnumGroupCntMap.containsKey(exData.getSum_end_num())) {
			return false;
		}
				
		//3. 출현번호 매치여부로 예상번호 설정
		result = this.matchAppearNumbers(exData, appearNumbersList);
		if (!result) {
			return false;
		}
		
		/*
		 * 4. 번호간 차이값 체크
		 * 번호간 차이값이 평균범위 포함되어 있는지 체크한다.
		 */
		result = this.isContainRange(exData, numbersRangeList);
		if (!result) {
			return false;
		}
		
		//5. AC 비교(7 ~ 10)
		result = this.isContainAc(exData, exptPtrnAnlyInfo);
		if (verification && isEqual) {
			if(!result) {
				equalCnt++;	//일치함.
				log.info("12. AC 비교 : " + equalCnt);
			}
		} else {
			if(!result) equalCnt++;	//일치함.
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
			return false;
		}
		
		/*
		 * 98. 임시 예외처리
		 * 
		 * 2019.03.08
		 * 40번대 구간 미출현
		 * 10번대 출현
		 * 이전 출현번호는 1개는 나옴.
		 * 2:4 ~ 4:2면 OK
		 * 
		 * 2019.03.15
		 * 미출구간 체크 제외 
		 */
		result = this.checkCustomException(exData, winDataList);
		if (!result) {
			return false;
		}
		
		/*
		 * 99. Cherokee 사이트 참고 예외처리 추가
		 * https://blog.naver.com/cameto13
		 * 2019.02.24 결과 망!
		 */
		
		return true;
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
	 * 2018.05.31<br>
	 * 
	 * @param data
	 * @param str "로","은"
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
			if ("0".equals(lastData)
					|| "1".equals(lastData)
					|| "3".equals(lastData)
					|| "6".equals(lastData)
					|| "7".equals(lastData)
					|| "8".equals(lastData)
					) {
				postPosition = "은";
			} else {
				postPosition = "는";
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
			acMsg += "표준 범위 " + acInfo.getAc_range() + "내인<br>";
			acMsg += winData.getAc() + this.getPostPosition(winData.getAc()) + " 분석되었습니다.";
			acMsg += "<br>";	
		} else {
			acMsg += "<br>";
			acMsg += "이번 <strong>AC</strong>는 특이하게<br>";
			acMsg += "표준 범위 " + acInfo.getAc_range() + "를 벗어난<br>";
			acMsg += winData.getAc() + this.getPostPosition(winData.getAc()) + " 예측하지 못한 결과로<br>";
			acMsg += "분석되었습니다.";
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
		containMsg += "최근 10회차동안 출현한 번호들중에서는<br>";
		containMsg += containList + "중<br>";
		if (containListCnt > 0) {
			containMsg += "[" + winContainList + "]<br>";
			containMsg += "총 " + containListCnt + "개의 번호가 " + (containListCnt==6?"모두 ":"") + "출현하였고,<br>";
		} else {
			containMsg += "당첨번호가 존재하지 않았으며,<br>";
		}
		containMsg += "<br>";
		
		notContainMsg += "<br>";
		notContainMsg += "최근 10회차동안 출현하지 않은 번호들 중에서는<br>";
		notContainMsg += notContainList + "중<br>";
		if (notContainListCnt > 0) {
			notContainMsg += "[" + winNotContainList + "]<br>";
			notContainMsg += "총 " + notContainListCnt + "개의 번호가 당첨번호로 " + (notContainListCnt==6?"모두 ":"") + "출현했습니다.<br>";
		} else {
			notContainMsg += "당첨번호는 존재하지 않았습니다.<br>";
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

}
