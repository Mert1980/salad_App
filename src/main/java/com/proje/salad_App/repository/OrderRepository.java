package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
