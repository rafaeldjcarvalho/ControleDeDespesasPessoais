package com.rafael.ControleDeDespesasPessoais.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.LoginDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.RegisterDTO;
import com.rafael.ControleDeDespesasPessoais.domain.dtos.ResponseDTO;
import com.rafael.ControleDeDespesasPessoais.domain.entitys.User;
import com.rafael.ControleDeDespesasPessoais.domain.services.UserService;
import com.rafael.ControleDeDespesasPessoais.infra.security.TokenService;

@Controller
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody RegisterDTO body) {
		this.userService.criarNovoUsuario(body);
		return ResponseEntity.ok(body.nome());
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO body) {
		User user = this.userService.autenticarUsuario(body);
		if(user != null) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
		}
		return ResponseEntity.badRequest().build();
	}

}
