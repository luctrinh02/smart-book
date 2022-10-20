package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
	List<User> findByRole(UserRole role);
	User findByRoleAndEmail(UserRole role,String email);
	@Query("SELECT u FROM User u WHERE role<>?1 AND email = ?2")
	User findByNotRoleAndEmail(UserRole role,String email);
	User findByEmail(String email);
	@Query("SELECT u FROM User u WHERE role=?1 AND fullname LIKE ?2")
	Page<User> findByRoleAndFullname(UserRole role,String fullname,Pageable page);
}
