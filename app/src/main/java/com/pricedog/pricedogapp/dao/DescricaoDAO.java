package com.pricedog.pricedogapp.dao;

import com.pricedog.pricedogapp.modelo.Descricao;

import java.util.List;


public interface DescricaoDAO  {
	String TABELA = "descricoes";
	
	long inserir(Descricao descricao);
	
	void alterar(Descricao descricao);

	void remover (Descricao descricao);
	
	Descricao getDescricao (long id);
	
	long getDescricaoId(Descricao descricao);
	
	List<Descricao> getLista();

	void close();

}