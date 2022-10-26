package com.dantn.bookStore.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BookParamsRequest;
import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.repositories.IBookRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BookStatusSingleton;

@Service
public class BookService {
	private IBookRepository repository;
	private EBookService eBookService;
	private BookStatusService bookStatusService;
	private TypeService typeService;
	private AuthorService authorService;
	private PublisherService publisherService;

	public BookService(IBookRepository repository, EBookService eBookService, BookStatusService bookStatusService,
			TypeService typeService, AuthorService authorService, PublisherService publisherService) {
		super();
		this.repository = repository;
		this.eBookService = eBookService;
		this.bookStatusService = bookStatusService;
		this.typeService = typeService;
		this.authorService = authorService;
		this.publisherService = publisherService;
	}

	List<Type> listType;
	List<Author> listAuthor;
	List<Publisher> listPublisher;
	List<BookStatus> listBookStatus;

	public Book getById(Integer id) {
		Optional<Book> optional = this.repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	public List<Book> getAll() {
		return this.repository.findAll();
	}

	public List<Book> getByIds(List<Integer> ids) {
		return this.repository.findAllById(ids);
	}

	public HashMap<String, Object> getBooks(BookParamsRequest params) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Pageable page = PageRequest.of(params.getPage(), AppConstraint.PAGE_NUM,
				Sort.by(params.getSortBy()).descending());
		listType = typeService.getAll();
		listAuthor = authorService.getAll();
		listPublisher = publisherService.getAll();
		listBookStatus = BookStatusSingleton.getInstance(bookStatusService);
		mapReturn.put("listType", listType);
		mapReturn.put("listAuthor", listAuthor);
		mapReturn.put("listPublisher", listPublisher);
		mapReturn.put("listBookStatus", listBookStatus);
		mapReturn.put("pageBook",
				repository.getBooks("%" + params.getKeyWord() + "%",
						params.getPublisher() != null ? listPublisher.get(Integer.parseInt(params.getPublisher()))
								: null,
						params.getAuthor() != null ? listAuthor.get(Integer.parseInt(params.getAuthor())) : null,
						params.getStatus() != null ? listBookStatus.get(Integer.parseInt(params.getStatus())) : null,
						"%" + params.getType() + ",%", page));
		return mapReturn;
	}

}
