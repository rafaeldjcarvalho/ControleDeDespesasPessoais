package com.rafael.ControleDeDespesasPessoais.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.LoginDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void criarNovoUsuario(RegisterDTO dados) {
		Optional<User> user = this.userRepository.findByEmail(dados.email());
		
		if(user.isEmpty()) {
			User newUser = new User(dados);
			newUser.setSenha(passwordEncoder.encode(dados.senha()));
			this.salvarUsuario(newUser);
		} else {
			throw new RuntimeException("User already exists");
		}
	}
	
	public User autenticarUsuario(LoginDTO dados) {
		User usuario = this.userRepository.findByEmail(dados.email()).orElseThrow(() -> new RuntimeException("User not found"));
		if(passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
			return usuario;
		}
		return null;
	}
	
	public void salvarUsuario(User usuario) {
		this.userRepository.save(usuario);
	}
}
