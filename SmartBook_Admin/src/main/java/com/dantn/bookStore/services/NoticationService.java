package com.dantn.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Notication;
import com.dantn.bookStore.repositories.INoticationRepository;

@Service
public class NoticationService {
	@Autowired
	private INoticationRepository repository;
	public Notication save(Notication notication) {
		return repository.save(notication);
	}
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	public void deleteAll() {
		repository.deleteAll();
	}
}
