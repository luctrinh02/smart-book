package com.dantn.bookStore.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/smart-book")
public class HomeController {

	@GetMapping("")
	public String index() {
		return "index";
	}
	@GetMapping("/type")
	public String indexType() {
		return "book/type/index";
	}
	
	@GetMapping("/publisher")
	public String indexPublisher() {
		return "book/publisher/index";
	}
	@GetMapping("/author")
	public String indexAuthor() {
		return "book/author/index";
	}
	
	@GetMapping("/content")
	public String indexContent() {
		return "book/content/index";
	}
	
	@GetMapping("/charactor")
	public String indexCharactor() {
		return "book/charactor/index";
	}
	@GetMapping("/book")
	public String indexBook() {
		return "book/book/index";
	}
	
	@GetMapping("/smart-book/user/admin")
	public String indexAdmin() {
		return "user/admin/index";
	}
	
	@GetMapping("/user/guest")
	public String indexGuest() {
		return "user/guest/index";
	}
	
	@GetMapping("/user/shipper")
	public String indexShipper() {
		return "user/shipper/index";
	}
	
	@GetMapping("/createUser")
	public String createUser() {
		return "user/create";
	}
	
	@GetMapping("/profileUser")
	public String profileUser() {
		return "user/profile";
	}

	
	@GetMapping("/home")
	public String home() {
		return "control/home/index";
	}
	
	@GetMapping("/book/create")
	public String createBook() {
		return "book/book/create";
	}
	
	@GetMapping("/book/update")
	public String updateBook() {
		return "book/book/update";
	}
	@GetMapping("/smart-book/login")
    public String login(@RequestParam(name="error",required = false,defaultValue = "false") Boolean error) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
	    if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
	        return "control/login/index";
	    }else {
	        return "redirect:/admin/smart-book#/home";
	    }
    }
}
