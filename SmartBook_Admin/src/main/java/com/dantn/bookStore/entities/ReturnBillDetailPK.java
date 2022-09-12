package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class ReturnBillDetailPK implements Serializable{
	@Column(name = "return_bill_id",insertable = false,updatable = false)
	private Integer returnBillId;
	@Column(name = "bill_id",insertable = false,updatable = false)
	private Integer billId;
	
	public Integer getReturnBillId() {
		return returnBillId;
	}
	public void setReturnBillId(Integer returnBillId) {
		this.returnBillId = returnBillId;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(billId, returnBillId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReturnBillDetailPK other = (ReturnBillDetailPK) obj;
		return Objects.equals(billId, other.billId) && Objects.equals(returnBillId, other.returnBillId);
	}
	
}
