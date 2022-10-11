package com.dantn.bookStore.api;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.services.AuthorService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/author")
public class AuthorApi {

	@Autowired
	private AuthorService authorService;

	@GetMapping("/getPage")
	public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
		mapReturn.put("data", data);
		mapReturn.put("listBook", getListBook(data));
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}
			Author author = authorService.findByName(value.trim());

			if (author != null) {
				mapReturn = DataUltil.setData("invalid", null);
				return ResponseEntity.ok(mapReturn);
			}

			Author Author = new Author();
			Author.setName(value.trim());
			authorService.create(Author);
			
			Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("data", data);
			mapReturn.put("listBook", getListBook(data));

		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "value") String value, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}

			Author author = authorService.findByName(value.trim());

			if (author != null) {
				mapReturn = DataUltil.setData("invalid", null);
				return ResponseEntity.ok(mapReturn);
			}
			
			Author pUpdate = authorService.findById(element);
			pUpdate.setName(value.trim());
			authorService.update(pUpdate);
			
			Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("data", data);
			mapReturn.put("listBook", getListBook(data));

		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			authorService.delete(element);
			mapReturn.put("statusCode", "ok");
			Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
			
			if(pageIndex > 0 && data.isEmpty()) {
				data = getData(pageIndex - 1, sortAuthor, keyWord, getAuthor);
			}
			
			mapReturn.put("statusCode", "ok");
			mapReturn.put("listBook", getListBook(data));
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/deleteList")
	public ResponseEntity<?> deleteListAuthor(
			@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			authorService.delete(Arrays.asList(listId));
			mapReturn.put("statusCode", "ok");
			Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
			
			if(pageIndex > 0 && data.isEmpty()) {
				data = getData(pageIndex - 1, sortAuthor, keyWord, getAuthor);
			}
			mapReturn.put("listBook", getListBook(data));
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	private Page<Author> getData(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor) {
		Page<Author> pageReturn;
		// Get Type
		switch (sortAuthor) {
		case 0:
			pageReturn = authorService.getPage(pageIndex, 10, "id", false, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		case 1:
			pageReturn = authorService.getPage(pageIndex, 10, "id", true, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		case 2:
			pageReturn = authorService.getPage(pageIndex, 10, "name", true, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		case 3:
			pageReturn = authorService.getPage(pageIndex, 10, "name", false, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		case 4:
			pageReturn = authorService.getPage(pageIndex, 10, "books.size", false, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		default:
			pageReturn = authorService.getPage(pageIndex, 10, "books.size", true, getToSize(getAuthor),
					getFromSize(getAuthor), "%" + keyWord + "%");
			break;
		}

		// Return View
		return pageReturn;
	}

	private Integer getToSize(Integer getAuthor) {
		switch (getAuthor) {
		case 0:
			return Integer.MIN_VALUE;
		case 1:
			return 1;
		case 2:
			return 51;
		default:
			return 101;
		}
	}

	private HashMap<String, Object> getListBook(Page<Author> page) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		for (Author author : page) {
			mapReturn.put(author.getId() + "", author.getBooks());
		}
		return mapReturn;
	}

	private Integer getFromSize(Integer getAuthor) {
		switch (getAuthor) {
		case 0:
			return Integer.MAX_VALUE;
		case 1:
			return 50;
		case 2:
			return 100;
		default:
			return Integer.MAX_VALUE;
		}
	}

}
