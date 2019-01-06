package com.lotto.spring.domain.dto;

/**
 * 기본 DTO
 * 
 * @author cremazer
 *
 */
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
	
	private String startNum;
	private String endNum;
	
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
		if (this.page==null || "".equals(this.page)) {
			return "1";
		}
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		if (this.rows==null || "".equals(this.rows)) {
			return "99999999";
		} 
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
	public String getStartNum() {
		startNum = Integer.toString(1+((Integer.parseInt(this.getPage())-1) * Integer.parseInt(this.getRows()))); 
		return startNum;
	}
	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}
	public String getEndNum() {
		endNum = Integer.toString(Integer.parseInt(this.getPage())*Integer.parseInt(this.getRows()));
		return endNum;
	}
	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}
}
