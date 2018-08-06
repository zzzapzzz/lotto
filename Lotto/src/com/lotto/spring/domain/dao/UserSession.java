package com.lotto.spring.domain.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

public class UserSession {
	
    private String usr_id;
    private String usr_nm;
    private String usr_thwd;
    private String store_type;
    private String store_desc;
    private String cost_centre_code;
    private String ship_to_code;
    private String login_tm;
    private String login_ip;
    private int login_fail_cnt;
    private String use_yn;
    private String thwd_q;
    private String thwd_a;
    private String email;
    private String email_chk_yn;
    private String email_chk_tm;
    private String auth_task;
    private String auth_menu;
    private String cr_dt;
    private String up_dt;
    
	private boolean isAdmin = false; 
    
    private List<HashMap<String, String>> gnbMenu;
    
    private String currMenuCd = "";

    private List<CaseInsensitiveMap> GNBmenulist;
    
    private List<CaseInsensitiveMap> lnbMenuAuthList;
    
    private Map taskAuth;	//(구)DepthC
    
    private String beforeUrl = "";
    
    private String nextUrl = "";
    
    private String menu = "";
    
    private List<HashMap> menuAuthUrlList;

	public String getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}

	public String getUsr_nm() {
		return usr_nm;
	}

	public void setUsr_nm(String usr_nm) {
		this.usr_nm = usr_nm;
	}

	public String getUsr_thwd() {
		return usr_thwd;
	}

	public void setUsr_thwd(String usr_thwd) {
		this.usr_thwd = usr_thwd;
	}

	public String getStore_type() {
		return store_type;
	}

	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}

	public String getStore_desc() {
		return store_desc;
	}

	public void setStore_desc(String store_desc) {
		this.store_desc = store_desc;
	}

	public String getCost_centre_code() {
		return cost_centre_code;
	}

	public void setCost_centre_code(String cost_centre_code) {
		this.cost_centre_code = cost_centre_code;
	}

	public String getShip_to_code() {
		return ship_to_code;
	}

	public void setShip_to_code(String ship_to_code) {
		this.ship_to_code = ship_to_code;
	}

	public String getLogin_tm() {
		return login_tm;
	}

	public void setLogin_tm(String login_tm) {
		this.login_tm = login_tm;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public int getLogin_fail_cnt() {
		return login_fail_cnt;
	}

	public void setLogin_fail_cnt(int login_fail_cnt) {
		this.login_fail_cnt = login_fail_cnt;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_chk_yn() {
		return email_chk_yn;
	}

	public void setEmail_chk_yn(String email_chk_yn) {
		this.email_chk_yn = email_chk_yn;
	}

	public String getEmail_chk_tm() {
		return email_chk_tm;
	}

	public void setEmail_chk_tm(String email_chk_tm) {
		this.email_chk_tm = email_chk_tm;
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
