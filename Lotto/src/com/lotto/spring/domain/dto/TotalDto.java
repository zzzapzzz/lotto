package com.lotto.spring.domain.dto;

/**
 * 총합 정보
 * 
 * @author cremazer
 *
 */
public class TotalDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 최저총합 (LOW_TOTAL) */
	private int low_total;
	/** 최고총합 (HIGH_TOTAL) */
	private int high_total;
	/** 총합범위 (TOTAL_RANGE) */
	private String total_range;
	
	/** 총합 (통계) */
	private int total;
	/** 출현회수 (통계) */
	private int appearCnt;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getLow_total() {
		return low_total;
	}
	public void setLow_total(int low_total) {
		this.low_total = low_total;
	}
	public int getHigh_total() {
		return high_total;
	}
	public void setHigh_total(int high_total) {
		this.high_total = high_total;
	}
	public String getTotal_range() {
		return total_range;
	}
	public void setTotal_range(String total_range) {
		this.total_range = total_range;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getAppearCnt() {
		return appearCnt;
	}
	public void setAppearCnt(int appearCnt) {
		this.appearCnt = appearCnt;
	}
	
	
		
}
