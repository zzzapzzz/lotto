package com.lotto.spring.domain.dto;

public class EmailVO {

	/** title (메일제목) */
	private String title;
	/** content (메일내용) */
	private String content;
	/**  (보내는사람 메일주소) */
	private String sender;
	/**  (받는사람 메일주소) */
	private String receiver;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


}
