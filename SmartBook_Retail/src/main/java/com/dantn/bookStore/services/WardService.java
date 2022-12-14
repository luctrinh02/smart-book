package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Ward;
import com.dantn.bookStore.repositories.IWardRepository;

@Service
public class WardService {
	@Autowired
	private IWardRepository districtRepository;
	
	public Ward findById(Integer id) {
		if(districtRepository.findById(id).isPresent()) {
			return districtRepository.findById(id).get();
		} else return null;
	}
	public List<Ward> getAll() {
		return districtRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
}
