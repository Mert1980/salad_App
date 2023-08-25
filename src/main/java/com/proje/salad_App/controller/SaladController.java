package com.proje.salad_App.controller;

import com.proje.salad_App.payload.request.SaladRequest;
import com.proje.salad_App.payload.response.SaladResponse;
import com.proje.salad_App.service.OrderService;
import com.proje.salad_App.service.SaladService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salads")
@RequiredArgsConstructor
public class SaladController {
    private final SaladService saladService;

    @PostMapping
    public ResponseEntity<SaladResponse> createSalad(@RequestBody SaladRequest request) {
        SaladResponse response = saladService.createSalad(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ GetMapping("/{id}")
    public ResponseEntity<SaladResponse> getSaladById(@PathVariable Long id) {
        SaladResponse response = saladService.getSaladById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SaladResponse>> getAllSalads() {
        List<SaladResponse> responses = saladService.getAllSalads();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaladResponse> updateSalad(@PathVariable Long id, @RequestBody SaladRequest request) {
        SaladResponse response = saladService.updateSalad(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalad(@PathVariable Long id) {
        saladService.deleteSalad(id);
        return ResponseEntity.noContent().build();
    }
}
