package com.bemobi.url_api.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		UrlEntity urlEntity = null;
		UrlModel urlModel = new UrlModel();
		String http = "http://";
		String random = geraRandom.geraRandom(url);
		//coloca valor random no alias se nao for passado na url
		if(alias != null && alias.isEmpty()) {
			alias = random;
		}
		
		/* faz a checagem se o alias nao esta vazio e se existe no banco */
		if (!existeAliasBanco(alias)) {
			urlEntity = new UrlEntity(url,alias,random); 		
			urlRepo.save(urlEntity);
		} else {					
				urlModel.setAlias(alias);
				urlModel.setTipoDeErro("001");
				urlModel.setDescErro("Ja existe alias no banco");
				
				return ResponseEntity.ok(urlModel);						
		}
		urlModel.setAlias(alias);
		urlModel.setUrlOriginal(url);
		urlModel.setTempoResposta(String.valueOf(System.currentTimeMillis() - tempo + "ms"));
		urlModel.setUrlEncurtada(http + "shortener/u/" + random);
		urlModel.setRandomGerado(random);
		
		return ResponseEntity.ok(urlModel);
		
		
	
	}
	
	/*Utilizado para testar se banco estava salvando as urls */
	@RequestMapping(path="/criadas", method = RequestMethod.GET)
	public ResponseEntity<?> listaTodas() {
		
		
		return ResponseEntity.ok(urlRepo.findAll());
	}
	
	
	@RequestMapping(path="/u/{alias}", method = RequestMethod.GET)
	public UrlModel redirecionaUrl(@PathVariable(value="alias") String alias, HttpServletResponse servletResponse) throws IOException, URISyntaxException {
		UrlModel urlModel = new UrlModel();

				if(!existeAliasBanco(alias)) {
					
					urlModel.setTipoDeErro("002");
					urlModel.setDescErro("nao existe alias no banco");
					return urlModel;
		} else {
					UrlEntity urlEntity =  urlRepo.findByAlias(alias);
					String urlRedirect = urlEntity.getUrl();
					//servletResponse.sendRedirect(urlRedirect);
					
					urlModel.setUrlOriginal(urlModel.getUrlOriginal());
					servletResponse.sendRedirect("http://"+urlRedirect);
					
					return null;
					
				}
			}
		
	

	public boolean existeAliasBanco(String alias) {
		Optional<UrlEntity> optUrl = Optional.ofNullable(urlRepo.findByAlias(alias));
		return optUrl.isPresent();
	}
}
