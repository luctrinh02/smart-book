package com.dantn.bookStore.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.xml.crypto.Data;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.UserRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.entities.UserStatus;
import com.dantn.bookStore.services.UserRoleService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.services.UserStatusService;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.UserRoleSingleton;
import com.dantn.bookStore.ultilities.UserStatusSingleton;

@RestController
@RequestMapping("/api/admin/user")
public class UserApi {
	private UserService userService;
	private UserRoleService userRoleService;
	private UserStatusService userStatusService;

	public UserApi(UserService userService, UserRoleService userRoleService, UserStatusService userStatusService) {
		super();
		this.userService = userService;
		this.userRoleService = userRoleService;
		this.userStatusService = userStatusService;
	}

	@GetMapping("")
	public ResponseEntity<?> getUserByRole(@RequestParam("role") Integer role, @RequestParam("page") Integer num,
			@RequestParam(name = "fullname", defaultValue = "") String fullname) {
		UserRole urole = UserRoleSingleton.getInstance(userRoleService).get(role);
		Page<User> page = userService.getUserByRole(urole, num, fullname);
		HashMap<String, Object> map = DataUltil.setData("ok", page);
		return ResponseEntity.ok(map);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
		User u=userService.getById(id);
		HashMap<String, Object> map = DataUltil.setData("ok", u);
		return ResponseEntity.ok(map);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<?> disable(@PathVariable("id") Integer id, @RequestBody String action, Principal principal) {
		String email = principal.getName();
		User u = userService.getById(id);
		HashMap<String, Object> map;
		if (email.equals(u.getEmail())) {
			map = DataUltil.setData("error", "Không thể tự vô hiệu bản thân");
		} else {
			if ("D".equals(action)) {
				u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(1));
				userService.save(u);
				map = DataUltil.setData("ok", "Vô hiệu thành công");
			} else if ("A".equals(action)) {
				u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
				userService.save(u);
				map = DataUltil.setData("ok", "Tài khoản đã được hoạt động");
			}else {
				map = DataUltil.setData("error", "Hành động sai trái");
			}
		}
		return ResponseEntity.ok(map);
	}
	@PostMapping("/validation")
	public ResponseEntity<?> validation(
			@RequestBody @Valid UserRequest request,BindingResult result
			){
		if(result.hasErrors()) {
			List<ObjectError> list=result.getAllErrors();
			HashMap<String, Object> map=DataUltil.setData("error", list);
			return ResponseEntity.ok(map);
		}else {
			HashMap<String, Object> map=DataUltil.setData("ok", "");
			return ResponseEntity.ok(map);
		}
	}
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody UserRequest request){
		User u=request.changeToEntity(new User());
		u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
		u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
		try {
			userService.save(u);
			HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			HashMap<String, Object> map=DataUltil.setData("eError", "Email trùng lặp");
			return ResponseEntity.ok(map);
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id,@RequestBody UserRequest request){
		User user=userService.getById(request.getId());
		User u=request.changeToEntity(user);
		u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
		u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
		try {
			userService.save(u);
			HashMap<String, Object> map=DataUltil.setData("ok", "sửa thành công");
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			HashMap<String, Object> map=DataUltil.setData("eError", "Email trùng lặp");
			return ResponseEntity.ok(map);
		}
	}
}
