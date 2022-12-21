package com.dantn.bookStore.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.entities.UserClickPK;
import com.dantn.bookStore.repositories.IUserClickRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserClickService {
	@Autowired
	private IUserClickRepository repository;
	@Autowired
	private UserService service;
	public UserClick save(Book book) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		User u=service.getByEmail(authentication.getName());
		UserClickPK clickPK=new UserClickPK();
		clickPK.setBookId(book.getId());
		clickPK.setUserId(u.getId());
		UserClick click=new UserClick();
		click.setBook(book);
		click.setUser(u);
		click.setUserClickPK(clickPK);
		click.setClickDate(new Date());
		return this.repository.save(click);
	}
	public void deleteByBook(Book book) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		User u=service.getByEmail(authentication.getName());
		UserClickPK clickPK=new UserClickPK();
		clickPK.setBookId(book.getId());
		clickPK.setUserId(u.getId());
		try {
			repository.deleteById(clickPK);
		} catch (Exception e) {
		}
	}
	public List<Book> getByUser(){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		User u=service.getByEmail(authentication.getName());
		return this.repository.findByUser(u);
	}
}
