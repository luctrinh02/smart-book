package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Type;

@Repository
public interface ITypeRepository extends JpaRepository<Type, Integer>{

}
