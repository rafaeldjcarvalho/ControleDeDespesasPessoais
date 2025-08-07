package com.rafael.ControleDeDespesasPessoais.domain.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.rafael.ControleDeDespesasPessoais.domain.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	@NotBlank
	@NotEmpty
	private String descricao;
	
	@Column(nullable = false)
	private BigDecimal valor;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false)
	private LocalTime hora;
	
	@Enumerated(EnumType.STRING)
	private TransactionType tipo;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Category categoria;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User usuario;
}
