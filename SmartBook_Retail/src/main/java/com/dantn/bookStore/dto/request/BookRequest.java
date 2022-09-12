package com.dantn.bookStore.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.dantn.bookStore.adapter.DtoToEntity;
import com.dantn.bookStore.entities.Author;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.BookStatus;
import com.dantn.bookStore.entities.Publisher;

public class BookRequest implements DtoToEntity<Book>{
	private Integer id;
	@NotBlank(message = "Không bỏ trống tên sách")
	private String name;
	@NotBlank(message = "Không bỏ trống ISBN")
	private String ISBN;
	@NotBlank(message = "Không bỏ trống số trang")
	@Positive(message = "Số trang là số nguyên lớn hơn 0")
	@Size(max = 4,message = "Số trang quá lớn")
	private String numOfPage;
	private Integer author;
	private Integer publisher;
	@NotBlank(message = "Không bỏ trống số tiền")
	@Positive(message = "Số tiền là số nguyên lớn hơn 0")
	@Size(max = 10,message = "Số tiền quá lớn")
	private BigDecimal price;
	@PositiveOrZero(message = "Giảm giá là số nguyên lớn hơn hoặc bằng 0")
	@Max(value = 100,message = "Tối đa giảm 100%")
	private Integer discount;
	@Positive(message = "Số lượng là số nguyên lớn hơn 0")
	@Size(max = 5,message = "Số lượng quá lớn")
	private Long amount;
	private String description;
	private String type;
	private String charactor;
	private String content;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getNumOfPage() {
		return numOfPage;
	}

	public void setNumOfPage(String numOfPage) {
		this.numOfPage = numOfPage;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	public Integer getPublisher() {
		return publisher;
	}

	public void setPublisher(Integer publisher) {
		this.publisher = publisher;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
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

	@Override
	public Book changeToEntity(Book b) {
		b.setAmount(amount);
		b.setAuthor(new Author());
		b.setCharactor(charactor);
		b.setContent(content);
		b.setCreatedTime(new Date());
		b.setDiscount(discount);
		b.setDescription(description);
		b.setISBN(ISBN);
		b.setType(type);
		b.setPublisher(new Publisher());
		b.setName(name);
		b.setNumOfPage(numOfPage);
		b.setPrice(price);
		if(id==null) {
			b.setBrokenAmount(0);
			b.setEvaluate(0);
			b.setPoint(0);
			b.setSaleAmount((long)0);
			b.setStatus(new BookStatus());
		}else {
			b.setUpdatedTime(new Date());
		}
		return b;
	}
	
}
