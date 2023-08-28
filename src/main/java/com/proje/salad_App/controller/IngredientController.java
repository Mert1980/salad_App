package com.proje.salad_App.controller;

import com.proje.salad_App.payload.request.IngredientRequest;
import com.proje.salad_App.payload.response.IngredientResponse;
import com.proje.salad_App.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
@Validated
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<IngredientResponse> getIngredient(@PathVariable Long id) {
        IngredientResponse response = ingredientService.getIngredient(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping// http://localhost:8080/ingredients/id
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<IngredientResponse> createIngredient(@RequestBody @Valid IngredientRequest ingredientRequest) {
        IngredientResponse ingredientResponse = ingredientService.createIngredient(ingredientRequest);
        return ResponseEntity.ok(ingredientResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<IngredientResponse> updateIngredient(
            @PathVariable Long id,
            @Valid @RequestBody IngredientRequest request) {
        IngredientResponse response = ingredientService.updateIngredient(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}

