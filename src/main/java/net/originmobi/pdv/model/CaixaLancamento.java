package net.originmobi.pdv.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import net.originmobi.pdv.enumerado.EstiloLancamento;
import net.originmobi.pdv.enumerado.TipoLancamento;

@Entity
@Table(name = "caixa_lancamento")
public class CaixaLancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Size(max = 250, message = "Quantidade máxima de caracteres para observação é de 250")
	private String observacao;

	@NumberFormat(pattern = "##,##0.00")
	private Double valor;

	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;

	@Enumerated(EnumType.STRING)
	private EstiloLancamento estilo;

	@ManyToOne
	private Caixa caixa;

	@ManyToOne
	private Usuario usuario;

	@OneToOne
	private Recebimento recebimento;

	@ManyToOne
	private PagarParcela parcelaPagar;

	@Column(name = "data_cadastro")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Timestamp dataCadastro;

	public CaixaLancamento() {
	}

	public CaixaLancamento(String observacao, Double valor, TipoLancamento tipo, EstiloLancamento estilo, Caixa caixa,
			Usuario usuario) {
		this.observacao = observacao;
		this.valor = valor;
		this.tipo = tipo;
		this.estilo = estilo;
		this.caixa = caixa;
		this.usuario = usuario;
	}

	public boolean isEntrada() {
		return EstiloLancamento.ENTRADA.equals(estilo);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public EstiloLancamento getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloLancamento estilo) {
		this.estilo = estilo;
	}

	public Optional<Caixa> getCaixa() {
		return Optional.ofNullable(caixa);
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
	}

	public void setParcelaPagar(PagarParcela parcelaPagar) {
		this.parcelaPagar = parcelaPagar;
	}

}
