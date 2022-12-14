package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Ward;

@Repository
public interface IWardRepository extends JpaRepository<Ward, Integer>{

}
