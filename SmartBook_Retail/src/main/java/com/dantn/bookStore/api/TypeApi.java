package com.dantn.bookStore.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Type;
import com.dantn.bookStore.services.TypeService;

@RestController
public class TypeApi {
	@Autowired
	private TypeService service;
	
	@GetMapping("/api/type")
	public ResponseEntity<?> getAllType() {
		HashMap<String, Object> mapType = new HashMap<String, Object>();
		List<Type> list = service.getAll();
		for (Type type : list) {
			mapType.put(type.getId().toString(), type.getValue());
		}
		return ResponseEntity.ok(mapType);
	}
}
