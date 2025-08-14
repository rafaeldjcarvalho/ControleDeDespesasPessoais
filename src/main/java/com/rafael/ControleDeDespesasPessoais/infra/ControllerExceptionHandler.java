package com.rafael.ControleDeDespesasPessoais.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rafael.ControleDeDespesasPessoais.domain.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> threatDuplicateEntity(DataIntegrityViolationException exception) {
		ExceptionDTO exceptionDTO = new ExceptionDTO("User already registered", "400");
		return ResponseEntity.badRequest().body(exceptionDTO);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> threat404(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> threatGeneralException(RuntimeException exception){
		ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

}
