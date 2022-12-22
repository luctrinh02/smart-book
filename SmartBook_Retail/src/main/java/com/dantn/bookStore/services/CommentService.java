package com.dantn.bookStore.services;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.CommentRequest;
import com.dantn.bookStore.dto.response.BookRedis;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Comment;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.ICommentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class CommentService {
	@Autowired
	private ICommentRepository repository;
	@Autowired
	private BillDetailService service;
	@Autowired
	private RedisTemplate<Object, Object> template;
	@Autowired
	private UserService userService;
	public Comment create(CommentRequest request,Principal principal) {
		if(!isValidRequest(request)) {
			return null;
		}
		//checking wrong letter
		
		//
		BillDetail detail=service.getById(request.getDetailId());
		detail.setIsComment(true);
		service.save(detail);
		Book b=detail.getBook();
		Comment cm=new Comment();
		cm=request.changeToEntity(cm);
		cm.setBook(b);
		User user=userService.getByEmail(principal.getName());
		cm.setUser(user);
		BookRedis redis=(BookRedis) template.opsForValue().get(b.getId());
		if(redis==null) {
			redis=new BookRedis(b,(long) request.getRate(),(long) 1);
		}else {
			redis.setEvaluate(redis.getEvaluate()+1);
			redis.setPoint(request.getRate()+redis.getPoint());
		}
		template.opsForValue().set(b.getId(), redis);
		return repository.save(cm);
	}
	public boolean isValidRequest(CommentRequest request) {
		BillDetail detail=service.getById(request.getDetailId());
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(detail==null) {
			return false;
		}else {
			if(detail.getBill().getStatus().getId()==7) {
				if(detail.getBill().getUser().getEmail().equals(authentication.getName())) {
					return !detail.getIsComment();
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
	}
}
