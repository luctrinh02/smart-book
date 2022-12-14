package com.dantn.bookStore.services;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.ProfileRequest;
import com.dantn.bookStore.dto.request.UserPasswordRequest;
import com.dantn.bookStore.dto.request.UserRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRepository;
import com.dantn.bookStore.repositories.IWardRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.FileUtil;
import com.dantn.bookStore.ultilities.UserRoleSingleton;
import com.dantn.bookStore.ultilities.UserStatusSingleton;

@Service
public class UserService {
	@Autowired
	private IUserRepository repository;
	@Autowired
	private IWardRepository iWardRepository;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public IUserRepository getRepository() {
		return repository;
	}

	public void setRepository(IUserRepository repository) {
		this.repository = repository;
	}

	public BCryptPasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public List<User> getUserByRole(UserRole role) {
		return this.repository.findByRole(role);
	}

	public User getUserByNotRoleAndEmail(UserRole role, String email) {
		return this.repository.findByNotRoleAndEmail(role, email);
	}

	public User getUserByRoleAndEmail(UserRole role, String email) {
		return this.repository.findByRoleAndEmail(role, email);
	}

	public User getByEmail(String email) {
		return this.repository.findByEmail(email);
	}

	public Page<User> getUserByRole(UserRole role, Integer page, String fullname) {
		return this.repository.findByRoleAndFullname(role, "%" + fullname + "%",
				PageRequest.of(page, AppConstraint.PAGE_NUM));
	}

	public User save(User user) {
		return this.repository.save(user);
	}

	public User getById(Integer id) {
		Optional<User> optional = this.repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	public User update(User user) throws Exception {
		int id = user.getId();
		if (repository.findById(id).isPresent()) {
			return repository.save(user);
		} else {
			throw new Exception("User not found");
		}
	}

	public List<User> getall() {
		return repository.findAll();
	}

	public HashMap<String, Object> disabled(Integer id, String action, Principal principal,
			UserStatusService userStatusService) {
		String email = principal.getName();
		User u = this.getById(id);
		HashMap<String, Object> map;
		if (email.equals(u.getEmail())) {
			map = DataUltil.setData("error", "Kh??ng th??? t??? v?? hi???u b???n th??n");
		} else {
			if ("D".equals(action)) {
				u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(1));
				this.save(u);
				map = DataUltil.setData("ok", "V?? hi???u th??nh c??ng");
			} else if ("A".equals(action)) {
				u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
				this.save(u);
				map = DataUltil.setData("ok", "T??i kho???n ???? ???????c ho???t ?????ng");
			} else {
				map = DataUltil.setData("error", "H??nh ?????ng sai tr??i");
			}
		}
		return map;
	}

	public Integer create(UserRequest request, UserRoleService userRoleService,
			UserStatusService userStatusService) {
		User u = request.changeToEntity(new User());
		u.setWard(iWardRepository.findById(request.getWard()).get());
		String encode;
		try {
			encode = FileUtil.fileToBase64(request.getFile());
			u.setImg(encode);
		} catch (IOException e) {
			e.printStackTrace();
		}

		u.setPassword(encoder.encode(request.getPassword()));
		u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
		u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
		try {
			this.save(u);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public Integer update(Integer id, UserRequest request, UserRoleService userRoleService, UserStatusService userStatusService) {
		User user = this.getById(request.getId());
		User u = request.changeToEntity(user);
		String encode;
		if(request.getBase64()==null) {
			try {
				encode = FileUtil.fileToBase64(request.getFile());
				u.setImg(encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		u.setWard(iWardRepository.findById(request.getWard()).get());
		u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
		u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
		try {
			this.save(u);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public HashMap<String, Object> changePass(UserPasswordRequest request, Principal principal) {
		User user = this.getByEmail(principal.getName());
		if ("".equals(request.getOldPass()) || request.getOldPass() == null) {
			HashMap<String, Object> map = DataUltil.setData("error", "L???i d??? li???u");
			map.put("oldPass", "Vui l??ng nh???p m???t kh???u c??");
			map.put("newPass", "");
			map.put("confirm", "");
			return map;
		} else if (!encoder.matches(request.getOldPass(), user.getPassword())) {
			System.out.println(encoder.matches(request.getOldPass(), user.getPassword()));
			HashMap<String, Object> map = DataUltil.setData("error", "L???i d??? li???u");
			map.put("oldPass", "M???t kh???u c?? kh??ng ch??nh x??c");
			map.put("newPass", "");
			map.put("confirm", "");
			return map;
		} else if ("".equals(request.getNewPass()) || request.getNewPass() == null) {
			HashMap<String, Object> map = DataUltil.setData("error", "L???i d??? li???u");
			map.put("newPass", "Vui l??ng nh???p m???t kh???u m???i");
			map.put("oldPass", "");
			map.put("confirm", "");
			return map;
		} else if (!request.getNewPass().equals(request.getConfirm())) {
			HashMap<String, Object> map = DataUltil.setData("error", "L???i d??? li???u");
			map.put("confirm", "X??c nh???n m???t kh???u kh??ng tr??ng kh???p");
			map.put("oldPass", "");
			map.put("newPass", "");
			return map;
		} else {
			user.setPassword(encoder.encode(request.getNewPass()));
			this.save(user);
			HashMap<String, Object> map = DataUltil.setData("ok", "?????i m???t kh???u th??nh c??ng");
			return map;
		}
	}

	public ResponseEntity<?> update(ProfileRequest request, Principal principal)
			throws IllegalStateException, IOException {
		User user = this.getByEmail(principal.getName());
		user.setFullname(request.getFullname());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setAddress(request.getAddress());
		user.setEmail(request.getEmail());
		if (request.getFile() != null) {
			if (!request.getFile().isEmpty()) {
				String encode = FileUtil.fileToBase64(request.getFile());
				user.setImg(encode);
			}
		}
		try {
			this.save(user);
			List<SimpleGrantedAuthority> roles = new ArrayList<>();
			roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getValue()));
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), roles);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return ResponseEntity.ok(1);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(2);
		}
	}

}
