package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	private Book mockBook;

	@BeforeEach
	void setUp() {
		mockBook = new Book(1, "title-1", "author-1");
	}

	@Test
	void addBook_whenSuccess_shouldReturnBook() {
		// Arrange
		when(bookRepository.save(mockBook)).thenReturn(mockBook);

		// Act
		Book saved = bookService.addBook(mockBook);

		// Assert
		assertEquals(mockBook, saved);
		verify(bookRepository, times(1)).save(mockBook);
	}
}
