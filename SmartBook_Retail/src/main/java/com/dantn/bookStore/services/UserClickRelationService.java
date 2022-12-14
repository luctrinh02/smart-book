package com.dantn.bookStore.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.entities.UserClickRelation;
import com.dantn.bookStore.repositories.IUserClickRelationRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class UserClickRelationService {
	@Autowired
	private IUserClickRelationRepository repository;
	@Autowired
	private CharactorService charactorService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private UserService service;
	public List<String> getRelation() {
		List<String> list = repository.findRelation(getUser(), PageRequest.of(0, 2));
		return list;
	}
	public User getUser() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		User user=service.getByEmail(authentication.getName());
		return user;
	}
	public String getKey() {
		List<String> list = this.getRelation();
		if(list.size()==0) return "";
		String result="";
		for (String x : list) {
			if(x.length()==0) continue;
			switch (x.charAt(0)) {
			case '*':
				result=result+(" "+charactorService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			case '&':
				result=result+(" "+contentService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			case '#':
				result=result+(" "+typeService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			default:
				result=result+(" "+x);
				break;
			}
		}
		return result.trim();
	}
	public List<UserClickRelation> save(UserClick userClick) {
		Book b=userClick.getBook();
		List<String> charactor=Arrays.asList(b.getCharactor().split(","));
		for(String x: charactor) {
			if("".equals(x)) continue;
			int index=charactor.indexOf(x);
			x="*"+x;
			charactor.set(index, x);
		}
		List<String> content=Arrays.asList(b.getContent().split(","));
		for(String x: content) {
			if("".equals(x)) continue;
			int index=content.indexOf(x);
			x="&"+x;
			content.set(index, x);
		}
		List<String> type=Arrays.asList(b.getType().split(","));
		for(String x: type) {
			if("".equals(x)) continue;
			int index=type.indexOf(x);
			x="#"+x;
			type.set(index, x);
		}
		List<String> strings=new ArrayList<>();
		if(charactor.size()!=0) strings.addAll(charactor);
		if(content.size()!=0) strings.addAll(content);
		if(type.size()!=0) strings.addAll(type);
		strings.add(b.getAuthor().getName());
		strings.add(b.getPublisher().getName());
		List<UserClickRelation> list=new ArrayList<>();
		for(String x:strings) {
			UserClickRelation clickRelation=new UserClickRelation();
			clickRelation.setUser(userClick.getUser());
			clickRelation.setClickDate(userClick.getClickDate());
			clickRelation.setRelation(x);
			list.add(clickRelation);
		}
		return repository.saveAll(list);
	}
}
