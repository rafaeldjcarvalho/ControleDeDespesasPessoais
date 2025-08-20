package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {
	
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
	
	@Test
	@DisplayName("Deve receber a categoria com sucesso do BD")
	void findByNomeCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		String name = "Transporte";
		this.createCategory(new CategoryDTO(null, name, "carro", user.getId()));
		
		Optional<Category> result = this.categoryRepository.findByNome(name);
		
		assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("Nao deve receber a categoria do BD, quando a categoria nao existe")
	void findByNomeCase2() {
		String name = "Transporte";
		
		Optional<Category> result = this.categoryRepository.findByNome(name);
		
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	@DisplayName("Deve receber uma lista de categoria com sucesso do BD")
	void findCategoriesByUserCase1() {
		User user = this.createUser(new RegisterDTO("teste", "test@gmail.com", "senha123"));
		this.createCategory(new CategoryDTO(null, "Transporte", "carro", user.getId()));
		this.createCategory(new CategoryDTO(null, "Alimentacao", "ifood", user.getId()));
		
		List<Category> lista = this.categoryRepository.findCategoriesByUser(user.getEmail());
		
		assertThat(lista.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("Nao deve receber uma lista de categoria do BD, quando o id do usuario nao existe")
	void findCategoriesByUserCase2() {
		List<Category> lista = this.categoryRepository.findCategoriesByUser("teste@gmail.com");
		
		assertThat(lista.isEmpty()).isTrue();
	}
}
