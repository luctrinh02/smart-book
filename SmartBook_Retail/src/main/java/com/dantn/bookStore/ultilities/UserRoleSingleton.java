package com.dantn.bookStore.ultilities;

import java.util.List;


import com.dantn.bookStore.entities.UserRole;
import com.dantn.bookStore.services.UserRoleService;

public class UserRoleSingleton {
	private static  List<UserRole> singleton;
	private UserRoleSingleton() {};
	public static List<UserRole> getInstance(UserRoleService service) {
		if (singleton==null) {
			singleton= service.getAll();
		}
		return singleton;
	}
}
