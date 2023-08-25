package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.exeption.IngredientNotFoundException;
import com.proje.salad_App.exeption.SaladNotFoundException;
import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.SaladResponse;
import com.proje.salad_App.repository.IngredientRepository;
import com.proje.salad_App.repository.SaladRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaladService {
    private final SaladRepository saladRepository;
    private final IngredientRepository ingredientRepository;
    public SaladResponse createSalad(SaladRequest request) {
        validateIngredients(request.getIngredientIds());

        Salad salad = new Salad();
        salad.setName(request.getName());

        Set<Ingredient> ingredients = new HashSet<>();
        for (Long ingredientId : request.getIngredientIds()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + ingredientId));
            ingredients.add(ingredient);
        }
        salad.setIngredients(ingredients);

        Salad savedSalad = saladRepository.save(salad);

        return mapSaladEntityToResponse(savedSalad);
    }

    private SaladResponse mapSaladEntityToResponse(Salad savedSalad) {
        SaladResponse response = new SaladResponse();
        response.setId(savedSalad.getId());
        response.setName(savedSalad.getName());
        // Map other fields as needed
        return response;
    }


    private void validateIngredients(Set<Long> ingredientIds) {
        for (Long ingredientId : ingredientIds) {
            ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + ingredientId));
        }

    }

    public SaladResponse getSaladById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Salad id cannot be null");
        }

        Salad salad = saladRepository.findById(id)
                .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + id));

        return mapSaladEntityToResponse(salad);
    }

    public List<SaladResponse> getAllSalads() {
        List<Salad> salads = saladRepository.findAll();

        return salads.stream()
                .map(this::mapSaladEntityToResponse)
                .collect(Collectors.toList());
    }

    public SaladResponse updateSalad(Long id, SaladRequest request) {
        Salad salad = saladRepository.findById(id)
                .orElseThrow(() -> new SaladNotFoundException("Salad not found with id: " + id));

        salad.setName(request.getName());

        // Clear previous ingredients
        salad.getIngredients().clear();

        // Add new ingredients based on ingredientIds from request
        for (Long ingredientId : request.getIngredientIds()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + ingredientId));
            salad.getIngredients().add(ingredient);
        }

        Salad savedSalad = saladRepository.save(salad);
        return mapSaladEntityToResponse(savedSalad);
    }

    public void deleteSalad(Long id) {
        if (!saladRepository.existsById(id)) {
            throw new SaladNotFoundException("Salad not found with id: " + id);
        }

        saladRepository.deleteById(id);
    }
}
