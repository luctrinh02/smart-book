package com.dantn.bookStore.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.ReturnRequest;
import com.dantn.bookStore.services.ReturnBillService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
public class ReturnBillApi {
	@Autowired
	private ReturnBillService service;

	@PostMapping("/api/return")
	public ResponseEntity<?> create(@RequestBody List<ReturnRequest> requests) {
		HashMap<String, Object> map = service.create(requests);
		return ResponseEntity.ok(map);
	}

	@PostMapping("/api/return/before")
	public ResponseEntity<?> before(@RequestBody List<ReturnRequest> requests) {
		HashMap<String, Object> map = service.beforeCreate(requests);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/api/return")
	public ResponseEntity<?> get(@RequestParam(name = "page", defaultValue = "0") Integer pageNum,
			@RequestParam(name = "transn", required = false) String tranSn, Principal principal) {
		if (tranSn == null || "".equals(tranSn)) {
			return ResponseEntity.ok(service.getByUser(principal, pageNum));
		} else {
			return ResponseEntity.ok(service.getByBill(tranSn, pageNum));
		}
	}

	@PutMapping("/api/return")
	public ResponseEntity<?> cancel(@RequestBody Integer id) {
		try {
			if (service.cancel(id)) {
				HashMap<String, Object> map = DataUltil.setData("ok", "Hủy thành công");
				return ResponseEntity.ok(map);
			} else {
				HashMap<String, Object> map = DataUltil.setData("error", "Không thể hủy đơn");
				return ResponseEntity.ok(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			HashMap<String, Object> map = DataUltil.setData("error", "Hệ thống bận");
			return ResponseEntity.ok(map);
		}
	}

	@GetMapping("/api/return/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(service.getDetail(id));
	}
}
