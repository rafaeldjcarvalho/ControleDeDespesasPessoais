package com.rafael.ControleDeDespesasPessoais.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.FinancialSummary;
import com.rafael.ControleDeDespesasPessoais.domain.services.FinancialSummaryService;

@Controller
@RequestMapping("/api/dashboard")
public class FinancialSummaryController {
	
	@Autowired
	private FinancialSummaryService summaryService;
	
	@GetMapping("/summary")
	public ResponseEntity<FinancialSummary> resumoFinanceiro(
			@RequestParam("usuario_id") Long usuario_id,
			@RequestParam("categoria_id") Long categoria_id,
			@RequestParam("mes") Long mes,
			@RequestParam("ano") Long ano) {
		FinancialSummary sumario = this.summaryService.getResumoFinanceiro(usuario_id, categoria_id, mes, ano);
		return ResponseEntity.ok(sumario);
	}
}
