package com.rafael.ControleDeDespesasPessoais.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	
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

	@Test
	@DisplayName("Deve receber o usuário com suscesso do DB")
	void findByEmailCase1() {
		String email = "rafa123@gmail.com";
		RegisterDTO data = new RegisterDTO("Rafael", email, "senha123");
		this.createUser(data);
		
		Optional<User> result = this.userRepository.findByEmail(email);
		
		assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("Nao deve receber o usuário do DB, quando o usuario nao existe")
	void findByEmailCase2() {
		String email = "rafa123@gmail.com";
		
		Optional<User> result = this.userRepository.findByEmail(email);
		
		assertThat(result.isEmpty()).isTrue();
	}
}
