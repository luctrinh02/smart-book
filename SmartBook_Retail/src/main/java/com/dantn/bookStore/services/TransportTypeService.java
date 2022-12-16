package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.TransportType;
import com.dantn.bookStore.repositories.ITransportTypeRepository;

@Service
public class TransportTypeService {
	@Autowired
	private ITransportTypeRepository districtRepository;
	
	public TransportType findById(Integer id) {
		if(districtRepository.findById(id).isPresent()) {
			return districtRepository.findById(id).get();
		} else return null;
	}
	public List<TransportType> getAll() {
		return districtRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
}
