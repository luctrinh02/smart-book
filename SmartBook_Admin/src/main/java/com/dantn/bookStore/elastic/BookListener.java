package com.dantn.bookStore.elastic;

import java.io.IOException;
import java.util.List;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.services.CharactorService;
import com.dantn.bookStore.services.ContentService;
import com.dantn.bookStore.services.EBookService;
import com.dantn.bookStore.services.TypeService;
@Configurable
public class BookListener {
    @Autowired
    private ObjectFactory<EBookService> service;
    @Autowired
    private ObjectFactory<ContentService> contentService;
    @Autowired
    private ObjectFactory<TypeService> typeService;
    @Autowired
    private ObjectFactory<CharactorService> charactorService;
    @PostPersist
    public void create(Book book) throws IOException {
        EBook eBook=getEBook(book);
        service.getObject().save(eBook);
    }
    @PostUpdate
    public void update(Book book) throws BeansException, IOException {
       if(book.getStatus().getId()==2) {
           service.getObject().delete(book.getId()+"");
       }else {
           if(book.getAmount()==0) {
               service.getObject().delete(book.getId()+"");
           }else {
               EBook eBook=getEBook(book);
               service.getObject().save(eBook);
           }
       }
    }
    private EBook getEBook(Book book) {
        EBook eBook=new EBook();
        eBook.setId(book.getId()+"");
        eBook.setAuthor(book.getAuthor().getName());
        eBook.setPublisher(book.getPublisher().getName());
        eBook.setName(book.getName());
        eBook.setCharactor(charactorService.getObject().getEvalue(book.getCharactor()));
        eBook.setContent(contentService.getObject().getEvalue(book.getContent()));
        eBook.setType(typeService.getObject().getEvalue(book.getType()));
        return eBook;
    }
}
