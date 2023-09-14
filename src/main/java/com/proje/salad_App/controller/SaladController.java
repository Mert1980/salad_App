package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.SaladNotFoundException;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.SaladResponse;
import com.proje.salad_App.payload.response.UserResponse;
import com.proje.salad_App.service.SaladService;
import com.proje.salad_App.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/salads")
@RequiredArgsConstructor
public class SaladController {

    private final SaladService saladService;
    private final UserService userService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<SaladResponse> createSalad(@RequestBody SaladRequest request) {
        SaladResponse response = saladService.createSalad(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping("/{id}/calculate-price")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Double> calculateSaladPrice(@PathVariable Long id) {
        // İlgili ID'ye sahip salatayı veritabanından alın
        SaladResponse saladResponse = saladService.getSaladResponseById(id);

        if (saladResponse == null) {
            // Hata işleme kodunu burada ekleyebilirsiniz.
            return ResponseEntity.notFound().build();
        }

        // SaladResponse nesnesinden fiyatı alın
        double totalPrice = saladResponse.getPrice();

        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<SaladResponse> getSaladById(@PathVariable Long id) {
        SaladResponse response = saladService.getSaladById(id);
        return ResponseEntity.ok(response);
    }



    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<SaladResponse>> getAllSalads() {
        List<SaladResponse> responses = saladService.getAllSalads();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<SaladResponse> updateSalad(
            @PathVariable Long id,
            @RequestBody SaladRequest request
    ) {
        SaladResponse response = saladService.updateSalad(id, request);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Void> deleteSalad(@PathVariable Long id) {
        SaladResponse response = saladService.getSaladById(id);

        if (response == null) {
            throw new SaladNotFoundException("Salad not found with id: " + id);
        }

        saladService.deleteSalad(id);
        return ResponseEntity.noContent().build();
    }
}
