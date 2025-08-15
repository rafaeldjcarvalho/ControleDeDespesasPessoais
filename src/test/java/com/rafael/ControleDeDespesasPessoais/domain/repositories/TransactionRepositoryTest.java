package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.ExpenseByCategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.TransactionDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Transaction;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private User createUser(RegisterDTO data) {
		User newUser = new User(data);
		newUser.setSenha(data.senha());
		this.entityManager.persist(newUser);
		return newUser;
	}
	
	private Category createCategory(CategoryDTO data) {
		Category newCategory = new Category();
		newCategory.setNome(data.nome());
		newCategory.setDescricao(data.descricao());
		newCategory.setUsuario(userRepository.findById(data.id_usuario()).get());
		this.entityManager.persist(newCategory);
		return newCategory;
	}
	
	private Transaction createTransaction(TransactionDTO data) {
		Transaction newTransaction = new Transaction();
		newTransaction.setCategoria(this.categoryRepository.findById(data.id_categoria()).get());
		newTransaction.setData(data.data());
		newTransaction.setDescricao(data.descricao());
		newTransaction.setHora(data.hora());
		newTransaction.setTipo(data.tipo());
		newTransaction.setUsuario(this.userRepository.findById(data.id_usuario()).get());
		newTransaction.setValor(data.valor());
		this.entityManager.persist(newTransaction);
		return newTransaction;
	}
	
	@Test
	@DisplayName("Deve receber uma lista de transacao com sucesso do BD")
	void findTransactionByUserCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		Category category2 = this.createCategory(new CategoryDTO(null, "Alimentacao", "ifood", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Hamburguer", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category2.getId(), user.getId()));
		
		List<Transaction> result = this.transactionRepository.findTransactionByUser(user.getId());
		
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de transacao do BD, quando o usuario nao existe")
	void findTransactionByUserCase2() {
		List<Transaction> result = this.transactionRepository.findTransactionByUser(4l);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Deve receber uma lista de transacao com sucesso do BD, apenas com id_usuario")
	void findTransactionByUserFilteredCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		Category category2 = this.createCategory(new CategoryDTO(null, "Alimentacao", "ifood", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Hamburguer", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category2.getId(), user.getId()));
		
		List<Transaction> result = this.transactionRepository.findTransactionByUserFiltered(user.getId(), null, null, null);
		
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de transacao do BD, quando o usuario nao existe mesmo sem filtros")
	void findTransactionByUserFilteredCase2() {
		List<Transaction> result = this.transactionRepository.findTransactionByUserFiltered(10l, null, null, null);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Deve receber uma lista de transacao com sucesso do BD, com todos os filtros")
	void findTransactionByUserFilteredCase3() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 1", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		
		List<Transaction> result = this.transactionRepository.findTransactionByUserFiltered(user.getId(), category.getId(), 8l, 2025l);
		
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de transacao do BD, quando uma categoria nao existe")
	void findTransactionByUserFilteredCase4() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		
		List<Transaction> result = this.transactionRepository.findTransactionByUserFiltered(user.getId(), 4l, 8l, 2025l);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Deve receber um valor total pelo tipo com sucesso do BD")
	void findTotalValueForTypeCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 1", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		
		BigDecimal result = this.transactionRepository.findTotalValueForType(user.getId(), TransactionType.DESPESA, 8l, 2025l);
		
		assertThat(result).isEqualByComparingTo(new BigDecimal(300));
	}
	
	@Test
	@DisplayName("Nao deve receber um valor total pelo tipo do BD, quando o usuario nao existe")
	void findTotalValueForTypeCase2() {
		BigDecimal result = this.transactionRepository.findTotalValueForType(3l, TransactionType.DESPESA, 8l, 2025l);
		
		assertThat(result).isNull();
	}
	
	@Test
	@DisplayName("Nao deve receber um valor total pelo tipo do BD, quando o tipo nao existe")
	void findTotalValueForTypeCase3() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 1", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 2", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		
		BigDecimal result = this.transactionRepository.findTotalValueForType(user.getId(), TransactionType.RECEITA, 8l, 2025l);
		
		assertThat(result).isNull();
	}
	
	@Test
	@DisplayName("Deve receber uma lista de despesa por categoria com sucesso do BD")
	void findDespesasPorCategoriaCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		Category category = this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		Category category2 = this.createCategory(new CategoryDTO(null, "Alimentacao", "ifood", user.getId()));
		this.createTransaction(new TransactionDTO(null, "Viajem 1", new BigDecimal(200), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category.getId(), user.getId()));
		this.createTransaction(new TransactionDTO(null, "Comida", new BigDecimal(100), LocalDate.now(), LocalTime.now(), TransactionType.DESPESA, category2.getId(), user.getId()));
		
		List<ExpenseByCategoryDTO> result = this.transactionRepository.findDespesasPorCategoria(TransactionType.DESPESA, user.getId(), 8l, 2025l);
		
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de despesa por categoria do BD, quando a categoria nao existe")
	void findDespesasPorCategoriaCase2() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		
		List<ExpenseByCategoryDTO> result = this.transactionRepository.findDespesasPorCategoria(TransactionType.DESPESA, user.getId(), 8l, 2025l);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de despesa por categoria do BD, quando o usuario nao existe")
	void findDespesasPorCategoriaCase3() {
		List<ExpenseByCategoryDTO> result = this.transactionRepository.findDespesasPorCategoria(TransactionType.DESPESA, 70l, 8l, 2025l);
		
		assertThat(result.isEmpty()).isTrue();
	}
}
