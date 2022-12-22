package com.dantn.bookStore.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class SearchRequest {
	@NotBlank(message =  "Vui lòng nhập từ khoá")
	private String key;
	private Double min;
	private Double max;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	
}
