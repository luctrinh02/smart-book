package com.dantn.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserController {
	@GetMapping("/smart-book/user")
	public String userIndex() {
		return "user/index";
	}
	@GetMapping("/smart-book/user/create")
	public String userCreate() {
		return "user/create";
	}
}
