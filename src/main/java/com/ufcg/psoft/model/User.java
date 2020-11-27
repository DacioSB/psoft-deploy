package com.ufcg.psoft.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.model.DTO.EnderecoDTO;
import com.ufcg.psoft.model.enums.PerfilCliente;

@Entity
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String login;
	@JsonIgnore
	private String senha;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();

	public User() {
		super();
		addPerfil(PerfilCliente.CLIENTE);
	}

	public User(String login, String senha) {
		this.login = login;
		this.senha = senha;
		addPerfil(PerfilCliente.CLIENTE);
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco exibeEnredeco(long id) {
		for (Endereco endereco : enderecos) {
			if (endereco.getId() == id) {
				return endereco;
			}
		}
		return null;
	}
	
	public boolean comparaEndereco(EnderecoDTO endereco) {
		for(Endereco end: enderecos) {
			if(end.getCep().equals(endereco.getCep()) && end.getBairro().equals(endereco.getBairro()) &&
					end.getRua().equals(endereco.getRua()) && end.getNumero().equals(endereco.getNumero())) {
				return true;
			}
		}
		return false;
	}

	public void addEnderecos(Endereco endereco) {
		this.enderecos.add(endereco);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<PerfilCliente> getPerfis() {
		return this.perfis.stream().map(x -> PerfilCliente.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(PerfilCliente perfil) {
		this.perfis.add(perfil.getCod());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void deleteEndereco(Long id) {
		for (int i = 0; i < this.enderecos.size(); i++) {
			if (this.enderecos.get(i).getId() == id) {
				this.enderecos.remove(i);
				break;
			}
		}
	}

}
