package com.ufcg.psoft.service;

import java.util.Optional;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean verificaLoginIgual(String login) {
        Optional<User> userOpt = this.userRepository.findByLogin(login);
        return userOpt.isPresent();
    }

    @Override
    public void save(User user) {
       this.userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOpt = this.userRepository.findById(id);
        if(userOpt.isPresent()){
            return userOpt.get();
        }
        return null;
    }
    
}
