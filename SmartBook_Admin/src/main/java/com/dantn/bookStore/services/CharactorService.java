package com.dantn.bookStore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.repositories.ICharactorRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class CharactorService {
	private ICharactorRepository repository;

	public CharactorService(ICharactorRepository repository) {
		super();
		this.repository = repository;
	}
	public Charactor save(Charactor charactor) {
		return this.repository.save(charactor);
	}
	public void delete(Integer id) {
		this.repository.deleteById(id);
	}
	public Page<Charactor> getByKeyWord(String key, Integer page){
		return this.repository.findByValue("%"+key+"%", PageRequest. of(page, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
	}
	public Charactor getById(Integer id) {
		Optional<Charactor> optional= this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	
}
