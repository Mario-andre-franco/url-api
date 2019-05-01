package com.bemobi.url_api.service;

import org.springframework.stereotype.Service;

@Service
public class GeraRandomService {
	
	public String geraRandom(String url) {
		String urlOriginal = "";
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVXWYZabcdefghijklmnopqrstuvxwyz0123456789";
		
		for(int i=0; i < 6; i++)
			urlOriginal += caracteres.charAt((int)Math.floor(Math.random() * caracteres.length()));
		return urlOriginal;
	}

}
