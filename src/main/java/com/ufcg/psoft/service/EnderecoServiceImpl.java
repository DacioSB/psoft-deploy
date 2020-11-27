package com.ufcg.psoft.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Endereco;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.EnderecoDTO;
import com.ufcg.psoft.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	UserService userService;

	@Override
	public Endereco save(EnderecoDTO enderecoDTO, long idUser) {
		Endereco endereco = new Endereco(enderecoDTO.getEstado(), enderecoDTO.getCidade(), enderecoDTO.getBairro(),
				enderecoDTO.getRua(), enderecoDTO.getNumero(), enderecoDTO.getCep(), enderecoDTO.getComplemento());

		User user = userService.findById(idUser);

		if (user == null) {
			return null;
		}
		
		user.addEnderecos(endereco);

		return this.enderecoRepository.save(endereco);

	}

	@Override
	public Endereco getEnderecoId(long id, long idUser) {

		User user = userService.findById(idUser);

		return user.exibeEnredeco(id);
	}

	@Transactional
	@Override
	public void deleteEnderecoId(Endereco endereco, User user) {
		user.deleteEndereco(endereco.getId());
	}

	@Override
	public List<Endereco> allEndereco(long idUser) {
		User user = userService.findById(idUser);

		return user.getEnderecos();

	}

}
