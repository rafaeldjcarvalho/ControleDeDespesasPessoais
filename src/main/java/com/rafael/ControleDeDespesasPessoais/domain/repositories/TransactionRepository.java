package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.ExpenseByCategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;
import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("SELECT t FROM Transaction t JOIN t.usuario u WHERE u.email = :usuarioEmail")
	List<Transaction> findTransactionByUser(@Param("usuarioEmail") String usuarioEmail);
	
	@Query("SELECT t FROM Transaction t JOIN t.usuario u WHERE u.email = :usuarioEmail AND " +
			"(:categoriaId IS NULL OR t.categoria.id = :categoriaId) AND " +
			"(:mes IS NULL OR MONTH(t.data) = :mes) AND " +
			"(:ano IS NULL OR YEAR(t.data) = :ano)")
	List<Transaction> findTransactionByUserFiltered(
			@Param("usuarioEmail") String usuarioEmail,
			@Param("categoriaId") Long categoriaId,
			@Param("mes") Long mes,
			@Param("ano") Long ano);
	
	@Query("SELECT SUM(t.valor) FROM Transaction t JOIN t.usuario u WHERE u.email = :usuarioEmail AND t.tipo = :tipo AND " + 
			"MONTH(t.data) = :mes AND YEAR(t.data) = :ano")
    BigDecimal findTotalValueForType(@Param("usuarioEmail") String usuarioEmail,
    		@Param("tipo") TransactionType tipo,
    		@Param("mes") Long mes, 
    		@Param("ano") Long ano);
	
	@Query("SELECT c.nome AS categoria, SUM(t.valor) AS total " +
			"FROM Transaction t JOIN t.categoria c JOIN t.usuario u WHERE t.tipo = :tipo AND " + 
	        "u.email = :usuarioEmail AND MONTH(t.data) = :mes AND YEAR(t.data) = :ano " +
	        "GROUP BY t.categoria")
	List<ExpenseByCategoryDTO> findDespesasPorCategoria(
			@Param("tipo") TransactionType tipo,
			@Param("usuarioEmail") String usuarioEmail,
			@Param("mes") Long mes, 
			@Param("ano") Long ano);
}
