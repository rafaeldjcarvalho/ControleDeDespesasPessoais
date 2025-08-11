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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.services.TransactionService;

@Controller
@RequestMapping("/api/transacoes")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	//- /transacoes?Id_usuario=1&mes=8&ano=2025&id_categoria=3
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> listarTodasTransacoes(
			@RequestParam(required = true) Long id_usuario,
			@RequestParam(required = false) int mes,
			@RequestParam(required = false) int ano,
			@RequestParam(required = false) Long id_categoria) {
		List<TransactionDTO> lista = this.transactionService.listarTransacoes(id_usuario, mes, ano, id_categoria);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id_usuario}/{id_transacao}")
	public ResponseEntity<TransactionDTO> listarTransacao(@PathVariable Long id_usuario, @PathVariable Long id_transacao) {
		TransactionDTO transacao = this.transactionService.listarTransacao(id_usuario, id_transacao);
		return ResponseEntity.ok(transacao);
	}
	
	@PostMapping
	public ResponseEntity<TransactionDTO> criarTransacao(@RequestBody TransactionDTO transacao) {
		TransactionDTO novaTransacao = this.transactionService.criarTransacao(transacao);
		return ResponseEntity.ok(novaTransacao);
	}
	
	@PutMapping
	public ResponseEntity<TransactionDTO> atualizarTransacao(@RequestBody TransactionDTO transacao) {
		TransactionDTO novaTransacao = this.transactionService.atualizarTransacao(transacao);
		return ResponseEntity.ok(novaTransacao);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deletarTransacao(@RequestBody TransactionDTO dados) {
		this.transactionService.deletarTransacao(dados);
		return ResponseEntity.noContent().build();
	}
}
