package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetailPK implements Serializable{
	@Column(name = "user_id",insertable = false,updatable = false)
	private Integer userId;
	@Column(name = "bill_id",insertable = false,updatable = false)
	private Integer billId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(billId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillDetailPK other = (BillDetailPK) obj;
		return Objects.equals(billId, other.billId) && Objects.equals(userId, other.userId);
	}
	
}
