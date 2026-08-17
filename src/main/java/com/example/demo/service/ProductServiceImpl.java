package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product add(Product product) {
		product.setId(null);
		return productRepository.save(product);
	}

	@Override
	public Optional<Product> update(Long id, Product product) {
		return productRepository.update(id, product);
	}

	@Override
	public boolean delete(Long id) {
		return productRepository.deleteById(id);
	}

	@Override
	public long getCount() {
		return productRepository.count();
	}

	@Override
	public List<Product> findByName(String keyword) {
		return productRepository.findByNameContaining(keyword);
	}

	@Override
	public List<Product> findByPriceRange(double min, double max) {
		List<Product> valid = productRepository.findByPriceRange(min, max);

		return valid;
	}
}
