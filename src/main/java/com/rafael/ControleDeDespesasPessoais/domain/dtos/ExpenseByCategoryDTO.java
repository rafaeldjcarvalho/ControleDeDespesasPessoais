package com.rafael.ControleDeDespesasPessoais.domain.dtos;

import java.math.BigDecimal;

public record ExpenseByCategoryDTO(
		String categoria,
		BigDecimal total) {}
