package com.dantn.bookStore.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ReturnBillDetail implements Serializable{
    @EmbeddedId
    private ReturnBillDetailPK PK;
    @ManyToOne
    @JoinColumn(name = "book_id",insertable = false,updatable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "return_bill_id",insertable = false,updatable = false)
    private ReturnBill returnBill;
    private Long amount;
    public ReturnBillDetailPK getPK() {
        return PK;
    }
    public void setPK(ReturnBillDetailPK pK) {
        PK = pK;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public ReturnBill getReturnBill() {
        return returnBill;
    }
    public void setReturnBill(ReturnBill returnBill) {
        this.returnBill = returnBill;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
}
