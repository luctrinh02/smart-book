package com.dantn.bookStore.elastic;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "books")
public class EBook {
	@Id
	private String id;
	@Field(type = FieldType.Text,name = "name")
	private String name;
	@Field(type = FieldType.Text,name = "author")
	private String author;
	@Field(type = FieldType.Text,name = "publisher")
	private String publisher;
	@Field(type = FieldType.Text,name = "type")
	private String type;
	@Field(type = FieldType.Text,name = "charactor")
	private String charactor;
	@Field(type = FieldType.Text,name = "content")
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCharactor() {
		return charactor;
	}
	public void setCharactor(String charactor) {
		this.charactor = charactor;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
