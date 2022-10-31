package com.dantn.bookStore.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BookParamsRequest;
import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.entities.Content;
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
	private CharactorService charactorService;
	private ContentService contentService;

	public BookService(IBookRepository repository, EBookService eBookService, BookStatusService bookStatusService,
			TypeService typeService, AuthorService authorService, PublisherService publisherService,
			CharactorService charactorService, ContentService contentService) {
		super();
		this.repository = repository;
		this.eBookService = eBookService;
		this.bookStatusService = bookStatusService;
		this.typeService = typeService;
		this.authorService = authorService;
		this.publisherService = publisherService;
		this.charactorService = charactorService;
		this.contentService = contentService;
	}

	public List<Book> getAll() {
		return this.repository.findAll();
	}

	private List<Type> listType;
	private List<Content> listContent;
	private List<Charactor> listCharactor;
	private List<Author> listAuthor;
	private List<Publisher> listPublisher;
	private List<BookStatus> listBookStatus;
	private BookParamsRequest paramsRequest;
	
	public HashMap<String, Object> update(Book book) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			repository.save(book);
			mapReturn.put("statusCode", "ok");
			Page<Book> pageBook = getBookData();
			mapReturn.put("pageBook", pageBook);
			mapReturn.put("typeInBook", getType(pageBook.getContent()));
			mapReturn.put("contentInBook", getContent(pageBook.getContent()));
			mapReturn.put("charactorInBook", getCharactor(pageBook.getContent()));
		} catch (Exception e) {
			mapReturn.put("statusCode", "error");
		}
		return mapReturn;
	}

	public Page<Book> getBookData() {
		Pageable page = PageRequest.of(paramsRequest.getPage(), AppConstraint.PAGE_NUM,
				Sort.by(paramsRequest.getSortBy()).descending());
		return repository.getBooks("%" + paramsRequest.getKeyWord() + "%",
				paramsRequest.getPublisher() != "" ? listPublisher.get(Integer.parseInt(paramsRequest.getPublisher()))
						: null,
				paramsRequest.getAuthor() != "" ? listAuthor.get(Integer.parseInt(paramsRequest.getAuthor())) : null,
				paramsRequest.getStatus() != "" ? listBookStatus.get(Integer.parseInt(paramsRequest.getStatus()))
						: null,
				"%" + paramsRequest.getType() + ",%", page);
	}

	public HashMap<String, Object> getBooks(BookParamsRequest params) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		listType = typeService.getAll();
		listContent = contentService.getAll();
		listCharactor = charactorService.getAll();
		listAuthor = authorService.getAll();
		listPublisher = publisherService.getAll();
		listBookStatus = BookStatusSingleton.getInstance(bookStatusService);
		paramsRequest = params;
		mapReturn.put("listType", listType);
		mapReturn.put("listContent", listContent);
		mapReturn.put("listCharactor", listCharactor);
		mapReturn.put("listAuthor", listAuthor);
		mapReturn.put("listPublisher", listPublisher);
		mapReturn.put("listBookStatus", listBookStatus);
		Page<Book> pageBook = getBookData();
		mapReturn.put("pageBook", pageBook);
		mapReturn.put("typeInBook", getType(pageBook.getContent()));
		mapReturn.put("contentInBook", getContent(pageBook.getContent()));
		mapReturn.put("charactorInBook", getCharactor(pageBook.getContent()));
		return mapReturn;
	}

	public Map<String, Object> getType(List<Book> lstBookAll) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		for (Book book : lstBookAll) {
			List<Type> typeInBook = new ArrayList<>();
			List<String> types = Arrays.asList(book.getType().split(","));
			for (Type t : listType) {
				for (String ts : types) {
					if (ts.equals(t.getId().toString())) {
						typeInBook.add(t);
					}
				}
			}
			mapReturn.put(book.getId().toString(), typeInBook);
		}
		return mapReturn;
	}
	
	public Map<String, Object> getCharactor(List<Book> lstBookAll) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		for (Book book : lstBookAll) {
			List<Charactor> typeInBook = new ArrayList<>();
			List<String> chars = Arrays.asList(book.getCharactor().split(","));
			for (Charactor c : listCharactor) {
				for (String cs : chars) {
					if (cs.equals(c.getId().toString())) {
						typeInBook.add(c);
					}
				}
			}
			mapReturn.put(book.getId().toString(), typeInBook);
		}
		return mapReturn;
	}
	
	public Map<String, Object> getContent(List<Book> lstBookAll) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		for (Book book : lstBookAll) {
			List<Content> typeInBook = new ArrayList<>();
			List<String> cons = Arrays.asList(book.getContent().split(","));
			for (Content c : listContent) {
				for (String cs : cons) {
					if (cs.equals(c.getId().toString())) {
						typeInBook.add(c);
					}
				}
			}
			mapReturn.put(book.getId().toString(), typeInBook);
		}
		return mapReturn;
	}
	
	public Map<String, Object> createType(String newType) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			Type t = new Type();
			t.setValue(newType);
			typeService.create(t);
			listType = typeService.getAll();
			mapReturn.put("listType", listType);
			mapReturn.put("statusCode", "ok");
		} catch (Exception e) {
			mapReturn.put("statusCode", "error");
		}
		return mapReturn;
	}
	
	public Map<String, Object> createContent(String newContent) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			Content t = new Content();
			t.setValue(newContent);
			contentService.create(t);
			listContent = contentService.getAll();
			mapReturn.put("listContent", listContent);
			mapReturn.put("statusCode", "ok");
		} catch (Exception e) {
			mapReturn.put("statusCode", "error");
		}
		return mapReturn;
	}
	
	public Map<String, Object> createCharactor(String newCharactor) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			Charactor t = new Charactor();
			t.setValue(newCharactor);
			charactorService.create(t);
			listCharactor = charactorService.getAll();
			mapReturn.put("listCharactor", listCharactor);
			mapReturn.put("statusCode", "ok");
		} catch (Exception e) {
			mapReturn.put("statusCode", "error");
		}
		return mapReturn;
	}
	
	

}
