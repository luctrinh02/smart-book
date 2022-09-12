package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.BookStatus;

@Repository
public interface IBookStatusRepository extends JpaRepository<BookStatus, Integer>{
}
