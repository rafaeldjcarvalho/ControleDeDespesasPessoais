package com.rafael.ControleDeDespesasPessoais.domain.dtos.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.CategoryDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.Category;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

@Component
public class CategoryMapper {
	
	@Autowired
	private UserRepository userRepository;
	
	public CategoryDTO toDTO(Category categoria) {
	    if (categoria == null) {
	        return null;
	    }
	    return new CategoryDTO(
	        categoria.getId(),
	        categoria.getNome(),
	        categoria.getDescricao(),
	        categoria.getUsuario().getId()
	    );
	}
	
	public Category toEntity(CategoryDTO categoriaDTO) {
	    if (categoriaDTO == null) {
	        return null;
	    }

	    Category categoria = new Category();
	    if (categoriaDTO.id() != null) {
	        categoria.setId(categoriaDTO.id());
	    }
	    categoria.setNome(categoriaDTO.nome());
	    categoria.setDescricao(categoriaDTO.descricao());
	    categoria.setUsuario(this.userRepository.findById(categoriaDTO.id_usuario()).get());
	    
	    return categoria;
	}
}
