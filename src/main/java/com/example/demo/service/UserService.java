package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ActivityLogRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final ActivityLogRepository activityLogRepository;

	// Constructor injection
	public UserService(UserRepository userRepository, ActivityLogRepository activityLogRepository) {
		this.userRepository = userRepository;
		this.activityLogRepository = activityLogRepository;
	}

	// CREATE
	@Transactional
	public User createUser(User request) {
		// throw error if email is existed
		if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
			throw new ResourceNotFoundException("Email is already existed: " + request.getEmail());

		}

		request.setEmail(request.getEmail().toLowerCase());
		User saved = userRepository.save(request);

		Log activityLog = new Log();
		activityLog.setAction("CREATE_USER");
		activityLog.setUser(request);

		if (request.getName().isBlank()) {
			throw new IllegalArgumentException("Name field cannot be blank");
		}

		activityLogRepository.save(activityLog);

		return saved;
	}

	// GET ALL
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()));
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
			throw new ResourceNotFoundException("Id not found");
		}
		userRepository.deleteById(id);
	}

	// GET COUNT
	public Long getCount() {
		return userRepository.count();
	}

	// FIND BY NAME
	public User findByName(String name) {
		return userRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid name: " + name));
	}

	// FIND BY KEYWORD
	public List<User> findByKeyword(String keyword) {
		return userRepository.findByNameContainingIgnoreCase(keyword);
	}
}
