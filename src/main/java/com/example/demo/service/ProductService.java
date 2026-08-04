package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();

	Optional<Product> getProductById(Long id);

	Optional<Product> getProductByName(String name);

	Product addProduct(Product product);

	Product updateProduct(Long id, Product product);

	boolean deleteProduct(Long id);
}
