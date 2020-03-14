package com.lotto.spring.domain.dto;

/**
 * 서비스 신청정보
 * 
 * @author cremazer
 *
 */
public class ServiceApplyDto extends DefaultDto{
	/** 신청 순번 */
	private int seq;
	/** 회원번호 */
	private int userNo;
	/** 신청유형 */
	private int applyType;
	/** 서비스상태 */
	private String svcStat;
	/** 신청일자 */
	private String applyDate;
	/** 승인일자 */
	private String startDate;
	/** 종료일자 */
	private String endDate;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getApplyType() {
		return applyType;
	}
	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}
	public String getSvcStat() {
		return svcStat;
	}
	public void setSvcStat(String svcStat) {
		this.svcStat = svcStat;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
