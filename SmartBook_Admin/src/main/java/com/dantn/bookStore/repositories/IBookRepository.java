package com.dantn.bookStore.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Publisher;


@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>{
	@Query("select b from Book b where b.name like ?1 and (?2 is null or b.publisher = ?2) and (?3 is null or b.author = ?3) and (?4 is null or b.status = ?4) and b.type like ?5")
	Page<Book> getBooks(String keyWord, Publisher publisher, Author author, BookStatus status, String type ,Pageable page);
	@Query("select b from Book b where b.saleAmount>0")
	Page<Book> getBooks(Pageable pageable);
}
