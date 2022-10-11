package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.repositories.IPublisherRepository;
@Service
public class PublisherService {
	@Autowired
	private IPublisherRepository rep;
	
	public Publisher create(Publisher obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Publisher update(Publisher obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Publisher findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Publisher> getAll() {
		return rep.findAll();
	}
	
	public Publisher findByName(String value) {
		return rep.findByName(value);
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Publisher> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Publisher> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortPublisher, Integer toSize, Integer fromSize, String keyWord) {
		Pageable page; 
		if (sortPublisher) {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
		} else {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
		}
		return rep.getPage(keyWord, toSize, fromSize, page);
	}
}
