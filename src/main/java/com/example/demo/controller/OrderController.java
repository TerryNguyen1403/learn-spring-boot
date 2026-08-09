package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/{userId}")
	public ResponseEntity<Order> create(@PathVariable Long userId, @RequestBody Order request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, request));
	}

	@GetMapping("/search")
	public ResponseEntity<List<Order>> getOrdersByUserId(@RequestParam Long userId) {
		return ResponseEntity.ok().body(orderService.getOrdersByUser(userId));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> getOrdersByUserIdAndStatus(@PathVariable Long userId,
			@RequestParam String status) {
		return ResponseEntity.ok().body(orderService.getOrdersByUserIdAndStatus(userId, status));
	}
}
