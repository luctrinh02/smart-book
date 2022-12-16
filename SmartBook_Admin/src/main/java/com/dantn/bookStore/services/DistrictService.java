package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.District;
import com.dantn.bookStore.repositories.IDistrictRepository;

@Service
public class DistrictService {
	@Autowired
	private IDistrictRepository districtRepository;
	
	public District findById(Integer id) {
		if(districtRepository.findById(id).isPresent()) {
			return districtRepository.findById(id).get();
		} else return null;
	}
	public List<District> getAll() {
		return districtRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
}
