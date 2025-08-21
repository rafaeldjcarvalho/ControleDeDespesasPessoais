package com.rafael.ControleDeDespesasPessoais.domain.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.services.TransactionService;
import com.rafael.ControleDeDespesasPessoais.infra.security.TokenService;

@Controller
@RequestMapping("/api/transacoes")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TokenService tokenService;
	
	//- /transacoes?mes=8&ano=2025&id_categoria=3
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> listarTodasTransacoes(
			@RequestHeader("Authorization") String token,
			@RequestParam(required = false) Long mes,
			@RequestParam(required = false) Long ano,
			@RequestParam(required = false) Long id_categoria) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		List<TransactionDTO> lista = this.transactionService.listarTransacoes(userEmail, mes, ano, id_categoria);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id_transacao}")
	public ResponseEntity<TransactionDTO> listarTransacao(@RequestHeader("Authorization") String token, @PathVariable Long id_transacao) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		TransactionDTO transacao = this.transactionService.listarTransacao(userEmail, id_transacao);
		return ResponseEntity.ok(transacao);
	}
	
	@PostMapping
	public ResponseEntity<TransactionDTO> criarTransacao(@RequestBody TransactionDTO transacao) {
		TransactionDTO novaTransacao = this.transactionService.criarTransacao(transacao);
		return ResponseEntity.ok(novaTransacao);
	}
	
	@PutMapping("/{id_transacao}")
	public ResponseEntity<TransactionDTO> atualizarTransacao(
			@RequestHeader("Authorization") String token, 
			@PathVariable Long id_transacao,
			@RequestBody TransactionDTO transacao) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		TransactionDTO novaTransacao = this.transactionService.atualizarTransacao(id_transacao, transacao);
		return ResponseEntity.ok(novaTransacao);
	}
	
	@DeleteMapping("/{id_transacao}")
	public ResponseEntity<?> deletarTransacao(@RequestHeader("Authorization") String token, @PathVariable Long id_transacao) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		this.transactionService.deletarTransacao(id_transacao, userEmail);
		return ResponseEntity.noContent().build();
	}
}
