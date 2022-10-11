package com.dantn.bookStore.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Author;
@Repository
public interface IAuthorRepository extends JpaRepository<Author, Integer> {
	@Query("SELECT p FROM Author p WHERE p.name like ?1 and p.books.size >= ?2 and p.books.size <= ?3")
	Page<Author> getPage(String keyWord,Integer toSize, Integer fromSize, Pageable page);
	
	@Query("SELECT p FROM Author p WHERE p.name = ?1")
	Author findByName(String value);
}
