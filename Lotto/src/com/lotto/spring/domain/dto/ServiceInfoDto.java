package com.lotto.spring.domain.dto;

/**
 * 서비스 정보
 * 
 * @author cremazer
 * @since 2020.03.14
 */
public class ServiceInfoDto extends DefaultDto{
	/** 서비스코드 */
	private String svc_cd;
	/** 서비스명 */
	private String svc_nm;
	/** 서비스구분 */
	private String svc_type;
	/** 서비스기준 */
	private String svc_base_type;
	/** 서비스기준값 */
	private String svc_base_val;
	/** 금액 */
	private String amount;
	/** 프로모션코드 */
	private String prmt_cd;
	/** 사용여부 */
	private String use_yn;
	/** 등록일자 */
	private String cr_dt;
	/** 수정일자 */
	private String up_dt;
	
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
	public String getSvc_type() {
		return svc_type;
	}
	public void setSvc_type(String svc_type) {
		this.svc_type = svc_type;
	}
	public String getSvc_base_type() {
		return svc_base_type;
	}
	public void setSvc_base_type(String svc_base_type) {
		this.svc_base_type = svc_base_type;
	}
	public String getSvc_base_val() {
		return svc_base_val;
	}
	public void setSvc_base_val(String svc_base_val) {
		this.svc_base_val = svc_base_val;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPrmt_cd() {
		return prmt_cd;
	}
	public void setPrmt_cd(String prmt_cd) {
		this.prmt_cd = prmt_cd;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getCr_dt() {
		return cr_dt;
	}
	public void setCr_dt(String cr_dt) {
		this.cr_dt = cr_dt;
	}
	public String getUp_dt() {
		return up_dt;
	}
	public void setUp_dt(String up_dt) {
		this.up_dt = up_dt;
	}
	
}
