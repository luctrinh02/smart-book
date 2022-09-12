package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.User;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Integer>{
	List<Bill> findByUser(User user);
	Bill findByTranSn(String tranSn);
}
