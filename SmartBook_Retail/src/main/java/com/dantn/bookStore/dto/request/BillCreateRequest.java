package com.dantn.bookStore.dto.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.ultilities.PhoneNumerChecking;

public class BillCreateRequest {
	private List<CartPK> cartPKs;
	private BigDecimal transportFee;
	private Integer couponId;
	private Integer transportType;
	private Integer paymentType;
	
	@NotBlank(message = "Không bỏ trống tên người nhận")
	private String fullname;
	@NotBlank(message = "Không bỏ trống số điện thoại")
	@PhoneNumerChecking
	private String phoneNumber;
	@NotBlank(message = "Vui lòng nhập địa chỉ cụ thể")
	private String addressDetail;
	@NotBlank(message = "Vui lòng chọn xã/phường")
	private String ward;
	@NotBlank(message = "Vui lòng chọn tỉnh/thành phố")
	private String city;
	@NotBlank(message = "Vui lòng chọn quận/huyện")
	private String district;

	
	public List<CartPK> getCartPKs() {
		return cartPKs;
	}
	public void setCartPKs(List<CartPK> cartPKs) {
		this.cartPKs = cartPKs;
	}
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getTransportType() {
		return transportType;
	}
	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public Integer getWard() {
		return Integer.parseInt(ward);
	}
	public void setWard(String ward) {
		this.ward = ward;
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
	
	
}
