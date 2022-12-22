package com.dantn.bookStore.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.repositories.IBookRepository;
import com.dantn.bookStore.repositories.IEBookRepository;
import com.dantn.bookStore.services.CharactorService;
import com.dantn.bookStore.services.ContentService;
import com.dantn.bookStore.services.TypeService;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@RestController
public class Synchronized {
	@Autowired
	private IBookRepository bookRepository2;
	@Autowired
	private TypeService typeService;
	@Autowired
	private CharactorService charactorService;
	@Autowired
	private ContentService contentService;

	@Autowired
	private IEBookRepository ieBookRepository;
	@GetMapping("/synchronized")
	public void save() throws ElasticsearchException, IOException, RuntimeException {
		List<Book> books=bookRepository2.findAll();
		List<EBook> eBooks = new ArrayList<>();
		for (Book book : books) {
			eBooks.add(getEBook(book));
		}
		ieBookRepository.saveAll(eBooks);
	}
	private EBook getEBook(Book book) {
		EBook eBook = new EBook();
		eBook.setId(book.getId() + "");
		eBook.setAuthor(book.getAuthor().getName());
		eBook.setPublisher(book.getPublisher().getName());
		eBook.setName(book.getName());
		eBook.setCharactor(charactorService.getEvalue(book.getCharactor()));
		eBook.setContent(contentService.getEvalue(book.getContent()));
		eBook.setType(typeService.getEvalue(book.getType()));
		return eBook;
	}
}
