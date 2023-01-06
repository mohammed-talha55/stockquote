package com.talha.stockquotes.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.talha.stockquotes.model.StockData;
import com.talha.stockquotes.pojo.Response;
import com.talha.stockquotes.pojo.StockDataRequest;
import com.talha.stockquotes.pojo.StockList;
import com.talha.stockquotes.pojo.StockResponse;
import com.talha.stockquotes.repo.StocksRepository;

@Service
public class StockDataService {

	@Autowired
	private StocksRepository stocksRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Response createStockData(StockDataRequest stockDataRequest) {

		Response response = new Response();
		if (checkInput(stockDataRequest)) {
			response.setResponseCode("403");
			response.setResponseStatus("Invalid Input");
			return response;
		} else {
			StockData stockData;
			Optional<StockData> stockDataResponse = stocksRepository.findByStockQuote(stockDataRequest.getStockQuote());
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
			String datestr = formatter.format(date);
			// Checking if the stock already exists. If yes, then updating the existing
			// stock
			if (stockDataResponse.isPresent()) {
				stockData = stockDataResponse.get();
				stockData.setPrice(priceFormater(stockDataRequest.getPrice()));
				response.setResponseCode("204");
				response.setResponseStatus("Stock Data Updated");
				stockData.setDate(datestr);
				stocksRepository.save(stockData);
			} else {
				if (stocksRepository.findAll().size() < 10) {
					stockData = new StockData();
					stockData.setCompanyName(stockDataRequest.getCompanyName());
					stockData.setCurrency(stockDataRequest.getCurrency());
					stockData.setPrice(priceFormater(stockDataRequest.getPrice()));
					stockData.setStockQuote(stockDataRequest.getStockQuote().toUpperCase());
					response.setResponseCode("201");
					response.setResponseStatus("Stock Data Added");
					stockData.setDate(datestr);
					stocksRepository.save(stockData);
				} else {
					response.setResponseCode("429");
					response.setResponseStatus("Limit Exceeded");
				}

			}
			return response;
		}
	}

	private boolean checkInput(StockDataRequest stockDataRequest) {
		if (stockDataRequest == null || stockDataRequest.getCompanyName() == null
				|| stockDataRequest.getCurrency() == null || stockDataRequest.getPrice() == null
				|| stockDataRequest.getStockQuote() == null || stockDataRequest.getCompanyName().isEmpty()
				|| stockDataRequest.getCurrency().isEmpty() || stockDataRequest.getPrice().isEmpty()
				|| stockDataRequest.getStockQuote().isEmpty())
			return true;
		else
			return false;
	}

	public ResponseEntity<Object> getAllRecords() {
		List<StockData> allRecords = stocksRepository.findAll();
		return new ResponseEntity<>(getResponseFormat(allRecords), HttpStatus.OK);
	}

	public ResponseEntity<Object> getRecords(List<String> stockQuotes) {
		Criteria criteria = new Criteria();
		List<Criteria> listCriterias = new ArrayList<Criteria>(stockQuotes.size());
		for (String quote : stockQuotes) {
			listCriterias.add(Criteria.where("stockQuote").is(quote.toUpperCase()));
		}
		criteria = criteria.orOperator(listCriterias.toArray(new Criteria[stockQuotes.size()]));
		Query searchQuery = new Query(criteria);
		System.out.println(searchQuery);
		List<StockData> response = mongoTemplate.find(searchQuery, StockData.class);
		return new ResponseEntity<>(getResponseFormat(response), HttpStatus.OK);
	}

	public String priceFormater(String price) {
		Float priceFloat = Float.parseFloat(price);
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		return df.format(priceFloat);
	}

	private StockList getResponseFormat(List<StockData> stockDataList) {
		List<StockResponse> stockResponseList = new ArrayList<StockResponse>();
		for (int i = 0; i < stockDataList.size(); i++) {
			StockResponse stockResponse = new StockResponse();
			stockResponse.setStockQuote(stockDataList.get(i).getStockQuote());
			stockResponse.setCompanyName(stockDataList.get(i).getCompanyName());
			stockResponse.setDate(stockDataList.get(i).getDate());
			stockResponse.setPrice(stockDataList.get(i).getPrice());
			stockResponse.setCurrency(stockDataList.get(i).getCurrency());
			stockResponseList.add(stockResponse);
		}
		return new StockList(stockResponseList);
	}

}
