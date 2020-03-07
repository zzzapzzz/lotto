package com.lotto.spring.domain.dto;

/**
 * 서비스 정보
 * 
 * @author cremazer
 *
 */
public class InfoServiceDto extends DefaultDto{
	/** 신청 순번 */
	private int seq;
	/** 회원번호 */
	private int userNo;
	/** 신청유형 */
	private int applyType;
	
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
}
