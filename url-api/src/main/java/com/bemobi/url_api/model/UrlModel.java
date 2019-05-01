package com.bemobi.url_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlModel {
	
	private String urlOriginal;
	private String alias;
	private String tempoResposta;
	private String tipoDeErro;
	private String descErro;
	private String urlEncurtada;
	
	
	public String getUrlOriginal() {
		return urlOriginal;
	}
	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTempoResposta() {
		return tempoResposta;
	}
	public void setTempoResposta(String tempoResposta) {
		this.tempoResposta = tempoResposta;
	}
	public String getTipoDeErro() {
		return tipoDeErro;
	}
	public void setTipoDeErro(String tipoDeErro) {
		this.tipoDeErro = tipoDeErro;
	}
	public String getDescErro() {
		return descErro;
	}
	public void setDescErro(String descErro) {
		this.descErro = descErro;
	}
	public String getUrlEncurtada() {
		return urlEncurtada;
	}
	public void setUrlEncurtada(String urlEncurtada) {
		this.urlEncurtada = urlEncurtada;
	}
	
	
}
