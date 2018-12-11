package com.lotto.spring.domain.dto;

import java.util.List;

/**
 * 회차합 정보
 * 
 * @author cremazer
 *
 */
public class CountSumDto extends DefaultDto{
	/** 당첨회차 (WIN_COUNT) */
	private int win_count;
	/** 회차합 (COUNT_SUM) */
	private int count_sum;
	/** 회차합 포함개수 (CONT_CNT) */
	private int cont_cnt;
	/** 회차합 미포함개수 (NOT_CONT_CNT) */
	private int not_cont_cnt;
	/** 회차합 포함숫자 (CONT_NUM) */
	private String cont_num;
	/** 회차합 미포함숫자 (NOT_CONT_NUM) */
	private String not_cont_num;
	
	/** 회차합 포함숫자목록 */
	private List<Integer> containList;
	/** 회차합 미포함숫자목록 */
	private List<Integer> notContainList;
	
	public int getWin_count() {
		return win_count;
	}
	public void setWin_count(int win_count) {
		this.win_count = win_count;
	}
	public int getCount_sum() {
		return count_sum;
	}
	public void setCount_sum(int count_sum) {
		this.count_sum = count_sum;
	}
	public int getCont_cnt() {
		return cont_cnt;
	}
	public void setCont_cnt(int cont_cnt) {
		this.cont_cnt = cont_cnt;
	}
	public int getNot_cont_cnt() {
		return not_cont_cnt;
	}
	public void setNot_cont_cnt(int not_cont_cnt) {
		this.not_cont_cnt = not_cont_cnt;
	}
	public String getCont_num() {
		return cont_num;
	}
	public void setCont_num(String cont_num) {
		this.cont_num = cont_num;
	}
	public String getNot_cont_num() {
		return not_cont_num;
	}
	public void setNot_cont_num(String not_cont_num) {
		this.not_cont_num = not_cont_num;
	}
	public List<Integer> getContainList() {
		return containList;
	}
	public void setContainList(List<Integer> containList) {
		this.containList = containList;
	}
	public List<Integer> getNotContainList() {
		return notContainList;
	}
	public void setNotContainList(List<Integer> notContainList) {
		this.notContainList = notContainList;
	}
		
}
