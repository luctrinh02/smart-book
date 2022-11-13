package com.dantn.bookStore.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String display;
	private String icon;
	private String url;
	private String classB;
	private Integer indexModule;
	private Boolean isAll;
	@JsonIgnore
	@OneToMany(mappedBy = "module")
	private List<SubModule> subs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public List<SubModule> getSubs() {
		return subs;
	}

	public void setSubs(List<SubModule> subs) {
		this.subs = subs;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassB() {
		return classB;
	}

	public void setClassB(String classB) {
		this.classB = classB;
	}

	public Integer getIndexModule() {
		return indexModule;
	}

	public void setIndexModule(Integer indexModule) {
		this.indexModule = indexModule;
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	
	
	
}
