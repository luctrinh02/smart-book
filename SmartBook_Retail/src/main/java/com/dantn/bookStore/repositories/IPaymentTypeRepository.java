package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.PaymentType;

@Repository
public interface IPaymentTypeRepository extends JpaRepository<PaymentType, Integer>{

}
