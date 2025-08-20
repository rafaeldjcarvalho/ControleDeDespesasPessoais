package com.rafael.ControleDeDespesasPessoais.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers.CategoryMapper;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.CategoryRepository;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	public void criarCategoria(CategoryDTO categoria) {
		User usuario = this.userRepository.findById(categoria.id_usuario()).orElseThrow(() -> new RuntimeException("User not found"));
		
		Category novaCategoria = new Category();
		novaCategoria.setNome(categoria.nome());
		novaCategoria.setDescricao(categoria.descricao());
		novaCategoria.setUsuario(usuario);
		
		this.salvarCategoria(novaCategoria);
	}
	
	public List<CategoryDTO> listarCategoriaDoUsuario(String usuarioEmail) {
		return this.categoryRepository.findCategoriesByUser(usuarioEmail)
				.stream()
				.map(categoryMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public CategoryDTO atualizarCategoria(Long id_categoria, String usuarioEmail, CategoryDTO dados) {
		Category categoria = verificarCategoria(id_categoria, usuarioEmail);
		
		categoria.setNome(dados.nome());
		categoria.setDescricao(dados.descricao());
		this.salvarCategoria(categoria);
		
		return new CategoryDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao(), categoria.getUsuario().getId());
	}
	
	public void salvarCategoria(Category categoria) {
		this.categoryRepository.save(categoria);
	}
	
	public void deletarCategoria(Long id_categoria, String usuarioEmail) {
		Category categoria = verificarCategoria(id_categoria, usuarioEmail);
		this.categoryRepository.delete(categoria);
	}
	
	private Category verificarCategoria(Long id_categoria, String usuarioEmail) {
		Category categoria = this.categoryRepository.findById(id_categoria).orElseThrow(() -> new RuntimeException("Category not found"));
		
		if(usuarioEmail != categoria.getUsuario().getEmail()) {
			throw new RuntimeException("This category belongs to another user");
		}
		
		return categoria;
	}
}
