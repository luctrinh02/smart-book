package com.dantn.bookStore.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.ViewContent;
import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.ContentService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/content")
public class ContentApi {
	@Autowired
	private ContentService contentService;
	@Autowired
	private BookService bookService;

	private Double maxPage;

	@GetMapping("/getPage")
	public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortContent", defaultValue = "0") Integer sortContent,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getContent", defaultValue = "0") Integer getContent) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		List<ViewContent> data = getData(pageIndex, sortContent, keyWord, getContent);
		mapReturn.put("maxPage", maxPage);
		mapReturn.put("data", data);
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createContent(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortContent", defaultValue = "0") Integer sortContent,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getContent", defaultValue = "0") Integer getContent,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}
			List<Content> listContent = contentService.findByValue("%" + value + "%");
			for (Content type : listContent) {
				if (type.getValue().trim().equalsIgnoreCase(value.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			Content type = new Content();
			type.setValue(value.trim());
			contentService.create(type);
			List<ViewContent> data = getData(pageIndex, sortContent, keyWord, getContent);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateContent(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortContent", defaultValue = "0") Integer sortContent,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getContent", defaultValue = "0") Integer getContent,
			@RequestParam(name = "value") String newValue, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Content typeUpdate = contentService.findById(element);
		try {
			if (newValue.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}

			List<Content> listContent = contentService.findByValue("%" + typeUpdate.getValue().trim() + "%");

			for (Content type : listContent) {
				if (type.getValue().trim().equalsIgnoreCase(newValue.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			typeUpdate.setValue(newValue.trim());
			contentService.update(typeUpdate);
			List<ViewContent> data = getData(pageIndex, sortContent, keyWord, getContent);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteContent(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortContent", defaultValue = "0") Integer sortContent,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getContent", defaultValue = "0") Integer getContent,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			contentService.delete(element);
			mapReturn.put("statusCode", "ok");
			List<ViewContent> data = getData(pageIndex, sortContent, keyWord, getContent);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			if (pageIndex > maxPage) {
				pageIndex--;
			}
			mapReturn.put("pageIndex", pageIndex);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/deleteList")
	public ResponseEntity<?> deleteListContent(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortContent", defaultValue = "0") Integer sortContent,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getContent", defaultValue = "0") Integer getContent,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			contentService.delete(Arrays.asList(listId));
			mapReturn.put("statusCode", "ok");
			List<ViewContent> data = getData(pageIndex, sortContent, keyWord, getContent);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			if (pageIndex > maxPage) {
				pageIndex--;
			}
			mapReturn.put("pageIndex", pageIndex);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	private List<ViewContent> getData(Integer pageIndex, Integer sortContent, String keyWord, Integer getContent) {
		List<Content> listContent = contentService.getAll();
		List<ViewContent> listViewContent = new ArrayList<>();

		// Key Word
		listContent = listContent.stream().filter(type -> type.getValue().toUpperCase().contains(keyWord.toUpperCase()))
				.collect(Collectors.toList());
		listViewContent = convertToViewContent(listContent);

		// Get Content
		switch (getContent) {
		case 0:
			listViewContent = listViewContent.stream().filter(viewContent -> viewContent.getListBook().size() >= 0)
					.collect(Collectors.toList());
			break;
		case 1:
			listViewContent = listViewContent.stream()
					.filter(viewContent -> viewContent.getListBook().size() >= 1 && viewContent.getListBook().size() <= 50)
					.collect(Collectors.toList());
			break;
		case 2:
			listViewContent = listViewContent.stream()
					.filter(viewContent -> viewContent.getListBook().size() > 50 && viewContent.getListBook().size() <= 100)
					.collect(Collectors.toList());
			break;
		case 3:
			listViewContent = listViewContent.stream().filter(viewContent -> viewContent.getListBook().size() > 100)
					.collect(Collectors.toList());
			break;
		}
		// Max Page
		maxPage = getMaxPage(listViewContent.size());
		if (pageIndex > maxPage) {
			pageIndex--;
		}
		// Sort Content
		switch (sortContent) {
		case 0:
			listViewContent
					.sort((ViewContent t1, ViewContent t2) -> Integer.compare(t2.getContent().getId(), t1.getContent().getId()));
			break;
		case 1:
			listViewContent
					.sort((ViewContent t1, ViewContent t2) -> Integer.compare(t1.getContent().getId(), t2.getContent().getId()));
			break;
		case 2:
			listViewContent.sort((ViewContent t1, ViewContent t2) -> t1.getContent().getValue().toUpperCase()
					.compareTo(t2.getContent().getValue().toUpperCase()));
			break;
		case 3:
			listViewContent.sort((ViewContent t1, ViewContent t2) -> t2.getContent().getValue().toUpperCase()
					.compareTo(t1.getContent().getValue().toUpperCase()));
			break;
		case 4:
			listViewContent.sort(
					(ViewContent t1, ViewContent t2) -> Integer.compare(t2.getListBook().size(), t1.getListBook().size()));
			break;
		case 5:
			listViewContent.sort(
					(ViewContent t1, ViewContent t2) -> Integer.compare(t1.getListBook().size(), t2.getListBook().size()));
			break;
		}

		// Page Index
		if (!listViewContent.isEmpty()) {
			listViewContent = listViewContent.subList(pageIndex * 10,
					listViewContent.size() >= pageIndex * 10 + 10 ? pageIndex * 10 + 10 : listViewContent.size());
		}
		// Return View
		return listViewContent;
	}

	public List<ViewContent> convertToViewContent(List<Content> listContent) {
		List<ViewContent> listViewContent = new ArrayList<ViewContent>();
		for (Content type : listContent) {
			ViewContent vt = new ViewContent();
			vt.setContent(type);
			vt.setListBook(getListBook(type));
			listViewContent.add(vt);
		}
		return listViewContent;
	}

	public List<Book> getListBook(Content type) {
		List<Book> lstBookAll = bookService.getAll();
		List<Book> bookInContent = new ArrayList<>();
		for (Book book : lstBookAll) {
			List<String> types = Arrays.asList(book.getContent().split(","));
			types.stream().forEach(t -> {
				if (t.trim().equals(type.getValue().trim())) {
					bookInContent.add(book);
				}
			});
		}
		return bookInContent;
	}

	public Double getMaxPage(Integer size) {
		double maxPage = Math.floor(size / 10);
		if (size % 10 == 0) {
			maxPage--;
		}
		return maxPage;
	}

}
