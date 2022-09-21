package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.ReturnBill;
@Repository
public interface IReturnBillRepository extends JpaRepository<ReturnBill, Integer>{
	List<ReturnBill> findByBill(Bill bill);
}
