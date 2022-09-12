package com.dantn.bookStore.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dantn.bookStore.adapter.DtoToEntity;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.ultilities.ConfirmPassword;
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
	@NotBlank(message = "Không bỏ trống tỉnh")
	private String province;
	@NotBlank(message = "Không bỏ trống quận/huyện")
	private String district;
	@NotBlank(message = "Không bỏ trống xã/phường")
	private String comune;
	@NotBlank(message = "Không bỏ trống địa chỉ")
	private String address;
	private Integer role;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	@Override
	public User changeToEntity(User u) {
		u.setAddress(address);
		u.setComune(comune);
		u.setDistrict(district);
		u.setEmail(email);
		u.setFullname(fullname);
		u.setProvince(province);
		u.setStatus(null);
		u.setRole(role==null?null:new UserRole());
		if(id==null) {
			u.setPassword(new BCryptPasswordEncoder().encode(password));
		}
		return u;
	}
	
}
