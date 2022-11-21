package com.dantn.bookStore.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.repositories.IEBookRepository;

@Service
public class EBookService {
	@Autowired
	private IEBookRepository repository;
	@Autowired
	private BookService bookService;
	public EBook save(EBook book) throws IOException {
		return this.repository.save(book);
	}
	public EBook delete(String id) throws IOException {
		return this.repository.deleteById(id);
	}
	public EBook getById(String id) throws IOException {
		return this.repository.getById(id);
	}
	public List<EBook> getAll() throws IOException{
		return this.repository.getAll();
	}
	public List<EBook> getByKey(String key) throws IOException{
		return this.repository.getByKey(key);
	}
	public List<Book> getBook(String key) throws IOException{
		List<EBook> eBooks=this.getByKey(key);
		List<Integer> ids=new ArrayList<>();
		for(EBook e:eBooks) {
			ids.add(Integer.parseInt(e.getId()));
		}
		return bookService.getByIds(ids);
	}
}
