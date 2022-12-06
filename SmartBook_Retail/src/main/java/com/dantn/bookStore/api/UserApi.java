package com.dantn.bookStore.api;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.UserPasswordRequest;
import com.dantn.bookStore.dto.request.UserRequest;
import com.dantn.bookStore.dto.request.UserUpdateRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.UserRoleService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.services.UserStatusService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/user")
public class UserApi {
	private UserService service;
	private UserStatusService statusService;
	private UserRoleService roleService;

	public UserApi(UserService service, UserStatusService statusService, UserRoleService roleService) {
		super();
		this.service = service;
		this.statusService = statusService;
		this.roleService = roleService;
	}

	@GetMapping("")
	public ResponseEntity<?> profile(Principal principal) {
		User user=null;
		if(principal!=null) {
			user = service.getByEmail(principal.getName());
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/before")
	public ResponseEntity<?> check(@RequestBody @Valid UserRequest request, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("error", errors);
			return ResponseEntity.ok(map);
		} else {
			return ResponseEntity.ok(DataUltil.setData("ok", ""));
		}
	}

	@PostMapping("")
	public ResponseEntity<?> registry(@RequestBody @Valid UserRequest request, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("error", errors);
			return ResponseEntity.ok(map);
		} else {
			HashMap<String, Object> map = service.registry(request, statusService, roleService);
			return ResponseEntity.ok(map);
		}
	}

	@PutMapping("")
	public ResponseEntity<?> profile(@ModelAttribute @Valid UserUpdateRequest request, BindingResult result,
			Principal principal) throws IllegalStateException, IOException {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("ok", errors);
			return ResponseEntity.ok(map);
		} else {
			HashMap<String, Object> map = service.profile(request, principal);
			return ResponseEntity.ok(map);
		}
	}

	@PatchMapping("")
	public ResponseEntity<?> changePass(@RequestBody UserPasswordRequest request, Principal principal) {
		HashMap<String, Object> map = service.changePassword(request, principal);
		return ResponseEntity.ok(map);
	}
}
