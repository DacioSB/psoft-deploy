package com.ufcg.psoft.model.DTO;

import java.math.BigDecimal;

public class ProdutoDispEPreco {
	
	private int situacao;
	private BigDecimal preco;
	
	public ProdutoDispEPreco() {
    }
	
	public ProdutoDispEPreco(int situacao, BigDecimal preco) {
		super();
		this.situacao = situacao;
		this.preco = preco;
    }

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}
