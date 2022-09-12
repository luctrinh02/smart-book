package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRepository;

@Service
public class UserService {
	private IUserRepository repository;

	public UserService(IUserRepository repository) {
		super();
		this.repository = repository;
	}
	public List<User> getUserByRole(UserRole role){
		return this.repository.findByRole(role);
	}
	public User getUserByRoleAndEmail(UserRole role,String email) {
		return this.repository.findByRoleAndEmail(role, email);
	}
	public User getByEmail(String email) {
		return this.repository.findByEmail(email);
	}
}
