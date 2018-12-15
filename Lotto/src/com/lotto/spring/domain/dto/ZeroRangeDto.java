package com.lotto.spring.domain.dto;

/**
 * 미출현구간대 정보
 * 
 * @author cremazer
 *
 */
public class ZeroRangeDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 구간1 (RANGE1) */
	private int range1;
	/** 구간2 (RANGE2) */
	private int range2;
	/** 구간3 (RANGE3) */
	private int range3;
	/** 구간4 (RANGE4) */
	private int range4;
	/** 구간5 (RANGE5) */
	private int range5;
	/** 미출구간개수 (CNT) */
	private int zero_cnt;
	/** 미출구간 (ZERO_RANGE) */
	private String zero_range;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getRange1() {
		return range1;
	}
	public void setRange1(int range1) {
		this.range1 = range1;
	}
	public int getRange2() {
		return range2;
	}
	public void setRange2(int range2) {
		this.range2 = range2;
	}
	public int getRange3() {
		return range3;
	}
	public void setRange3(int range3) {
		this.range3 = range3;
	}
	public int getRange4() {
		return range4;
	}
	public void setRange4(int range4) {
		this.range4 = range4;
	}
	public int getRange5() {
		return range5;
	}
	public void setRange5(int range5) {
		this.range5 = range5;
	}
	public int getZero_cnt() {
		return zero_cnt;
	}
	public void setZero_cnt(int zero_cnt) {
		this.zero_cnt = zero_cnt;
	}
	public String getZero_range() {
		return zero_range;
	}
	public void setZero_range(String zero_range) {
		this.zero_range = zero_range;
	}
	
}
