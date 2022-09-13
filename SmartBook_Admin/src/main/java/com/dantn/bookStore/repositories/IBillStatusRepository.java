package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.BillStatus;

@Repository
public interface IBillStatusRepository extends JpaRepository<BillStatus, Integer>{
	
}
