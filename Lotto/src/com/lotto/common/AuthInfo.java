package com.lotto.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.chello.base.common.resource.ResourceManager;
import com.lotto.spring.domain.dao.SystemSession;
import com.lotto.spring.domain.dao.UserSession;

@SessionAttributes({"SystemInfo"})
public class AuthInfo {
	
	/**
	 * 상단 메뉴 셋팅
	 */
	public static void getMenuSetting(HttpServletRequest request,HttpServletResponse response) {
    	
		HttpSession session = request.getSession();
    	UserSession userInfo = (UserSession) session.getAttribute("UserInfo");
//    	SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
    	
    	String[] arrUrl = userInfo.getMenu().split("\\/"); 
    	List<CaseInsensitiveMap> gnbMenu =  userInfo.getGNBmenulist();
    	
    	CaseInsensitiveMap one = null;
    	for (int i=0; i<gnbMenu.size(); i++) {
    		one = gnbMenu.get(i);
    		
    		String depth_1_cd = String.valueOf(one.get("depth_1_cd"));
    		
			if (arrUrl[0].equals(depth_1_cd)) {
				one.put("current", "Y");
			} else {
				one.put("current", "N");
			}
    		
    	}
	}
	
	/**
	 * system.conf 셋팅
	 */
	public static SystemSession getSystemSetting(HttpServletRequest request,HttpServletResponse response) {
    	
		HttpSession session = request.getSession();
		SystemSession systemInfo = (SystemSession) session.getAttribute("SystemInfo");
		
		SystemSession systemInfo1 = new SystemSession();
		
		
		if (systemInfo == null) {
			
			ResourceManager resManagerForAuth = ResourceManager.getInstance();
			String protocol       = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","PROTOCOL"),"http");
			
			systemInfo1.setDomain_url(protocol+"://"+resManagerForAuth.getValue("conf.system","DOMAIN_NAME"));
			systemInfo1.setM_domain_url(protocol+"://"+resManagerForAuth.getValue("conf.system","M_DOMAIN_NAME"));
			systemInfo1.setApp_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "").trim());
			systemInfo1.setScreen_ip(resManagerForAuth.getValue("conf.system","SCREEN_IP"));
			systemInfo1.setTotalfile_ip(resManagerForAuth.getValue("conf.system","TOTALFILE_IP"));
			systemInfo1.setCss_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","CSS_ROOT"), "").trim());
	    	systemInfo1.setJs_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","JS_ROOT"), "").trim());
	    	systemInfo1.setImg_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","IMG_ROOT"), "").trim());
	    	
	    	//2016.09.08 cremazer
	    	//사이트코드 업무별 세분화
	    	systemInfo1.setHtml_target(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET"), ""));
	    	systemInfo1.setHtml_target_search(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SEARCH"), ""));
	    	systemInfo1.setHtml_target_approval(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_APPROVAL"), ""));
	    	systemInfo1.setHtml_target_interest(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_INTEREST"), ""));
	    	systemInfo1.setHtml_target_stat(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_STAT"), ""));
	    	systemInfo1.setHtml_target_sysinfo(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SYSINFO"), ""));
	    	systemInfo1.setHtml_target_sysmng(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SYSMNG"), ""));
	    	
	    	systemInfo1.setMax_depth(Integer.parseInt(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","MAXDEPTH"), "4")));
	    	systemInfo1.setComp_type(resManagerForAuth.getValue("conf.system","COMP_TYPE"));
	    	systemInfo1.setGroup_id(resManagerForAuth.getValue("conf.system","GROUP_ID"));
	    	systemInfo1.setTeam_id(resManagerForAuth.getValue("conf.system","TEAM_ID"));
	    	systemInfo1.setPart_id(resManagerForAuth.getValue("conf.system","PART_ID"));
	    	systemInfo1.setProc_nm(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SEARCH_PROC_NM"), ""));
	    	systemInfo1.setDatabase(resManagerForAuth.getValue("conf.system", "DATABASE"));
	    	systemInfo1.setDefaultId(resManagerForAuth.getValue("conf.system", "DONGBANG"));
	    	systemInfo1.setDbIp(resManagerForAuth.getValue("conf.system", "DB_IP"));
	    	systemInfo1.setPlayerVer(resManagerForAuth.getValue("conf.system", "VER_PLAYER"));
	    	
	    	//2016.05.17 cremazer
	    	systemInfo1.setAnylinkYn(resManagerForAuth.getValue("conf.system", "ANYLINK_YN"));
	    	systemInfo1.setSsoYn(resManagerForAuth.getValue("conf.system", "SSO_YN"));
	    	systemInfo1.setSafeDbYn(resManagerForAuth.getValue("conf.system", "SAFEDB_YN"));
	    	
	    	if ("hwgeneralins".equals(systemInfo1.getHtml_target())) {
		    	//SSO관련
		    	systemInfo1.setTOA(resManagerForAuth.getValue("conf.system", "TOA"));
		    	systemInfo1.setSSO_DOMAIN(resManagerForAuth.getValue("conf.system", "SSO_DOMAIN"));
		    	systemInfo1.setND_URL1(resManagerForAuth.getValue("conf.system", "ND_URL1"));
		    	systemInfo1.setND_URL2(resManagerForAuth.getValue("conf.system", "ND_URL2"));
	    	}
	    	
	    	return systemInfo1;
        } else {
        	
        	return systemInfo;
        }
    	
	}
	
	/**
	 * system.conf 셋팅
	 */
	public static SystemSession getSystemSetting() {
		SystemSession systemInfo = new SystemSession();
		
		ResourceManager resManagerForAuth = ResourceManager.getInstance();
		String protocol       = WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","PROTOCOL"),"http");
		
		systemInfo.setDomain_url(protocol+"://"+resManagerForAuth.getValue("conf.system","DOMAIN_NAME"));
		systemInfo.setM_domain_url(protocol+"://"+resManagerForAuth.getValue("conf.system","M_DOMAIN_NAME"));
		systemInfo.setApp_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","APP_ROOT"), "").trim());
		systemInfo.setScreen_ip(resManagerForAuth.getValue("conf.system","SCREEN_IP"));
		systemInfo.setTotalfile_ip(resManagerForAuth.getValue("conf.system","TOTALFILE_IP"));
		systemInfo.setCss_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","CSS_ROOT"), "").trim());
    	systemInfo.setJs_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","JS_ROOT"), "").trim());
    	systemInfo.setImg_root(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","IMG_ROOT"), "").trim());
    	
    	//2016.09.08 cremazer
    	//사이트코드 업무별 세분화
    	systemInfo.setHtml_target(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET"), ""));
    	systemInfo.setHtml_target_search(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SEARCH"), ""));
    	systemInfo.setHtml_target_approval(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_APPROVAL"), ""));
    	systemInfo.setHtml_target_interest(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_INTEREST"), ""));
    	systemInfo.setHtml_target_stat(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_STAT"), ""));
    	systemInfo.setHtml_target_sysinfo(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SYSINFO"), ""));
    	systemInfo.setHtml_target_sysmng(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","HTML_TARGET_SYSMNG"), ""));
    	
    	systemInfo.setMax_depth(Integer.parseInt(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","MAXDEPTH"), "4")));
    	systemInfo.setComp_type(resManagerForAuth.getValue("conf.system","COMP_TYPE"));
    	systemInfo.setGroup_id(resManagerForAuth.getValue("conf.system","GROUP_ID"));
    	systemInfo.setTeam_id(resManagerForAuth.getValue("conf.system","TEAM_ID"));
    	systemInfo.setPart_id(resManagerForAuth.getValue("conf.system","PART_ID"));
    	systemInfo.setProc_nm(WebUtil.replaceParam(resManagerForAuth.getValue("conf.system","SEARCH_PROC_NM"), ""));
    	systemInfo.setDatabase(resManagerForAuth.getValue("conf.system", "DATABASE"));
    	systemInfo.setDefaultId(resManagerForAuth.getValue("conf.system", "DONGBANG"));
    	systemInfo.setDbIp(resManagerForAuth.getValue("conf.system", "DB_IP"));
    	systemInfo.setPlayerVer(resManagerForAuth.getValue("conf.system", "VER_PLAYER"));
    	
    	//2016.05.17 cremazer
    	systemInfo.setAnylinkYn(resManagerForAuth.getValue("conf.system", "ANYLINK_YN"));
    	systemInfo.setSsoYn(resManagerForAuth.getValue("conf.system", "SSO_YN"));
    	systemInfo.setSafeDbYn(resManagerForAuth.getValue("conf.system", "SAFEDB_YN"));
    	
    	if ("hwgeneralins".equals(systemInfo.getHtml_target())) {
	    	//SSO관련
	    	systemInfo.setTOA(resManagerForAuth.getValue("conf.system", "TOA"));
	    	systemInfo.setSSO_DOMAIN(resManagerForAuth.getValue("conf.system", "SSO_DOMAIN"));
	    	systemInfo.setND_URL1(resManagerForAuth.getValue("conf.system", "ND_URL1"));
	    	systemInfo.setND_URL2(resManagerForAuth.getValue("conf.system", "ND_URL2"));
    	}
    	return systemInfo;
    	
	}
}
