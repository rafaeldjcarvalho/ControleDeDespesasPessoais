package com.rafael.ControleDeDespesasPessoais.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers.CategoryMapper;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.CategoryRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

public class CategoryServiceTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private CategoryMapper categoryMapper;
	
	@Autowired
	@InjectMocks
	private CategoryService categoryService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Deve criar uma categoria com sucesso no BD")
	void criarCategoriaCase1() {
		CategoryDTO data = new CategoryDTO(null, "Alimentacao", "ifood", 1l);
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
		
		this.categoryService.criarCategoria(data);
		
		verify(categoryRepository, times(1)).save(any(Category.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando o usuario nao existe")
	void criarCategoriaCase2() {
		CategoryDTO data = new CategoryDTO(null, "Alimentacao", "ifood", 1l);
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.categoryService.criarCategoria(data);
		});
		
		assertEquals("User not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve atualizar uma categoria com sucesso no BD")
	void atualizarCategoriaCase1() {
		CategoryDTO data = new CategoryDTO(null, "Alimentacao", "ifood", 1l);
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1l, data.nome(), data.descricao(), new User(1l, "teste", "teste@gmail.com", "senha123"))));
		
		CategoryDTO result = this.categoryService.atualizarCategoria(1L, "teste@gmail.com", data);
		verify(categoryRepository, times(1)).save(any(Category.class));
		assertThat(result.id()).isEqualTo(1l);
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeExceptio, quando a categoria nao existe")
	void atualizarCategoriaCase2() {
		CategoryDTO data = new CategoryDTO(1l, "Alimentacao", "ifood", 1l);
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.categoryService.atualizarCategoria(data.id(), "teste@gmail.com", data);
		});
		
		assertEquals("Category not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeExceptio, quando a categoria nao pertence ao usuario")
	void atualizarCategoriaCase3() {
		CategoryDTO data = new CategoryDTO(1l, "Alimentacao", "ifood", 1l);
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1l, data.nome(), data.descricao(), new User(2l, "teste", "teste@gmail.com", "senha123"))));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.categoryService.atualizarCategoria(data.id(), "teste3@gmail.com", data);
		});
		
		assertEquals("This category belongs to another user", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve deletar uma categoria com sucesso do BD")
	void deletarCategoriaCase1() {
		CategoryDTO data = new CategoryDTO(null, "Alimentacao", "ifood", 1l);
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1l, data.nome(), data.descricao(), new User(1l, "teste", "teste@gmail.com", "senha123"))));
		
		this.categoryService.deletarCategoria(1l, "teste@gmail.com");
		
		verify(categoryRepository, times(1)).findById(1l);
		verify(categoryRepository, times(1)).delete(any(Category.class));
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a categoria nao existe")
	void deletarCategoriaCase2() {
		CategoryDTO data = new CategoryDTO(1l, "Alimentacao", "ifood", 1l);
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.categoryService.deletarCategoria(data.id(), "teste@gmail.com");
		});
		
		assertEquals("Category not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando a categoria nao pertence ao usuario")
	void deletarCategoriaCase3() {
		CategoryDTO data = new CategoryDTO(1l, "Alimentacao", "ifood", 1l);
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1l, data.nome(), data.descricao(), new User(2l, "teste", "teste@gmail.com", "senha123"))));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.categoryService.deletarCategoria(data.id(), "teste3@gmail.com");
		});
		
		assertEquals("This category belongs to another user", thrown.getMessage());
	}
}
