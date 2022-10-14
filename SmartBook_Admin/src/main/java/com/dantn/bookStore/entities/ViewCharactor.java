package com.dantn.bookStore.entities;

import java.util.List;

public class ViewCharactor {
	private Charactor charactor;
	private List<Book> listBook;
	public Charactor getCharactor() {
		return charactor;
	}
	public void setCharactor(Charactor charactor) {
		this.charactor = charactor;
	}
	public List<Book> getListBook() {
		return listBook;
	}
	public void setListBook(List<Book> listBook) {
		this.listBook = listBook;
	}
	
}
