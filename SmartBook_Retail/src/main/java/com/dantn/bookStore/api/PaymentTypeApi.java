package com.dantn.bookStore.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.services.PaymentTypeService;
import com.dantn.bookStore.ultilities.PaymentTypeSingleton;

@RestController
@RequestMapping("/api/paymentType")
public class PaymentTypeApi {
	private PaymentTypeService wardService;

	public PaymentTypeApi(PaymentTypeService wardService) {
		super();
		this.wardService = wardService;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(PaymentTypeSingleton.getInstance(wardService));
	}
}
