package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Order;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.enums.OrderStatus;
import com.proje.salad_App.exeption.OrderNotFoundException;
import com.proje.salad_App.exeption.SaladNotFoundException;
import com.proje.salad_App.exeption.UserNotFoundException;
import com.proje.salad_App.payload.request.OrderRequest;
import com.proje.salad_App.payload.response.OrderResponse;
import com.proje.salad_App.repository.OrderRepository;
import com.proje.salad_App.repository.SaladRepository;
import com.proje.salad_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SaladRepository saladRepository;
    public OrderResponse createOrder(OrderRequest request) {
        Order order = buildOrderFromRequest(request);
        Order savedOrder = orderRepository.save(order);
        return mapOrderEntityToResponse(savedOrder);
    }

    public OrderResponse mapOrderEntityToResponse(Order savedOrder) {
        OrderResponse response = new OrderResponse();

        response.setUserId(savedOrder.getUser().getId());

        Set<Long> saladIds = savedOrder.getSalads().stream()
                .map(Salad::getId)
                .collect(Collectors.toSet());
        response.setSaladIds(saladIds);

        response.setOrderTime(savedOrder.getOrderTime());

        // Set status as string representation of OrderStatus
        response.setStatus(savedOrder.getStatus().toString());

        return response;
    }

    private Order buildOrderFromRequest(OrderRequest request) {
        Order order = new Order();
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));
        order.setUser(user);

        Set<Salad> salads = new HashSet<>();
        for (Long saladId : request.getSaladIds()) {
            Salad salad = saladRepository.findById(saladId)
                    .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + saladId));
            salads.add(salad);
        }
        order.setSalads(salads);

        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return order;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, OrderRequest request) {
        // Get the existing Order from the repository
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Update the Order entity with data from the request
        // ...

        // Save and return the updated Order
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        // Get the Order from the repository
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Delete the Order
        orderRepository.delete(order);
    }
}
