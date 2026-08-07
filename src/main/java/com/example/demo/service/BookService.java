package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

public class BookService {
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book addBook(Book request) {
		return bookRepository.save(request);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
}
