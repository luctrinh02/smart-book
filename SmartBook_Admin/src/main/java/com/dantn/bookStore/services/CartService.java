package com.dantn.bookStore.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Cart;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.ICartRepository;

@Service
public class CartService {
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
}
