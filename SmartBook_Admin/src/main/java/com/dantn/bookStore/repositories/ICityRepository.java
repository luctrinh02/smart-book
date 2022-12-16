package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.City;
@Repository
public interface ICityRepository extends JpaRepository<City, Integer>{

}
