package com.dantn.bookStore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.repositories.IContentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
@Service
public class ContentService {
	private IContentRepository repository;

	public ContentService(IContentRepository repository) {
		super();
		this.repository = repository;
	}
	public Page<Content> getContent(Integer page,String value){
		return this.repository.findByValue("%"+value+"%",PageRequest.of(0, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
	}
	public Content getById(Integer id) {
		Optional<Content> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public Content save(Content content) {
		return this.repository.save(content);
	}
	public void delete(Integer id) {
		this.repository.deleteById(id);
	}
}
