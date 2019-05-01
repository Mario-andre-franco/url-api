package com.bemobi.url_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bemobi.url_api.entity.UrlEntity;
import com.bemobi.url_api.model.UrlModel;
import com.bemobi.url_api.repository.UrlRepository;

@Service
public class GeraRandomService {
	
	
	@Autowired
	UrlRepository urlRepo;
	
	
	public String geraRandom(String url) {
		String urlOriginal = "";
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVXWYZabcdefghijklmnopqrstuvxwyz0123456789";
		
		for(int i=0; i < 6; i++)
			urlOriginal += caracteres.charAt((int)Math.floor(Math.random() * caracteres.length()));
		return urlOriginal;
	}
	
	
	public UrlModel redirectUrl (String alias) {
		Optional<UrlEntity> urlOpt = Optional.ofNullable(urlRepo.findByAlias(alias));
		UrlModel urlModel = new UrlModel();
		
		String urlOri = null;
		
		if(urlOpt.isPresent()) {
			UrlEntity urlEntity= urlOpt.get();
			urlOri = urlEntity.getUrl();
			
			urlRepo.save(urlEntity);
		}
		
		else {
			urlModel.setTipoDeErro("002");
			urlModel.setDescErro("Url nao cadastrada");
			
		}
		
		urlModel.setUrlOriginal(urlOri);
		urlModel.setAlias(alias);
		
		return urlModel;
	}

}
