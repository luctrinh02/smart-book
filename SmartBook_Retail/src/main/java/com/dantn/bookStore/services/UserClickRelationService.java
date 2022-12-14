package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

	public List<String> getRelation() {
		List<String> list = repository.findRelation(AppConstraint.USER, PageRequest.of(0, 2));
		return list;
	}

	public String getKey() {
		List<String> list = this.getRelation();
		if(list.size()==0) return "";
		String result="";
		for (String x : list) {
			switch (x.charAt(0)) {
			case 'A':
				result.concat(charactorService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			case 'B':
				result.concat(contentService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			case 'C':
				result.concat(typeService.findById(Integer.parseInt(x.substring(1))).getValue());
				break;
			default:
				break;
			}
		}
		return result.trim();
	}
}
