package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.ReturnBill;
@Repository
public interface IReturnBillRepository extends JpaRepository<ReturnBill, Integer>{

}
