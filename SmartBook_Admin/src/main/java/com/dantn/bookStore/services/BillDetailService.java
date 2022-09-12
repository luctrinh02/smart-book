package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.repositories.IBillDetailRepository;

@Service
public class BillDetailService {
	private IBillDetailRepository repository;

	public BillDetailService(IBillDetailRepository repository) {
		super();
		this.repository = repository;
	}
	public List<BillDetail> getByBill(Bill bill){
		return this.repository.findByBill(bill);
	}
	public BillDetail save(BillDetail billDetail) {
		return this.repository.save(billDetail);
	}
}
