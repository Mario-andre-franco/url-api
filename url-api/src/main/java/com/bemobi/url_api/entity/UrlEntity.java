package com.bemobi.url_api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="db_url")
public class UrlEntity {
	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String url;
	
	@Column(nullable=true)
	private String alias;
	
	@Column(nullable=true)
	private String randomUrl;
	
	
	private int count_alias = 0;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public int getCount_alias() {
		return count_alias;
	}


	public void setCount_alias(int count_alias) {
		this.count_alias = count_alias;
	}


	public String getRandomUrl() {
		return randomUrl;
	}


	public void setRandomUrl(String randomUrl) {
		this.randomUrl = randomUrl;
	}
	
}
