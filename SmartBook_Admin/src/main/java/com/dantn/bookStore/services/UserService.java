package com.dantn.bookStore.services;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.UserRequest;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.repositories.IUserRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.UserRoleSingleton;
import com.dantn.bookStore.ultilities.UserStatusSingleton;

@Service
public class UserService {
	private IUserRepository repository;

	public UserService(IUserRepository repository) {
		super();
		this.repository = repository;
	}
	public List<User> getUserByRole(UserRole role){
		return this.repository.findByRole(role);
	}
	public User getUserByNotRoleAndEmail(UserRole role,String email) {
	    return this.repository.findByNotRoleAndEmail(role, email);
	}
	public User getUserByRoleAndEmail(UserRole role,String email) {
		return this.repository.findByRoleAndEmail(role, email);
	}
	public User getByEmail(String email) {
		return this.repository.findByEmail(email);
	}
	public Page<User> getUserByRole(UserRole role,Integer page,String fullname){
		return this.repository.findByRoleAndFullname(role, "%"+fullname+"%",PageRequest.of(page, AppConstraint.PAGE_NUM));
	}
	public User save(User user) {
		return this.repository.save(user);
	}
	public User getById(Integer id) {
		Optional<User> optional=this.repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}	
	public List<User> getall(){
		return repository.findAll();
	}
	public HashMap<String, Object> disabled(Integer id,String action,Principal principal,UserStatusService userStatusService){
	    String email = principal.getName();
        User u = this.getById(id);
        HashMap<String, Object> map;
        if (email.equals(u.getEmail())) {
            map = DataUltil.setData("error", "Không thể tự vô hiệu bản thân");
        } else {
            if ("D".equals(action)) {
                u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(1));
                this.save(u);
                map = DataUltil.setData("ok", "Vô hiệu thành công");
            } else if ("A".equals(action)) {
                u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
                this.save(u);
                map = DataUltil.setData("ok", "Tài khoản đã được hoạt động");
            }else {
                map = DataUltil.setData("error", "Hành động sai trái");
            }
        }
        return map;
	}
	public HashMap<String, Object> create(UserRequest request,UserRoleService userRoleService,UserStatusService userStatusService){
	    User u=request.changeToEntity(new User());
        u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
        u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
        try {
            this.save(u);
            HashMap<String, Object> map=DataUltil.setData("ok", "Thêm thành công");
            return map;
        } catch (Exception e) {
            HashMap<String, Object> map=DataUltil.setData("eError", "Email trùng lặp");
            return map;
        }
	}
	public HashMap<String, Object> update(Integer id,UserRequest request,UserRoleService userRoleService,UserStatusService userStatusService){
	    User user=this.getById(request.getId());
        User u=request.changeToEntity(user);
        u.setStatus(UserStatusSingleton.getInstance(userStatusService).get(0));
        u.setRole(UserRoleSingleton.getInstance(userRoleService).get(request.getRole()));
        try {
            this.save(u);
            HashMap<String, Object> map=DataUltil.setData("ok", "Chỉnh sửa thành công");
            return map;
        } catch (Exception e) {
            HashMap<String, Object> map=DataUltil.setData("eError", "Email trùng lặp");
            return map;
        }
	}
}
