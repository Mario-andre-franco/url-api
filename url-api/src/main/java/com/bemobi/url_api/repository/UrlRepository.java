package com.bemobi.url_api.repository;

/** 
 * Repository onde esta a parte da persistencia do JPA
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bemobi.url_api.entity.UrlEntity;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
	
	public UrlEntity findById(Long id);
	public UrlEntity findByAlias(String alias);
	public UrlEntity findByUrl (String url);

}
