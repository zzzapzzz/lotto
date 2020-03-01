package com.lotto.spring.domain.dto;

/**
 * 번호별 정보
 * 
 * @author cremazer
 * @since 2020.02.08
 */
public class NumberStatDto extends DefaultDto{
	/** 번호 */
	private int number;
	/** 출현개수 */
	private int appearCnt;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getAppearCnt() {
		return appearCnt;
	}
	public void setAppearCnt(int appearCnt) {
		this.appearCnt = appearCnt;
	}
		
}
