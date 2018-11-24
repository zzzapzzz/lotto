package com.lotto.spring.domain.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

public class UserSession {
	
	public static final String[][] RESULT_ARR = {
			{"F01","아이디 또는 비밀번호 누락"},
			{"F02","아이디 또는 비밀번호가 일치하지 않습니다."},
			{"F05","비밀번호 오류 횟수가 5회 초과하였습니다. 비밀번호를 다시 설정하시겠습니까?"},
			{"F06","접근이 허용되지 않은 IP입니다."},
			{"T01","초기화되거나 유효기간이 만료된 비밀번호입니다."},
			{"T03","접근할 수 없는 사용자입니다. 관리자에게 문의바랍니다."},
			{"T04","암호를 변경한 지 3개월이 경과되었거나 변경한 이력이 없습니다. 비밀번호를 다시 설정하시겠습니까?"},
			{"T05","사용 권한이 없습니다. 관리자에게 문의바랍니다."},
			{"T06","접속 권한이 없습니다. 관리자에게 문의바랍니다."},
			{"T","로그인 성공"}
	};
	
	private int user_no;
	private String email;
	private String nickname;
	private String grade;
	private int point;
	private int used_point;
	private String mbtlnum;
	private String thwd;
	private String rcmd_nick;
	private String svc_agreen_yn;
	private int login_fail_cnt;
	private String auth_task;
	private String auth_menu;
	private String cr_dt;
	private String up_dt;
    
	private boolean isAdmin = false; 
    
    private List<HashMap<String, String>> gnbMenu;
    
    private String currMenuCd = "";

    private List<CaseInsensitiveMap> GNBmenulist;
    
    private List<CaseInsensitiveMap> lnbMenuAuthList;
    
    @SuppressWarnings("rawtypes")
	private Map taskAuth;	//(구)DepthC
    
    private String beforeUrl = "";
    
    private String nextUrl = "";
    
    private String menu = "";
    
    @SuppressWarnings("rawtypes")
	private List<HashMap> menuAuthUrlList;

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

	public String getRcmd_nick() {
		return rcmd_nick;
	}

	public void setRcmd_nick(String rcmd_nick) {
		this.rcmd_nick = rcmd_nick;
	}

	public String getSvc_agreen_yn() {
		return svc_agreen_yn;
	}

	public void setSvc_agreen_yn(String svc_agreen_yn) {
		this.svc_agreen_yn = svc_agreen_yn;
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

	public String getAuth_menu() {
		return auth_menu;
	}

	public void setAuth_menu(String auth_menu) {
		this.auth_menu = auth_menu;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<HashMap<String, String>> getGnbMenu() {
		return gnbMenu;
	}

	public void setGnbMenu(List<HashMap<String, String>> gnbMenu) {
		this.gnbMenu = gnbMenu;
	}

	public String getCurrMenuCd() {
		return currMenuCd;
	}

	public void setCurrMenuCd(String currMenuCd) {
		this.currMenuCd = currMenuCd;
	}

	public List<CaseInsensitiveMap> getGNBmenulist() {
		return GNBmenulist;
	}

	public void setGNBmenulist(List<CaseInsensitiveMap> gNBmenulist) {
		GNBmenulist = gNBmenulist;
	}

	public List<CaseInsensitiveMap> getLnbMenuAuthList() {
		return lnbMenuAuthList;
	}

	public void setLnbMenuAuthList(List<CaseInsensitiveMap> lnbMenuAuthList) {
		this.lnbMenuAuthList = lnbMenuAuthList;
	}

	public Map getTaskAuth() {
		return taskAuth;
	}

	public void setTaskAuth(Map taskAuth) {
		this.taskAuth = taskAuth;
	}

	public String getBeforeUrl() {
		return beforeUrl;
	}

	public void setBeforeUrl(String beforeUrl) {
		this.beforeUrl = beforeUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public List<HashMap> getMenuAuthUrlList() {
		return menuAuthUrlList;
	}

	public void setMenuAuthUrlList(List<HashMap> menuAuthUrlList) {
		this.menuAuthUrlList = menuAuthUrlList;
	}
    

}
