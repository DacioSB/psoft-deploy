package com.ufcg.psoft.model.DTO;

public class EnderecoDTO {
	private String estado;

	private String cidade;

	private String bairro;

	private String rua;

	private String numero;

	private String complemento;

	private String cep;

	public EnderecoDTO(String estado, String cidade, String bairro, String rua, String numero, String complemento,
			String cep) {
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "EnderecoDTO [estado=" + estado + ", cidade=" + cidade + ", bairro=" + bairro + ", rua=" + rua
				+ ", numero=" + numero + ", complemento=" + complemento + ", cep=" + cep + "]";
	}
	
}
