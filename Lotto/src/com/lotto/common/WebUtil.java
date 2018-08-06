package com.lotto.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class WebUtil {

  private WebUtil() {
    super();
  };

  private static String[][] convertStr = { { "document", "docuM" },
      { "cookie", "cooie" }, { "<", "" }, { ">", "" }, { "'", "" },
      { "\"", "" }, { "alert", "alart" } };

  public static String replaceParam(String param) {

    param = param.replace("&lt;", "<");
    param = param.replace("&gt;", ">");
    param = param.replace("leftlt", "<");
    param = param.replace("rightgt", ">");
    param = param.replace("null", "");
    param = param.replace("NULL", "");

    param = param.replace("&#40;", "(");
    param = param.replace("&#41;", ")");
    param = param.replace("&#35;", "#");
    param = param.replace("&#38;", "&");
    
    return param;
  }

  /**
   * 파라미터값 치환
   * 
   * @param inputVal
   *          입력파라미터
   * @param defaultVal
   *          기본값
   * @return 적용파라미터값
   */
  public static String replaceParam(String inputVal, String defaultVal) {

    // 2015.03.26
    // 기존 StringHandler 적용
    String returnVal = inputVal;
    if (returnVal == null || "null".equals(returnVal)
        || "NULL".equals(returnVal) || returnVal.length() < 1) {
      return defaultVal;
    } else {
      returnVal = returnVal.replaceAll(
          "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").trim();
      for (int i = 0; i < convertStr.length; i++) {
        returnVal = returnVal.replaceAll(convertStr[i][0], convertStr[i][1])
            .trim();
      }

      /*
       * 2016.04.09
       *  ③ ( ⇒ &#40; ④ ) ⇒ &#41; ⑤ # ⇒ &#35;  ⑥ &  ⇒ &#38; 추가
       */
      returnVal = returnVal.replace("&#40;", "(");
      returnVal = returnVal.replace("&#41;", ")");
      returnVal = returnVal.replace("&#35;", "#");
      returnVal = returnVal.replace("&#38;", "&");
      
      returnVal = returnVal.replace("&lt;", "<");
      returnVal = returnVal.replace("&gt;", ">");
      returnVal = returnVal.replace("leftlt", "<");
      returnVal = returnVal.replace("rightgt", ">");
      returnVal = returnVal.replace("null", "");
      returnVal = returnVal.replace("NULL", "");

      // SQL 명령어 처리
      if (returnVal.toUpperCase().indexOf("ALTER") > -1) {
        returnVal = returnVal.toUpperCase().replace("ALTER", "");
      }
      if (returnVal.toUpperCase().indexOf("BACKUP") > -1) {
        returnVal = returnVal.toUpperCase().replace("BACKUP", "");
      }
      if (returnVal.toUpperCase().indexOf("CREATE") > -1) {
        returnVal = returnVal.toUpperCase().replace("CREATE", "");
      }
      if (returnVal.toUpperCase().indexOf("COMMIT") > -1) {
        returnVal = returnVal.toUpperCase().replace("COMMIT", "");
      }
      if (returnVal.toUpperCase().indexOf("DATABASE") > -1) {
        returnVal = returnVal.toUpperCase().replace("DATABASE", "");
      }
      if (returnVal.toUpperCase().indexOf("DELETE") > -1) {
        returnVal = returnVal.toUpperCase().replace("DELETE", "");
      }
      if (returnVal.toUpperCase().indexOf("DROP") > -1) {
        returnVal = returnVal.toUpperCase().replace("DROP", "");
      }
      if (returnVal.toUpperCase().indexOf("EXEC") > -1) {
        returnVal = returnVal.toUpperCase().replace("EXEC", "");
      }
      if (returnVal.toUpperCase().indexOf("FETCH") > -1) {
        returnVal = returnVal.toUpperCase().replace("FETCH", "");
      }
      if (returnVal.toUpperCase().indexOf("FROM") > -1) {
        returnVal = returnVal.toUpperCase().replace("FROM", "");
      }
      if (returnVal.toUpperCase().indexOf("INSERT") > -1) {
        returnVal = returnVal.toUpperCase().replace("INSERT", "");
      }
      if (returnVal.toUpperCase().indexOf("ROLLBACK") > -1) {
        returnVal = returnVal.toUpperCase().replace("ROLLBACK", "");
      }
      if (returnVal.toUpperCase().indexOf("SELECT") > -1) {
        returnVal = returnVal.toUpperCase().replace("SELECT", "");
      }
      if (returnVal.toUpperCase().indexOf("SHUTDOWN") > -1) {
        returnVal = returnVal.toUpperCase().replace("SHUTDOWN", "");
      }
      if (returnVal.toUpperCase().indexOf("TABLE") > -1) {
        returnVal = returnVal.toUpperCase().replace("TABLE", "");
      }
      if (returnVal.toUpperCase().indexOf("UPDATE") > -1) {
        returnVal = returnVal.toUpperCase().replace("UPDATE", "");
      }
      if (returnVal.toUpperCase().indexOf("VALUES") > -1) {
        returnVal = returnVal.toUpperCase().replace("VALUES", "");
      }
      if (returnVal.toUpperCase().indexOf("WHERE") > -1) {
        returnVal = returnVal.toUpperCase().replace("WHERE", "");
      }

      // OS 명령어 처리 (Windows)
      if (returnVal.toUpperCase().indexOf("DRWATSON") > -1) {
        returnVal = returnVal.toUpperCase().replace("DRWATSON", "");
      }
      if (returnVal.toUpperCase().indexOf("DRWTSN32") > -1) {
        returnVal = returnVal.toUpperCase().replace("DRWTSN32", "");
      }
      if (returnVal.toUpperCase().indexOf("MSCONFIG") > -1) {
        returnVal = returnVal.toUpperCase().replace("MSCONFIG", "");
      }
//      if (returnVal.toUpperCase().indexOf("SFC") > -1) {
//        returnVal = returnVal.toUpperCase().replace("SFC", "");
//      }
      if (returnVal.toUpperCase().indexOf("REGEDIT") > -1) {
        returnVal = returnVal.toUpperCase().replace("REGEDIT", "");
      }
      if (returnVal.toUpperCase().indexOf("OOBE") > -1) {
        returnVal = returnVal.toUpperCase().replace("OOBE", "");
      }
      if (returnVal.toUpperCase().indexOf("MSOOBE") > -1) {
        returnVal = returnVal.toUpperCase().replace("MSOOBE", "");
      }
      if (returnVal.toUpperCase().indexOf("SYSEDIT") > -1) {
        returnVal = returnVal.toUpperCase().replace("SYSEDIT", "");
      }
      if (returnVal.toUpperCase().indexOf("DXDIAG") > -1) {
        returnVal = returnVal.toUpperCase().replace("DXDIAG", "");
      }
      if (returnVal.toUpperCase().indexOf("MSINFO32") > -1) {
        returnVal = returnVal.toUpperCase().replace("MSINFO32", "");
      }
      if (returnVal.toUpperCase().indexOf("COMPMGMT") > -1) {
        returnVal = returnVal.toUpperCase().replace("COMPMGMT", "");
      }
      if (returnVal.toUpperCase().indexOf("DEVMGMT") > -1) {
        returnVal = returnVal.toUpperCase().replace("DEVMGMT", "");
      }
      if (returnVal.toUpperCase().indexOf("DFRG") > -1) {
        returnVal = returnVal.toUpperCase().replace("DFRG", "");
      }
      if (returnVal.toUpperCase().indexOf("FSMGMT") > -1) {
        returnVal = returnVal.toUpperCase().replace("FSMGMT", "");
      }
//      if (returnVal.toUpperCase().indexOf("CMD") > -1) {
//        returnVal = returnVal.toUpperCase().replace("CMD", "");
//      }
      if (returnVal.toUpperCase().indexOf("DISKMGMT") > -1) {
        returnVal = returnVal.toUpperCase().replace("DISKMGMT", "");
      }
      if (returnVal.toUpperCase().indexOf("FORMAT") > -1) {
        returnVal = returnVal.toUpperCase().replace("FORMAT", "");
      }
      //2016.06.10 cremazer
      //헬프데스트 권한등록으로 인한 주석처리
//      if (returnVal.toUpperCase().indexOf("HELP") > -1) {
//        returnVal = returnVal.toUpperCase().replace("HELP", "");
//      }
//      if (returnVal.toUpperCase().indexOf("DIR") > -1) {
//        returnVal = returnVal.toUpperCase().replace("DIR", "");
//      }
//      if (returnVal.toUpperCase().indexOf("CLS") > -1) {
//        returnVal = returnVal.toUpperCase().replace("CLS", "");
//      }
      if (returnVal.toUpperCase().indexOf("NETSTAT") > -1) {
        returnVal = returnVal.toUpperCase().replace("NETSTAT", "");
      }
      if (returnVal.toUpperCase().indexOf("PING") > -1) {
        returnVal = returnVal.toUpperCase().replace("PING", "");
      }
      if (returnVal.toUpperCase().indexOf("MOVE") > -1) {
        returnVal = returnVal.toUpperCase().replace("MOVE", "");
      }
//      if (returnVal.toUpperCase().indexOf("RD") > -1) {
//        returnVal = returnVal.toUpperCase().replace("RD", "");
//      }
//      if (returnVal.toUpperCase().indexOf("DEL") > -1) {
//        returnVal = returnVal.toUpperCase().replace("DEL", "");
//      }
//      if (returnVal.toUpperCase().indexOf("MD") > -1) {
//        returnVal = returnVal.toUpperCase().replace("MD", "");
//      }
      if (returnVal.toUpperCase().indexOf("COPY") > -1) {
        returnVal = returnVal.toUpperCase().replace("COPY", "");
      }

      return returnVal;
    }

  }
  /**
   * 파라미터값 치환 <br />
   * 2016.4.18 cremazer <br />
   * Response <br />
   *  <br />
   * [SP] 서버전송 사이트 교차접속 스크립트 공격 취약성 <br />
   *  <br />
   * 외부에서 입력되는 검증되지 않은 입력이 동적 웹 페이지의 생성에 사용될 경우, 전송된 동적 웹 페이지를  <br />
   * 열람하는 접속자의 권한으로 부적절한 스크립트가 수행되어 정보 유출 등의 피해를 입힐 수 있습니다.  <br />
   * 외부에서 입력한 문자열을 사용하여 결과 페이지를 생성할 경우 replaceAll 등의 메소드를 사용하여  <br />
   * 위험한 문자열을 제거하여야 합니다. <br />
   *  <br />
   * [참고] <br />
   *  <br />
   * [1] CWE - 80 크로스 사이트 스크립트 공격 취약점(XSS) -  http://cwe.mitre.org/data/definitions/80.html <br />  
   * [2] OWASP Top 10 2010 - (OWASP 2010) A2 Cross-Site Scripting(XSS)  <br />
   * [3] SANS Top 25 2010 - Insecure Interaction Between Components, RANK 1 CWE-79 Improper <br /> 
   * Neutralization of Input During Web Page Generation (''Cross-site Scripting'')  <br />
   *  <br />
   * Ex) BadCode <br /> 
   * // 외부로 부터 이름을 받음 <br />  
   * String name = request.getParameter("name"); <br />
   * //외부로 부터 받은 name이 그대로 출력 <br />
   * <p>NAME:<%=name%></p>   <br />
   *  <br />
   * Ex) GoodCode <br /> 
   * // 외부로 부터 이름을 받음 <br />  
   * String name = request.getParameter("name"); <br /> 
   * // 외부의 입력값에 대한 검정을 한다. <br />
   * if ( name != null  ) { <br />
   *   name = name.replaceAll("<","&lt;"); <br />
   *   name = name.replaceAll(">","&gt;"); <br />
   * }else { <br />
   *   return; <br /> 
   * }  <br />
   *  <br />
   *  // 외부로 부터 받은 name  출력 <br /> 
   * <p>NAME:<%=name%></p> <br />
   *  <br />
   * @param inputVal
   *          입력파라미터
   * @param defaultVal
   *          기본값
   * @return 적용파라미터값
   */
  public static String replaceResParam(String inputVal, String defaultVal) {
	  
	  // 2015.03.26
	  // 기존 StringHandler 적용
	  String returnVal = inputVal;
	  if (returnVal == null || "null".equals(returnVal)
			  || "NULL".equals(returnVal) || returnVal.length() < 1) {
		  return defaultVal;
	  } else {
		  
		  returnVal = returnVal.replace("(", "&#40;");
		  returnVal = returnVal.replace(")", "&#41;");
//		  returnVal = returnVal.replace("#", "&#35;");
//		  returnVal = returnVal.replace("&", "&#38;");
		  returnVal = returnVal.replace("<", "&lt;");
		  returnVal = returnVal.replace(">", "&gt;");
		  
		  
		  return returnVal;
	  }
	  
  }

  	/**
  	 * 통화시간을 TimeSet으로 변환하기
  	 * (문자형숫자를 00:00:00 형태로 변환)
  	 * 
  	 * @param strDuration 통화시간
  	 * @return 통화시간 TimeSet
  	 */
	public static String convertDurationToTimeSet(String strDuration) {

		int iHh = 0;
		int iMM = 0;
		int iSs = 0;

		String strHh = "";
		String strMM = "";
		String strSs = "";
		
		iHh = (int) Math.floor(Integer.parseInt(strDuration) / 3600);
		iMM = (int) Math.floor((Integer.parseInt(strDuration) % 3600) / 60);
		iSs = (int) (Integer.parseInt(strDuration) % 3600) % 60;
      
		if (iHh < 10) {
			strHh = WebUtil.stringAppend3("0", Integer.toString(iHh), ":");
		} else {
			strHh = Integer.toString(iHh) + ":";
		}	
		if (iMM < 10) {
			strMM = WebUtil.stringAppend3("0", Integer.toString(iMM), ":");
		} else {
			strMM = Integer.toString(iMM) + ":";
		}	
		if (iSs < 10) {
			strSs = "0" + Integer.toString(iSs);
		} else {
			strSs = Integer.toString(iSs);
		}	
		      
		return WebUtil.stringAppend3(strHh, strMM, strSs);
	}

	/**
	 * 녹음길이 코드를 통화시간으로 변환하기
	 * 
	 * @param isStart 녹음시작길이여부 
	 * @param durCode 녹음길이코드
	 * @return 통화시간
	 */
	public static String convertCodeToDuration(boolean isStart, String durCode) {
		int code = Integer.parseInt(durCode);
		String duration = "";
		switch (code) {
		case 30*0:
			if (isStart) {
				duration = "000000";
			} else {
				duration = "000029";
			}
			break;
		case 30*1:
			if (isStart) {
				duration = "000030";
			} else {
				duration = "000029";
			}
			break;
		case 30*2:
			if (isStart) {
				duration = "000100";
			} else {
				duration = "000059";
			}
			break;
		case 30*3:
			if (isStart) {
				duration = "000130";
			} else {
				duration = "000129";
			}
			break;
		case 30*4:
			if (isStart) {
				duration = "000200";
			} else {
				duration = "000159";
			}
			break;
		case 30*5:
			if (isStart) {
				duration = "000230";
			} else {
				duration = "000229";
			}
			break;
		case 30*6:
			if (isStart) {
				duration = "000300";
			} else {
				duration = "000259";
			}
			break;
		case 30*7:
			if (isStart) {
				duration = "000330";
			} else {
				duration = "000329";
			}
			break;
		case 30*8:
			if (isStart) {
				duration = "000400";
			} else {
				duration = "000359";
			}
			break;
		case 30*9:
			if (isStart) {
				duration = "000430";
			} else {
				duration = "000429";
			}
			break;
		case 30*10:
			if (isStart) {
				duration = "000500";
			} else {
				duration = "000459";
			}
			break;
		case 30*11:
			if (isStart) {
				duration = "000530";
			} else {
				duration = "000529";
			}
			break;
		case 30*12:
			if (isStart) {
				duration = "000600";
			} else {
				duration = "000559";
			}
			break;
		case 30*13:
			if (isStart) {
				duration = "000630";
			} else {
				duration = "000629";
			}
			break;
		case 30*14:
			if (isStart) {
				duration = "000700";
			} else {
				duration = "000659";
			}
			break;
		case 30*15:
			if (isStart) {
				duration = "000730";
			} else {
				duration = "000729";
			}
			break;
		case 30*16:
			if (isStart) {
				duration = "000800";
			} else {
				duration = "000759";
			}
			break;
		case 30*17:
			if (isStart) {
				duration = "000830";
			} else {
				duration = "000829";
			}
			break;
		case 30*18:
			if (isStart) {
				duration = "000900";
			} else {
				duration = "000859";
			}
			break;
		case 30*19:
			if (isStart) {
				duration = "000930";
			} else {
				duration = "000929";
			}
			break;
		case 30*20:
			if (isStart) {
				duration = "001000";
			} else {
				duration = "000959";
			}
			break;
			
		default:
			//10분 이상일 경우
			if (isStart) {
				duration = "001000";
			} else {
				duration = "235959";
			}
			break;
		}
		
		return duration;
	}

  public static int getEncodedLength(String s) {
    if (s == null) {
      throw new IllegalArgumentException(
          "Cannot calculate UTF-8 length of null string");
    }

    int result = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if ((c >= 0x0001) && (c <= 0x007F)) {
        result++;
      } else if (c > 0x07FF) {
        result += 3;
      } else {
        result += 2;
      }
    }
    return result;
  }

  public static String stringAppend2(String param1, String param2) {

    StringBuffer strAppend = new StringBuffer();

    if (param1 == null) {
      return null;
    } else {

      strAppend.append(param1);
      strAppend.append(param2);

    }
    return strAppend.toString();
  }

  public static String stringAppend3(String param1, String param2, String param3) {

    StringBuffer strAppend = new StringBuffer();

    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
    }
    return strAppend.toString();
  }

  public static String stringAppend4(String param1, String param2,
      String param3, String param4) {
    StringBuffer strAppend = new StringBuffer();
    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
      strAppend.append(param4);
    }
    return strAppend.toString();
  }

  public static String stringAppend5(String param1, String param2,
      String param3, String param4, String param5) {
    StringBuffer strAppend = new StringBuffer();
    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
      strAppend.append(param4);
      strAppend.append(param5);
    }
    return strAppend.toString();
  }

  public static String stringAppend6(String param1, String param2,
      String param3, String param4, String param5, String param6) {
    StringBuffer strAppend = new StringBuffer();
    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
      strAppend.append(param4);
      strAppend.append(param5);
      strAppend.append(param6);
    }
    return strAppend.toString();
  }

  public static String stringAppend7(String param1, String param2,
      String param3, String param4, String param5, String param6, String param7) {
    StringBuffer strAppend = new StringBuffer();
    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
      strAppend.append(param4);
      strAppend.append(param5);
      strAppend.append(param6);
      strAppend.append(param7);
    }
    return strAppend.toString();
  }

  public static String stringAppend9(String param1, String param2,
      String param3, String param4, String param5, String param6,
      String param7, String param8, String param9) {
    StringBuffer strAppend = new StringBuffer();
    if (param1 == null) {
      return null;
    } else {
      strAppend.append(param1);
      strAppend.append(param2);
      strAppend.append(param3);
      strAppend.append(param4);
      strAppend.append(param5);
      strAppend.append(param6);
      strAppend.append(param7);
      strAppend.append(param8);
      strAppend.append(param9);
    }
    return strAppend.toString();
  }
  
  /**
   * <b>날짜 가져오기</b><br />
   * <br />
   * SimpleDateFormat pattern example<br />
   * 	yyyy-MM-dd 1969-12-31<br />
   *    yyyy-MM-dd 1970-01-01<br />
   *    yyyy-MM-dd HH:mm 1969-12-31 16:00<br />
   *    yyyy-MM-dd HH:mm 1970-01-01 00:00<br />
   *    yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800<br />
   *    yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000<br />
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800<br />
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000<br />
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800<br />
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000<br />
   *    
   * @param recDate 기준날짜, "" 일시 오늘 날짜
   * @param difDay 일 수(-:과거,+:미래)
   * @param pattern 형식 (예:yyyy-MM-dd)
   * @return 계산된 날짜
   */
  public static String getDay(String recDate, int difDay, String pattern) {

    int year = 0;
    int month = 0;
    int day = 0;
    
    Calendar cal = Calendar.getInstance();
    
    if ("".equals(recDate)) {
    	//현재 날짜 설정
    	year = cal.get(Calendar.YEAR);
    	month = cal.get(Calendar.MONTH) + 1;
    	day = cal.get(Calendar.DAY_OF_MONTH);
    } else {
    	//기준 날짜 설정
    	year = Integer.parseInt(recDate.substring(0, 4));
    	month = Integer.parseInt(recDate.substring(4, 6));
    	day = Integer.parseInt(recDate.substring(6, 8));
    	cal.set(year, month - 1, day);
    }

    //날짜 차이 설정
    if (difDay != 0) {
    	cal.add(Calendar.DAY_OF_MONTH, difDay);
    }

    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    return sdf.format(cal.getTime());
  }
  
  /**
   * 날짜 가져오기
   * 
   * SimpleDateFormat pattern example
   * 	yyyy-MM-dd 1969-12-31
   *    yyyy-MM-dd 1970-01-01
   *    yyyy-MM-dd HH:mm 1969-12-31 16:00
   *    yyyy-MM-dd HH:mm 1970-01-01 00:00
   *    yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
   *    yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
   *    
   * @param recDate 기준날짜, "" 일시 오늘 날짜
   * @param val 일 수(-:과거,+:미래)
   * @param ymdType 년월일 구분 (D:일,M:월,Y:년)
   * @param pattern 형식 (예:yyyy-MM-dd)
   * @return 계산된 날짜
   */
  public static String getDate(String recDate, int val, String ymdType, String pattern) {
	  
	  int year = 0;
	  int month = 0;
	  int day = 0;
	  
	  Calendar cal = Calendar.getInstance();
	  
	  if ("".equals(recDate)) {
		  //현재 날짜 설정
		  year = cal.get(Calendar.YEAR);
		  month = cal.get(Calendar.MONTH) + 1;
		  day = cal.get(Calendar.DAY_OF_MONTH);
	  } else {
		  //기준 날짜 설정
		  year = Integer.parseInt(recDate.substring(0, 4));
		  month = Integer.parseInt(recDate.substring(4, 6));
		  day = Integer.parseInt(recDate.substring(6, 8));
		  cal.set(year, month - 1, day);
	  }
	  
	  //날짜 차이 설정
	  if (val != 0) {
		  if ("D".equals(ymdType)) {
			  cal.add(Calendar.DAY_OF_MONTH, val);
		  } else if ("M".equals(ymdType)) {
			  cal.add(Calendar.MONTH, val);
		  } else if ("Y".equals(ymdType)) {
			  cal.add(Calendar.YEAR, val);
		  } else {
			  cal.add(Calendar.DAY_OF_MONTH, val);
		  }
	  }
	  
	  SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	  
	  return sdf.format(cal.getTime());
  }

  /**
   * 시간 더하기
   * 
   * SimpleDateFormat pattern example
   * 	yyyy-MM-dd 1969-12-31
   *    yyyy-MM-dd 1970-01-01
   *    yyyy-MM-dd HH:mm 1969-12-31 16:00
   *    yyyy-MM-dd HH:mm 1970-01-01 00:00
   *    yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
   *    yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
   *    yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
   *    yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
   *
   * @param startTime 시작시간 (yyyyMMddHHmmss)
   * @param second 증가 초
   * @return
   */
  public static String addTime(String startTime, int second) {
	
	  int year = 0;
	  int month = 0;
	  int day = 0;
	  int hour = 0;
	  int min = 0;
	  int sec = 0;
	  
	  Calendar cal = Calendar.getInstance();
	  
  	  //기준 날짜 설정
  	  year = Integer.parseInt(startTime.substring(0, 4));
  	  month = Integer.parseInt(startTime.substring(4, 6));
  	  day = Integer.parseInt(startTime.substring(6, 8));
  	  hour = Integer.parseInt(startTime.substring(8, 10));
  	  min = Integer.parseInt(startTime.substring(10, 12));
  	  sec = Integer.parseInt(startTime.substring(12, 14));
  	  
  	  //시간 설정
  	  cal.set(year, month - 1, day, hour, min + (second/60), sec + (second%60) );
      
  	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	  return sdf.format(cal.getTime());
  }
  
  public static String getSHA256(String str) {
      String rtnSHA = "";

      try{
          MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
          sh.update(str.getBytes()); 
          byte byteData[] = sh.digest();
          StringBuffer sb = new StringBuffer(); 

          for(int i = 0 ; i < byteData.length ; i++){
              sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
          }
          rtnSHA = sb.toString();
      }catch(NoSuchAlgorithmException e){
          e.printStackTrace(); 
          rtnSHA = null; 
      }
      
      return rtnSHA;
  } 

  /**
   * 마스킹 처리
   * 
   * @param data 마스킹 대상
   * @param type 처리유형<br /><ul><li>TLNO : 0101234****</li><li>TLNO_M : 010****5678</li><li>RSNO : 820618*******</li></ul>
   * @return
   */
  public static String getMaskingData(String data, String type) {
	  String returnVal = "";
	  if (data != null) {
		  	data = data.replace("-", "");
	  }
	  
	  if ("TLNO".equals(type)) {
		  	if (data != null && data.length() > 7) {
		  		returnVal = data.substring(0, data.length()-4)+"****";
		  	}
	  } else if ("TLNO_M".equals(type)) {
		  //가운데 번호 마스킹 처리
		  if (data != null && data.length() > 7) {
			  returnVal = data.substring(0, 3)+"****"+data.substring(7, data.length());
		  }
	  } else if ("RSNO".equals(type)) {
		  	if (data.length() == 13) {
			  returnVal = data.substring(0, data.length()-7)+"*******";
			 }
	  } else if ("RSNO_FILENM".equals(type)) {
		  
		  //2016.06.24 cremazer
		  String strTarget = data.substring(data.lastIndexOf("/")+1, data.length());		
		  if (strTarget.length() > 22 ) {
		  	  int idx = checkJuminIndex(data);
		  	  if (idx > 0) {
		  	  	returnVal = data.substring(0,idx) + data.substring(idx, 13 + idx -7) + "*******" + data.substring(13 + idx, data.length());
		  	  } else {
		  	  	returnVal = data;
		  	  }
		  } else {
		  	  returnVal = data;
		  }
		
	  }
	  
	  return returnVal;
  }
  
  	/**
  	 * 주민번호 시작지점 찾기
  	 * 2016.06.13 cremazer
  	 * 
  	 * 2016.06.24 cremazer
  	 * 	checkCnt 처리로직 수정
  	 * 
  	 * @param data
  	 * @return
  	 */
	public static int checkJuminIndex(String data) {
		int index = 0;
		for (int i = 0; i < data.length() - 13; i++) {
			String str = data.substring(i, i + 13);
			
			int checkCnt = 0;
			for (int j = 0; j < str.length(); j++) {
				if (isNumber(str)) {
					checkCnt++;					
				}
			}
			
			if (checkCnt == 13) {
				index = i;
				break;
			}
		}

		return index;
	}
	
	/**
	 * 숫자여부 판단하기
	 * 2016.06.13 cremazer
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}

		Pattern p = Pattern.compile("([\\p{Digit}]+)(([.]?)([\\]{digit}]+))?");
		Matcher m = p.matcher(str);
		return m.matches();
	}
  
  /**
   * 사용자 접속 IP 조회
   * 2016.04.11 cremazer
   * 
   * @param request
   * @param response
   * @return
   */
  public static String getUser_IP(HttpServletRequest request, HttpServletResponse response) {
	  String ip = request.getHeader("X-Forwarded-For");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("Proxy-Client-IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("WL-Proxy-Client-IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_CLIENT_IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getRemoteAddr(); 
      }

		return ip;
	}

  /**
   * data Null 처리
   * 2016.05.03 cremazer
   * 
   * @param data
   * @param initVal
   * @return
   */
  public static String isNull(String data, String initVal) {
	  String returnVal = "";
	  if (data == null || "".equals(data) || "NULL".equals(data.toUpperCase())) {
		  returnVal = "";
	  } else {
		  returnVal = data;
	  }
	  
	  return returnVal;
  }
  
  /**
   * Null 여부 확인 <br />
   * 2016.06.01 cremazer <br />
   * 
   * 	[SP] 널포인터 역참조 <br />
   *	널 포인터 역참조는 ''일반적으로 그 객체가 NULL이 될 수 없다''라고 하는 가정을 위반했을 때 발생합니다. <br /> 
   *	공격자가 의도적으로 NULL 포인터 역참조를 실행하는 경우, 그 결과 발생하는 예외 사항을 이용하여  <br />
   *	추후의 공격을 계획하는 데 사용될 수 있습니다.  <br />
   *	따라서, Null이 될 수 있는 reference는 참조하기 전에 반드시 null 인지 검사하여 안전한 경우에만 사용해야 합니다. <br />
   *	 <br />
   *	[참고] <br />
   *	 <br />
   *	[1] CWE - 476 널포인터 역참조 - http://cwe.mitre.org/data/definitions/476.html <br />
   *	 <br />
   *	Ex) BadCode <br />
   *	String cmd = System.getProperty("cmd"); <br />
   *	// cmd가 null인지 체크하지 않았다. <br />
   *	cmd = cmd.trim(); <br />
   *	 <br />
   *	Ex) GoodCode <br />
   *	String cmd = System.getProperty("cmd"); <br />
   *	// cmd가 null인지 체크하여야 한다. <br />
   *	if (cmd != null) { <br />
   *		cmd = cmd.trim(); <br />
   *  <br />
   * @param data
   * @return
   */
  public static boolean isNull(Object data) {
	  boolean result = false;
	  if (data == null) {
		  result = true;
	  } 
	  return result;
  }
  
  /**
   * CRLF 제거 <br />
   * 2016.06.01 cremazer <br />
   *  <br />
   * [SP] HTTP 응답 분할 <br />
   *  <br />
   * HTTP 요청에 들어 있는 인자 값이 HTTP 응답 헤더에 포함되어 사용자에게 다시 전달되는 경우, <br /> 
   * 입력 값에 CR(Carriage Return)이나 LF(Line Feed)와 같은 개행 문자가 존재하면,  <br />
   * HTTP 응답이 2개 이상으로 분리될 수 있습니다. 이 경우 공격자는 개행 문자를 이용하여 첫 번째 응답을 종료시키고 <br /> 
   * 두 번째 응답에 악의적인 코드를 주입할 수 있고 이를 이용해서 XSS 및 캐시 훼손 (cache poisoning) 공격을  <br />
   * 시도할 수 있습니다.  <br />
   * 외부에서 입력된 인자값을 사용하여 클라이언트에게 반환되는 HTTP 헤더 값을 생성할 경우 RF, LF등이 제거되어야 합니다. <br />
   *  <br />
   * [참고] <br />
   *  <br />
   * [1] CWE-113 HTTP 응답분할 - http://cwe.mitre.org/data/definitions/113.html <br />
   * [2] OWASP Top 10 2004 A1 Unvalidated Input <br />
   * [3] OWASP Top 10 2007 A2 Injection Flaws <br />
   * [4] Web Application Security Consortium 24 + 2 HTTP Response Splitting <br />
   *  <br />
   * Ex) BadCode <br /> 
   * ...  <br />
   * // 외부로부터 AUTHOR_PARAM을 입력 받는다. <br /> 
   *                 String author = request.getParameter(AUTHOR_PARAM); <br /> 
   *                 ...  <br />
   * // 쿠키를 생성한다. 쿠키 생성시 외부 입력데이터를 쿠키에 저장한다. <br /> 
   *                 Cookie cookie = new Cookie("author", author);  <br />
   *                 cookie.setMaxAge(cookieExpiration);  <br />
   * // 외부 출력으로 cookie를 보낸다.  <br />
   *                 response.addCookie(cookie); <br /> 
   * ...  <br />
   * <br /> 
   * Ex) GoodCode <br /> 
   * ... <br /> 
   *                 String author = request.getParameter(AUTHOR_PARAM); <br /> 
   * // 외부 입력값에 CRLF를 제거한다.                ...  <br />
   *                 author = author.replaceAll("\n",");  <br />
   *                 author = author.replaceAll("\r",");  <br />
   *                 Cookie cookie = new Cookie("author", author); <br /> 
   *                 cookie.setMaxAge(cookieExpiration);  <br />
   *                 response.addCookie(cookie); 
   *                 ...  <br />
   *  <br />
   * @param data
   * @return
   */
  public static String replaceCrLf(String data) {
	  data = data.replaceAll("\n",""); 
	  data = data.replaceAll("\r","");
	  return data;
  }
  
  /**
	 * 시간을 초단위로 변환 (예: "102930" -> 37770)
	 * 2016.06.09 cremazer
	 * 
	 * @param time
	 *          시간(6자리)
	 * @return 초
	 */
	public static int convertTimeToSeconds(String time) {
		String hour = time.substring(0, 2); // 시
		String min = time.substring(2, 4); // 분
		String sec = time.substring(4, 6); // 초

		return Integer.parseInt(sec) + (Integer.parseInt(min) * 60) + (Integer.parseInt(hour) * 60 * 60);
	}
	
	/**
	 * 만기일 초과 여부
	 * 
	 * @param dueDate
	 * @return true:초과, false:미초과
	 */
	public static boolean isOverDueDate (String dueDate) {
		boolean result = false;
		
		//dueDate가 크면, 만기일이 지나지 않음.(미초과)
		if (!dateCompare(dueDate, "")) {
			result = true;
		} 
		
		return result;
		
	}
	
	
	/**
	 * 두 날짜 비교
	 * 
	 * @param date1
	 *            (YYYYMMDD)
	 * @param date2
	 *            (YYYYMMDD)
	 * @return
	 */
	public static boolean dateCompare(String firstDate, String secondDate) {
		boolean result = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		if (firstDate != null && !"".equals(firstDate)) {
			
			// YYYYMMDD 형식으로 설정
			String date1 = firstDate.replaceAll("-", "");
			date1 = date1.replaceAll("/", "");
			
			// 기준 날짜가 지정되어 있다면 입력된 날짜로 설정한다.
			int year = Integer.parseInt(date1.substring(0, 4));
			int month = Integer.parseInt(date1.substring(4, 6));
			int day = Integer.parseInt(date1.substring(6, 8));

			cal1.set(year, month - 1, day);
		}

		if (secondDate != null && !"".equals(secondDate)) {
			
			String date2 = secondDate.replaceAll("-", "");
			date2 = date2.replaceAll("/", "");
			
			// 비교할 대상의 날짜가 지정되어 있다면 입력된 날짜로 설정한다.
			int year = Integer.parseInt(date2.substring(0, 4));
			int month = Integer.parseInt(date2.substring(4, 6));
			int day = Integer.parseInt(date2.substring(6, 8));

			cal2.set(year, month - 1, day);

		}

//		System.out.println(cal1.compareTo(cal2));
		
		if (cal1.compareTo(cal2) > 0) {
			// 비교값이 0보다 크면, 앞의 날짜가 더 크다
			result = true;
		}

		return result;
	}
	
	/**
	 * 만료일 계산
	 * 
	 * @param listen_period_cd 청취기간코드
	 * @return String[] result [0 : 승인일(YYYYMMDDHHMISS), 1 : 만료일(YYYYMMDDHHMISS)]
	 */
	public static String[] getExpireDtm(String listen_period_cd) {
		
		String[] result = new String[2]; 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		
		Calendar curCal = Calendar.getInstance();
		
		result[0] = sdf.format(curCal.getTime());	//승인일	
		result[1] = "";	//만료일
		
		//청취 기간이 "기타"일 경우, 만료일 계산하지 않음.
		if (!"999".equals(listen_period_cd)) {
			int val = 0;
			Calendar cal = Calendar.getInstance();
			
			if (listen_period_cd.length() == 3) { 
				val = Integer.parseInt(listen_period_cd.substring(1, listen_period_cd.length()));
				
				if (listen_period_cd.startsWith("1")) {
					//분
					cal.add(Calendar.MINUTE, val);
				} else if (listen_period_cd.startsWith("2")) {
					//시간
					cal.add(Calendar.HOUR_OF_DAY, val);
				} else if (listen_period_cd.startsWith("3")) {
					//일
					cal.add(Calendar.DAY_OF_MONTH, val);
				} else if (listen_period_cd.startsWith("4")) {
					//주
					cal.add(Calendar.DAY_OF_MONTH, val*7);
				} else if (listen_period_cd.startsWith("5")) {
					//달
					cal.add(Calendar.MONTH, val);
				} else if (listen_period_cd.startsWith("6")) {
					//년
					cal.add(Calendar.YEAR, val);
				}
				
			} else if (listen_period_cd.length() == 4) {
				val = Integer.parseInt(listen_period_cd.substring(0, 3));
				String ymdType = listen_period_cd.substring(3, 4);
				
				if ("D".equals(ymdType)) {
					//일
					cal.add(Calendar.DAY_OF_MONTH, val);
				} else if ("M".equals(ymdType)) {
					//달
					cal.add(Calendar.MONTH, val);
				} else if ("Y".equals(ymdType)) {
					//년
					cal.add(Calendar.YEAR, val);
				}
				
			}
			
			result[1] = sdf.format(cal.getTime());	//만료일
		}
		
		return result;
	}
	
	/**
	 * 제한일자를 초과했는지 체크
	 * 2016.06.23 cremazer
	 * 
	 * @param startDate	시작일자
	 * @param endDate	종료일자
	 * @param limitDay	제한일수
	 * @return	초과여부 (true:초과,false:미초과)
	 */
//	public static boolean isOverLimitDays (String startDate, String endDate, int limitDay) {
//		
//		boolean result = false;
//		
//		Calendar startCal = Calendar.getInstance();
//		  
//		int startYear = Integer.parseInt(startDate.substring(0, 4));
//		int startMonth = Integer.parseInt(startDate.substring(4, 6));
//		int startDay = Integer.parseInt(startDate.substring(6, 8));
//		
//		startCal.set(startYear, startMonth - 1, startDay);
//		
//		
//		Calendar endCal = Calendar.getInstance();
//		
//		int endYear = Integer.parseInt(endDate.substring(0, 4));
//		int endMonth = Integer.parseInt(endDate.substring(4, 6));
//		int endDay = Integer.parseInt(endDate.substring(6, 8));
//		
//		endCal.set(endYear, endMonth - 1, endDay);
//		
//		long bring = endCal.getTimeInMillis() - startCal.getTimeInMillis();
//		long days = (bring/(60*60*24*1000));
//		int day = (int)days;
//		
//		if (day > limitDay) {
//		 result = true;
//		} 
//		
//		return result;
//		 
//	}
}
