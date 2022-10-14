package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.repositories.ICharactorRepository;


@Service
public class CharactorService {

	@Autowired
	private ICharactorRepository rep;
	
	public Charactor create(Charactor obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Charactor update(Charactor obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Charactor findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Charactor> findByValue(String value) {
		return rep.findByValue(value);
	}
	
	public List<Charactor> getAll() {
		return rep.findAll();
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Charactor> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Charactor> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortCharactor) {
		if (sortCharactor) {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()));
		} else {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending()));
		}
	}
}
