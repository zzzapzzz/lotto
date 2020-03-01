package com.lotto.spring.domain.dto;

/**
 * 저고비율 정보
 * 
 * @author cremazer
 *
 */
public class LowHighDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 저고비율유형 (LOWHIGH_TYPE) */
	private String lowhigh_type;
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
	public String getLowhigh_type() {
		return lowhigh_type;
	}
	public void setLowhigh_type(String lowhigh_type) {
		this.lowhigh_type = lowhigh_type;
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