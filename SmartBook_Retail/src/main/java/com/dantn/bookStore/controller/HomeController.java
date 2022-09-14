package com.dantn.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("retail")
	public String index() {
		return "index";
	}
}
