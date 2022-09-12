package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>{
	
}
