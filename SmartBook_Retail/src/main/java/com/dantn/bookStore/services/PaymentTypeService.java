package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.PaymentType;
import com.dantn.bookStore.repositories.IPaymentTypeRepository;

@Service
public class PaymentTypeService {
	@Autowired
	private IPaymentTypeRepository districtRepository;
	
	public PaymentType findById(Integer id) {
		if(districtRepository.findById(id).isPresent()) {
			return districtRepository.findById(id).get();
		} else return null;
	}
	public List<PaymentType> getAll() {
		return districtRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
}
