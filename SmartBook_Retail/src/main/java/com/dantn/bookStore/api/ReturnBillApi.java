package com.dantn.bookStore.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.ReturnBill;

@RestController
public class ReturnBillApi {
	@PostMapping("/api/returnBill")
	public ResponseEntity<?> add(@RequestBody List<ReturnBill> bill){
		return null;
	}
	@PostMapping("/api/returnBill/check")
	public ResponseEntity<?> check(@RequestBody List<ReturnBill> bill){
		return null;
	}
}
