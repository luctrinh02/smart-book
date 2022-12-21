package com.dantn.bookStore.services;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IBillRepository;
import com.dantn.bookStore.repositories.IShipmentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@Service
public class BillService {
	private IBillRepository repository;
	private IShipmentRepository shipmentRepository;
	private UserService service;

	public BillService(IBillRepository repository, IShipmentRepository shipmentRepository, UserService service) {
		super();
		this.repository = repository;
		this.shipmentRepository = shipmentRepository;
		this.service = service;
	}

	public List<Bill> getByUser(User user) {
		return this.repository.findByUser(user);
	}

	public Bill getByTranSn(String transn) {
		return this.repository.findByTranSn(transn);
	}

	public Bill save(Bill bill) {
		return this.repository.save(bill);
	}

	public Bill changeStatus(Bill bill, BillStatus status) {
		bill.setStatus(status);
		return this.save(bill);
	}

	public Page<Bill> getAll(Integer page) {
		return this.repository.findAll(PageRequest.of(page, AppConstraint.PAGE_NUM));
	}

	public Bill getById(Integer id) {
		Optional<Bill> optional = this.repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	public Page<Bill> getByAcepted(BillStatus billStatus, Integer page) {
		return this.repository.findByStatus(billStatus,
				PageRequest.of(page, AppConstraint.PAGE_NUM, Sort.by("id").ascending()));
	}

	public HashMap<String, Object> update(BillUpdateRequest request, BillStatusService billStatusService, Principal principal) {
		BillStatus billStatus = BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex());
		Bill bill = this.getById(request.getId());
		if (request.getStatusIndex() == 2) {
			if ("".equals(request.getMessage().trim()) || request.getMessage() == null) {
				HashMap<String, Object> map = DataUltil.setData("blank", "");
				return map;
			} else {
				bill.setStatus(billStatus);
				bill.setUpdatedTime(new Date());
				bill.setMessage(request.getMessage());
				this.save(bill);
				HashMap<String, Object> map = DataUltil.setData("ok", "");
				return map;
			}
		} else if (request.getStatusIndex() == 3) {
			User user = service.getByEmail(principal.getName());
			Shipment s = new Shipment();
			s.setBill(true);
			s.setStatus(billStatus);
			s.setBillId(bill.getId());
			s.setCreatedTime(new Date());
			s.setUser(user);
			this.shipmentRepository.save(s);
			bill.setUpdatedTime(new Date());
			bill.setStatus(billStatus);
			this.save(bill);
			HashMap<String, Object> map = DataUltil.setData("ok", "");
			return map;
		} else {
			bill.setUpdatedTime(new Date());
			bill.setStatus(billStatus);
			this.save(bill);
			HashMap<String, Object> map = DataUltil.setData("ok", "");
			return map;
		}
		
	}

	public List<Bill> getBill(BillStatus billStatus) {
		Long date = new Date().getTime() - 259200000;
		Date d = new Date(date);
		return repository.findByStatusAndUpdatedTime(billStatus, d);
	}

	public List<Bill> saveAll(List<Bill> bills) {
		return this.repository.saveAll(bills);
	}
}
