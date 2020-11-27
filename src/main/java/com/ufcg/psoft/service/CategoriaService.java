package com.ufcg.psoft.service;

import com.ufcg.psoft.model.Categoria;

public interface CategoriaService {
    Categoria save(Categoria categoria);
    Categoria findByNome(String nome);
}
