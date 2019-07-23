package com.lotto.spring.domain.dto;

/**
 * 예상번호 정보
 * 
 * @author cremazer
 *
 */
public class ExDataDto extends DefaultDto{
	/** 예상회차 */
	private int ex_count;
	/** 순번 */
	private int seq;
	/** 번호1 */
	private int num1;
	/** 번호2 */
	private int num2;
	/** 번호3 */
	private int num3;
	/** 번호4 */
	private int num4;
	/** 번호5 */
	private int num5;
	/** 번호6 */
	private int num6;
	/** 총합 */
	private int total;
	/** 끝수합 */
	private int sum_end_num;
	/** 저고비율 */
	private String low_high;
	/** 홀짝비율 */
	private String odd_even;
	/** 산술적복잡도(AC) */
	private int ac;
	/** 예상패턴일치개수 */
	private int ptrn_match_cnt;
	
	/** 다음발표일 */
	private String nextAnnounceDate;
	
	//번호간 차이
	private int[] difNumbers;
	//당첨번호 배열
	private int[] numbers;
		
	public int getEx_count() {
		return ex_count;
	}
	public void setEx_count(int ex_count) {
		this.ex_count = ex_count;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public int getNum4() {
		return num4;
	}
	public void setNum4(int num4) {
		this.num4 = num4;
	}
	public int getNum5() {
		return num5;
	}
	public void setNum5(int num5) {
		this.num5 = num5;
	}
	public int getNum6() {
		return num6;
	}
	public void setNum6(int num6) {
		this.num6 = num6;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSum_end_num() {
		return sum_end_num;
	}
	public void setSum_end_num(int sum_end_num) {
		this.sum_end_num = sum_end_num;
	}
	public String getLow_high() {
		return low_high;
	}
	public void setLow_high(String low_high) {
		this.low_high = low_high;
	}
	public String getOdd_even() {
		return odd_even;
	}
	public void setOdd_even(String odd_even) {
		this.odd_even = odd_even;
	}
	public int getAc() {
		return ac;
	}
	public void setAc(int ac) {
		this.ac = ac;
	}
	public int getPtrn_match_cnt() {
		return ptrn_match_cnt;
	}
	public void setPtrn_match_cnt(int ptrn_match_cnt) {
		this.ptrn_match_cnt = ptrn_match_cnt;
	}
	public String getNextAnnounceDate() {
		return nextAnnounceDate;
	}
	public void setNextAnnounceDate(String nextAnnounceDate) {
		this.nextAnnounceDate = nextAnnounceDate;
	}
	public int[] getDifNumbers() {
		return difNumbers;
	}
	public void setDifNumbers(int[] difNumbers) {
		this.difNumbers = difNumbers;
	}
	public int[] getNumbers() {
		return numbers;
	}
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}
	
	public String toString() {
		return num1 + ", " + num2 + ", " + num3 + ", " + num4 + ", " + num5 + ", " + num6;
	}
	
}
