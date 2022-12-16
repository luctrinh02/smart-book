package com.dantn.bookStore.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.services.CityService;
import com.dantn.bookStore.ultilities.LandBoundarySingleton;

@RestController
@RequestMapping("/api/city")
public class LandBoundaryApi {
	private CityService wardService;

	public LandBoundaryApi(CityService wardService) {
		super();
		this.wardService = wardService;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(LandBoundarySingleton.getInstance(wardService));
	}
}
