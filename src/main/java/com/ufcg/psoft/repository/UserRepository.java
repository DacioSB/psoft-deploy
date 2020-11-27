package com.ufcg.psoft.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.ufcg.psoft.model.User;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    @Transactional
    @ReadOnlyProperty
    Optional<User> findByLogin(String login);
}
