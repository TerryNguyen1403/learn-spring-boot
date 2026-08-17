package com.example.demo.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User existing = userRepository.findByName(username)
				.orElseThrow(() -> new UserNotFoundException("Username not found: " + username));

		return org.springframework.security.core.userdetails.User.builder().username(existing.getName())
				.password(existing.getPassword()).authorities(new SimpleGrantedAuthority("ROLE_" + existing.getRole()))
				.disabled(!existing.isActive()).build();
	}
}
