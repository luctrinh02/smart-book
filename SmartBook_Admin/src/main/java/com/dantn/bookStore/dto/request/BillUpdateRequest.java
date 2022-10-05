package com.dantn.bookStore.dto.request;

public class BillUpdateRequest {
	private Integer id;
	private String message;
	private Integer statusIndex;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusIndex() {
		return statusIndex;
	}
	public void setStatusIndex(Integer statusIndex) {
		this.statusIndex = statusIndex;
	}
	
}
