package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;

@Repository
public interface IBillDetailRepository extends JpaRepository<BillDetail, BillDetailPK>{
	List<BillDetail> findByBill(Bill bill);
}
