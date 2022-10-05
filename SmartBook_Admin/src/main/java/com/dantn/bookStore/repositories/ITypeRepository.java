package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Type;

@Repository
public interface ITypeRepository extends JpaRepository<Type, Integer>{
	@Query("select t from Type t where t.value like ?1")
	List<Type> findByValue(String value);
}
