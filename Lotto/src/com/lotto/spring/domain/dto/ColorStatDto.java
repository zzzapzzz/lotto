package com.lotto.spring.domain.dto;

/**
 * 색상별 정보
 * 
 * @author cremazer
 * @since 2020.02.09
 */
public class ColorStatDto extends DefaultDto{
	/** 
	 * 구간
	 * 1~10 
	 * 11~20 
	 * 21~30 
	 * 31~40 
	 * 41~45 
	 */
	private String range;
	/** 출현비율 */
	private double appearPercent;
	
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public double getAppearPercent() {
		return appearPercent;
	}
	public void setAppearPercent(double appearPercent) {
		this.appearPercent = appearPercent;
	}
	
		
}
