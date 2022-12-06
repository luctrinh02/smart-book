package com.dantn.bookStore.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.services.BookService;

@RestController
public class BookApi {
	@Autowired
	private BookService service;
	@GetMapping("/api/book")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(this.service.getall());
	}
	@PostMapping("test")
	public ResponseEntity<?> get(BookStatus b){
		return ResponseEntity.ok(b);
	}
	
	@GetMapping("/api/book/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping("/api/book/comment/{id}")
	public ResponseEntity<?> getComment(@PathVariable("id") Integer id){
		return ResponseEntity.ok(service.getById(id).getComments());
	}
	

	
}
