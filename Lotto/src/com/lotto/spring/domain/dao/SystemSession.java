package com.lotto.spring.domain.dao;

public class SystemSession {
	
    private String domain_url = "";
    
    private String m_domain_url = "";
    
    private String app_root = "";
    
    private String screen_ip = "";
    
    private String totalfile_ip = "";
    
    private String css_root= "";
    
    private String js_root = "";
    
    private String img_root = "";
    

	/**
	 * <b>사이트코드 업무별 세분화 </b><br />
     * 2016.09.08 cremazer <br />
     * 2016.10.10 cremazer 녹취승인, 콜뱅크 분리 <br />
     *
     * @see <br />
     * html_target : 대표 Site Type <br />
     * html_target_search : 녹취조회 <br />
     * html_target_approval : 녹취승인 <br />
     * html_target_interest : 콜뱅크 <br />
     * html_target_stat : 녹취통계 <br />
     * html_target_sysinfo : 시스템현황 <br />
     * html_target_sysmng : 시스템관리 <br />
     * 
	 */
    private String html_target = "";
    private String html_target_search = "";
    private String html_target_approval = "";
    private String html_target_interest = "";
    private String html_target_stat = "";
    private String html_target_sysinfo = "";
    private String html_target_sysmng = "";
    
    private int max_depth = 0;
    
    private String comp_type = "";
    
    private String group_id = "";
    
    private String team_id = "";
    
    private String part_id = "";
    
    private String proc_nm	= "";
    
    private String database	= "";
    
    private String defaultId = "";
    
    private String dbIp = "";
    
    private String playerVer = "";
    
    /** 인증 타입 (ID/PW 방식 : 1) */
    private String TOA = "";
    /** 도메인 */
    private String SSO_DOMAIN = "";
    /** Nexess API가 사용할 Nexess Daemon의 주소1 */
    private String ND_URL1 = "";
    /** Nexess API가 사용할 Nexess Daemon의 주소2 */
    private String ND_URL2 = "";
    
    //2016.05.17 cremazer 
    /** 기간계연동여부 */
    private String anylinkYn = "";
    /** SSO연동여부 */
    private String ssoYn = "";
    /** SAFE DB 연동여부 */
    private String safeDbYn = "";

    public String getDomain_url() {
		return domain_url;
	}

	public void setDomain_url(String domain_url) {
		this.domain_url = domain_url;
	}

	public String getM_domain_url() {
		return m_domain_url;
	}

	public void setM_domain_url(String m_domain_url) {
		this.m_domain_url = m_domain_url;
	}

	public String getApp_root() {
		return app_root;
	}

	public void setApp_root(String app_root) {
		this.app_root = app_root;
	}

	public String getScreen_ip() {
		return screen_ip;
	}

	public void setScreen_ip(String screen_ip) {
		this.screen_ip = screen_ip;
	}

	public String getTotalfile_ip() {
		return totalfile_ip;
	}

	public void setTotalfile_ip(String totalfile_ip) {
		this.totalfile_ip = totalfile_ip;
	}

	public String getCss_root() {
		return css_root;
	}

	public void setCss_root(String css_root) {
		this.css_root = css_root;
	}

	public String getJs_root() {
		return js_root;
	}

	public void setJs_root(String js_root) {
		this.js_root = js_root;
	}

	public String getImg_root() {
		return img_root;
	}

	public void setImg_root(String img_root) {
		this.img_root = img_root;
	}

	public String getHtml_target() {
		return html_target;
	}

	public void setHtml_target(String html_target) {
		this.html_target = html_target;
	}

	public int getMax_depth() {
		return max_depth;
	}

	public void setMax_depth(int max_depth) {
		this.max_depth = max_depth;
	}

	public String getComp_type() {
		return comp_type;
	}

	public void setComp_type(String comp_type) {
		this.comp_type = comp_type;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getPart_id() {
		return part_id;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}

	public String getProc_nm() {
		return proc_nm;
	}

	public void setProc_nm(String proc_nm) {
		this.proc_nm = proc_nm;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDefaultId() {
		return defaultId;
	}

	public void setDefaultId(String defaultId) {
		this.defaultId = defaultId;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public String getTOA() {
		return TOA;
	}

	public void setTOA(String tOA) {
		TOA = tOA;
	}

	public String getSSO_DOMAIN() {
		return SSO_DOMAIN;
	}

	public void setSSO_DOMAIN(String sSO_DOMAIN) {
		SSO_DOMAIN = sSO_DOMAIN;
	}

	public String getND_URL1() {
		return ND_URL1;
	}

	public void setND_URL1(String nD_URL1) {
		ND_URL1 = nD_URL1;
	}

	public String getND_URL2() {
		return ND_URL2;
	}

	public void setND_URL2(String nD_URL2) {
		ND_URL2 = nD_URL2;
	}

	public String getPlayerVer() {
		return playerVer;
	}

	public void setPlayerVer(String playerVer) {
		this.playerVer = playerVer;
	}

	public String getSsoYn() {
		return ssoYn;
	}

	public void setSsoYn(String ssoYn) {
		this.ssoYn = ssoYn;
	}

	public String getSafeDbYn() {
		return safeDbYn;
	}

	public void setSafeDbYn(String safeDbYn) {
		this.safeDbYn = safeDbYn;
	}

	public String getAnylinkYn() {
		return anylinkYn;
	}

	public void setAnylinkYn(String anylinkYn) {
		this.anylinkYn = anylinkYn;
	}

	public String getHtml_target_search() {
		return html_target_search;
	}

	public void setHtml_target_search(String html_target_search) {
		this.html_target_search = html_target_search;
	}

	public String getHtml_target_approval() {
		return html_target_approval;
	}

	public void setHtml_target_approval(String html_target_approval) {
		this.html_target_approval = html_target_approval;
	}

	public String getHtml_target_interest() {
		return html_target_interest;
	}

	public void setHtml_target_interest(String html_target_interest) {
		this.html_target_interest = html_target_interest;
	}

	public String getHtml_target_stat() {
		return html_target_stat;
	}

	public void setHtml_target_stat(String html_target_stat) {
		this.html_target_stat = html_target_stat;
	}

	public String getHtml_target_sysinfo() {
		return html_target_sysinfo;
	}

	public void setHtml_target_sysinfo(String html_target_sysinfo) {
		this.html_target_sysinfo = html_target_sysinfo;
	}

	public String getHtml_target_sysmng() {
		return html_target_sysmng;
	}

	public void setHtml_target_sysmng(String html_target_sysmng) {
		this.html_target_sysmng = html_target_sysmng;
	}

}
