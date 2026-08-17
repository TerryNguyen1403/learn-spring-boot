package com.example.demo.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.service.DiscountService;
import com.example.demo.service.ProductServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductServiceImpl productServiceImpl;
	private final DiscountService discountService;

	public ProductController(ProductServiceImpl productServiceImpl, DiscountService discountService) {
		this.productServiceImpl = productServiceImpl;
		this.discountService = discountService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productServiceImpl.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productServiceImpl.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
		if (product.getProductName().isEmpty() || product.getPrice() <= 0) {
			throw new IllegalArgumentException();
		}

		Product created = productServiceImpl.add(product);

		URI uri = URI.create("/products/" + created.getId());

		return ResponseEntity.created(uri).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Optional<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
		return ResponseEntity.ok().body(productServiceImpl.update(id, product));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		log.info("DELETE HTTP /products/{}", id);
		if (id <= 0) {
			return ResponseEntity.badRequest().body("message: id=" + id + " không hợp lệ");
		}

		boolean result = productServiceImpl.delete(id);

		if (result) {
			return ResponseEntity.ok().body("message: Đã xóa sản phẩm: " + id);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/count")
	public long getCount() {
		return productServiceImpl.getCount();
	}

	@GetMapping("/name/{keyword}")
	public ResponseEntity<List<Product>> findByName(@PathVariable String keyword) {
		List<Product> found = productServiceImpl.findByName(keyword);
		log.debug("Sản phẩm: {}", found);

		if (found.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(found);
	}

	@GetMapping("/get-price-range")
	public ResponseEntity<List<Product>> findByPriceRange() {
		double min = 100000.0;
		double max = 300000.0;

		List<Product> valid = productServiceImpl.findByPriceRange(min, max);

		if (valid.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(valid);
	}

	@GetMapping("/on-sale")
	public Collection<Product> getSale(@RequestBody Product request) {
		return discountService.applyDiscount(request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
