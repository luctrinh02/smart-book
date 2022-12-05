package com.dantn.bookStore.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BookParamsRequest;
import com.dantn.bookStore.dto.request.BookRequest;
import com.dantn.bookStore.dto.request.ExcelDto;
import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IBookRepository;
import com.dantn.bookStore.repositories.IEBookRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BookStatusSingleton;
import com.dantn.bookStore.ultilities.ExcelUltil;
import com.dantn.bookStore.ultilities.FileUtil;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@Service
public class BookService {
	private IBookRepository repository;
	private BookStatusService bookStatusService;
	private TypeService typeService;
	private AuthorService authorService;
	private PublisherService publisherService;
	private CharactorService charactorService;
	private ContentService contentService;
	private UserService userService;
	private IEBookRepository ieBookRepository;

	public BookService(IBookRepository repository, BookStatusService bookStatusService, TypeService typeService,
			AuthorService authorService, PublisherService publisherService, CharactorService charactorService,
			ContentService contentService, UserService userService, IEBookRepository ieBookRepository) {
		super();
		this.repository = repository;
		this.bookStatusService = bookStatusService;
		this.typeService = typeService;
		this.authorService = authorService;
		this.publisherService = publisherService;
		this.charactorService = charactorService;
		this.contentService = contentService;
		this.userService = userService;
		this.ieBookRepository = ieBookRepository;
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

	public Book save(BookRequest request, Principal principal) throws IllegalStateException, IOException {
		Book b = new Book();
		b = request.changeToEntity(b);
		if (b.getId() != null) {
			Book old = repository.findById(b.getId()).get();
			b.setImage(old.getImage());
			b.setCreatedTime(old.getCreatedTime());
			b.setSaleAmount(old.getSaleAmount());
			b.setEvaluate(old.getEvaluate());
			b.setPoint(b.getPoint());
		}
		b.setBatch(false);
		b.setAuthor(authorService.findById(Integer.parseInt(request.getAuthor())));
		b.setPublisher(publisherService.findById(Integer.parseInt(request.getPublisher())));
		b.setCreatedBy(userService.getByEmail(principal.getName()));
		b.setStatus(BookStatusSingleton.getInstance(bookStatusService).get(Integer.parseInt(request.getStatus())));
		if (request.getFile() != null) {
			if (!request.getFile().isEmpty()) {
				String encode = FileUtil.fileToBase64(request.getFile());
				b.setImage(encode);
			}
		}
		return repository.save(b);
	}

	public Book getById(Integer id) {
		Optional<Book> optional = this.repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	private List<Book> getBookInExcel(ExcelDto dto, Principal principal) throws IOException,IllegalArgumentException {
		User user = userService.getByEmail(principal.getName());
		List<Book> books = new ArrayList<>();
		InputStream inputStream = dto.getFile().getInputStream();
		Workbook workbook = ExcelUltil.getWorkbook(inputStream, dto.getFile().getOriginalFilename());
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() == 0) {
				continue;
			}
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			Book book = new Book();
			book.setCreatedBy(user);
			book.setEvaluate(0);
			book.setPoint(0);
			book.setSaleAmount((long) 0);
			book.setSaleTime(null);
			book.setBatch(true);
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				Object cellValue = ExcelUltil.getCellValue(cell);
				if (cellValue == null || cellValue.toString().isEmpty()) {
					continue;
				}
				// Set value for dog object
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
				case ExcelUltil.COLUMN_INDEX_AMOUNT:
					book.setAmount(Double.valueOf((Double) cellValue).longValue());
					break;
				case ExcelUltil.COLUMN_INDEX_AUTHORID:
					Author author = authorService.findById(Double.valueOf((Double) cellValue).intValue());
					book.setAuthor(author);
					break;
				case ExcelUltil.COLUMN_INDEX_CHARACTOR:
					book.setCharactor((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_CONTENT:
					book.setContent((String) cellValue);
					;
					break;
				case ExcelUltil.COLUMN_INDEX_DESCRIPTION:
					book.setDescription((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_DISCOUNT:
					book.setDiscount(Double.valueOf((Double) cellValue).intValue());
					break;
				case ExcelUltil.COLUMN_INDEX_HEIGHT:
					book.setHeight(Double.valueOf((Double) cellValue).intValue());
					break;
				case ExcelUltil.COLUMN_INDEX_IMAGE:
					book.setImage((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_ISBN:
					book.setISBN((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_LENGTH:
					book.setLength(Double.valueOf((Double) cellValue).intValue());
					;
					break;
				case ExcelUltil.COLUMN_INDEX_NAME:
					book.setName((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_NUMOFPAGE:
					book.setNumOfPage(Double.valueOf((Double) cellValue).intValue());
					break;
				case ExcelUltil.COLUMN_INDEX_PRICE:
					book.setPrice(new BigDecimal((Double) cellValue));
					break;
				case ExcelUltil.COLUMN_INDEX_PUBLISHERID:
					Publisher publisher = publisherService.findById(Double.valueOf((Double) cellValue).intValue());
					book.setPublisher(publisher);
					break;
				case ExcelUltil.COLUMN_INDEX_STATUSID:
					BookStatus bookStatus = BookStatusSingleton.getInstance(bookStatusService)
							.get(Double.valueOf((Double) cellValue).intValue() - 1);
					book.setStatus(bookStatus);
					break;
				case ExcelUltil.COLUMN_INDEX_TYPE:
					book.setType((String) cellValue);
					break;
				case ExcelUltil.COLUMN_INDEX_WEIGHT:
					book.setWeight(Double.valueOf((Double) cellValue).intValue());
					break;
				case ExcelUltil.COLUMN_INDEX_WIDTH:
					book.setWidth(Double.valueOf((Double) cellValue).intValue());
					break;
				default:
					break;
				}
			}
			books.add(book);
		}
		return books;
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

	@Transactional(rollbackOn = { RuntimeException.class, IOException.class, ElasticsearchException.class })
	public void saveExcel(ExcelDto dto, Principal principal) throws IOException,RuntimeException,ElasticsearchException,IllegalArgumentException {
		if(dto.getFile()==null) {
			throw new IllegalArgumentException("Không bỏ trống file");
		}
		List<Book> books = getBookInExcel(dto, principal);
		books = repository.saveAll(books);
		List<EBook> eBooks = new ArrayList<>();
		for (Book book : books) {
			eBooks.add(getEBook(book));
		}
		ieBookRepository.saveAll(eBooks);
	}
}
