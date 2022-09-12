package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.UserRole;
@Repository
public interface IUserRoleReposiory extends JpaRepository<UserRole, Integer>{
	
}
