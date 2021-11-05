package net.originmobi.pdv.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import net.originmobi.pdv.enumerado.VendaSituacao;

@Entity
@Getter
@Setter
public class Venda implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long codigo;
    protected String observacao;

    @Column(name = "valor_produtos")
    @NumberFormat(pattern = "##,##0.00")
    protected Double valorProdutos;

    @Column(name = "valor_desconto")
    @NumberFormat(pattern = "##,##0.00")
    protected Double valorDesconto;

    @Column(name = "valor_acrescimo")
    @NumberFormat(pattern = "##,##0.00")
    protected Double valorAcrescimo;

    @Column(name = "valor_total")
    @NumberFormat(pattern = "##,##0.00")
    protected Double valorTotal;

    @Enumerated(EnumType.STRING)
    protected VendaSituacao situacao;

    @Column(name = "data_cadastro")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    protected Timestamp dataCadastro;

    @Column(name = "data_finalizado")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    protected Timestamp dataFinalizado;

    @Column(name = "data_cancelado")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    protected Timestamp dataCancelado;

    @ManyToOne
    protected Pessoa pessoa;

    @ManyToOne
    protected Usuario usuario;

    @ManyToOne
    @JoinTable(name = "pagamento_tipo_venda",
            joinColumns = @JoinColumn(name = "ven_codigo"),
            inverseJoinColumns = @JoinColumn(name = "pag_tipo_codigo"))
    protected PagamentoTipo pagamentotipo;

    @ManyToMany
    @JoinTable(name = "venda_produtos")
    protected List<Produto> produtos;

    public Venda() {
    }

    public Venda(String observacao, Double valorProdutos, Double valorDesconto, Double valorAcrescimo,
            Double valorTotal, VendaSituacao situacao, Timestamp dataCadastro, Timestamp dataFinalizado,
            Timestamp dataCancelado, Pessoa pessoa, Usuario usuario) {
        super();
        this.observacao = observacao;
        this.valorProdutos = valorProdutos;
        this.valorDesconto = valorDesconto;
        this.valorAcrescimo = valorAcrescimo;
        this.valorTotal = valorTotal;
        this.situacao = situacao;
        this.dataCadastro = dataCadastro;
        this.dataFinalizado = dataFinalizado;
        this.dataCancelado = dataCancelado;
        this.pessoa = pessoa;
        this.usuario = usuario;
    }

    public boolean isAberta() {
        return situacao.equals(VendaSituacao.ABERTA);
    }
}
