package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.dto.response.BookRedis;
import com.dantn.bookStore.entities.Comment;
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer>{
	@Query("SELECT new com.dantn.bookStore.dto.response.BookRedis(cm.book,SUM(cm.rate),COUNT(cm.book)) FROM Comment cm GROUP BY cm.book")
	List<BookRedis> getComment();
}
