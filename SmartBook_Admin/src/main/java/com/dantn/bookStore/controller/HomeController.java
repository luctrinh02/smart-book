package com.dantn.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {
	@GetMapping("/smart-book")
	public String index() {
		return "index";
	}
	@GetMapping("/smart-book/type")
	public String indexType() {
		return "book/type/index";
	}
	
	@GetMapping("/smart-book/publisher")
	public String indexPublisher() {
		return "book/publisher/index";
	}
	@GetMapping("/smart-book/author")
	public String indexAuthor() {
		return "book/author/index";
	}
	
	@GetMapping("/smart-book/content")
	public String indexContent() {
		return "book/content/index";
	}
	
	@GetMapping("/smart-book/charactor")
	public String indexCharactor() {
		return "book/charactor/index";
	}
	@GetMapping("/smart-book/book")
	public String indexBook() {
		return "book/book/index";
	}
	
	@GetMapping("/smart-book/login")
	public String indexLogin() {
		return "control/login/index";
	}
	
	
	@GetMapping("/smart-book/user/admin")
	public String indexAdmin() {
		return "user/admin/index";
	}
	
	@GetMapping("/smart-book/user/guest")
	public String indexGuest() {
		return "user/guest/index";
	}
	
	@GetMapping("/smart-book/user/shipper")
	public String indexShipper() {
		return "user/shipper/index";
	}
	
	@GetMapping("/smart-book/createUser")
	public String createUser() {
		return "user/create";
	}
	
	@GetMapping("/smart-book/profileUser")
	public String profileUser() {
		return "user/profile";
	}
	
}
