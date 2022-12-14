package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Book implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String ISBN;
	@Lob
	private String image;
	private Integer height;
	private Integer width;
	private Integer length;
	private Integer weight;
	private Integer year;
	@Column(name = "num_of_page")
	private Integer numOfPage;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	private BigDecimal price;
	private Integer discount;
	private Long amount;
	@Column(name = "sale_amount")
	private Long saleAmount;
	@Lob
	private String description;
	private boolean batch;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	@Column(name = "created_time")
	@Temporal(TemporalType.DATE)
	private Date createdTime;
	@Column(name = "updated_time")
	@Temporal(TemporalType.DATE)
	private Date updatedTime;
	@Column(name = "sale_time")
	@Temporal(TemporalType.DATE)
	private Date saleTime;
	private String type;
	private String charactor;
	private String content;
	@ManyToOne
	@JoinColumn(name = "status_id")
	private BookStatus status;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<UserClick> userClicks;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<UserBuy> userBuys;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<Cart> carts;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<BillDetail> billDetails;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<Comment> comments;
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<ReturnBillDetail> returnBillDetails;
	
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getNumOfPage() {
		return numOfPage;
	}

	public void setNumOfPage(Integer numOfPage) {
		this.numOfPage = numOfPage;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
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

	public Long getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(Long saleAmount) {
		this.saleAmount = saleAmount;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public List<UserClick> getUserClicks() {
		return userClicks;
	}

	public void setUserClicks(List<UserClick> userClicks) {
		this.userClicks = userClicks;
	}

	public List<UserBuy> getUserBuys() {
		return userBuys;
	}

	public void setUserBuys(List<UserBuy> userBuys) {
		this.userBuys = userBuys;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public List<BillDetail> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<ReturnBillDetail> getReturnBillDetails() {
		return returnBillDetails;
	}

	public void setReturnBillDetails(List<ReturnBillDetail> returnBillDetails) {
		this.returnBillDetails = returnBillDetails;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public boolean isBatch() {
		return batch;
	}

	public void setBatch(boolean batch) {
		this.batch = batch;
	}

	

	
	
}
