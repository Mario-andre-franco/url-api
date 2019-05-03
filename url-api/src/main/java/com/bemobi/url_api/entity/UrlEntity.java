package com.bemobi.url_api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * A entity, é utilizada para parte de banco. Onde são salvos os valores
 * que sao passados na tela
 */

@Entity
@Table(name="db_url")
public class UrlEntity {
	
	public UrlEntity(String url, String alias, String randomUrl) {
		super();
		this.url = url;
		this.alias = alias;
		this.randomUrl = randomUrl;
		this.countAlias = 0;
	}


	public UrlEntity() {
		super();
	}


	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String url;
	
	@Column(nullable=true)
	private String alias;
	
	@Column(nullable=true,name="RANDOMURL")
	private String randomUrl;
	
	
	private int countAlias = 0;


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


	public int getCountAlias() {
		return countAlias;
	}


	public void setCountAlias(int countAlias) {
		this.countAlias = countAlias;
	}


	public String getRandomUrl() {
		return randomUrl;
	}


	public void setRandomUrl(String randomUrl) {
		this.randomUrl = randomUrl;
	}
	
}
