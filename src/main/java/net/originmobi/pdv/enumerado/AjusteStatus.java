package net.originmobi.pdv.enumerado;

public enum AjusteStatus {
	APROCESSAR("A Processar"), PROCESSADO("Processado");

	private String descricao;

	AjusteStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
