package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Salad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaladRepository extends JpaRepository <Salad,Long>{
}
