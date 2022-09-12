package com.dantn.bookStore.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class CartPK implements Serializable{
	@Column(name = "user_id",insertable = false,updatable = false)
	private Integer userId;
	@Column(name = "book_id",insertable = false,updatable = false)
	private Integer bookId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
}
