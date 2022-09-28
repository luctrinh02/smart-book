package com.dantn.bookStore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

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
	public Page<User> getUserByRole(UserRole role,Integer page,String fullname){
		return this.repository.findByRoleAndFullname(role, "%"+fullname+"%",PageRequest.of(page, AppConstraint.PAGE_NUM));
	}
	public User save(User user) {
		return this.repository.save(user);
	}
	public User getById(Integer id) {
		Optional<User> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}	
	public List<User> getall(){
		return repository.findAll();
	}
}
