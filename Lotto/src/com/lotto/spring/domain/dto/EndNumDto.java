package com.lotto.spring.domain.dto;

/**
 * 끝수합 정보
 * 
 * @author cremazer
 *
 */
public class EndNumDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 최저끝수합 (LOW_ENDNUM) */
	private int low_endnum;
	/** 최고끝수합 (HIGH_ENDNUM) */
	private int high_endnum;
	/** 끝수합범위 (ENDNUM_RANGE) */
	private String endnum_range;
	
	/** 끝수합 (통계) */
	private int sumEndNum;
	/** 출현회수 (통계) */
	private int appearCnt;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getLow_endnum() {
		return low_endnum;
	}
	public void setLow_endnum(int low_endnum) {
		this.low_endnum = low_endnum;
	}
	public int getHigh_endnum() {
		return high_endnum;
	}
	public void setHigh_endnum(int high_endnum) {
		this.high_endnum = high_endnum;
	}
	public String getEndnum_range() {
		return endnum_range;
	}
	public void setEndnum_range(String endnum_range) {
		this.endnum_range = endnum_range;
	}
	public int getSumEndNum() {
		return sumEndNum;
	}
	public void setSumEndNum(int sumEndNum) {
		this.sumEndNum = sumEndNum;
	}
	public int getAppearCnt() {
		return appearCnt;
	}
	public void setAppearCnt(int appearCnt) {
		this.appearCnt = appearCnt;
	}
	
	
	
		
}
