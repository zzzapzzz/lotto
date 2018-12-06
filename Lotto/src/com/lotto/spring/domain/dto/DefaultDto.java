package com.lotto.spring.domain.dto;

public class DefaultDto {
	
	/** 행번호 */
	private String row_num;
	/** 등록자 회원번호 */
	private int reg_user_no;
	/** 접속IP */
	private String access_ip;
	
	/* jqgrid search option */
	private String page;
	private String rows;
	private String sidx;
	private String sord;
	private int startNum;
	private int endNum;
	
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
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = 1 + ( (Integer.parseInt(this.page) - 1 ) * Integer.parseInt(this.rows) );
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = Integer.parseInt(this.page)*Integer.parseInt(this.rows);
	}
	
}
