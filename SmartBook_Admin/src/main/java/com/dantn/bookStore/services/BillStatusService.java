package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.repositories.IBillStatusRepository;

@Service
public class BillStatusService {
	private IBillStatusRepository repository;

	public BillStatusService(IBillStatusRepository repository) {
		super();
		this.repository = repository;
	}
	public List<BillStatus> getAll(){
		return this.repository.findAll();
	}
}
