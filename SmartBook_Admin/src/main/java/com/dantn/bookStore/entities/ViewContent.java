package com.dantn.bookStore.entities;

import java.util.List;

public class ViewContent {
	private Content content;
	private List<Book> listBook;
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public List<Book> getListBook() {
		return listBook;
	}
	public void setListBook(List<Book> listBook) {
		this.listBook = listBook;
	}
	
}
