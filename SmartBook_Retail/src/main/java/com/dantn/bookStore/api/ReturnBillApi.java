package com.dantn.bookStore.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.ReturnRequest;
import com.dantn.bookStore.services.ReturnBillService;

@RestController
public class ReturnBillApi {
	@Autowired
	private ReturnBillService service;

	@PostMapping("/api/return")
	public ResponseEntity<?> create(@RequestBody List<ReturnRequest> requests) {
		HashMap<String, Object> map=service.create(requests);
		return ResponseEntity.ok(map);
	}

	@PostMapping("/api/return/before")
	public ResponseEntity<?> before(@RequestBody List<ReturnRequest> requests) {
		HashMap<String, Object> map=service.beforeCreate(requests);
		return ResponseEntity.ok(map);
	}
}
