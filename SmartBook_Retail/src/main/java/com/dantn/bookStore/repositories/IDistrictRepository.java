package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.District;

@Repository
public interface IDistrictRepository extends JpaRepository<District, Integer> {
	
}
