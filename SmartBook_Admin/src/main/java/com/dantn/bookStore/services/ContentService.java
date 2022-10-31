package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.repositories.IContentRepository;


@Service
public class ContentService {

	@Autowired
	private IContentRepository rep;
	
	public Content create(Content obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Content update(Content obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Content findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Content> findByValue(String value) {
		return rep.findByValue(value);
	}
	
	public List<Content> getAll() {
		return rep.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Content> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Content> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortContent) {
		if (sortContent) {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()));
		} else {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending()));
		}
	}
}
