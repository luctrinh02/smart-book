package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.User;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Integer>{
	Page<Bill> findByUser(User user,Pageable page);
	Bill findByTranSnAndUser(String tranSn,User user);
}
