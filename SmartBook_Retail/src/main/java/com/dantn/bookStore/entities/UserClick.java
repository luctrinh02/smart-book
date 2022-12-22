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

import com.dantn.bookStore.listener.UserClickListener;

@Entity
@Table
@EntityListeners(UserClickListener.class)
public class UserClick implements Serializable{
	@EmbeddedId
	private UserClickPK userClickPK;
	@ManyToOne
	@JoinColumn(name = "user_id",insertable = false,updatable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "book_id",insertable = false,updatable = false)
	private Book book;
	@Temporal(TemporalType.TIMESTAMP)
	private Date clickDate;
	public UserClickPK getUserClickPK() {
		return userClickPK;
	}
	public void setUserClickPK(UserClickPK userClickPK) {
		this.userClickPK = userClickPK;
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
	@Override
	public String toString() {
		return "UserClick [user=" + user + ", book=" + book + "]";
	}
	public Date getClickDate() {
		return clickDate;
	}
	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}
	
}
