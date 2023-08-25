package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository <Stock,Long> {

}
