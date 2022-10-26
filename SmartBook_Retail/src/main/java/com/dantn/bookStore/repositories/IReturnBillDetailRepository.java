package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.entities.ReturnBillDetailPK;
@Repository
public interface IReturnBillDetailRepository extends JpaRepository<ReturnBillDetail, ReturnBillDetailPK>{
	List<ReturnBillDetail> findByReturnBill(ReturnBill returnBill);
}
