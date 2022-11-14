package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.entities.UserClickPK;

@Repository
public interface IUserClickRepository extends JpaRepository<UserClick, UserClickPK>{
	@Query("SELECT c.book FROM UserClick c WHERE c.user=?1")
	List<Book> findByUser(User user);
}
