package com.rafael.ControleDeDespesasPessoais.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.FinancialSummary;
import com.rafael.ControleDeDespesasPessoais.domain.services.FinancialSummaryService;
import com.rafael.ControleDeDespesasPessoais.infra.security.TokenService;

@Controller
@RequestMapping("/api/dashboard")
public class FinancialSummaryController {
	
	@Autowired
	private FinancialSummaryService summaryService;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/summary")
	public ResponseEntity<FinancialSummary> resumoFinanceiro(
			@RequestHeader("Authorization") String token,
			@RequestParam("mes") Long mes,
			@RequestParam("ano") Long ano) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		FinancialSummary sumario = this.summaryService.getResumoFinanceiro(userEmail, mes, ano);
		return ResponseEntity.ok(sumario);
	}
}
