package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetailPK implements Serializable{
	@Column(name = "book_id",insertable = false,updatable = false)
	private Integer bookId;
	@Column(name = "bill_id",insertable = false,updatable = false)
	private Integer billId;
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(billId, bookId);
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
		return Objects.equals(billId, other.billId) && Objects.equals(bookId, other.bookId);
	}
	
}
