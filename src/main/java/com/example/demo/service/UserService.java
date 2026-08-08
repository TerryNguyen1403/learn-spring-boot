package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	// Constructor injection
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// CREATE
	public User createUser(User request) {
		// throw error if email is existed
		if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
			throw new IllegalArgumentException("Email is already existed: " + request.getEmail());

		}

		request.setEmail(request.getEmail().toLowerCase());
		return userRepository.save(request);
	}

	// GET ALL
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// GET BY ID
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));
	}

	// UPDATE
	public User updateUser(Long id, User request) {
		User existing = getUserById(id);
		existing.setEmail(request.getEmail());
		existing.setName(request.getName());
		existing.setPhoneNumber(request.getPhoneNumber());

		return userRepository.save(existing);
	}

	// PATCH
	public User patchUser(Long id, User request) {
		User existing = getUserById(id);

		if (request.getEmail() != null) {
			existing.setEmail(request.getEmail());
		}

		if (request.getName() != null) {
			existing.setName(request.getName());
		}

		if (request.getPhoneNumber() != null) {
			existing.setPhoneNumber(request.getPhoneNumber());
		}

		return userRepository.save(existing);
	}

	// Delete
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new IllegalArgumentException("Id not found");
		}
		userRepository.deleteById(id);
	}

	// GET COUNT
	public Long getCount() {
		return userRepository.count();
	}
}
