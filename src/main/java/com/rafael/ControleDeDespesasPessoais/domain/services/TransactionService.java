package com.rafael.ControleDeDespesasPessoais.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers.TransactionMapper;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.CategoryRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.TransactionRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	public TransactionDTO criarTransacao(TransactionDTO dados) {
		User usuario = this.userRepository.findById(dados.id_usuario()).orElseThrow(() -> new RuntimeException("User not found"));
		Category categoria = this.categoryRepository.findById(dados.id_categoria()).orElseThrow(() -> new RuntimeException("Category not found"));
		
		Transaction transacao = new Transaction();
		transacao.setDescricao(dados.descricao());
		transacao.setData(dados.data());
		transacao.setHora(dados.hora());
		transacao.setValor(dados.valor());
		transacao.setTipo(dados.tipo());
		transacao.setCategoria(categoria);
		transacao.setUsuario(usuario);
		
		this.salvarTransacao(transacao);
		
		return this.transactionMapper.toDTO(transacao);
	}
	
	public List<TransactionDTO> listarTransacoes(String usuarioEmail, Long mes, Long ano, Long id_categoria) {
		List<Transaction> lista = this.transactionRepository.findTransactionByUserFiltered(usuarioEmail, id_categoria, mes, ano);
		return lista.stream()
				.map(transactionMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public TransactionDTO listarTransacao(String usuarioEmail, Long id_transacao) {
		Transaction transacao = this.transactionRepository.findById(id_transacao).orElseThrow(() -> new RuntimeException("Transaction not found"));
		if(transacao.getUsuario().getEmail() != usuarioEmail) {
			throw new RuntimeException("This transaction belongs to another user");
		}
		return this.transactionMapper.toDTO(transacao); 
	}
	
	public TransactionDTO atualizarTransacao(Long id_transacao, TransactionDTO novosDados) {
		Transaction transacao = this.transactionRepository.findById(id_transacao).orElseThrow(() -> new RuntimeException("Transaction not found"));
		if (transacao.getUsuario().getId() != novosDados.id_usuario()) {
			throw new RuntimeException("This transaction belongs to another user");
		}
		transacao.setDescricao(novosDados.descricao());
		transacao.setValor(novosDados.valor());
		transacao.setData(novosDados.data());
		transacao.setHora(novosDados.hora());
		transacao.setTipo(novosDados.tipo());
		transacao.setCategoria(this.categoryRepository.findById(novosDados.id_categoria()).get());
		transacao.setUsuario(this.userRepository.findById(novosDados.id_usuario()).get());
		
		this.salvarTransacao(transacao);
		
		return this.transactionMapper.toDTO(transacao);
	}
	
	public void deletarTransacao(Long id_transacao, String usuarioEmail) {
		Transaction transacao = this.transactionRepository.findById(id_transacao).orElseThrow(() -> new RuntimeException("Transaction not found"));
		if (transacao.getUsuario().getEmail() != usuarioEmail) {
			throw new RuntimeException("This transaction belongs to another user");
		}
		this.transactionRepository.delete(transacao);
	}
	
	public void salvarTransacao(Transaction transacao) {
		this.transactionRepository.save(transacao);
	}

}
