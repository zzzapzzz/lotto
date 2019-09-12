package com.lotto.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lotto.spring.domain.dto.ExDataDto;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.MyLottoSaveNumDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;
import com.lotto.spring.domain.dto.WinDataDto;

public final class LottoUtil {

	private LottoUtil() {
		super();
	};

	// 패턴유형
	/** 회차합 포함개수 */
	public static final int CONTAIN_CNT_BY_COUNT = 1;
	/** 전회차 추출번호 */
	public static final int PREV_COUNT = 2; 
	/** 저고비율 */
	public static final int LOW_HIGH_RATIO = 3; 
	/** 홀짝비율 */
	public static final int ODD_EVEN_RATIO = 4; 
	/** 총합의 범위 */
	public static final int TOTAL_RANGE = 5; 
	/** 연속수의 개수 */
	public static final int CONSECUTIVELY_NUMBERS = 6; 
	/** 끝수합의 범위 */
	public static final int SUM_END_NUMBER_RANGE = 7; 
	/** 그룹 내 포함개수 */
	public static final int CONTAIN_GROUP_CNT = 8;
	/** 끝자리가 같은 수의 개수 */
	public static final int END_NUMBER_CNT = 9;
	/** 소수 개수 */
	public static final int SOTSU_CNT = 10;
	/** 3의 배수 개수 */
	public static final int NUMBER_OF_3_CNT = 11;
	/** 합성수의 개수 */
	public static final int NUMBER_OF_NOT_3_CNT = 12;
	/** AC */
	public static final int AC = 13;
	/** 궁합수 개수 */
	public static final int MC_MATCH_CNT = 14;
	
	
	/** 총합범위유형 100이하 */
	public static final int TOTAL_RANGE_TYPE_1 = 1;
	/** 총합범위유형 101 ~ 150 */
	public static final int TOTAL_RANGE_TYPE_2 = 2;
	/** 총합범위유형 151 ~ 200 */
	public static final int TOTAL_RANGE_TYPE_3 = 3;
	/** 총합범위유형 201 이상 */
	public static final int TOTAL_RANGE_TYPE_4 = 4;
	
	public static int[] getNumbersFromObj(Object data) {
		int[] numbers = { 0, 0, 0, 0, 0, 0 };
				
		if (data instanceof WinDataDto) {
			numbers = LottoUtil.getNumbers((WinDataDto)data);
		} else if (data instanceof ExDataDto) {
			numbers = LottoUtil.getNumbers((ExDataDto)data);
		} else if (data instanceof WinDataAnlyDto) {
			numbers = LottoUtil.getNumbers((WinDataAnlyDto)data);
		} else if (data instanceof WinDataAnlyDto) {
			numbers = LottoUtil.getNumbers((MyLottoSaveNumDto)data);
		} else if (data instanceof int[]){
			numbers = (int[])data;
		}
		
		return numbers;
		
	}
	
	public static int ACCESS_CNT = 0;
	
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
	 * 번호 배열 가져오기
	 * 
	 * @param data
	 * @return
	 */
	public static int[] getNumbers(WinDataAnlyDto data) {
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
	 * 번호 배열 가져오기
	 * 
	 * @param data
	 * @return
	 */
	public static int[] getNumbers(MyLottoSaveNumDto data) {
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
	 * 번호 배열 가져오기
	 * 
	 * @param data
	 * @return
	 */
	public static int[] getNumbers(ExDataDto data) {
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
	@SuppressWarnings("unchecked")
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
	public static int getTotal(Object data){
		int total = 0;		
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		
		for(int index = 0 ; index < numbers.length ; index++){			
			total += numbers[index];
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
		
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		
		for(int num : numbers){
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
		
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		
		for(int num : numbers){
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
	public static int getSumEndNumber(Object data){
		int total = 0;
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		
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
	public static int getAc(Object data){
		int ac = 0;
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		
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
	@SuppressWarnings({ "static-access", "deprecation" })
	public static Calendar getNextAccounceCalendar(){
		
		Calendar nextAnnounceCal = Calendar.getInstance();

		// 현재 Calendar
		Calendar todayCal = Calendar.getInstance();
					
		// 오늘이 토요일인가?
		if (7 == todayCal.get(Calendar.DAY_OF_WEEK)
				&& 20 > todayCal.get(Calendar.HOUR_OF_DAY)
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

	public static boolean comparePattern(ExptPtrnAnlyDto sourcePattern, ExptPtrnAnlyDto targetPattern, int patternType) {
		if (patternType == CONTAIN_CNT_BY_COUNT) {
			if (sourcePattern.getCount_sum() == targetPattern.getCount_sum()
					&& sourcePattern.getCont_cnt() == targetPattern.getCont_cnt()
					&& sourcePattern.getNot_cont_cnt() == targetPattern.getNot_cont_cnt()) {
				return true;
			}
		} else if (patternType == PREV_COUNT) {
			if (sourcePattern.getSame_num_cnt() == targetPattern.getSame_num_cnt()
					&& sourcePattern.getUp_1_cnt() == targetPattern.getUp_1_cnt()
					&& sourcePattern.getDown_1_cnt() == targetPattern.getDown_1_cnt()) {
				return true;
			}
		} else if (patternType == LOW_HIGH_RATIO) {
			if (sourcePattern.getLow_high().equals(targetPattern.getLow_high())) {
				return true;
			}
		} else if (patternType == ODD_EVEN_RATIO) {
			if (sourcePattern.getOdd_even().equals(targetPattern.getOdd_even())) {
				return true;
			}
		} else if (patternType == TOTAL_RANGE) {
			if (sourcePattern.getTotal_range_type() == targetPattern.getTotal_range_type()) {
				return true;
			}
		} else if (patternType == CONSECUTIVELY_NUMBERS) {
			if (sourcePattern.getC_num_cnt() == targetPattern.getC_num_cnt()) {
				return true;
			}
		} else if (patternType == CONTAIN_GROUP_CNT) {
			// 그룹내 미포함 구간 비교
			if (sourcePattern.getZeroCntRange().size() == targetPattern.getZeroCntRange().size()) {
				for (int i = 0; i < sourcePattern.getZeroCntRange().size(); i++) {
					if (sourcePattern.getZeroCntRange().get(i) != targetPattern.getZeroCntRange().get(i)) {
						return false;
					}
				}
				// 모든 미포함 구간이 일치하면 같은 패턴임.
				return true;
			}
		} else if (patternType == END_NUMBER_CNT) {
			if (sourcePattern.getEnd_num_same_cnt() == targetPattern.getEnd_num_same_cnt()) {
				return true;
			}
		} else if (patternType == SOTSU_CNT) {
			if (sourcePattern.getP_num_cnt() == targetPattern.getP_num_cnt()) {
				return true;
			}
		} else if (patternType == NUMBER_OF_3_CNT) {
			if (sourcePattern.getMulti_3_cnt() == targetPattern.getMulti_3_cnt()) {
				return true;
			}
		} else if (patternType == NUMBER_OF_NOT_3_CNT) {
			if (sourcePattern.getComp_num_cnt() == targetPattern.getComp_num_cnt()) {
				return true;
			}
		} else if (patternType == AC) {
			if (sourcePattern.getAc() == targetPattern.getAc()) {
				return true;
			}
		} else if (patternType == MC_MATCH_CNT) {
			if (sourcePattern.getMcnum_cnt() == targetPattern.getMcnum_cnt()) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * @description <div id=description><b>소수 구하기</b></div >
	 *              <div id=detail>소수인지 아닌지 확인한다.</div >
	 * @param number
	 * @return
	 */
	public static boolean getSotsu(int number){
		boolean result = false;
		
		if(number < 2) return false;
		
		for(int i = 2 ; i <= number ; i++){
			if(number % i == 0){
				if(number == i){
					return true;
				}else{
					break;
				}
			}
		}
		
		return result;
	}

	/**
	 * 임의의 회차합 가져오기
	 * 
	 * @return 7~10
	 */
	public static int getRandomContainCnt() {
		int containCnt = 0;
		
		do {
			containCnt = (int)(Math.random()*10) + 1; 
		} while (!(containCnt >= 7 && containCnt <= 10));	//2017.10.29 not조건 수정
		
		return containCnt;
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값 계산하기</b></div>
     *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div>
	 * @param data
	 * @return
	 */
	public static int[] getDifNumbers(Object data) {
		int[] numbers = LottoUtil.getNumbersFromObj(data);
		return LottoUtil.getDifNumbers(numbers);
	}
	
	/**
	 * @description <div id=description><b>번호간 차이값 계산하기</b></div>
	 *              <div id=detail>각 번호의 차이를 비교하여 설정한다.</div>
	 * @param numbers
	 * @return
	 */
	public static int[] getDifNumbers(int[] numbers) {
		int[] difNubmers = {0,0,0,0,0};
		
		for (int i = 0; i < numbers.length -1 ; i++) {
			difNubmers[i] = Math.abs(numbers[i] - numbers[i+1]);
		}
		
		return difNubmers;
	}

	/**
	 * 퍼센트(%) 구하기
	 * 소수점 2째자리(0.00%)
	 * 
	 * @param dividend 피제수
	 * @param divisor 제수
	 * @return 0.00
	 */
	public static double getPercent(int dividend, int divisor) {
		double d_cnt = dividend;
		double d_total = divisor;
		DecimalFormat df = new DecimalFormat("#.##");
		if (0 != divisor) {
			return Double.parseDouble(df.format( d_cnt/d_total*100 ));
		} else {
			return 0.0;
		}
	}
}
