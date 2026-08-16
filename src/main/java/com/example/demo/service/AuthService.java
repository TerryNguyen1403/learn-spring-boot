package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegistryRequestDTO;
import com.example.demo.dto.RegistryResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateUserException;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public RegistryResponseDTO userRegistry(RegistryRequestDTO request) {
		boolean existing = userRepository.existsByEmail(request.email());

		// Throw exception if email is existing
		if (existing)
			throw new DuplicateUserException("Email is already existed");

		User user = new User();
		user.setName(request.name());
		user.setEmail(request.email());
		String hashed = passwordEncoder.encode(request.password());
		user.setPassword(hashed);
		user.setRole("ROLE_USER");
		user.setActive(true);

		User saved = userRepository.save(user);

		return new RegistryResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(),
				saved.isActive());
	}
}
