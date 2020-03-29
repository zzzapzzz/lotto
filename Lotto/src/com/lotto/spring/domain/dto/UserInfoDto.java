package com.lotto.spring.domain.dto;

/**
 * 회원 정보
 * 
 * @author cremazer
 *
 */
public class UserInfoDto extends DefaultDto{
	/** 회원번호 */
	private int user_no;
	/** 이메일 */
	private String email;
	/** 닉네임 */
	private String nickname;
	/** 회원고유키 */
	private String user_key;
	/** 등급 */
	private String grade;
	/** 포인트 */
	private int point;
	/** 사용포인트 */
	private int used_point;
	/** 이동전화번호 */
	private String mbtlnum;
	/** 비밀번호 */
	private String thwd;
	/** 비밀번호찾기 질문 */
	private String thwd_q;
	/** 비밀번호찾기 답변 */
	private String thwd_a;
	/** 추천인 회원번호 */
	private int rcmd_user_no;
	/** 서비스약관동의여부 */
	private String svc_agree_yn;
	/** 로그인실패횟수 */
	private int login_fail_cnt;
	/** 업무권한코드 */
	private String auth_task;
	/** 업무권한명 */
	private String auth_task_nm;
	/** 메뉴권한코드 */
	private String auth_menu;
	/** 메뉴권한명 */
	private String auth_menu_nm;
	/** 사용여부코드 */
	private String use_yn;	
	/** 사용여부명 */
	private String use_yn_nm;	
	/** 등록일자 */
	private String cr_dt;
	/** 수정일자 */
	private String up_dt;
	
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getUsed_point() {
		return used_point;
	}
	public void setUsed_point(int used_point) {
		this.used_point = used_point;
	}
	public String getMbtlnum() {
		return mbtlnum;
	}
	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}
	public String getThwd() {
		return thwd;
	}
	public void setThwd(String thwd) {
		this.thwd = thwd;
	}
	public String getThwd_q() {
		return thwd_q;
	}
	public void setThwd_q(String thwd_q) {
		this.thwd_q = thwd_q;
	}
	public String getThwd_a() {
		return thwd_a;
	}
	public void setThwd_a(String thwd_a) {
		this.thwd_a = thwd_a;
	}
	public int getRcmd_user_no() {
		return rcmd_user_no;
	}
	public void setRcmd_user_no(int rcmd_user_no) {
		this.rcmd_user_no = rcmd_user_no;
	}
	public String getSvc_agree_yn() {
		return svc_agree_yn;
	}
	public void setSvc_agree_yn(String svc_agree_yn) {
		this.svc_agree_yn = svc_agree_yn;
	}
	public int getLogin_fail_cnt() {
		return login_fail_cnt;
	}
	public void setLogin_fail_cnt(int login_fail_cnt) {
		this.login_fail_cnt = login_fail_cnt;
	}
	public String getAuth_task() {
		return auth_task;
	}
	public void setAuth_task(String auth_task) {
		this.auth_task = auth_task;
	}
	public String getAuth_task_nm() {
		return auth_task_nm;
	}
	public void setAuth_task_nm(String auth_task_nm) {
		this.auth_task_nm = auth_task_nm;
	}
	public String getAuth_menu() {
		return auth_menu;
	}
	public void setAuth_menu(String auth_menu) {
		this.auth_menu = auth_menu;
	}
	public String getAuth_menu_nm() {
		return auth_menu_nm;
	}
	public void setAuth_menu_nm(String auth_menu_nm) {
		this.auth_menu_nm = auth_menu_nm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getUse_yn_nm() {
		return use_yn_nm;
	}
	public void setUse_yn_nm(String use_yn_nm) {
		this.use_yn_nm = use_yn_nm;
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
