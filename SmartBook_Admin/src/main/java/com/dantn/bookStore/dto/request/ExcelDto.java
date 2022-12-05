package com.dantn.bookStore.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class ExcelDto {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
