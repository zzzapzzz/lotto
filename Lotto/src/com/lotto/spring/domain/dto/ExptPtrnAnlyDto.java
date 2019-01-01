package com.lotto.spring.domain.dto;

import java.util.ArrayList;

/**
 * 패턴 정보
 * 
 * @author cremazer
 *
 */
public class ExptPtrnAnlyDto extends DefaultDto{
	/** 예상회차 (EX_COUNT) */
	private int 	ex_count;
	/** 회차합 (COUNT_SUM) */
	private int 	count_sum;
	/** 포함개수 (CONT_CNT) */
	private int 	cont_cnt;
	/** 미포함개수 (NOT_CONT_CNT) */
	private int 	not_cont_cnt;
	/** 같은번호개수 (SAME_NUM_CNT) */
	private int 	same_num_cnt;
	/** 1큰수개수 (UP_1_CNT) */
	private int 	up_1_cnt;
	/** 1작은수개수 (DOWN_1_CNT) */
	private int 	down_1_cnt;
	/** 저고비율 (LOW_HIGH) */
	private String 	low_high;
	/** 홀짝비율 (ODD_EVEN) */
	private String 	odd_even;
	/** 
	 * 총합범위유형 (TOTAL_RANGE_TYPE)
	 * 1: 100 이하
	 * 2: 101 ~ 150
	 * 3: 151 ~ 200
	 * 4: 201 이상 
	 */
	private int total_range_type;
	/** 총합범위 (TOTAL_RANGE) */
	private String 	total_range;
	/** 끝수합범위 (END_SUM_RANGE) */
	private String 	end_sum_range;
	/** 연속수_개수 (C_NUM_CNT) : Consecutively Number Cnt */
	private int 	c_num_cnt;
	
	/** 그룹내미포함 (NOT_CONT_GRP) */
	private String 	not_cont_grp;
	/** 각 자리의 포함개수 */
	private int[] containGroupCnt = {0,0,0,0,0};
	/** 숫자가 없는 자리 수의 개수 */
	private int zeroCnt = 0;
	/** 
	 * 숫자가 없는 자리 범위
	 * 1 : 1~10
	 * 2 : 11~20
	 * 3 : 21~30
	 * 4 : 31~40
	 * 5 : 41~45
	 */
	private ArrayList<Integer> zeroCntRange = new ArrayList<Integer>();
	
	/** 끝자리같은수개수 (END_NUM_SAME_CNT) */
	private int 	end_num_same_cnt;
	/** 소수_개수 (P_NUM_CNT) */
	private int 	p_num_cnt;
	/** 3의배수개수 (MULTI_3_CNT) */
	private int 	multi_3_cnt;
	/** 합성수개수 (COMP_NUM_CNT) */
	private int 	comp_num_cnt;
	/** AC (AC) */
	private int 	ac;
	/** 궁합수개수 (MCNUM_CNT) */
	private int 	mcnum_cnt;
	
	public int getEx_count() {
		return ex_count;
	}
	public void setEx_count(int ex_count) {
		this.ex_count = ex_count;
	}
	public int getCount_sum() {
		return count_sum;
	}
	public void setCount_sum(int count_sum) {
		this.count_sum = count_sum;
	}
	public int getCont_cnt() {
		return cont_cnt;
	}
	public void setCont_cnt(int cont_cnt) {
		this.cont_cnt = cont_cnt;
	}
	public int getNot_cont_cnt() {
		return not_cont_cnt;
	}
	public void setNot_cont_cnt(int not_cont_cnt) {
		this.not_cont_cnt = not_cont_cnt;
	}
	public int getSame_num_cnt() {
		return same_num_cnt;
	}
	public void setSame_num_cnt(int same_num_cnt) {
		this.same_num_cnt = same_num_cnt;
	}
	public int getUp_1_cnt() {
		return up_1_cnt;
	}
	public void setUp_1_cnt(int up_1_cnt) {
		this.up_1_cnt = up_1_cnt;
	}
	public int getDown_1_cnt() {
		return down_1_cnt;
	}
	public void setDown_1_cnt(int down_1_cnt) {
		this.down_1_cnt = down_1_cnt;
	}
	public String getLow_high() {
		return low_high;
	}
	public void setLow_high(String low_high) {
		this.low_high = low_high;
	}
	public String getOdd_even() {
		return odd_even;
	}
	public void setOdd_even(String odd_even) {
		this.odd_even = odd_even;
	}
	public int getTotal_range_type() {
		return total_range_type;
	}
	public void setTotal_range_type(int total_range_type) {
		this.total_range_type = total_range_type;
	}
	public String getTotal_range() {
		return total_range;
	}
	public void setTotal_range(String total_range) {
		this.total_range = total_range;
	}
	public String getEnd_sum_range() {
		return end_sum_range;
	}
	public void setEnd_sum_range(String end_sum_range) {
		this.end_sum_range = end_sum_range;
	}
	public int getC_num_cnt() {
		return c_num_cnt;
	}
	public void setC_num_cnt(int c_num_cnt) {
		this.c_num_cnt = c_num_cnt;
	}
	public String getNot_cont_grp() {
		return not_cont_grp;
	}
	public void setNot_cont_grp(String not_cont_grp) {
		this.not_cont_grp = not_cont_grp;
	}
	public int[] getContainGroupCnt() {
		return containGroupCnt;
	}
	public void setContainGroupCnt(int[] containGroupCnt) {
		this.containGroupCnt = containGroupCnt;
	}
	public int getZeroCnt() {
		return zeroCnt;
	}
	public void setZeroCnt(int zeroCnt) {
		this.zeroCnt = zeroCnt;
	}
	public ArrayList<Integer> getZeroCntRange() {
		return zeroCntRange;
	}
	public void setZeroCntRange(ArrayList<Integer> zeroCntRange) {
		this.zeroCntRange = zeroCntRange;
	}
	public int getEnd_num_same_cnt() {
		return end_num_same_cnt;
	}
	public void setEnd_num_same_cnt(int end_num_same_cnt) {
		this.end_num_same_cnt = end_num_same_cnt;
	}
	public int getP_num_cnt() {
		return p_num_cnt;
	}
	public void setP_num_cnt(int p_num_cnt) {
		this.p_num_cnt = p_num_cnt;
	}
	public int getMulti_3_cnt() {
		return multi_3_cnt;
	}
	public void setMulti_3_cnt(int multi_3_cnt) {
		this.multi_3_cnt = multi_3_cnt;
	}
	public int getComp_num_cnt() {
		return comp_num_cnt;
	}
	public void setComp_num_cnt(int comp_num_cnt) {
		this.comp_num_cnt = comp_num_cnt;
	}
	public int getAc() {
		return ac;
	}
	public void setAc(int ac) {
		this.ac = ac;
	}
	public int getMcnum_cnt() {
		return mcnum_cnt;
	}
	public void setMcnum_cnt(int mcnum_cnt) {
		this.mcnum_cnt = mcnum_cnt;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("==============================================\n");
		sb.append("예상회차 = " + this.ex_count + "\n");
		sb.append("\n");
		sb.append("회차합 = " + this.count_sum + "\n");
		sb.append("포함개수 = " + this.cont_cnt + "\n");
		sb.append("미포함개수 = " + this.not_cont_cnt + "\n");
		sb.append("\n");
		sb.append("같은번호개수 = " + this.same_num_cnt + "\n");
		sb.append("1큰수개수 = " + this.up_1_cnt + "\n");
		sb.append("1작은수개수 = " + this.down_1_cnt + "\n");
		sb.append("\n");
		sb.append("저고비율 = " + this.low_high + "\n");
		sb.append("\n");
		sb.append("홀짝비율 = " + this.odd_even + "\n");
		sb.append("\n");
		sb.append("총합범위 (1: 100 이하, 2: 101 ~ 150, 3: 151 ~ 200, 4: 201 이상)\n");
		sb.append("\t" + this.total_range_type + "\n");
//		sb.append("총합범위 = " + this.total_range + "\n");
//		sb.append("\n");
//		sb.append("끝수합범위 = " + this.end_sum_range + "\n");
		sb.append("\n");
		sb.append("연속수_개수 = " + this.c_num_cnt + "\n");
		sb.append("\n");
		sb.append("숫자가 없는 자리 수의 개수 = " + this.zeroCnt + "\n");
//		sb.append("그룹내미포함 = " + this.not_cont_grp + "\n");
		sb.append("숫자가 없는 자리 범위 (1 : 1~10, 2 : 11~20, 3 : 21~30, 4 : 31~40, 5 : 41~45)\n");
		for (int i = 0; i < zeroCntRange.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(this.zeroCntRange.get(i));
		}
		sb.append("\n");
		sb.append("\n");
		sb.append("끝자리같은수개수 = " + this.end_num_same_cnt + "\n");
		sb.append("\n");
		sb.append("소수_개수 = " + this.p_num_cnt + "\n");
		sb.append("\n");
		sb.append("3의배수개수 = " + this.multi_3_cnt + "\n");
		sb.append("\n");
		sb.append("합성수개수 = " + this.comp_num_cnt + "\n");
		sb.append("\n");
		sb.append("AC = " + this.ac + "\n");
		sb.append("\n");
		sb.append("궁합수개수 = " + this.mcnum_cnt + "\n");
		sb.append("==============================================\n");
		
		return sb.toString();
	}
}
