package com.dantn.bookStore.api;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.services.ContentService;
import com.dantn.bookStore.ultilities.DataUltil;


@Repository
@RequestMapping("/api/admin/content")
public class ContentApi {
	private ContentService service;

	public ContentApi(ContentService service) {
		super();
		this.service = service;
	}

	@GetMapping("")
	public ResponseEntity<?> get(@RequestParam("page") Integer pageNum
			,@RequestParam(name = "value",defaultValue = "") String value){
		Page<Content> page=service.getContent(pageNum, value);
		return ResponseEntity.ok(page);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){
		Content content=service.getById(id);
		return ResponseEntity.ok(content);
	}
	@PostMapping("")
	public ResponseEntity<?> add(@RequestBody Content content){
		if("".equals(content.getValue().trim())||content.getValue()==null) {
			HashMap<String, Object> map=DataUltil.setData("error", "Không bỏ trống từ khóa");
			return ResponseEntity.ok(map);
		}else {
			content.setValue(content.getValue().trim());
			try {
				service.save(content);
				HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				HashMap<String, Object> map=DataUltil.setData("error", "Từ khóa đã tồn tại");
				return ResponseEntity.ok(map);
			}
		}
	}
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody Content content){
		if("".equals(content.getValue().trim())||content.getValue()==null) {
			HashMap<String, Object> map=DataUltil.setData("error", "Không bỏ trống từ khóa");
			return ResponseEntity.ok(map);
		}else {
			content.setValue(content.getValue().trim());
			try {
				service.save(content);
				HashMap<String, Object> map=DataUltil.setData("ok", "Sửa thành công");
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				HashMap<String, Object> map=DataUltil.setData("error", "Từ khóa đã tồn tại");
				return ResponseEntity.ok(map);
			}
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		service.delete(id);
		HashMap<String, Object> map=DataUltil.setData("ok", "Xóa thành công");
		return ResponseEntity.ok(map);
	}
}
