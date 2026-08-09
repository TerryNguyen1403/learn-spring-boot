package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}

	public Order createOrder(Long id, Order request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User id not found: " + id));

		// If user exist
		request.setUser(user);
		return orderRepository.save(request);
	}

	public List<Order> getOrdersByUser(Long userId) {
		return orderRepository.findByUserId(userId);
	}

	public List<Order> getOrdersByUserIdAndStatus(Long userId, String status) {
		return orderRepository.findByUserIdAndStatus(userId, status);
	}
}
