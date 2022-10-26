package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.entities.ReturnBillDetailPK;
@Repository
public interface IReturnBillDetailRepository extends JpaRepository<ReturnBillDetail, ReturnBillDetailPK>{

}
