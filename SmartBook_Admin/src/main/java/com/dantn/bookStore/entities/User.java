package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "email",unique = true,nullable = false)
	private String email;
	private String password;
	private String fullname;
	private String phoneNumber;
	private String address;
	@ManyToOne
	@JoinColumn(name = "ward_id")
	private Ward ward;
	@Lob
	private String img;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private UserRole role;
	@ManyToOne
	@JoinColumn(name = "status_id")
	private UserStatus status;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<UserSearch> searchs;
	@JsonIgnore
	@OneToMany(mappedBy = "createdBy")
	private List<Book> books;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<UserClick> userClicks;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<UserBuy> userBuys;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Cart> carts;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Bill> bills;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Shipment> shipments ;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<ReturnBill> returnBills ;
	
	public Ward getWard() {
		return ward;
	}
	public void setWard(Ward ward) {
		this.ward = ward;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public List<UserSearch> getSearchs() {
		return searchs;
	}
	public void setSearchs(List<UserSearch> searchs) {
		this.searchs = searchs;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
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
	public List<Bill> getBills() {
		return bills;
	}
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Shipment> getShipments() {
		return shipments;
	}
	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public List<ReturnBill> getReturnBills() {
		return returnBills;
	}
	public void setReturnBills(List<ReturnBill> returnBills) {
		this.returnBills = returnBills;
	}


}
