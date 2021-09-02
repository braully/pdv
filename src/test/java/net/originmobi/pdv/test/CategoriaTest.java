package net.originmobi.pdv.test;

import net.originmobi.pdv.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;


class CategoriaTest {

    private Categoria cat;

    @BeforeEach
    void init() {

        this.cat =  new Categoria(new Long( 17959694), "CAT", new Date(System.currentTimeMillis()));
    }

    @Test
    void testGetCodigo() {
        Long ncodigo = this.cat.getCodigo();

        assertEquals(ncodigo, this.cat.getCodigo());
    }
    @Test
    void testSetCodigo() {
        Long novoCodigo = new Long(57859394);
        this.cat.setCodigo(novoCodigo);
        Long novo = this.cat.getCodigo();

        assertEquals(novoCodigo, novo);
    }

    @Test
    void testGetDescricao() {
        String desc = this.cat.getDescricao();
        assertEquals(desc, this.cat.getDescricao());
    }
    @Test
    void testSetDescricao() {
        String desc = "Nova Descrição";
        this.cat.setDescricao(desc);
        assertEquals(desc, this.cat.getDescricao());
    }
    @Test
    void testGetDataCadastro() {
        Date data = this.cat.getData_cadastro();
        assertEquals(data, this.cat.getData_cadastro());
    }
    @Test
    void testSetDataCadastro() {
        Date newDate = new Date(System.currentTimeMillis());
        this.cat.setData_cadastro(newDate);
        assertEquals(newDate, this.cat.getData_cadastro());
    }

}
