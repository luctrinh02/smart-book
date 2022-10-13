package com.dantn.bookStore.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.repositories.ICharactorRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.DataUltil;

@Service
public class CharactorService {
	private ICharactorRepository repository;

	public CharactorService(ICharactorRepository repository) {
		super();
		this.repository = repository;
	}
	public Charactor save(Charactor charactor) {
		return this.repository.save(charactor);
	}
	public void delete(Integer id) {
		this.repository.deleteById(id);
	}
	public Page<Charactor> getByKeyWord(String key, Integer page){
		return this.repository.findByValue("%"+key+"%", PageRequest. of(page, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
	}
	public Charactor getById(Integer id) {
		Optional<Charactor> optional= this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public HashMap<String, Object> add(String value){
	    if("".equals(value.trim()) || value==null) {
            HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập tên nhân vật!");
            return map;
        }else {
            Charactor c=new Charactor();
            c.setValue(value.trim());
            try {
                this.save(c);
                HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
                return map;
            } catch (Exception e) {
                HashMap<String, Object> map=DataUltil.setData("error", "Tên nhân vật không trùng lặp");
                return map;
            }
        }
	}
	public HashMap<String, Object> update(Charactor charactor) {
	    if(charactor.getValue()==null || "".equals(charactor.getValue().trim())) {
            HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập tên nhân vật!");
            return map;
        }else {
            try {
                charactor.setValue(charactor.getValue().trim());
                this.save(charactor);
                HashMap<String, Object> map=DataUltil.setData("ok", "Sửa thành công");
                return map;
            } catch (Exception e) {
                HashMap<String, Object> map=DataUltil.setData("error", "Tên nhân vật không trùng lặp");
                return map;
            }
        }
	}
}
