package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class InMemoryProductRepository implements ProductRepository {
	private ConcurrentHashMap<Long, Product> store = new ConcurrentHashMap<>();
	private AtomicLong id = new AtomicLong(1);

	@Override
	public List<Product> findAll() {
		return new ArrayList<Product>(store.values());
	}

	@Override
	public Optional<Product> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Product save(Product product) {
		boolean isExist = store.values().stream()
				.anyMatch(p -> p.getProductName().equalsIgnoreCase(product.getProductName()));

		if (isExist) {
			throw new IllegalArgumentException("Sản phẩm: " + product.getProductName() + " đã tồn tại trên hệ thống");
		}

		if (product.getId() == null) {
			product.setId(id.getAndIncrement());
		}
		store.put(product.getId(), product);

		return product;
	}

	@Override
	public Optional<Product> update(Long id, Product product) {
		if (!store.containsKey(id)) {
			return Optional.empty();
		}
		store.put(id, product);
		return Optional.of(product);
	}

	@Override
	public boolean deleteById(Long id) {
		return store.remove(id) != null;
	}

	@Override
	public long count() {
		return store.size();
	}

	@Override
	public List<Product> findByNameContaining(String keyword) {
		List<Product> found = store.values().stream().filter(p -> p.getProductName().toLowerCase().contains(keyword))
				.toList();

		return found;
	}

	@Override
	public List<Product> findByPriceRange(double min, double max) {
		List<Product> valid = store.values().stream().filter(p -> p.getPrice() >= min && p.getPrice() <= max).toList();

		return valid;
	}
}
