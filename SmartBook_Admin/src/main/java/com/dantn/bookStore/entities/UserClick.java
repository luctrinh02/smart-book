package com.dantn.bookStore.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class UserClick implements Serializable{
	@EmbeddedId
	private UserClickPK userClickPK;
	@ManyToOne
	@JoinColumn(name = "user_id",insertable = false,updatable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "book_id",insertable = false,updatable = false)
	private Book book;
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
	
}
