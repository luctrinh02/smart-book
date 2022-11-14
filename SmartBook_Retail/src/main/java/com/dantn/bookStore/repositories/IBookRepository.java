package com.dantn.bookStore.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>{
	@Query("SELECT b FROM Book b WHERE b.status=?1")
	Page<Book> findAllByStatus(BookStatus status,Pageable pageable);
}
