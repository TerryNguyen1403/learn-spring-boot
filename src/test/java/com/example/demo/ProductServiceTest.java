package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	private Product laptop;

	private ConcurrentHashMap<Long, Product> updateResult = new ConcurrentHashMap<>();

	@BeforeEach
	void setUp() {
		laptop = new Product(1L, "Laptop", 4000.0, 5, "LAP-0001");
	}

	@Test
	void getAllProducts_returnListOfProducts() {
		// Arrange
		Product mouse = new Product(2L, "Mouse", 2500.0, 3, "MOU-0002");
		when(productRepository.findAll()).thenReturn(List.of(laptop, mouse));

		// Act
		List<Product> found = productServiceImpl.findAll();

		// Assert
		assertEquals(2, found.size());
		assertEquals("Laptop", found.get(0).getProductName());
		verify(productRepository, times(1)).findAll();
	}

	@Test
	void getAllProducts_whenEmpty_shouldReturnEmptyList() {
		// Arrange
		when(productRepository.findAll()).thenReturn(List.of());

		// Act
		List<Product> found = productServiceImpl.findAll();

		// Assert
		assertEquals(true, found.isEmpty());
		verify(productRepository, times(1)).findAll();
	}

	@Test
	void updateProduct() {
		// Arrange
		Long id = 1L;
		Product updateData = new Product(1L, "PC", 5000.0, 6, "PCA-4567");
		when(productRepository.update(id, updateData)).thenReturn(Optional.of(updateData));

		// Act
		Optional<Product> result = productServiceImpl.update(id, updateData);

		// Assert
		assertTrue(result.isPresent());
		assertEquals("PC", result.get().getProductName());
		assertEquals(5000.0, result.get().getPrice());
		assertEquals(6, result.get().getQuantity());
		assertEquals("PCA-4567", result.get().getSku());

		verify(productRepository, times(1)).update(id, updateData);
	}
}
