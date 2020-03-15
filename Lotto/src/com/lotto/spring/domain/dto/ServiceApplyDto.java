package com.lotto.spring.domain.dto;

/**
 * 서비스 신청정보
 * 
 * @author cremazer
 * @since 2020.03.07
 * @modify 컬럼명에 맞게 변수명을 _로 변경.(2020.03.14)
 */
public class ServiceApplyDto extends DefaultDto{
	/** 회원번호 */
	private int user_no;
	/** 서비스코드 */
	private String svc_cd;
	/** 서비스명 */
	private String svc_nm;
	/** 신청 순번 */
	private int seq;
	/** 신청유형 */
	private int apply_type;
	/** 신청유형 */
	private String apply_type_nm;
	/** 서비스상태 */
	private String svc_stat;
	/** 서비스상태명 */
	private String svc_stat_nm;
	/** 신청일자 */
	private String apply_date;
	/** 승인일자 */
	private String start_date;
	/** 종료일자 */
	private String end_date;
	
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getSvc_cd() {
		return svc_cd;
	}
	public void setSvc_cd(String svc_cd) {
		this.svc_cd = svc_cd;
	}
	public String getSvc_nm() {
		return svc_nm;
	}
	public void setSvc_nm(String svc_nm) {
		this.svc_nm = svc_nm;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getApply_type() {
		return apply_type;
	}
	public void setApply_type(int apply_type) {
		this.apply_type = apply_type;
	}
	public String getApply_type_nm() {
		return apply_type_nm;
	}
	public void setApply_type_nm(String apply_type_nm) {
		this.apply_type_nm = apply_type_nm;
	}
	public String getSvc_stat() {
		return svc_stat;
	}
	public void setSvc_stat(String svc_stat) {
		this.svc_stat = svc_stat;
	}
	public String getSvc_stat_nm() {
		return svc_stat_nm;
	}
	public void setSvc_stat_nm(String svc_stat_nm) {
		this.svc_stat_nm = svc_stat_nm;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
}
