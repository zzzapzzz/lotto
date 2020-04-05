package com.lotto.spring.domain.dto;

/**
 * MY로또저장번호 정보
 * 
 * @author cremazer
 *
 */
public class MyLottoSaveNumDto extends DefaultDto{
	/** 예상회차 */
	private int ex_count;
	/** 회원번호 */
	private int user_no;
	/** 순번 */
	private int seq;
	/** 번호1 */
	private int num1;
	/** 번호2 */
	private int num2;
	/** 번호3 */
	private int num3;
	/** 번호4 */
	private int num4;
	/** 번호5 */
	private int num5;
	/** 번호6 */
	private int num6;
	/** 당첨결과 */
	private String win_rslt;
	/** 이메일발송여부 */
	private String email_send_yn;
	/** 문자발송여부 */
	private String sms_send_yn;
	/** 등록일자 */
	private String cr_dt;
	/** 수정일자 */
	private String up_dt;
	
	/** 순번목록 */
	private String seqs;
	/** 등록개수 */
	private int maxSaveCnt;
	/** 미사용 조회여부 */
	private String onlyUnused;
	
	public int getEx_count() {
		return ex_count;
	}
	public void setEx_count(int ex_count) {
		this.ex_count = ex_count;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public int getNum4() {
		return num4;
	}
	public void setNum4(int num4) {
		this.num4 = num4;
	}
	public int getNum5() {
		return num5;
	}
	public void setNum5(int num5) {
		this.num5 = num5;
	}
	public int getNum6() {
		return num6;
	}
	public void setNum6(int num6) {
		this.num6 = num6;
	}
	public String getWin_rslt() {
		return win_rslt;
	}
	public void setWin_rslt(String win_rslt) {
		this.win_rslt = win_rslt;
	}
	public String getEmail_send_yn() {
		return email_send_yn;
	}
	public void setEmail_send_yn(String email_send_yn) {
		this.email_send_yn = email_send_yn;
	}
	public String getSms_send_yn() {
		return sms_send_yn;
	}
	public void setSms_send_yn(String sms_send_yn) {
		this.sms_send_yn = sms_send_yn;
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
	public String getSeqs() {
		return seqs;
	}
	public void setSeqs(String seqs) {
		this.seqs = seqs;
	}
	public int getMaxSaveCnt() {
		return maxSaveCnt;
	}
	public void setMaxSaveCnt(int maxSaveCnt) {
		this.maxSaveCnt = maxSaveCnt;
	}
	public String getOnlyUnused() {
		return onlyUnused;
	}
	public void setOnlyUnused(String onlyUnused) {
		this.onlyUnused = onlyUnused;
	}
}
