package com.dantn.bookStore.controller;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/smart-book")
public class HomeController {
	@Autowired
	private MailUtil mail;

	@Autowired
	private UserService userService;

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
	
	@GetMapping("/user/admin")
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
		return "user/update";
	}
	@GetMapping("/profile")
	public String profile() {
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
	
	@GetMapping("/bill")
	public String bill() {
		return "bill/bill";
	}
	@GetMapping("/returnBill")
	public String returnBill() {
		return "returnBill/returnBill";
	}
	@GetMapping("/shipment")
	public String shipment() {
		return "shipment/shipment";
	}
	
	@GetMapping("/login")
    public String login(@RequestParam(name="error",required = false,defaultValue = "false") Boolean error) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
	    if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
	        return "control/login/index";
	    }else {
	        return "redirect:/admin/smart-book#/home";
	    }
    }
	@GetMapping("/logout")
	public String logout() {
		return "control/login/index";
	}

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "control/login/forgot_password";
	}

	private String getRandomPassword() {
		String rds = "123456789";
		return rds;
	}
	@PostMapping("/forgot_password")
	public String forgotPost(ModelMap map, HttpServletRequest request, String email,String password) {
		String newPass = userService.getEncoder().encode(password);
		User u = userService.getByEmail(email);
		if (u == null) {
			request.getAttribute("error");
			return "control/login/forgot_password";
		} else {
			u.setPassword(newPass);
			try {
				userService.update(u);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mail.sendEmail(u.getEmail(), "Mật khẩu mới là: " + newPass,
					"Hãy bảo mật mail này hoặc đổi mật khẩu ngay sau khi đăng nhập");
			request.getSession().setAttribute("message", "Mật khẩu mới đã được gửi tới email!");
			return "redirect:/admin/smart-book/logout";
		}
	}
}
