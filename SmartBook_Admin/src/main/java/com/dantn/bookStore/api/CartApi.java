package com.dantn.bookStore.api;


import java.security.Principal;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping("/api/product/{id}")
	public ResponseEntity<?> addToCart(@PathVariable("id") Integer id,
			@RequestParam(name = "amount") Long amount
			,Principal principal) {
		Book book=this.bookService.getById(id);
		if(amount>book.getAmount()) {
			HashMap<String, Object> map=DataUltil.setData("error", "Số lượng sách không đủ");
			return ResponseEntity.ok(map);
		}else{
			User user=userService.getByEmail(principal.getName());
			CartPK pk=new CartPK();
			pk.setBookId(book.getId());
			pk.setUserId(user.getId());
			Cart cart=new Cart();
			cart.setUser(user);
			cart.setAmount(amount);
			cart.setBook(book);
			cart.setCartPK(pk);
			cart=cartService.save(cart);
			HashMap<String, Object> map=DataUltil.setData("ok", "Thêm vào giỏ thành công");
			return ResponseEntity.ok(map);
		}
	}
	@DeleteMapping("/api/product/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id",required = false) Integer id,Principal principal){
		User user=userService.getByEmail(principal.getName());
		if(id==null) {
			this.cartService.deleteAll(user);
			return ResponseEntity.ok("Xóa thành công");
		}else {
			Book book=this.bookService.getById(id);
			CartPK pk=new CartPK();
			pk.setBookId(book.getId());
			pk.setUserId(user.getId());
			this.cartService.delete(pk);
			return ResponseEntity.ok("Xóa thành công");
		}
	}
}
