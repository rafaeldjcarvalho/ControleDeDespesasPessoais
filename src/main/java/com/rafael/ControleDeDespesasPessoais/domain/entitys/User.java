package com.rafael.ControleDeDespesasPessoais.domain.entitys;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	@NotBlank
	@NotEmpty
	private String nome;
	
	@Column(nullable = false, length = 100, unique = true)
	@NotBlank
	@NotEmpty
	private String email;
	
	@Column(nullable = false)
	@Length(min = 8)
	@NotBlank
	@NotEmpty
	private String senha;
	
}
