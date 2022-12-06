package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Comment;
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer>{

}
