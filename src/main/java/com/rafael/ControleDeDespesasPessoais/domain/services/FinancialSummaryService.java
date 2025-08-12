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
			Long usuario_id,
			Long mes,
			Long ano) {
		BigDecimal totalReceitas = this.transactionRepository.findTotalValueForType(usuario_id, TransactionType.RECEITA, mes, ano);
		BigDecimal totalDespesas = this.transactionRepository.findTotalValueForType(usuario_id, TransactionType.DESPESA, mes, ano);
		List<ExpenseByCategoryDTO> despesaPorCategoria = this.transactionRepository.findDespesasPorCategoria(TransactionType.DESPESA, usuario_id, mes, ano);
		
		BigDecimal saldo = totalReceitas.subtract(totalDespesas);
		
		return new FinancialSummary(totalReceitas, totalDespesas, saldo, despesaPorCategoria);
	}

}
