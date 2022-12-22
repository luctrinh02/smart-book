package com.dantn.bookStore.listener;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.services.UserClickRelationService;

@Configurable
public class UserClickListener {
	@Autowired
	private ObjectFactory<UserClickRelationService> factory;
	@PostPersist
	@PostUpdate
	private void add(UserClick click) {
		try {
			factory.getObject().save(click);
		} catch (Exception e) {
		}
	}
}
