package com.dantn.bookStore.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.CommentRequest;
import com.dantn.bookStore.entities.Comment;
import com.dantn.bookStore.services.CommentService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
public class CommentApi {
	@Autowired
	private CommentService service;
	@PostMapping("/api/comment")
	public ResponseEntity<?> addComment(@RequestBody @Valid CommentRequest request,BindingResult result){
		if(result.hasErrors()) {
			List<ObjectError> errors=result.getAllErrors();
			return ResponseEntity.ok(DataUltil.setData("error", errors));
		}else {
			Comment m=service.create(request);
			if(m==null) {
				return ResponseEntity.ok(DataUltil.setData("402", "Lỗi dữ liệu"));
			}else {
				return ResponseEntity.ok(DataUltil.setData("ok", "Đánh giá thành công"));
			}
		}
	}
}
