package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserClickRelation;

@Repository
public interface IUserClickRelationRepository extends JpaRepository<UserClickRelation, Integer>{
	@Query("SELECT u.relation FROM UserClickRelation u Where u.user=?1 GROUP BY u.relation ORDER BY Count(u.relation) DESC")
	List<String> findRelation(User user,Pageable pageable);
}
