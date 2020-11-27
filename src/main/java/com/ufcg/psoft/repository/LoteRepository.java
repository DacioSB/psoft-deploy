package com.ufcg.psoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long>{
	List<Lote> findByProduto(Produto produto);
}
