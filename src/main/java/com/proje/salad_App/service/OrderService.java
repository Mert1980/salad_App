package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Order;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.enums.OrderStatus;
import com.proje.salad_App.exeption.OrderNotFoundException;
import com.proje.salad_App.exeption.SaladNotFoundException;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.exeption.UserNotFoundException;
import com.proje.salad_App.payload.request.OrderRequest;
import com.proje.salad_App.payload.response.OrderResponse;
import com.proje.salad_App.repository.OrderRepository;
import com.proje.salad_App.repository.SaladRepository;
import com.proje.salad_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SaladRepository saladRepository;

    // private final SaladService saladService;
    public OrderResponse createOrder(OrderRequest request) {
        // İlk olarak siparişin fiyatını hesaplayın
        double totalPrice = calculateOrderTotalPrice(new ArrayList<>(request.getSaladIds()));

        Order order = buildOrderFromRequest(request);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        return mapOrderEntityToResponse(savedOrder);
    }

    public OrderResponse mapOrderEntityToResponse(Order savedOrder) {
        OrderResponse response = new OrderResponse();
        response.setId(savedOrder.getId());
        response.setUserId(savedOrder.getUser().getId());
        List<String > saladNames = savedOrder.getSalads().stream()
                .map(Salad::getName)
                .collect(Collectors.toList());
        response.setSaladNames(saladNames);

        response.setOrderTime(savedOrder.getOrderTime());
        response.setStatus(savedOrder.getStatus().toString());

        // Siparişin fiyatını da kullanıcıya döndürün
        response.setTotalPrice(savedOrder.getTotalPrice());

        return response;
    }
    private Order buildOrderFromRequest(OrderRequest request) {
        Order order = new Order();
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));
        order.setUser(user);

        List<Salad> salads = new ArrayList<>();
        for (Long saladId : request.getSaladIds()) {
            Salad salad = saladRepository.findById(saladId)
                    .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + saladId));
            salads.add(salad);
        }
        order.setSalads(new ArrayList<>(salads));

        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return order;
    }

    private double calculateOrderTotalPrice(List<Long> saladIds) {
        double totalPrice = 0;

        // Her bir salata kimliğini ve miktarını takip etmek için bir harita kullanın
        Map<Long, Integer> saladQuantityMap = new HashMap<>();

        for (Long saladId : saladIds) {
            saladQuantityMap.put(saladId, saladQuantityMap.getOrDefault(saladId, 0) + 1);
        }

        // Her bir salata kimliği için fiyatı toplam fiyata ekleyin
        for (Map.Entry<Long, Integer> entry : saladQuantityMap.entrySet()) {
            Long saladId = entry.getKey();
            int quantity = entry.getValue();

            Salad salad = saladRepository.findById(saladId)
                    .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + saladId));

            // Salata miktarını da hesaplamaya dahil edin
            totalPrice += salad.getPrice() * quantity;
        }

        return totalPrice;
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long id, OrderRequest request) {
        // Mevcut siparişi veritabanından alın
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Kullanıcının sadece kendi siparişini güncellemesini sağlayın
        if (!existingOrder.getUser().getId().equals(request.getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to update this order.");
        }

        // Yalnızca salata bilgisini güncelleyin
        List<Long> newSaladIds = request.getSaladIds();
        List<Salad> newSalads = getSaladsFromIds(newSaladIds);

        // Yeni salataları kullanarak toplam fiyatı hesaplayın
        double totalPrice = calculateOrderTotalPrice(new ArrayList<>(request.getSaladIds()));

        // Mevcut siparişi güncelleyin
        existingOrder.setSalads(newSalads);
        existingOrder.setTotalPrice(totalPrice);

        // Güncellenmiş siparişi kaydedin
        existingOrder = orderRepository.save(existingOrder); // Mevcut siparişi güncellenmiş haliyle kaydet

        return existingOrder;
    }

    private List<Salad> getSaladsFromIds(List<Long> saladIds) {
        List<Salad> salads = new ArrayList<>();
        for (Long saladId : saladIds) {
            Salad salad = saladRepository.findById(saladId)
                    .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + saladId));
            salads.add(salad);
        }
        return salads;
    }

    public void deleteOrder(Long id) {
        // Get the Order from the repository
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Delete the Order
        orderRepository.delete(order);
    }
}
