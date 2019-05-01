package com.bemobi.url_api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bemobi.url_api.entity.UrlEntity;
import com.bemobi.url_api.model.UrlModel;
import com.bemobi.url_api.repository.UrlRepository;
import com.bemobi.url_api.service.GeraRandomService;

@RestController
public class Controller {
	
	
	@Autowired
	UrlRepository urlRepo;
	
	@Autowired
	GeraRandomService geraRandom;
	
	
	@RequestMapping(path="", method = RequestMethod.GET)
	@ResponseBody
	String acessoErrado() {
		return "Erro ao acessar";
	}
	
	@RequestMapping(path="/salvar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UrlEntity> salvarValores(@RequestParam String url,
												   @RequestParam(required=false) String alias,HttpServletResponse response ) {
		Long tempo = System.currentTimeMillis();
		UrlEntity urlEntity = new UrlEntity();
		UrlModel urlModel = new UrlModel();
		String http = "http://";
		
		
		if (StringUtils.isEmpty(alias)) {
			urlEntity.setUrl(url);
			urlEntity.setAlias(null);
			urlRepo.save(urlEntity);
		} else {
			
			if(existeAliasBanco(alias)) {
				urlModel.setTipoDeErro("001");
				urlModel.setDescErro("Ja existe alias no banco");
				return ResponseEntity.status(200).body(urlEntity);
			}	
			
		}
		urlEntity.setUrl(url);
		urlEntity.setAlias(alias);
		urlRepo.save(urlEntity);
		
		urlModel.setAlias(alias);
		urlModel.setUrlOriginal(url);
		urlModel.setTempoResposta(String.valueOf(System.currentTimeMillis() - tempo + "ms"));
		urlModel.setUrlEncurtada(http + "shortener/u/" + geraRandom.geraRandom(url));
		
		return ResponseEntity.ok(urlEntity);
		
		
	
	}
	
	
	public Boolean existeAliasBanco(String alias) {
		Optional<UrlEntity> optUrl = Optional.ofNullable(urlRepo.findByAlias(alias));
		return optUrl.isPresent();
	}
}
