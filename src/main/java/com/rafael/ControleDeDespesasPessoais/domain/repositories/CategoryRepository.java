package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<Category> findByNome(String nome);
	
	@Query("SELECT c FROM Category c JOIN c.usuario u WHERE u.email = :usuarioEmail")
	List<Category> findCategoriesByUser(@Param("usuarioEmail") String usuarioEmail);
}
