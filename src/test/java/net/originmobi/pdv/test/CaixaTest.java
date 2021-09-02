package net.originmobi.pdv.test;

import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.enumerado.caixa.CaixaTipo;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CaixaTest {
    Caixa caixa;
    Usuario user;
    CaixaTipo tipo;

    @BeforeEach
    void init(){
        //tipo = mock(CaixaTipo.class);
        user = mock(Usuario.class);
        this.caixa = new Caixa("descrição do caixa",tipo.CAIXA,
                0.00, 0.00, 0.00,
                new Date(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), user);
        }

    @Test
    void testGetDescricao() {
        String desc = this.caixa.getDescricao();
        assertEquals(desc, this.caixa.getDescricao());
    }
    @Test
    void testSetDescricao() {
        String desc = "Nova Descrição";
        this.caixa.setDescricao(desc);
        assertEquals(desc, this.caixa.getDescricao());
    }

    @Test
    void testGetCaixaTipo() {
        assertNotNull(this.caixa.getTipo());
        assertEquals(tipo.CAIXA, this.caixa.getTipo());
    }

    @Test
    void testCaixaTipoIsBanco() {
        assertFalse(this.caixa.isBanco());
    }

    @Test
    void testCaixaTipoIsCofre() {
        assertFalse(this.caixa.isCofre());
    }

    @Test
    void tesSetCaixaTipoBanco() {
        this.caixa.setTipo(tipo.BANCO);
        assertTrue(this.caixa.isBanco());
    }

    @Test
    void tesSetCaixaTipoCofre() {
        this.caixa.setTipo(tipo.COFRE);
        assertTrue(this.caixa.isCofre());
    }

    @Test
    void testGetValorAbertura() {
        Double valor = this.caixa.getValor_abertura();
        assertEquals(valor, this.caixa.getValor_abertura());
    }
    @Test
    void testSetValorAbertura() {
        Double valor = 15.00;
        this.caixa.setValor_abertura(valor);
        assertEquals(valor, this.caixa.getValor_abertura());
    }

    @Test
    void testGetValorFechamento() {
        Double valor = this.caixa.getValor_fechamento();
        assertEquals(valor, this.caixa.getValor_fechamento());
    }
    @Test
    void testSetValorFechamento() {
        Double valor = 15.00;
        this.caixa.setValor_fechamento(valor);
        assertEquals(valor, this.caixa.getValor_fechamento());
    }

    @Test
    void testGetValorTotal() {
        Double valor = this.caixa.getValor_total();
        assertEquals(valor, this.caixa.getValor_total());
    }
    @Test
    void testSetValorTotal() {
        Double valor = 15.00;
        this.caixa.setValor_total(valor);
        assertEquals(valor, this.caixa.getValor_total());
    }

    @Test
    void testGetDataCadastro() {
        Date data = this.caixa.getData_cadastro();
        assertEquals(data, this.caixa.getData_cadastro());
    }
    @Test
    void testSetDataCadastro() {
        Date newDate = new Date(System.currentTimeMillis());
        this.caixa.setData_cadastro(newDate);
        assertEquals(newDate, this.caixa.getData_cadastro());
    }
    @Test
    void testGetDataFechamentoo() {
        Timestamp data = this.caixa.getData_fechamento();
        assertEquals(data, this.caixa.getData_fechamento());
    }
    @Test
    void testSetDataFechamento() {
        Timestamp newDate = new Timestamp(System.currentTimeMillis());
        this.caixa.setData_fechamento(newDate);
        assertEquals(newDate, this.caixa.getData_fechamento());
    }

    @Test
    void testGetUsuario() {
       Usuario usr = this.caixa.getUsuario();
        assertNotNull(usr);
    }
    @Test
    void testSetUsuario() {
        Usuario usr = mock(Usuario.class);
        caixa.setUsuario(usr);
        assertNotNull(usr);
        assertEquals(usr, this.caixa.getUsuario());
    }
}
