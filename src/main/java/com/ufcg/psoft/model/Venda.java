package com.ufcg.psoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Venda implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "id.venda")
	private Set<VendaProduto> itens = new HashSet<>();

	public Venda() {

	}

	public Venda(long id, Set<VendaProduto> produtos) {
		this.id = id;
		this.itens = produtos;
	}

	public Long getId() {
		return this.id;
	}

	public BigDecimal getValorTotal() {

		BigDecimal valorTotal = new BigDecimal(0);

		for (VendaProduto vendaProd : itens) {
			valorTotal = valorTotal.add(vendaProd.getSubtotal());

		}
		return valorTotal;

	}

	public Set<VendaProduto> getItens() {
		return itens;
	}

	public void setItens(Set<VendaProduto> produtos) {
		this.itens = produtos;
	}

	public void insereProduto(VendaProduto produto) {
		this.itens.add(produto);
	}

	public VendaProduto existeVendaProduto(long id) {
		for (VendaProduto vendaProd : itens) {
			if (vendaProd.getProduto().getId() == id) {
				return vendaProd;
			}
		}
		return null;
	}

	public void setItem(VendaProduto vendProd) {
		insereProduto(vendProd);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Venda other = (Venda) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.id + " " + this.getValorTotal();
	}

}
