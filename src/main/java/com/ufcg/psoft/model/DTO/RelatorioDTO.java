package com.ufcg.psoft.model.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ufcg.psoft.model.Venda;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelatorioDTO {

    private List<Venda> listaVendas = new ArrayList<>();
    private List<LoteShowDTO> listaLoteDTOs = new ArrayList<>();
    private int qtdLotes;
    private BigDecimal receitas;

    public RelatorioDTO(List<LoteShowDTO> loteDTOs, List<Venda> vendas, int qtdLotes){
        this.listaVendas = vendas;
        this.listaLoteDTOs = loteDTOs;
        this.qtdLotes = qtdLotes;
        this.receitas = setReceitas(vendas);
    }
    private BigDecimal setReceitas(List<Venda> vendas) {
        BigDecimal soma = BigDecimal.valueOf(.0);
        for (Venda venda : vendas) {
            soma = soma.add(venda.getValorTotal());
        }
        return soma;
    }

    public List<Venda> getListaVendas() {
        return listaVendas;
    }

    public List<LoteShowDTO> getListaLoteDTOs() {
        return listaLoteDTOs;
    }

    public int getQtdLotes() {
        return qtdLotes;
    }

    public BigDecimal getReceitas() {
        return receitas;
    }
    
}
