package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Product;

public interface ProductRepository {
	List<Product> findAll();

	Optional<Product> findById(Long id);

	Product save(Product product);

	Optional<Product> update(Long id, Product product);

	boolean deleteById(Long id);

	long count();

	List<Product> findByNameContaining(String keyword);

	List<Product> findByPriceRange(double min, double max);
}
