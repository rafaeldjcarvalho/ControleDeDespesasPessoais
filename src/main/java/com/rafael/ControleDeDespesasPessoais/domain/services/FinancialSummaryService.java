package com.rafael.ControleDeDespesasPessoais.domain.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.ExpenseByCategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.FinancialSummary;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.TransactionRepository;

@Service
public class FinancialSummaryService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public FinancialSummary getResumoFinanceiro(
			Long usuario_id,
			Long categoria_id,
			Long mes,
			Long ano) {
		BigDecimal totalReceitas = this.transactionRepository.findTotalValueForType(usuario_id, "RECEITA", mes, ano);
		BigDecimal totalDespesas = this.transactionRepository.findTotalValueForType(usuario_id, "DESPESA", mes, ano);
		List<ExpenseByCategoryDTO> despesaPorCategoria = this.transactionRepository.findDespesasPorCategoria(categoria_id, usuario_id, mes, ano);
		
		BigDecimal saldo = totalReceitas.subtract(totalDespesas);
		
		return new FinancialSummary(totalReceitas, totalDespesas, saldo, despesaPorCategoria);
	}

}
