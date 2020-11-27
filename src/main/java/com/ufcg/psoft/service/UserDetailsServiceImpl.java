package com.ufcg.psoft.service;

import java.util.Optional;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.repository.UserRepository;
import com.ufcg.psoft.security.UserSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByLogin(login);
        
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException(login);
        }
        User user = userOpt.get();
        return new UserSS(user.getId(), user.getLogin(), user.getSenha(), user.getPerfis());
	}
    
}
