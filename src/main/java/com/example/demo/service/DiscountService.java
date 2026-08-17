package com.example.demo.service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;

@Service
public class DiscountService {
	private ConcurrentHashMap<Long, Product> discountItems = new ConcurrentHashMap<>();
	private AtomicLong id = new AtomicLong(1);

	public Collection<Product> applyDiscount(Product product) {
		product.setId(id.getAndIncrement());
		double salePrice = product.getPrice() - (product.getPrice() * 20 / 100);
		product.setPrice(salePrice);
		discountItems.put(product.getId(), product);

		return discountItems.values();
	}
}
