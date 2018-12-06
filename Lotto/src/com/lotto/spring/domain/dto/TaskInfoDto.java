package com.lotto.spring.domain.dto;

/**
 * 업무권한정보
 * @author cremazer
 * @since 2018.12.02
 */
public class TaskInfoDto extends DefaultDto{
	String task_1_cd;
	String task_1_nm;
	String task_2_cd;
	String task_2_nm;
	int cnt;
	int sort;
	String checked;
	String use_yn;
	
	public String getTask_1_cd() {
		return task_1_cd;
	}
	public void setTask_1_cd(String task_1_cd) {
		this.task_1_cd = task_1_cd;
	}
	public String getTask_1_nm() {
		return task_1_nm;
	}
	public void setTask_1_nm(String task_1_nm) {
		this.task_1_nm = task_1_nm;
	}
	public String getTask_2_cd() {
		return task_2_cd;
	}
	public void setTask_2_cd(String task_2_cd) {
		this.task_2_cd = task_2_cd;
	}
	public String getTask_2_nm() {
		return task_2_nm;
	}
	public void setTask_2_nm(String task_2_nm) {
		this.task_2_nm = task_2_nm;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
