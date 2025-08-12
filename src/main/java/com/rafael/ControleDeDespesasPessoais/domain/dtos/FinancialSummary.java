package com.rafael.ControleDeDespesasPessoais.domain.dtos;

import java.math.BigDecimal;
import java.util.List;

public record FinancialSummary(
		BigDecimal totalReceitas,
		BigDecimal totalDespesas,
		BigDecimal saldo,
		List<ExpenseByCategoryDTO> despesasPorCategoria) {

}
