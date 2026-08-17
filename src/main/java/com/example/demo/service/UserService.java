package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserPatchDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

import jakarta.validation.constraints.Size;

@Service
@Validated
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// Constructor injection
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// CREATE
	@Transactional
	public UserResponseDTO createUser(UserCreateDTO request) {
		// throw error if email is existed
		if (userRepository.existsByEmail(request.email().toLowerCase())) {
			throw new EmailAlreadyExistsException("Email is already existed: " + request.email());

		}

		User user = new User();
		user.setName(request.name());
		user.setEmail(request.email());
		String hashed = passwordEncoder.encode(request.password());
		user.setPassword(hashed);
		user.setRole(request.role());
		user.setActive(request.isActive());
		userRepository.save(user);

		UserResponseDTO response = new UserResponseDTO(request.name(), request.email(), request.role(),
				request.isActive());

		return response;
	}

	// GET ALL
	public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
				.map(user -> new UserResponseDTO(user.getName(), user.getEmail(), user.getRole(), user.isActive()));
	}

	// GET BY ID
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id not found: " + id));
	}

	// UPDATE
	public User updateUser(Long id, User request) {
		User existing = getUserById(id);
		existing.setEmail(request.getEmail());
		existing.setName(request.getName());
		existing.setPassword(request.getPassword());

		return userRepository.save(existing);
	}

	// PATCH
	@Transactional
	public User patchUser(Long id, UserPatchDTO request) {
		User existing = getUserById(id);

		if (request.email() != null) {
			existing.setEmail(request.email());
		}

		if (request.name() != null) {
			existing.setName(request.name());
		}

		return userRepository.save(existing);
	}

	// Delete
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found");
		}
		userRepository.deleteById(id);
	}

	// GET COUNT
	public Long getCount() {
		return userRepository.count();
	}

	// FIND BY NAME
	public User findByName(
			@Size(min = 3, max = 20, message = "Name must be in range between 3 and 20 characters") String name) {
		return userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException("Invalid name: " + name));
	}

	// FIND BY KEYWORD
	public List<User> findByKeyword(String keyword) {
		return userRepository.findByNameContainingIgnoreCase(keyword);
	}
}
