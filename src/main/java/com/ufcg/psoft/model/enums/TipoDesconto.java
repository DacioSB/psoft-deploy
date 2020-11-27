package com.ufcg.psoft.model.enums;

public enum TipoDesconto {
    SEM_DESCONTO(1, "Sem Desconto"),
	BOM_DESCONTO(2, "Bom Desconto"),
    OTIMO_DESCONTO(3, "Otimo Desconto"),
    SUPER_DESCONTO(4, "Super Desconto");
	
	private int cod;
	private String descricao;
	
	private TipoDesconto(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static TipoDesconto toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (TipoDesconto x : TipoDesconto.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
