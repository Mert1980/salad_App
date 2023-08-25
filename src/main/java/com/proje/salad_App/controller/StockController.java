package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Stock;
import com.proje.salad_App.payload.request.StockRequest;
import com.proje.salad_App.payload.response.StockResponse;
import com.proje.salad_App.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/stocks")
public class StockController {
    private final StockService stockService;

  
    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockById(@PathVariable Long id) {
        // Map Stock entity to StockResponse and return
        Stock stock = stockService.getStockById(id);
        StockResponse stockResponse = mapStockEntityToResponse(stock);
        return ResponseEntity.ok(stockResponse);
    }

    private StockResponse mapStockEntityToResponse(Stock stock) {
        StockResponse stockResponse = new StockResponse();
        // Stock varlığını StockResponse DTO'ya dönüştürme işlemleri
        stockResponse.setQuantity(stock.getQuantity());
        stockResponse.setUnit(stock.getUnit());
        // Diğer gerekli dönüşümleri gerçekleştirin

        return stockResponse;
    }


    @PostMapping
    public ResponseEntity<StockResponse> createStock(@RequestBody StockRequest request) {
        // Create new Stock entity from StockRequest and save it
        // Map saved Stock entity to StockResponse and return
        Stock stock = stockService.mapStockRequestToEntity(request); // mapStockRequestToEntity ile dönüşümü yap
        Stock savedStock = stockService.createStock(stock);
        StockResponse stockResponse = mapStockEntityToResponse(savedStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockResponse);
    }




    @PutMapping("/{id}")
    public ResponseEntity<StockResponse> updateStock(@PathVariable Long id, @RequestBody StockRequest request) {
        // Get the Stock from the repository
        Stock stock = stockService.getStockById(id);

        // Update the Stock entity with data from request
        stock.setQuantity(request.getQuantity());
        stock.setUnit(request.getUnit());

        // Save the updated Stock
        Stock updatedStock = stockService.saveStock(stock);

        // Map the updated Stock to StockResponse and return
        StockResponse response = stockService.mapStockEntityToResponse(updatedStock);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        // Delete the Stock entity
        stockService.deleteStock(id);

        // Return ResponseEntity.ok() if deleted successfully
        return ResponseEntity.ok().build();
    }
}
