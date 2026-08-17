package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserPatchDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
	private final UserService userService;

	// Constructor injection
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// CREATE
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
	}

	// GET ALL
	@GetMapping
	public Page<UserResponseDTO> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
		return userService.getAllUsers(pageable);
	}

	// GET BY ID
	@GetMapping("/{id}")
	public User getById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	// UPDATE
	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody User request) {
		return userService.updateUser(id, request);
	}

	// PATCH
	@PatchMapping("/{id}")
	public User patch(@PathVariable Long id, @Valid @RequestBody UserPatchDTO request) {
		return userService.patchUser(id, request);
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

	// GET COUNT
	@GetMapping("/count")
	public Long getCount() {
		return userService.getCount();
	}

	// FIND BY NAME
	@GetMapping("/name")
	public ResponseEntity<User> findByName(@RequestParam @NotBlank String name) {
		return ResponseEntity.ok(userService.findByName(name));
	}

	// FIND BY KEYWORD
	@GetMapping("/keyword")
	public ResponseEntity<List<User>> findByKeyword(@RequestParam String keyword) {
		return ResponseEntity.ok(userService.findByKeyword(keyword));
	}
}
