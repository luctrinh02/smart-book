package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dantn.bookStore.elastic.BookListener;
import com.dantn.bookStore.listener.BillListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
@EntityListeners(BillListener.class)
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
	@Column(name = "book_money")
    private BigDecimal bookMoney;
	private String message;
	@ManyToOne 
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "status_id")
	private BillStatus status;
	@ManyToOne
    @JoinColumn(name = "coupon_id")
	private Coupon coupon;
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
	private boolean missed;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    public BigDecimal getBookMoney() {
        return bookMoney;
    }
    public void setBookMoney(BigDecimal bookMoney) {
        this.bookMoney = bookMoney;
    }
    public Coupon getCoupon() {
        return coupon;
    }
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    public List<ReturnBill> getReturnBills() {
        return returnBills;
    }
    public void setReturnBills(List<ReturnBill> returnBills) {
        this.returnBills = returnBills;
    }
	public boolean isMissed() {
		return missed;
	}
	public void setMissed(boolean missed) {
		this.missed = missed;
	}
	
}
