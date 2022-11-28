package com.dantn.bookStore.services;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Cart;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserClick;
import com.dantn.bookStore.repositories.ICartRepository;
import com.dantn.bookStore.ultilities.DataUltil;

@Service
public class CartService {
	private static final Logger LOGGER=LoggerFactory.getLogger(CartService.class);
	private ICartRepository repository;

	public CartService(ICartRepository repository) {
		super();
		this.repository = repository;
	}
	public List<Cart> getByUser(User user){
		return this.repository.findByUser(user);
	}
	public Cart save(Cart cart) {
		return this.repository.save(cart);
	}
	public void delete(CartPK cartPK) {
		this.repository.deleteById(cartPK);
	}
	public void deleteByIds(List<CartPK> pks) {
		this.repository.deleteAllById(pks);
	}
	public void deleteAll(User user) {
		List<Cart> list=repository.findByUser(user);
		this.repository.deleteAll(list);
	}
	public Cart getById(CartPK cartPK) {
		Optional<Cart> optional=this.repository.findById(cartPK);
		return optional.isPresent()?optional.get():null;
	}
	public List<Cart> getByIds(List<CartPK> cartPKs){
		return this.repository.findAllById(cartPKs);
	}
	public HashMap<String, Object> addToCart(Integer id,Long amount,Principal principal,UserService userService,BookService bookService){
		Book book=bookService.getById(id);
		if(amount>book.getAmount()) {
			HashMap<String, Object> map=DataUltil.setData("error", "Số lượng sách không đủ");
			map.put("max", book.getAmount());
			return map;
		}else{
			User user=userService.getByEmail(principal.getName());
			CartPK pk=new CartPK();
			pk.setBookId(book.getId());
			pk.setUserId(user.getId());
			Cart c=this.getById(pk);
			UserClick u=new UserClick();
			u.setBook(book);
			u.setUser(user);
			LOGGER.info(u.toString());
			if(c==null) {
				Cart cart=new Cart();
				cart.setUser(user);
				cart.setAmount(amount);
				cart.setBook(book);
				cart.setCartPK(pk);
				cart=this.save(cart);
				HashMap<String, Object> map=DataUltil.setData("ok", "Thêm vào giỏ thành công");
				return map;
			}else {
				if(amount+c.getAmount()>book.getAmount()) {
					HashMap<String, Object> map=DataUltil.setData("error", "Số lượng sách không đủ");
					return map;
				}else {
					Cart cart=c;
					cart.setUser(user);
					cart.setAmount(amount+cart.getAmount());
					cart.setBook(book);
					cart.setCartPK(pk);
					cart=this.save(cart);
					HashMap<String, Object> map=DataUltil.setData("ok", "Thêm vào giỏ thành công");
					return map;
				}
			}
		}
	}
	public HashMap<String, Object> update(CartPK cartPK,Long amount){
		Cart cart=this.getById(cartPK);
		if(amount>cart.getBook().getAmount()) {
			HashMap<String, Object> map=DataUltil.setData("error", "Số lượng sản phẩm không đủ");
			map.put("max", cart.getAmount());
			return map;
		}else {
			cart.setAmount((long)amount);
			this.save(cart);
			return null;
		}
	}
}
