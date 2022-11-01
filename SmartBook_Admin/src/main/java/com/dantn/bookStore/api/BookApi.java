package com.dantn.bookStore.api;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BookParamsRequest;
import com.dantn.bookStore.dto.request.BookRequest;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.ultilities.DataUltil;


@RestController
@RequestMapping("/api/book")
public class BookApi {
	private BookService bookService;

	public BookApi(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@PostMapping("/getBooks")
	public ResponseEntity<?> getBooks(@RequestBody BookParamsRequest params) {
		return ResponseEntity.ok(bookService.getBooks(params));
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.update(book));
	}
	
	@PostMapping("/createType")
	public ResponseEntity<?> createType(@RequestBody String newType) {
		return ResponseEntity.ok(bookService.createType(newType));
	}
	
	@PostMapping("/createContent")
	public ResponseEntity<?> createContent(@RequestBody String newContent) {
		return ResponseEntity.ok(bookService.createContent(newContent));
	}

	@PostMapping("/createCharactor")
	public ResponseEntity<?> createCharactor(@RequestBody String newCharactor) {
		return ResponseEntity.ok(bookService.createCharactor(newCharactor));
	}
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody @Valid BookRequest request,BindingResult result,Principal principal){
		if(result.hasErrors()) {
			List<ObjectError> list=result.getAllErrors();
			HashMap<String, Object> map=DataUltil.setData("error", list);
			return ResponseEntity.ok(map);
		}else {
			bookService.create(request, principal);
			HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
			return ResponseEntity.ok(map);
		}
	}
	
	
	
	
	
	
	
	
}
