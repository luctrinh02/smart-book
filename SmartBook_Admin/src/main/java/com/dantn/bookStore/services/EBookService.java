package com.dantn.bookStore.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.repositories.IEBookRepository;

@Service
public class EBookService {
	@Autowired
	private IEBookRepository repository;
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
	public List<EBook> getByKey(String key,Integer fromRecord) throws IOException{
		return this.repository.getByKey(key, fromRecord);
	}
}
