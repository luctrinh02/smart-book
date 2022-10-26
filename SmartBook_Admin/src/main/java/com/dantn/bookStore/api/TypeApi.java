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
import com.dantn.bookStore.entities.ViewType;
import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.TypeService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/type")
public class TypeApi {
	@Autowired
	private TypeService typeService;
	@Autowired
	private BookService bookService;

	private Double maxPage;

	@GetMapping("/getPage")
	public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortType", defaultValue = "0") Integer sortType,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getType", defaultValue = "0") Integer getType) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		List<ViewType> data = getData(pageIndex, sortType, keyWord, getType);
		mapReturn.put("maxPage", maxPage);
		mapReturn.put("data", data);
		return ResponseEntity.ok(mapReturn);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createType(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortType", defaultValue = "0") Integer sortType,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getType", defaultValue = "0") Integer getType,
			@RequestParam(name = "value") String value) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			if (value.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}
			List<Type> listType = typeService.findByValue("%" + value + "%");
			for (Type type : listType) {
				if (type.getValue().trim().equalsIgnoreCase(value.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			Type type = new Type();
			type.setValue(value.trim());
			typeService.create(type);
			List<ViewType> data = getData(pageIndex, sortType, keyWord, getType);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateType(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortType", defaultValue = "0") Integer sortType,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getType", defaultValue = "0") Integer getType,
			@RequestParam(name = "value") String newValue, @RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		Type typeUpdate = typeService.findById(element);
		try {
			if (newValue.trim() == "") {
				mapReturn = DataUltil.setData("blank", null);
				return ResponseEntity.ok(mapReturn);
			}

			List<Type> listType = typeService.findByValue("%" + typeUpdate.getValue().trim() + "%");

			for (Type type : listType) {
				if (type.getValue().trim().equalsIgnoreCase(newValue.trim())) {
					mapReturn = DataUltil.setData("invalid", null);
					return ResponseEntity.ok(mapReturn);
				}
			}
			typeUpdate.setValue(newValue.trim());
			typeService.update(typeUpdate);
			List<ViewType> data = getData(pageIndex, sortType, keyWord, getType);
			mapReturn.put("statusCode", "ok");
			mapReturn.put("maxPage", maxPage);
			mapReturn.put("data", data);
		} catch (Exception e) {
			mapReturn = DataUltil.setData("error", null);
		}

		return ResponseEntity.ok(mapReturn);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteType(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortType", defaultValue = "0") Integer sortType,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getType", defaultValue = "0") Integer getType,
			@RequestParam(name = "element") Integer element) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();

		try {
			typeService.delete(element);
			mapReturn.put("statusCode", "ok");
			List<ViewType> data = getData(pageIndex, sortType, keyWord, getType);
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
	public ResponseEntity<?> deleteListType(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(name = "sortType", defaultValue = "0") Integer sortType,
			@RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(name = "getType", defaultValue = "0") Integer getType,
			@RequestParam(name = "listId") Integer[] listId) {
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			typeService.delete(Arrays.asList(listId));
			mapReturn.put("statusCode", "ok");
			List<ViewType> data = getData(pageIndex, sortType, keyWord, getType);
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

	private List<ViewType> getData(Integer pageIndex, Integer sortType, String keyWord, Integer getType) {
		List<Type> listType = typeService.getAll();
		List<ViewType> listViewType = new ArrayList<>();

		// Key Word
		listType = listType.stream().filter(type -> type.getValue().toUpperCase().contains(keyWord.toUpperCase()))
				.collect(Collectors.toList());
		listViewType = convertToViewType(listType);

		// Get Type
		switch (getType) {
		case 0:
			listViewType = listViewType.stream().filter(viewType -> viewType.getListBook().size() >= 0)
					.collect(Collectors.toList());
			break;
		case 1:
			listViewType = listViewType.stream()
					.filter(viewType -> viewType.getListBook().size() >= 1 && viewType.getListBook().size() <= 50)
					.collect(Collectors.toList());
			break;
		case 2:
			listViewType = listViewType.stream()
					.filter(viewType -> viewType.getListBook().size() > 50 && viewType.getListBook().size() <= 100)
					.collect(Collectors.toList());
			break;
		case 3:
			listViewType = listViewType.stream().filter(viewType -> viewType.getListBook().size() > 100)
					.collect(Collectors.toList());
			break;
		}
		// Max Page
		maxPage = getMaxPage(listViewType.size());
		if (pageIndex > maxPage) {
			pageIndex--;
		}
		// Sort Type
		switch (sortType) {
		case 0:
			listViewType
					.sort((ViewType t1, ViewType t2) -> Integer.compare(t2.getType().getId(), t1.getType().getId()));
			break;
		case 1:
			listViewType
					.sort((ViewType t1, ViewType t2) -> Integer.compare(t1.getType().getId(), t2.getType().getId()));
			break;
		case 2:
			listViewType.sort((ViewType t1, ViewType t2) -> t1.getType().getValue().toUpperCase()
					.compareTo(t2.getType().getValue().toUpperCase()));
			break;
		case 3:
			listViewType.sort((ViewType t1, ViewType t2) -> t2.getType().getValue().toUpperCase()
					.compareTo(t1.getType().getValue().toUpperCase()));
			break;
		case 4:
			listViewType.sort(
					(ViewType t1, ViewType t2) -> Integer.compare(t2.getListBook().size(), t1.getListBook().size()));
			break;
		case 5:
			listViewType.sort(
					(ViewType t1, ViewType t2) -> Integer.compare(t1.getListBook().size(), t2.getListBook().size()));
			break;
		}

		// Page Index
		if (!listViewType.isEmpty()) {
			listViewType = listViewType.subList(pageIndex * 10,
					listViewType.size() >= pageIndex * 10 + 10 ? pageIndex * 10 + 10 : listViewType.size());
		}
		// Return View
		return listViewType;
	}

	public List<ViewType> convertToViewType(List<Type> listType) {
		List<ViewType> listViewType = new ArrayList<ViewType>();
		for (Type type : listType) {
			ViewType vt = new ViewType();
			vt.setType(type);
			vt.setListBook(getListBook(type));
			listViewType.add(vt);
		}
		return listViewType;
	}

	public List<Book> getListBook(Type type) {
		List<Book> lstBookAll = bookService.getAll();
		List<Book> bookInType = new ArrayList<>();
		for (Book book : lstBookAll) {
			List<String> types = Arrays.asList(book.getType().split(","));
			types.stream().forEach(t -> {
				if (t.trim().equals(type.getId().toString())) {
					bookInType.add(book);
				}
			});
		}
		return bookInType;
	}

	public Double getMaxPage(Integer size) {
		double maxPage = Math.floor(size / 10);
		if (size % 10 == 0) {
			maxPage--;
		}
		return maxPage;
	}

}
