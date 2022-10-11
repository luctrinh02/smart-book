package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.repositories.IAuthorRepository;
@Service
public class AuthorService {
	@Autowired
	private IAuthorRepository rep;
	
	public Author create(Author obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Author update(Author obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Author findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Author> getAll() {
		return rep.findAll();
	}
	
	public Author findByName(String value) {
		return rep.findByName(value);
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Author> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Author> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortAuthor, Integer toSize, Integer fromSize, String keyWord) {
		Pageable page; 
		if (sortAuthor) {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
		} else {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
		}
		return rep.getPage(keyWord, toSize, fromSize, page);
	}
}
