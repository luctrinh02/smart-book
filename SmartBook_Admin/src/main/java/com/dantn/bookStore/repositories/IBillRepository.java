package com.dantn.bookStore.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.User;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Integer>{
	List<Bill> findByUser(User user);
	Bill findByTranSn(String tranSn);
	Page<Bill> findByStatus(BillStatus status,Pageable pageable);
	@Query("SELECT b FROM Bill b WHERE  b.status=?1 and b.updatedTime <= ?2")
	List<Bill> findByStatusAndUpdatedTime(BillStatus status,Date updatedTime);
	@Query("SELECT COUNT(b.user) FROM Bill b WHERE b.createdTime=?1 GROUP BY b.user")
	Long countCustomerInday(Date date);
}
