package com.talha.stockquotes.service;

import com.talha.stockquotes.model.StockData;
import com.talha.stockquotes.pojo.StockDataRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.talha.stockquotes.repo.StocksRepository;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class StockDataServiceTest {

	@InjectMocks
	private StockDataService stockDataService;

	@Mock
	private StocksRepository stocksRepository;

	@Mock
	private MongoTemplate mongoTemplate;

	@Test
	public void findAllStocksTest() {
		List<StockData> stockDataList = new ArrayList<StockData>();
		StockData stockData = new StockData();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String datestr = formatter.format(date);
		stockData.setDate(datestr);
		stockData.setStockQuote("HCL");
		stockData.setPrice("100");
		stockData.setCurrency("INR");
		stockData.setCompanyName("HCL Tech");
		stockDataList.add(stockData);
		Mockito.when(stocksRepository.findAll()).thenReturn(stockDataList);
		stockDataService.getAllRecords();
	}

	@Test
	public void findStocksTest(){
		List<String> stockQuotes = new ArrayList<String>(Arrays.asList("HCL", "IBM"));
		stockDataService.getRecords(stockQuotes);
	}

	@Test
	public void createStockExistingTest(){
		StockDataRequest stockDataRequest = new StockDataRequest();
		stockDataRequest.setStockQuote("HCL");
		stockDataRequest.setCompanyName("HCL Tech");
		stockDataRequest.setPrice("100");
		stockDataRequest.setCurrency("INR");
		StockData stockData = new StockData();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String datestr = formatter.format(date);
		stockData.setDate(datestr);
		stockData.setStockQuote("HCL");
		stockData.setPrice("100");
		stockData.setCurrency("INR");
		stockData.setCompanyName("HCL Tech");
		Mockito.when(stocksRepository.findByStockQuote(stockDataRequest.getStockQuote())).thenReturn(Optional.of(stockData));
		stockDataService.createStockData(stockDataRequest);
	}

	@Test
	public void createStockTest(){
		StockDataRequest stockDataRequest = new StockDataRequest();
		stockDataRequest.setStockQuote("HCL");
		stockDataRequest.setCompanyName("HCL Tech");
		stockDataRequest.setPrice("100");
		stockDataRequest.setCurrency("INR");
		stockDataService.createStockData(stockDataRequest);
	}

	@Test
	public void createStockNullTest(){
		StockDataRequest stockDataRequest = new StockDataRequest();
		stockDataRequest.setStockQuote("");
		stockDataRequest.setCompanyName("HCL Tech");
		stockDataRequest.setPrice("100");
		stockDataRequest.setCurrency("INR");
		stockDataService.createStockData(stockDataRequest);
	}

}
