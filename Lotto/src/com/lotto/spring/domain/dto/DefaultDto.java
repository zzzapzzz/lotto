package com.lotto.spring.domain.dto;

public class DefaultDto {
	
	/** 행번호 */
	private String row_num;
	/** 등록자 회원번호 */
	private int reg_user_no;
	/** 접속IP */
	private String access_ip;
	
	public String getRow_num() {
		return row_num;
	}
	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}
	public int getReg_user_no() {
		return reg_user_no;
	}
	public void setReg_user_no(int reg_user_no) {
		this.reg_user_no = reg_user_no;
	}
	public String getAccess_ip() {
		return access_ip;
	}
	public void setAccess_ip(String access_ip) {
		this.access_ip = access_ip;
	}
	
	
}
