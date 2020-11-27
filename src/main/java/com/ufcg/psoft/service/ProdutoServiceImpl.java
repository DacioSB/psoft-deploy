package com.ufcg.psoft.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ufcg.psoft.model.BomDesconto;
import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Desconto;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.OtimoDesconto;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.SemDesconto;
import com.ufcg.psoft.model.SuperDesconto;
import com.ufcg.psoft.model.enums.TipoDesconto;
import com.ufcg.psoft.repository.CategoriaRepository;
import com.ufcg.psoft.repository.ProdutoRepository;

import exceptions.ObjetoInvalidoException;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private LoteService loteService;

	@Override
	public List<Produto> findAllProdutos() {
		return produtoRepository.findAll();
	}
	public Page<Produto> findAllProdutosOrdered(Pageable page) {
		return produtoRepository.findAll(page);
	}
	@Override
	public void saveProduto(Produto produto) {
		produtoRepository.save(produto);
	}
	@Override
	public Produto findById(long id) {

		Optional<Produto> produto = this.produtoRepository.findById(id);

		if (!produto.isPresent()) {
			return null;
		}

		return produto.get();
	}

	@Override
	public void updateProduto(Produto produto) {
		Produto currentProduto = findById(produto.getId());
		
		currentProduto.mudaNome(produto.getNome());
		currentProduto.setPreco(produto.getPreco());
		currentProduto.setCodigoBarra(produto.getCodigoBarra());
		currentProduto.mudaFabricante(produto.getFabricante());
		currentProduto.mudaCategoria(produto.getCategoria());
		
		produtoRepository.save(currentProduto);
	}
	@Override
	public void deleteProdutoById(long id) {
		Optional<Produto> produto = this.produtoRepository.findById(id);
		
		if (produto.isPresent()) {
			produtoRepository.delete(produto.get());
		}
		
	}


	@Override
	public boolean doesProdutoExist(long idProduto) {

		return this.produtoRepository.findById(idProduto).isPresent();

	}
	@Override
	public void mudaDisponibilidade(Produto produto, int situacao) throws ObjetoInvalidoException {
		Produto currentProduto = findById(produto.getId());
		
		if(currentProduto != null){
			currentProduto.mudaSituacao(situacao);
			produtoRepository.save(currentProduto);
		}
		
	}

	// produtos em falta
	@Override
	public List<Produto> findProdutosIndisponiveis() {
		return produtoRepository.findBySituacao(Produto.INDISPONIVEL);
	}

	@Override
	public Categoria insertTipoDesconto(Long idCategoria, TipoDesconto tipoDesconto) {
		Optional<Categoria> cOptional = this.categoriaRepository.findById(idCategoria);

		if(cOptional.isPresent()){
			Categoria categoria = cOptional.get();
			Desconto desconto = escolheTipoDesconto(tipoDesconto);
			categoria.setDesconto(desconto);
			return categoria;
		}
		return null;
	}

	private Desconto escolheTipoDesconto(TipoDesconto desconto) {
		switch (desconto.getCod()) {
			case 1:
				return new SemDesconto();
			case 2:
				return new BomDesconto();
			case 3:
				return new OtimoDesconto();
			case 4:
				return new SuperDesconto();
			default:

				throw new IllegalArgumentException("estado invalido");
		}
	}
	
	@Override
	public List<Produto> findItensEmBaixa() {
		List<Produto> produtos = findAllProdutos();
		List<Lote> lotes = loteService.findAllLotes();
		List<Produto> lotesEmBaixa = new ArrayList<>();
		
		for (Produto produto : produtos) {
			int qtdItens = 0;
			
			for (Lote lote : lotes) {
				if (lote.getProduto().equals(produto)) {
					qtdItens += lote.getNumeroDeItens();
				}
			}
			if (qtdItens <= 15) {
				lotesEmBaixa.add(produto);
			}
		}

		return lotesEmBaixa;
	}
}
