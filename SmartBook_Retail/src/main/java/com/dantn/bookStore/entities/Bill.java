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
public class Bill implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tran_sn")
	private String tranSn;
	@Column(name = "total_money")
	private BigDecimal totalMoney;
	@Column(name = "transport_fee")
	private BigDecimal transportFee;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "status_id")
	private BillStatus status;
	@Temporal(TemporalType.DATE)
	@Column(name = "created_time")
	private Date createdTime;
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_time")
	private Date updatedTime;
	@JsonIgnore
	@OneToMany(mappedBy = "bill")
	private List<BillDetail> details;
	@JsonIgnore
	@OneToMany(mappedBy = "bill")
	private List<ReturnBill> returnBills;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTranSn() {
		return tranSn;
	}
	public void setTranSn(String tranSn) {
		this.tranSn = tranSn;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BillStatus getStatus() {
		return status;
	}
	public void setStatus(BillStatus status) {
		this.status = status;
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
	public List<BillDetail> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}
	public List<ReturnBill> getReturnBills() {
		return returnBills;
	}
	public void setReturnBills(List<ReturnBill> returnBills) {
		this.returnBills = returnBills;
	}
	
}
