package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Ingredient;
import com.proje.salad_App.entity.concretes.Stock;
import com.proje.salad_App.exeption.IngredientNotFoundException;
import com.proje.salad_App.exeption.StockNotFoundException;
import com.proje.salad_App.payload.request.StockRequest;
import com.proje.salad_App.payload.response.StockResponse;
import com.proje.salad_App.repository.IngredientRepository;
import com.proje.salad_App.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository stockRepository;
    private final IngredientRepository ingredientRepository;


    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + id));
    }

    public Stock createStock(Stock request) {
        // Get the Ingredient from the repository
        Ingredient ingredient = ingredientRepository.findById(request.getIngredientId())
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + request.getIngredientId()));

        // Create a new Stock entity
        Stock stock = new Stock();
        stock.setIngredient(ingredient);
        stock.setQuantity(request.getQuantity());
        stock.setUnit(request.getUnit());

        // Save the Stock entity
        return stockRepository.save(stock);
    }


    public Stock mapStockRequestToEntity(StockRequest request) {
        Ingredient ingredient = ingredientRepository.findById(request.getIngredientId())
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + request.getIngredientId()));

        Stock stock = new Stock();
        stock.setIngredient(ingredient);
        stock.setQuantity(request.getQuantity());
        stock.setUnit(request.getUnit());

        return stock;
    }

    public void deleteStock(Long id) {
        // Get the Stock from the repository
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + id));

        // Delete the Stock
        stockRepository.delete(stock);
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public StockResponse mapStockEntityToResponse(Stock updatedStock) {
        StockResponse response = new StockResponse();
        response.setIngredient(updatedStock.getIngredient());
        response.setQuantity(updatedStock.getQuantity());
        response.setUnit(updatedStock.getUnit());
        return response;
    }

    public StockResponse getStockResponseById(Long id) {
        Stock stock = getStockById(id);
        return mapStockEntityToResponse(stock);
    }
}

