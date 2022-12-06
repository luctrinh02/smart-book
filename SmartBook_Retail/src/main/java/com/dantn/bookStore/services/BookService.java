package com.dantn.bookStore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.repositories.IBookRepository;
import com.dantn.bookStore.ultilities.BookStatusSingleton;

@Service
public class BookService {
	private IBookRepository repository;
	private BookStatusService service;
	

	public BookService(IBookRepository repository, BookStatusService service) {
		super();
		this.repository = repository;
		this.service = service;
	}
	public Book getById(Integer id) {
		Optional<Book> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public List<Book> getByIds(List<Integer> ids){
		return this.repository.findAllById(ids);
	}
	public List<Book> getall() {
		return this.repository.findAll();
	}
	public Page<Book> getall(Integer num) {
		return this.repository.findAllByStatus(BookStatusSingleton.getInstance(service).get(0),PageRequest.of(0, num,Sort.by("saleAmount").descending()));
	}
	public Book save(Book book) {
		return this.repository.save(book);
	}
	public Page<Book> getFuture(String condition){
		return this.repository.findFuture(BookStatusSingleton.getInstance(service).get(0),PageRequest.of(0, 24,Sort.by(condition,"id").descending()));
	}
}
