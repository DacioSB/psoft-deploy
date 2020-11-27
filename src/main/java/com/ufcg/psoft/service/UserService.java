package com.ufcg.psoft.service;

import com.ufcg.psoft.model.User;

public interface UserService {
    
    boolean verificaLoginIgual(String login);
    void save(User user);
    User findById(Long id);
}
