package com.lotto.spring.domain.dto;

/**
 * 구간별 정보
 * 
 * @author cremazer
 * @since 2020.02.10
 */
public class RangeStatDto extends DefaultDto{
	/** 
	 * 구간
	 * 1~10 
	 * 11~20 
	 * 21~30 
	 * 31~40 
	 * 41~45 
	 */
	private String range;
	/** 출현건수 */
	private int appearCnt;
	
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public int getAppearCnt() {
		return appearCnt;
	}
	public void setAppearCnt(int appearCnt) {
		this.appearCnt = appearCnt;
	}
	
		
}
