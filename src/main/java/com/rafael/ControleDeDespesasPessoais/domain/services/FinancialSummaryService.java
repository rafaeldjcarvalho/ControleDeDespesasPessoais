package com.rafael.ControleDeDespesasPessoais.domain.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.ExpenseByCategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.FinancialSummary;
import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.TransactionRepository;

@Service
public class FinancialSummaryService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public FinancialSummary getResumoFinanceiro(
			String usuarioEmail,
			Long mes,
			Long ano) {
		BigDecimal totalReceitas = this.transactionRepository.findTotalValueForType(usuarioEmail, TransactionType.RECEITA, mes, ano);
		BigDecimal totalDespesas = this.transactionRepository.findTotalValueForType(usuarioEmail, TransactionType.DESPESA, mes, ano);
		List<ExpenseByCategoryDTO> despesaPorCategoria = this.transactionRepository.findDespesasPorCategoria(TransactionType.DESPESA, usuarioEmail, mes, ano);
		
		BigDecimal saldo = totalReceitas.subtract(totalDespesas);
		
		return new FinancialSummary(totalReceitas, totalDespesas, saldo, despesaPorCategoria);
	}

}
