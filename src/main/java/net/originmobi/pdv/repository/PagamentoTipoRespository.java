package net.originmobi.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.originmobi.pdv.model.PagamentoTipo;

public interface PagamentoTipoRespository extends JpaRepository<PagamentoTipo, Long> {

	public PagamentoTipo findByCodigo(Long codigo);

	@Query("select pt.qtdParcelas from PagamentoTipo pt where pt.codigo = ?1")
	public String quantidadeParcelar(Long codigo);
}
