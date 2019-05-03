package com.bemobi.url_api.service;

/** 
 * Servico para gerar os valores random para colocar apos a url 
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
