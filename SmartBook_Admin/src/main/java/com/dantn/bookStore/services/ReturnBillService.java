package com.dantn.bookStore.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.repositories.IReturnBillRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;

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
    
    public Page<ReturnBill> getReturnBills(Integer page,BillStatusService billStatusService,Integer statusIndex){
        BillStatus status=BillStatusSingleton.getInstance(billStatusService).get(statusIndex);
        return this.repository.findByStatus(status,PageRequest.of(0, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
    }
    
    public ReturnBill changeStatus(BillUpdateRequest request) {
        return null;
    }
}
