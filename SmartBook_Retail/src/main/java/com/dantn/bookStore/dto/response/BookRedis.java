package com.dantn.bookStore.dto.response;

import java.io.Serializable;

import com.dantn.bookStore.entities.Book;

public class BookRedis implements Serializable{
	private Book book;
	private Long point;
	private Long evaluate;
	

	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public Long getPoint() {
		return point;
	}


	public void setPoint(Long point) {
		this.point = point;
	}


	public Long getEvaluate() {
		return evaluate;
	}

	
	public BookRedis(Book book, Long point, Long evaluate) {
		super();
		this.book = book;
		this.point = point;
		this.evaluate = evaluate;
	}


	public void setEvaluate(Long evaluate) {
		this.evaluate = evaluate;
	}


	public BookRedis() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
