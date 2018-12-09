package com.lotto.spring.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chello.base.spring.core.DefaultService;
import com.lotto.common.CommonUtils;
import com.lotto.common.LottoUtil;
import com.lotto.spring.domain.dto.WinDataDto;

@Service("excelService")
public class ExcelService extends DefaultService {

	private Logger log = Logger.getLogger(this.getClass());
    private final String filePath = "C:\\smlotto\\temp\\";
    
	private XSSFWorkbook workBook = null;
	
	public File getFileInfo(HttpServletRequest request){
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        Map<String, Object> listMap = null;
         
        //임시파일 저장 디렉토리 생성 
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
        
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                file = new File(filePath + storedFileName);
                try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					log.info("ExcelService.getFileInfo IllegalStateException"); 
					e.printStackTrace();
				} catch (IOException e) {
					log.info("ExcelService.getFileInfo IOException"); 
					e.printStackTrace();
				}
                
//                listMap = new HashMap<String,Object>();
//                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
//                listMap.put("STORED_FILE_NAME", storedFileName);
//                listMap.put("FILE_SIZE", multipartFile.getSize());
//                list.add(listMap);
            }
        }
        
        
        return file;
	}

	@SuppressWarnings("rawtypes")
	public List xlsExcelReader(MultipartHttpServletRequest req, String dataType) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		
		if ("UserInfo".equals(dataType)) {
			list = getUserInfoListByXls(req);
		} else if ("WinData".equals(dataType)) {
			list = getWinDataListByXls(req);
		}
		
		return list;
	}
	
	/**
	 * @param file
	 * @param dataType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List xlsExcelReader(File file, String dataType) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		
		if ("UserInfo".equals(dataType)) {
			list = getUserInfoListByXls(file);
		} else if ("WinData".equals(dataType)) {
			list = getWinDataListByXls(file);
		}
		
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List getWinDataListByXls(MultipartHttpServletRequest req) {
		return getWinDataListByXls(getFileInfo(req));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List getWinDataListByXls(File file) {
		// 반환할 객체를 생성
		List list = new ArrayList<>();
		HSSFWorkbook workbook = null;

		try {
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
//			workbook = new HSSFWorkbook(file.getInputStream());
			workbook = new HSSFWorkbook(new FileInputStream(file));

			// 탐색에 사용할 Sheet, Row, Cell 객체
			HSSFSheet curSheet;
			HSSFRow curRow;
			HSSFCell curCell;
			WinDataDto dto;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0, 1은 헤더정보이기 때문에 무시
					if (rowIndex > 1) {
						curRow = curSheet.getRow(rowIndex);
						String value;
						boolean isError = false;	// 입력 에러 여부
						
						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							dto = new WinDataDto();
							
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);

									log.debug("******************************************");
									log.debug("isnull = " + (curCell == null));
									log.debug("Style = " + curCell.getCellStyle());
									log.debug("Type = " + curCell.getCellType());
									
									// 2018.07.07
									if (cellIndex == 0 && (curCell == null || curCell.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
										log.debug("skip set data. continue");
										isError = true;
										break;
									}
									
									value = "";
									// cell 스타일이 다르더라도 String으로 반환 받음
									switch (curCell.getCellType()) {
									case HSSFCell.CELL_TYPE_FORMULA:
										value = curCell.getCellFormula();
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										value = curCell.getNumericCellValue() + "";
										
										if (cellIndex == 0) {
											// 2018.07.07
											// 셀 유형이 "날짜"일 경우 날짜처리. 2018.06.21
											if (DateUtil.isCellDateFormatted(curCell)) {
												Date date = curCell.getDateCellValue();
												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											} else {
												// 일반 숫자가 입력되어 있을 경우, 값을 가져오면서 숫자뒤에 .0이 붙음.
												// .0 제거
												if (value != null && !"".equals(value) && value.indexOf(".0") > -1) {
													if (value.endsWith(".0")) {
														value = value.replace(".0", "");
													}
												}
											}
										}
										
										break;
									case HSSFCell.CELL_TYPE_STRING:
										value = curCell.getStringCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BOOLEAN:
										value = curCell.getBooleanCellValue() + "";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										value = "";
										break;
									case HSSFCell.CELL_TYPE_ERROR:
										value = curCell.getErrorCellValue() + "";
										break;
									default:
										value = new String();
										break;
									} // end switch

									// 현재 colum index에 따라서 vo입력
									switch (cellIndex) {
									case 0: // 회차
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("회차 입력값 오류 : " + value);
										}
										dto.setWin_count(Integer.valueOf(value));
										break;
									case 1: // 번호1
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호1 입력값 오류 : " + value);
										}
										dto.setNum1(Integer.valueOf(value));
										break;
									case 2: // 번호2
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호2 입력값 오류 : " + value);
										}
										dto.setNum2(Integer.valueOf(value));
										break;
									case 3: // 번호3
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호3 입력값 오류 : " + value);
										}
										dto.setNum3(Integer.valueOf(value));
										break;
									case 4: // 번호4
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호4 입력값 오류 : " + value);
										}
										dto.setNum4(Integer.valueOf(value));
										break;
									case 5: // 번호5
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호5 입력값 오류 : " + value);
										}
										dto.setNum5(Integer.valueOf(value));
										break;
									case 6: // 번호6
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호6 입력값 오류 : " + value);
										}
										dto.setNum6(Integer.valueOf(value));
										break;
									case 7: // 보너스번호
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("보너스번호 입력값 오류 : " + value);
										}
										dto.setBonus_num(Integer.valueOf(value));
										break;
									default:
										break;
									}
									
									//추가정보 생성
									int iTotal = LottoUtil.getTotal(dto);
							    	String sLowHigh = LottoUtil.getLowHigh(dto);  
							    	String sOddEven = LottoUtil.getOddEven(dto);  
							    	int iSumEndNum = LottoUtil.getSumEndNumber(dto);
							    	int iAc = LottoUtil.getAc(dto);
							    	
							    	dto.setTotal(iTotal);					//총합(보너스번호 제외)
									dto.setLow_high(sLowHigh);					//고저
									dto.setOdd_even(sOddEven);					//홀짝
									dto.setSum_end_num(iSumEndNum);			//끝수합
									dto.setAc(iAc);								//산술적 복잡도(AC)
								} // end for
								
								if (!isError) {
									// cell 탐색 이후 vo 추가
									list.add(dto);
								}
								
							} // end
						} // end if
					}

				}
			}
		} catch (IOException e) {
			log.info("[ERROR] An error occurred while reading the xls file.");
			e.printStackTrace();
		} catch (Exception e) {
			log.info("[ERROR] 당첨번호 목록 엑셀업로드 오류 => " + e.getMessage());
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private List getUserInfoListByXls(MultipartHttpServletRequest req) {
		return getUserInfoListByXls(getFileInfo(req));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getUserInfoListByXls(File file) {
		// 반환할 객체를 생성
		List list = new ArrayList<>();
		HSSFWorkbook workbook = null;

		//소속유형 목록 조회
//		Map codeSearchMap = new HashMap();
//		codeSearchMap.put("code_type", "C000000001");
//		List<CaseInsensitiveMap> storeTypeList = (List<CaseInsensitiveMap> ) baseDao.getList("commonMapper.getCodeList", codeSearchMap);
//		
//		try {
//			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
////			workbook = new HSSFWorkbook(file.getInputStream());
//			workbook = new HSSFWorkbook(new FileInputStream(file));
//
//			// 탐색에 사용할 Sheet, Row, Cell 객체
//			HSSFSheet curSheet;
//			HSSFRow curRow;
//			HSSFCell curCell;
//			UserInfoDto dto;
//
//			// Sheet 탐색 for문
//			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
//				// 현재 sheet 반환
//				curSheet = workbook.getSheetAt(sheetIndex);
//				// row 탐색 for문
//				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
//					// row 0, 1은 헤더정보이기 때문에 무시
//					if (rowIndex > 1) {
//						curRow = curSheet.getRow(rowIndex);
//						String value;
//						
//						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
//						if (curRow.getCell(0) != null) {
//							dto = new UserInfoDto();
//							
//							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
//								// cell 탐색 for문
//								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
//									curCell = curRow.getCell(cellIndex);
//									/*
//									 * 2018.06.25 심규서
//									 * 엑셀 필터를 걸어 빈 열까지 처리된 상태로 업로드 시 NullPointerException 발생
//									 * 열 입력 범위까지 예외처리 추가
//									 */
//									if (cellIndex >= 7 && curCell == null) {
//										break;
//									}
//									
//									value = "";
//									// cell 스타일이 다르더라도 String으로 반환 받음
//									switch (curCell.getCellType()) {
//									case HSSFCell.CELL_TYPE_FORMULA:
//										value = curCell.getCellFormula();
//										break;
//									case HSSFCell.CELL_TYPE_NUMERIC:
//										// 2018.07.07
//										// 셀 유형이 "날짜"일 경우 날짜처리. 2018.06.21
//										if (DateUtil.isCellDateFormatted(curCell)) {
//											Date date = curCell.getDateCellValue();
//											value = new SimpleDateFormat("yyyy-MM-dd").format(date);
//										} else {
//											// "일반"일 경우 숫자 처리
//											value = curCell.getNumericCellValue() + "";
//											// 일반 숫자가 입력되어 있을 경우, 값을 가져오면서 숫자뒤에 .0이 붙음.
//											// .0 제거
//											if (value != null && !"".equals(value) && value.indexOf(".0") > -1) {
//												if (value.endsWith(".0")) {
//													value = value.replace(".0", "");
//												}
//											}
//										}
//										break;
//									case HSSFCell.CELL_TYPE_STRING:
//										value = curCell.getStringCellValue() + "";
//										break;
//									case HSSFCell.CELL_TYPE_BOOLEAN:
//										value = curCell.getBooleanCellValue() + "";
//										break;
//									case HSSFCell.CELL_TYPE_BLANK:
//										value = "";
//										break;
//									case HSSFCell.CELL_TYPE_ERROR:
//										value = curCell.getErrorCellValue() + "";
//										break;
//									default:
//										value = new String();
//										break;
//									} // end switch
//
//									// 현재 colum index에 따라서 vo입력
//									switch (cellIndex) {
//									case 0: // 소속구분
//										dto.setStore_type(getStoreTypeCode(storeTypeList, value));
//										break;
//									case 1: // 소속
//										dto.setStore_desc(value);
//										break;
//									case 2: // 사원명
//										dto.setUsr_nm(value);
//										break;
//									case 3: // 사원코드
//										dto.setUsr_id(value);
//										break;
//									case 4: // 코스트센터코드
//										dto.setCost_centre_code(value);
//										break;
//									case 5: // 배송처코드
//										dto.setShip_to_code(value);
//										break;
//									case 6: // 비밀번호초기값
//										dto.setEtc01(value);
//										break;
//									default:
//										break;
//									}
//									
//								} // end for
//									// cell 탐색 이후 vo 추가
//								list.add(dto);
//							} // end
//						} // end if
//					}
//
//				}
//			}
//		} catch (IOException e) {
//			log.info("[ERROR] An error occurred while reading the xls file.");
//			e.printStackTrace();
//		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List xlsxExcelReader(MultipartHttpServletRequest req, String dataType) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		
		if ("UserInfo".equals(dataType)) {
			list = getUserInfoListByXlsx(req);
		} else if ("WinData".equals(dataType)) {
			list = getWinDataListByXlsx(req);	
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List xlsxExcelReader(File file, String dataType) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		
		if ("UserInfo".equals(dataType)) {
			list = getUserInfoListByXlsx(file);
		} else if ("WinData".equals(dataType)) {
			list = getWinDataListByXlsx(file);	
		}
		
		return list;
	}	
	
	@SuppressWarnings("rawtypes")
	private List getWinDataListByXlsx(MultipartHttpServletRequest req) {
//		MultipartFile file = req.getFile("excel");
		return getWinDataListByXlsx(getFileInfo(req));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getWinDataListByXlsx(File file) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		XSSFWorkbook workbook = null;

		try {
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
//			workbook = new XSSFWorkbook(file.getInputStream());
			workbook = new XSSFWorkbook(new FileInputStream(file));

			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			WinDataDto dto;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex > 0) {
						curRow = curSheet.getRow(rowIndex);
						String value;
						boolean isError = false;	// 입력 에러 여부

						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							dto = new WinDataDto();
							
							// 2018.07.07 java.lang.IllegalStateException: Cannot get a text value from a numeric cell
							// if문 주석처리함.
//							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);

									log.debug("******************************************");
									log.debug("isnull = " + (curCell == null));
									log.debug("Style = " + curCell.getCellStyle());
									log.debug("Type = " + curCell.getCellType());
									
									// 2018.07.07
									if (cellIndex == 0 && (curCell == null || curCell.getCellType() == XSSFCell.CELL_TYPE_BLANK)) {
										log.debug("skip set data. continue");
										isError = true;
										break;
									}
									
									value = "";
									// cell 스타일이 다르더라도 String으로 반환 받음
									switch (curCell.getCellType()) {
									case XSSFCell.CELL_TYPE_FORMULA:
										value = curCell.getCellFormula();
										break;
									case XSSFCell.CELL_TYPE_NUMERIC:
										value = curCell.getNumericCellValue() + "";
										
										if (cellIndex == 0) {
											// 2018.07.07
											// 셀 유형이 "날짜"일 경우 날짜처리. 2018.06.21
											if (DateUtil.isCellDateFormatted(curCell)) {
												Date date = curCell.getDateCellValue();
												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
											} else {
												// 일반 숫자가 입력되어 있을 경우, 값을 가져오면서 숫자뒤에 .0이 붙음.
												// .0 제거
												if (value != null && !"".equals(value) && value.indexOf(".0") > -1) {
													if (value.endsWith(".0")) {
														value = value.replace(".0", "");
													}
												}
											}
										}
										
										break;
									case XSSFCell.CELL_TYPE_STRING:
										value = curCell.getStringCellValue() + "";
										break;
									case XSSFCell.CELL_TYPE_BOOLEAN:
										value = curCell.getBooleanCellValue() + "";
										break;
									case XSSFCell.CELL_TYPE_BLANK:
										value = "";
										break;
									case XSSFCell.CELL_TYPE_ERROR:
										value = curCell.getErrorCellValue() + "";
										break;
									default:
										value = new String();
										break;
									} // end switch

									
									// 일반 숫자가 입력되어 있을 경우, 값을 가져오면서 숫자뒤에 .0이 붙음.
									// .0 제거
									if (value != null && !"".equals(value) && value.indexOf(".0") > -1) {
										if (value.endsWith(".0")) {
											value = value.replace(".0", "");
										}
									}
									
									
									log.debug("row = " + rowIndex + ", cell = " + cellIndex + ", value = " + value);
									
									// 현재 colum index에 따라서 vo입력
									switch (cellIndex) {
									case 0: // 회차
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("회차 입력값 오류 : " + value);
										}
										dto.setWin_count(Integer.valueOf(value));
										break;
									case 1: // 번호1
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호1 입력값 오류 : " + value);
										}
										dto.setNum1(Integer.valueOf(value));
										break;
									case 2: // 번호2
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호2 입력값 오류 : " + value);
										}
										dto.setNum2(Integer.valueOf(value));
										break;
									case 3: // 번호3
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호3 입력값 오류 : " + value);
										}
										dto.setNum3(Integer.valueOf(value));
										break;
									case 4: // 번호4
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호4 입력값 오류 : " + value);
										}
										dto.setNum4(Integer.valueOf(value));
										break;
									case 5: // 번호5
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호5 입력값 오류 : " + value);
										}
										dto.setNum5(Integer.valueOf(value));
										break;
									case 6: // 번호6
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("번호6 입력값 오류 : " + value);
										}
										dto.setNum6(Integer.valueOf(value));
										break;
									case 7: // 보너스번호
										if (!CommonUtils.isNumber(value)) {
											throw new Exception("보너스번호 입력값 오류 : " + value);
										}
										dto.setBonus_num(Integer.valueOf(value));
										break;
									default:
										break;
									}
									
									//추가정보 생성
									int iTotal = LottoUtil.getTotal(dto);
							    	String sLowHigh = LottoUtil.getLowHigh(dto);  
							    	String sOddEven = LottoUtil.getOddEven(dto);  
							    	int iSumEndNum = LottoUtil.getSumEndNumber(dto);
							    	int iAc = LottoUtil.getAc(dto);
							    	
							    	dto.setTotal(iTotal);					//총합(보너스번호 제외)
									dto.setLow_high(sLowHigh);					//고저
									dto.setOdd_even(sOddEven);					//홀짝
									dto.setSum_end_num(iSumEndNum);			//끝수합
									dto.setAc(iAc);	
									
								} // end for
								
								if (!isError) {
									// cell 탐색 이후 vo 추가
									list.add(dto);
								}
								
//							} // end
						} // end if
					}

				}
			}
		} catch (IOException e) {
			log.info("[ERROR] An error occurred while reading the xls file.");
			e.printStackTrace();
		} catch (Exception e) {
			log.info("[ERROR] 당첨번호 목록 엑셀업로드 오류 => " + e.getMessage());
		}

		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private List getUserInfoListByXlsx(MultipartHttpServletRequest req) {
//		MultipartFile file = req.getFile("excel");
		return getUserInfoListByXlsx(getFileInfo(req));
	}
	
	@SuppressWarnings("rawtypes")
	private List getUserInfoListByXlsx(File file) {
		// 반환할 객체를 생성
		List list = new ArrayList();
		XSSFWorkbook workbook = null;

//		//소속유형 목록 조회
//		Map codeSearchMap = new HashMap();
//		codeSearchMap.put("code_type", "C000000001");
//		List<CaseInsensitiveMap> storeTypeList = (List<CaseInsensitiveMap> ) baseDao.getList("commonMapper.getCodeList", codeSearchMap);
//				
//		try {
//			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
////			workbook = new XSSFWorkbook(file.getInputStream());
//			workbook = new XSSFWorkbook(new FileInputStream(file));
//
//			// 탐색에 사용할 Sheet, Row, Cell 객체
//			XSSFSheet curSheet;
//			XSSFRow curRow;
//			XSSFCell curCell;
//			UserInfoDto dto;
//
//			// Sheet 탐색 for문
//			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
//				// 현재 sheet 반환
//				curSheet = workbook.getSheetAt(sheetIndex);
//				// row 탐색 for문
//				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
//					// row 0, 1은 헤더정보이기 때문에 무시
//					if (rowIndex > 1) {
//						curRow = curSheet.getRow(rowIndex);
//						String value;
//
//						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
//						if (curRow.getCell(0) != null) {
//							dto = new UserInfoDto();
//							
//							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
//								// cell 탐색 for문
//								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
//									
//									curCell = curRow.getCell(cellIndex);
//									
//									/*
//									 * 2018.06.25 심규서
//									 * 엑셀 필터를 걸어 빈 열까지 처리된 상태로 업로드 시 NullPointerException 발생
//									 * 열 입력 범위까지 예외처리 추가
//									 */
//									if (cellIndex >= 7 && curCell == null) {
//										break;
//									}
//									
//									value = "";
//									
//									// cell 스타일이 다르더라도 String으로 반환 받음
//									try {
//										switch (curCell.getCellType()) {
//										case XSSFCell.CELL_TYPE_FORMULA:
//											value = curCell.getCellFormula();
//											break;
//										case XSSFCell.CELL_TYPE_NUMERIC:
//											// 2018.07.07
//											// 셀 유형이 "날짜"일 경우 날짜처리. 2018.06.21
//											if (DateUtil.isCellDateFormatted(curCell)) {
//												Date date = curCell.getDateCellValue();
//												value = new SimpleDateFormat("yyyy-MM-dd").format(date);
//											} else {
//												// "일반"일 경우 숫자 처리
//												value = curCell.getNumericCellValue() + "";
//												// 일반 숫자가 입력되어 있을 경우, 값을 가져오면서 숫자뒤에 .0이 붙음.
//												// .0 제거
//												if (value != null && !"".equals(value) && value.indexOf(".0") > -1) {
//													if (value.endsWith(".0")) {
//														value = value.replace(".0", "");
//													}
//												}
//											}
//											break;
//										case XSSFCell.CELL_TYPE_STRING:
//											value = curCell.getStringCellValue() + "";
//											break;
//										case XSSFCell.CELL_TYPE_BOOLEAN:
//											value = curCell.getBooleanCellValue() + "";
//											break;
//										case XSSFCell.CELL_TYPE_BLANK:
//											value = "";
//											break;
//										case XSSFCell.CELL_TYPE_ERROR:
//											value = curCell.getErrorCellValue() + "";
//											break;
//										default:
//											value = new String();
//											break;
//										} // end switch
//
//									} catch (NullPointerException npe) {
//										log.info("###########################################################");
//										log.info(npe.getMessage());
//										log.info("rowIndex : " + rowIndex + ", cellIndex : " + cellIndex);
//										log.info("###########################################################"); 
//									}
//									
//									// 현재 colum index에 따라서 vo입력
//									switch (cellIndex) {
//									case 0: // 소속구분
//										dto.setStore_type(getStoreTypeCode(storeTypeList, value));
//										break;
//									case 1: // 소속
//										dto.setStore_desc(value);
//										break;
//									case 2: // 사원명
//										dto.setUsr_nm(value);
//										break;
//									case 3: // 사원코드
//										dto.setUsr_id(value);
//										break;
//									case 4: // 코스트센터코드
//										dto.setCost_centre_code(value);
//										break;
//									case 5: // 배송처코드
//										dto.setShip_to_code(value);
//										break;
//									case 6: // 비밀번호초기값
//										dto.setEtc01(value);
//										break;
//									default:
//										break;
//									}
//									
//								} // end for
//								// cell 탐색 이후 vo 추가
//								list.add(dto);
//							} // end
//						} // end if
//					}
//
//				}
//			}
//		} catch (IOException e) {
//			log.info("[ERROR] An error occurred while reading the xlsx file.");
//			e.printStackTrace();
//		}

		return list;
	}

	
	/**
	 * 파일로부터 저장된 정보 가져오기
	 * 
	 * @param fileName
	 * @return
	 */
	public Vector<String> getInOutSheetFromFile(InputStream inputStream) {

		// 라인 저장 임시 벡터
		Vector<String> tmpContent = new Vector<String>();
		Vector<String> content = new Vector<String>();
		boolean isNull = false;
		// File file = new File(fileName);

		try {// 엑셀 파일 오픈
		// workBook = new XSSFWorkbook(new FileInputStream(file));
			workBook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Row row : workBook.getSheetAt(0)) {// 행 구분
			String str = new String();
			for (Cell cell : row) { // 열구분
				isNull = false;

				// 셀의 타입 따라 받아서 구분지어 받되 한 행을 하나의 스트링으로 저장
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					str = str + cell.getRichStringCellValue().getString();
					break;

				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell))
						str = str + cell.getDateCellValue().toString();
					else
						str = str
								+ Integer.toString((int) cell
										.getNumericCellValue());
					break;

				case Cell.CELL_TYPE_BOOLEAN:
					str = str + cell.getBooleanCellValue();
					break;

				case Cell.CELL_TYPE_FORMULA:
					str = str + cell.getCellFormula();
					break;

				default: // 값이 없는 열은 포함되지 않게 함.
					isNull = true;
				}
				if (isNull != true)
					str = str + ",";
			}
			// 한 행이 읽혀 지면 벡터에 추가
			tmpContent.add(str);
		}

		// 한 행씩 넣으면서 마지막 배열에 space가 추가되것을 제거 시킴.
		// 이부분은 space가 있을때만 사용합니다.
		for (int i = 0; i < tmpContent.size(); i++) {
			String str = tmpContent.get(i);
			str = str.substring(0, str.length() - 1);
			content.add(str);
		}

		return content;
	}

	/**
	 * 오더 제출하기
	 * 
	 * @param map
	 * @param uploadList
	 * @return
	 */
	@Deprecated
	@SuppressWarnings({ "resource", "rawtypes" })
	public CaseInsensitiveMap submitOrder(Map map,
			ArrayList<CaseInsensitiveMap> uploadList) {
		CaseInsensitiveMap resultMap = new CaseInsensitiveMap();
		resultMap.put("result", "fail");
		resultMap.put("msg", "오더 제출에 실패했습니다.(Order submission failed.)");
		
		String orderType = (String) map.get("order_type");
		String remarkType = (String) map.get("remark_type");
		
		//컬럼 목록 가져오기
	    List<CaseInsensitiveMap> getColumns = getColumns(orderType, remarkType);
	    if (CommonUtils.canReadList(getColumns)) {
	    	String[][] getColumnsTitles = getColumnsTitles(orderType, remarkType);
	    	if (CommonUtils.canReadList(getColumnsTitles)) {
	    		/*****************************************************************************
	    	     * 엑셀파일 기록 시작
	    	     *****************************************************************************/
	    		String SHEET_NAME = "Upload";
	    		String downloadPath = (String) map.get("download_path");
	    		String fileName = (String) map.get("file_name");
	    		
	    		// Verify directory creation
	    		File directory = new File(downloadPath);
	    		if (!directory.exists()) {
	    			directory.mkdirs();
	    		}
	    		
	    		// 워크북 생성
	    		XSSFWorkbook objWorkBook = new XSSFWorkbook();
	    		// 워크시트 생성
	    		XSSFSheet objSheet = objWorkBook.createSheet();
	    		// 시트 이름
	    		objWorkBook.setSheetName(0, SHEET_NAME);
	    		// 행생성
	    		XSSFRow objRow = objSheet.createRow(0);
	    		// 셀 생성
	    		XSSFCell objCell = null;

	    		
	    		/********************
	    		 * 타이틀 설정
	    		 ********************/
	    		// 컬럼 사이즈
	    		int colSize = getColumns.size();
	    		// title row cnt
	    		int titleRowCnt = getColumnsTitles.length;
	    		
	    		/********************
	    		 * 컬럼길이 width 설정 기준 57 = 1px
	    		 ********************/
	    		for (int i = 0; i < colSize; i++) {
	    			CaseInsensitiveMap dataMap = getColumns.get(i);
	    			String width = (String) dataMap.get("width");
	    			int wid = Integer.parseInt(width);
	    			objSheet.setColumnWidth(i, 57 * wid);
	    		}
	    		
	    		/********************
	    		 * 스타일 설정
	    		 ********************/
	    		// 스타일 객체 생성
	    		XSSFCellStyle styleHd = objWorkBook.createCellStyle(); // 제목 스타일
	    		XSSFCellStyle styleSub = objWorkBook.createCellStyle(); // 상단 스타일
	    		XSSFCellStyle styleCon = objWorkBook.createCellStyle(); // 내용 스타일
	    		XSSFCellStyle styleConRight = objWorkBook.createCellStyle(); // 내용 스타일 RIGHT

	    		// 제목 폰트
	    		XSSFFont font = objWorkBook.createFont();
	    		font.setFontHeightInPoints((short) 15);
	    		font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
	    		// 제목 스타일에 폰트 적용, 정렬
	    		styleHd.setFont(font);
	    		styleHd.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    		styleHd.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    		// 테두리
	    		styleHd.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    		styleHd.setBottomBorderColor(new XSSFColor(Color.BLACK));
	    		styleHd.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    		styleHd.setLeftBorderColor(new XSSFColor(Color.BLACK));
	    		styleHd.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    		styleHd.setRightBorderColor(new XSSFColor(Color.BLACK));
	    		styleHd.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    		styleHd.setTopBorderColor(new XSSFColor(Color.BLACK));
	    		// 셀에 색 넣기
	    		styleHd.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);// not
	    		// BackgroundColor
	    		byte[] hdRgb = new byte[3];
	    		hdRgb[0] = (byte) 253; // red
	    		hdRgb[1] = (byte) 233; // green
	    		hdRgb[2] = (byte) 217; // blue
	    		styleHd.setFillForegroundColor(new XSSFColor(hdRgb));
	    		
	    		// 상단 폰트
	    		XSSFFont font2 = objWorkBook.createFont();
	    		font2.setBoldweight((short) font.BOLDWEIGHT_BOLD);
	    		// 테두리
	    		styleSub.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    		styleSub.setBottomBorderColor(new XSSFColor(Color.BLACK));
	    		styleSub.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    		styleSub.setLeftBorderColor(new XSSFColor(Color.BLACK));
	    		styleSub.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    		styleSub.setRightBorderColor(new XSSFColor(Color.BLACK));
	    		styleSub.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    		styleSub.setTopBorderColor(new XSSFColor(Color.BLACK));
	    		// 셀에 색 넣기
	    		styleSub.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);// not
	    		// BackgroundColor
	    		byte[] subRgb = new byte[3];
	    		subRgb[0] = (byte) 255; // red
	    		subRgb[1] = (byte) 192; // green
	    		subRgb[2] = (byte) 0; // blue
	    		styleSub.setFillForegroundColor(new XSSFColor(subRgb));
	    		// 글자 속성
	    		styleSub.setFont(font2);
	    		styleSub.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    		styleSub.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    		styleSub.setWrapText(true);

	    		// 내용 스타일
	    		styleCon.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    		styleCon.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    		// 테두리
	    		styleCon.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    		styleCon.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    		styleCon.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    		styleCon.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    		
	    		styleConRight.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	    		styleConRight.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    		// 테두리
	    		styleConRight.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    		styleConRight.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    		styleConRight.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    		styleConRight.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    		
	    		/********************
	    		 * Data
	    		 ********************/
	    		// 타이틀--------------------------------------------------
	    		for (int i = 0; i < getColumnsTitles.length; i++) {
	    			objRow = objSheet.createRow(i);

	    			for (int j = 0; j < getColumnsTitles[i].length; j++) {
	    				objCell = objRow.createCell(j);
	    				objCell.setCellValue(getColumnsTitles[i][j]);
	    				if (i == 0) {
	    					objCell.setCellStyle(styleHd);
	    				} else {
	    					objCell.setCellStyle(styleSub);
	    				}
	    			}
	    		}

	    		// 내용--------------------------------------------------
	    		for (int rowIdx = 0; rowIdx < uploadList.size(); rowIdx++) {
	    			objRow = objSheet.createRow(titleRowCnt + rowIdx);
	    			objRow.setHeight((short) 0x150);

	    			CaseInsensitiveMap rowMap = uploadList.get(rowIdx);

	    			for (int colIdx = 0; colIdx < colSize; colIdx++) {
	    				CaseInsensitiveMap colMap = getColumns.get(colIdx);
	    				String name = (String) colMap.get("name");

	    				objCell = objRow.createCell(colIdx);
	    				if ("quantity".equals(name)) {
	    					objCell.setCellValue( Integer.parseInt(String.valueOf(rowMap.get(name))) );
	    					objCell.setCellStyle(styleConRight);
	    				} else if ("delivery_date".equals(name)) {
	    					objCell.setCellValue((String) rowMap.get(name));
	    					objCell.setCellStyle(styleConRight);
	    				} else {
	    					objCell.setCellValue((String) rowMap.get(name));
	    					objCell.setCellStyle(styleCon);
	    				}
	    			}

	    		}// end for 내용
	    		
	    		//파일 저장
	    		File file = new File(downloadPath + "/", fileName);
	    		FileOutputStream fileOutput = null;
	    		try {
	    			fileOutput = new FileOutputStream(file);
	    			objWorkBook.write(fileOutput);
	    			
	    			resultMap.put("result", "success");
	    			resultMap.put("msg", "오더를 제출했습니다.");
	    		} catch (FileNotFoundException e) {
	    			resultMap.put("msg", "오더 제출에 실패했습니다.(FileNotFoundException : " + e.getMessage() + ")");
	    		} catch (IOException e) {
	    			resultMap.put("msg", "오더 제출에 실패했습니다.(IOException : " + e.getMessage() + ")");
	    		} finally {
	    			try {
	    				if (fileOutput != null) {
	    					fileOutput.close();
	    				}
	    			} catch (IOException e) {
	    				resultMap.put("msg", "오더 제출에 실패했습니다.(fileOutput.close() IOException : " + e.getMessage() + ")");
	    			}
	    		}
	    		
	    	} else {
	    		resultMap.put("msg", "오더 제출에 실패했습니다.(There is no column title.)");
	    	}
	    } else {
	    	resultMap.put("msg", "오더 제출에 실패했습니다.(There is no column data.)");
	    }
		
		return resultMap;
	}

	/**
	 * 컬럼정보 조회
	 * 
	 * @param remarkType Remark유형
	 * @return
	 */
	public List<CaseInsensitiveMap> getColumns(String orderType, String remarkType) {		
		String[][] arrColumnsInfo = getArrColumnsInfo(orderType, remarkType);
		
		List<CaseInsensitiveMap> columnsList = new ArrayList<CaseInsensitiveMap>();
		
		if (arrColumnsInfo != null) {
			for (int i = 0; i < arrColumnsInfo.length; i++) {
				CaseInsensitiveMap columns = new CaseInsensitiveMap();
				columns.put("index_name", arrColumnsInfo[i][0]);
				columns.put("name", arrColumnsInfo[i][1]);
				columns.put("width", arrColumnsInfo[i][2]);
				columnsList.add(columns);
			}
		}
		
		return columnsList;
	}
	
	/**
	 * 컬럼배열 정보 조회
	 * 
	 * @param remarkType Remark유형
	 * @return
	 */
	private String[][] getArrColumnsInfo(String orderType, String remarkType) {
		if ("TR".equals(orderType)) {
			String[][] arrColumnsInfo = {
					{"Item No.*","item_no","97"},
					{"Quantity*","quantity","90"},
					{"Gen. Product Posing Group*","gen_prod_posting_group","143"},
					{"From Location*","from_location","80"},
					{"To Location*","to_location","75"},
					{"Cost Centre Code","cost_centre_code","100"},
					{"Remark","remark","100"},
					{"Requested Delivery Date","delivery_date","120"}
			};
			return arrColumnsInfo;
		} else if ("SO".equals(orderType)) {
			// PM, NL,OT 를 같은 양식으로 처리한다고 함. - 2018.07.18 임성철 과장
//			if ("PM".equals(remarkType)
//					|| "NL".equals(remarkType)
//					|| "SO".equals(remarkType)) {
				String[][] arrColumnsInfo = {
						{"Item No.*","item_no","97"},
						{"Quantity*","quantity","90"},
						{"Gen. Product Posing Group*","gen_prod_posting_group","143"},
						{"Customer Code*","customer_code","128"},
						{"Ship-to Code*","ship_to_code","107"},
						{"Cost Centre Code*","cost_centre_code","100"},
						{"Remark*","remark","100"},
						{"Requested Delivery Date","delivery_date","120"}
				};
				return arrColumnsInfo;
//			} else {
//				return new String[0][0];
//			}
		} else if ("SS".equals(orderType)) {
			String[][] arrColumnsInfo = {
					{"Item No.","item_no","97"},
					{"Quantity","quantity","90"},
					{"Gen. Product Posing Group","gen_prod_posting_group","143"},
					{"Location Code","from_location","128"},
					{"Customer Code","customer_code","128"},
					{"Ship-to Code","ship_to_code","107"},
					{"Cost Centre Code","cost_centre_code","100"},
					{"Staff","staff","100"},
					{"Remark","remark","100"}
			};
			return arrColumnsInfo;
		} else if ("FG".equals(orderType)) {	// 2018.06.11 cremazer
			String[][] arrColumnsInfo = {
					{"Item No.","item_no","97"},
					{"Quantity","quantity","90"},
					{"Gen. Product Posing Group","gen_prod_posting_group","143"},
					{"Location Code","from_location","128"},
					{"Customer Code","customer_code","128"},
					{"Ship-to Code","ship_to_code","107"},
					{"Cost Centre Code","cost_centre_code","100"},
					{"Staff","staff","100"},
					{"Remark","remark","100"}
			};
			return arrColumnsInfo;
		} else {
			return new String[0][0];
		}
	}
	
	/**
	 * 컬럼 타이틀 목록 조회
	 * 
	 * @param orderType Order유형
	 * @param remarkType Remark유형
	 * @return
	 */
	public String[][] getColumnsTitles(String orderType, String remarkType) {
		
			//프로모션
			if ("TR".equals(orderType)) {
				String[][] arrColumnsTitles = {
					{"Item No.*","Quantity*","Gen. Product Posing Group*","From Location*","To Location*","Cost Centre Code","Remark","Requested Delivery Date"},
					{"20 chars","20 chars","20 chars","10 chars","10 chars","20 chars","20 chars","YYYYMMDD"}
				};
				
				return arrColumnsTitles;
			} else if ("SO".equals(orderType)) {
				// PM, NL,OT 를 같은 양식으로 처리한다고 함. - 2018.07.18 임성철 과장
//				if ("PM".equals(remarkType)) {
					String[][] arrColumnsTitles = {
						{"Item No.*","Quantity*","Gen. Product Posing Group*","Customer Code*","Ship-to Code*","Cost Centre Code*","Remark*","Requested Delivery Date"},
						{"20 chars","20 chars","20 chars","10 chars","10 chars","20 chars","20 chars","YYYYMMDD"}
					};
					return arrColumnsTitles;
//				} else {
//					return new String[0][0];
//				}
			//직원세일
			} else if ("SS".equals(orderType)) {
				String[][] arrColumnsTitles = {
						{"Item No.","Quantity","Gen. Product Posing Group","Location Code","Customer Code","Ship-to Code","Cost Centre Code","Staff","Remark"},
						{"20 chars","20 chars","20 chars","10 chars","10 chars","10 chars","20 chars","40 chars","20 chars"}
				};
				return arrColumnsTitles;
			//프리굳
			} else if ("FG".equals(orderType)) {
				String[][] arrColumnsTitles = {
						{"Item No.","Quantity","Gen. Product Posing Group","Location Code","Customer Code","Ship-to Code","Cost Centre Code","Staff","Remark"},
						{"20 chars","20 chars","20 chars","10 chars","10 chars","10 chars","20 chars","40 chars","20 chars"}
				};
				return arrColumnsTitles;
			} else {
				String[][] arrColumnsTitles = {
						{"Item No.*","Quantity*","Gen. Product Posing Group*","Customer Code*","Ship-to Code*","Cost Centre Code*","Remark*","Requested Delivery Date"},
						{"20 chars","20 chars","20 chars","10 chars","10 chars","20 chars","20 chars","YYYYMMDD"}
				};
				
				return arrColumnsTitles;
			}
	}
}
