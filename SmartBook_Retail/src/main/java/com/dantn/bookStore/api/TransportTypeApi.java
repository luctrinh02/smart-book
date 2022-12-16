package com.dantn.bookStore.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.services.TransportTypeService;
import com.dantn.bookStore.ultilities.TransportTypeSingleton;

@RestController
@RequestMapping("/api/transportType")
public class TransportTypeApi {
	private TransportTypeService wardService;

	public TransportTypeApi(TransportTypeService wardService) {
		super();
		this.wardService = wardService;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(TransportTypeSingleton.getInstance(wardService));
	}
}
