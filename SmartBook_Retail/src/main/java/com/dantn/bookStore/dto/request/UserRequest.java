package com.dantn.bookStore.dto.request;


import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.dantn.bookStore.adapter.DtoToEntity;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.ultilities.ConfirmPassword;
import com.dantn.bookStore.ultilities.PhoneNumerChecking;
@ConfirmPassword
public class UserRequest implements DtoToEntity<User>{

	private Integer id;
	@NotBlank(message = "Không bỏ trống email")
	@Email(message = "Email không đúng định dạng")
	private String email;
	@NotBlank(message = "Không bỏ trống mật khẩu")
	private String password;
	private String confirm;
	@NotBlank(message = "Không bỏ trống tên")
	private String fullname;
	@NotBlank(message = "Không bỏ trống địa chỉ cụ thể")
	private String address;
	@PhoneNumerChecking
	private String phoneNumber;
	private Integer role;
	private MultipartFile file;
	@NotBlank(message = "Vui lòng chọn xã/phường")
	private String ward;
	@NotBlank(message = "Vui lòng chọn tỉnh/thành phố")
	private String city;
	@NotBlank(message = "Vui lòng chọn quận/huyện")
	private String district;
	private String base64;
	
	
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getWard() {
		return Integer.parseInt(ward);
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
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
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public User changeToEntity(User u) {
		u.setAddress(address);
		u.setEmail(email);
		u.setFullname(fullname);
		u.setPhoneNumber(phoneNumber);
		if(u.getId()==null) {
			u.setPassword(new BCryptPasswordEncoder().encode(password));
		}
		return u;
	}
	
}
