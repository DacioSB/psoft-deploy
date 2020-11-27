package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.enums.TipoDesconto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import exceptions.ObjetoInvalidoException;

public interface ProdutoService {

	List<Produto> findAllProdutos();

	public Page<Produto> findAllProdutosOrdered(Pageable page);

	void saveProduto(Produto produto);

	Produto findById(long id);

	void updateProduto(Produto user);

	void deleteProdutoById(long id);

	boolean doesProdutoExist(long idProduto);

	Categoria insertTipoDesconto(Long idProduto, TipoDesconto desconto);

	void mudaDisponibilidade(Produto produto, int situacao) throws ObjetoInvalidoException;
	
	List<Produto> findProdutosIndisponiveis();
	
	List<Produto> findItensEmBaixa();
}
