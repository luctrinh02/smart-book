package com.dantn.bookStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.repositories.IShipmentRepository;

@Service
public class ShipmentService {
	@Autowired
	private IShipmentRepository repository;
	public Shipment save(Shipment shipment) {
		return this.repository.save(shipment);
	}
	public Shipment getByBillId(Integer id) {
		return this.repository.getByBillId(id, false);
	}
}
