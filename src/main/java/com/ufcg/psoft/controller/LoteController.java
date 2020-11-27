package com.ufcg.psoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.LoteDTO;
import com.ufcg.psoft.model.DTO.LoteShowDTO;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class LoteController {
	
	@Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private LoteService loteService;
    
    
    
    @PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/lote/lowInventory/")
	public ResponseEntity<?> listItensEmBaixa() {
		List<Produto> produtos = produtoService.findItensEmBaixa();
		
		if (produtos.isEmpty()) {
			return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/lote/perto-da-validade/")
	public ResponseEntity<?> listItensPertoDaValidade(){
		List<Lote> lotes = loteService.findItensValidade();
		
		if(lotes.isEmpty()) {
			return new ResponseEntity<Lote>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/produto/{id}/lote")
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) {
		Produto product = produtoService.findById(produtoId);

		if (product == null) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create lote. Produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Lote lote = loteService.saveLote(new Lote(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade()));

		try {
			loteService.checkQtdItens(lote);
			
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

	@GetMapping("/lote/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> listAllLotess() {
		List<LoteShowDTO> lotes = LoteShowDTO.converter(loteService.findAllLotes());

		if (lotes.isEmpty()) {
			return new ResponseEntity<Lote>(HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<List<LoteShowDTO>>(lotes, HttpStatus.OK);
	}

}
