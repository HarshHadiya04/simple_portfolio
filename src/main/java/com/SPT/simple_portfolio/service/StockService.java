package com.SPT.simple_portfolio.service;

import com.SPT.simple_portfolio.entity.Stock;
import com.SPT.simple_portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserService userService;

    public Stock createStock(String userId, Stock stock) {
        if (!userService.getUserById(userId).getId().equals(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        stock.setUserId(userId);
        return stockRepository.save(stock);
    }

    public List<Stock> getStocksByUserId(String userId) {
        if (!userService.getUserById(userId).getId().equals(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        return stockRepository.findByUserId(userId);
    }

    public Stock updateStock(String stockId, Stock updatedStock) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock with ID " + stockId + " does not exist."));
        existingStock.setStockName(updatedStock.getStockName());
        existingStock.setQuantity(updatedStock.getQuantity());
        existingStock.setPrice(updatedStock.getPrice());
        return stockRepository.save(existingStock);
    }

    public void deleteStock(String stockId) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock with ID " + stockId + " does not exist."));
        stockRepository.delete(existingStock);
    }
}
