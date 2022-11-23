package com.dantn.bookStore.api;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.ProfileRequest;
import com.dantn.bookStore.dto.request.UserPasswordRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
public class AuthenticationApi {
	@Autowired
	private UserService service;

	@GetMapping("/api/admin/pricipal")
	public ResponseEntity<?> pricipal(Principal principal) {
		if (principal == null) {
			return null;
		} else {
			User u = service.getByEmail(principal.getName());
			return ResponseEntity.ok(u);
		}
	}

	@PutMapping("/api/admin/change")
	public ResponseEntity<?> changePass(@RequestBody UserPasswordRequest request, Principal principal) {
		HashMap<String, Object> map = service.changePass(request, principal);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/api/admin/profile")
	public ResponseEntity<?> profile(Principal principal) {
		User user = service.getByEmail(principal.getName());
		return ResponseEntity.ok(user);
	}

	@PutMapping("/api/admin/profile/before")
	public ResponseEntity<?> check(@RequestBody @Valid ProfileRequest request, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("error", list);
			return ResponseEntity.ok(map);
		} else {
			HashMap<String, Object> map = DataUltil.setData("ok", "");
			return ResponseEntity.ok(map);
		}
	}

	@PutMapping("/api/admin/profile")
	public ResponseEntity<?> update(@ModelAttribute @Valid ProfileRequest request, BindingResult result,
			Principal principal) throws IllegalStateException, IOException {
		if (result.hasErrors()) {
			return ResponseEntity.ok(result.getAllErrors());
		} else {
			 return service.update(request, principal);
		}

	}
}
