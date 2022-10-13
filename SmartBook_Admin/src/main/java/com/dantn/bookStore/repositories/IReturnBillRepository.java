package com.dantn.bookStore.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.BillStatus;
@Repository
public interface IReturnBillRepository extends JpaRepository<ReturnBill, Integer>{
    Page<ReturnBill> findByStatus(BillStatus status,Pageable pageable);
}
