package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.entities.UserClickPK;
import com.dantn.bookStore.repositories.IUserClickRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserClickService {
	@Autowired
	private IUserClickRepository repository;
	public UserClick save(Book book) {
		UserClickPK clickPK=new UserClickPK();
		clickPK.setBookId(book.getId());
		clickPK.setUserId(AppConstraint.USER.getId());
		UserClick click=new UserClick();
		click.setBook(book);
		click.setUser(AppConstraint.USER);
		click.setUserClickPK(clickPK);
		return this.repository.save(click);
	}
	public void deleteByBook(Book book) {
		UserClickPK clickPK=new UserClickPK();
		clickPK.setBookId(book.getId());
		clickPK.setUserId(AppConstraint.USER.getId());
		try {
			repository.deleteById(clickPK);
		} catch (Exception e) {
		}
	}
	public List<Book> getByUser(){
		return this.repository.findByUser(AppConstraint.USER);
	}
}
