package com.lotto.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 



import javax.servlet.http.HttpServletRequest;
 



import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@Component("fileUtils")
public class FileUtils {
    private static final String filePath = "C:\\smlotto\\temp\\";
     
    public static List<Map<String,Object>> getFileInfo(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
         
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
                multipartFile.transferTo(file);
                 
                listMap = new HashMap<String,Object>();
                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
                listMap.put("STORED_FILE_NAME", storedFileName);
                listMap.put("FILE_SIZE", multipartFile.getSize());
                list.add(listMap);
            }
        }
        return list;
    }

	public static int getRequestFileCnt(HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	    int requestFileCnt = 0;
	    MultipartFile multipartFile = null;
	    while(iterator.hasNext()){
	    	requestFileCnt++;
	    	iterator.next();
	    }
		return requestFileCnt;
	}

	public static String getFileExt(HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	    MultipartFile multipartFile = null;
	    String fileExt = "";
	    while(iterator.hasNext()){
	    	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
	    }
	    
	    if(multipartFile.isEmpty() == false){
    		System.out.println("------------- file start -------------");
    		System.out.println("name : "+multipartFile.getName());
    		System.out.println("filename : "+multipartFile.getOriginalFilename());
    		System.out.println("size : "+multipartFile.getSize());
    		System.out.println("-------------- file end --------------\n");
    		
    		String fileName = multipartFile.getOriginalFilename();
    		fileExt = fileName.substring(fileName.indexOf("."));
	    }
		return fileExt;
	}
}