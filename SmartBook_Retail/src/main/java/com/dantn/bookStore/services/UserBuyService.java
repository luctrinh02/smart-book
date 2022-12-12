package com.dantn.bookStore.services;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.UserBuy;
import com.dantn.bookStore.entities.UserBuyPK;
import com.dantn.bookStore.repositories.IUserBuyRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserBuyService {
	@Autowired
	private IUserBuyRepository repository;
	public UserBuy save(Book book) {
		UserBuyPK buyPK=new UserBuyPK();
		buyPK.setBookId(book.getId());
		buyPK.setUserId(AppConstraint.USER.getId());
		UserBuy buy=new UserBuy();
		buy.setBook(book);
		buy.setUserBuyPK(buyPK);
		buy.setUser(AppConstraint.USER);
		buy.setTime(new Date());
		return this.repository.save(buy);
	}
	public void deleteByBook(Book book) {
		UserBuyPK buyPK=new UserBuyPK();
		buyPK.setBookId(book.getId());
		buyPK.setUserId(AppConstraint.USER.getId());
		this.repository.deleteById(buyPK);
	}
	public List<Book> getBook(){
		return repository.findByUser(AppConstraint.USER);
	}
	public Book getById(Integer id) {
		UserBuyPK buyPK=new UserBuyPK(AppConstraint.USER.getId(),id);
		Optional<UserBuy> optional=repository.findById(buyPK);
		return optional.isPresent()?optional.get().getBook():null;
	}
}
