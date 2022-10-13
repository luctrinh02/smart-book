package com.dantn.bookStore.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IBillRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

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
	public Page<Bill> getByAcepted(BillStatus billStatus,Integer page){
		return this.repository.findByStatus(billStatus, PageRequest.of(page, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
	}
	public HashMap<String, Object> update(BillUpdateRequest request,BillStatusService billStatusService){
	    BillStatus billStatus=BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex());
        Bill bill=this.getById(request.getId());
        if(bill.getStatus().getId()!=1) {
            HashMap<String, Object> map=DataUltil.setData("error", "Đơn đã bị hủy hoặc xác nhận");
            return map;
        }else if(request.getStatusIndex()==2) {
            if("".equals(request.getMessage().trim()) || request.getMessage()==null) {
                HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập lý do hủy");
                return map;
            }else {
                bill.setStatus(billStatus);
                this.save(bill);
                HashMap<String, Object> map=DataUltil.setData("ok", "Hủy thành công");
                return map;
            }
        }else {
            bill.setStatus(billStatus);
            this.save(bill);
            HashMap<String, Object> map=DataUltil.setData("ok", "Duyệt thành công");
            return map;
        }
	}
}
