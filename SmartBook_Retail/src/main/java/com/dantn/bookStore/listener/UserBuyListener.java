package com.dantn.bookStore.listener;

import javax.persistence.PostPersist;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dantn.bookStore.entities.UserBuy;
import com.dantn.bookStore.services.UserClickService;

@Configurable
public class UserBuyListener {
	@Autowired
	private ObjectFactory<UserClickService> factory;
	@PostPersist
	public void create(UserBuy buy) {
		factory.getObject().deleteByBook(buy.getBook());
	}
}
