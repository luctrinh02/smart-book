package com.dantn.bookStore.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.response.HomeResponse;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.BillDetailService;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BookService;
@RestController
public class HomeApi {
	private BillService billService;
	private BillDetailService billDetailService;
	private BookService bookService;
	
	public HomeApi(BillService billService, BillDetailService billDetailService, BookService bookService) {
		super();
		this.billService = billService;
		this.billDetailService = billDetailService;
		this.bookService = bookService;
	}

	@GetMapping("/api/home")
	public ResponseEntity<?> getHome(){
		Long user=billService.countCustomerInday();
		Long sale=billDetailService.countBook();
		Long sales=billDetailService.sumBook();
		List<Book> books=bookService.getTop10();
		HomeResponse response=new HomeResponse(sale==null?0:sale, sales==null?0:sales, user==null?0:user, books);
		return ResponseEntity.ok(response);
	}
}
