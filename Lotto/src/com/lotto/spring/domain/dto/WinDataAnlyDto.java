package com.lotto.spring.domain.dto;

/**
 * 당첨번호 분석정보
 * 
 * @author cremazer
 *
 */
public class WinDataAnlyDto extends DefaultDto{
	/** 당첨회차 */
	private int win_count;
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
	/** 보너스번호 */
	private int bonus_num;
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
	/** 등록일자 */
	private String cr_dt;
	/** 수정일자 */
	private String up_dt;
	
	/** 회차합 (COUNT_SUM) */
	private int count_sum;
	/** 회차합 포함개수 (CONT_CNT) */
	private int cont_cnt;
	/** 회차합 미포함개수 (NOT_CONT_CNT) */
	private int not_cont_cnt;
	
	//번호간 차이
	private int[] difNumbers;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
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
	public int getBonus_num() {
		return bonus_num;
	}
	public void setBonus_num(int bonus_num) {
		this.bonus_num = bonus_num;
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
	public String getCr_dt() {
		return cr_dt;
	}
	public void setCr_dt(String cr_dt) {
		this.cr_dt = cr_dt;
	}
	public String getUp_dt() {
		return up_dt;
	}
	public void setUp_dt(String up_dt) {
		this.up_dt = up_dt;
	}
	public int getCount_sum() {
		return count_sum;
	}
	public void setCount_sum(int count_sum) {
		this.count_sum = count_sum;
	}
	public int getCont_cnt() {
		return cont_cnt;
	}
	public void setCont_cnt(int cont_cnt) {
		this.cont_cnt = cont_cnt;
	}
	public int getNot_cont_cnt() {
		return not_cont_cnt;
	}
	public void setNot_cont_cnt(int not_cont_cnt) {
		this.not_cont_cnt = not_cont_cnt;
	}
	public int[] getDifNumbers() {
		return difNumbers;
	}
	public void setDifNumbers(int[] difNumbers) {
		this.difNumbers = difNumbers;
	}
	
}
