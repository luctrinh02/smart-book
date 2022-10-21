package com.dantn.bookStore.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.dantn.bookStore.entities.CartPK;

public class BillCreateRequest {
	private List<CartPK> cartPKs;
	private BigDecimal transportFee;
	private Integer couponId;
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
	
}
