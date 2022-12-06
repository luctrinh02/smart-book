package com.dantn.bookStore.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.CommentRequest;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Comment;
import com.dantn.bookStore.repositories.ICommentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;

@Service
public class CommentService {
	@Autowired
	private ICommentRepository repository;
	@Autowired
	private BookService bookService;
	@Autowired
	private BillDetailService service;
	public Comment create(CommentRequest request) {
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
		cm.setUser(AppConstraint.USER);
		b.setPoint(b.getPoint()+request.getRate());
		b.setEvaluate(b.getEvaluate()+1);
		bookService.save(b);
		return repository.save(cm);
	}
	public boolean isValidRequest(CommentRequest request) {
		BillDetail detail=service.getById(request.getDetailId());
		if(detail==null) {
			return false;
		}else {
			if(detail.getBill().getStatus().getId()==5) {
				if(detail.getBill().getUser().equals(AppConstraint.USER)) {
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
