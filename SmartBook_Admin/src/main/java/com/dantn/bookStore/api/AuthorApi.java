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
		HashMap<String, Object> mapReturn = authorService.getPage(pageIndex, sortAuthor, keyWord, getAuthor);
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = authorService.create(pageIndex, sortAuthor, keyWord, getAuthor, value);
		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "value") String value, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = authorService.update(pageIndex, sortAuthor, keyWord, getAuthor, value, element);
		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteAuthor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = authorService.delete(pageIndex, sortAuthor, keyWord, getAuthor, keyWord, element);
		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/deleteList")
	public ResponseEntity<?> deleteListAuthor(
			@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortAuthor", defaultValue = "0") Integer sortAuthor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getAuthor", defaultValue = "0") Integer getAuthor,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = authorService.delete(pageIndex, sortAuthor, keyWord, getAuthor, keyWord, listId);
		return ResponseEntity.ok(mapReturn);
	}
}
