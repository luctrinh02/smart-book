package com.dantn.bookStore.services;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.repositories.IReturnBillDetailRepository;

@Service
public class ReturnBillDetailService {
	private IReturnBillDetailRepository repository;

	public ReturnBillDetailService(IReturnBillDetailRepository repository) {
		super();
		this.repository = repository;
	}
	public ReturnBillDetail save(ReturnBillDetail detail) {
		return this.repository.save(detail);
	}
}
