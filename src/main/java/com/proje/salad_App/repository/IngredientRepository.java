package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.entity.enums.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByIngredientType(IngredientType type);
}
