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

import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.services.PublisherService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/publisher")
public class PublisherApi {

	@Autowired
	private PublisherService publisherService;

	@GetMapping("/getPage")
	public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher) {
	    HashMap<String, Object> mapReturn = publisherService.getPage(pageIndex, sortPublisher, keyWord, getPublisher);
        return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createPublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "value") String value) {
	    HashMap<String, Object> mapReturn = publisherService.create(pageIndex, sortPublisher, keyWord, getPublisher, value);
        return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updatePublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "value") String value, @RequestParam(name = "element") Integer element) {
	    HashMap<String, Object> mapReturn = publisherService.update(pageIndex, sortPublisher, keyWord, getPublisher, value, element);
        return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deletePublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "element") Integer element) {
	    HashMap<String, Object> mapReturn = publisherService.delete(pageIndex, sortPublisher, keyWord, getPublisher, element);
        return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/deleteList")
	public ResponseEntity<?> deleteListPublisher(
			@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = publisherService.delete(pageIndex, sortPublisher, keyWord, getPublisher, getPublisher, listId);
		return ResponseEntity.ok(mapReturn);
	}


}
