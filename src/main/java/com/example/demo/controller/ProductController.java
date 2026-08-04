package com.example.demo.controller;

import java.net.URI;
import java.util.List;

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
import com.example.demo.service.ProductServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductServiceImpl productServiceImpl;

	public ProductController(ProductServiceImpl productServiceImpl) {
		this.productServiceImpl = productServiceImpl;
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productServiceImpl.getAllProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productServiceImpl.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Product> getProductByName(@PathVariable String name) {
		return productServiceImpl.getProductByName(name).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		if (product.getProductName().isEmpty() || product.getPrice() <= 0) {
			throw new IllegalArgumentException();
		}

		Product created = productServiceImpl.addProduct(product);

		URI uri = URI.create("/products/" + created.getId());

		return ResponseEntity.created(uri).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
		return ResponseEntity.ok().body(productServiceImpl.updateProduct(id, product));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		log.info("DELETE HTTP /products/{}", id);
		if (id <= 0) {
			return ResponseEntity.badRequest().body("message: id=" + id + " không hợp lệ");
		}

		boolean result = productServiceImpl.deleteProduct(id);

		if (result) {
			return ResponseEntity.ok().body("message: Đã xóa sản phẩm: " + id);
		}

		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
