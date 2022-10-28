package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.entities.ReturnBillDetailPK;
@Service
public interface IReturnBillDetailRepository extends JpaRepository<ReturnBillDetail, ReturnBillDetailPK>{
    List<ReturnBillDetail> findByReturnBill(ReturnBill returnBill);
}
