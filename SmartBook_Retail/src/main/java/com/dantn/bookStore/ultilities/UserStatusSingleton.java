package com.dantn.bookStore.ultilities;

import java.util.List;


import com.dantn.bookStore.entities.UserStatus;
import com.dantn.bookStore.services.UserStatusService;

public class UserStatusSingleton {
	private static List<UserStatus> singleton;
	private UserStatusSingleton() {}
	public static List<UserStatus> getInstance(UserStatusService service) {
		if(singleton==null) {
			singleton=service.getAll();
		}
		return singleton;
	}
}
