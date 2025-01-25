package com.SPT.simple_portfolio.controller;

import com.SPT.simple_portfolio.entity.Stock;
import com.SPT.simple_portfolio.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "https://simple-portfolio-tracker-one.vercel.app")
public class StockController {
    @Autowired
    private StockService stockService;

    // POST: Create a stock for a user
    @PostMapping("/{user_id}")
    public ResponseEntity<?> createStock(@PathVariable("user_id") String userId, @RequestBody Stock stock) {
        try {
            Stock createdStock = stockService.createStock(userId, stock);
            return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // GET: Retrieve stocks for a user
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getStocksByUserId(@PathVariable("user_id") String userId) {
        try {
            List<Stock> stocks = stockService.getStocksByUserId(userId);
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PUT: Update stock
    @PutMapping("/{stock_id}")
    public ResponseEntity<?> updateStock(@PathVariable("stock_id") String stockId, @RequestBody Stock updatedStock) {
        try {
            Stock stock = stockService.updateStock(stockId, updatedStock);
            return new ResponseEntity<>(stock, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE: Delete a stock by stockId
    @DeleteMapping("/{stock_id}")
    public ResponseEntity<?> deleteStock(@PathVariable("stock_id") String stockId) {
        try {
            stockService.deleteStock(stockId);  // Call the service to delete the stock
            return new ResponseEntity<>("Stock deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
