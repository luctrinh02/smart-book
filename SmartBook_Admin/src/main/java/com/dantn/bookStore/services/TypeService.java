package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.repositories.ITypeRepository;


@Service
public class TypeService {

	@Autowired
	private ITypeRepository rep;
	
	public Type create(Type obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Type update(Type obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Type findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Type> getAll() {
		return rep.findAll();
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Type> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Type> getPageAZ(int pageIndex,int pageSize, Boolean sortType) {
		if (sortType) {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending()));
		} else {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by("id").descending()));
		}
	}
	
}
