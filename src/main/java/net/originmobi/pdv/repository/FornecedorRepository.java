package net.originmobi.pdv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.originmobi.pdv.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	public List<Fornecedor> findByNomeContaining(String nome);

	public Fornecedor findByCnpj(String cnpj);

	public Fornecedor findByCodigo(Long codigo);

}
