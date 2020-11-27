package com.ufcg.psoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ufcg.psoft.model.Produto;
@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	List<Produto> findBySituacao(int situacao);
}
