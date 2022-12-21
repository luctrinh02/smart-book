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
import com.dantn.bookStore.entities.ViewCharactor;
import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.CharactorService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/charactor")
public class CharactorApi {
	@Autowired
	private CharactorService charactorService;
	@Autowired
	private BookService bookService;

	private Double maxPage;

	@GetMapping("/getPage")
	public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortCharactor", defaultValue = "0") Integer sortCharactor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getCharactor", defaultValue = "0") Integer getCharactor) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		List<ViewCharactor> data = getData(pageIndex, sortCharactor, keyWord, getCharactor);
		mapReturn.put("maxPage", maxPage);
		mapReturn.put("data", data);
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCharactor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortCharactor", defaultValue = "0") Integer sortCharactor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getCharactor", defaultValue = "0") Integer getCharactor,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}
			List<Charactor> listCharactor = charactorService.findByValue("%" + value + "%");
			for (Charactor type : listCharactor) {
				if (type.getValue().trim().equalsIgnoreCase(value.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			Charactor type = new Charactor();
			type.setValue(value.trim());
			charactorService.create(type);
			List<ViewCharactor> data = getData(pageIndex, sortCharactor, keyWord, getCharactor);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCharactor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortCharactor", defaultValue = "0") Integer sortCharactor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getCharactor", defaultValue = "0") Integer getCharactor,
			@RequestParam(name = "value") String newValue, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Charactor typeUpdate = charactorService.findById(element);
		try {
			if (newValue.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}

			List<Charactor> listCharactor = charactorService.findByValue("%" + typeUpdate.getValue().trim() + "%");

			for (Charactor type : listCharactor) {
				if (type.getValue().trim().equalsIgnoreCase(newValue.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			typeUpdate.setValue(newValue.trim());
			charactorService.update(typeUpdate);
			List<ViewCharactor> data = getData(pageIndex, sortCharactor, keyWord, getCharactor);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCharactor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortCharactor", defaultValue = "0") Integer sortCharactor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getCharactor", defaultValue = "0") Integer getCharactor,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			charactorService.delete(element);
			mapReturn.put("statusCode", "ok");
			List<ViewCharactor> data = getData(pageIndex, sortCharactor, keyWord, getCharactor);
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
	public ResponseEntity<?> deleteListCharactor(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortCharactor", defaultValue = "0") Integer sortCharactor,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getCharactor", defaultValue = "0") Integer getCharactor,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			charactorService.delete(Arrays.asList(listId));
			mapReturn.put("statusCode", "ok");
			List<ViewCharactor> data = getData(pageIndex, sortCharactor, keyWord, getCharactor);
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

	private List<ViewCharactor> getData(Integer pageIndex, Integer sortCharactor, String keyWord, Integer getCharactor) {
		List<Charactor> listCharactor = charactorService.getAll();
		List<ViewCharactor> listViewCharactor = new ArrayList<>();

		// Key Word
		listCharactor = listCharactor.stream().filter(type -> type.getValue().toUpperCase().contains(keyWord.toUpperCase()))
				.collect(Collectors.toList());
		listViewCharactor = convertToViewCharactor(listCharactor);

		// Get Charactor
		switch (getCharactor) {
		case 0:
			listViewCharactor = listViewCharactor.stream().filter(viewCharactor -> viewCharactor.getListBook().size() >= 0)
					.collect(Collectors.toList());
			break;
		case 1:
			listViewCharactor = listViewCharactor.stream()
					.filter(viewCharactor -> viewCharactor.getListBook().size() >= 1 && viewCharactor.getListBook().size() <= 50)
					.collect(Collectors.toList());
			break;
		case 2:
			listViewCharactor = listViewCharactor.stream()
					.filter(viewCharactor -> viewCharactor.getListBook().size() > 50 && viewCharactor.getListBook().size() <= 100)
					.collect(Collectors.toList());
			break;
		case 3:
			listViewCharactor = listViewCharactor.stream().filter(viewCharactor -> viewCharactor.getListBook().size() > 100)
					.collect(Collectors.toList());
			break;
		}
		// Max Page
		maxPage = getMaxPage(listViewCharactor.size());
		if (pageIndex > maxPage) {
			pageIndex--;
		}
		// Sort Charactor
		switch (sortCharactor) {
		case 0:
			listViewCharactor
					.sort((ViewCharactor t1, ViewCharactor t2) -> Integer.compare(t2.getCharactor().getId(), t1.getCharactor().getId()));
			break;
		case 1:
			listViewCharactor
					.sort((ViewCharactor t1, ViewCharactor t2) -> Integer.compare(t1.getCharactor().getId(), t2.getCharactor().getId()));
			break;
		case 2:
			listViewCharactor.sort((ViewCharactor t1, ViewCharactor t2) -> t1.getCharactor().getValue().toUpperCase()
					.compareTo(t2.getCharactor().getValue().toUpperCase()));
			break;
		case 3:
			listViewCharactor.sort((ViewCharactor t1, ViewCharactor t2) -> t2.getCharactor().getValue().toUpperCase()
					.compareTo(t1.getCharactor().getValue().toUpperCase()));
			break;
		case 4:
			listViewCharactor.sort(
					(ViewCharactor t1, ViewCharactor t2) -> Integer.compare(t2.getListBook().size(), t1.getListBook().size()));
			break;
		case 5:
			listViewCharactor.sort(
					(ViewCharactor t1, ViewCharactor t2) -> Integer.compare(t1.getListBook().size(), t2.getListBook().size()));
			break;
		}

		// Page Index
		if (!listViewCharactor.isEmpty()) {
			listViewCharactor = listViewCharactor.subList(pageIndex * 10,
					listViewCharactor.size() >= pageIndex * 10 + 10 ? pageIndex * 10 + 10 : listViewCharactor.size());
		}
		// Return View
		return listViewCharactor;
	}

	public List<ViewCharactor> convertToViewCharactor(List<Charactor> listCharactor) {
		List<ViewCharactor> listViewCharactor = new ArrayList<ViewCharactor>();
		for (Charactor type : listCharactor) {
			ViewCharactor vt = new ViewCharactor();
			vt.setCharactor(type);
			vt.setListBook(getListBook(type));
			listViewCharactor.add(vt);
		}
		return listViewCharactor;
	}

	public List<Book> getListBook(Charactor type) {
		List<Book> lstBookAll = new ArrayList<>();
		List<Book> bookInCharactor = new ArrayList<>();
		for (Book book : lstBookAll) {
			List<String> types = Arrays.asList(book.getCharactor().split(","));
			types.stream().forEach(t -> {
				if (t.trim().equals(type.getId().toString().trim())) {
					bookInCharactor.add(book);
				}
			});
		}
		return bookInCharactor;
	}

	public Double getMaxPage(Integer size) {
		double maxPage = Math.floor(size / 10);
		if (size % 10 == 0) {
			maxPage--;
		}
		return maxPage;
	}

}