package net.originmobi.pdv.service;

import net.originmobi.pdv.model.MaquinaCartao;
import net.originmobi.pdv.repository.MaquinaCartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaquinaCartaoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaquinaCartaoRepository maquinas;

    public void cadastrar(MaquinaCartao maquinaCartao) {
        try {
            maquinas.save(maquinaCartao);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<MaquinaCartao> lista() {
        return maquinas.findAll();
    }

    public void remove(Long codigo) {
        try {
            maquinas.deleteById(codigo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar remover a máquina, chame o suporte");
        }
    }

}
