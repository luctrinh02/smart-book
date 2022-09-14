package com.dantn.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/smart-book")
	public String index() {
		return "index";
	}
	@GetMapping("/smart-book/cart")
	public String cart() {
		return "cart/cart";
	}
}
