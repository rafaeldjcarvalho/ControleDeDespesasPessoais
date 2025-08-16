package com.rafael.ControleDeDespesasPessoais.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.LoginDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@InjectMocks
	private UserService userService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Deve criar um usuario com sucesso no BD")
	void criarNovoUsuarioCase1() {
		RegisterDTO data = new RegisterDTO("Rafael", "rafa123@gmail.com", "senha123");
		
		when(userRepository.findByEmail(data.email())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(data.senha())).thenReturn("SenhaCriptografada");
		
		this.userService.criarNovoUsuario(data);
		
		verify(userRepository, times(1)).findByEmail(data.email());
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando tentar criar um usuario que ja existe")
	void criarNovoUsuarioCase2() {
		RegisterDTO data = new RegisterDTO("Rafael", "rafa123@gmail.com", "senha123");
		
		when(userRepository.findByEmail(data.email())).thenReturn(Optional.of(new User()));
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.userService.criarNovoUsuario(data);
		});
		
		assertEquals("User already exists", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve retornar um usuario com sucesso, com email e senha validos")
	void autenticarUsuarioCase1() {
		LoginDTO data = new LoginDTO("teste@gmail.com", "teste123");
		
		when(userRepository.findByEmail(data.email())).thenReturn(Optional.of(new User()));
		when(passwordEncoder.matches(any(), any())).thenReturn(true);
		
		User user = this.userService.autenticarUsuario(data);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	@DisplayName("Deve lançar um RuntimeException, quando o usuario nao e encontrado")
	void autenticarUsuarioCase2() {
		LoginDTO data = new LoginDTO("teste@gmail.com", "teste123");
		
		Exception thrown = assertThrows(RuntimeException.class, () -> {
			this.userService.autenticarUsuario(data);
		});
		
		assertEquals("User not found", thrown.getMessage());
	}
	
	@Test
	@DisplayName("Deve retornar null, quando a senha esta incorreta")
	void autenticarUsuarioCase3() {
		LoginDTO data = new LoginDTO("teste@gmail.com", "teste123");
		
		when(userRepository.findByEmail(data.email())).thenReturn(Optional.of(new User()));
		when(passwordEncoder.matches(any(), any())).thenReturn(false);
		
		User user = this.userService.autenticarUsuario(data);
		
		assertThat(user).isNull();
	}
}
