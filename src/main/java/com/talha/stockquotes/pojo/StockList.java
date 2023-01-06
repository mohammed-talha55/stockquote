package com.talha.stockquotes.pojo;

import java.util.List;

public class StockList {

	private List<StockResponse> stocks;

	public StockList(List<StockResponse> stocks) {
		this.stocks = stocks;
	}

	public List<StockResponse> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockResponse> stocks) {
		this.stocks = stocks;
	}

}
