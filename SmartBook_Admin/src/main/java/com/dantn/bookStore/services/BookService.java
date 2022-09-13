package com.dantn.bookStore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.repositories.IBookRepository;

@Service
public class BookService {
	private IBookRepository repository;
	
	public BookService(IBookRepository repository) {
		super();
		this.repository = repository;
	}

	public Book getById(Integer id) {
		Optional<Book> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public List<Book> getByIds(List<Integer> ids){
		return this.repository.findAllById(ids);
	}
}
