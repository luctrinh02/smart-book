package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReturnBillDetailPK implements Serializable{
    @Column(name = "book_id",insertable = false,updatable = false)
    private Integer bookId;
    @Column(name = "return_bill_id",insertable = false,updatable = false)
    private Integer returnBillId;
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public Integer getReturnBillId() {
        return returnBillId;
    }
    public void setReturnBillId(Integer returnBillId) {
        this.returnBillId = returnBillId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(bookId, returnBillId);
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
        return Objects.equals(bookId, other.bookId) && Objects.equals(returnBillId, other.returnBillId);
    }
    
}
