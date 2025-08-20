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

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.services.CategoryService;
import com.rafael.ControleDeDespesasPessoais.infra.security.TokenService;

@Controller
@RequestMapping("/api/categorias")
public class CategoryController {
	
	@Autowired
	private CategoryService categoriaService;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> listarCategorias(@RequestHeader("Authorization") String token) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		List<CategoryDTO> lista = categoriaService.listarCategoriaDoUsuario(userEmail);
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	public ResponseEntity<?> criarCategoria(@RequestBody CategoryDTO dados) {
		this.categoriaService.criarCategoria(dados);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> atualizarCategoria(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody CategoryDTO dados) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		CategoryDTO categoria = this.categoriaService.atualizarCategoria(id, userEmail, dados);
		return ResponseEntity.ok(categoria);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCategoria(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		String[] tokenFormated = token.split(" ");
		String userEmail = tokenService.validateToken(tokenFormated[1]);
		if(userEmail == null) {
			throw new RuntimeException("Token is invalid");
		}
		this.categoriaService.deletarCategoria(id, userEmail);
		return ResponseEntity.noContent().build();
	}

}
