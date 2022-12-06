package com.dantn.bookStore.api;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.EBookService;
import com.dantn.bookStore.services.SuggestService;
import com.dantn.bookStore.services.UserSearchService;
import com.dantn.bookStore.ultilities.AppConstraint;

@RestController
public class SearchApi {
	@Autowired
	private EBookService service;
	@Autowired
	private UserSearchService searchService;
	@Autowired
	private SuggestService suggestService;
	@GetMapping("/api/book/search")
	public ResponseEntity<?> search(@RequestParam("key") String key,Principal principal) throws IOException{
		List<Book> list=service.getBook(key);
		if(principal!=null) {
			searchService.save(AppConstraint.USER, key);
		}
		return ResponseEntity.ok(list);
	}
	@GetMapping("/api/book/suggest")
	public ResponseEntity<?> suggest() throws IOException{
		List<Book> list=suggestService.getSuggest(service);
		return ResponseEntity.ok(list);
	}
}
