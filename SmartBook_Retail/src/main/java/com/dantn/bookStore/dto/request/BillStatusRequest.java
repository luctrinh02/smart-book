package com.dantn.bookStore.dto.request;

import javax.validation.constraints.NotBlank;

public class BillStatusRequest {
	private Integer status;
	private String message;
	private String date;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
