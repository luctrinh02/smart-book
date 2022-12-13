package com.dantn.bookStore.repositories;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Shipment;
@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, Integer>{
	@Query("SELECT s FROM Shipment s WHERE s.billId=?1 s.bill=?2")
	Shipment findByBillId(Integer id,boolean isBill);
}
