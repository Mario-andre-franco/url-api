package com.bemobi.url_api.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	@RequestMapping(path="/salvar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UrlModel> salvarValores(@RequestParam String url,
												   @RequestParam(required=false) String alias,HttpServletResponse response ) {
		Long tempo = System.currentTimeMillis();
		UrlEntity urlEntity = new UrlEntity();
		UrlModel urlModel = new UrlModel();
		String http = "http://";
		String random = geraRandom.geraRandom(url);
		
		if (StringUtils.isEmpty(alias)) {
			urlEntity.setUrl(url);
			urlEntity.setAlias(null);
			urlRepo.save(urlEntity);
		} 
		
		else {
			
			if(existeAliasBanco(alias)) {
				/*apenas retornar erro e qual alias  */
				urlModel.setAlias(alias);
				urlModel.setTipoDeErro("001");
				urlModel.setDescErro("Ja existe alias no banco");
				
				return ResponseEntity.ok(urlModel);
			}	
			
		}
		urlModel.setUrlOriginal(url);
		urlModel.setTempoResposta(String.valueOf(System.currentTimeMillis() - tempo + "ms"));
		urlModel.setUrlEncurtada(http + "shortener/u/" + random);
		urlModel.setRandomGerado(random);
		
		return ResponseEntity.ok(urlModel);
		
		
	
	}
	
	@RequestMapping(path="/u/{alias}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity <UrlModel> redirecionaUrl(@PathVariable String alias, HttpServletResponse servletResponse) throws IOException {
		UrlModel urlModel = new UrlModel();
		
				if(!existeAliasBanco(alias)) {
					urlModel.setTipoDeErro("002");
					urlModel.setDescErro("nao existe alias no banco");
					return ResponseEntity.ok(urlModel);
		} 
				
				else {
					geraRandom.redirectUrl(urlModel.getUrlOriginal());
					servletResponse.sendRedirect(urlModel.getUrlOriginal());
					urlModel.setUrlOriginal(urlModel.getUrlOriginal());
					
				}
				UrlModel erroUrl = new UrlModel();
				erroUrl.setTipoDeErro("500");
				erroUrl.setDescErro("Pagina nao encontrada");
				return ResponseEntity.status(200).body(erroUrl);
			}
		
	
	
	public Boolean existeAliasBanco(String alias) {
		Optional<UrlEntity> optUrl = Optional.ofNullable(urlRepo.findByAlias(alias));
		return optUrl.isPresent();
	}
}
