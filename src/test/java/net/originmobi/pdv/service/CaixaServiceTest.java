package net.originmobi.pdv.service;

import net.originmobi.pdv.enumerado.caixa.CaixaTipo;
import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.CaixaLancamento;
import net.originmobi.pdv.repository.CaixaRepository;
import net.originmobi.pdv.utilitarios.CaixaFactory;
import net.originmobi.pdv.utilitarios.UsuarioFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SecurityContextHolder.class, SecurityContext.class })
public class CaixaServiceTest {

    @InjectMocks
    private CaixaService caixaService;

    @Mock
    private CaixaRepository caixaRepositoryMock;

    @Mock
    private UsuarioService usuarioServiceMock;

    @Mock
    private CaixaLancamentoService caixaLancamentoServiceMock;

    @BeforeEach
    void setUp () {

        BDDMockito.when(caixaRepositoryMock.caixaAberto())
                .thenReturn(null);
        BDDMockito.when(caixaRepositoryMock.caixasAbertos())
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("CAIXA")));


        BDDMockito.when(usuarioServiceMock.buscaUsuario(ArgumentMatchers.anyString()))
                .thenReturn((UsuarioFactory.createUserValid()));

        BDDMockito.when(caixaRepositoryMock.save(ArgumentMatchers.any(Caixa.class)))
                .thenReturn(CaixaFactory.createValidCaixa(CaixaTipo.valueOf("CAIXA")));
    }

    @Test
    @DisplayName("Teste do metodo cadastro")
    @WithMockUser("teste")
    public void cadastraCaixa() {
        Caixa caixa = CaixaFactory.createValidCaixa(CaixaTipo.valueOf("CAIXA"));
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
    }

    //Increasing Coverage
    @Test
    @DisplayName("Teste do metodo cadastro")
    @WithMockUser("teste")
    public void cadastraCaixaBanco() {
        Caixa caixa = CaixaFactory.createValidCaixa(CaixaTipo.valueOf("BANCO"));
        caixa.setAgencia("1234-5");
        caixa.setConta("123456789-10");
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
    }

    //Increasing Coverage
    @Test
    @DisplayName("Teste do metodo cadastro com caixa aberto")
    @WithMockUser("teste")
    public void cadastraCaixaComCaixaAberto() {
        BDDMockito.when(caixaRepositoryMock.caixaAberto())
                .thenReturn(Optional.of(CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"))));
        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));

       RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () ->caixaService.cadastro(caixa));
        assertTrue(runtimeException.getMessage().contains("Existe caixa de dias anteriores em aberto, favor verifique"));
    }

    //Increasing Coverage
    @Test
    @DisplayName("Teste do metodo cadastro com valor de abertura negativo")
    @WithMockUser("teste")
    public void cadastraCaixaComValorAberturaInvalido() {

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        caixa.setValorAbertura(-5.0);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () ->caixaService.cadastro(caixa));
        assertTrue(runtimeException.getMessage().contains("Valor informado é inválido"));
    }

    //Increasing Coverage
    @Test
    @DisplayName("Teste do metodo cadastro com valor de abertura > 0")
    @WithMockUser("teste")
    public void cadastraCaixaComValorAberturaMaiorQueZero() {
        BDDMockito.when(caixaLancamentoServiceMock.lancamento(ArgumentMatchers.any(CaixaLancamento.class)))
                .thenReturn("Lançamento realizado com sucesso");

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        caixa.setValorAbertura(5.0);
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
    }
    @Test
    @DisplayName("Teste do metodo fechaCaixa")
    @WithMockUser(password = "123")
    public void fechaCaixa() {

        BDDMockito.when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"))));

        String expectedMsg = "Caixa fechado com sucesso";
        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        String msg = caixaService.fechaCaixa(caixa.getCodigo(), "123");
        assertEquals(expectedMsg, msg);

    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo fechaCaixa com senha vazia")
    @WithMockUser("teste")
    public void fechaCaixaSenhaVazia() {

        BDDMockito.when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"))));

        String expectedMsg = "Favor, informe a senha";
        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        String msg = caixaService.fechaCaixa(caixa.getCodigo(), "");
        assertEquals(expectedMsg, msg);

    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo fechaCaixa com senha incorreta")
    @WithMockUser("teste")
    public void fechaCaixaSenhaIncorreta() {

        BDDMockito.when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"))));

        String expectedMsg = "Senha incorreta, favor verifique";
        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        String msg = caixaService.fechaCaixa(caixa.getCodigo(), "TÁ ERRADA");
        assertEquals(expectedMsg, msg);

    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo fechaCaixa")
    @WithMockUser("teste")
    public void fechaCaixaJaFechado() {

        BDDMockito.when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CaixaFactory.createValidCaixa(CaixaTipo.valueOf("CAIXA"))));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () ->caixaService.fechaCaixa(caixa.getCodigo(), caixa.getUsuario().getSenha()));
        assertTrue(runtimeException.getMessage().contains("Caixa já esta fechado"));

    }

    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo listarCaixas com filter")
    @WithMockUser("teste")
    public void listarCaixasComFilter() {

        BDDMockito.when(caixaRepositoryMock.buscaCaixasPorDataAbertura(ArgumentMatchers.any(Date.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("CAIXA")));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        Long expectedCodigo = caixa.getCodigo();
        List<Caixa> caixas = caixaService.listarCaixas(CaixaFactory.createCaixaFilter());
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);


    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo listarCaixas sem filter")
    @WithMockUser("teste")
    public void listarCaixasSemFilter() {

        BDDMockito.when(caixaRepositoryMock.listaCaixas())
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("CAIXA")));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        Long expectedCodigo = caixa.getCodigo();
        List<Caixa> caixas = caixaService.listarCaixas(CaixaFactory.createCaixaFilterWithoutDate());
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);


    }

    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo listarBancos com filter")
    @WithMockUser("teste")
    public void listarBancosComFilter() {

        BDDMockito.when(caixaRepositoryMock.buscaCaixaTipoData(
                ArgumentMatchers.any(CaixaTipo.class), ArgumentMatchers.any(Date.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("BANCO")));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("BANCO"));
        Long expectedCodigo = caixa.getCodigo();
        List<Caixa> caixas = caixaService.listaBancosAbertosTipoFilterBanco(
                CaixaTipo.BANCO, CaixaFactory.createBancoFilter());
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);


    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo listarBancos sem filter")
    @WithMockUser("teste")
    public void listarBancosSemFilter() {

        BDDMockito.when(caixaRepositoryMock.buscaCaixaTipo(ArgumentMatchers.any(CaixaTipo.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("BANCO")));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("BANCO"));
        Long expectedCodigo = caixa.getCodigo();
        List<Caixa> caixas = caixaService.listaBancosAbertosTipoFilterBanco(
                CaixaTipo.BANCO, CaixaFactory.createBancoFilterWithoutDate());
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);


    }
    //Increasing coverage
    @Test
    @DisplayName("Teste do metodo listaBancos")
    @WithMockUser("teste")
    public void listaBancos() {

        BDDMockito.when(caixaRepositoryMock.buscaBancos(ArgumentMatchers.any(CaixaTipo.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("BANCO")));

        Caixa caixa = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("BANCO"));
        Long expectedCodigo = caixa.getCodigo();
        List<Caixa> caixas = caixaService.listaBancos();
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);
        Assertions.assertThat(caixas.get(0).getTipo()).isEqualTo(CaixaTipo.BANCO);


    }

    @Test
    @DisplayName("Teste do metodo caixaAberto")
    public void caixaAberto(){

        BDDMockito.when(caixaRepositoryMock.caixaAberto())
                .thenReturn(Optional.of(CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"))));

        Caixa cx = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA"));
        Optional<Caixa> caixa = caixaService.caixaAberto();
        Assertions.assertThat(caixa).isNotNull();
        Assertions.assertThat(caixa.get().getCodigo()).isNotNull().isEqualTo(cx.getCodigo());

    }

    @Test
    @DisplayName("Teste do metodo caixaAbertos")
    public void CaixasAbertos(){
        Long expectedCodigo = CaixaFactory.createValidCaixaToBeClosed(CaixaTipo.valueOf("CAIXA")).getCodigo();
        List<Caixa> caixas = caixaService.caixasAbertos();
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);
    }

    @Test
    @DisplayName("Teste do metodo busca")
    public void busca(){

        BDDMockito.when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CaixaFactory.createValidCaixa(CaixaTipo.valueOf("CAIXA"))));

        Caixa cx = CaixaFactory.createValidCaixa(CaixaTipo.valueOf("CAIXA"));
        Optional<Caixa> caixa = caixaService.busca(cx.getCodigo());
        assertNotNull(caixa);
        assertEquals(cx.getCodigo(), caixa.get().getCodigo());
    }

    @Test
    @DisplayName("Teste do metodo listaCaixasAbertosTipo com tipo = caixa")
    public void listaCaixasAbertosTipoCAIXA() {

        BDDMockito.when(caixaRepositoryMock.buscaCaixaTipo(ArgumentMatchers.any(CaixaTipo.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("CAIXA")));

        List<Caixa> cxs = CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("CAIXA"));
        List<Caixa> caixas = caixaService.listaCaixasAbertosTipo(CaixaTipo.valueOf("CAIXA"));
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(cxs.get(0).getCodigo());
        assertEquals(caixas.get(0).getTipo(), CaixaTipo.CAIXA);
    }

    @Test
    @DisplayName("Teste do metodo listaCaixasAbertosTipo com tipo = banco")
    public void listaCaixasAbertosTipoBANCO() {

        BDDMockito.when(caixaRepositoryMock.buscaCaixaTipo(ArgumentMatchers.any(CaixaTipo.class)))
                .thenReturn(CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("BANCO")));

        List<Caixa> cxs = CaixaFactory.createValidListCaixa(CaixaTipo.valueOf("BANCO"));
        List<Caixa> caixas = caixaService.listaCaixasAbertosTipo(CaixaTipo.valueOf("BANCO"));
        Assertions.assertThat(caixas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(cxs.get(0).getCodigo());
        assertEquals(caixas.get(0).getTipo(), CaixaTipo.BANCO);
    }
}
