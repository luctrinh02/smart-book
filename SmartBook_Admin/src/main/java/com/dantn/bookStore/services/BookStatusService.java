package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.repositories.IBookStatusRepository;

@Service
public class BookStatusService {
	private IBookStatusRepository repository;

	public BookStatusService(IBookStatusRepository repository) {
		super();
		this.repository = repository;
	}
	public List<BookStatus> getAll(){
		return this.repository.findAll();
	}
}
