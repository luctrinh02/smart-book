package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dantn.bookStore.listener.UserBuyListener;
@Entity
@Table
@EntityListeners(UserBuyListener.class)
public class UserBuy implements Serializable{
	@EmbeddedId
	private UserBuyPK userBuyPK;
	@ManyToOne
	@JoinColumn(name = "user_id",insertable = false,updatable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "book_id",insertable = false,updatable = false)
	private Book book;
	private Long purchases;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	public UserBuyPK getUserBuyPK() {
		return userBuyPK;
	}
	public void setUserBuyPK(UserBuyPK userBuyPK) {
		this.userBuyPK = userBuyPK;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Long getPurchases() {
		return purchases;
	}
	public void setPurchases(Long purchases) {
		this.purchases = purchases;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
