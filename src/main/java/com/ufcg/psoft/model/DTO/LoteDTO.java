package com.ufcg.psoft.model.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LoteDTO {

    private int numeroDeItens;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataDeValidade;

    public LoteDTO() {
    }
    public LoteDTO(int numeroDeItens, Date dataDeValidade) {
        super();
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    public Date getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(Date dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
