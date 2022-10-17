package com.dantn.bookStore.entities;

import java.util.List;

public class ViewType {
	private Type type;
	private List<Book> listBook;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public List<Book> getListBook() {
		return listBook;
	}
	public void setListBook(List<Book> listBook) {
		this.listBook = listBook;
	}
	
}
