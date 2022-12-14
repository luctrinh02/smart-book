package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.City;
import com.dantn.bookStore.repositories.ICityRepository;

@Service
public class CityService {
	@Autowired
	private ICityRepository cityRepository;
	
	public City findById(Integer id) {
		if(cityRepository.findById(id).isPresent()) {
			return cityRepository.findById(id).get();
		} else return null;
	}
	public List<City> getAll() {
		return cityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
}
