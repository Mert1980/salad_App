package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Order;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.payload.request.OrderRequest;
import com.proje.salad_App.payload.response.OrderResponse;
import com.proje.salad_App.repository.UserRepository;
import com.proje.salad_App.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserRepository userRepository;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // Kullanıcının kendi siparişini oluşturmasına izin verin

        // OrderResponse'i oluşturmak için orderService'i çağırın
        OrderResponse response = orderService.createOrder(request);

        // Yanıtı döndürün
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof User) {
//            User user = (User) authentication.getPrincipal();
//            Long loggedInUserId = user.getId();

            // Order bilgilerini al ve işle
            Order order = orderService.getOrderById(id);

            // Kullanıcı admin ise veya siparişin sahibi ise erişime izin ver
                         OrderResponse response = orderService.mapOrderEntityToResponse(order);
                return ResponseEntity.ok(response);
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
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        // Kullanıcının kimliğini doğrulayın


        // Mevcut siparişi veritabanından alın
//        Order existingOrder = orderService.getOrderById(id);
//
//        // Kullanıcının sadece kendi siparişini güncellemesini sağlayın
//        if (!loggedInUsername.equals(existingOrder.getUser().getUsername())) {
//            throw new UnauthorizedAccessException("You are not authorized to update this order.");
//        }

        // Siparişi güncelleyin
        Order updatedOrder = orderService.updateOrder(id, request);

        // OrderResponse oluşturun ve döndürün
        OrderResponse response = orderService.mapOrderEntityToResponse(updatedOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication.getPrincipal() instanceof User) {
//            User user = (User) authentication.getPrincipal();
//            Long loggedInUserId = user.getId();

            // Get the Order from the repository
            Order order = orderService.getOrderById(id);

//            // Kullanıcının sadece kendi siparişini silebilmesini sağla
//            if (!loggedInUserId.equals(order.getUser().getId())) {
//                throw new UnauthorizedAccessException("You are not authorized to delete this order.");
//            }

            // Delete the Order
            orderService.deleteOrder(id);

            // Return ResponseEntity.ok() if deleted successfully
            return ResponseEntity.ok().build();
//        } else {
//            throw new UnauthorizedAccessException("You are not authorized to delete this order.");
//        }
    }
}
