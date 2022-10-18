package com.dantn.bookStore.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class UserUpdateRequest {
	private Integer id;
	@NotBlank(message = "Không bỏ trống email")
	@Email(message = "Email không đúng định dạng")
	private String email;
	@NotBlank(message = "Không bỏ trống số điện thoại")
	private String phoneNumber;
	@NotBlank(message = "Không bỏ trống tên")
	private String fullname;
	@NotBlank(message = "Không bỏ trống địa chỉ")
	private String address;
	private MultipartFile file;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
