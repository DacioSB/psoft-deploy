package com.ufcg.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ufcg.psoft.model.Venda;

@Repository
@Transactional
public interface VendaRepository extends JpaRepository<Venda, Long>{

}
