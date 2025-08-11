package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("SELECT t FROM Transaction t JOIN t.usuario u WHERE u.id = :usuario_id")
	List<Transaction> findTransactionByUser(@Param("usuario_id") Long usuario_id);
	
	@Query("SELECT t FROM Transaction t JOIN t.usuario u WHERE u.id = :usuario_id AND" +
			"(:categoriaId IS NULL OR t.categoria.id = :categoriaId) AND" +
			"(:mes IS NULL OR MONTH(t.data) = :mes) AND" +
			"(:ano IS NULL OR YEAR(t.data) = :ano)")
	List<Transaction> findTransactionByUserFiltered(
			@Param("usuario_id") Long usuario_id,
			@Param("categoriaId") Long categoriaId,
			@Param("mes") int mes,
			@Param("ano") int ano);
}
