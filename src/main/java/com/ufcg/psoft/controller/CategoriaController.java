package com.ufcg.psoft.controller;

import java.net.URI;

import javax.transaction.Transactional;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.DTO.CategoriaFormDTO;
import com.ufcg.psoft.service.CategoriaService;
import com.ufcg.psoft.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/categoria")
    public ResponseEntity<?> adicionaCategoria(@RequestBody @Validated CategoriaFormDTO form, UriComponentsBuilder uriBuilder){
        Categoria cat = this.categoriaService.findByNome(form.getNome());
        if(cat != null){
            return new ResponseEntity<>(new CustomErrorType("A categoria " + cat.getNome() + " ja esta cadastrada!"), HttpStatus.CONFLICT);
        }
        Categoria categoria = this.categoriaService.save(form.convert());
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(categoria);
    }
}
