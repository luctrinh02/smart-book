package com.dantn.bookStore.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Book;

@Service
public class SuggestService {
	private UserClickService clickService;
	private UserSearchService searchService;
	private UserBuyService buyService;
	private BookService bookService;
    private ContentService contentService;
    private TypeService typeService;
    private CharactorService charactorService;
	private UserClickRelationService clickRelationService;
	public SuggestService(UserClickService clickService, UserSearchService searchService, UserBuyService buyService,
			BookService bookService, ContentService contentService, TypeService typeService,
			CharactorService charactorService, UserClickRelationService clickRelationService) {
		super();
		this.clickService = clickService;
		this.searchService = searchService;
		this.buyService = buyService;
		this.bookService = bookService;
		this.contentService = contentService;
		this.typeService = typeService;
		this.charactorService = charactorService;
		this.clickRelationService = clickRelationService;
	}
	public List<Book> getSuggest(EBookService service) throws IOException{
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
	    if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
	    	List<Book> books=bookService.getall(24).getContent();
	    	return books;
	    }else {
	    	//lấy mua
			Set<Book> buyList=buyService.getBook();
			//lấy click
			List<Book> clickList=clickService.getByUser();
			Set<Book> clickSet=new HashSet<>(clickList);
			//lấy từ liên quan click
			String key=clickRelationService.getKey();
			if(!"".equals(key)) {
				List<Book> relationList=service.getBook(key);
				clickSet.addAll(new HashSet<>(relationList));
			}
			if(buyList.size()!=0) {
				clickSet.removeAll(buyList);
			}
			//click nhiều quá thì dừng
			if(clickList.size()>=24) {
				return new ArrayList<>(clickSet);
			}else {
				//lấy từ search
				List<Book> searchList=new ArrayList<>();
				String search=searchService.getKey();
				if(!"".equals(search)) {
					searchList=service.getBook(search);
				}
				Set<Book> searchSet=new HashSet<>(searchList);
				clickSet.addAll(searchSet);
				if(buyList.size()!=0) {
					clickSet.removeAll(buyList);
				}
				//nhiều quá thì dừng
				if(buyList.size()!=0) {
					clickSet.removeAll(buyList);
				}
				if(clickSet.size()>24) {
					return new ArrayList<>(clickSet).subList(0, 24);
				}else {
					//lấy từ cái liên quan mua
					if(buyList.size()!=0) {
						Book book=buyList.iterator().next();
						StringBuilder builder=new StringBuilder();
						builder.append(charactorService.getEvalue(book.getCharactor())+" ");
						builder.append(contentService.getEvalue(book.getContent())+" ");
						builder.append(typeService.getEvalue(book.getType())+" ");
						builder.append(book.getAuthor().getName()+" ");
						builder.append(book.getPublisher().getName()+" ");
						builder.append(book.getName()+" ");
						searchList=service.getBook(search);
						clickSet.addAll(new HashSet<>(searchList));
						clickSet.removeAll(buyList);
					}
					//nhiều quá thì dừng
					if(clickSet.size()>24) {
						return new ArrayList<>(clickSet).subList(0, 24);
					}else {
						int lost=24-clickSet.size()+buyList.size();
						List<Book> books=bookService.getall(lost).getContent();
						clickSet.addAll(new HashSet<>(books));
						if(buyList.size()!=0) {
							clickSet.removeAll(buyList);
						}
						if(clickSet.size()>24) {
							return new ArrayList<>(clickSet).subList(0, 24);
						}else {
							return new ArrayList<>(clickSet);
						}
					}
				}
			}
	    }
	}
}
