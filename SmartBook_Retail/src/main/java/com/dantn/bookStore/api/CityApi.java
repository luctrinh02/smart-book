package com.dantn.bookStore.api;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.services.CityService;
import com.dantn.bookStore.ultilities.LandBoundarySingleton;

@RestController
@RequestMapping("/api/city")
public class CityApi {
	private CityService wardService;

	public CityApi(CityService wardService) {
		super();
		this.wardService = wardService;
	}
	
	@GetMapping("")
	public HashMap<String, Object> getAll() {
		return LandBoundarySingleton.getInstance(wardService);
	}
}
