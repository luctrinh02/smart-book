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
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
		mapReturn.put("data", data);
		mapReturn.put("listBook", getListBook(data));
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createPublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}
			Publisher publisher = publisherService.findByName(value.trim());

			if (publisher != null) {
				mapReturn = DataUltil.setData("invalid", null);
				return ResponseEntity.ok(mapReturn);
			}

			Publisher Publisher = new Publisher();
			Publisher.setName(value.trim());
			publisherService.create(Publisher);
			
			Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("data", data);
			mapReturn.put("listBook", getListBook(data));

		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updatePublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "value") String value, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}

			Publisher publisher = publisherService.findByName(value.trim());

			if (publisher != null) {
				mapReturn = DataUltil.setData("invalid", null);
				return ResponseEntity.ok(mapReturn);
			}
			
			Publisher pUpdate = publisherService.findById(element);
			pUpdate.setName(value.trim());
			publisherService.update(pUpdate);
			
			Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("data", data);
			mapReturn.put("listBook", getListBook(data));

		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deletePublisher(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			publisherService.delete(element);
			mapReturn.put("statusCode", "ok");
			Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
			
			if(pageIndex > 0 && data.isEmpty()) {
				data = getData(pageIndex - 1, sortPublisher, keyWord, getPublisher);
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
	public ResponseEntity<?> deleteListPublisher(
			@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			publisherService.delete(Arrays.asList(listId));
			mapReturn.put("statusCode", "ok");
			Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
			
			if(pageIndex > 0 && data.isEmpty()) {
				data = getData(pageIndex - 1, sortPublisher, keyWord, getPublisher);
			}
			mapReturn.put("listBook", getListBook(data));
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	private Page<Publisher> getData(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher) {
		Page<Publisher> pageReturn;
		// Get Type
		switch (sortPublisher) {
		case 0:
			pageReturn = publisherService.getPage(pageIndex, 10, "id", false, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		case 1:
			pageReturn = publisherService.getPage(pageIndex, 10, "id", true, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		case 2:
			pageReturn = publisherService.getPage(pageIndex, 10, "name", true, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		case 3:
			pageReturn = publisherService.getPage(pageIndex, 10, "name", false, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		case 4:
			pageReturn = publisherService.getPage(pageIndex, 10, "books.size", false, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		default:
			pageReturn = publisherService.getPage(pageIndex, 10, "books.size", true, getToSize(getPublisher),
					getFromSize(getPublisher), "%" + keyWord + "%");
			break;
		}

		// Return View
		return pageReturn;
	}

	private Integer getToSize(Integer getPublisher) {
		switch (getPublisher) {
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

	private HashMap<String, Object> getListBook(Page<Publisher> page) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		for (Publisher publisher : page) {
			mapReturn.put(publisher.getId() + "", publisher.getBooks());
		}
		return mapReturn;
	}

	private Integer getFromSize(Integer getPublisher) {
		switch (getPublisher) {
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
