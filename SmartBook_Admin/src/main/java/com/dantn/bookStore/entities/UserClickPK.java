package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserClickPK implements Serializable{
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
	@Override
	public int hashCode() {
		return Objects.hash(bookId, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserClickPK other = (UserClickPK) obj;
		return Objects.equals(bookId, other.bookId) && Objects.equals(userId, other.userId);
	}
	
}
