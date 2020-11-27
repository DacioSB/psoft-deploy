package com.ufcg.psoft.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.ufcg.psoft.model.Lote;

import com.ufcg.psoft.model.Venda;

import com.ufcg.psoft.model.DTO.LoteShowDTO;

import com.ufcg.psoft.model.DTO.RelatorioDTO;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.VendaService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	LoteService loteService;
	
	@Autowired
	VendaService vendaService;

	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/relatorio")
	public ResponseEntity<?> consultaRelatorio(){
		List<Lote> lotes = this.loteService.findAllLotes();
		List<Venda> vendas = this.vendaService.findAllVendas();
		int qtdLotes = lotes.size();
		RelatorioDTO relatorioDTO = new RelatorioDTO(LoteShowDTO.converter(lotes), vendas, qtdLotes);

		return ResponseEntity.ok(relatorioDTO);

	}
	
	
}
