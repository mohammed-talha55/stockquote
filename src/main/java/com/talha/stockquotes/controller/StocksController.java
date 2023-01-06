package com.talha.stockquotes.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talha.stockquotes.pojo.StockDataRequest;
import com.talha.stockquotes.service.StockDataService;

@RestController
@RequestMapping("/stockQuote")
public class StocksController {

	@Autowired
	private StockDataService stockDataService;

	@PostMapping(path = "/addQuote")
	public ResponseEntity<Object> createStock(@RequestBody StockDataRequest stockDataRequest) {
		return new ResponseEntity<>(stockDataService.createStockData(stockDataRequest), HttpStatus.OK);
	}

	@GetMapping(path = "/queryQuote")
	public ResponseEntity<Object> getStocks(@RequestParam("symbols") String symbols) {
		System.out.println("Symbols :" + symbols);
		return stockDataService.getRecords(Arrays.asList(symbols.split("\\s*,\\s*")));
	}

	@GetMapping
	public ResponseEntity<Object> getAllStocks() {
		return stockDataService.getAllRecords();
	}
}
