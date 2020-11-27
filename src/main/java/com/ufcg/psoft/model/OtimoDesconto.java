package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("OtimoDesconto")
public class OtimoDesconto extends Desconto{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OtimoDesconto() {
        super("Otimo desconto");
    }

    @Override
    public BigDecimal desconto(BigDecimal precoProduto) {
        return precoProduto.subtract(precoProduto.multiply(BigDecimal.valueOf(0.25)));
    }
}
