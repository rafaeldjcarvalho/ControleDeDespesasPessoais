package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
