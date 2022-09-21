package com.dantn.bookStore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.repositories.IReturnBillRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class ReturnBillService {
	private IReturnBillRepository repository;

	public ReturnBillService(IReturnBillRepository repository) {
		super();
		this.repository = repository;
	}
	public ReturnBill save(ReturnBill returnBill) {
		return this.repository.save(returnBill);
	}
	public Page<ReturnBill> getAll(Integer page) {
		return this.repository.findAll(PageRequest.of(page, AppConstraint.PAGE_NUM,Sort.by("id").descending()));
	}
	public ReturnBill getById(Integer id) {
		Optional<ReturnBill> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
}
