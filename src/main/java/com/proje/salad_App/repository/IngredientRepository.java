package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
