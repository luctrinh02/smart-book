package com.dantn.bookStore.repositories;

import com.dantn.bookStore.entities.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Integer>{

    @Query("SELECT p FROM Coupon p WHERE p.name like ?1 and p.bills.size >= ?2 and p.bills.size <= ?3")
    Page<Coupon> getPage(String keyWord, Integer toSize, Integer fromSize, Pageable page);

    @Query("select t from Coupon t where t.name like ?1")
    Coupon findByName(String value);
}
