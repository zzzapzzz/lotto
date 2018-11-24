package com.lotto.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Mandatory Class
 * DB유효성 검증을 위한 클래스
 * unuse.
 * 
 * @since 2018.06.13
 * @author cremazer
 *
 */
public class Mandatory {
	private Map mandadoryDto;

	public Mandatory(String dtoType) {
		if ("SSUser".equals(dtoType)) {
			this.mandadoryDto = new HashMap();
			
		}
	}
	
	public Map getMandadoryDto() {
		return mandadoryDto;
	}

//	public void setMandadoryDto(Map mandadoryDto) {
//		this.mandadoryDto = mandadoryDto;
//	}
}
