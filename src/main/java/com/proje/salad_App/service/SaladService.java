package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.exeption.*;
import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.AdminResponse;
import com.proje.salad_App.payload.response.IngredientResponse;
import com.proje.salad_App.payload.response.SaladResponse;
import com.proje.salad_App.payload.response.UserResponse;
import com.proje.salad_App.repository.AdminRepository;
import com.proje.salad_App.repository.IngredientRepository;
import com.proje.salad_App.repository.SaladRepository;
import com.proje.salad_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaladService {
    private final SaladRepository saladRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdminService adminService;
    private final AdminRepository adminRepository;




    public SaladResponse createSalad(SaladRequest request) {
        Salad salad = prepareSalad(request); // User ve Admin bağlantısını kaldırdık
        return saveSalad(salad);
    }


    private Salad prepareSalad(SaladRequest request) {
        List<Ingredient> ingredients = validateAndRetrieveIngredients(request.getIngredientIds());

        Salad salad = new Salad();
        salad.setName(request.getName());
        salad.setIngredients(ingredients);

        salad.setPrice(this.calculateSaladPrice(salad));

        return salad;
    }

    private List<Ingredient> validateAndRetrieveIngredients(List<Long> ingredientIds) {
        List<Ingredient> ingredients = new ArrayList<>();
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
        response.setPrice(savedSalad.getPrice());

        // Ingredients'ı dönüştürmeden doğrudan atama yap
        response.setIngredients(savedSalad.getIngredients().stream()
                .map(this::mapIngredientEntityToResponse)
                .collect(Collectors.toSet()));

        // Diğer alanları da gerektiği gibi doldurabilirsiniz
        return response;
    }



    private IngredientResponse mapIngredientEntityToResponse(Ingredient ingredient) {
        IngredientResponse response = new IngredientResponse();
        response.setId(ingredient.getId());
        response.setName(ingredient.getName());
        response.setPrice(ingredient.getPrice());
        response.setIngredientType(ingredient.getIngredientType());
        // Diğer alanları da gerektiği gibi doldurabilirsiniz
        return response;
    }


    public SaladResponse getSaladById(Long id) {
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
        List<Ingredient> updatedIngredients = new ArrayList<>();
        for (Long ingredientId : request.getIngredientIds()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + ingredientId));
            updatedIngredients.add(ingredient);
        }
        salad.setIngredients(updatedIngredients);
        salad.setPrice(calculateSaladPrice(salad));

        Salad savedSalad = saladRepository.save(salad);
        return mapSaladEntityToResponse(savedSalad);
    }

    public SaladResponse saveSalad(Salad salad) {
        Salad savedSalad = saladRepository.save(salad);
        return mapSaladEntityToResponse(savedSalad);
    }

    public void deleteSalad(Long id) {
        if (!saladRepository.existsById(id)) {
            throw new SaladNotFoundException("Salad not found with id: " + id);
        }

        saladRepository.deleteById(id);
    }


    public double calculateSaladPrice(Salad salad) {
        double totalPrice = 0.0;
        for (Ingredient ingredient : salad.getIngredients()) {

            double ingredientTotalPrice = ingredient.getPrice();
            totalPrice += ingredientTotalPrice;
        }
        return totalPrice;
    }


    public SaladResponse getSaladResponseById(Long id) {
        Salad salad = saladRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salad not found with id: " + id));

        // Salad nesnesini kullanarak bir SaladResponse nesnesi oluşturabilirsiniz.
        SaladResponse saladResponse = new SaladResponse();
        saladResponse.setId(salad.getId());
        saladResponse.setName(salad.getName());
        saladResponse.setPrice(salad.getPrice());
        // Diğer özellikleri de ekleyebilirsiniz.

        return saladResponse;
    }
}


