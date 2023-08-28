package com.proje.salad_App.controller;
import com.proje.salad_App.entity.concretes.Salad;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.SaladResponse;
import com.proje.salad_App.service.SaladService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salads")
@RequiredArgsConstructor
public class SaladController {
    private final SaladService saladService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<SaladResponse> createSalad(@RequestBody SaladRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.createSalad(request, user); // Kullanmak istediğiniz metod

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create-custom-salad")
    public ResponseEntity<SaladResponse> createCustomSalad(@RequestBody SaladRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.createCustomSalad(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/calculate-price")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Double> calculateSaladPrice(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.getSaladById(id);

        if (!response.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to calculate the price of this salad.");
        }

        double totalPrice = saladService.calculateSaladPrice(response);

        return ResponseEntity.ok(totalPrice);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<SaladResponse> getSaladById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.getSaladById(id);

        if (!response.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to view this salad.");
        }

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
    public ResponseEntity<SaladResponse> updateSalad(@PathVariable Long id, @RequestBody SaladRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.updateSalad(id, request);

        if (!response.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to update this salad.");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Void> deleteSalad(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        SaladResponse response = saladService.getSaladById(id);

        if (!response.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to delete this salad.");
        }

        saladService.deleteSalad(id);
        return ResponseEntity.noContent().build();
    }


}
