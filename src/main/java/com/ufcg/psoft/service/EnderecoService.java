package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Endereco;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.EnderecoDTO;

public interface EnderecoService {

	Endereco save(EnderecoDTO enderecoDTO, long idUser);
	
	void deleteEnderecoId(Endereco endereco, User user);

	Endereco getEnderecoId(long id, long idUser);
	
	List<Endereco> allEndereco(long idUser);
}
