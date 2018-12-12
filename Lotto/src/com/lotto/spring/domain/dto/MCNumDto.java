package com.lotto.spring.domain.dto;

/**
 * 궁합수 정보
 * 
 * @author cremazer
 *
 */
public class MCNumDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 번호 (NUM) */
	private int num;
	/** 궁합수 (MC_NUM) */
	private String mc_num;
	/** 불협수 (NOT_MC_NUM) */
	private String not_mc_num;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMc_num() {
		return mc_num;
	}
	public void setMc_num(String mc_num) {
		this.mc_num = mc_num;
	}
	public String getNot_mc_num() {
		return not_mc_num;
	}
	public void setNot_mc_num(String not_mc_num) {
		this.not_mc_num = not_mc_num;
	}
}