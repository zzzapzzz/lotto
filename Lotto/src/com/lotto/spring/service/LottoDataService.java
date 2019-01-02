package com.lotto.spring.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.common.LottoUtil;
import com.lotto.spring.domain.dto.CountSumDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.MCNumDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;

@Service("lottoDataService")
public class LottoDataService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());

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
	 * <div id=description><b>회차별 회차합 정보 구하기</b></div ><br>
     * <div id=detail>회차별 회차합 정보를 추출하고, 포함/미포함 번호의 개수를 설정한다.</div ><br>
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
	 * @description <div id=description><b>첫번째 숫자 제외수 - Mod</b></div >
     *              <div id=detail>첫번째 숫자를 45로 나눈 나머지를 더하여 중복되는 수가 있는지 찾는다.</div >
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
	 * @description <div id=description><b>번호간 차이값 설정하기</b></div >
     *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div >
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
	 * @description <div id=description><b>당첨번호 배열 설정하기</b></div >
	 * 
	 * @param winDataList
	 */
	public void setNumbers(List<WinDataAnlyDto> winDataList) {
		for (int i = 0; i < winDataList.size(); i++) {
			WinDataAnlyDto winData = winDataList.get(i);
			int[] difNumbers = this.getDifNumbers(LottoUtil.getNumbers(winData));
			winData.setDifNumbers(difNumbers);
		}
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값 계산하기</b></div >
     *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div >
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
	 * @description <div id=description><b>숫자별 출현번호의 목록 구하기</b></div >
	 *              <div id=detail>각 숫자별 출현번호가 80% 이상 출현한 숫자의 범위를 구한다.</div >
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
//			System.out.println( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent*100) + "%");
			
			numbersList.add(countData[i][0]);
			sumPercent += percent*100;			
			
			//80%이상이면 종료
			if(sumPercent > 80.0){
//				System.out.println("총 출현률 : " + sumPercent + "%");
				break;
			}
		}
		
//		System.out.println("================================");
		
		return numbersList;
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값의 표준범위 구하기</b></div >
	 *              <div id=detail>각 번호간 차이값을 구해 80% 이상 출현한 차이값 범위를 구한다.</div >
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
			
			numbersRangeList.add(this.getMaxMinByRangeDif(rangeCount, difList, winDataList));
			
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
//			System.out.println( countData[i][0] + " : " + countData[i][1] + " --- 출현률 : " + (percent*100) + "%");
			
			sumPercent += percent*100;			
			
			//80%이상이면 종료
			if(sumPercent > 80.0){
//				System.out.println("총 출현률 : " + sumPercent + "%");
				break;
			}
		}
				
//		System.out.println("================================");
		map.put("min", min);
		map.put("max", max);
		
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
}
