package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRoleReposiory;

@Service
public class UserRoleService {
	private IUserRoleReposiory reposiory;

	public UserRoleService(IUserRoleReposiory reposiory) {
		super();
		this.reposiory = reposiory;
	}
	public List<UserRole> getAll(){
		return this.reposiory.findAll();
	}
}
