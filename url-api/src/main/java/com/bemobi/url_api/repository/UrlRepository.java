package com.bemobi.url_api.repository;

import org.springframework.stereotype.Repository;

import com.bemobi.url_api.entity.UrlEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UrlRepository extends CrudRepository<UrlEntity, Long> {
	
	public UrlEntity findById(Long id);
	public UrlEntity findByAlias(String alias);
	public UrlEntity findByUrl (String url);
	

}
