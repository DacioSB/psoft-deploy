package com.ufcg.psoft.model.DTO;

public class ProdutoDisponibilidade {

	private int situacao;
	
	public ProdutoDisponibilidade() {
    }
	
	public ProdutoDisponibilidade(int situacao) {
		super();
		this.situacao = situacao;
    }

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
}
