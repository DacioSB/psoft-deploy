package com.ufcg.psoft.controller;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Endereco;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.EnderecoDTO;
import com.ufcg.psoft.service.EnderecoService;
import com.ufcg.psoft.service.UserService;
import com.ufcg.psoft.util.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EnderecoController {
	/**
	 *
	 */
	private static final String NAO_ESTA_CADASTRADO = " não está cadastrado";

	private static final String USUARIO_COM_ID = "Usuario com id ";

	@Autowired
	UserService userService;

	@Autowired
	EnderecoService enderecoService;


	@PostMapping("/endereco/{idUser}")
	public ResponseEntity<?> addEndereco(@RequestBody EnderecoDTO enderecoDTP, @PathVariable("idUser") long idUser) {

		User user = userService.findById(idUser);

		if (user == null) {
			return new ResponseEntity<>(new CustomErrorType(USUARIO_COM_ID + idUser + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		if (enderecoDTP.getEstado() == null || enderecoDTP.getEstado().equals("")) {
			return new ResponseEntity<>(new CustomErrorType("estado não pode ser vazio ou nullo"), HttpStatus.NOT_FOUND);
		}
		if (enderecoDTP.getCidade() == null || enderecoDTP.getCidade().equals("")) {
			return new ResponseEntity<>(new CustomErrorType("cidade não pode ser vazio ou nullo"), HttpStatus.NOT_FOUND);
		}
		if (enderecoDTP.getBairro() == null || enderecoDTP.getBairro().equals("")) {
			return new ResponseEntity<>(new CustomErrorType("bairro não pode ser vazio ou nullo"), HttpStatus.NOT_FOUND);
		}
		if (enderecoDTP.getRua() == null || enderecoDTP.getRua().equals("")) {
			return new ResponseEntity<>(new CustomErrorType("rua não pode ser vazio ou nullo"), HttpStatus.NOT_FOUND);
		}

		if (enderecoDTP.getCep() == null || enderecoDTP.getCep().equals("")) {
			return new ResponseEntity<>(new CustomErrorType("cep não pode ser vazio ou nullo"), HttpStatus.NOT_FOUND);
		}
		if (enderecoDTP.getNumero() == null) {
			return new ResponseEntity<>(new CustomErrorType("numero não pode ser nullo"), HttpStatus.NOT_FOUND);
		}
		
		if(user.comparaEndereco(enderecoDTP)) {
			return new ResponseEntity<>(new CustomErrorType("endereco já cadastrado"), HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(enderecoService.save(enderecoDTP, idUser));
	}

	@GetMapping("/endereco/{id}/user/{idUser}")
	public ResponseEntity<?> selecionaEndereco(@PathVariable("id") long id, @PathVariable("idUser") long idUser) {

		User user = userService.findById(idUser);

		if (user == null) {
			return new ResponseEntity<>(new CustomErrorType(USUARIO_COM_ID + idUser + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		Endereco endereco = user.exibeEnredeco(id);

		if (endereco == null) {
			return new ResponseEntity<>(new CustomErrorType("endereco com id " + id + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(endereco);
	}
	@Transactional
	@DeleteMapping("/endereco/{id}/user/{idUser}")
	public ResponseEntity<?> removeEndereco(@PathVariable("id") long id, @PathVariable("idUser") long idUser) {

		User user = userService.findById(idUser);

		if (user == null) {

			return new ResponseEntity<>(new CustomErrorType(USUARIO_COM_ID + idUser + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		Endereco endereco = enderecoService.getEnderecoId(id, idUser);

		if (endereco == null) {
			return new ResponseEntity<>(new CustomErrorType("endereco com id " + id + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		enderecoService.deleteEnderecoId(endereco, user);

		return new ResponseEntity<Endereco>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/endereco/{idUser}")
	public ResponseEntity<?> allEnderecos(@PathVariable("idUser") long idUser) {

		User user = userService.findById(idUser);

		if (user == null) {

			return new ResponseEntity<>(new CustomErrorType(USUARIO_COM_ID + idUser + NAO_ESTA_CADASTRADO),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(enderecoService.allEndereco(idUser), HttpStatus.OK);
	}

}
