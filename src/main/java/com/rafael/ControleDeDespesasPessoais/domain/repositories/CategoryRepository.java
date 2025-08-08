package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<String> findByNome(String nome);
}
