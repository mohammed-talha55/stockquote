package com.talha.stockquotes.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.talha.stockquotes.model.StockData;

public interface StocksRepository extends MongoRepository<StockData, String> {

	@Query("{stockQuote:'?0'}")
	Optional<StockData> findByStockQuote(String stockQuote);
}
