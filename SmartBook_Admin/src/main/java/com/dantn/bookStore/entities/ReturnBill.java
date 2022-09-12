package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
	@Temporal(TemporalType.DATE)
	@Column(name = "created_time")
	private Date createdTime;
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_time")
	private Date updatedTime;
	@Column(name = "return_money")
	private BigDecimal returnMoney;
	@ManyToOne
	@JoinColumn(name = "status_id")
	private BillStatus status;
	@JsonIgnore
	@OneToMany(mappedBy = "returnBill")
	private List<ReturnBillDetail> returnBillDetails;
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
	public BigDecimal getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}
	public BillStatus getStatus() {
		return status;
	}
	public void setStatus(BillStatus status) {
		this.status = status;
	}
	public List<ReturnBillDetail> getReturnBillDetails() {
		return returnBillDetails;
	}
	public void setReturnBillDetails(List<ReturnBillDetail> returnBillDetails) {
		this.returnBillDetails = returnBillDetails;
	}
	
}
