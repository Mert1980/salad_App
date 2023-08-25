package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.exeption.IngredientNotFoundException;
import com.proje.salad_App.payload.request.IngredientRequest;
import com.proje.salad_App.payload.response.IngredientResponse;
import com.proje.salad_App.repository.IngredientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService { private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientResponse> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = new Ingredient();
        BeanUtils.copyProperties(ingredientRequest, ingredient);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return convertToResponse(savedIngredient);
    }

    private IngredientResponse convertToResponse(Ingredient ingredient) {
        IngredientResponse ingredientResponse = new IngredientResponse();
        BeanUtils.copyProperties(ingredient, ingredientResponse);
        return ingredientResponse;
    }
    public IngredientResponse getIngredient(Long id) {
        // Get the Ingredient from the repository
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Map Ingredient entity to IngredientResponse
        return mapIngredientEntityToResponse(ingredient);
    }
    public IngredientResponse updateIngredient(Long id, IngredientRequest request) {
        // Get the Ingredient from the repository
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Update the Ingredient entity with data from request
        updateIngredientEntityFromRequest(ingredient, request);

        // Save the updated Ingredient
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);

        // Map the updated Ingredient to IngredientResponse
        return mapIngredientEntityToResponse(updatedIngredient);
    }
    private void updateIngredientEntityFromRequest(Ingredient ingredient, IngredientRequest request) {
        if (request.getName() != null) {
            ingredient.setName(request.getName());
        }

        if (request.getIngredientType() != null) {
            ingredient.setIngredientType(request.getIngredientType());
        }
    }

    public void deleteIngredient(Long id) {
        // Get the Ingredient from the repository
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Delete the Ingredient
        ingredientRepository.delete(ingredient);
    }
    private Ingredient mapIngredientRequestToEntity(IngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setPrice(request.getPrice());
        ingredient.setIngredientType(request.getIngredientType());
        // Set adet and unit based on request, if applicable
        return ingredient;
    }

    private IngredientResponse mapIngredientEntityToResponse(Ingredient ingredient) {
        IngredientResponse response = new IngredientResponse();
        response.setName(ingredient.getName());
        response.setPrice(ingredient.getPrice());
        response.setIngredientType(ingredient.getIngredientType());
        // Set adet and unit based on entity, if applicable
        return response;
    }



}
