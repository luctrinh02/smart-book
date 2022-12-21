package com.dantn.bookStore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.BookService;

@RestController
@RequestMapping("/api/home")
public class HomeApi {
	@Autowired
	private BookService bookService;
	@GetMapping("/future")
	private ResponseEntity<?> getTrend(@RequestParam("page") Integer page,@RequestParam("condition") String condition){
		Page<Book> list=bookService.getFuture(condition,page);
		return ResponseEntity.ok(list);
	}
}
