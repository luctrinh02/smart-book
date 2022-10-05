package com.dantn.bookStore.api;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Charactor;
import com.dantn.bookStore.services.CharactorService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/admin/charactor")
public class CharactorApi {
	private CharactorService charactorService;

	public CharactorApi(CharactorService charactorService) {
		super();
		this.charactorService = charactorService;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getCharactor(
			@RequestParam("page") Integer pageNum
			,@RequestParam(name = "value",defaultValue = "") String value){
		Page<Charactor> page=charactorService.getByKeyWord(value, pageNum);
		return ResponseEntity.ok(page);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){
		Charactor character=charactorService.getById(id);
		return ResponseEntity.ok(character);
	}
	@PostMapping("")
	public ResponseEntity<?> add(@RequestBody String value){
		if("".equals(value.trim()) || value==null) {
			HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập tên nhân vật!");
			return ResponseEntity.ok(map);
		}else {
			Charactor c=new Charactor();
			c.setValue(value.trim().toLowerCase()	);
			try {
				charactorService.save(c);
				HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				HashMap<String, Object> map=DataUltil.setData("error", "Tên nhân vật không trùng lặp");
				return ResponseEntity.ok(map);
			}
		}
	}
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody Charactor charactor){
		if(charactor.getValue()==null || "".equals(charactor.getValue().trim())) {
			HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập tên nhân vật!");
			return ResponseEntity.ok(map);
		}else {
			try {
				charactor.setValue(charactor.getValue().trim());
				charactorService.save(charactor);
				HashMap<String, Object> map=DataUltil.setData("ok", "Sửa thành công");
				return ResponseEntity.ok(map);
			} catch (Exception e) {
				HashMap<String, Object> map=DataUltil.setData("error", "Tên nhân vật không trùng lặp");
				return ResponseEntity.ok(map);
			}
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id){
		charactorService.delete(id);
		HashMap<String, Object> map=DataUltil.setData("ok", "Xóa nhân vật thành công");
		return ResponseEntity.ok(map);
	}
}
