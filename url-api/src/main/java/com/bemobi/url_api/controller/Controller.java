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
import org.springframework.web.bind.annotation.RestController;

import com.bemobi.url_api.entity.UrlEntity;
import com.bemobi.url_api.model.UrlModel;
import com.bemobi.url_api.repository.UrlRepository;
import com.bemobi.url_api.service.GeraRandomService;
import com.bemobi.url_api.types.Errors;


/** 
 * A class Controller faz todo o mapeamento das url's que são passadas.
 * Após fazer as verificações, é retornado o JSON na tela, dependendo das
 * regras de negócio.
 */

@RestController
public class Controller {
	
	
	@Autowired
	UrlRepository urlRepo;
	
	@Autowired
	GeraRandomService geraRandom;
	
	
	@RequestMapping(path="/salvar", method = RequestMethod.POST)
	public ResponseEntity<UrlModel> salvarValores(@RequestParam String url,
												   @RequestParam(required=false) String alias,HttpServletResponse response ) {
		
		Long tempo = System.currentTimeMillis();
		UrlEntity urlEntity = null;
		UrlModel urlModel = new UrlModel();
		String http = "http://";
		String random = geraRandom.geraRandom(url);
		
		//coloca valor random no alias se nao for passado na url
		if(alias == null) {
			alias = random;
		}
		
		/* faz a checagem se o alias nao esta vazio e se existe no banco */
		if (!existeAliasBanco(alias)) {
			urlEntity = new UrlEntity(url,alias,random); 		
			urlRepo.save(urlEntity);
		} else {					
				urlModel.setAlias(alias);
				urlModel.setTipoDeErro(Errors.ERRO01.getCodigo());
				urlModel.setDescErro(Errors.ERRO01.getDescricao());
				
				return ResponseEntity.ok(urlModel);						
		}
		urlModel.setAlias(alias);
		urlModel.setUrlOriginal(url);
		urlModel.setTempoResposta(String.valueOf(System.currentTimeMillis() - tempo + "ms"));
		/* implementado pois estava setando null caso a url nao fosse passada */
		if (alias == null) {
			urlModel.setUrlEncurtada(http + "shortener/u/" + random);
			urlModel.setRandomGerado(random);
			return ResponseEntity.ok(urlModel);
		} 
			urlModel.setUrlEncurtada(http + "shortener/u/" + alias);
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
					urlModel.setTipoDeErro(Errors.ERRO02.getCodigo());
					urlModel.setDescErro(Errors.ERRO02.getDescricao());
					return urlModel;
		} else {
					UrlEntity urlEntity =  urlRepo.findByAlias(alias);
					String urlRedirect = urlEntity.getUrl();
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
