package com.dantn.bookStore.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;

@Repository
public interface IBillDetailRepository extends JpaRepository<BillDetail, BillDetailPK>{
	List<BillDetail> findByBill(Bill bill);
	@Query("SELECT SUM(b.amount) FROM BillDetail b WHERE b.bill.createdTime=?1 AND b.bill.status.id NOT IN (3,6)")
	Long countBook(Date date);
	@Query("SELECT SUM(b.amount) FROM BillDetail b WHERE b.bill.status.id NOT IN (3,6)")
	Long sumAmount();
}
