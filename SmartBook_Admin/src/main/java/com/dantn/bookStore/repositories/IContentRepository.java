package com.dantn.bookStore.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Content;
@Repository
public interface IContentRepository extends JpaRepository<Content, Integer>{
	@Query("SELECT c FROM Content c where c.value LIKE ?1")
	Page<Content> findByValue(String value,Pageable pageable);
}
