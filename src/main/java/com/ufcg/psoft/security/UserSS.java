package com.ufcg.psoft.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.ufcg.psoft.model.enums.PerfilCliente;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSS implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String login;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(){

    }
    public UserSS(Long id, String login, String senha, Set<PerfilCliente> perfis){
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
    }

    public Long getId(){
        return this.id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }    
    @Override
    public boolean isAccountNonExpired() {
       
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
