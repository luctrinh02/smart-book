package com.dantn.bookStore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IBillRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class BillService {
	private IBillRepository repository;

	public BillService(IBillRepository repository) {
		super();
		this.repository = repository;
	}
	public List<Bill> getByUser(User user){
		return this.repository.findByUser(user);
	}
	public Bill getByTranSn(String transn) {
		return this.repository.findByTranSn(transn);
	}
	public Bill save(Bill bill) {
		return this.repository.save(bill);
	}
	public Bill changeStatus(Bill bill,BillStatus status) {
		bill.setStatus(status);
		return this.save(bill);
	}
	public Page<Bill> getAll(Integer page) {
		return this.repository.findAll(PageRequest.of(page, AppConstraint.PAGE_NUM));
	}
	public Bill getById(Integer id) {
		Optional<Bill> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
}
