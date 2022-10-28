package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.repositories.IReturnBillDetailRepository;

@Service
public class ReturnBillDetailService {
    @Autowired
    private IReturnBillDetailRepository repository;
    public List<ReturnBillDetail> details(ReturnBill bill){
        return this.repository.findByReturnBill(bill);
    }
    public ReturnBillDetail save(ReturnBillDetail billDetail) {
        return this.repository.save(billDetail);
    }
}
