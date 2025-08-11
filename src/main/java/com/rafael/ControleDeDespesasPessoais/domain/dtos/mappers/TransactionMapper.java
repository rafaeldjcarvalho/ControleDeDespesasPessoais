package com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.CategoryRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

@Component
public class TransactionMapper {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public TransactionDTO toDTO(Transaction transacao) {
	    if (transacao == null) {
	        return null;
	    }
	    return new TransactionDTO(
	    	transacao.getId(),
	        transacao.getDescricao(),
	        transacao.getValor(),
	        transacao.getData(),
	        transacao.getHora(),
	        transacao.getTipo(),
	        transacao.getCategoria().getId(),
	        transacao.getUsuario().getId()
	    );
	}
	
	public Transaction toEntity(TransactionDTO transacaoDTO) {
	    if (transacaoDTO == null) {
	        return null;
	    }

	    Transaction transacao = new Transaction();
	    if (transacaoDTO.id() != null) {
	        transacao.setId(transacaoDTO.id());
	    }
	    transacao.setDescricao(transacaoDTO.descricao());
	    transacao.setValor(transacaoDTO.valor());
	    transacao.setData(transacaoDTO.data());
	    transacao.setHora(transacaoDTO.hora());
	    transacao.setTipo(transacaoDTO.tipo());
	    transacao.setCategoria(this.categoryRepository.findById(transacaoDTO.id_categoria()).get());
	    transacao.setUsuario(this.userRepository.findById(transacaoDTO.id_usuario()).get());
	    
	    return transacao;
	}
}
