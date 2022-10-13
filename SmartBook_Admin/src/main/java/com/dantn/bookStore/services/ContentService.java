package com.dantn.bookStore.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.repositories.IContentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.DataUltil;
@Service
public class ContentService {
	private IContentRepository repository;

	public ContentService(IContentRepository repository) {
		super();
		this.repository = repository;
	}
	public Page<Content> getContent(Integer page,String value){
		return this.repository.findByValue("%"+value+"%",PageRequest.of(0, AppConstraint.PAGE_NUM,Sort.by("id").ascending()));
	}
	public Content getById(Integer id) {
		Optional<Content> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public Content save(Content content) {
		return this.repository.save(content);
	}
	public void delete(Integer id) {
		this.repository.deleteById(id);
	}
	public HashMap<String, Object> add(Content content){
	    if("".equals(content.getValue().trim())||content.getValue()==null) {
            HashMap<String, Object> map=DataUltil.setData("error", "Không bỏ trống từ khóa");
            return map;
        }else {
            content.setValue(content.getValue().trim());
            try {
                this.save(content);
                HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
                return map;
            } catch (Exception e) {
                HashMap<String, Object> map=DataUltil.setData("error", "Từ khóa đã tồn tại");
                return map;
            }
        }
	}
	public HashMap<String, Object> update(Content content){
        if("".equals(content.getValue().trim())||content.getValue()==null) {
            HashMap<String, Object> map=DataUltil.setData("error", "Không bỏ trống từ khóa");
            return map;
        }else {
            content.setValue(content.getValue().trim());
            try {
                this.save(content);
                HashMap<String, Object> map=DataUltil.setData("ok", "Sửa thành công");
                return map;
            } catch (Exception e) {
                HashMap<String, Object> map=DataUltil.setData("error", "Từ khóa đã tồn tại");
                return map;
            }
        }
    }
}
