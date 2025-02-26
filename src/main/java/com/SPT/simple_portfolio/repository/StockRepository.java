package com.SPT.simple_portfolio.repository;

import com.SPT.simple_portfolio.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    List<Stock> findByUserId(String userId);
}
