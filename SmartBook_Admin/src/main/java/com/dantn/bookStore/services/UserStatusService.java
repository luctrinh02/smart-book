package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.UserStatus;
import com.dantn.bookStore.repositories.IUserStatusRepository;

@Service
public class UserStatusService {
	private IUserStatusRepository repository;
	public UserStatusService(IUserStatusRepository repository) {
		super();
		this.repository = repository;
	}
	public List<UserStatus> getAll(){
		return this.repository.findAll();
	}
}
