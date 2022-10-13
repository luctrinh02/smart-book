package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Charactor;

@Repository
public interface ICharactorRepository extends JpaRepository<Charactor, Integer>{
	@Query("select t from Charactor t where t.value like ?1")
	List<Charactor> findByValue(String value);
}
