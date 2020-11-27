package com.ufcg.psoft.model.DTO;

import com.ufcg.psoft.model.Categoria;

public class CategoriaFormDTO {
    private String nome;

    public CategoriaFormDTO() {

    }
    public CategoriaFormDTO(Categoria categoria) {
        this.nome = categoria.getNome();
    }

    public String getNome() {
        return nome;
    }
    public Categoria convert(){
        return new Categoria(this.nome);
    }
   
}
