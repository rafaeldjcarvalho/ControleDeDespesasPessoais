package com.rafael.ControleDeDespesasPessoais.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;

public record TransactionDTO(
		Long id,
		String descricao,
		BigDecimal valor,
		LocalDate data,
		LocalTime hora,
		TransactionType tipo,
		Long id_categoria,
		Long id_usuario) {}
