package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.spring.domain.dto.MenuInfoDto;
import com.lotto.spring.domain.dto.TaskInfoDto;
import com.lotto.spring.domain.dto.UserInfoDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("sysmngService")
public class SysmngService extends DefaultService {

//	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 사용자 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<CaseInsensitiveMap> getUserList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getUserList", map);
	}
	
	public ArrayList<UserInfoDto> getUserList2(Map map) {
		return (ArrayList<UserInfoDto>) baseDao.getList("sysmngMapper.getUserList2", map);
	}
	
	/**
	 * 사용자 목록 개수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getUserListCnt(Map map) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getUserListCnt", map);
	}
	
	/**
	 * 사번 중복체크
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean dupCheckUserId(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.getSingleRow("sysmngMapper.dupCheckUserId", map);
		if(i > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 사용자정보 등록
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap createUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.createUserInfo", dto);
	}
	
	/**
	 * 사용자정보 수정
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap modifyUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.modifyUserInfo", dto);
	}
	
	/**
	 * 사용자정보 삭제
	 * 
	 * @param dto
	 * @return
	 */
	public CaseInsensitiveMap deleteUserInfo(UserInfoDto dto) {
		return (CaseInsensitiveMap) baseDao.getSingleRow("sysmngMapper.deleteUserInfo", dto);
	}
	
	/**
	 * 사용자목록 등록
	 * 2018.01.28
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertUserInfoList(Map map) {
		boolean flag = false;		
		int i = (Integer) baseDao.insert("sysmngMapper.insertUserInfoList", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	
	/**
	 * 권한코드 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<CaseInsensitiveMap> getAuthCodeList(Map map) {
		return (ArrayList<CaseInsensitiveMap>) baseDao.getList("sysmngMapper.getAuthCodeList", map);
	}
	
	/**
	 * 권한코드 목록 개수 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthCodeListCnt(Map map) {
		return (Integer) baseDao.getSingleRow("sysmngMapper.getAuthCodeListCnt", map);
	}
	
	/**
	 *업무권한정보 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<TaskInfoDto> getAuthTaskInfoList(Map map) {
		return (ArrayList<TaskInfoDto>) baseDao.getList("sysmngMapper.getAuthTaskInfoList", map);
	}
	
	/**
	 * 권한코드 중복체크
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean dupCheckAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.getSingleRow("sysmngMapper.dupCheckAuthCode", map);
		if(i > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 권한코드 등록
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean createAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.createAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 권한코드 수정
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean modifyAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.update("sysmngMapper.modifyAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 권한코드 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteAuthCode(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteAuthCode", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 기존 업무권한 매핑정보 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteTaskAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteTaskAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 업무권한 매핑정보 저장
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveTaskAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.saveTaskAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * Tree Data 설정 (업무권한)
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx 
	 * @param depth 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray getAuthTaskInfoTree(List list, int startIdx, int endIdx, int depth) {
		JSONArray jsonRtnArr = new JSONArray();
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			TaskInfoDto one = (TaskInfoDto) list.get(i);
			
//			String task_1_cd = (String) one.get("task_1_cd");
//			String task_2_cd = (String) one.get("task_2_cd");
//			String task_1_nm = (String) one.get("task_1_nm");
//			String task_2_nm = (String) one.get("task_2_nm");
//			String checked = (String) one.get("checked");
//			int cnt = (int) one.get("cnt");
			
			String task_1_cd = (String) one.getTask_1_cd();
			String task_2_cd = (String) one.getTask_2_cd();
			String task_1_nm = (String) one.getTask_1_nm();
			String task_2_nm = (String) one.getTask_2_nm();
			String checked = (String) one.getChecked();
			int cnt = one.getCnt();
			
//			System.out.println("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
			//depth JSON 메뉴 생성
			JSONObject depthJson = new JSONObject();
			
			if (1 == depth) {
				depthJson.put("id", task_1_cd);
				depthJson.put("text", task_1_nm);
				depthJson.put("value", task_1_cd);
				
				if (cnt > 1) {
					depthJson.put("hasChildren", true);
					depthJson.put("ChildNodes", this.getAuthTaskInfoTree(list, i, i + cnt, 2));
					int checkCnt = this.getAuthTaskInfoCheckCnt(list, i, i + cnt);
					depthJson.put("checkstate", ((checkCnt == cnt) ? 1 : 0));
					i += (cnt - 1);
				} else {
					depthJson.put("hasChildren", false);
					depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
				}
			} else {
				depthJson.put("id", task_1_cd+"_"+task_2_cd);
				depthJson.put("text", task_2_nm);
				depthJson.put("value", task_1_cd+"_"+task_2_cd);
				
				depthJson.put("hasChildren", false);
				depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
			}
			
			depthJson.put("showcheck", true);
			depthJson.put("complete", true);
			depthJson.put("isexpand", true);
			
			jsonRtnArr.add(depthJson);
			
//			System.out.println("i="+i);
		}
		
		return jsonRtnArr;
	}

	/**
	 * 업무권한 체크 건수 가져오기
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthTaskInfoCheckCnt(List list, int startIdx, int endIdx) {
		int checkCnt = 0;
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			TaskInfoDto one = (TaskInfoDto) list.get(i);
			
			String checked = (String) one.getChecked();
			if ("Y".equals(checked)) {
				checkCnt++;
			}
		}
		
		return checkCnt;
	}
	
	/**
	 * 메뉴권한정보 목록 조회
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<MenuInfoDto> getAuthMenuInfoList(Map map) {
		return (ArrayList<MenuInfoDto>) baseDao.getList("sysmngMapper.getAuthMenuInfoList", map);
	}
	
	/**
	 * Tree Data 설정 (메뉴권한)
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx 
	 * @param depth 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray getAuthMenuInfoTree(List list, int startIdx, int endIdx, int depth) {
		JSONArray jsonRtnArr = new JSONArray();
		
		for (int i = startIdx; i < endIdx; i++) {
//			CaseInsensitiveMap one = (CaseInsensitiveMap) list.get(i);
			MenuInfoDto one = (MenuInfoDto) list.get(i);
			
			System.out.println(i + " data = " + one.toString());
			
			//java.lang.ClassCastException: java.lang.Short cannot be cast to java.lang.Integer			
//			int menu_id = Integer.parseInt(String.valueOf(one.get("menu_id")));
//			int p_menu_id = Integer.parseInt(String.valueOf(one.get("p_menu_id")));
//			String menu_nm = (String) one.get("menu_nm");
//			String checked = (String) one.get("checked");
//			int cnt = (int) one.get("cnt");
			
			int menu_id = one.getMenu_id();
			int p_menu_id = one.getP_menu_id();
			String menu_nm = one.getMenu_nm();
			String checked = one.getChecked();
			int cnt = one.getCnt();
			
//			System.out.println("[" + depth + "] " + task_1_cd + " / " + task_2_cd + " / " + task_1_nm + " / " + task_2_nm);
			
			//depth JSON 메뉴 생성
			JSONObject depthJson = new JSONObject();
			
			if (1 == depth) {
				depthJson.put("id", String.valueOf(menu_id));
				depthJson.put("text", menu_nm);
				depthJson.put("value", String.valueOf(menu_id));
				
				if (0 == p_menu_id) {
					depthJson.put("hasChildren", true);
					depthJson.put("ChildNodes", this.getAuthMenuInfoTree(list, (i+1), (i+1) + cnt, 2));
					int checkCnt = this.getAuthMenuInfoCheckCnt(list, (i+1), (i+1) + cnt);
					depthJson.put("checkstate", ((checkCnt == cnt) ? 1 : 0));
					i += cnt;
				} else {
					depthJson.put("hasChildren", false);
					depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
				}
			} else {
				depthJson.put("id", String.valueOf(menu_id));
				depthJson.put("text", menu_nm);
				depthJson.put("value", String.valueOf(menu_id));
				
				depthJson.put("hasChildren", false);
				depthJson.put("checkstate", (("Y".equals(checked)) ? 1 : 0));
			}
			
			depthJson.put("showcheck", true);
			depthJson.put("complete", true);
			depthJson.put("isexpand", true);
			
			jsonRtnArr.add(depthJson);
			
//			System.out.println("i="+i);
		}
		
		return jsonRtnArr;
	}
	
	/**
	 * 메뉴권한 체크 건수 가져오기
	 * 
	 * @param list
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getAuthMenuInfoCheckCnt(List list, int startIdx, int endIdx) {
		int checkCnt = 0;
		
		for (int i = startIdx; i < endIdx; i++) {
			MenuInfoDto one = (MenuInfoDto) list.get(i);
			
			String checked = one.getChecked();
			if ("Y".equals(checked)) {
				checkCnt++;
			}
		}
		
		return checkCnt;
	}

	/**
	 * 기존 메뉴권한 매핑정보 삭제
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteMenuAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.delete("sysmngMapper.deleteMenuAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}
	
	/**
	 * 메뉴권한 매핑정보 저장
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveMenuAuthInfo(Map map) {
		boolean flag = false;
		int i = (Integer) baseDao.insert("sysmngMapper.saveMenuAuthInfo", map);
		//2018.04.25 리턴값 버그로 true 처리
//		if(i > 0) {
			flag = true;
//		}
		return flag;
	}

}
