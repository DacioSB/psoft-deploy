package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

import exceptions.ObjetoInvalidoException;

public interface LoteService {

	List<Lote> findAllLotes();

	Lote findById(long id);

	void updateLote(Lote lote);

	void deleteLoteById(long id);

	int size();

	Lote saveLote(Lote lote);
	
	List<Lote> findLotesByProduto(Produto produto);

	void checkQtdItens(Produto produto) throws ObjetoInvalidoException;

	void checkQtdItens(Lote lote) throws ObjetoInvalidoException;

	List<Lote> findItensValidade();
	
	boolean indisponibilizarPorValidade(Produto p) throws ObjetoInvalidoException;

}
