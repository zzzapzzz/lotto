package com.lotto.spring.domain.dto;

/**
 * AC 정보
 * 
 * @author cremazer
 *
 */
public class AcDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 최저AC (LOW_AC) */
	private int low_ac;
	/** 최고AC (HIGH_AC) */
	private int high_ac;
	/** AC범위 (AC_RANGE) */
	private String ac_range;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getLow_ac() {
		return low_ac;
	}
	public void setLow_ac(int low_ac) {
		this.low_ac = low_ac;
	}
	public int getHigh_ac() {
		return high_ac;
	}
	public void setHigh_ac(int high_ac) {
		this.high_ac = high_ac;
	}
	public String getAc_range() {
		return ac_range;
	}
	public void setAc_range(String ac_range) {
		this.ac_range = ac_range;
	}
	
		
}
