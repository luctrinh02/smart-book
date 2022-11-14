package com.dantn.bookStore.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserSearch;
import com.dantn.bookStore.repositories.IUserSearchRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserSearchService {
	@Autowired
	private IUserSearchRepository repository;
	public UserSearch save(User user,String word) {
		UserSearch search=new UserSearch();
		search.setUser(user);
		search.setWord(word);
		return this.repository.save(search);
	}
	public String getKey() {
		List<UserSearch> list=this.repository.findTop2ByUserOrderByIdDesc(AppConstraint.USER);
		StringBuilder builder=new StringBuilder("");
		for(UserSearch search:list) {
			builder.append(search.getWord());
			builder.append(" ");
		}
		return builder.toString().trim();
	}
}
