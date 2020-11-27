package com.ufcg.psoft.model.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFormDTO {
    private String login;
    private String senha;
    @JsonIgnore
    @Autowired
    private BCryptPasswordEncoder bcpe;

    public UserFormDTO(){

    }
    public UserFormDTO(User user) {
        this.setLogin(user.getLogin());
        this.setSenha(user.getSenha());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static List<UserFormDTO> convert(List<User> users) {
		return users.stream().map(UserFormDTO::new).collect(Collectors.toList());
    }
    
    public User convert(){
        this.bcpe = new BCryptPasswordEncoder();
        return new User(this.login, this.bcpe.encode(this.senha));
    }
    public User update(Long id, UserRepository userRepository){
        User user = userRepository.getOne(id);
        user.setLogin(this.login);
        user.setSenha(this.bcpe.encode(this.senha));
        return user;
    }

}
