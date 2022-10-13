package com.dantn.bookStore.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.repositories.IPublisherRepository;
import com.dantn.bookStore.ultilities.DataUltil;
@Service
public class PublisherService {
	@Autowired
	private IPublisherRepository rep;
	
	public Publisher create(Publisher obj) {
		obj.setId(null);
		return rep.save(obj);
	}

	public Publisher update(Publisher obj) {
		return rep.save(obj);
	}

	public Integer delete(Integer id) {
		 rep.deleteById(id);
		return id;
	}
	
	public Publisher findById(Integer id) {
		if(rep.findById(id).isPresent()) {
			return rep.findById(id).get();
		} else return null;
	}
	
	public List<Publisher> getAll() {
		return rep.findAll();
	}
	
	public Publisher findByName(String value) {
		return rep.findByName(value);
	}
	
	public List<Integer> delete(List<Integer> listId) {
		if (listId != null) {
			List<Publisher> listDelete = rep.findAllById(listId);
			rep.deleteAll(listDelete);
			return listId;
		} else
			return null;
	}
	public Page<Publisher> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortPublisher, Integer toSize, Integer fromSize, String keyWord) {
		Pageable page; 
		if (sortPublisher) {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
		} else {
			page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
		}
		return rep.getPage(keyWord, toSize, fromSize, page);
	}
	
	public HashMap<String, Object> getPage(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
        mapReturn.put("data", data);
        mapReturn.put("listBook", getListBook(data));
        return mapReturn;
	}
	
	public HashMap<String, Object> create(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher,String value){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();

        try {
            if (value.trim() == "") {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }
            Publisher publisher = this.findByName(value.trim());

            if (publisher != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }

            Publisher Publisher = new Publisher();
            Publisher.setName(value.trim());
            this.create(Publisher);
            
            Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBook", getListBook(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
	}
	
	public HashMap<String, Object> update(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher,String value,Integer element){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        try {
            if (value.trim() == "") {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }

            Publisher publisher = this.findByName(value.trim());

            if (publisher != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }
            
            Publisher pUpdate = this.findById(element);
            pUpdate.setName(value.trim());
            this.update(pUpdate);
            
            Page<Publisher> data = getData(pageIndex, sortPublisher, keyWord, getPublisher);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBook", getListBook(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
	}
	
	public HashMap<String, Object> delete(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher,Integer element){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();

        try {
            this.delete(element);
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

        return mapReturn;
	}
	
	public HashMap<String, Object> delete(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher,Integer element,Integer[] listId){
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
	    try {
            this.delete(Arrays.asList(listId));
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

        return mapReturn;
	}
	
	private Page<Publisher> getData(Integer pageIndex, Integer sortPublisher, String keyWord, Integer getPublisher) {
        Page<Publisher> pageReturn;
        // Get Type
        switch (sortPublisher) {
        case 0:
            pageReturn = this.getPage(pageIndex, 10, "id", false, getToSize(getPublisher),
                    getFromSize(getPublisher), "%" + keyWord + "%");
            break;
        case 1:
            pageReturn = this.getPage(pageIndex, 10, "id", true, getToSize(getPublisher),
                    getFromSize(getPublisher), "%" + keyWord + "%");
            break;
        case 2:
            pageReturn = this.getPage(pageIndex, 10, "name", true, getToSize(getPublisher),
                    getFromSize(getPublisher), "%" + keyWord + "%");
            break;
        case 3:
            pageReturn = this.getPage(pageIndex, 10, "name", false, getToSize(getPublisher),
                    getFromSize(getPublisher), "%" + keyWord + "%");
            break;
        case 4:
            pageReturn = this.getPage(pageIndex, 10, "books.size", false, getToSize(getPublisher),
                    getFromSize(getPublisher), "%" + keyWord + "%");
            break;
        default:
            pageReturn = this.getPage(pageIndex, 10, "books.size", true, getToSize(getPublisher),
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
