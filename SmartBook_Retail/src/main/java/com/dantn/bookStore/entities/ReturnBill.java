package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
public class ReturnBill implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private BillStatus status;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdTime;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedTime;
    private String message;
    @JsonIgnore
    @OneToMany(mappedBy = "returnBill")
    private List<ReturnBillDetail> billDetails;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Bill getBill() {
        return bill;
    }
    public void setBill(Bill bill) {
        this.bill = bill;
    }
    public BillStatus getStatus() {
        return status;
    }
    public void setStatus(BillStatus status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    public List<ReturnBillDetail> getBillDetails() {
        return billDetails;
    }
    public void setBillDetails(List<ReturnBillDetail> billDetails) {
        this.billDetails = billDetails;
    }
    
}
