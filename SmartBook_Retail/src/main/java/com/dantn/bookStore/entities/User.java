package com.dantn.bookStore.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private String province;
	private String district;
	private String comune;
	private String address;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	
}
