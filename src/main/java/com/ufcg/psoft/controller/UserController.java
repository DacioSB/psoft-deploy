package com.ufcg.psoft.controller;

import java.net.URI;


import javax.transaction.Transactional;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.UserDTO;
import com.ufcg.psoft.model.DTO.UserFormDTO;
import com.ufcg.psoft.service.UserService;
import com.ufcg.psoft.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserService uService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Validated UserFormDTO form, UriComponentsBuilder uriBuilder){
        String validacao = this.validaCampos(form.getLogin(), form.getSenha());

        if(validacao != null){
            if(validacao.equals("login")){
                return new ResponseEntity<>(new CustomErrorType("campo login não é opcional"), HttpStatus.NOT_ACCEPTABLE);
            }else if(validacao.equals("senha")){
                return new ResponseEntity<>(new CustomErrorType("campo senha não é opcional"), HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if (this.uService.verificaLoginIgual(form.getLogin())){
            return new ResponseEntity<>(new CustomErrorType("usuario existente"), HttpStatus.NOT_ACCEPTABLE);
        } else {
            User user = form.convert();
            this.uService.save(user);

            URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).body(new UserDTO(user));
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> encontraCliente(@PathVariable Long id){
        User user = this.uService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new CustomErrorType("User with id  " + id + " not found"),
            HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new UserDTO(user));
    }
    
    private String validaCampos(String login, String senha){
        if (login == null || login.equals("")) {
            return "login";
        }
        if (senha == null || senha.equals("")) {
            return "senha";
        }
        return null;
    } 

}
