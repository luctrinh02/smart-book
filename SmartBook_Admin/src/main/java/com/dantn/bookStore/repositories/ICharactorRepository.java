package com.dantn.bookStore.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Charactor;

@Repository
public interface ICharactorRepository extends JpaRepository<Charactor, Integer>{
	@Query("SELECT c FROM Charactor c where c.value like ?1")
	Page<Charactor> findByValue(String value,Pageable pageable);
}
