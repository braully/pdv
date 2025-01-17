package net.originmobi.pdv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empresa_parametros")
public class EmpresaParametro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "serie_nfe")
	private int serieNfe;

	@Column(name = "tipo_ambiente_codigo")
	private int ambiente;

	private Double pCredSN;

	public EmpresaParametro() {
		super();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public int getSerieNfe() {
		return serieNfe;
	}

	public void setSerieNfe(int serieNfe) {
		this.serieNfe = serieNfe;
	}

	public int getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(int ambiente) {
		this.ambiente = ambiente;
	}

	public Double getpCredSN() {
		return pCredSN;
	}

	public void setpCredSN(Double pCredSN) {
		this.pCredSN = pCredSN;
	}

}
