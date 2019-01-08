package com.lotto.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;
import com.lotto.common.LottoUtil;
import com.lotto.spring.domain.dto.ExptPtrnAnlyDto;
import com.lotto.spring.domain.dto.PtrnInfoDto;
import com.lotto.spring.domain.dto.WinDataAnlyDto;

@Service("patternAnalysisService")
public class PatternAnalysisService extends DefaultService {
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired(required = true)
    private SysmngService sysmngService;
	
	@Autowired(required = true)
	private LottoDataService lottoDataService;
	
	/**
	 * @description <div id=description><b>예측패턴 조회</b></div >
	 *              <div id=detail>예측패턴 정보를 조회한다.</div >
	 * @param winDataList 전체리스트 (오름차순)
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ExptPtrnAnlyDto getExpectPattern(List<WinDataAnlyDto> winDataList) {
		// 예상패턴분석정보
		ExptPtrnAnlyDto exptPtrnAnlyDto = new ExptPtrnAnlyDto();
		// 패턴 일괄 저장 목록
		List<PtrnInfoDto> ptrnInfoList = new ArrayList<PtrnInfoDto>();
		
		// 예상회차 설정
		int ex_count = winDataList.get(winDataList.size()-1).getWin_count() + 1;
		exptPtrnAnlyDto.setEx_count(ex_count);
		
		//1. 전회차 포함개수 설정
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.CONTAIN_CNT_BY_COUNT, ptrnInfoList);
		
		/*
		 * 2. 전회차 추출번호 패턴
		 * - 같은번호 출현 개수 (e : equal)
		 * - 전회차 당첨번호의 1보다 큰 숫자 개수 (n : next)
		 * - 
		 * */
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.PREV_COUNT, ptrnInfoList);
		
		//3. 저고비율
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.LOW_HIGH_RATIO, ptrnInfoList);
		
		//4. 홀짝비율
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.ODD_EVEN_RATIO, ptrnInfoList);
		
		//5. 총합의 범위
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.TOTAL_RANGE, ptrnInfoList);
		
		//6. 연속수의 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.CONSECUTIVELY_NUMBERS, ptrnInfoList);
		
		//7. 끝수합의 범위 (없음)
		
		//8. 그룹 내 포함개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.CONTAIN_GROUP_CNT, ptrnInfoList);
		
		//9. 끝자리가 같은 수의 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.END_NUMBER_CNT, ptrnInfoList);
		
		//10. 소수 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.SOTSU_CNT, ptrnInfoList);
		
		//11. 3의 배수 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.NUMBER_OF_3_CNT, ptrnInfoList);
		
		//12. 합성수의 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.NUMBER_OF_NOT_3_CNT, ptrnInfoList);
		
		//13. AC
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.AC, ptrnInfoList);
		
		//14. 궁합수 개수
		this.setPatternInfo(exptPtrnAnlyDto, winDataList, LottoUtil.MC_MATCH_CNT, ptrnInfoList);
		
		
		log.info("exptPtrnAnlyDto data : " + exptPtrnAnlyDto.toString());
		log.info("패턴목록 (" + ptrnInfoList.size() + "): \n");		
		for (int i = 0; i < ptrnInfoList.size(); i++) {
			PtrnInfoDto pi = ptrnInfoList.get(i);
			log.info(pi.toString());
		}
		
		
		// 기존 예상패턴분석 정보 삭제
		baseDao.delete("ptrnAnlyMapper.deleteExptPtrnAnlyInfo", exptPtrnAnlyDto);
		
		// 예상패턴분석 정보 등록
		baseDao.insert("ptrnAnlyMapper.insertExptPtrnAnlyInfo", exptPtrnAnlyDto);
		
		// 기존 패턴목록 삭제
		baseDao.delete("ptrnAnlyMapper.deletePtrnInfo");
		
		// 패턴목록 일괄등록
		Map map = new HashMap();
		map.put("list", ptrnInfoList);
		baseDao.insert("ptrnAnlyMapper.insertPtrnInfoList", map);
		
		
		return exptPtrnAnlyDto;
	}

	/**
	 * 패턴정보 설정
	 * 
	 * @param exptPtrnAnlyDto 저장할 예상패턴분석 정보
	 * @param winDataList 전체데이터
	 * @param patternType 패턴유형
	 * @param ptrnInfoList 저장할 패턴정보목록
	 */
	public void setPatternInfo(ExptPtrnAnlyDto exptPtrnAnlyDto, List<WinDataAnlyDto> winDataList, int patternType, List<PtrnInfoDto> ptrnInfoList) {
		
		boolean ppDebug = false;
//		boolean ppDebug = true;
		
		//과거 패턴 회차 리스트
		ArrayList<ArrayList<WinDataAnlyDto>> allPatternCountList = new ArrayList<ArrayList<WinDataAnlyDto>>();
		int countListIndex = 0;
		
		/*
		 * 최근회차부터 1회차전과 비교하여 패턴을 저장한다.
		 */
		for (int i = winDataList.size() - 1 ; i > 0; i--) {
			WinDataAnlyDto sourceData = winDataList.get(i);	//앞회차
			WinDataAnlyDto targetData = winDataList.get(i-1);	//뒤회차

			ExptPtrnAnlyDto sourcePattern = new ExptPtrnAnlyDto();
			
			// 패턴분석정보 설정
			this.setExptPtrnAnly(patternType, sourcePattern, winDataList, sourceData, targetData);
			
			if (ppDebug) {
				if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
					log.info("========= 회차합 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : 회차합 - " + sourcePattern.getCount_sum() + "/포함개수 - "
							+ sourcePattern.getCont_cnt() + "/미포함개수 - " + sourcePattern.getNot_cont_cnt());
				} else if (patternType == LottoUtil.PREV_COUNT) {
					log.info("========= 전회차 추출번호 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getSame_num_cnt() + "/"
							+ sourcePattern.getUp_1_cnt() + "/" + sourcePattern.getDown_1_cnt());
				} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
					log.info("========= 저고비율 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getLow_high());
				} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
					log.info("========= 홀짝비율 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getOdd_even());
				} else if (patternType == LottoUtil.TOTAL_RANGE) {
					log.info("========= 총합 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getTotal_range_type() + "범위");
				} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
					log.info("========= 연속수의 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getC_num_cnt());
				} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
					log.info("========= 그룹 내 미포함 패턴 확인 =========");
					ArrayList<Integer> containGroupCnt = sourcePattern.getZeroCntRange();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < containGroupCnt.size(); j++) {
						sb.append( (j==0?"":"-") + containGroupCnt.get(j));
					}
					log.info(sourceData.getWin_count() + " : " + sb.toString());
				} else if (patternType == LottoUtil.END_NUMBER_CNT) {
					log.info("========= 끝자리가 같은 수의 최대 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getEnd_num_same_cnt());
				} else if (patternType == LottoUtil.SOTSU_CNT) {
					log.info("========= 소수 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getP_num_cnt());
				} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
					log.info("========= 3의 배수 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getMulti_3_cnt());
				} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
					log.info("========= 합성수 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getComp_num_cnt());
				} else if (patternType == LottoUtil.AC) {
					log.info("========= AC 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getAc());
				} else if (patternType == LottoUtil.MC_MATCH_CNT) {
					log.info("========= 궁합수 개수 패턴 확인 =========");
					log.info(sourceData.getWin_count() + " : " + sourcePattern.getMcnum_cnt());
				}
			}
			
			// 패턴이 같은 회차 목록을 구한다.
			ArrayList<WinDataAnlyDto> countList = new ArrayList<WinDataAnlyDto>();
			if (allPatternCountList.size() == 0) {
				// 과거 패턴 회차 리스트가 없으면 전체 리스트를 대상으로 비교하여 목록을 구한다.
//				log.info("과거 패턴 회차 리스트가 없으면 전체 리스트를 대상으로 비교하여 목록을 구한다.");
				this.getPatternList(sourcePattern, winDataList, countList, i - 1, patternType);
			} else {
				// 과거 패턴 회차 리스트가 있으면 회차 리스트를 대상으로 비교하여 목록을 구한다.
//				log.info("과거 패턴 회차 리스트가 있으면 회차 리스트를 대상으로 비교하여 목록을 구한다.");
				this.getPatternList(sourcePattern, winDataList, allPatternCountList.get(countListIndex), countList,
						patternType);
			}

			// 패턴이 같은 회차 목록이 있으면 과거 패턴 회차 리스트에 추가하고 없으면 패턴비교를 종료한다.
			if (countList.size() > 0) {
				allPatternCountList.add(countList);
				countListIndex = allPatternCountList.size() - 1;
			} else {
				break;
			}
		} // end for 패턴을 비교하여 저장
		
		/*
		 * 과거 패턴 회차 리스트 중에서 가장 마지막 패턴의 회차수를 확인하여 패턴 개수를 더해 첫 번째 회차를 설정하고 설정한 회차의 다음회차의
		 * 패턴을 예측패턴으로 설정한다.
		 */
		if (allPatternCountList == null || allPatternCountList.size() == 0
				|| allPatternCountList.get(countListIndex).size() == 0) {
			log.info("전회차 추출번호 패턴 설정 중 일치하는 패턴을 발견하지 못했습니다.");

			// 2016.11.30 LottoUtil.CONTAIN_CNT_BY_COUNT 에 대한 일치패턴 확인 못함.
			if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
				exptPtrnAnlyDto.setCount_sum(10);
				exptPtrnAnlyDto.setCont_cnt(5);
				exptPtrnAnlyDto.setNot_cont_cnt(1);
			}

		} else {
			try {
				// 가장 마지막 패턴의 회차 목록 중 첫번째 패턴의 첫번 째 회차를 설정한다.
				ArrayList<WinDataAnlyDto> countList = allPatternCountList.get(countListIndex);
				// 첫 번째 회차의 다음회차
				WinDataAnlyDto sourceData = winDataList.get(countList.get(0).getWin_count() - 1 + countListIndex + 1);
				// 첫 번째 회차
				WinDataAnlyDto targetData = winDataList.get(countList.get(0).getWin_count() - 1 + countListIndex);
				
				if (ppDebug) {
					log.info("========= 예측 패턴 확인 =========");
					
					ExptPtrnAnlyDto targetPattern = new ExptPtrnAnlyDto();
					
					log.info("첫 번째 회차 : " + targetData.getWin_count());
					if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
						this.getContainCntByCountPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getCount_sum() + "/"
								+ targetPattern.getCont_cnt() + "/" + targetPattern.getNot_cont_cnt());
					} else if (patternType == LottoUtil.PREV_COUNT) {
						this.getPrevCountPattern(targetPattern, targetData, winDataList.get(targetData.getWin_count() - 2));
						log.info(targetData.getWin_count() + " : " + targetPattern.getSame_num_cnt() + "/"
								+ targetPattern.getUp_1_cnt() + "/" + targetPattern.getDown_1_cnt());
					} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
						this.getLowHighPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getLow_high());
					} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
						this.getOddEvenPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getOdd_even());
					} else if (patternType == LottoUtil.TOTAL_RANGE) {
						this.getTotalRangePattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getTotal_range_type());
					} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
						this.getConsecutivelyNumbersPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getC_num_cnt());
					} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
						this.getContainGroupCntPattern(targetPattern, targetData);
						ArrayList<Integer> containGroupCnt = targetPattern.getZeroCntRange();
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < containGroupCnt.size(); j++) {
							sb.append( (j==0?"":"-") + containGroupCnt.get(j));
						}
						log.info(sourceData.getWin_count() + " : " + sb.toString());
					} else if (patternType == LottoUtil.END_NUMBER_CNT) {
						this.getEndNumberCntPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getEnd_num_same_cnt());
					} else if (patternType == LottoUtil.SOTSU_CNT) {
						this.getSotsuCntPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getP_num_cnt());
					} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
						this.getNumberOf3CntPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getMulti_3_cnt());
					} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
						this.getNumberOfNot3CntPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getComp_num_cnt());
					} else if (patternType == LottoUtil.AC) {
						this.getAcPattern(targetPattern, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getAc());
					} else if (patternType == LottoUtil.MC_MATCH_CNT) {
						this.getMcMatchCntPattern(targetPattern, winDataList, targetData);
						log.info(targetData.getWin_count() + " : " + targetPattern.getMcnum_cnt());
					}
					
					log.info("첫 번째 회차의 다음회차 : " + sourceData.getWin_count());
					if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
						this.getContainCntByCountPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getCount_sum() + "/"
								+ targetPattern.getCont_cnt() + "/" + targetPattern.getNot_cont_cnt());
					} else if (patternType == LottoUtil.PREV_COUNT) {
						this.getPrevCountPattern(targetPattern, sourceData, targetData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getSame_num_cnt() + "/"
								+ targetPattern.getUp_1_cnt() + "/" + targetPattern.getDown_1_cnt());
					} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
						this.getLowHighPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getLow_high());
					} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
						this.getOddEvenPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getOdd_even());
					} else if (patternType == LottoUtil.TOTAL_RANGE) {
						this.getTotalRangePattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getTotal_range_type());
					} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
						this.getConsecutivelyNumbersPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getC_num_cnt());
					} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
						this.getContainGroupCntPattern(targetPattern, sourceData);
						ArrayList<Integer> containGroupCnt = targetPattern.getZeroCntRange();
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < containGroupCnt.size(); j++) {
							sb.append( (j==0?"":"-") + containGroupCnt.get(j));
						}
						log.info(sourceData.getWin_count() + " : " + sb.toString());
					} else if (patternType == LottoUtil.END_NUMBER_CNT) {
						this.getEndNumberCntPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getEnd_num_same_cnt());
					} else if (patternType == LottoUtil.SOTSU_CNT) {
						this.getSotsuCntPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getP_num_cnt());
					} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
						this.getNumberOf3CntPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getMulti_3_cnt());
					} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
						this.getNumberOfNot3CntPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getComp_num_cnt());
					} else if (patternType == LottoUtil.AC) {
						this.getAcPattern(targetPattern, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getAc());
					} else if (patternType == LottoUtil.MC_MATCH_CNT) {
						this.getMcMatchCntPattern(targetPattern, winDataList, sourceData);
						log.info(sourceData.getWin_count() + " : " + targetPattern.getMcnum_cnt());
					}
					log.info("");
				} // end if ppDebug 예측 패턴 확인
				
				
				// 예상패턴정보 설정
				if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
					this.getContainCntByCountPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.PREV_COUNT) {
					this.getPrevCountPattern(exptPtrnAnlyDto, sourceData, targetData);
				} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
					this.getLowHighPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
					this.getOddEvenPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.TOTAL_RANGE) {
					this.getTotalRangePattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
					this.getConsecutivelyNumbersPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
					this.getContainGroupCntPattern(exptPtrnAnlyDto, sourceData);
					ArrayList<Integer> containGroupCnt = exptPtrnAnlyDto.getZeroCntRange();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < containGroupCnt.size(); j++) {
						sb.append( (j==0?"":"-") + containGroupCnt.get(j));
					}
					String not_cont_grp = sb.toString();
					exptPtrnAnlyDto.setNot_cont_grp(not_cont_grp);
				} else if (patternType == LottoUtil.END_NUMBER_CNT) {
					this.getEndNumberCntPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.SOTSU_CNT) {
					this.getSotsuCntPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
					this.getNumberOf3CntPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
					this.getNumberOfNot3CntPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.AC) {
					this.getAcPattern(exptPtrnAnlyDto, sourceData);
				} else if (patternType == LottoUtil.MC_MATCH_CNT) {
					this.getMcMatchCntPattern(exptPtrnAnlyDto, winDataList, sourceData);
				}
				
				// 패턴정보 목록 등록
				for (int j = 0; j < countList.size(); j++) {
					PtrnInfoDto ptrnInfoDto = new PtrnInfoDto();
					ptrnInfoDto.setPtrn_type(patternType);
					ptrnInfoDto.setSeq(allPatternCountList.size());
					ptrnInfoDto.setStart_count(countList.get(j).getWin_count());
					ptrnInfoDto.setPtrn_cnt(countList.size());

					// 현재 패턴정보
					int currentIndex = countList.get(j).getWin_count() - 1;
					WinDataAnlyDto currentSourceData = winDataList.get(currentIndex);	//앞회차
					WinDataAnlyDto currentTargetData = winDataList.get(currentIndex-1);	//뒤회차

					ExptPtrnAnlyDto currentPatternData = new ExptPtrnAnlyDto();
					// 패턴분석정보 설정
					this.setExptPtrnAnly(patternType, currentPatternData, winDataList, currentSourceData, currentTargetData);
					String ptrnInfo = this.getPtrnInfo(patternType, currentPatternData);
					ptrnInfoDto.setPtrn_info(ptrnInfo);
					
					// 다음 패턴정보
					try {
						int nextIndex = currentIndex - 1;
						WinDataAnlyDto nextSourceData = winDataList.get(nextIndex);	//앞회차
						WinDataAnlyDto nextTargetData = winDataList.get(nextIndex-1);	//뒤회차
						
						ExptPtrnAnlyDto nextPatternData = new ExptPtrnAnlyDto();
						// 패턴분석정보 설정
						this.setExptPtrnAnly(patternType, nextPatternData, winDataList, nextSourceData, nextTargetData);
						String nextInfo = this.getPtrnInfo(patternType, nextPatternData);
						ptrnInfoDto.setNext_info(nextInfo);
					} catch (ArrayIndexOutOfBoundsException aiooe) {
						int nextIndex = currentIndex - 1;
						log.warn("다음 정보가 없습니다. (" + nextIndex + ")");
						ptrnInfoDto.setNext_info(null);
					} catch (Exception e) {
						ptrnInfoDto.setNext_info(null);
					}
					
					// 리스트에 등록 후 일괄등록 처리
					ptrnInfoList.add(ptrnInfoDto);
				}
			} catch (IndexOutOfBoundsException ioobe) {
				
				log.warn("### 예상패턴분석 Exception Start ##################");
				log.warn("\t패턴유형 : " + patternType);
				log.warn("\tallPatternCountList size = " + allPatternCountList.size());
				log.warn("\tcountListIndex = " + countListIndex);
				log.warn("\tcountList size = " + allPatternCountList.get(countListIndex).size());
				log.warn("\tcountList 0 win count = " + allPatternCountList.get(countListIndex).get(0).getWin_count());
				log.warn("\t첫 번째 회차의 다음회차  = " + (allPatternCountList.get(countListIndex).get(0).getWin_count() - 1 + countListIndex + 1));
				
				log.warn("\t> 데이터 확인");
				for (int i = 0; i < allPatternCountList.size(); i++) {
					ArrayList<WinDataAnlyDto> countList = allPatternCountList.get(i);
					
					log.warn("\t> 패턴" + (i+1));
					log.warn("\t> 패턴 개수 = " + countList.size());
					for (int j = 0; j < countList.size(); j++) {
						WinDataAnlyDto sourceData = countList.get(j);
						ExptPtrnAnlyDto targetPattern = new ExptPtrnAnlyDto();
						this.getSotsuCntPattern(targetPattern, sourceData);
						log.warn("\t\t>> 회차 = " + sourceData.getWin_count() + " / 소수_개수 = " + targetPattern.getP_num_cnt());
					}
				}
				
				log.warn("### 예상패턴분석 Exception End ##################");
				ioobe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 패턴정보 가져오기
	 * 
	 * @param patternType 패턴유형
	 * @param patternData 패턴데이터
	 * @return
	 */
	private String getPtrnInfo(int patternType, ExptPtrnAnlyDto patternData) {
		StringBuffer sb = new StringBuffer();
		if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
			sb.append(patternData.getCount_sum() + "/" + patternData.getCont_cnt() + "/" + patternData.getNot_cont_cnt());
		} else if (patternType == LottoUtil.PREV_COUNT) {
			sb.append(patternData.getSame_num_cnt() + "/" + patternData.getUp_1_cnt() + "/" + patternData.getDown_1_cnt());
		} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
			sb.append(patternData.getLow_high());
		} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
			sb.append(patternData.getOdd_even());
		} else if (patternType == LottoUtil.TOTAL_RANGE) {
			sb.append(patternData.getTotal_range_type());
		} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
			sb.append(patternData.getC_num_cnt());
		} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
			ArrayList<Integer> containGroupCnt = patternData.getZeroCntRange();
			StringBuffer sb2 = new StringBuffer();
			for (int j = 0; j < containGroupCnt.size(); j++) {
				sb2.append( (j==0?"":"-") + containGroupCnt.get(j));
			}			
			sb.append(sb2.toString());
		} else if (patternType == LottoUtil.END_NUMBER_CNT) {
			sb.append(patternData.getEnd_num_same_cnt());
		} else if (patternType == LottoUtil.SOTSU_CNT) {
			sb.append(patternData.getP_num_cnt());
		} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
			sb.append(patternData.getMulti_3_cnt());
		} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
			sb.append(patternData.getComp_num_cnt());
		} else if (patternType == LottoUtil.AC) {
			sb.append(patternData.getAc());
		} else if (patternType == LottoUtil.MC_MATCH_CNT) {
			sb.append(patternData.getMcnum_cnt());
		}
		
		return sb.toString();
	}

	/**
	 * 패턴분석정보 설정
	 * 
	 * @param patternType 패턴유형
	 * @param ptrnAnlyInfo 패턴분석정보
	 * @param winDataList 전체데이터 (오름차순)
	 * @param sourceData 앞회차 정보 (ex:839)
	 * @param targetData 뒤회차 정보 (ex:838)
	 */
	private void setExptPtrnAnly(int patternType, ExptPtrnAnlyDto ptrnAnlyInfo, List<WinDataAnlyDto> winDataList,
			WinDataAnlyDto sourceData, WinDataAnlyDto targetData) {
		if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
			this.getContainCntByCountPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.PREV_COUNT) {
			// 전회차 추출번호 패턴
			// 두 회차 번호를 비교해 당첨번호 추출 패턴을 구한다.
			this.getPrevCountPattern(ptrnAnlyInfo, sourceData, targetData);
		} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
			// 저고비율 패턴
			this.getLowHighPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
			// 홀짝비율 패턴
			this.getOddEvenPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.TOTAL_RANGE) {
			// 총합의 범위 패턴
			this.getTotalRangePattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
			// 연속수의 개수 패턴
			this.getConsecutivelyNumbersPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
			// 그룹 내 포함개수 패턴
			this.getContainGroupCntPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.END_NUMBER_CNT) {
			// 끝자리가 같은 수의 개수 패턴
			this.getEndNumberCntPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.SOTSU_CNT) {
			// 소수 개수 패턴
			this.getSotsuCntPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
			// 3의 배수 개수 패턴
			this.getNumberOf3CntPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
			// 합성수 개수 패턴
			this.getNumberOfNot3CntPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.AC) {
			// AC 패턴
			this.getAcPattern(ptrnAnlyInfo, sourceData);
		} else if (patternType == LottoUtil.MC_MATCH_CNT) {
			// 궁합수 개수 패턴
			this.getMcMatchCntPattern(ptrnAnlyInfo, winDataList, sourceData);
		}
		
	}

	/**
	 * <b>당첨번호 회차합 정보를 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 앞회차
	 */
	public void getContainCntByCountPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		ptrnInfo.setCount_sum(sourceData.getCount_sum());
		ptrnInfo.setCont_cnt(sourceData.getCont_cnt());
		ptrnInfo.setNot_cont_cnt(sourceData.getNot_cont_cnt());
	}
	
	/**
	 * <b>두 회차 번호를 비교해 당첨번호 추출 패턴을 구한다.</b>
	 * <p>(예: 570회차와 569회차 당첨번호를 비교하여 570회차에 나온 추출패턴을 구하기)</p>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 앞회차
	 * @param targetData 뒤회차
	 */
	public void getPrevCountPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData, WinDataAnlyDto targetData) {
		
		int e = 0;	//equal - 같은번호 출현개수
		int n = 0;	//next - 전회차 당첨번호의 1보다 큰 숫자 개수
		int p = 0;	//prev - 전회차 당첨번호의 1보다 작은 숫자 개수
		
		int[] sourceDataNumbers = LottoUtil.getNumbers(sourceData);
		int[] targetDataNumbers = LottoUtil.getNumbers(targetData);
		
		for (int i = 0; i < sourceDataNumbers.length; i++) {
			for (int j = 0; j < targetDataNumbers.length; j++) {
				if(sourceDataNumbers[i] == targetDataNumbers[j]){
					e++;
				}else if(sourceDataNumbers[i] == targetDataNumbers[j]+1){
					n++;
				}else if(sourceDataNumbers[i] == targetDataNumbers[j]-1){
					p++;
				}
			}		
		}
		
		ptrnInfo.setSame_num_cnt(e);
		ptrnInfo.setUp_1_cnt(n);
		ptrnInfo.setDown_1_cnt(p);
		
	}
	
	/**
	 * <b>저고비율 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getLowHighPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		String lowHigh = sourceData.getLow_high();

		ptrnInfo.setLow_high(lowHigh);
	}

	/**
	 * <b>홀짝비율 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getOddEvenPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		String oddEven = sourceData.getOdd_even();

		ptrnInfo.setOdd_even(oddEven);
	}
	
	/**
	 * <b>총합 패턴을 구한다.</b>
	 * 
	 * * 총합범위유형 (TOTAL_RANGE_TYPE)
	 * 1: 100 이하
	 * 2: 101 ~ 150
	 * 3: 151 ~ 200
	 * 4: 201 이상
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getTotalRangePattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int total = sourceData.getTotal();
		if (total <= 100) {
			ptrnInfo.setTotal_range_type(LottoUtil.TOTAL_RANGE_TYPE_1);
		} else if (total > 100 && total <= 150) {
			ptrnInfo.setTotal_range_type(LottoUtil.TOTAL_RANGE_TYPE_2);
		} else if (total > 150 && total <= 200) {
			ptrnInfo.setTotal_range_type(LottoUtil.TOTAL_RANGE_TYPE_3);
		} else {
			ptrnInfo.setTotal_range_type(LottoUtil.TOTAL_RANGE_TYPE_4);
		}
	}
	
	/**
	 * <b>연속수의 개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getConsecutivelyNumbersPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		int consecutiveCnt = 0;
		
		for(int index = 0 ; index < numbers.length - 1 ; index++){
			if( (numbers[index] + 1) == numbers[index+1] ){
				consecutiveCnt++;
			}
		}
		
		ptrnInfo.setC_num_cnt(consecutiveCnt);
	}
	
	/**
	 * <b>그룹 내 포함개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getContainGroupCntPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		
		/** 각 자리의 포함개수 */
		int[] containGroupCnt = {0,0,0,0,0};
		/** 숫자가 없는 자리 수의 개수 */
		int zeroCnt = 0;
		/** 
		 * 숫자가 없는 자리 범위
		 * 1 : 1~10
		 * 2 : 11~20
		 * 3 : 21~30
		 * 4 : 31~40
		 * 5 : 41~45
		 */
		ArrayList<Integer> zeroCntRange = new ArrayList<Integer>();
		
		
		//각 자리의 포함개수를 구한다.
		for(int index = 0 ; index < numbers.length ; index++){
			int mok = numbers[index]/10;
			/*
			 * 10의자리수는 작은 자리 수로 설정한다.
			 * 10 : 1의 자리
			 * 20 : 10의 자리
			 * 30 : 20의 자리
			 * 40 : 30의 자리
			 */
			if(mok > 0 && (numbers[index] % 10 == 0)){
				mok -= 1;
			}
			containGroupCnt[mok] = containGroupCnt[mok] + 1;
		}
		
		//숫자가 포함되지 않은 자릿수의 개수를 구한다.
		for(int index = 0 ; index < containGroupCnt.length ; index++){
			
			if(containGroupCnt[index] == 0){
				zeroCnt++;				
				zeroCntRange.add(index+1);
			}
		}
		
		ptrnInfo.setContainGroupCnt(containGroupCnt);
		ptrnInfo.setZeroCnt(zeroCnt);
		ptrnInfo.setZeroCntRange(zeroCntRange);
	}
	
	/**
	 * <b>끝자리가 같은 수의 개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getEndNumberCntPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		
		int[] endNumberCnt = {0,0,0,0,0,0,0,0,0,0};	//각 자리의 포함개수(0~9)
		int maxEndNumberCnt = 0;
		
		//각 번호별 끝자리의 출현개수를 구한다.
		for(int index = 0 ; index < numbers.length ; index++){
			int nmj = numbers[index] % 10;
			endNumberCnt[nmj] = endNumberCnt[nmj] + 1;
		}
		
		//끝자리가 가장 많이 출현한 개수를 구한다.
		for(int index = 0 ; index < endNumberCnt.length ; index++){
			if(endNumberCnt[index] > maxEndNumberCnt){
				maxEndNumberCnt = endNumberCnt[index]; 
			}
		}
		
		ptrnInfo.setEnd_num_same_cnt(maxEndNumberCnt);
	}
	
	/**
	 * <b>소수 개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getSotsuCntPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		
		int sotsuCnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			if(numbers[index] >= 2 && LottoUtil.getSotsu(numbers[index])){
				sotsuCnt++;
			}
		}
		
		ptrnInfo.setP_num_cnt(sotsuCnt);
	}
	
	/**
	 * <b>3의 배수 개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getNumberOf3CntPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		int numberOf3Cnt = 0;
		
		for(int index = 0 ; index < numbers.length ; index++){
			if(numbers[index] % 3 == 0){
				numberOf3Cnt++;
			}
		}
		
		ptrnInfo.setMulti_3_cnt(numberOf3Cnt);
	}
	
	/**
	 * <b>합성수 개수 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getNumberOfNot3CntPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int[] numbers = LottoUtil.getNumbers(sourceData);
		int numberOfNot3Cnt = 0;
		/** 합성수 배열 */
		int[] hapsu = {1,4,8,10,14,16,20,22,25,26,28,32,34,35,38,40,44};
		
		for(int index = 0 ; index < numbers.length ; index++){
			for(int i = 0 ; i < hapsu.length ; i++){
				if(numbers[index] == hapsu[i]){
					numberOfNot3Cnt++;
					break;
				}
			}
		}
		
		ptrnInfo.setComp_num_cnt(numberOfNot3Cnt);
	}
	
	/**
	 * <b>AC 패턴을 구한다.</b>
	 * 
	 * @param ptrnInfo 패턴정보
	 * @param sourceData 원본 데이터
	 */
	public void getAcPattern(ExptPtrnAnlyDto ptrnInfo, WinDataAnlyDto sourceData) {
		int ac = sourceData.getAc();
		
		ptrnInfo.setAc(ac);
	}
	
	/**
	 * <b>궁합수 개수 패턴을 구한다.</b>
	 * @param dataList 
	 * 
	 * @param sourceData 원본 데이터
	 * @return 패턴
	 */
	public void getMcMatchCntPattern(ExptPtrnAnlyDto ptrnInfo, List<WinDataAnlyDto> winDataList, WinDataAnlyDto sourceData) {
		Map<Integer, Map<String, ArrayList<Integer>>> mcNumberMap = lottoDataService.getMcNumberByAnly(winDataList);
		int[] dataNumbers = LottoUtil.getNumbers(sourceData);
		int mcMatchCnt = 0;
		
		//각 번호별 궁합수가 있는지 확인
		for (int count = 0; count < dataNumbers.length; count++) {
			Map<String, ArrayList<Integer>> mcMap = mcNumberMap.get(dataNumbers[count]);
			ArrayList<Integer> mcList = mcMap.get("mc");
			
			//궁합수 조회
			for (int i = 0; i < mcList.size(); i++) {
				boolean isExist = false;
				//예측번호 모두 비교
				for (int j = 0; j < dataNumbers.length; j++) {
					if(dataNumbers[j] == mcList.get(i)){
						//궁합수가 존재한다면
						mcMatchCnt++;
						isExist = true;
						break;
					}
					
				}
				
				if(isExist){
					break;
				}
			}
		}
		
		ptrnInfo.setMcnum_cnt(mcMatchCnt);
	}
	
	/**
	 * 패턴이 같은 회차 목록을 구한다.
	 * 
	 * @param sourcePattern 패턴
	 * @param winDataList 전체데이터
	 * @param countList 패턴이 같은 회차 목록
	 * @param startCnt 비교를 시작할 전체데이터 목록 index
	 * @param patternType 패턴 타입
	 */
	private void getPatternList(ExptPtrnAnlyDto sourcePattern, List<WinDataAnlyDto> winDataList, ArrayList<WinDataAnlyDto> countList, int startCnt, int patternType) {
		
		for (int i = startCnt; i > 0; i--) {
			// 앞회차와 전회차와 비교해 당첨번호 추출 패턴을 구한다.
			WinDataAnlyDto sourceData = winDataList.get(i);
			WinDataAnlyDto targetData = winDataList.get(i - 1);

			ExptPtrnAnlyDto targetPattern = new ExptPtrnAnlyDto();
			if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
				this.getContainCntByCountPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.PREV_COUNT) {
				this.getPrevCountPattern(targetPattern, sourceData, targetData);
			} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
				this.getLowHighPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
				this.getOddEvenPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.TOTAL_RANGE) {
				this.getTotalRangePattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
				this.getConsecutivelyNumbersPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
				this.getContainGroupCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.END_NUMBER_CNT) {
				this.getEndNumberCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.SOTSU_CNT) {
				this.getSotsuCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
				this.getNumberOf3CntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
				this.getNumberOfNot3CntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.AC) {
				this.getAcPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.MC_MATCH_CNT) {
				this.getMcMatchCntPattern(targetPattern, winDataList, sourceData);
			}

			if (LottoUtil.comparePattern(sourcePattern, targetPattern, patternType)) {
				countList.add(sourceData);
			}
		}
	}
	
	/**
	 * 패턴이 같은 회차 목록을 구한다.
	 * @param sourcePattern 패턴
	 * @param winDataList 전체데이터
	 * @param patternCountList 비교할 회차 목록
	 * @param countList 패턴이 같은 회차 목록
	 * @param patternType 패턴 타입
	 */
	private void getPatternList(ExptPtrnAnlyDto sourcePattern, 
								List<WinDataAnlyDto> winDataList, 
								ArrayList<WinDataAnlyDto> patternCountList, 
								ArrayList<WinDataAnlyDto> countList, 
								int patternType) {
		// 비교할 회차 목록만큼 반복한다.
		for (int i = 0; i < patternCountList.size(); i++) {

			// 비교할 회차 목록의 회차가 2회차보다 작으면 종료한다.
			if (patternCountList.get(i).getWin_count() - 1 < 2) {
				break;
			}

			// 원본 데이터는 비교할 회차의 1회 전 회차 데이터로 설정한다.
			WinDataAnlyDto sourceData = winDataList.get(patternCountList.get(i).getWin_count() - 1 - 1);
			// 대상 데이터는 비교할 회차의 2회 전 회차 데이터로 설정한다.
			WinDataAnlyDto targetData = winDataList.get(patternCountList.get(i).getWin_count() - 1 - 2);

			ExptPtrnAnlyDto targetPattern = new ExptPtrnAnlyDto();
			if (patternType == LottoUtil.CONTAIN_CNT_BY_COUNT) {
				this.getContainCntByCountPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.PREV_COUNT) {
				this.getPrevCountPattern(targetPattern, sourceData, targetData);
			} else if (patternType == LottoUtil.LOW_HIGH_RATIO) {
				this.getLowHighPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.ODD_EVEN_RATIO) {
				this.getOddEvenPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.TOTAL_RANGE) {
				this.getTotalRangePattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.CONSECUTIVELY_NUMBERS) {
				this.getConsecutivelyNumbersPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.CONTAIN_GROUP_CNT) {
				this.getContainGroupCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.END_NUMBER_CNT) {
				this.getEndNumberCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.SOTSU_CNT) {
				this.getSotsuCntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.NUMBER_OF_3_CNT) {
				this.getNumberOf3CntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.NUMBER_OF_NOT_3_CNT) {
				this.getNumberOfNot3CntPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.AC) {
				this.getAcPattern(targetPattern, sourceData);
			} else if (patternType == LottoUtil.MC_MATCH_CNT) {
				this.getMcMatchCntPattern(targetPattern, winDataList, sourceData);
			}

			if (LottoUtil.comparePattern(sourcePattern, targetPattern, patternType)) {
				countList.add(sourceData);
			}
		}
	}

	/**
	 * 예상번호 패턴정보 조회
	 * 
	 * @param dto
	 * @return
	 */
	public ExptPtrnAnlyDto getExptPtrnAnlyInfo(ExptPtrnAnlyDto dto) {
		return (ExptPtrnAnlyDto) baseDao.getSingleRow("ptrnAnlyMapper.getExptPtrnAnlyInfo", dto);
	}
	
	
	
}
