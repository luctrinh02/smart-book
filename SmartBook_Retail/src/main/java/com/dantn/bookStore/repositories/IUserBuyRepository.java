package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserBuy;
import com.dantn.bookStore.entities.UserBuyPK;

@Repository
public interface IUserBuyRepository extends JpaRepository<UserBuy, UserBuyPK>{
	@Query("SELECT c.book FROM UserBuy c WHERE c.user=?1 ORDER BY c.time desc")
	List<Book> findByUser(User user);
}
