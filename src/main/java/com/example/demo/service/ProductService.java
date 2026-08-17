package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Product;

public interface ProductService {
	List<Product> findAll();

	Optional<Product> findById(Long id);

	Product add(Product product);

	Optional<Product> update(Long id, Product product);

	boolean delete(Long id);

	long getCount();

	List<Product> findByName(String keyword);

	List<Product> findByPriceRange(double min, double max);
}
