package com.ufcg.psoft.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;

import exceptions.ObjetoInvalidoException;

public interface VendaService {

	List<Venda> findAllVendas();
	
	public Page<Venda> findAllVendasOrdered(Pageable page);

	Optional<Venda> findById(long id);
	
	Venda addVenda();

	void addProduto(long id, long idP, int quantidade);

	boolean quantidadeProdutoMaior(int quanidadeItens, Lote lote);

	void removeProduto(Lote lote, int quantidade);

	Lote loteDoProduto(Produto produto, int quantidade);
	
	List<Lote> loteDoProduto(Produto produto);

	void deleteVenda(Venda venda) throws ObjetoInvalidoException;

}
