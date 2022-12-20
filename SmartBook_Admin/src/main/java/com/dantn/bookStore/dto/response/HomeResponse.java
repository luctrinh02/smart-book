package com.dantn.bookStore.dto.response;

import java.io.Serializable;
import java.util.List;

import com.dantn.bookStore.entities.Book;

public class HomeResponse implements Serializable{
	private Long sale;
	private Long sales;
	private Long user;
	private List<Book> books;
	public Long getSale() {
		return sale;
	}
	public void setSale(Long sale) {
		this.sale = sale;
	}
	public Long getSales() {
		return sales;
	}
	public void setSales(Long sales) {
		this.sales = sales;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public HomeResponse(Long sale, Long sales, Long user, List<Book> books) {
		super();
		this.sale = sale;
		this.sales = sales;
		this.user = user;
		this.books = books;
	}
	public HomeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
