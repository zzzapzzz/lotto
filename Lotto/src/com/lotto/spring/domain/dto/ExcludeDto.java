package com.lotto.spring.domain.dto;

/**
 * 제외수 정보
 * 
 * @author cremazer
 *
 */
public class ExcludeDto extends DefaultDto{
	/** 예상회차 (EX_COUNT) */
	private int ex_count;
	/** 번호1규칙 (NUM1_RULE) */
	private int num1_rule;
	/** 번호2규칙 (NUM2_RULE) */
	private int num2_rule;
	/** 번호3규칙 (NUM3_RULE) */
	private int num3_rule;
	/** 번호4규칙 (NUM4_RULE) */
	private int num4_rule;
	/** 번호5규칙 (NUM5_RULE) */
	private int num5_rule;
	/** 번호6규칙 (NUM6_RULE) */
	private int num6_rule;
	/** 제외수 (EXCLUDE_NUM) */
	private String exclude_num;
	
	public int getEx_count() {
		return ex_count;
	}
	public void setEx_count(int ex_count) {
		this.ex_count = ex_count;
	}
	public int getNum1_rule() {
		return num1_rule;
	}
	public void setNum1_rule(int num1_rule) {
		this.num1_rule = num1_rule;
	}
	public int getNum2_rule() {
		return num2_rule;
	}
	public void setNum2_rule(int num2_rule) {
		this.num2_rule = num2_rule;
	}
	public int getNum3_rule() {
		return num3_rule;
	}
	public void setNum3_rule(int num3_rule) {
		this.num3_rule = num3_rule;
	}
	public int getNum4_rule() {
		return num4_rule;
	}
	public void setNum4_rule(int num4_rule) {
		this.num4_rule = num4_rule;
	}
	public int getNum5_rule() {
		return num5_rule;
	}
	public void setNum5_rule(int num5_rule) {
		this.num5_rule = num5_rule;
	}
	public int getNum6_rule() {
		return num6_rule;
	}
	public void setNum6_rule(int num6_rule) {
		this.num6_rule = num6_rule;
	}
	public String getExclude_num() {
		return exclude_num;
	}
	public void setExclude_num(String exclude_num) {
		this.exclude_num = exclude_num;
	}
		
}
