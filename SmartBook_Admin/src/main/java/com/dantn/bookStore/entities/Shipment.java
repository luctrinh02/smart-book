package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table
public class Shipment implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer billId;
	private Boolean bill;
	@Temporal(TemporalType.DATE)
	@Column(name = "created_time")
	private Date createdTime;
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_time")
	private Date updatedTime;
	@ManyToOne
	@JoinColumn(name="status_id")
	private BillStatus status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public BillStatus getStatus() {
		return status;
	}
	public void setStatus(BillStatus status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    public Integer getBillId() {
        return billId;
    }
    public void setBillId(Integer billId) {
        this.billId = billId;
    }
    public Boolean getBill() {
        return bill;
    }
    public void setBill(Boolean bill) {
        this.bill = bill;
    }
    
	
}
