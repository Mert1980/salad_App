package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Order;

import com.proje.salad_App.payload.request.OrderRequest;
import com.proje.salad_App.payload.response.OrderResponse;
import com.proje.salad_App.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;



    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // Create a new Order entity
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        // Get the Order from the repository
        Order order = orderService.getOrderById(id);

        // Map the Order entity to OrderResponse
        OrderResponse response = orderService.mapOrderEntityToResponse(order);

        // Return the OrderResponse with OK status
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = orders.stream()
                .map(orderService::mapOrderEntityToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        Order updatedOrder = orderService.updateOrder(id, request);
        OrderResponse response = orderService.mapOrderEntityToResponse(updatedOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        // Delete the Order entity
        orderService.deleteOrder(id);

        // Return ResponseEntity.ok() if deleted successfully
        return ResponseEntity.ok().build();
    }
}
