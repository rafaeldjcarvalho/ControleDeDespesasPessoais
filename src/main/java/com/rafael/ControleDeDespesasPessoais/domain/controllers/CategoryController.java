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

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.services.CategoryService;

@Controller
@RequestMapping("/api/categorias")
public class CategoryController {
	
	@Autowired
	private CategoryService categoriaService;
	
	@GetMapping("/{id_usuario}")
	public ResponseEntity<List<CategoryDTO>> listarCategorias(@PathVariable Long id_usuario) {
		List<CategoryDTO> lista = categoriaService.listarCategoriaDoUsuario(id_usuario);
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	public ResponseEntity<?> criarCategoria(@RequestBody CategoryDTO dados) {
		this.categoriaService.criarCategoria(dados);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoryDTO dados) {
		CategoryDTO categoria = this.categoriaService.atualizarCategoria(id, dados);
		return ResponseEntity.ok(categoria);
	}
	
	@DeleteMapping("/{id_categoria}/{id_usuario}")
	public ResponseEntity<?> deletarCategoria(@PathVariable Long id_categoria, @PathVariable Long id_usuario) {
		this.categoriaService.deletarCategoria(id_categoria, id_usuario);
		return ResponseEntity.noContent().build();
	}

}
