package com.lotto.spring.domain.dto;

/**
 * 패턴 정보
 * 
 * @author cremazer
 *
 */
public class PtrnInfoDto extends DefaultDto{
	/** 패턴유형 */
	private int 	ptrn_type;
	/** 순번 */
	private int 	seq;
	/** 시작회차 */
	private int 	start_count;
	/** 패턴개수 */
	private int 	ptrn_cnt;
	/** 패턴정보 */
	private String 	ptrn_info;
	/** 다음정보 */
	private String 	next_info;
	
	public int getPtrn_type() {
		return ptrn_type;
	}
	public void setPtrn_type(int ptrn_type) {
		this.ptrn_type = ptrn_type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getPtrn_cnt() {
		return ptrn_cnt;
	}
	public void setPtrn_cnt(int ptrn_cnt) {
		this.ptrn_cnt = ptrn_cnt;
	}
	public int getStart_count() {
		return start_count;
	}
	public void setStart_count(int start_count) {
		this.start_count = start_count;
	}
	public String getPtrn_info() {
		return ptrn_info;
	}
	public void setPtrn_info(String ptrn_info) {
		this.ptrn_info = ptrn_info;
	}
	public String getNext_info() {
		return next_info;
	}
	public void setNext_info(String next_info) {
		this.next_info = next_info;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("==============================================\n");
		sb.append("패턴유형 = " + this.ptrn_type + "\n");
		sb.append("\n");
		sb.append("순번 = " + this.seq + "\n");
		sb.append("\n");
		sb.append("패턴개수 = " + this.ptrn_cnt + "\n");
		sb.append("\n");
		sb.append("시작회차 = " + this.start_count + "\n");
		sb.append("\n");
		sb.append("패턴정보 = " + this.ptrn_info + "\n");
		sb.append("\n");
		sb.append("다음정보 = " + this.next_info + "\n");
		sb.append("\n");
		sb.append("==============================================\n");
		
		return sb.toString();
	}
	
}
