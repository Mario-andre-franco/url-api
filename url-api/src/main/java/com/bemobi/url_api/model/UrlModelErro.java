package com.bemobi.url_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlModelErro {
	
	
	
	private String codigoErro;
	private String descErro;
	private String alias;
	
	
	public String getCodigoErro() {
		return codigoErro;
	}
	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}
	public String getDescErro() {
		return descErro;
	}
	public void setDescErro(String descErro) {
		this.descErro = descErro;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
}
