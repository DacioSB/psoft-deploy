package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VendaProduto {

	@JsonIgnore
	@EmbeddedId
	private VendaProdutoPK id = new VendaProdutoPK();
	
	private int qtdItems;
	
	private BigDecimal valor;

	public VendaProduto() {

	}

	public VendaProduto(int quantidadeItem, Venda venda, Produto produto, BigDecimal valor) {
		this.qtdItems = quantidadeItem;
		this.valor = valor;
		this.id.setVenda(venda);
		this.id.setProduto(produto);
	}

	public int getQtdItems() {
		return qtdItems;
	}

	public void setQtdItems(int qtdItems) {
		this.qtdItems += qtdItems;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public VendaProdutoPK getId() {
		return id;
	}
	
	public BigDecimal getSubtotal() {
		if((getProduto().getCategoria() == null) || (this.getProduto().getCategoria().getDesconto() == null)){
			return this.valor.multiply(new BigDecimal(this.qtdItems));
		}
		return this.getProduto().getCategoria().getDesconto().desconto(this.valor.multiply(new BigDecimal(this.qtdItems)));
		
	}
	
	@JsonIgnore
	public Venda getVenda() {
		return this.id.getVenda();
	}
	
	@JsonIgnore
	public Produto getProduto() {
		return this.id.getProduto();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaProduto other = (VendaProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VendaProduto [id=" + id + ", qtdItems=" + qtdItems + ", valor=" + valor + "]";
	}
	
}
