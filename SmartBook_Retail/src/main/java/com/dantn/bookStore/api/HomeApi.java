package com.dantn.bookStore.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.EBookService;
import com.dantn.bookStore.services.UserBuyService;

@RestController
@RequestMapping("/api/home")
public class HomeApi {
	@Autowired
	private BookService bookService;
	@Autowired
	private EBookService eBookService;
	@Autowired
	private UserBuyService buyService;
	@GetMapping("/future")
	private ResponseEntity<?> getTrend(@RequestParam("page") Integer page,@RequestParam("condition") String condition){
		Page<Book> list=bookService.getFuture(condition,page);
		return ResponseEntity.ok(list);
	}
	@GetMapping("/related/{id}")
	private ResponseEntity<?> getTrend(@PathVariable("id") Integer id) throws IOException{
		List<Book> books=buyService.getBook();
		Book book=bookService.getById(id);
		EBook eBook=eBookService.getById(book.getId()+"");
		LinkedHashSet<Book> list1=new LinkedHashSet<>(eBookService.getBookByName(eBook.getName()));
		LinkedHashSet<Book> list2=new LinkedHashSet<>(eBookService.getBookByType(eBook.getType()));
		list1.addAll(list2);
		List<Book> list=new ArrayList<>(list1);
		list.remove(book);
		list.removeAll(books);
		if(list.size()>=6) {
			return ResponseEntity.ok(list.subList(0, 6));
		}else {
			
			return ResponseEntity.ok(list);
		}
	}
}
