package com.rafael.ControleDeDespesasPessoais.domain.dtos;

public record CategoryDTO(
		Long id,
		String nome,
		String descricao,
		Long id_usuario) {}
