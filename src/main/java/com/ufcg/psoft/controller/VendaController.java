package com.ufcg.psoft.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.VendaService;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class VendaController {

	@Autowired
	private VendaService vendaServiceImpl;
	@Autowired
	private ProdutoService produtoServiceImpl;
	@Autowired
	private LoteService loteService;
	
	@PostMapping("/venda")
	public ResponseEntity<Venda> criaVenda() {

		Venda venda = vendaServiceImpl.addVenda();

		return ResponseEntity.ok(venda);
	}

	@Transactional
	@PutMapping("/venda/{idV}/produto/{idP}")
	public ResponseEntity<?> addProdutoVenda(@RequestParam(required = false, defaultValue = "1") Integer quantidade,
			@PathVariable Long idV, @PathVariable Long idP) throws ObjetoInvalidoException {

		Lote lote = null;
		Optional<Venda> vendaOpt = vendaServiceImpl.findById(idV);

		if (!produtoServiceImpl.doesProdutoExist(idP)) {
			return new ResponseEntity<>(new CustomErrorType("Produto com id " + idP + " nao existe"),
					HttpStatus.NOT_FOUND);
		}
		
		Produto produto = produtoServiceImpl.findById(idP);

		if (!vendaOpt.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Venda com id " + idV + " nao existe"),
					HttpStatus.NOT_FOUND);
		}

		lote = vendaServiceImpl.loteDoProduto(produto, quantidade);

		if (lote == null) {
			return new ResponseEntity<>(
					new CustomErrorType("O produto " + produto + " nao esta cadastrado em nenhum lote"),
					HttpStatus.NOT_FOUND);
		}

		if (!vendaServiceImpl.quantidadeProdutoMaior(quantidade, lote)) {
			return new ResponseEntity<>(
					new CustomErrorType(
							"A quantidade de retirada do produto é maior que a quantidade presente no lote "),
					HttpStatus.NOT_FOUND);
		}

		vendaServiceImpl.removeProduto(lote, quantidade);
		
		loteService.checkQtdItens(produto);
		
		this.vendaServiceImpl.addProduto(idV, idP, quantidade);
	
		Venda venda = vendaOpt.get();

		return ResponseEntity.ok(venda);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@Transactional
	@GetMapping("/venda/ordenado")
	public ResponseEntity<?> getAllVendas(
			@PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) {

		return ResponseEntity.ok(vendaServiceImpl.findAllVendasOrdered(page));

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/venda/{id}")
	public ResponseEntity<?> deleteVenda(@PathVariable("id") long id) throws ObjetoInvalidoException {
		Optional<Venda> vendaOptional = vendaServiceImpl.findById(id);

		if (!vendaOptional.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. Venda with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}

		Venda venda = vendaOptional.get();

		vendaServiceImpl.deleteVenda(venda);

		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
}
