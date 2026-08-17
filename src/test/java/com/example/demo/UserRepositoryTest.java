package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

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
		User user = new User(1L, "Nguyen Van A", "nguyenvana@gmail.com", "testpassword", "USER");

		// Act
		User saved = userRepository.save(user);

		// Assert
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	void findByName_whenValid_shouldReturnUser() {
		// Arrange
		User user = new User(1L, "Nguyen Van A", "nguyenvana@gmail.com", "0988677456", "ADMIN");
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
		User user1 = new User(1L, "Nguyen Van A", "nguyenvana@gmail.com", "0988677456", "USER");
		User user2 = new User(2L, "Nguyen Van B", "nguyenvanb@gmail.com", "0988677456", "USER");
		User user3 = new User(3L, "Tran Van C", "tranvanc@gmail.com", "0988677456", "ADMIN");
		testEntityManager.persistAndFlush(user1);
		testEntityManager.persistAndFlush(user2);
		testEntityManager.persistAndFlush(user3);

		// Act
		List<User> found = userRepository.findByNameContainingIgnoreCase("Nguyen");

		// Assert: should return 2
		assertThat(found.size()).isEqualTo(2);
	}

	@Test
	void whenEmailIsDuplicate_shouldThrowException() {
		// Arrange
		User user1 = new User(1L, "Nguyen Van A", "nguyenvana@gmail.com", "0988677456", "ADMIN");
		userRepository.saveAndFlush(user1);

		User user2 = new User(1L, "Nguyen Van B", "nguyenvana@gmail.com", "0988677456", "ADMIN");

		// Act

		// Assert
		assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user2));

	}
}
