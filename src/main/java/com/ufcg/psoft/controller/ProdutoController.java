package com.ufcg.psoft.controller;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.ProdutoDispEPreco;
import com.ufcg.psoft.model.DTO.ProdutoDisponibilidade;
import com.ufcg.psoft.model.enums.TipoDesconto;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.VendaService;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@CrossOrigin
@RequestMapping("/api")
@RestController
public class ProdutoController {
    
    /**
	 *
	 */
	private static final String NOT_FOUND = " not found";

	/**
	 *
	 */
	private static final String PRODUTO_WITH_ID = "Produto with id ";

	@Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private VendaService vendaService;
    
    @Autowired
    private LoteService loteService;
    
 // -------------------Retrieve All
	 // Products--------------------------------------------
 	@PreAuthorize("hasAnyRole('ADMIN')")
 	@GetMapping("/produto/")
 	public ResponseEntity<?> listAllProducts() {
 		List<Produto> produtos = produtoService.findAllProdutos();

 		if (produtos.isEmpty()) {
 			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 		}
 		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
 	}
 	
 	@PreAuthorize("hasAnyRole('ADMIN')")
 	@PostMapping("/produto/")
 	public ResponseEntity<?> criarProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {

 		boolean produtoExiste = false;

 		for (Produto p : produtoService.findAllProdutos()) {
 			if (p.getCodigoBarra().equals(produto.getCodigoBarra())) {
 				produtoExiste = true;
 			}
 		}

 		if (produtoExiste) {
 			return new ResponseEntity<>(new CustomErrorType("O produto " + produto.getNome() + " do fabricante "
 					+ produto.getFabricante() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
 		}

 		try {
 			produto.mudaSituacao(Produto.INDISPONIVEL);
 		} catch (ObjetoInvalidoException e) {
 			return new ResponseEntity<>(new CustomErrorType("Error: Produto" + produto.getNome() + " do fabricante "
 					+ produto.getFabricante() + " alguma coisa errada aconteceu!"), HttpStatus.NOT_ACCEPTABLE);
 		}

 		produtoService.saveProduto(produto);


 		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
 	}
	 
 	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/produto/{id}")
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {
		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(new CustomErrorType(PRODUTO_WITH_ID + id + NOT_FOUND),
					HttpStatus.NOT_FOUND);	
		}
		
		Produto produto = produtoService.findById(id);
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	 
 	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/produto/{id}")
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

		Produto currentProduto = null;

		for (Produto p : produtoService.findAllProdutos()) {
			if (p.getId() == id) {
				currentProduto = p;
			}
		}

		if (currentProduto == null) {
			return new ResponseEntity<>(new CustomErrorType("Unable to update. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
			
		}

		produtoService.updateProduto(produto);
		return new ResponseEntity<Produto>(HttpStatus.OK);
	}
	 
 	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/produto/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable("id") long id) {

		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);	
		}
		
		Produto produto = produtoService.findById(id);
		
		List<Lote> lotes = vendaService.loteDoProduto(produto);
		
		if(!lotes.isEmpty()) {
			for (Lote lote : lotes) {
				loteService.deleteLoteById(lote.getId());				
			}
		}

		produtoService.deleteProdutoById(id);
		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
 	
	@GetMapping(value = "/produto/info/{id}")
	public ResponseEntity<?> consultarDisponibilidade(@PathVariable("id") long id) throws ObjetoInvalidoException {
		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(new CustomErrorType(PRODUTO_WITH_ID + id + NOT_FOUND),
					HttpStatus.NOT_FOUND);	
		}
		
		Produto p = produtoService.findById(id);
		
		if (p.getSituacao() == Produto.INDISPONIVEL) {
			ProdutoDisponibilidade produto = new ProdutoDisponibilidade(p.getSituacao());
			return new ResponseEntity<ProdutoDisponibilidade>(produto, HttpStatus.OK);
		} else {
			ProdutoDispEPreco produto = new ProdutoDispEPreco(p.getSituacao(), p.getPreco());
			return new ResponseEntity<ProdutoDispEPreco>(produto, HttpStatus.OK);			
		}
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/produto/info/{id}")
	public ResponseEntity<?> indisponibilidadeProdutoValidade(@PathVariable("id") long idProduto) throws ObjetoInvalidoException, ParseException{
		if (!produtoService.doesProdutoExist(idProduto)) {
			return new ResponseEntity<>(new CustomErrorType(PRODUTO_WITH_ID + idProduto + NOT_FOUND),
					HttpStatus.NOT_FOUND);
		}
		
		Produto p = produtoService.findById(idProduto);
		
		if(p.getSituacao() == 2) {
			return new ResponseEntity<>(new CustomErrorType(PRODUTO_WITH_ID + idProduto+ " already unavailable"), HttpStatus.CONFLICT);
		}
		
		List<Lote> lotes = loteService.findAllLotes();
		if (lotes.isEmpty()) {
			return new ResponseEntity<String>("Nao há lotes cadastrados",HttpStatus.NO_CONTENT);
		}
		
		boolean operacao = loteService.indisponibilizarPorValidade(p);
		
		if(operacao) {
			return new ResponseEntity<String>("Operacao realizada: Produto " + idProduto + " esta indisponivel", HttpStatus.OK);
		}
		
		else {
			return new ResponseEntity<String>("Operacao nao realizada: O produto " + idProduto + " nao possui todos os lotes vencidos ou nao possui lote.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PutMapping("/produto/desconto/{idCategoria}")
    public ResponseEntity<?> atribuiDescontoCategoria(@PathVariable Long idCategoria, 
        @RequestParam(required = false, defaultValue = "SEM_DESCONTO") TipoDesconto desconto){
        

        Categoria categoria = produtoService.insertTipoDesconto(idCategoria, desconto);
        
        if(categoria == null){
            return new ResponseEntity<>(new CustomErrorType(PRODUTO_WITH_ID + idCategoria + NOT_FOUND),
					HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok().build();

        
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/produto/ordenado")
    public ResponseEntity<?> getAllProducts(@PageableDefault(sort = {"nome"}, 
        direction = Sort.Direction.DESC, page = 0, size = 10) Pageable page){
       
        return ResponseEntity.ok(produtoService.findAllProdutosOrdered(page));

        
	}
    
    @PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/produto/lowInventory/")
	public ResponseEntity<?> listItensEmBaixa() {
		List<Produto> produtos = produtoService.findItensEmBaixa();
		
		if (produtos.isEmpty()) {
			return new ResponseEntity<Lote>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
    
}
