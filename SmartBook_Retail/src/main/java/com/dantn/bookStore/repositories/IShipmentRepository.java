package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Shipment;
@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, Integer>{
	@Query("SELECT s FROM Shipment s WHERE s.billId=?1 and s.bill=?2")
	Shipment getByBillId(Integer billId,Boolean bill);
}
