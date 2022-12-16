package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.TransportType;

@Repository
public interface ITransportTypeRepository extends JpaRepository<TransportType, Integer>{

}
