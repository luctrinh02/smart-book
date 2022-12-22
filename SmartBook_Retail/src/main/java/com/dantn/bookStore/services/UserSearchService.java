package com.dantn.bookStore.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserSearch;
import com.dantn.bookStore.repositories.IUserSearchRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserSearchService {
	@Autowired
	private IUserSearchRepository repository;
	@Autowired
	private UserService service;
	public UserSearch save(User user,String word) {
		UserSearch search=new UserSearch();
		search.setUser(user);
		search.setWord(word);
		return this.repository.save(search);
	}
	public String getKey() {
		List<UserSearch> list=this.repository.findTop2ByUserOrderByIdDesc(getUser());
		StringBuilder builder=new StringBuilder("");
		for(UserSearch search:list) {
			builder.append(search.getWord());
			builder.append(" ");
		}
		return builder.toString().trim();
	}
	public User getUser() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		User user=service.getByEmail(authentication.getName());
		return user;
	}
}
