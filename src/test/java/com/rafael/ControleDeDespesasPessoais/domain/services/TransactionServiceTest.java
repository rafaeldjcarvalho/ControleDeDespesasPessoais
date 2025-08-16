package com.rafael.ControleDeDespesasPessoais.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers.TransactionMapper;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.CategoryRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.TransactionRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

public class TransactionServiceTest {
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private TransactionMapper transactionMapper;
	
	@Autowired
	@InjectMocks
	private TransactionService transactionService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Deve receber um TransactionDTO com sucesso do BD")
	void criarTransacaoCase1() {
		TransactionDTO data = new TransactionDTO(1l, "viajem", new BigDecimal(20), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 1l);
		
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category(1l, "Lazer", "viajens", user)));
		
		this.transactionService.criarTransacao(data);
		
		verify(userRepository, times(1)).findById(1l);
		verify(categoryRepository, times(1)).findById(1l);
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando o usuario nao existe")
	void criarTransacaoCase2() {
		TransactionDTO data = new TransactionDTO(1l, "viajem", new BigDecimal(20), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 1l);
		
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category(1l, "Lazer", "viajens", new User())));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.criarTransacao(data);
		});
		
		assertEquals("User not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a categoria nao existe")
	void criarTransacaoCase3() {
		TransactionDTO data = new TransactionDTO(1l, "viajem", new BigDecimal(20), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 1l);
		
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.criarTransacao(data);
		});
		
		assertEquals("Category not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve receber um TransactionDTO com sucesso do BD")
	void listarTransacaoCase1() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", new User());
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		
		this.transactionService.listarTransacao(1l, 1l);
		
		verify(transactionRepository, times(1)).findById(1l);
		verify(transactionMapper, times(1)).toDTO(any(Transaction.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao pertence ao usuario")
	void listarTransacaoCase2() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", new User());
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.listarTransacao(2l, 1l);
		});
		
		assertEquals("This transaction belongs to another user", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao existe")
	void listarTransacaoCase3() {
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.listarTransacao(2l, 1l);
		});
		
		assertEquals("Transaction not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve receber um TransactionDTO com sucesso do BD")
	void atualizarTransacaoCase1() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", user);
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 1l);
		
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(category));
		
		this.transactionService.atualizarTransacao(data);
		
		verify(transactionRepository, times(1)).findById(1l);
		verify(transactionRepository, times(1)).save(any(Transaction.class));
		verify(transactionMapper, times(1)).toDTO(any(Transaction.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao pertence ao usuario")
	void atualizarTransacaoCase2() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", new User());
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 2l);
		
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.atualizarTransacao(data);
		});
		
		assertEquals("This transaction belongs to another user", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao existe")
	void atualizarTransacaoCase3() {
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 2l);
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.atualizarTransacao(data);
		});
		
		assertEquals("Transaction not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve deletar uma transacao com sucesso no BD")
	void deletarTransacaoCase1() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", new User());
	
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 1l);
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		
		this.transactionService.deletarTransacao(data);
		
		verify(transactionRepository, times(1)).findById(1l);
		verify(transactionRepository, times(1)).delete(any(Transaction.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao existe")
	void deletarTransacaoCase2() {
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 2l);
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.deletarTransacao(data);
		});
		
		assertEquals("Transaction not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a transacao nao pertence ao usuario")
	void deletarTransacaoCase3() {
		User user = new User(1l, "teste", "teste@gmail.com", "senha123");
		Category category = new Category(1l, "Lazer", "viajens", new User());
		TransactionDTO data = new TransactionDTO(1l, "viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, 1l, 2l);
		
		when(transactionRepository.findById(1l)).thenReturn(Optional.of(new Transaction(1l, "viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category, user)));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.transactionService.deletarTransacao(data);
		});
		
		assertEquals("This transaction belongs to another user", thrown.getMessage());
	}
}
