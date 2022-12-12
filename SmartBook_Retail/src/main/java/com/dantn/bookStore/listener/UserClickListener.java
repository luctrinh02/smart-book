package com.dantn.bookStore.listener;

import javax.persistence.PostPersist;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.services.UserClickService;

@Configurable
public class UserClickListener {
	@Autowired
	private ObjectFactory<UserClickService> factory;
	@PostPersist
	private void add(UserClick click) {
		factory.getObject().save(click.getBook());
	}
}
