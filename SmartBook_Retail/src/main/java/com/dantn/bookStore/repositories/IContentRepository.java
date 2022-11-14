package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Content;

@Repository
public interface IContentRepository extends JpaRepository<Content, Integer>{
	@Query("select t from Content t where t.value like ?1")
	List<Content> findByValue(String value);
}
