package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("SemDesconto")
public class SemDesconto extends Desconto {

    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SemDesconto() {
        super("Sem desconto");
    }

    @Override
    public BigDecimal desconto(BigDecimal precoProduto) {
        return precoProduto;
    }
    
}
