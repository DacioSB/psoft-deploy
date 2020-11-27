package com.ufcg.psoft.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.repository.LoteRepository;

import exceptions.ObjetoInvalidoException;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private LoteRepository loteRepository;

	@Override
	public Lote saveLote(Lote lote) {
		return loteRepository.save(lote);
	}

	@Override
	public Lote findById(long id) {
		Optional<Lote> loteOptional = loteRepository.findById(id);
		if (loteOptional.isPresent()) {
			return loteOptional.get();
		}
		return null;
	}

	@Override
	public void updateLote(Lote lote) {
		Lote currentLote = findById(lote.getId());

		currentLote.setNumeroDeItens(lote.getNumeroDeItens());
		currentLote.setDataDeValidade(lote.getDataDeValidade());

		loteRepository.save(currentLote);
	}

	@Override
	public void deleteLoteById(long id) {
		loteRepository.delete(findById(id));
	}

	@Override
	public List<Lote> findAllLotes() {
		return loteRepository.findAll();
	}

	@Override
	public int size() {
		return this.findAllLotes().size();
	}

	@Override
	public List<Lote> findLotesByProduto(Produto produto) {
		return loteRepository.findByProduto(produto);
	}
	
	@Override
	public void checkQtdItens(Produto produto) throws ObjetoInvalidoException {
		List<Lote> lotes = loteRepository.findByProduto(produto);
		
		for (Lote lote : lotes) {
			this.checkQtdItens(lote);
		}
	}

	@Override
	public void checkQtdItens(Lote lote) throws ObjetoInvalidoException {
		if (lote.getNumeroDeItens() == 0) {
			produtoService.mudaDisponibilidade(lote.getProduto(), 2);
		} else {
			produtoService.mudaDisponibilidade(lote.getProduto(), 1);
		}
	}

	@Override
	public List<Lote> findItensValidade() {
		List<Lote> lotes = findAllLotes();
		List<Lote> lotesPValidade = new ArrayList<>();

		for (Lote l : lotes) {
			if (l.pertoValidade()) {
				lotesPValidade.add(l);
			}
		}

		return lotesPValidade;
	}
	
	@Override
	public boolean indisponibilizarPorValidade(Produto p) throws ObjetoInvalidoException {
		List<Lote> lotes = this.findAllLotes();
		List<Lote> lotesProduto = new LinkedList<Lote>();
		for(Lote l: lotes) {
			if(l.getProduto().equals(p)) {
				lotesProduto.add(l);
			}
		}
		
		Date dataAtual = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		
		formatter.format(dataAtual);
		
		int counter = 0;
		for(Lote lp: lotesProduto) {
			Date dataValidade = lp.getDataDeValidade();
			formatter.format(dataValidade);
			
			if(dataAtual.after(dataValidade)) {
				counter++;
			}
			
		}
		
		if(lotesProduto.isEmpty()) {
			return false;
		}
		
		else if(counter == lotesProduto.size()) {
			produtoService.mudaDisponibilidade(p, 2);
			return true;
		}
		
		else {
			return false;
		}
		
	}
}
