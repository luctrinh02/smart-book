package com.dantn.bookStore.ultilities;

import java.util.List;

import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.services.BookStatusService;

public class BookStatusSingleton {
	private static List<BookStatus> singleton;
	private BookStatusSingleton () {}
	public static List<BookStatus> getInstance(BookStatusService service){
		if(singleton==null) {
			singleton=service.getAll();
		}
		return singleton;
	}
}
