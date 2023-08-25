package com.proje.salad_App.controller;

import com.proje.salad_App.payload.request.IngredientRequest;
import com.proje.salad_App.payload.response.IngredientResponse;
import com.proje.salad_App.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
@Validated
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredient(@PathVariable Long id) {
        IngredientResponse response = ingredientService.getIngredient(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")// http://localhost:8080/ingredients/id
    public ResponseEntity<IngredientResponse> createIngredient(@RequestBody @Valid IngredientRequest ingredientRequest) {
        IngredientResponse ingredientResponse = ingredientService.createIngredient(ingredientRequest);
        return ResponseEntity.ok(ingredientResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> updateIngredient(
            @PathVariable Long id,
            @Valid @RequestBody IngredientRequest request) {
        IngredientResponse response = ingredientService.updateIngredient(id, request);
        return ResponseEntity.ok(response);
    }




}

