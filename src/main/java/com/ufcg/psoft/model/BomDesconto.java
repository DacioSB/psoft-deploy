package com.ufcg.psoft.model;


import java.math.BigDecimal;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("BomDesconto")
public class BomDesconto extends Desconto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BomDesconto() {
        super("Bom desconto");
    }
    @Override
    public BigDecimal desconto(BigDecimal precoProduto) {
        return precoProduto.subtract(precoProduto.multiply(BigDecimal.valueOf(0.1)));
    }
    
}
