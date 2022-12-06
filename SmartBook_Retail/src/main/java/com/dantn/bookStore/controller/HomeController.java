package com.dantn.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/smart-book")
	public String index() {
		return "index";
	}
	@GetMapping("/smart-book/home")
	public String home() {
		return "home/home";
	}
	@GetMapping("/smart-book/cart")
	public String cart() {
		return "cart/cart";
	}
	@GetMapping("/smart-book/history")
	public String history() {
		return "history/history";
	}
	@GetMapping("/smart-book/return")
	public String returnBill() {
		return "returnBill/return";
	}
	@GetMapping("/smart-book/book")
	public String detailBook() {
		return "book/detail";
	}
}
