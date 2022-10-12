package com.dantn.bookStore.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.entities.ViewType;
import com.dantn.bookStore.repositories.ITypeRepository;


@Service
public class TypeService {

	@Autowired
	private ITypeRepository rep;
	
	public Type create(Type obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Type update(Type obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Type findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Type> findByValue(String value) {
		return rep.findByValue(value);
	}
	
	public List<Type> getAll() {
		return rep.findAll();
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Type> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Type> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortType) {
		if (sortType) {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()));
		} else {
			return rep.findAll(PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending()));
		}
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

    public List<Book> getListBook(Type type,BookService bookService) {
        List<Book> lstBookAll = bookService.getAll();
        List<Book> bookInType = new ArrayList<>();
        for (Book book : lstBookAll) {
            List<String> types = Arrays.asList(book.getType().split(","));
            types.stream().forEach(t -> {
                if (t.trim().equals(type.getValue().trim())) {
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
