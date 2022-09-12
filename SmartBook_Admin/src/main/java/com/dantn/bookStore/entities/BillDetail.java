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
public class BillDetail implements Serializable{
	@EmbeddedId
	private BillDetailPK billDetailPK;
	@ManyToOne
	@JoinColumn(name = "bill_id",insertable = false,updatable = false)
	private Bill bill;
	@ManyToOne
	@JoinColumn(name = "book_id",insertable = false,updatable = false)
	private Book book;
	private BigDecimal price;
	private Long amount;
	public BillDetailPK getBillDetailPK() {
		return billDetailPK;
	}
	public void setBillDetailPK(BillDetailPK billDetailPK) {
		this.billDetailPK = billDetailPK;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
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
