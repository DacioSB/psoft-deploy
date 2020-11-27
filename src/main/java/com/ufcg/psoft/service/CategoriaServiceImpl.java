package com.ufcg.psoft.service;

import java.util.Optional;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRep;

    @Override
    public Categoria save(Categoria categoria) {
        categoria = this.categoriaRep.save(categoria);
        return categoria;

    }

    @Override
    public Categoria findByNome(String nome) {
        Optional<Categoria> categoriaOpt = this.categoriaRep.findByNome(nome);
        return categoriaOpt.isPresent() ? categoriaOpt.get() : null; 
    }
    
}
