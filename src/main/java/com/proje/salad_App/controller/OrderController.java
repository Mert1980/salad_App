package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Order;

import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.payload.request.OrderRequest;
import com.proje.salad_App.payload.response.OrderResponse;
import com.proje.salad_App.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long loggedInUserId = Long.parseLong(userDetails.getUsername()); // Assuming username is the user's ID

        OrderResponse response = orderService.createOrder(request);

        if (!loggedInUserId.equals(response.getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to access this order.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long loggedInUserId = user.getId();

            // Order bilgilerini al ve işle
            Order order = orderService.getOrderById(id);

            // Kullanıcının sadece kendi siparişine bakmasını sağla
            if (!loggedInUserId.equals(order.getUser().getId())) {
                throw new UnauthorizedAccessException("You are not authorized to access this order.");
            }

            // OrderResponse oluştur ve dön
            OrderResponse response = orderService.mapOrderEntityToResponse(order);
            return ResponseEntity.ok(response);
        } else {
            throw new UnauthorizedAccessException("You are not authorized to access this order.");
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = orders.stream()
                .map(orderService::mapOrderEntityToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long loggedInUserId = user.getId();

            // Get the existing Order from the repository
            Order existingOrder = orderService.getOrderById(id);

            // Kullanıcının sadece kendi siparişini güncellemesini sağla
            if (!loggedInUserId.equals(existingOrder.getUser().getId())) {
                throw new UnauthorizedAccessException("You are not authorized to update this order.");
            }

            // Update the Order entity with data from the request
            Order updatedOrder = orderService.updateOrder(id, request);

            // OrderResponse oluştur ve dön
            OrderResponse response = orderService.mapOrderEntityToResponse(updatedOrder);
            return ResponseEntity.ok(response);
        } else {
            throw new UnauthorizedAccessException("You are not authorized to update this order.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long loggedInUserId = user.getId();

            // Get the Order from the repository
            Order order = orderService.getOrderById(id);

            // Kullanıcının sadece kendi siparişini silebilmesini sağla
            if (!loggedInUserId.equals(order.getUser().getId())) {
                throw new UnauthorizedAccessException("You are not authorized to delete this order.");
            }

            // Delete the Order
            orderService.deleteOrder(id);

            // Return ResponseEntity.ok() if deleted successfully
            return ResponseEntity.ok().build();
        } else {
            throw new UnauthorizedAccessException("You are not authorized to delete this order.");
        }
    }
}
