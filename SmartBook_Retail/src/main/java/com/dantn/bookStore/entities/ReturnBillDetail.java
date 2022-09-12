package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table
public class ReturnBillDetail implements Serializable{
	@EmbeddedId
	private ReturnBillDetailPK returnBillDetailPK;
	@ManyToOne
	@JoinColumn(name = "return_bill_id",insertable = false,updatable = false)
	private ReturnBill returnBill;
	@ManyToOne
	@JoinColumn(name = "book_id",insertable = false,updatable = false)
	private Book book;
	private BigDecimal price;
	private Long amount;
	public ReturnBillDetailPK getReturnBillDetailPK() {
		return returnBillDetailPK;
	}
	public void setReturnBillDetailPK(ReturnBillDetailPK returnBillDetailPK) {
		this.returnBillDetailPK = returnBillDetailPK;
	}
	public ReturnBill getReturnBill() {
		return returnBill;
	}
	public void setReturnBill(ReturnBill returnBill) {
		this.returnBill = returnBill;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
