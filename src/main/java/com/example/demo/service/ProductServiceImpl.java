package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {
	private final List<Product> products = new ArrayList<>();
	private Long id = 1L;

	public ProductServiceImpl() {
		products.add(new Product(id++, "Laptop Dell XPS 15", 25000000.0, 10));
		products.add(new Product(id++, "iPhone 16 Pro Max", 35000000.0, 5));
		products.add(new Product(id++, "Tai nghe Sony WH-1000XM5", 7000000.0, 20));
	}

	@Override
	public List<Product> getAllProducts() {
		return products;
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		Optional<Product> found = products.stream().filter(p -> p.getId().equals(id)).findFirst();

		return found;
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		Optional<Product> found = products.stream()
				.filter(p -> p.getProductName().toLowerCase().contains(name.toLowerCase())).findFirst();

		return found;
	}

	@Override
	public Product addProduct(Product product) {
		product.setId(id++);
		products.add(product);

		return product;
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Product existing = products.stream().filter(p -> p.getId() == id).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Id not found"));

		if (product.getPrice() < 0) {
			throw new IllegalArgumentException("Negative price value");
		}

		existing.setPrice(product.getPrice());
		existing.setProductName(product.getProductName());
		existing.setQuantity(product.getQuantity());

		return existing;
	}
}
