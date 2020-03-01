package com.lotto.spring.domain.dto;

/**
 * 홀짝비율 정보
 * 
 * @author cremazer
 *
 */
public class OddEvenDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 홀짝비율유형 (ODDEVEN_TYPE) */
	private String oddeven_type;
	/** 비율 (RATIO) */
	private String ratio;
	
	/** 출현회수 (통계) */
	private int appearCnt;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public String getOddeven_type() {
		return oddeven_type;
	}
	public void setOddeven_type(String oddeven_type) {
		this.oddeven_type = oddeven_type;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public int getAppearCnt() {
		return appearCnt;
	}
	public void setAppearCnt(int appearCnt) {
		this.appearCnt = appearCnt;
	}
}