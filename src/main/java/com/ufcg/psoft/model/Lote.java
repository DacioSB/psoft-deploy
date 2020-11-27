package com.ufcg.psoft.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class Lote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Produto produto;
    private int numeroDeItens;
    private Date dataDeValidade;

    public Lote() {
        this.id = 0;
    }

    public Lote(Produto produto, int numeroDeItens, Date dataDeValidade) {
        super();
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }

    public Lote(long id, Produto produto, int numeroDeItens, Date dataDeValidade) {
        this.id = id;
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    public Date getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(Date dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", produto=" + produto.getId() +
                ", numeroDeItens=" + numeroDeItens +
                ", dataDeValidade='" + dataDeValidade + '\'' +
                '}';
    }

	public void subtraiItens(int quantidade) {
		this.setNumeroDeItens(getNumeroDeItens() - quantidade);
	}
	
	public void adicionaItens(int quantidade) {
		this.setNumeroDeItens(getNumeroDeItens() + quantidade);
	}

	public boolean pertoValidade() {
		Date dataAtual = new Date();
		long mes = 2592000000L;
		return (this.dataDeValidade.getTime()-dataAtual.getTime()) <= mes;
		
	}
}
