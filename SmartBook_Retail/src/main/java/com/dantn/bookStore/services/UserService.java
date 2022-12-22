package com.dantn.bookStore.services;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.UserPasswordRequest;
import com.dantn.bookStore.dto.request.UserRequest;
import com.dantn.bookStore.dto.request.UserUpdateRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRepository;
import com.dantn.bookStore.repositories.IWardRepository;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.UserRoleSingleton;
import com.dantn.bookStore.ultilities.UserStatusSingleton;

@Service
public class UserService {
	private IUserRepository repository;
	private IWardRepository iWardRepository;
	

	public UserService(IUserRepository repository, IWardRepository iWardRepository) {
		super();
		this.repository = repository;
		this.iWardRepository = iWardRepository;
	}

	public List<User> getUserByRole(UserRole role) {
		return this.repository.findByRole(role);
	}

	public User getUserByRoleAndEmail(UserRole role, String email) {
		return this.repository.findByRoleAndEmail(role, email);
	}

	public User getByEmail(String email) {
		return this.repository.findByEmail(email);
	}

	public User getById(Integer id) {
		Optional<User> optional = this.repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	public Integer registry(UserRequest request, UserStatusService statusService,
			UserRoleService roleService) {
		User u = new User();
		u.setWard(iWardRepository.findById(request.getWard()).get());
		u = request.changeToEntity(u);
		u.setStatus(UserStatusSingleton.getInstance(statusService).get(0));
		u.setRole(UserRoleSingleton.getInstance(roleService).get(0));
		HashMap<String, Object> map;
		try {
			this.repository.save(u);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public HashMap<String, Object> profile(UserUpdateRequest request, Principal principal)
			throws IllegalStateException, IOException {
		User u = this.getByEmail(principal.getName());
		u.setAddress(request.getFullname());
		u.setEmail(request.getEmail());
		u.setFullname(request.getFullname());
		u.setPhoneNumber(request.getPhoneNumber());
		if (!request.getFile().isEmpty()) {
			String fileName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
			File file = new File(
					new File("src\\main\\resources\\static\\imgUpload").getAbsolutePath() + "/" + fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			request.getFile().transferTo(file);
			u.setImg(fileName);
		}
		try {
			this.repository.save(u);
			HashMap<String, Object> map = DataUltil.setData("ok", "Thay đổi thông tin thành công");
			return map;
		} catch (Exception e) {
			HashMap<String, Object> map = DataUltil.setData("email", "Email không được trùng lặp");
			return map;
		}
	}

	public HashMap<String, Object> changePassword(UserPasswordRequest request, Principal principal) {
		User u = this.getByEmail(principal.getName());
		HashMap<String, Object> map;
		if ("".equals(request.getPassword())) {
			map = DataUltil.setData("error", "lỗi");
			map.put("password", "Không bỏ trống password");
		} else if (request.getPassword().equals(request.getConfirm())) {
			map = DataUltil.setData("error", "lỗi");
			map.put("password", "Xác nhận mật khẩu không chính xác");
		} else {
			u.setPassword(request.getPassword());
			this.repository.save(u);
			map = DataUltil.setData("ok", "Đổi mật khẩu thành công");
		}
		return map;
	}
}
