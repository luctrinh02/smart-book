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
	@Field(type = FieldType.Text,name = "ISBN")
	private String ISBN;
	@Field(type = FieldType.Text,name = "image")
	private String image;
	@Field(type = FieldType.Text,name = "numOfPage")
	private String numOfPage;
	@Field(type = FieldType.Text,name = "author")
	private String author;
	@Field(type = FieldType.Text,name = "publisher")
	private String publisher;
	@Field(type = FieldType.Text,name = "price")
	private String price;
	@Field(type = FieldType.Integer,name = "discount")
	private Integer discount;
	@Field(type = FieldType.Long,name = "amount")
	private Long amount;
	@Field(type = FieldType.Long,name = "saleAmount")
	private Long saleAmount;
	@Field(type = FieldType.Integer,name = "point")
	private Integer point;
	@Field(type = FieldType.Integer,name = "evaluate")
	private Integer evaluate;
	@Field(type = FieldType.Text,name = "description")
	private String description;
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
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNumOfPage() {
		return numOfPage;
	}
	public void setNumOfPage(String numOfPage) {
		this.numOfPage = numOfPage;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Long saleAmount) {
		this.saleAmount = saleAmount;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
