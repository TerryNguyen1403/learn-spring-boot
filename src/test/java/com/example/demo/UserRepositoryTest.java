package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void save_ShouldPersistUserAndGeneratedId() {
		// Arrange
		User user = new User("Nguyen Van A", "nguyenvana@gmail.com", "0988677456");

		// Act
		User saved = userRepository.save(user);

		// Assert
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	void findByName_whenValid_shouldReturnUser() {
		// Arrange
		User user = new User("Nguyen Van A", "nguyenvana@gmail.com", "0988677456");
		testEntityManager.persistAndFlush(user);

		// Act
		Optional<User> found = userRepository.findByName("Nguyen Van A");

		// Assert
		assertThat(found).isPresent();
		assertThat(found.get().getName()).isEqualTo("Nguyen Van A");
	}

	@Test
	void findByName_whenEmpty_shouldReturnNone() {
		// Arrange: No data preparation

		// Act
		Optional<User> found = userRepository.findByName("ten bat ky");

		// Assert
		assertThat(found).isEmpty();
	}

	@Test
	void findByNameContaining_whenTrue_shouldReturnListOfUsers() {
		// Arrange
		User user1 = new User("Nguyen Van A", "nguyenvana@gmail.com", "0988677456");
		User user2 = new User("Nguyen Van B", "nguyenvanb@gmail.com", "0988677456");
		User user3 = new User("Tran Van C", "tranvanc@gmail.com", "0988677456");
		testEntityManager.persistAndFlush(user1);
		testEntityManager.persistAndFlush(user2);
		testEntityManager.persistAndFlush(user3);

		// Act
		List<User> found = userRepository.findByNameContainingIgnoreCase("Nguyen");

		// Assert: should return 2
		assertThat(found.size()).isEqualTo(2);
	}
}
