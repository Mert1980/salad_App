package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.exeption.IngredientNotFoundException;
import com.proje.salad_App.exeption.SaladNotFoundException;
import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.IngredientResponse;
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

    public SaladResponse createSalad(SaladRequest request, User user) {
        Salad salad = prepareSalad(request, user);
        return saveSalad(salad);
    }
    public SaladResponse createCustomSalad(SaladRequest request, User user) {
        Salad salad = prepareSalad(request, user);
        return saveSalad(salad);
    }

    private Salad prepareSalad(SaladRequest request, User user) {
        Set<Ingredient> ingredients = validateAndRetrieveIngredients(request.getIngredientIds());

        Salad salad = new Salad();
        salad.setName(request.getName());
        salad.setIngredients(ingredients);
        salad.setUser(user);

        return salad;
    }

    private Set<Ingredient> validateAndRetrieveIngredients(Set<Long> ingredientIds) {
        Set<Ingredient> ingredients = new HashSet<>();
        for (Long ingredientId : ingredientIds) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + ingredientId));
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private SaladResponse mapSaladEntityToResponse(Salad savedSalad) {
        SaladResponse response = new SaladResponse();
        response.setId(savedSalad.getId());
        response.setName(savedSalad.getName());
        // Map other fields as needed
        return response;
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

    public SaladResponse saveSalad(Salad salad) {
        Salad savedSalad = saladRepository.save(salad);
        return mapSaladEntityToResponse(savedSalad);
    }



    public double calculateSaladPrice(SaladResponse saladResponse) {
        // SaladResponse nesnesinden gerekli bilgileri kullanarak fiyat hesaplamasını yapın
        double totalPrice = 0.0;

        for (IngredientResponse ingredientResponse : saladResponse.getIngredients()) {
            // Malzeme fiyatlarını hesaplayarak totalPrice'e ekleyin
            totalPrice += ingredientResponse.getPrice();
        }

        return totalPrice;
    }

}
