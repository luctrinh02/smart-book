package com.dantn.bookStore.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.repositories.IAuthorRepository;
import com.dantn.bookStore.ultilities.DataUltil;
@Service
public class AuthorService {
	@Autowired
	private IAuthorRepository rep;
	
	public Author create(Author obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Author update(Author obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Author findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Author> getAll() {
		return rep.findAll();
	}
	
	public Author findByName(String value) {
		return rep.findByName(value);
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Author> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Author> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortAuthor, Integer toSize, Integer fromSize, String keyWord) {
		Pageable page; 
		if (sortAuthor) {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
		} else {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
		}
		return rep.getPage(keyWord, toSize, fromSize, page);
	}
	public HashMap<String, Object> create(Integer pageIndex,Integer sortAuthor,String keyWord,Integer getAuthor,String value){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();

        try {
            if (value.trim() == "") {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }
            Author author = this.findByName(value.trim());

            if (author != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }

            Author Author = new Author();
            Author.setName(value.trim());
            this.create(Author);
            
            Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBook", getListBook(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
	}
	public HashMap<String, Object> update(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor,String value,Integer element){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        try {
            if (value.trim() == "") {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }

            Author author = this.findByName(value.trim());

            if (author != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }
            
            Author pUpdate = this.findById(element);
            pUpdate.setName(value.trim());
            this.update(pUpdate);
            
            Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBook", getListBook(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
	}
	
	public HashMap<String, Object> delete(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor,String value,Integer element){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();

        try {
            this.delete(element);
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

        return mapReturn;
	}
	
	public HashMap<String, Object> delete(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor,String value,Integer[] listId){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        try {
            this.delete(Arrays.asList(listId));
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

        return mapReturn;
	}
	 public HashMap<String, Object> getPage(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor){
	     HashMap<String, Object> mapReturn = new HashMap<String, Object>();
	        Page<Author> data = getData(pageIndex, sortAuthor, keyWord, getAuthor);
	        mapReturn.put("data", data);
	        mapReturn.put("listBook", getListBook(data));
	        return mapReturn;
	 }
	private Page<Author> getData(Integer pageIndex, Integer sortAuthor, String keyWord, Integer getAuthor) {
        Page<Author> pageReturn;
        // Get Type
        switch (sortAuthor) {
        case 0:
            pageReturn = this.getPage(pageIndex, 10, "id", false, getToSize(getAuthor),
                    getFromSize(getAuthor), "%" + keyWord + "%");
            break;
        case 1:
            pageReturn = this.getPage(pageIndex, 10, "id", true, getToSize(getAuthor),
                    getFromSize(getAuthor), "%" + keyWord + "%");
            break;
        case 2:
            pageReturn = this.getPage(pageIndex, 10, "name", true, getToSize(getAuthor),
                    getFromSize(getAuthor), "%" + keyWord + "%");
            break;
        case 3:
            pageReturn = this.getPage(pageIndex, 10, "name", false, getToSize(getAuthor),
                    getFromSize(getAuthor), "%" + keyWord + "%");
            break;
        case 4:
            pageReturn = this.getPage(pageIndex, 10, "books.size", false, getToSize(getAuthor),
                    getFromSize(getAuthor), "%" + keyWord + "%");
            break;
        default:
            pageReturn = this.getPage(pageIndex, 10, "books.size", true, getToSize(getAuthor),
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
		/*
		 * for (Author author : page) { mapReturn.put(author.getId() + "",
		 * author.getBooks()); }
		 */
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

