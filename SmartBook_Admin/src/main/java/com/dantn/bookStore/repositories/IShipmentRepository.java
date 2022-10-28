package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.entities.User;
@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, Integer>{
    List<Shipment> findByUserAndStatus(User user,BillStatus status);
}
