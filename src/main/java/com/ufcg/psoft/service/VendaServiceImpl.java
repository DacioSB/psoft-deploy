package com.ufcg.psoft.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.VendaProduto;
import com.ufcg.psoft.repository.LoteRepository;
import com.ufcg.psoft.repository.ProdutoRepository;
import com.ufcg.psoft.repository.VendaProdutoRepository;
import com.ufcg.psoft.repository.VendaRepository;

import exceptions.ObjetoInvalidoException;

@Service
public class VendaServiceImpl implements VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private VendaProdutoRepository vendaProdutoRepository;

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private LoteService loteService;

	@Override
	public List<Venda> findAllVendas() {
		return this.vendaRepository.findAll();
	}

	@Override
	public Page<Venda> findAllVendasOrdered(Pageable page) {
		return vendaRepository.findAll(page);
	}

	@Override
	public Optional<Venda> findById(long id) {
		return vendaRepository.findById(id);
	}

	@Override
	@Transactional
	public Venda addVenda() {

		Venda venda = new Venda();

		venda = vendaRepository.save(venda);

		return venda;
	}

	@Override
	@Transactional
	public void addProduto(long idVenda, long idProduto, int quantidade) {

		Venda venda = vendaRepository.getOne(idVenda);

		Optional<Produto> produtoOpt = produtoRepository.findById(idProduto);

		if(produtoOpt.isPresent()){
			VendaProduto vendaProduto = null;

			VendaProduto vendaProdutoTeste = venda.existeVendaProduto(idProduto);

			if (vendaProdutoTeste == null) {

				vendaProduto = new VendaProduto(quantidade, venda, produtoOpt.get(), produtoOpt.get().getPreco());

			} else {

				vendaProdutoTeste.setQtdItems(quantidade);
				vendaProduto = vendaProdutoTeste;
			}

			venda.setItem(vendaProduto);

			vendaProdutoRepository.save(vendaProduto);
		}

		
	}

	@Override
	public boolean quantidadeProdutoMaior(int quanidadeItens, Lote lote) {
		return quanidadeItens <= lote.getNumeroDeItens();
	}

	@Override
	public void removeProduto(Lote lote, int quantidade) {
		lote.subtraiItens(quantidade);
	}

	@Override
	public List<Lote> loteDoProduto(Produto produto) {
		List<Lote> lotes = loteRepository.findByProduto(produto);

		return lotes;
	}

	@Override
	public Lote loteDoProduto(Produto produto, int quantidade) {
		List<Lote> lotes = loteRepository.findByProduto(produto);

		for (Lote lote : lotes) {
			if (lote.getNumeroDeItens() >= quantidade) {
				return lote;
			}
		}
		
		if (!lotes.isEmpty()) { return lotes.get(0); }
		
		return null;
	}
	
	@Override
	public void deleteVenda(Venda venda) throws ObjetoInvalidoException {
		Set<VendaProduto> produtosVenda = venda.getItens();

		for (VendaProduto vProduto : produtosVenda) {
			Produto produto = vProduto.getProduto();
			int qtdItens = vProduto.getQtdItems();

			Lote lote = loteDoProduto(produto).get(0);
			lote.adicionaItens(qtdItens);
			loteService.checkQtdItens(lote);
		}

		for (VendaProduto vProduto : produtosVenda) {
			vendaProdutoRepository.delete(vProduto);
		}

		vendaRepository.delete(venda);
	}

}
