package com.lotto.common;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lotto.spring.domain.dto.WinDataDto;

public final class LottoUtil {

	private LottoUtil() {
		super();
	};

	/**
	 * 번호 배열 가져오기
	 * 
	 * @param data
	 * @return
	 */
	public static int[] getNumbers(WinDataDto data) {
		int[] numbers = { 0, 0, 0, 0, 0, 0 };
		numbers[0] = data.getNum1();
		numbers[1] = data.getNum2();
		numbers[2] = data.getNum3();
		numbers[3] = data.getNum4();
		numbers[4] = data.getNum5();
		numbers[5] = data.getNum6();

		return numbers;
	}

	/**
	 * @description <div id=description><b>데이터 정렬</b></div >
     *              <div id=detail>데이터를 정렬한다.</div >
     * @param data
	 * @return
	 */
	public static Object dataSort(Object data){
		Object obj = new Object();
		
		if(data instanceof List){
			Collections.sort((List<Integer>)data);
			obj = data;
		}else if(data instanceof int[]){
			for (int i = 0; i < ((int[])data).length - 1; i++) {
				for (int j = i+1; j < ((int[])data).length; j++) {
					if(((int[])data)[i] > ((int[])data)[j]){
						int temp = ((int[])data)[i];
						((int[])data)[i] = ((int[])data)[j];
						((int[])data)[j] = temp;
					}
				}
			}
			obj = data;
		}
		
		return obj;
	}
	
	/**
	 * @description <div id=description><b>비율 구하기</b></div >
     *              <div id=detail>두 수를 나눠 소수점 첫째짜리까지의 비율을 구한다.</div >
     * @param operand
	 * @param operator
	 * @return
	 */
	public static Double getRatio(int operand, int operator){
		double result = (double)operand / (double)operator * 100;
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		return Double.valueOf(nf.format(result));
	}
	
	/**
	 * 비율 정보 
	 * @return
	 */
	public static String[] getRatioTitle() {
		return new String[]{"0:6","1:5","2:4","3:3","4:2","5:1","6:0"};
	}
	
	/**
	 * @description <div id=description><b>총합 계산</b></div >
     *              <div id=detail>6개 번호의 총합을 계산한다.</div >
     * @param data
	 * @return
	 */
	public static int getTotal(WinDataDto data){
		int total = 0;		
		int[] datas = LottoUtil.getNumbers(data);
		
		for(int index = 0 ; index < datas.length ; index++){			
			total += datas[index];
		}
		
		return total;
	}
	
	/**
	 * @description <div id=description><b>고저 계산</b></div >
     *              <div id=detail>6개 번호의 낮고 높은 비율을 계산한다.</div >
     * @param data
	 * @return
	 */
	public static String getLowHigh(Object data){
		int low = 0;
		int high = 0;
		
		int[] datas = null;
		
		if(data instanceof WinDataDto){
			datas = LottoUtil.getNumbers((WinDataDto)data);
		}else if(data instanceof int[]){
			datas = (int[])data;
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
	 * @description <div id=description><b>홀짝 계산</b></div >
     *              <div id=detail>6개 번호의 홀수,짝수 비율을 계산한다.</div >
     * @param data
	 * @return
	 */
	public static String getOddEven(Object data){
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
	 * @description <div id=description><b>끝수합 구하기</b></div >
	 *              <div id=detail>끝수합을 구한다.</div >
	 * @param data
	 * @return
	 */
	public static int getSumEndNumber(WinDataDto data){
		int total = 0;
		int[] numbers = LottoUtil.getNumbers(data);
		
		for(int index = 0 ; index < numbers.length ; index++){
			total += numbers[index] % 10;
		}
		
		return total;
	}
	
	/**
	 * @description <div id=description><b>산술적 복잡도(AC) 구하기</b></div >
	 *              <div id=detail>포아송 분포법을 이용하여 산술적 복잡도를 구한다.</div >
	 * @param data
	 * @return
	 */
	public static int getAc(WinDataDto data){
		int ac = 0;
		int[] numbers = LottoUtil.getNumbers(data);
		
		List<Integer> list = new ArrayList<Integer>();	//전체 개수를 세기 위한 리스트
		Map<Integer, Integer> mapData = new HashMap<Integer, Integer>();	//숫자 판별을 위한 해쉬맵
		
		for(int row = numbers.length - 1 ; row > 0 ; row--){	//행 계산
			for(int col = row - 1 ; col >= 0 ; col--){			//열 계산
				int resultNum = numbers[row] - numbers[col];
				if(!mapData.containsKey(resultNum)){
					list.add(resultNum);
					mapData.put(resultNum, resultNum);
				}
			}
		}
		
		ac = list.size() - 5;	// 목록개수 - (r - 1) : r은 로또당첨개수
		
		return ac;
	}
	
	/**
	 * @description <div id=description><b>마지막 입력된 당첨번호의 날짜 구하기</b></div >
	 *              <div id=detail>마지막 입력된 당첨번호의 날짜(Calendar)를 구한다.</div >
	 * @param lastData
	 * @return
	 */
	public static Calendar getLastDataCalendar(WinDataDto lastData){
		
		String lastCrDt = lastData.getCr_dt();
		
		Calendar lastCrDtCal = Calendar.getInstance();
		lastCrDtCal.set(Calendar.YEAR, Integer.parseInt(lastCrDt.substring(0, 4)));
		lastCrDtCal.set(Calendar.MONTH, Integer.parseInt(lastCrDt.substring(4, 6)) - 1);
		lastCrDtCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(lastCrDt.substring(6, 8)));
		lastCrDtCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(lastCrDt.substring(8, 10)));
		lastCrDtCal.set(Calendar.MINUTE, Integer.parseInt(lastCrDt.substring(10, 12)));
		lastCrDtCal.set(Calendar.SECOND, Integer.parseInt(lastCrDt.substring(12, 14)));
		
		return lastCrDtCal;
	}
	
	/**
	 * @description <div id=description><b>다음 회차 날짜 구하기</b></div >
	 *              <div id=detail>다음 로또 당첨번호 발표 날짜(Calendar)를 구한다.<br>
	 *              마감은 토요일 20시 설정
	 *              </div >
	 * @return
	 */
	public static Calendar getNextAccounceCalendar(){
		
		Calendar nextAnnounceCal = Calendar.getInstance();

		// 현재 Calendar
		Calendar todayCal = Calendar.getInstance();
					
		// 오늘이 토요일인가?
		if (7 == todayCal.get(Calendar.DAY_OF_WEEK)
				&& 20 > todayCal.get(Calendar.HOUR)
				) {
			// 조회시간이 토요일, 마감시간 이전이면 당일날 마감일시로 설정
			
			nextAnnounceCal.set(Calendar.HOUR_OF_DAY, 20);
			nextAnnounceCal.set(Calendar.MINUTE, 0);
			nextAnnounceCal.set(Calendar.SECOND, 0);
		} else {
			// 조회시간이 토요일이 아니라면 다음 토요일로 설정
			
			nextAnnounceCal.add(Calendar.DAY_OF_MONTH, 1);    //1일 후
			for (int i = 0; i < nextAnnounceCal.DAY_OF_WEEK; i++) {
				Date date = nextAnnounceCal.getTime();
				if(date.getDay() == 6){
					break;
				}
				nextAnnounceCal.add(Calendar.DAY_OF_MONTH, 1);    //1일 후
			}
		}
		
		// 마감시간 설정 (20시)
		nextAnnounceCal.set(Calendar.HOUR_OF_DAY, 20);
		nextAnnounceCal.set(Calendar.MINUTE, 0);
		nextAnnounceCal.set(Calendar.SECOND, 0);
		
		return nextAnnounceCal;
	}
	
}
