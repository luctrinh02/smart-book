package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
	List<User> findByRole(UserRole role);
	User findByRoleAndEmail(UserRole role,String email);
	User findByEmail(String email);
}
