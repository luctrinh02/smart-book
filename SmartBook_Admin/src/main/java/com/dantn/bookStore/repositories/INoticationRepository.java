package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Notication;

@Repository
public interface INoticationRepository extends JpaRepository<Notication, Integer>{
	
}
