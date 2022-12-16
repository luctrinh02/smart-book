package com.dantn.bookStore.api;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillCreateRequest;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Cart;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.CartService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
public class CartApi {
	private UserService userService;
	private CartService cartService;
	private BookService bookService;
	
	public CartApi(UserService userService, CartService cartService, BookService bookService) {
		super();
		this.userService = userService;
		this.cartService = cartService;
		this.bookService = bookService;
	}
	@PostMapping("/api/book/{id}")
	public ResponseEntity<?> addToCart(@PathVariable("id") Integer id,
			@RequestParam(name = "amount") Long amount
			,Principal principal) {
		HashMap<String, Object> map=cartService.addToCart(id, amount, principal, userService, bookService);
		return ResponseEntity.ok(map);
	}
	@PostMapping("/api/cart")
	public ResponseEntity<?> delete(@RequestBody CartPK id,Principal principal){
		User user=userService.getByEmail(principal.getName());
		if(id==null) {
			this.cartService.deleteAll(user);
		}else {
			this.cartService.delete(id);
		}
		HashMap<String, Object> map=DataUltil.setData("ok", "Xóa thành công");
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/api/cart")
	public ResponseEntity<?> get(Principal principal){
		User user=userService.getByEmail(principal.getName());
		List<Cart> carts=cartService.getByUser(user);
		return ResponseEntity.ok(carts);
	}
	@PutMapping("/api/cart")
	public ResponseEntity<?> update(@RequestBody CartPK cartPK,@RequestParam("amount") Long amount){
		HashMap<String, Object> map=cartService.update(cartPK, amount);
		return ResponseEntity.ok(map);
	}
}
