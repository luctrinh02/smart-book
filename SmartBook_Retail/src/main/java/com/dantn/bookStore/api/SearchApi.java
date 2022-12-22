package com.dantn.bookStore.api;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.SearchRequest;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.EBookService;
import com.dantn.bookStore.services.SuggestService;
import com.dantn.bookStore.services.UserSearchService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.AppConstraint;

@RestController
public class SearchApi {
	@Autowired
	private EBookService service;
	@Autowired
	private UserSearchService searchService;
	@Autowired
	private SuggestService suggestService;
	@Autowired
	private UserService userService;
	@PostMapping("/api/book/search")
	public ResponseEntity<?> search(@RequestBody @Valid SearchRequest request,Principal principal) throws IOException{
		List<Book> list=service.getBook(request);
		if(principal!=null) {
			User u=userService.getByEmail(principal.getName());
			searchService.save(u, request.getKey());
		}
		return ResponseEntity.ok(list);
	}
	@GetMapping("/api/book/suggest")
	public ResponseEntity<?> suggest() throws IOException{
		List<Book> list=suggestService.getSuggest(service);
		return ResponseEntity.ok(list);
	}
}
