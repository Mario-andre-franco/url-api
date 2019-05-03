package com.bemobi.url_api.types;

/** 
 * Enum utilizado para seter dos erros
 */
public enum Errors {
	
	ERRO01("001","CUSTOM ALIAS ALREADY EXISTS"),
	ERRO02("002","CUSTOM ALIAS NOT FOUND");
	
	private final String descricao;
	private final String codigo;
	
	private Errors(String descricao, String codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
}
