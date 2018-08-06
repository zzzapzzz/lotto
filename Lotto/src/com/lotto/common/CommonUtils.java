package com.lotto.common;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
     
    public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
	 * 숫자 체크
	 * 
	 * @param str
	 * @return true: 숫자, false: 숫자아님
	 */
    public static boolean isNumber(String str){
        
        String expr = "^[-+]?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][-+]?[0-9]+)?$";
        Pattern pattern = Pattern.compile(expr);
        Matcher matcher = pattern.matcher(str);
        
        return matcher.matches(); //일치하면 true, 일치하지 않으면 false
    }

    /**
     * 목록을 읽을 수 있는지 확인
     * 
     * @param list 대상목록
     * @return true : 읽을 수 있음. false : 읽을 수 없음.
     */
    public static boolean canReadList(List list){
    	return (list != null && list.size() > 0) ? true : false;
    }
    
    /**
     * 배열을 읽을 수 있는지 확인
     * 
     * @param arr 대상배열 (2차원)
     * @return true : 읽을 수 있음. false : 읽을 수 없음.
     */
    public static boolean canReadList(String[][] arr){
    	return (arr != null && arr.length > 0) ? true : false;
    }
}