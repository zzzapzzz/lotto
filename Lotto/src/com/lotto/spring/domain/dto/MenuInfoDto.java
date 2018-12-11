package com.lotto.spring.domain.dto;

/**
 * 메뉴권한 정보
 * 
 * @author cremazer
 * @since 2018.12.02
 */
public class MenuInfoDto extends DefaultDto{
	int menu_id;
	int p_menu_id;
	String menu_nm;
	String menu_url;
	int menu_order;
	int cnt;
	String checked;
	String use_yn;
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public int getP_menu_id() {
		return p_menu_id;
	}
	public void setP_menu_id(int p_menu_id) {
		this.p_menu_id = p_menu_id;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public int getMenu_order() {
		return menu_order;
	}
	public void setMenu_order(int menu_order) {
		this.menu_order = menu_order;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	
	
}
