package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegistryRequestDTO;
import com.example.demo.dto.RegistryResponseDTO;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/registry")
	public ResponseEntity<RegistryResponseDTO> register(@Valid @RequestBody RegistryRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.userRegistry(request));
	}
}
