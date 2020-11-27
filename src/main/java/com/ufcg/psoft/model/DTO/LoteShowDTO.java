package com.ufcg.psoft.model.DTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.psoft.model.Lote;

public class LoteShowDTO {
    
    private Long id;
    private int numeroDeItens;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataDeValidade;

    public LoteShowDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoteShowDTO(Lote lote) {
        this.numeroDeItens = lote.getNumeroDeItens();
        this.dataDeValidade = lote.getDataDeValidade();
        this.setId(lote.getId());
    }
    public LoteShowDTO(int numeroDeItens, Date dataDeValidade) {
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
    public static List<LoteShowDTO> converter(List<Lote> lotes){
        return lotes.stream().map(LoteShowDTO::new).collect(Collectors.toList());
    }
}
